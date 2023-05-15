package com.codeup.adlister.controllers;
import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.dao.CategoriesDao;
import com.codeup.adlister.models.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String[] selectedCategories = request.getParameterValues("categories");
        Set<Category> updatedCategories = new HashSet<>();

        // Fetch the current ad's categories
        Ad currentAd = DaoFactory.getAdsDao().findById(id);
        List<Category> currentCategories = currentAd.getCategories();

        if (selectedCategories != null) {
            CategoriesDao categoriesDao = DaoFactory.getCategoriesDao();
            for (String categoryId : selectedCategories) {
                Category category = categoriesDao.findById(Long.parseLong(categoryId));
                if (category != null) {
                    // Check if the category is already in the ad's categories
                    if (currentCategories.contains(category)) {
                        // If it's already in the list, remove it from the current categories
                        currentCategories.remove(category);
                    } else {
                        // If it's not in the list, add it to the updated categories
                        updatedCategories.add(category);
                    }
                }
            }
        }

        // Merge the current categories (with the removed ones) and updated categories
        currentCategories.addAll(updatedCategories);

        Ad updatedAd = new Ad(id, userId, title, description, currentCategories);
        DaoFactory.getAdsDao().update(updatedAd);

        if (selectedCategories != null) {
            DaoFactory.getCategoriesDao().updateCategoriesForAd(id, currentCategories);
        }

        response.sendRedirect(request.getContextPath() + "/profile");
    }
}

