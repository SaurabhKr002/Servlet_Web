package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(
        description = "Login Servlet Testing",
        urlPatterns = { "/LoginServlet" },
        initParams = {
                @WebInitParam(name = "user", value = "Saurabh"),
                @WebInitParam(name = "password", value = "BridgeLabz@22")
        }
)
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        // Get servlet config init params
        String userID = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");
        /* TASK 1
        if (userID.equals(user) && password.equals(pwd)) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            rd.include(request, response);
        }*/
        /*TASK 2 and 3*/
        if (!user.matches("^Cap[a-zA-Z]{3,}$")) {
            sendErrorMessage(response, request, "Invalid username! Must start with 'Cap' and have at least 3 more letters.");
            return;
        }

        // Password validation: Min 8 chars, 1 uppercase, 1 number, exactly 1 special character
        if (!pwd.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$") || pwd.replaceAll("[^a-zA-Z0-9]", "").length() != pwd.length() - 1) {
            sendErrorMessage(response, request, "Invalid password! Must be at least 8 characters, have 1 uppercase, 1 number, and exactly 1 special character.");
            return;
        }

        // Check credentials
        if (userID.equals(user) && password.equals(pwd)) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
        } else {
            sendErrorMessage(response, request, "Either username or password is wrong.");
        }
    }

    private void sendErrorMessage(HttpServletResponse response, HttpServletRequest request, String message) throws IOException, ServletException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
        PrintWriter out = response.getWriter();
        out.println("<font color=red>" + message + "</font>");
        rd.include(request, response);
    }
}