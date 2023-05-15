package com.codeup.adlister.dao;

import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.Category;

import java.util.List;

public interface Ads {
    // get a list of all the ads
    List<Ad> all();
    // insert a new ad and return the new ad's id
    Long insert(Ad ad);

    Ad findById(long id);

    List<Ad> search(String searchQuery);

    List<Ad> getUserAds(long userId);
    void update(Ad ad);
    void delete(long id);
    List<Ad> getByCategory(Category category);
}
