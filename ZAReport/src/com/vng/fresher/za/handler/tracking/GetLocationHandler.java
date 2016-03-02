/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler.tracking;

import com.vng.fresher.za.handler.BaseHandler;
import com.vng.fresher.za.model.Location;
import com.vng.fresher.za.util.Helper;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

/**
 *
 * @author quanvd
 */
public class GetLocationHandler extends BaseHandler{
    
    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //if(isLogined(req)) {
        if(true){
            String appId = req.getParameter("appId");
            int id = Helper.parseInt(appId);
            Timestamp from = Helper.parseDate(req.getParameter("from"));
            Timestamp to = Helper.parseDate(req.getParameter("to"));
            List<Location> datas = trackingService.getLocation(id, from, to);
            JSONArray jsons = new JSONArray();
            for(Location data : datas) {
                jsons.add(data.toJSON());
            }
            setHeaderJson(resp);
            resp.getWriter().write(jsons.toJSONString());
        } else {
            resp.sendRedirect("/login");
        }
    }
}
