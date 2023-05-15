package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.CategoriesDao;
import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.Category;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "controllers.AdsIndexServlet", urlPatterns = "/ads")
public class AdsIndexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("ads", DaoFactory.getAdsDao().all());

        CategoriesDao categoriesDao = DaoFactory.getCategoriesDao();
        List<Category> allCategories = categoriesDao.getAllCategories();
        request.setAttribute("allCategories", allCategories);

        request.getRequestDispatcher("/WEB-INF/ads/index.jsp").forward(request, response);
    }
}
