<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>Библиотека</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);
        }
        .hero {
            background: url('https://source.unsplash.com/random/1600x600/?library,books') no-repeat center center;
            background-size: cover;
            color: white;
            padding: 100px 0;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.5);
            border-radius: 0 0 20px 20px;
        }
        .card {
            transition: transform 0.3s, box-shadow 0.3s;
            cursor: pointer;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.2);
        }
        .card-icon {
            font-size: 3rem;
            text-align: center;
            margin-bottom: 1rem;
        }
        footer {
            background-color: #343a40;
            color: white;
            padding: 20px 0;
            margin-top: 50px;
            text-align: center;
        }
        .fun-text {
            font-size: 1.2rem;
            color: #ffc107;
            text-align: center;
            margin-top: 15px;
            font-style: italic;
        }
        .justify-content-center {
            justify-content: center;
        }
        .fun-text-blue {
		    font-size: 1.3rem;
		    font-weight: bold;
		    color: #0d6efd;   /* яркий синий (Bootstrap primary) */
		    text-align: center;
		    margin-top: 15px;
		}
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/jspf/header.jsp" />

    <div class="hero text-center" style="color: #2c3e50; text-shadow: none;">
        <% if (!isAdmin) { %>
        	<h1>Добро пожаловать в библиотеку!</h1>
        <% } %>
        <% if (isAdmin) { %>
        	<h1>Добро пожаловать!</h1>
        <% } %>
        <% if (isAdmin) { %>
        	<div class="fun-text-blue">
        		<p class="lead">Управление книгами, авторами, пользователями и выдачей</p>
        	</div>
        <% } %>
        
        <% if (!isAdmin) { %>
            <div class="fun-text-blue">
			    📢 Здесь могла быть реклама вашей книжки!👀
			</div>
        <% } %>
    </div>

    <div class="container mt-5">
        <div class="row g-4 justify-content-center">
            <% if (isAdmin) { %>
                <!-- Карточки для администратора -->
                <div class="col-md-3 d-flex">
	                <div class="card w-100 text-center p-3 d-flex flex-column">
	                    <div class="card-icon">📖</div>
	                    <h5 class="card-title">Книги</h5>
	                    <p class="card-text flex-grow-1">Просмотр, добавление, редактирование и удаление книг</p>
	                    <a href="books" class="btn btn-outline-primary mt-auto">Перейти</a>
	                </div>
	            </div>
                <div class="col-md-3 d-flex">
                    <div class="card w-100 text-center p-3 d-flex flex-column">
                        <div class="card-icon">✍️</div>
                        <h5 class="card-title">Авторы</h5>
                        <p class="card-text flex-grow-1">Управление авторами книг</p>
                        <a href="authors" class="btn btn-outline-primary mt-auto">Перейти</a>
                    </div>
                </div>
                <div class="col-md-3 d-flex">
                    <div class="card w-100 text-center p-3 d-flex flex-column">
                        <div class="card-icon">👥</div>
                        <h5 class="card-title">Пользователи</h5>
                        <p class="card-text flex-grow-1">Управление пользователями системы</p>
                        <a href="users" class="btn btn-outline-primary mt-auto">Перейти</a>
                    </div>
                </div>
                <div class="col-md-3 d-flex">
                    <div class="card w-100 text-center p-3 d-flex flex-column">
                        <div class="card-icon">📅</div>
                        <h5 class="card-title">Выдача книг</h5>
                        <p class="card-text flex-grow-1">Учёт выдачи и возврата книг</p>
                        <a href="deliveries" class="btn btn-outline-primary mt-auto">Перейти</a>
                    </div>
                </div>
            <% } else { %>
                <!-- Карточки для обычного пользователя -->
                <div class="col-md-3 d-flex">
	                <div class="card w-100 text-center p-3 d-flex flex-column">
	                    <div class="card-icon">📖</div>
	                    <h5 class="card-title">Книги</h5>
	                    <p class="card-text flex-grow-1">Просмотр, взять книгу</p>
	                    <a href="books" class="btn btn-outline-primary mt-auto">Перейти</a>
	                </div>
	            </div>
                <div class="col-md-3 d-flex">
                    <div class="card w-100 text-center p-3 d-flex flex-column">
                        <div class="card-icon">📘</div>
                        <h5 class="card-title">Мои книги</h5>
                        <p class="card-text flex-grow-1">Список книг, которые вы взяли</p>
                        <a href="deliveries?action=my" class="btn btn-outline-primary mt-auto">Перейти</a>
                    </div>
                </div>
                <div class="col-md-3 d-flex">
                    <div class="card w-100 text-center p-3 d-flex flex-column">
                        <div class="card-icon">✨</div>
                        <h5 class="card-title">Совет дня</h5>
                        <p class="card-text flex-grow-1">«Книга — это мечта, которую вы держите в руках»</p>
                        <span class="btn btn-outline-secondary mt-auto disabled">Читайте с удовольствием</span>
                    </div>
                </div>
            <% } %>
        </div>
    </div>

    <footer>
        <p>© 2026 Библиотека на JavaEE. Все права защищены.</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>