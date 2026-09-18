# Billetera Digital — Caso 1 (Servlet + JSP)

Aplicación web simple (sin base de datos) que simula una billetera digital:
saldo en memoria (sesión HTTP) e historial de movimientos (Recarga / Gasto).

## Estructura del proyecto

```
src/main/java/com/billetera/model/
    TipoMovimiento.java          -> enum RECARGA / GASTO
    Movimiento.java               -> un movimiento individual (inmutable)
    Billetera.java                 -> saldo + historial + regla de negocio central
    SaldoInsuficienteException.java -> excepción de negocio (Gasto > saldo)

src/main/java/com/billetera/servlet/
    BilleteraServlet.java          -> Servlet controlador (@WebServlet("/billetera"))

src/main/webapp/
    index.html                     -> redirige a /billetera
    WEB-INF/web.xml                -> configuración mínima
    WEB-INF/billetera.jsp          -> vista (JSTL + Bootstrap, sin scriptlets)
```

## Cómo funciona

- `GET /billetera` -> el Servlet arma el saldo y el historial como atributos
  del request y hace `forward()` a `billetera.jsp`.
- `POST /billetera` -> el Servlet valida el monto (> 0) y, si es un Gasto,
  que no supere el saldo actual (delegado en `Billetera.registrarMovimiento`).
  - Si la validación falla: se hace `forward()` de nuevo a la misma vista
    mostrando el mensaje de error (no hay redirect porque no se modificó nada).
  - Si todo sale bien: se hace `response.sendRedirect()` de vuelta a
    `/billetera` (patrón **Post-Redirect-Get**), para que recargar la
    página (F5) no vuelva a enviar el mismo movimiento.
- El saldo y los movimientos viven en la **sesión HTTP** (`HttpSession`),
  no hay base de datos.

## Opción A — Ejecutar con Maven (recomendado)

Requiere tener Maven y acceso a internet (para descargar las dependencias
de Maven Central la primera vez).

```bash
mvn clean package
```

Esto genera `target/billetera-digital.war`. Cópialo a la carpeta
`webapps/` de tu instalación de Tomcat 9 (o superior compatible con
Servlet 4.0/javax.servlet):

```bash
cp target/billetera-digital.war /ruta/a/tomcat/webapps/
```

Inicia Tomcat y entra a:

```
http://localhost:8080/billetera-digital/
```

## Opción B — Usar el WAR ya compilado

Si te compartieron el archivo `billetera-digital.war` ya generado, no
necesitas Maven ni internet para desplegarlo: ya incluye las librerías
JSTL dentro de `WEB-INF/lib`. Solo cópialo a `webapps/` de tu Tomcat y
listo.

## Notas

- El único requisito externo es la hoja de estilos de Bootstrap, que se
  carga desde un CDN (`cdn.jsdelivr.net`) — se necesita conexión a
  internet en el **navegador** para verla con estilos, aunque el servidor
  funcione sin conexión.
- El saldo de prueba inicial es **S/ 250.00** y se reinicia si se invalida
  la sesión (por ejemplo, cerrando el navegador o esperando el timeout de
  sesión de Tomcat).
