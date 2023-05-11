package com.codeup.adlister.controllers;
import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name = "controllers.UpdateAdServlet", urlPatterns = "/ads/update")
public class UpdateAdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in
        User loggedInUser = (User) request.getSession().getAttribute("user");
        if (loggedInUser == null) {
            response.sendRedirect("/login");
            return;
        }
        long id = Long.parseLong(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        long userId = ((User) request.getSession().getAttribute("user")).getId();

        Ad updatedAd = new Ad(id, userId, title, description);
        DaoFactory.getAdsDao().update(updatedAd);

        response.sendRedirect("/ads");
    }
}

