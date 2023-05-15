package com.codeup.adlister.dao;

import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.Category;
import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class MySQLAdsDao extends BaseDao implements Ads {
    public MySQLAdsDao(Config config) {
        super(config);
    }

    @Override
    public List<Ad> all() {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM ads");
            ResultSet rs = stmt.executeQuery();
            return createAdsFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all ads.", e);
        }
    }

    @Override
    public Long insert(Ad ad) {
        try {
            String insertQuery = "INSERT INTO ads(user_id, title, description) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, ad.getUserId());
            stmt.setString(2, ad.getTitle());
            stmt.setString(3, ad.getDescription());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            long adId = rs.getLong(1);

            // Associate categories with the newly created ad
            for (Category category : ad.getCategories()) {
                String associateCategoryQuery = "INSERT INTO ad_categories(ad_id, category_id) VALUES (?, ?)";
                PreparedStatement associateStmt = connection.prepareStatement(associateCategoryQuery);
                associateStmt.setLong(1, adId);
                associateStmt.setLong(2, category.getId());
                associateStmt.executeUpdate();
            }

            return adId;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating a new ad.", e);
        }
    }

    private Ad extractAd(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        long userId = rs.getLong("user_id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        List<Category> categories = getCategoriesForAd(id);
        return new Ad(id, userId, title, description, categories);
    }
    private List<Category> getCategoriesForAd(long adId) {
        String query = "SELECT c.id, c.name FROM categories c JOIN ad_categories ac ON c.id = ac.category_id WHERE ac.ad_id = ?";
        List<Category> categories = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, adId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                long categoryId = rs.getLong("id");
                String categoryName = rs.getString("name");
                categories.add(new Category(categoryId, categoryName));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving categories for ad.", e);
        }

        return categories;
    }


    private List<Ad> createAdsFromResults(ResultSet rs) throws SQLException {
        List<Ad> ads = new ArrayList<>();
        while (rs.next()) {
            ads.add(extractAd(rs));
        }
        return ads;
    }

    @Override
    public Ad findById(long id) {
        String query = "SELECT * FROM ads WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractAd(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding ad by id.", e);
        }
        return null;
    }
    @Override
    public List<Ad> search(String searchQuery) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ads WHERE title LIKE ? OR description LIKE ?");
            String likeQuery = "%" + searchQuery + "%";
            stmt.setString(1, likeQuery);
            stmt.setString(2, likeQuery);
            ResultSet rs = stmt.executeQuery();
            return createAdsFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error searching for ads.", e);
        }
    }
    @Override
    public List<Ad> getUserAds(long userId) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM ads WHERE user_id = ?");
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            return createAdsFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user ads.", e);
        }
    }
    @Override
    public void update(Ad ad) {
        try {
            String updateQuery = "UPDATE ads SET user_id=?, title=?, description=? WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(updateQuery);
            stmt.setLong(1, ad.getUserId());
            stmt.setString(2, ad.getTitle());
            stmt.setString(3, ad.getDescription());
            stmt.setLong(4, ad.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating the ad.", e);
        }
    }

    @Override
    public void delete(long id) {
        try {
            String deleteQuery = "DELETE FROM ads WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting the ad.", e);
        }
    }
    @Override
    public List<Ad> getByCategory(Category category) {
        String query = "SELECT ads.* FROM ads " +
                "JOIN ad_categories ON ads.id = ad_categories.ad_id " +
                "WHERE ad_categories.category_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, category.getId());
            ResultSet rs = stmt.executeQuery();
            return createAdsFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving ads by category", e);
        }
    }
}
