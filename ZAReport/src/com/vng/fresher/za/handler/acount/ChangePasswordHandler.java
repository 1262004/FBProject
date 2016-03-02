/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler.acount;

import com.vng.fresher.za.handler.BaseHandler;
import com.vng.fresher.za.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 * @author quanvd
 */
public class ChangePasswordHandler extends BaseHandler{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isLogined(req)) {
            boolean isChanged = false;
            HttpSession session = req.getSession();
            User user = (User)session.getAttribute(USER);
            String password = req.getParameter("password");
            
            user = userService.changePassword(user, password);
            if(user != null) {
                session.setAttribute(USER, user);
                isChanged = true;
            }
            
            JSONObject json = new JSONObject();
            json.put("success", isChanged);
            
            setHeaderJson(resp);
            resp.getWriter().write(json.toJSONString());
        } else {
            resp.sendRedirect("/login");
        }
    }
    
}
