<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    HttpSession ses = request.getSession(false);
    String role = (ses != null) ? (String) ses.getAttribute("role") : "";
    boolean isAdmin = "ADMIN".equals(role);
%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">📚 Библиотека</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <!-- Общий пункт для всех -->
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/books">📖 Книги</a></li>
                
                <% if (isAdmin) { %>
                    <!-- Пункты только для администратора -->
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/authors">✍️ Авторы</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/users">👥 Пользователи</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/deliveries">📅 Выдача</a></li>
                <% } else { %>
                    <!-- Пункты только для обычного пользователя -->
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/deliveries?action=my">📖 Мои книги</a></li>
                <% } %>
                
                <!-- Общая кнопка выхода -->
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">🚪 Выход</a></li>
            </ul>
        </div>
    </div>
</nav>