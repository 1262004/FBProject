/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 *
 * @author baonh2
 */
public class Memcached {
    
    public static MemCachedClient mcc; 
    
    static {
        String[] servers = {"localhost:11211"};
        Integer[] weight = {1};
        SockIOPool pool = SockIOPool.getInstance("ZAnalytics");
        pool.setServers(servers);
        pool.setWeights(weight);
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaxIdle(1000 * 60 * 60 *6);
        pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);
        pool.initialize();
        
        mcc = new MemCachedClient("ZAnalytics");
    }
}
