package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.Ad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.AdDetailsServlet", urlPatterns = "/ads/details")
public class AdDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long adId = Long.parseLong(request.getParameter("id"));
        Ad ad = DaoFactory.getAdsDao().findById(adId);
        request.setAttribute("ad", ad);
        request.getRequestDispatcher("/WEB-INF/ads/details.jsp").forward(request, response);
    }
}

