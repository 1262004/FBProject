/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.service;

import com.vng.fresher.za.model.Browser;
import com.vng.fresher.za.model.Device;
import com.vng.fresher.za.model.Language;
import com.vng.fresher.za.model.Location;
import com.vng.fresher.za.model.OS;
import com.vng.fresher.za.model.PageOverview;
import com.vng.fresher.za.model.Referal;
import com.vng.fresher.za.model.AppOverview;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author quanvd
 */
public interface TrackingService{
    
    public List<AppOverview> getSiteOverview(int appId, Timestamp from, Timestamp to);
    public List<PageOverview> getPageOverview(int appId, Timestamp from, Timestamp to, String type);
    public List<Browser> getBrowser(int appId, Timestamp from, Timestamp to);
    public List<Device> getDevice(int appId, Timestamp from, Timestamp to);
    public List<OS> getOS(int appId, Timestamp from, Timestamp to);
    public List<Location> getLocation(int appId, Timestamp from, Timestamp to);
    public List<Language> getLanguage(int appId, Timestamp from, Timestamp to);
    public List<Referal> getReferal(int appId, Timestamp from, Timestamp to);
}
