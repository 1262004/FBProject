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
public class PageOverview extends BaseModel{
    
    private int appId;
    private String path;
    private Timestamp date;
    private int pageviews;
    private int uniquePageviews;
    private int bounces;
    private int entrances;
    private int exits;
    private long timeOnPage;

    public PageOverview() {
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("appId", appId);
        if(path != null){
            json.put("page", path);
        } else {
            json.put("date", date.getTime());
        }
        json.put("pageviews", pageviews);
        json.put("uniquePageviews", uniquePageviews);
        json.put("timeOnPage", timeOnPage);
        json.put("bounces", bounces);
        json.put("entrances", entrances);
        json.put("exits", exits);
        json.put("avgTimeOnPage", (float)timeOnPage / (pageviews - exits));
        json.put("bounceRate", (float)bounces * 100 / entrances);
        json.put("exitRate", (float)exits * 100 / pageviews);
        return json;
    }
    
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getPageviews() {
        return pageviews;
    }

    public void setPageviews(int pageviews) {
        this.pageviews = pageviews;
    }

    public int getUniquePageviews() {
        return uniquePageviews;
    }

    public void setUniquePageviews(int uniquePageviews) {
        this.uniquePageviews = uniquePageviews;
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

    public int getExits() {
        return exits;
    }

    public void setExits(int exits) {
        this.exits = exits;
    }

    public long getTimeOnPage() {
        return timeOnPage;
    }

    public void setTimeOnPage(long timeOnPage) {
        this.timeOnPage = timeOnPage;
    }
}
