/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.dao.impl.jdbc;

import com.vng.fresher.za.Database;
import com.vng.fresher.za.dao.TrackingDao;
import com.vng.fresher.za.model.Browser;
import com.vng.fresher.za.model.Device;
import com.vng.fresher.za.model.Language;
import com.vng.fresher.za.model.Location;
import com.vng.fresher.za.model.OS;
import com.vng.fresher.za.model.PageOverview;
import com.vng.fresher.za.model.Referal;
import com.vng.fresher.za.model.AppOverview;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author baonh2
 */
public class TrackingJdbcDao implements TrackingDao{
    
    private final String APPID = "app_id";
    private final String DATE = "date_tracking";
    private final String SESSION = "sessions";
    private final String PAGEVIEW = "pageviews";
    private final String UNIQUE_PAGEVIEW = "unique_pageviews";
    private final String NEW_VISITOR = "new_visitors";
    private final String RETURN_VISITOR = "return_visitors";
    private final String BOUNCE ="bounces";
    private final String ENTRANCE = "entrances";
    private final String EXIT = "exits";
    private final String SESSION_DURATION = "total_session_duration";
    private final String PATH = "path";
    private final String TIME_ON_PAGE = "total_time_on_page";
    
    @Override
    public List<AppOverview> selectSiteOverview(int appId, Timestamp from, Timestamp to) {
        
        String sql = String.format("select %s, %s, %s, %s, %s, %s, %s, %s, %s, %s "
                                    + "from %s "
                                    + "where %s=? "
                                    + "and %s between ? and ?", 
                                    DATE, SESSION, PAGEVIEW, UNIQUE_PAGEVIEW, NEW_VISITOR, 
                                    RETURN_VISITOR, BOUNCE, ENTRANCE, EXIT, SESSION_DURATION,
                                    "app_overview", 
                                    APPID, 
                                    DATE);
        List<AppOverview> arrSOV = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            ps.setTimestamp(2, from);
            ps.setTimestamp(3, to);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                AppOverview sOV = new AppOverview();
                sOV.setAppId(appId);
                sOV.setDate(rs.getTimestamp(DATE));
                sOV.setSessions(rs.getInt(SESSION));
                sOV.setPageviews(rs.getInt(PAGEVIEW));
                sOV.setUniquePageviews(rs.getInt(UNIQUE_PAGEVIEW));
                sOV.setNewVisitors(rs.getInt(NEW_VISITOR));
                sOV.setReturnVisitors(rs.getInt(RETURN_VISITOR));
                sOV.setBounces(rs.getInt(BOUNCE));
                sOV.setEntrances(rs.getInt(ENTRANCE));
                sOV.setSessionDurations(rs.getLong(SESSION_DURATION));
                
                arrSOV.add(sOV);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TrackingJdbcDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {}
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return arrSOV;
    }

    @Override
    public List<PageOverview> selectPageOverviewByPath(int appId, Timestamp from, Timestamp to) {
        String sql = String.format("select %s, sum(%s), sum(%s), sum(%s), sum(%s), sum(%s), sum(%s) "
                                    + "from %s "
                                    + "where %s=? "
                                    + "and %s between ? and ? "
                                    + "group by %s", 
                                    PATH, PAGEVIEW, UNIQUE_PAGEVIEW, BOUNCE, ENTRANCE, EXIT, TIME_ON_PAGE,
                                    "page_overview", 
                                    APPID, DATE, PATH);
        List<PageOverview> arrPOV = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            ps.setTimestamp(2, from);
            ps.setTimestamp(3, to);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                PageOverview pOV = new PageOverview();
                pOV.setAppId(appId);
                pOV.setPath(rs.getString(PATH));
                pOV.setPageviews(rs.getInt("sum(pageviews)"));
                pOV.setUniquePageviews(rs.getInt("sum(unique_pageviews)"));
                pOV.setBounces(rs.getInt("sum(bounces)"));
                pOV.setEntrances(rs.getInt("sum(entrances)"));
                pOV.setExits(rs.getInt("sum(exits)"));
                pOV.setTimeOnPage(rs.getLong("sum(total_time_on_page)"));
                
                arrPOV.add(pOV);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TrackingJdbcDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {}
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return arrPOV;
    }

    @Override
    public List<PageOverview> selectPageOverviewByDate(int appId, Timestamp from, Timestamp to) {
        
        String sql = String.format("select %s, sum(%s), sum(%s), sum(%s), sum(%s), sum(%s), sum(%s) "
                                    + "from %s "
                                    + "where %s=? "
                                    + "and %s between ? and ? "
                                    + "group by %s", 
                                    DATE, PAGEVIEW, UNIQUE_PAGEVIEW, BOUNCE, ENTRANCE, EXIT, TIME_ON_PAGE,
                                    "page_overview", 
                                    APPID, DATE, DATE);
        List<PageOverview> arrPOV = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            ps.setTimestamp(2, from);
            ps.setTimestamp(3, to);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                PageOverview pOV = new PageOverview();
                pOV.setAppId(appId);
                pOV.setDate(rs.getTimestamp(DATE));
                pOV.setPageviews(rs.getInt("sum(pageviews)"));
                pOV.setUniquePageviews(rs.getInt("sum(unique_pageviews)"));
                pOV.setBounces(rs.getInt("sum(bounces)"));
                pOV.setEntrances(rs.getInt("sum(entrances)"));
                pOV.setExits(rs.getInt("sum(exits)"));
                pOV.setTimeOnPage(rs.getLong("sum(total_time_on_page)"));
                
                arrPOV.add(pOV);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TrackingJdbcDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {}
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return arrPOV;
    }
    
