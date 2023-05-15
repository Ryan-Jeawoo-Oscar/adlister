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
import java.util.Arrays;
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
        Set<Category> finalCategories = new HashSet<>();

        // Fetch the current ad's categories
        Ad currentAd = DaoFactory.getAdsDao().findById(id);
        List<Category> currentCategories = currentAd.getCategories();

        if (selectedCategories != null) {
            CategoriesDao categoriesDao = DaoFactory.getCategoriesDao();
            for (String categoryId : selectedCategories) {
                Category category = categoriesDao.findById(Long.parseLong(categoryId));
                if (category != null) {
                    // If the category id is already in the current categories, remove it
                    if (currentCategories.stream().anyMatch(currentCategory -> currentCategory.getId() == category.getId())) {
                        currentCategories.removeIf(currentCategory -> currentCategory.getId() == category.getId());
                    } else {
                        // If the category id is not in the current categories, add it
                        finalCategories.add(category);
                    }
                }
            }
        }

        // Add the remaining current categories to the final categories
        finalCategories.addAll(currentCategories);

        Ad updatedAd = new Ad(id, userId, title, description, new ArrayList<>(finalCategories));
        DaoFactory.getAdsDao().update(updatedAd);

        if (selectedCategories != null) {
            DaoFactory.getCategoriesDao().updateCategoriesForAd(id, new ArrayList<>(finalCategories));
        }

        response.sendRedirect(request.getContextPath() + "/profile");
    }
}

