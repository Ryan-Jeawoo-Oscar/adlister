package com.codeup.adlister.dao;

import com.codeup.adlister.models.Category;

import java.util.List;

public interface CategoriesDao {
    List<Category> getAllCategories();
    Category findByName(String name);
    Category findById(long id);
    long insert(Category category);
    void insertCategoriesForAd(long adId, List<Category> categories);
    void updateCategoriesForAd(long adId, List<Category> categories);

}

