/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author baonh2
 */
public class Database {
        
    private static final BasicDataSource dataSource = new BasicDataSource();
    
    static {
        dataSource.setDriverClassName(AppConfig.DRIVER);
        dataSource.setUrl(AppConfig.URL);
        dataSource.setUsername(AppConfig.USER);
        dataSource.setPassword(AppConfig.PASSWORD);
        dataSource.setMaxActive(20);
        dataSource.setInitialSize(5);
        dataSource.setMaxIdle(5);
    }
    
    public static Connection getConnection() throws SQLException{
       
        return dataSource.getConnection();
    }
}
