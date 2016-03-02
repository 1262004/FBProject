/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author baonh2
 */
public class Helper {
    
    public static final int SAL_BYTE_SIZE = 10;
    
    private static final SecureRandom random = new SecureRandom();
    
    public static boolean validateUsername(String username) {
        if(username == null){
            return false;
        }
        Pattern pattern = Pattern.compile("\\w{3,60}");
        Matcher matcher = pattern.matcher(username.trim());
        return matcher.matches();
    }
    
    public static boolean validatePassword(String password){
        if(password == null){
            return false;
        }
        Pattern pattern = Pattern.compile("\\w{3,60}");
        Matcher matcher = pattern.matcher(password.trim());
        return matcher.matches();
    }
    
    public static Timestamp parseDate(String dateStr) {
        
        try{
            long time = Long.parseLong(dateStr.trim());
            return new Timestamp(time);
        } catch(Exception ex){}
        return null;
    }
    
    public static int parseInt(String strInt) {
        try{
            return Integer.parseInt(strInt.trim());
        } catch(Exception ex) {}
        return -1;
    }
    
    public static long parseLong(String strLong) {
        try{
            return Long.parseLong(strLong);
        } catch(Exception ex) {}
        return -1;
    }
    
    public static String encrypt(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            byte[] hash = md.digest(str.getBytes());
            BigInteger bi = new BigInteger(1, hash);
            return bi.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
    
    public static String createSalt(){
        byte[] salt = new byte[SAL_BYTE_SIZE];
        random.nextBytes(salt);
        BigInteger bi = new BigInteger(1, salt);
        return bi.toString(16);   
    }
    
    public static String createProductId() {
        byte[] appId = new byte[4];
        random.nextBytes(appId);
        BigInteger bi = new BigInteger(1, appId);
        return bi.toString(16).toUpperCase();   
    }
    
    public static String genEmbedCode(int appId) {
        String code = String.format("<!-- ZA -->" +
                                    "<script type='text/javascript'>" +
                                    "var _paq = _paq || [];" +
                                    "_paq.push(['trackPageView']);" +
                                    "_paq.push(['enableLinkTracking']);" +
                                    "(function() {" +
                                    "var u='http://118.102.6.118:4527/requestreceiver';" +
                                    "_paq.push(['setTrackerUrl', u]);" +
                                    "_paq.push(['setSiteId', %s]);" +
                                    "var d=document;" +
                                    "var g=d.createElement('script');" +
                                    "var s=d.getElementsByTagName('script')[0];" +
                                    "g.type='text/javascript';" +
                                    "g.async=true; g.defer=true;" +
                                    "g.src='http://118.102.6.120:3333/za.js';" +
                                    "s.parentNode.insertBefore(g,s);" +
                                    "})();" +
                                    "</script>" +
                                    "<noscript>" +
                                    "<p>" +
                                    "<img src='http://118.102.6.118:4527/requestreceiver?idsite=%s' style='border:0;' alt='' />" +
                                    "</p>" +
                                    "</noscript>" +
                                    "<!-- End ZA Code -->",
                                    appId, appId);
        return code;
    }

    public static boolean validateProductName(String name) {
        return name != null;
    }

    public static boolean validateProductUrl(String url) {
        return url != null;
    }

    public static boolean validateProductTimeout(int timeout) {
        return timeout > 0;
    }

    public static boolean validateProductStatus(String status) {
        if(status.equals("enable") || status.equals("disabled"))
            return true;
        return false;
    }
}
