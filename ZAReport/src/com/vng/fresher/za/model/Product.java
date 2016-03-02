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
public class Product extends BaseModel{
    
    private int id;
    private String name;
    private String url;
    private int timeout;
    private String status;
    private Timestamp registeredAt;
    private String owner;

    public Product() {
    }

    public Product(int id, String name, String url, int timeout, String status, Timestamp registeredAt, String owner) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.timeout = timeout;
        this.status = status;
        this.registeredAt = registeredAt;
        this.owner = owner;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("appId", id);
        json.put("name", name);
        json.put("url", url);
        json.put("timeout", timeout);
        json.put("status", status);
        json.put("createdAt", registeredAt.getTime());
        json.put("owner", owner);
        return json;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
