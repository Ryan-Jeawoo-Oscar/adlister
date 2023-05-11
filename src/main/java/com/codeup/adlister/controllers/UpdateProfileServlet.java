package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;
import com.codeup.adlister.util.Password;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.UpdateProfileServlet", urlPatterns = "/profile/update")
public class UpdateProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in
        User loggedInUser = (User) request.getSession().getAttribute("user");
        if (loggedInUser == null) {
            response.sendRedirect("/login");
            return;
        }

        // Get the updated information from the form
        String username = request.getParameter("username");
        String email = request.getParameter("email");

        // Create a new user object with updated information
        User updatedUser = new User(loggedInUser.getId(), username, email);

        // Update the user in the database
        DaoFactory.getUsersDao().update(updatedUser);

        // Update the user in the session
        request.getSession().setAttribute("user", updatedUser);

        // Redirect to the profile page
        response.sendRedirect("/profile");
    }
}

