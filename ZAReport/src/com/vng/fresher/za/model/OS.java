/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.model;

import org.json.simple.JSONObject;

/**
 *
 * @author quanvd
 */
public class OS extends BaseModel{
    
    private int appId;
    private String osName;
    private int sessions;

    public OS() {
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("appId", appId);
        json.put("osName", osName);
        json.put("sessions", sessions);
        return json;
    }
    
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getosName() {
        return osName;
    }

    public void setosName(String osName) {
        this.osName = osName;
    }

    public int getSessions() {
        return sessions;
    }

    public void setSessions(int sessions) {
        this.sessions = sessions;
    }
}
