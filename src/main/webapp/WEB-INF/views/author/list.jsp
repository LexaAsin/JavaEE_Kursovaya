<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    HttpSession ses = request.getSession(false);
    String role = (ses != null) ? (String) ses.getAttribute("role") : "";
    boolean isAdmin = "ADMIN".equals(role);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Авторы</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%); }
        .container { background: white; border-radius: 20px; box-shadow: 0 0 20px rgba(0,0,0,0.1); padding: 30px; margin-top: 30px; }
        .table thead th { background-color: #343a40; color: white; }
        h2 { border-left: 5px solid #4e73df; padding-left: 15px; }
        footer { background-color: #343a40; color: white; padding: 15px 0; margin-top: 30px; text-align: center; }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/jspf/header.jsp" />
    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h2>✍️ Список авторов</h2>
            <% if (isAdmin) { %>
                <a href="authors?action=new" class="btn btn-success">➕ Добавить автора</a>
            <% } %>
        </div>
        <table class="table table-bordered table-hover">
            <thead><tr><th>ID</th><th>ФИО</th><th>Телефон</th><th>Email</th><th>Рейтинг</th><th>Действия</th></tr></thead>
            <tbody>
                <c:forEach var="author" items="${authors}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>   <!-- порядковый номер -->
                        <td>${author.fullName}</td>
                        <td>${author.phone}</td>
                        <td>${author.email}</td>
                        <td>${author.rating}</td>
                        <td>
                            <div class="btn-group">
                                <a href="authors?action=edit&id=${author.id}" class="btn btn-sm btn-warning">✏️ Ред.</a>
                                <% if (isAdmin) { %>
                                    <a href="authors?action=delete&id=${author.id}" class="btn btn-sm btn-danger" onclick="return confirm('Удалить автора?')">🗑️ Уд.</a>
                                <% } %>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <footer><p>© 2026 Библиотека на JavaEE. Все права защищены.</p></footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>