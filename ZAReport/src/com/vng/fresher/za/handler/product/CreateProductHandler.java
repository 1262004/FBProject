/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler.product;

import com.vng.fresher.za.handler.BaseHandler;
import com.vng.fresher.za.model.Product;
import com.vng.fresher.za.model.User;
import com.vng.fresher.za.util.Helper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 * @author quanvd
 */
public class CreateProductHandler extends BaseHandler {

    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (isLogined(req)) {
            JSONObject json;
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(USER);
            String name = req.getParameter("name");
            String url = req.getParameter("url");
            Product product = productService.create(user.getId(), name, url, user.getUsername());
            if (product != null) {
                json = product.toJSON();
                String code = Helper.genEmbedCode(product.getId());
                json.put("embedCode", code);
                json.put("role", "admin");
            } else {
                json = new JSONObject();
                json.put("error", "Create new product fail.");
            }
            setHeaderJson(resp);
            resp.getWriter().write(json.toJSONString());
        } else {
            resp.sendRedirect("/login");
        }

    }
}
