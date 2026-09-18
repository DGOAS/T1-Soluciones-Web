package com.billetera.servlet;

import com.billetera.model.Billetera;
import com.billetera.model.SaldoInsuficienteException;
import com.billetera.model.TipoMovimiento;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet controlador de la Billetera Digital.
 *
 * GET  /billetera -> muestra el saldo actual y el historial de movimientos.
 * POST /billetera -> registra un nuevo movimiento (Recarga o Gasto) y,
 *                     si todo sale bien, aplica el patrón Post-Redirect-Get
 *                     (sendRedirect en vez de forward) para que un F5 en el
 *                     navegador no vuelva a enviar el mismo movimiento.
 *
 * Los datos (saldo, movimientos) NO se guardan en base de datos: viven en
 * la sesión HTTP mientras dure la conversación con el navegador.
 */
@WebServlet("/billetera")
public class BilleteraServlet extends HttpServlet {

    private static final double SALDO_INICIAL = 250.00;
    private static final String ATRIBUTO_SESION = "billetera";
    private static final String VISTA_JSP = "/WEB-INF/billetera.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        mostrarVista(req, resp, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Billetera billetera = obtenerBilletera(req);
        String error = validarYRegistrar(req, billetera);

        if (error != null) {
            // Falló la validación o la regla de negocio: no hubo cambios de
            // estado, así que no hace falta redirect; mostramos el error
            // en la misma vista mediante forward().
            mostrarVista(req, resp, error);
            return;
        }

        // Post-Redirect-Get: el POST termina en sendRedirect(), no en forward().
        resp.sendRedirect(req.getContextPath() + "/billetera");
    }

    /**
     * Valida los parámetros del formulario y, si son correctos, delega en
     * Billetera el registro del movimiento (ahí vive la regla de negocio
     * central: un Gasto no puede superar el saldo).
     *
     * @return el mensaje de error si algo falló, o null si todo salió bien.
     */
    private String validarYRegistrar(HttpServletRequest req, Billetera billetera) {
        String tipoParam = req.getParameter("tipo");
        String montoParam = req.getParameter("monto");

        TipoMovimiento tipo;
        try {
            tipo = TipoMovimiento.valueOf(tipoParam);
        } catch (IllegalArgumentException | NullPointerException e) {
            return "Debe seleccionar un tipo de movimiento válido (Recarga o Gasto).";
        }

        double monto;
        try {
            monto = Double.parseDouble(montoParam);
        } catch (NumberFormatException e) {
            return "El monto ingresado no es un número válido.";
        }

        if (monto <= 0) {
            return "El monto debe ser mayor a cero.";
        }

        try {
            billetera.registrarMovimiento(tipo, monto);
        } catch (SaldoInsuficienteException e) {
            return e.getMessage();
        }

        return null;
    }

    /** Arma los datos de la billetera como atributos del request y hace forward a la JSP. */
    private void mostrarVista(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
        Billetera billetera = obtenerBilletera(req);
        req.setAttribute("saldo", billetera.getSaldo());
        req.setAttribute("movimientos", billetera.getMovimientos());
        if (error != null) {
            req.setAttribute("error", error);
        }
        RequestDispatcher rd = req.getRequestDispatcher(VISTA_JSP);
        rd.forward(req, resp);
    }

    /** Obtiene la billetera de la sesión actual, creándola con el saldo de prueba si no existe. */
    private Billetera obtenerBilletera(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Billetera billetera = (Billetera) session.getAttribute(ATRIBUTO_SESION);
        if (billetera == null) {
            billetera = new Billetera(SALDO_INICIAL);
            session.setAttribute(ATRIBUTO_SESION, billetera);
        }
        return billetera;
    }
}
