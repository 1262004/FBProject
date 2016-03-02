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
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 * @author quanvd
 */
public class UpdateProductHandler extends BaseHandler {

    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (isLogined(req)) {
            int appId = Helper.parseInt(req.getParameter("appId"));
            String name = req.getParameter("name");
            int timeout = Helper.parseInt(req.getParameter("timeout"));
            String status = req.getParameter("status");
            Timestamp registeredAt = Helper.parseDate(req.getParameter("registeredAt"));

            boolean isUpdated = false;
            if (isLoginedAsAmin(req)) {
                isUpdated = productService.updateProduct(appId, name, timeout, status, registeredAt);
            } else {
                HttpSession session = req.getSession();
                User user = (User) session.getAttribute(USER);
                RoleUserApp role = productService.getRoleOnApp(user.getId(), appId);
                if (role.getRole().equals("admin")) {
                    isUpdated = productService.updateProduct(appId, name, timeout, status, registeredAt);
                }
            }

            JSONObject json = new JSONObject();
            json.put("isUpdated", isUpdated);
            setHeaderJson(resp);
            resp.getWriter().write(json.toJSONString());

        } else {
            resp.sendRedirect("/login");
        }
    }

}
