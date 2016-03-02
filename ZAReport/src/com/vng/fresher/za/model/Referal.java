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
public class Referal extends BaseModel{
    
    private int appId;
    private Timestamp date;
    private String urlRef;
    private int refType;
    private int sessions;

    public Referal() {
    }

    public Referal(int appId, Timestamp date, String urlRef, int refType, int sessions) {
        this.appId = appId;
        this.date = date;
        this.urlRef = urlRef;
        this.refType = refType;
        this.sessions = sessions;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("appId", appId);
        json.put("date", date.getTime());
        json.put("urlRef", urlRef);
        json.put("refType", refType);
        json.put("sessions", getSessions());
        return json;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getUrlRef() {
        return urlRef;
    }

    public void setUrlRef(String urlRef) {
        this.urlRef = urlRef;
    }

    public int getRefType() {
        return refType;
    }

    public void setRefType(int refType) {
        this.refType = refType;
    }

    public int getSessions() {
        return sessions;
    }

    public void setSessions(int sessions) {
        this.sessions = sessions;
    }
    
}
