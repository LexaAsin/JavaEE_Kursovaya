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
    <title>Выдача книг</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/jspf/header.jsp" />
    <div class="container mt-4">
        <h2>📅 Список выдач</h2>
        <% if (isAdmin) { %>
            <a href="deliveries?action=new" class="btn btn-success mb-3">➕ Новая выдача</a>
        <% } %>
        <table class="table table-bordered">
            <thead><tr><th>ID</th><th>Книга</th><th>Пользователь</th><th>Дата выдачи</th><th>Дата возврата</th><th>Действия</th></tr></thead>
            <tbody>
                <c:forEach var="book" items="${books}" varStatus="status">
				    <tr>
				        <td>${status.index + 1}</td>      <!-- порядковый номер вместо ID -->
				        <td>${d.user.firstName} ${d.user.lastName} (${d.user.username})</td>
				        <td>${book.title}</td>
				        <td>${book.author.fullName}</td>
				        <td>${book.genre}</td>
				        <td>
				            <div class="btn-group">
				                <% if (isAdmin) { %>
				                    <a href="books?action=edit&id=${book.id}" class="btn btn-sm btn-warning">✏️ Ред.</a>
				                    <a href="books?action=delete&id=${book.id}" class="btn btn-sm btn-danger" onclick="return confirm('Удалить книгу?')">🗑️ Уд.</a>
				                <% } else { %>
				                    <form method="post" action="deliveries?action=take" style="display:inline;">
				                        <input type="hidden" name="bookId" value="${book.id}">
				                        <select name="days" class="form-select form-select-sm d-inline-block w-auto" required>
				                            <option value="7">1 неделя</option>
				                            <option value="14" selected>2 недели</option>
				                            <option value="28">4 недели</option>
				                            <option value="0">Свой срок</option>
				                        </select>
				                        <input type="number" name="customDays" placeholder="дней" class="form-control form-control-sm d-inline-block w-auto" style="display:none;">
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
    <jsp:include page="/WEB-INF/jspf/footer.jsp" />
</body>
</html>