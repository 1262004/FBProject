/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.model;

import java.io.Serializable;
import java.sql.Timestamp;
import org.json.simple.JSONObject;

/**
 *
 * @author quanvd
 */
public class AppOverview extends BaseModel{
    
    private int appId;
    private Timestamp date;
    private int sessions;
    private int pageviews;
    private int uniquePageviews;
    private int newVisitors;
    private int returnVisitors;
    private int bounces;
    private int entrances;
    private long sessionDurations;

    public AppOverview() {
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("appId", appId);
        json.put("date", date.getTime());
        json.put("sessions", sessions);
        json.put("pageviews", pageviews);
        json.put("uniquePageviews", uniquePageviews);
        json.put("newVisitors", newVisitors);
        json.put("returnVisitors", returnVisitors);
        json.put("bounces",bounces);
        json.put("entrances", entrances);
        json.put("sessionDuration", sessionDurations);
        json.put("pagesPerSession", (float)pageviews / sessions);
        json.put("avgSessionDuration", (float)sessionDurations / sessions);
        json.put("bounceRate", (float)bounces * 100 / entrances);
        json.put("percentNewSession", (float)newVisitors * 100 / sessions);
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

    public int getSessions() {
        return sessions;
    }

    public void setSessions(int sessions) {
        this.sessions = sessions;
    }

    public int getPageviews() {
        return pageviews;
    }

    public void setPageviews(int pageview) {
        this.pageviews = pageview;
    }

    public int getUniquePageviews() {
        return uniquePageviews;
    }

    public void setUniquePageviews(int uniquePageviews) {
        this.uniquePageviews = uniquePageviews;
    }

    public int getNewVisitors() {
        return newVisitors;
    }

    public void setNewVisitors(int newVisitors) {
        this.newVisitors = newVisitors;
    }

    public int getReturnVisitors() {
        return returnVisitors;
    }

    public void setReturnVisitors(int returnVisitors) {
        this.returnVisitors = returnVisitors;
    }

    public int getBounces() {
        return bounces;
    }

    public void setBounces(int bounces) {
        this.bounces = bounces;
    }

    public int getEntrances() {
        return entrances;
    }

    public void setEntrances(int entrances) {
        this.entrances = entrances;
    }

    public long getSessionDurations() {
        return sessionDurations;
    }

    public void setSessionDurations(long sessionDurations) {
        this.sessionDurations = sessionDurations;
    }
    
}
