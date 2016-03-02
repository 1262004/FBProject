/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.dao;

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
 * @author baonh2
 */
public interface TrackingDao {
    
    public List<AppOverview> selectSiteOverview(int appId, Timestamp from, Timestamp to);
    public List<PageOverview> selectPageOverviewByPath(int appId, Timestamp from, Timestamp to);
    public List<PageOverview> selectPageOverviewByDate(int appId, Timestamp from, Timestamp to);
    public List<Browser> selectBrowser(int appId, Timestamp from, Timestamp to);
    public List<Device> selectDevice(int appId, Timestamp from, Timestamp to);
    public List<OS> selectOS(int appId, Timestamp from, Timestamp to);
    public List<Location> selectLocation(int appId, Timestamp from, Timestamp to);
    public List<Language> selectLanguage(int appId, Timestamp from, Timestamp to);
    public List<Referal> selectReferal(int appId, Timestamp from, Timestamp to);
}
