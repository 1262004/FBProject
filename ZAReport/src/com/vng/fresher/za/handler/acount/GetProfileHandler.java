/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler.acount;

import com.vng.fresher.za.handler.BaseHandler;
import com.vng.fresher.za.model.User;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author quanvd
 */
public class GetProfileHandler extends BaseHandler{

    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        if(isLogined(req)) {
            HttpSession session = req.getSession();
            User user = (User)session.getAttribute(USER);
            setHeaderJson(resp);
            resp.getWriter().write(user.toJSON().toJSONString());
        } else {
            resp.sendRedirect("/login");
        }
    }
    
}
