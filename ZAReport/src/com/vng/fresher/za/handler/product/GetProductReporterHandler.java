/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler.product;

import com.vng.fresher.za.handler.BaseHandler;
import com.vng.fresher.za.model.RoleUserApp;
import com.vng.fresher.za.model.User;
import com.vng.fresher.za.util.Helper;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

/**
 *
 * @author quanvd
 */
public class GetProductReporterHandler extends BaseHandler{

    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        if(isLogined(req)) {
            int appId = Helper.parseInt(req.getParameter("appId"));
            int userId = getLoginedUserId(req);
            RoleUserApp role = productService.getRoleOnApp(userId, appId);
            
            JSONArray jsonUsers = new JSONArray();
            if(role != null) {
                List<User> users = userService.getReporter(appId);
                List<RoleUserApp> roles = productService.getRoleOnAppByAppId(appId);
                for(User user : users) {
                    for(RoleUserApp r : roles) {
                        if(user.getId() == r.getUserId()) {
                            user.setRole(r.getRole());
                            break;
                        }
                    }
                    jsonUsers.add(user.toJSON());
                }
            }
            setHeaderJson(resp);
            resp.getWriter().write(jsonUsers.toJSONString());
        } else {
            resp.sendRedirect("/login");
        }
    }
    
    
}
