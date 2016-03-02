/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler.acount;

import com.vng.fresher.za.handler.BaseHandler;
import com.vng.fresher.za.model.User;
import com.vng.fresher.za.util.Helper;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author quanvd
 */
public class UpdateUserHandler extends BaseHandler {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        if(isLogined(req)) {
            String password = req.getParameter("password");
            String role = req.getParameter("role");
            String status = req.getParameter("status");
            Timestamp registeredAt = Helper.parseDate(req.getParameter("registeredAt"));
            
            if(isLoginedAsAmin(req)) {
                int userId = Helper.parseInt(req.getParameter("userId"));
            }
        } else {
            resp.sendRedirect("/login");
        }
    }
    
}
