/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.service.ipml;

import com.vng.fresher.za.Memcached;
import com.vng.fresher.za.dao.TrackingDao;
import com.vng.fresher.za.dao.impl.jdbc.TrackingJdbcDao;
import com.vng.fresher.za.model.Browser;
import com.vng.fresher.za.model.Device;
import com.vng.fresher.za.model.Language;
import com.vng.fresher.za.model.Location;
import com.vng.fresher.za.model.OS;
import com.vng.fresher.za.model.PageOverview;
import com.vng.fresher.za.model.Referal;
import com.vng.fresher.za.model.AppOverview;
import com.vng.fresher.za.service.TrackingService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author baonh2
 */
public class TrackingServiceImpl implements TrackingService {

    private static final TrackingDao trackingDao = new TrackingJdbcDao();

    @Override
    public List<AppOverview> getSiteOverview(int appId, Timestamp from, Timestamp to) {

        String memKey = String.format("TRACK_SITE_OVERVIEW_%s_%s_%s", appId, from.getTime(), to.getTime());
        List<AppOverview> data = (List<AppOverview>) Memcached.mcc.get(memKey);
       
        if (data == null) {
        //if (true) {
            data = trackingDao.selectSiteOverview(appId, from, to);
            if (data.size() > 0) {
                Memcached.mcc.add(memKey, data);
            }
        }
        return data;
    }

    @Override
    public List<PageOverview> getPageOverview(int appId, Timestamp from, Timestamp to, String type) {

        String memKey = String.format("TRACK_PAGE_OCVERVIEW_%s_%s_%s_%s", appId, from.getTime(), to.getTime(), type);
        List<PageOverview> data = (List<PageOverview>) Memcached.mcc.get(memKey);
        if (data == null) {
            if (type != null && type.equals("path")) {
                data = trackingDao.selectPageOverviewByPath(appId, from, to);
            } else {
                List<PageOverview> rawData = trackingDao.selectPageOverviewByDate(appId, from, to);
                data = new ArrayList<>();
                int size = rawData.size();
                for (int i = 0; i < size - 1; i++) {
                    PageOverview obj1 = rawData.get(i);
                    long t1 = obj1.getDate().getTime() / 86400000;
                    for (int j = i + 1; j < size; j++) {
                        PageOverview obj2 = rawData.get(j);
                        long t2 = obj2.getDate().getTime() / 86400000;
                        if (t1 == t2) {
                            int pageviews = obj1.getPageviews() + obj2.getPageviews();
                            int uniquePageviews = obj1.getUniquePageviews() + obj2.getUniquePageviews();
                            int bounces = obj1.getBounces() + obj2.getBounces();
                            int entrances = obj1.getEntrances() + obj2.getEntrances();
                            int exits = obj1.getExits() + obj2.getExits();
                            long timeOnPage = obj1.getTimeOnPage() + obj2.getTimeOnPage();

                            obj1.setPageviews(pageviews);
                            obj1.setUniquePageviews(uniquePageviews);
                            obj1.setBounces(bounces);
                            obj1.setEntrances(entrances);
                            obj1.setExits(exits);
                            obj1.setTimeOnPage(timeOnPage);

                            rawData.remove(j);
                            j--;
                            size--;
                        }
                    }
                    data.add(obj1);
                }
            }
            if (data.size() > 0) {
                Memcached.mcc.add(memKey, data);
            }
        }
        return data;
    }

    @Override
    public List<Browser> getBrowser(int appId, Timestamp from, Timestamp to) {

        String memKey = String.format("TRACK_BROWSER_%s_%s_%s", appId, from.getTime(), to.getTime());
        List<Browser> data = (List<Browser>) Memcached.mcc.get(memKey);
        if (data == null) {
            data = trackingDao.selectBrowser(appId, from, to);
            if (data.size() > 0) {
                Memcached.mcc.add(memKey, data);
            }
        }
        return data;
    }

    @Override
    public List<Device> getDevice(int appId, Timestamp from, Timestamp to) {
        
        String memKey = String.format("TRACK_DEVICE_%s_%s_%s", appId, from.getTime(), to.getTime());
        List<Device> data = (List<Device>) Memcached.mcc.get(memKey);
        if (data == null) {
            data = trackingDao.selectDevice(appId, from, to);
            if (data.size() > 0) {
                Memcached.mcc.add(memKey, data);
            }
        }
        return data;
    }

    @Override
    public List<OS> getOS(int appId, Timestamp from, Timestamp to) {
        
        String memKey = String.format("TRACK_OS_%s_%s_%s", appId, from.getTime(), to.getTime());
        List<OS> data = (List<OS>) Memcached.mcc.get(memKey);
        if (data == null) {
            data = trackingDao.selectOS(appId, from, to);
            if (data.size() > 0) {
                Memcached.mcc.add(memKey, data);
            }
        }
        return data;
    }

    @Override
    public List<Location> getLocation(int appId, Timestamp from, Timestamp to) {
        
        String memKey = String.format("TRACK_LOCATION_%s_%s_%s", appId, from.getTime(), to.getTime());
        List<Location> data = (List<Location>) Memcached.mcc.get(memKey);
        if (data == null) {
            data = trackingDao.selectLocation(appId, from, to);
            if (data.size() > 0) {
                Memcached.mcc.add(memKey, data);
            }
        }
        return data;
    }

    @Override
    public List<Language> getLanguage(int appId, Timestamp from, Timestamp to) {
        
        String memKey = String.format("TRACK_LANGUAGE_%s_%s_%s", appId, from.getTime(), to.getTime());
        List<Language> data = (List<Language>) Memcached.mcc.get(memKey);
        if (data == null) {
            data = trackingDao.selectLanguage(appId, from, to);
            if (data.size() > 0) {
                Memcached.mcc.add(memKey, data);
            }
        }
        return data;
    }

    @Override
    public List<Referal> getReferal(int appId, Timestamp from, Timestamp to) {
        
        String memKey = String.format("TRACK_REFERAL_%s_%s_%s", appId, from.getTime(), to.getTime());
        List<Referal> data = (List<Referal>) Memcached.mcc.get(memKey);
        if (data == null) {
            data = trackingDao.selectReferal(appId, from, to);
            if (data.size() > 0) {
                Memcached.mcc.add(memKey, data);
            }
        }
        return data;
    }

}
