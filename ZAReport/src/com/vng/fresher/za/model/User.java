/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.model;

import java.sql.Timestamp;
import org.json.simple.JSONObject;

/**
 *
 * @author quanvd
 */
public class User extends BaseModel{
    
    private int id;
    private String username;
    private String password;
    private String psalt;
    private String role;
    private String rsalt;
    private String status;
    private Timestamp registeredAt;

    public User(){}

    public User(int id, String username, String password, String psalt, String role, String rsalt, String status, Timestamp registeredAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.psalt = psalt;
        this.role = role;
        this.rsalt = rsalt;
        this.status = status;
        this.registeredAt = registeredAt;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("userId", id);
        json.put("username", username);
        json.put("role", role);
        json.put("status", status);
        json.put("registeredAt", registeredAt.getTime());
        return json;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPsalt() {
        return psalt;
    }

    public void setPsalt(String psalt) {
        this.psalt = psalt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRsalt() {
        return rsalt;
    }

    public void setRsalt(String rsalt) {
        this.rsalt = rsalt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

}
