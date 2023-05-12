package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.User;
import com.codeup.adlister.util.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "controllers.CreateAdServlet", urlPatterns = "/ads/create")
public class CreateAdServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/ads/create.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        User loggedInUser = (User) request.getSession().getAttribute("user");
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        List<String> errorMessages = new ArrayList<>();

        if(!Validator.isValidTitle(title)) {
            errorMessages.add("Invalid title, the Ad Title must between 2 to 30 characters");
        }
        if(!Validator.isValidDescription(description)) {
            errorMessages.add("Invalid title, the description must between 5 to 500 characters");
        }
        if (!errorMessages.isEmpty()) {
            String errorMessage = String.join("\\n", errorMessages);
            request.setAttribute("title", title); // Set the title as an attribute
            request.setAttribute("description", description); // Set the description as an attribute
            request.setAttribute("error", errorMessage); // Set the error message as an attribute
            request.getRequestDispatcher("/WEB-INF/ads/create.jsp").forward(request, response);
            return;
        }
        Ad ad = new Ad(
                loggedInUser.getId(),
                title,
                description
        );

        DaoFactory.getAdsDao().insert(ad);
        response.sendRedirect("/ads");
    }
}
