package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.CategoriesDao;
import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.Category;
import com.codeup.adlister.models.User;
import com.codeup.adlister.models.Ad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "controllers.ViewProfileServlet", urlPatterns = "/profile")
public class ViewProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }
        User user = (User) request.getSession().getAttribute("user");
        List<Ad> userAds = DaoFactory.getAdsDao().getUserAds(user.getId());
        request.setAttribute("userAds", userAds);

        CategoriesDao categoriesDao = DaoFactory.getCategoriesDao();
        List<Category> allCategories = categoriesDao.getAllCategories();
        request.setAttribute("allCategories", allCategories);

        request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
    }
}
