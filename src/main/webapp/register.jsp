<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container" style="max-width: 500px; margin-top: 100px;">
        <div class="card shadow">
            <div class="card-header bg-success text-white">
                <h4 class="mb-0">Регистрация</h4>
            </div>
            <div class="card-body">
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger">${error}</div>
                <% } %>
                <form method="post">
                    <div class="mb-3">
                        <label>Логин</label>
                        <input type="text" name="username" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label>Пароль</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label>Имя</label>
                        <input type="text" name="firstName" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label>Фамилия</label>
                        <input type="text" name="lastName" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label>Телефон</label>
                        <input type="text" name="phone" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-success w-100">Зарегистрироваться</button>
                </form>
                <div class="mt-3 text-center">
                    <a href="login">Уже есть аккаунт? Войдите</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>