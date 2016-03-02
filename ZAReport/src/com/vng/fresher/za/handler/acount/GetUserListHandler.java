/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler.acount;

import com.vng.fresher.za.handler.BaseHandler;
import com.vng.fresher.za.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

/**
 *
 * @author quanvd
 */
public class GetUserListHandler extends BaseHandler {

    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        if(isLoginedAsAmin(req)) {
            List<User> users = userService.getUserList();
            JSONArray jonUsers = new JSONArray();
            for (User user : users) {
                jonUsers.add(user.toJSON());
            }
            setHeaderJson(resp);
            resp.getWriter().write(jonUsers.toJSONString());
        } else {
           resp.sendRedirect("/login");
       }
    }
           
}
