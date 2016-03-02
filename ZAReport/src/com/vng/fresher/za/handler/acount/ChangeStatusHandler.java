/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler.acount;

import com.vng.fresher.za.handler.BaseHandler;
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
public class ChangeStatusHandler extends BaseHandler {

    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        JSONObject json = new JSONObject();
        
        if(isLoginedAsAmin(req)) {
            String username = req.getParameter("username");
            String status = req.getParameter("status");
            boolean isChanged = userService.changeStatus(username, status);
            json.put("success", isChanged);
        } else {
            json.put("error", "Please login as Administrator.");
        }
        
        setHeaderJson(resp);
        resp.getWriter().write(json.toJSONString());
    }
    
}
