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
public class AddProductReporterHandler extends BaseHandler {

    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (isLogined(req)) {
            boolean isSuccess = false;
            String username = req.getParameter("username");
            User reporter = userService.getUser(username);
            if (reporter != null) {
                int appId = Helper.parseInt(req.getParameter("appId"));
                if (isLoginedAsAmin(req)) {
                    isSuccess = productService.addProductReporter(reporter.getId(), appId);
                } else {
                    int userId = getLoginedUserId(req);
                    RoleUserApp role = productService.getRoleOnApp(userId, appId);
                    if (role.getRole().equals("admin")) {
                        isSuccess = productService.addProductReporter(reporter.getId(), appId);
                    }
                }
            }

            JSONObject json = new JSONObject();
            json.put("isSuccess", isSuccess);
            setHeaderJson(resp);
            resp.getWriter().write(json.toJSONString());

        } else {
            resp.sendRedirect("/login");
        }
    }
}
