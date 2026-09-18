<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mi Billetera Digital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <span class="navbar-brand mb-0 h1">Mi Billetera Digital</span>
    </div>
</nav>

<div class="container pb-5">

    <!-- Saldo actual -->
    <div class="card mb-4 shadow-sm">
        <div class="card-body">
            <h6 class="text-muted mb-1">Saldo actual</h6>
            <h2 class="text-success fw-bold mb-0">
                S/ <fmt:formatNumber value="${saldo}" minFractionDigits="2" maxFractionDigits="2"/>
            </h2>
        </div>
    </div>

    <!-- Mensaje de error de validación / regla de negocio -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
    </c:if>

    <!-- Historial de movimientos -->
    <div class="card mb-4 shadow-sm">
        <div class="card-header bg-white">
            <strong>Movimientos recientes</strong>
        </div>
        <div class="card-body p-0">
            <table class="table table-striped mb-0 align-middle">
                <thead>
                <tr>
                    <th>Fecha</th>
                    <th>Tipo</th>
                    <th class="text-end">Monto</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty movimientos}">
                        <tr>
                            <td colspan="3" class="text-center text-muted py-4">
                                Aún no hay movimientos registrados.
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="mov" items="${movimientos}">
                            <tr>
                                <td>${mov.fechaFormateada}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${mov.recarga}">
                                            <span class="badge bg-success">Recarga</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">Gasto</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <c:choose>
                                    <c:when test="${mov.recarga}">
                                        <td class="text-end fw-semibold text-success">
                                            + S/ <fmt:formatNumber value="${mov.monto}" minFractionDigits="2" maxFractionDigits="2"/>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="text-end fw-semibold text-danger">
                                            - S/ <fmt:formatNumber value="${mov.monto}" minFractionDigits="2" maxFractionDigits="2"/>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Formulario de nuevo movimiento -->
    <div class="card shadow-sm">
        <div class="card-header bg-white">
            <strong>Nuevo movimiento</strong>
        </div>
        <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/billetera" class="row g-3">
                <div class="col-md-5">
                    <label for="tipo" class="form-label">Tipo</label>
                    <select class="form-select" id="tipo" name="tipo" required>
                        <option value="RECARGA">Recarga</option>
                        <option value="GASTO">Gasto</option>
                    </select>
                </div>
                <div class="col-md-5">
                    <label for="monto" class="form-label">Monto (S/)</label>
                    <input type="number" step="0.01" min="0.01" class="form-control"
                           id="monto" name="monto" placeholder="0.00" required>
                </div>
                <div class="col-md-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">Registrar movimiento</button>
                </div>
            </form>
        </div>
    </div>

</div>
</body>
</html>
