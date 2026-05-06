package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);

        // Страницы, доступные всем (неавторизованным и авторизованным)
        boolean isOpenPage = uri.endsWith("index.jsp") || uri.equals(request.getContextPath() + "/")
                || uri.endsWith("login.jsp") || uri.endsWith("/login")
                || uri.endsWith("register.jsp") || uri.endsWith("/register")
                || uri.contains("/css/") || uri.contains("/images/");

        // Страницы, требующие авторизации (но не роли)
        boolean isProtected = uri.contains("/books") || uri.contains("/authors")
                || uri.contains("/readers") || uri.contains("/deliveries");

        if (isOpenPage) {
            // Открытые страницы — пропускаем всех
            chain.doFilter(request, response);
        } else if (isProtected) {
            // Защищённые страницы — только для залогиненных
            if (session != null && session.getAttribute("userId") != null) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
        } else {
            // Любые другие запросы (например, на сервлеты действий) — тоже требуют логина
            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {}
    public void destroy() {}
}