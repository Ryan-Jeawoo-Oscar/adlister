package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;
import com.codeup.adlister.util.Password;
import com.codeup.adlister.util.Validator;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "controllers.RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("confirm_password");

        List<String> errorMessages = new ArrayList<>();

        if(!Validator.isValidUsername(username)) {
            errorMessages.add("Invalid username, the username must between 2 to 15 character");
        }
        if(!Validator.isValidEmail(email)) {
            errorMessages.add("Invalid email, the email must has a @");
        }
        if(!Validator.isValidPassword(password)) {
            errorMessages.add("Invalid password, the password must between 4 to 10 character");
        }
        if((! password.equals(passwordConfirmation))) {
            errorMessages.add("Passwords don't match");
        }

        // validate input
        if (!Validator.isValidUsername(username) ||
                !Validator.isValidEmail(email) ||
                !Validator.isValidPassword(password) ||
                !password.equals(passwordConfirmation)) {
            String errorMessage = String.join("\\n", errorMessages);
            response.sendRedirect("/register?error=" + errorMessage);
            return;
        }

        // create and save a new user
        User user = new User(username, email, password);

        // hash the password

        String hash = Password.hash(user.getPassword());

        user.setPassword(hash);

        DaoFactory.getUsersDao().insert(user);
        response.sendRedirect("/login");
    }
}
