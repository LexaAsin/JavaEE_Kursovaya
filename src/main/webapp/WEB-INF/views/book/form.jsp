<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:if test="${empty book.id}">Добавление</c:if><c:if test="${not empty book.id}">Редактирование</c:if> книги</title>
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
        <h2><c:if test="${empty book.id}">➕ Новая книга</c:if><c:if test="${not empty book.id}">✏️ Редактирование книги</c:if></h2>
        <form method="post" id="bookForm">
            <c:if test="${not empty book.id}"><input type="hidden" name="id" value="${book.id}"></c:if>
            <div class="mb-3">
                <label class="form-label">Название</label>
                <input type="text" name="title" value="${book.title}" class="form-control" required>
            </div>
            <div class="mb-3">
			    <label class="form-label">Автор</label>
			    <select name="authorId" id="authorSelect" class="form-control" required>
			        <option value="" disabled selected style="color: gray;">Выберите автора или добавьте нового</option>
			        <c:forEach var="author" items="${authors}">
			            <option value="${author.id}" ${book.author.id == author.id ? 'selected' : ''}>${author.fullName}</option>
			        </c:forEach>
			        <option value="new" style="font-weight: bold; color: #0d6efd;">➕ Добавить нового...</option>
			    </select>
			</div>
            <div class="mb-3">
                <label class="form-label">Жанр</label>
                <input type="text" name="genre" value="${book.genre}" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Год издания</label>
                <input type="number" name="year" value="${book.year}" class="form-control">
            </div>
            <button type="submit" class="btn btn-success">💾 Сохранить</button>
            <a href="books?action=list" class="btn btn-secondary">❌ Отмена</a>
        </form>
    </div>
    <footer><p>© 2026 Библиотека на JavaEE. Все права защищены.</p></footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Модальное окно для создания нового автора -->
    <div class="modal fade" id="newAuthorModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Новый автор</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">ФИО</label>
                        <input type="text" id="newAuthorFullName" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Телефон</label>
                        <input type="text" id="newAuthorPhone" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" id="newAuthorEmail" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Рейтинг</label>
                        <input type="number" step="0.1" id="newAuthorRating" class="form-control">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                    <button type="button" class="btn btn-primary" id="saveAuthorBtn">Сохранить</button>
                </div>
            </div>
        </div>
    </div>

    <script>
	    // Запоминаем последний нормальный выбор
	    let lastAuthorValue = '';
	    const authorSelect = document.getElementById('authorSelect');
	    
	    authorSelect.addEventListener('change', function() {
	        if (this.value === 'new') {
	            // Открываем модальное окно
	            var modal = new bootstrap.Modal(document.getElementById('newAuthorModal'));
	            modal.show();
	            // Возвращаем предыдущее значение
	            this.value = lastAuthorValue;
	        } else {
	            lastAuthorValue = this.value;
	        }
	    });
	    
	    // Сохраняем начальное значение после загрузки страницы
	    window.addEventListener('DOMContentLoaded', function() {
	        lastAuthorValue = authorSelect.value;
	    });
	    
	    // Обработчик сохранения автора (как был, только добавляем выбор нового автора)
	    document.getElementById('saveAuthorBtn').onclick = function() {
	        const fullName = document.getElementById('newAuthorFullName').value;
	        const phone = document.getElementById('newAuthorPhone').value;
	        const email = document.getElementById('newAuthorEmail').value;
	        const rating = document.getElementById('newAuthorRating').value;
	        
	        if (!fullName) {
	            alert('Введите ФИО автора');
	            return;
	        }
	        
	        fetch('${pageContext.request.contextPath}/books?action=createAuthor', {
	            method: 'POST',
	            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	            body: 'fullName=' + encodeURIComponent(fullName) +
	                  '&phone=' + encodeURIComponent(phone) +
	                  '&email=' + encodeURIComponent(email) +
	                  '&rating=' + encodeURIComponent(rating)
	        })
	        .then(res => res.json())
	        .then(data => {
	            let select = document.getElementById('authorSelect');
	            // Добавляем нового автора в список (перед пунктом "➕ Добавить нового...")
	            let option = new Option(data.fullName, data.id);
	            // Вставляем перед последним пунктом
	            let lastOption = select.options[select.options.length - 1];
	            select.add(option, lastOption);
	            // Выбираем нового автора
	            select.value = data.id;
	            lastAuthorValue = data.id;
	            // Закрываем модальное окно
	            var modal = bootstrap.Modal.getInstance(document.getElementById('newAuthorModal'));
	            modal.hide();
	            // Очищаем форму
	            document.getElementById('newAuthorFullName').value = '';
	            document.getElementById('newAuthorPhone').value = '';
	            document.getElementById('newAuthorEmail').value = '';
	            document.getElementById('newAuthorRating').value = '';
	        })
	        .catch(err => alert('Ошибка: ' + err));
	    }
	</script>
</body>
</html>