    @Override
    public List<Browser> selectBrowser(int appId, Timestamp from, Timestamp to) {
        
        String sql = String.format("select %s, sum(%s) "
                                    + "from %s "
                                    + "where %s=? "
                                    + "and %s between ? and ? "
                                    + "group by %s", 
                                    "browser_type", SESSION,
                                    "browser", APPID, DATE, "browser_type");
        List<Browser> browsers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            ps.setTimestamp(2, from);
            ps.setTimestamp(3, to);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Browser browser = new Browser();
                browser.setAppId(appId);
                browser.setBrowserName(rs.getString("browser_type"));
                browser.setSessions(rs.getInt("sum(sessions)"));
                
                browsers.add(browser);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TrackingJdbcDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {}
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {}
            }
        }
        return browsers;
    }

    @Override
    public List<Device> selectDevice(int appId, Timestamp from, Timestamp to) {
        
        String sql = String.format("select %s, sum(%s) "
                                    + "from %s "
                                    + "where %s=? "
                                    + "and %s between ? and ? "
                                    + "group by %s", 
                                    "device_type", SESSION,
                                    "device", APPID, DATE, "device_type");
        List<Device> devices = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            ps.setTimestamp(2, from);
            ps.setTimestamp(3, to);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Device device = new Device();
                device.setAppId(appId);
                device.setDeviceName(rs.getString("device_type"));
                device.setSessions(rs.getInt("sum(sessions)"));
                
                devices.add(device);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TrackingJdbcDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {}
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {}
            }
        }
        return devices;
    }

    @Override
    public List<OS> selectOS(int appId, Timestamp from, Timestamp to) {
        
        String sql = String.format("select %s, sum(%s) "
                                    + "from %s "
                                    + "where %s=? "
                                    + "and %s between ? and ? "
                                    + "group by %s", 
                                    "os_type", SESSION,
                                    "os", APPID, DATE, "os_type");
        List<OS> arrOS = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            ps.setTimestamp(2, from);
            ps.setTimestamp(3, to);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                OS os = new OS();
                os.setAppId(appId);
                os.setosName(rs.getString("os_type"));
                os.setSessions(rs.getInt("sum(sessions)"));
                
                arrOS.add(os);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TrackingJdbcDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {}
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {}
            }
        }
        return arrOS;
    }

    @Override
    public List<Location> selectLocation(int appId, Timestamp from, Timestamp to) {
        
        String sql = String.format("select %s, sum(%s) "
                                    + "from %s "
                                    + "where %s=? "
                                    + "and %s between ? and ? "
                                    + "group by %s", 
                                    "location", SESSION,
                                    "location", APPID, DATE, "location");
        List<Location> cities = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            ps.setTimestamp(2, from);
            ps.setTimestamp(3, to);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Location city = new Location();
                city.setAppId(appId);
                city.setCity(rs.getString("location"));
                city.setSessions(rs.getInt("sum(sessions)"));
                
                cities.add(city);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TrackingJdbcDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {}
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {}
            }
        }
        return cities;
    }

    @Override
    public List<Language> selectLanguage(int appId, Timestamp from, Timestamp to) {
        String sql = String.format("select %s, sum(%s) "
                                    + "from %s "
                                    + "where %s=? "
                                    + "and %s between ? and ? "
                                    + "group by %s", 
                                    "language", SESSION,
                                    "language", APPID, DATE, "language");
        List<Language> languages = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            ps.setTimestamp(2, from);
            ps.setTimestamp(3, to);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Language language = new Language();
                language.setAppId(appId);
                language.setLanguage(rs.getString("language"));
                language.setSessions(rs.getInt("sum(sessions)"));
                
                languages.add(language);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TrackingJdbcDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {}
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {}
            }
        }
        return languages;
    }

    @Override
    public List<Referal> selectReferal(int appId, Timestamp from, Timestamp to) {
        
        String sql = String.format("select %s, %s, %s, %s "
                                    + "from %s "
                                    + "where %s=? "
                                    + "and %s between ? and ?", 
                                    DATE, "url_ref", "ref_type", SESSION,
                                    "referal", APPID, DATE);
        List<Referal> referals = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            ps.setTimestamp(2, from);
            ps.setTimestamp(3, to);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Referal referal = new Referal();
                referal.setAppId(appId);
                referal.setDate(rs.getTimestamp(DATE));
                referal.setUrlRef(rs.getString("url_ref"));
                referal.setRefType(rs.getInt("ref_type"));
                referal.setSessions(rs.getInt(SESSION));
                
                referals.add(referal);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TrackingJdbcDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {}
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {}
            }
        }
        return referals;
    }
}
