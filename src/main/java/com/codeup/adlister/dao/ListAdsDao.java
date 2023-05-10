package com.codeup.adlister.dao;

import com.codeup.adlister.models.Ad;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ListAdsDao implements Ads {
    private List<Ad> ads;

    public List<Ad> all() {
        if (ads == null) {
            ads = generateAds();
        }
        return ads;
    }

    @Override
    public Ad findById(long id) {
        if (ads == null) {
            ads = generateAds();
        }
        return ads.stream()
                .filter(ad -> ad.getId() == id)
                .findFirst()
                .orElse(null);
    }
    @Override
    public List<Ad> search(String searchQuery) {
        if (ads == null) {
            ads = generateAds();
        }
        String query = searchQuery.toLowerCase();
        return ads.stream()
                .filter(ad -> ad.getTitle().toLowerCase().contains(query) || ad.getDescription().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }
    @Override
    public List<Ad> getUserAds(long userId) {
        if (ads == null) {
            ads = generateAds();
        }
        return ads.stream()
                .filter(ad -> ad.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public Long insert(Ad ad) {
        // make sure we have ads
        if (ads == null) {
            ads = generateAds();
        }
        // we'll assign an "id" here based on the size of the ads list
        // really the dao would handle this
        ad.setId((long) ads.size());
        ads.add(ad);
        return ad.getId();
    }

    private List<Ad> generateAds() {
        List<Ad> ads = new ArrayList<>();
        ads.add(new Ad(
            1,
            1,
            "playstation for sale",
            "This is a slightly used playstation"
        ));
        ads.add(new Ad(
            2,
            1,
            "Super Nintendo",
            "Get your game on with this old-school classic!"
        ));
        ads.add(new Ad(
            3,
            2,
            "Junior Java Developer Position",
            "Minimum 7 years of experience required. You will be working in the scripting language for Java, JavaScript"
        ));
        ads.add(new Ad(
            4,
            2,
            "JavaScript Developer needed",
            "Must have strong Java skills"
        ));
        return ads;
    }
}
