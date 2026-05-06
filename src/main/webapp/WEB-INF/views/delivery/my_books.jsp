<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Мои книги</title>
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
        <h2>📖 Мои книги</h2>
        <table class="table table-bordered">
		    <thead>
		        <tr>
		            <th>Книга</th>
		            <th>Дата выдачи</th>
		            <th>Срок возврата</th>
		            <th>Действия</th>
		        </tr>
		    </thead>
		    <tbody>
		        <c:forEach var="d" items="${myDeliveries}">
		            <tr>
		                <td>${d.book.title}</td>
		                <td>${d.dateInput}</td>
		                <td>${d.dateOutput}</td>
		                <td>
		                    <a href="deliveries?action=return&id=${d.id}" 
		                       class="btn btn-sm btn-success" 
		                       onclick="return confirm('Вернуть книгу?')">↩️ Вернуть</a>
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