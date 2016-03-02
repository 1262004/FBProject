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
public class RoleUserApp extends BaseModel{
    
    private int userId;
    private int appId;
    private String role;
    private String salt;

    public RoleUserApp() {
    }

    public RoleUserApp(int userId, int appId, String role, String salt) {
        this.userId = userId;
        this.appId = appId;
        this.role = role;
        this.salt = salt;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("userId", userId);
        json.put("appId", appId);
        json.put("role", role);
        json.put("salt", salt);
        return json;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
