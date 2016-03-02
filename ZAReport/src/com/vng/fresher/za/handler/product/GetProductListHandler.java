/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler.product;

import com.vng.fresher.za.handler.BaseHandler;
import com.vng.fresher.za.model.Product;
import com.vng.fresher.za.model.RoleUserApp;
import com.vng.fresher.za.model.User;
import com.vng.fresher.za.util.Helper;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author quanvd
 */
public class GetProductListHandler extends BaseHandler{

    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        if(isLogined(req)) {
            List<Product> products;
            List<RoleUserApp> roles = null;
            if(isLoginedAsAmin(req)) {
                products = productService.getAllProduct();
            } else {
                HttpSession session = req.getSession();
                User user = (User)session.getAttribute(USER);
                int userId = user.getId();
                products = productService.getAllProductByUser(userId);
                roles = productService.getRoleOnAppByUserId(userId);
            }
            JSONArray jsonProducts = new JSONArray();
            for (Product product : products) {
                JSONObject json = product.toJSON();
                String code = Helper.genEmbedCode(product.getId());
                json.put("embedCode", code);
                
                if(roles == null) {
                    json.put("role", "admin");
                } else {
                    for(RoleUserApp role : roles) {
                        if(product.getId() == role.getAppId()) {
                            json.put("role", role.getRole());
                        }
                    }
                }
                jsonProducts.add(json);
            }
            setHeaderJson(resp);
            resp.getWriter().write(jsonProducts.toJSONString());
        } else {
            resp.sendRedirect("/login");
        }
    }
}
