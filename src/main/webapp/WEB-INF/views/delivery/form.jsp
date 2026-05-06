<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:if test="${empty delivery.id}">Новая выдача</c:if><c:if test="${not empty delivery.id}">Редактирование выдачи</c:if></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%); }
        .form-container { background: white; border-radius: 20px; box-shadow: 0 0 20px rgba(0,0,0,0.1); padding: 30px; max-width: 600px; margin: 40px auto; }
        h2 { border-left: 5px solid #4e73df; padding-left: 15px; }
        footer { background-color: #343a40; color: white; padding: 15px 0; margin-top: 30px; text-align: center; }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/jspf/header.jsp" />
    <div class="form-container">
        <h2><c:if test="${empty delivery.id}">➕ Новая выдача</c:if><c:if test="${not empty delivery.id}">✏️ Редактирование выдачи</c:if></h2>
        <form method="post">
            <c:if test="${not empty delivery.id}"><input type="hidden" name="id" value="${delivery.id}"></c:if>
            <div class="mb-3">
                <label class="form-label">Книга</label>
                <select name="bookId" class="form-control" required>
                    <option value="">Выберите книгу</option>
                    <c:forEach var="book" items="${books}">
                        <option value="${book.id}" ${delivery.book.id == book.id ? 'selected' : ''}>${book.title}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Пользователь</label>
                <select name="userId" class="form-control" required>
                    <option value="">Выберите пользователя</option>
                    <c:forEach var="user" items="${users}">
                        <option value="${user.id}" ${delivery.user.id == user.id ? 'selected' : ''}>
                            ${user.firstName} ${user.lastName} (${user.username})
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Дата выдачи</label>
                <input type="date" name="dateInput" value="${delivery.dateInput}" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Дата возврата</label>
                <input type="date" name="dateOutput" value="${delivery.dateOutput != null ? delivery.dateOutput : ''}" class="form-control">
            </div>
            <button type="submit" class="btn btn-success">💾 Сохранить</button>
            <a href="deliveries" class="btn btn-secondary">❌ Отмена</a>
        </form>
    </div>
    <footer><p>© 2026 Библиотека на JavaEE. Все права защищены.</p></footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>