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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author quanvd
 */
public class DeleteProductHandler extends BaseHandler{

    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        if(isLogined(req)) {
            boolean isDeleted = false;
            int appId = Helper.parseInt(req.getParameter("appId"));
            if(isLoginedAsAmin(req)) {
                isDeleted = productService.deleteProductById(appId);
            } else {
                User user = getLoginedUser(req);
                RoleUserApp role = productService.getRoleOnApp(user.getId(), appId);
                if(role.getRole().equals("admin")) {
                    isDeleted = productService.deleteProductById(appId);
                }
            }
            
            JSONObject json = new JSONObject();
            json.put("isDeleted", isDeleted);
            setHeaderJson(resp);
            resp.getWriter().write(json.toJSONString());
            
        } else {
            resp.sendRedirect("/login");
        }
    }
    
}
