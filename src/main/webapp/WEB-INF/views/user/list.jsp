<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Пользователи</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/jspf/header.jsp" />
    <div class="container mt-4">
        <h2>👥 Пользователи</h2>
        <a href="users?action=new" class="btn btn-success mb-3">➕ Добавить пользователя</a>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Логин</th>
                    <th>Пароль</th>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Телефон</th>
                    <th>Роль</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.password}</td>
                        <td>${user.firstName != null ? user.firstName : ""}</td>
                        <td>${user.lastName != null ? user.lastName : ""}</td>
                        <td>${user.phone != null ? user.phone : ""}</td>
                        <td>${user.role}</td>
                        <td>
                            <a href="users?action=edit&id=${user.id}" class="btn btn-sm btn-warning">✏️ Ред.</a>
                            <a href="users?action=delete&id=${user.id}" class="btn btn-sm btn-danger" onclick="return confirm('Удалить пользователя?')">🗑️ Уд.</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <jsp:include page="/WEB-INF/jspf/footer.jsp" />
</body>
</html>