<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Форма пользователя</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/jspf/header.jsp" />
    <div class="container mt-4">
        <h2><c:if test="${empty user.id}">Добавление</c:if><c:if test="${not empty user.id}">Редактирование</c:if> пользователя</h2>
        <form method="post">
            <c:if test="${not empty user.id}">
                <input type="hidden" name="id" value="${user.id}">
            </c:if>
            <div class="mb-3">
                <label class="form-label">Логин</label>
                <input type="text" name="username" value="${user.username}" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Пароль</label>
                <input type="text" name="password" value="${user.password}" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Имя</label>
                <input type="text" name="firstName" value="${user.firstName}" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Фамилия</label>
                <input type="text" name="lastName" value="${user.lastName}" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Телефон</label>
                <input type="text" name="phone" value="${user.phone}" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Роль</label>
                <select name="role" class="form-control">
                    <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>USER</option>
                    <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                </select>
            </div>
            <button type="submit" class="btn btn-success">Сохранить</button>
            <a href="users" class="btn btn-secondary">Отмена</a>
        </form>
    </div>
    <jsp:include page="/WEB-INF/jspf/footer.jsp" />
</body>
</html>