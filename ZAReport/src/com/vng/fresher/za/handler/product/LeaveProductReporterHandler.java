/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler.product;

import com.vng.fresher.za.handler.BaseHandler;
import com.vng.fresher.za.util.Helper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author quanvd
 */
public class LeaveProductReporterHandler extends BaseHandler{

    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        if(isLogined(req)){
            int appId = Helper.parseInt(req.getParameter("appId"));
            int userId = getLoginedUserId(req);
            boolean isSuccess = productService.leaveProductReporter(userId, appId);
            
            JSONObject json = new JSONObject();
            json.put("isSuccess", isSuccess);
            setHeaderJson(resp);
            resp.getWriter().write(json.toJSONString());
        } else {
            resp.sendRedirect("/login");
        }
    }
    
    
}
