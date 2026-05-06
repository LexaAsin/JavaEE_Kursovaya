<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:if test="${empty author.id}">Добавление</c:if><c:if test="${not empty author.id}">Редактирование</c:if> автора</title>
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
        <h2><c:if test="${empty author.id}">➕ Новый автор</c:if><c:if test="${not empty author.id}">✏️ Редактирование автора</c:if></h2>
        <form method="post">
            <c:if test="${not empty author.id}"><input type="hidden" name="id" value="${author.id}"></c:if>
            <div class="mb-3">
                <label class="form-label">ФИО</label>
                <input type="text" name="fullName" value="${author.fullName}" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Телефон</label>
                <input type="text" name="phone" value="${author.phone}" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" value="${author.email}" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Рейтинг</label>
                <input type="number" step="0.1" name="rating" value="${author.rating}" class="form-control">
            </div>
            <button type="submit" class="btn btn-success">💾 Сохранить</button>
            <a href="authors" class="btn btn-secondary">❌ Отмена</a>
        </form>
    </div>
    <footer><p>© 2026 Библиотека на JavaEE. Все права защищены.</p></footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>