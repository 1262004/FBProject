/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler;

import com.vng.fresher.za.model.User;
import com.vng.fresher.za.service.ProductService;
import com.vng.fresher.za.service.TrackingService;
import com.vng.fresher.za.service.UserService;
import com.vng.fresher.za.service.ipml.ProductServiceImpl;
import com.vng.fresher.za.service.ipml.TrackingServiceImpl;
import com.vng.fresher.za.service.ipml.UserServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author baonh2
 */
public abstract class BaseHandler extends HttpServlet{
    
    protected final String USER = "user";
    protected final String ROLE_ADMIN = "admin";
    protected static final UserService userService = new UserServiceImpl();
    protected static final ProductService productService = new ProductServiceImpl();
    protected static final TrackingService trackingService = new TrackingServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }
    
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
    }
    
    protected void setHeaderJson(HttpServletResponse resp) {
        resp.setContentType("application/json;charset=UTF-8");
    }
    
    protected void setHeaderHtml(HttpServletResponse resp) {
        resp.setContentType("text/html;charset=UTF-8");
    }
    
    protected User getLoginedUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (User)session.getAttribute(USER);
    }
    
    protected int getLoginedUserId(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute(USER);
        return user.getId();
    }
    
    protected boolean isLogined(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute(USER);
        if(user != null) {
            return true;
        }
        return false;
    }
    
    protected boolean isLoginedAsAmin(HttpServletRequest req) {
        
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute(USER);
        if(user != null && user.getRole().equals(ROLE_ADMIN)) {
            return true;
        }
        return false;
    }
}
