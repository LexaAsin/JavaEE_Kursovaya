package controller;

import dao.BookDao;
import dao.DeliveryDao;
import dao.AppUserDao;
import domain.Book;
import domain.Delivery;
import domain.AppUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/deliveries")
public class DeliveryServlet extends HttpServlet {

    private final DeliveryDao deliveryDao = new DeliveryDao();
    private final BookDao bookDao = new BookDao();
    private final AppUserDao userDao = new AppUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showForm(req, resp);
                break;
            case "edit":
                editForm(req, resp);
                break;
            case "delete":
                deleteDelivery(req, resp);
                break;
            case "my":
                myBooks(req, resp);
                break;
            case "take":
                takeBook(req, resp);
                break;
            case "return":
                returnBook(req, resp);
                break;
            default:
                listDeliveries(req, resp);
                break;
        }
    }

    private void listDeliveries(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Delivery> deliveries = deliveryDao.findAll();
        req.setAttribute("deliveries", deliveries);
        req.getRequestDispatcher("/WEB-INF/views/delivery/list.jsp").forward(req, resp);
    }

    private void myBooks(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        List<Delivery> deliveries = deliveryDao.findByUserId(userId);
        req.setAttribute("myDeliveries", deliveries);
        req.getRequestDispatcher("/WEB-INF/views/delivery/my_books.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Book> books = bookDao.findAll();
        List<AppUser> users = userDao.findAll();
        req.setAttribute("books", books);
        req.setAttribute("users", users);
        req.setAttribute("delivery", new Delivery());
        req.getRequestDispatcher("/WEB-INF/views/delivery/form.jsp").forward(req, resp);
    }

    private void editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Delivery delivery = deliveryDao.findById(id);
        List<Book> books = bookDao.findAll();
        List<AppUser> users = userDao.findAll();
        req.setAttribute("books", books);
        req.setAttribute("users", users);
        req.setAttribute("delivery", delivery);
        req.getRequestDispatcher("/WEB-INF/views/delivery/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if ("take".equals(action)) {
            takeBook(req, resp);
            return;
        }
        
        // --- Обычное сохранение / обновление (для админа) ---
        String idParam = req.getParameter("id");
        Delivery delivery = new Delivery();

        String bookIdStr = req.getParameter("bookId");
        String userIdStr = req.getParameter("userId");
        
        if (bookIdStr == null || userIdStr == null) {
            resp.sendRedirect(req.getContextPath() + "/deliveries");
            return;
        }
        
        Long bookId = Long.parseLong(bookIdStr);
        Long userId = Long.parseLong(userIdStr);
        
        Book book = bookDao.findById(bookId);
        AppUser user = userDao.findById(userId);
        
        delivery.setBook(book);
        delivery.setUser(user);
        delivery.setDateInput(LocalDate.parse(req.getParameter("dateInput")));
        
        String dateOutput = req.getParameter("dateOutput");
        if (dateOutput != null && !dateOutput.isEmpty()) {
            delivery.setDateOutput(LocalDate.parse(dateOutput));
        }
        
        if (idParam == null || idParam.isEmpty()) {
            deliveryDao.save(delivery);
        } else {
            delivery.setId(Long.parseLong(idParam));
            deliveryDao.update(delivery);
        }
        resp.sendRedirect(req.getContextPath() + "/deliveries");
    }

    private void deleteDelivery(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        deliveryDao.delete(id);
        resp.sendRedirect(req.getContextPath() + "/deliveries");
    }
    private void takeBook(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String bookIdStr = req.getParameter("bookId");
        String daysStr = req.getParameter("days");
        
        if (bookIdStr == null || daysStr == null) {
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        }
        
        Long bookId = Long.parseLong(bookIdStr);
        Long userId = (Long) req.getSession().getAttribute("userId");
        int days = Integer.parseInt(daysStr);
        
        Delivery delivery = new Delivery();
        delivery.setBook(bookDao.findById(bookId));
        delivery.setUser(userDao.findById(userId));
        delivery.setDateInput(LocalDate.now());
        delivery.setDateOutput(LocalDate.now().plusDays(days));
        
        deliveryDao.save(delivery);
        resp.sendRedirect(req.getContextPath() + "/deliveries?action=my");
    }
    private void returnBook(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long deliveryId = Long.parseLong(req.getParameter("id"));
        deliveryDao.returnBook(deliveryId);
        resp.sendRedirect(req.getContextPath() + "/deliveries?action=my");
    }
}