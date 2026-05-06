package controller;

import dao.AppUserDao;
import domain.AppUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private final AppUserDao userDao = new AppUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.getRequestDispatcher("/WEB-INF/views/user/form.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = Long.parseLong(req.getParameter("id"));
                AppUser user = userDao.findById(id);
                req.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/views/user/form.jsp").forward(req, resp);
                break;
            case "delete":
                userDao.delete(Long.parseLong(req.getParameter("id")));
                resp.sendRedirect("users");
                break;
            default:
                List<AppUser> users = userDao.findAll();
                req.setAttribute("users", users);
                req.getRequestDispatcher("/WEB-INF/views/user/list.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        String role = req.getParameter("role");

        if (idParam == null || idParam.isEmpty()) {
            userDao.save(username, password, firstName, lastName, phone, role);
        } else {
            Long id = Long.parseLong(idParam);
            userDao.update(id, username, password, firstName, lastName, phone, role);
        }
        resp.sendRedirect("users");
    }
}