package com.codeup.adlister.dao;

import com.codeup.adlister.models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLCategoriesDao extends BaseDao implements CategoriesDao {
    public MySQLCategoriesDao(Config config) {
        super(config);
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM categories");
            ResultSet rs = stmt.executeQuery();
            return createCategoriesFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all categories.", e);
        }
    }

    @Override
    public Category findByName(String name) {
        String query = "SELECT * FROM categories WHERE name = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractCategory(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding category by name.", e);
        }
        return null;
    }
    @Override
    public Category findById(long id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM categories WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractCategory(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding category by id.", e);
        }
        return null;
    }

    @Override
    public long insert(Category category) {
        try {
            String insertQuery = "INSERT INTO categories(name) VALUES (?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, category.getName());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating a new category.", e);
        }
    }
    @Override
    public void insertCategoriesForAd(long adId, List<Category> categories) {
        String insertQuery = "INSERT INTO ad_categories(ad_id, category_id) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(insertQuery);
            for (Category category : categories) {
                stmt.setLong(1, adId);
                stmt.setLong(2, category.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding categories for the ad.", e);
        }
    }
    @Override
    public void updateCategoriesForAd(long adId, List<Category> categories) {
        try {
            // Delete existing ad-category associations
            String deleteQuery = "DELETE FROM ad_categories WHERE ad_id = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
            deleteStmt.setLong(1, adId);
            deleteStmt.executeUpdate();

            // Insert new ad-category associations
            String insertQuery = "INSERT INTO ad_categories (ad_id, category_id) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);

            // Check for duplicates
            String checkDuplicateQuery = "SELECT * FROM ad_categories WHERE ad_id = ? AND category_id = ?";
            PreparedStatement checkDuplicateStmt = connection.prepareStatement(checkDuplicateQuery);

            for (Category category : categories) {
                checkDuplicateStmt.setLong(1, adId);
                checkDuplicateStmt.setLong(2, category.getId());

                ResultSet rs = checkDuplicateStmt.executeQuery();

                // If the entry doesn't already exist, insert it
                if (!rs.next()) {
                    insertStmt.setLong(1, adId);
                    insertStmt.setLong(2, category.getId());
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting new ad category.", e);
        }
    }




    private Category extractCategory(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        return new Category(id, name);
    }

    private List<Category> createCategoriesFromResults(ResultSet rs) throws SQLException {
        List<Category> categories = new ArrayList<>();
        while (rs.next()) {
            categories.add(extractCategory(rs));
        }
        return categories;
    }
}

