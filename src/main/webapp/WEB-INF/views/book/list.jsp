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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Книги</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%); }
        .container-custom { background: rgba(255,255,255,0.95); border-radius: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.1); padding: 30px; margin-top: 30px; }
        .table thead th { background-color: #343a40; color: white; }
        h2 { border-left: 5px solid #4e73df; padding-left: 15px; }
        footer { background-color: #343a40; color: white; padding: 15px 0; margin-top: 30px; text-align: center; }
        .btn-group { display: flex; gap: 5px; align-items: center; flex-wrap: wrap; }
        .take-book-form { display: flex; gap: 5px; align-items: center; flex-wrap: wrap; }
        .form-select-sm, .form-control-sm { width: auto; }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/jspf/header.jsp" />
    <div class="container container-custom">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h2>📚 Список книг</h2>
            <% if (isAdmin) { %>
                <a href="books?action=new" class="btn btn-success">➕ Добавить книгу</a>
            <% } %>
        </div>
        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th>№</th>
                    <th>Название</th>
                    <th>Автор</th>
                    <th>Жанр</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${books}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${book.title}</td>
                        <td>${book.author.fullName}</td>
                        <td>${book.genre}</td>
                        <td>
                            <div class="btn-group">
                                <% if (isAdmin) { %>
                                    <a href="books?action=edit&id=${book.id}" class="btn btn-sm btn-warning">✏️ Ред.</a>
                                    <a href="books?action=delete&id=${book.id}" class="btn btn-sm btn-danger" onclick="return confirm('Удалить книгу?')">🗑️ Уд.</a>
                                <% } else { %>
                                    <form method="post" action="deliveries?action=take" class="take-book-form">
                                        <input type="hidden" name="bookId" value="${book.id}">
                                        <select name="days" class="form-select form-select-sm" required>
                                            <option value="7">1 неделя</option>
                                            <option value="14" selected>2 недели</option>
                                            <option value="28">4 недели</option>
                                        </select>
                                        <input type="number" name="customDays" placeholder="дней" class="form-control form-control-sm" style="display:none;">
                                        <button type="submit" class="btn btn-sm btn-primary">📖 Взять</button>
                                    </form>
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