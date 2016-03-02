/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.dao.impl.jdbc;

import com.vng.fresher.za.Database;
import com.vng.fresher.za.dao.UserDao;
import com.vng.fresher.za.model.RoleUserApp;
import com.vng.fresher.za.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author baonh2
 */
public class UserJdbcDao implements UserDao{

    private final String SQL_INSERT = "insert into user(username, password, psalt, role, rsalt, status, registeredAt) values(?,?,?,?,?,?,?)";
    private final String SQL_SELECT_ALL = "select id, username, password, psalt, role, rsalt, status, registeredAt from user";
    private final String SQL_SELECT_BY_ID = "select username, password, psalt, role, rsalt, status, registeredAt from user where id=?";
    private final String SQL_SELECT_BY_USERNAME = "select id, password, psalt, role, rsalt, status, registeredAt from user where username=?";
    private final String SQL_SELECT_BY_APP_ID = "select id, username, status, registeredAt from user, user_app where id=user_id and app_id=?";
    private final String SQL_UPDATE = "update user set password=?, psalt=?, role=?, rsalt=?, status=?, registeredAt=? where id=?";
    private final String SQL_UPDATE_PASSWORD = "update user set password=?, psalt=? where username=?";
    private final String SQL_UPDATE_ROLE = "update user set role=?, rsalt=? where username=?";
    private final String SQL_UPDATE_STATUS = "update user set status=? where username=?";
    private final String SQL_DELETE_BY_ID = "delete from user where id=?";
    private final String SQL_DELETE_BY_USERNAME = "delete from user where username=?";
    private final String SQL_SELECT_ROLE_APP = "select app_id, role, salt from user_app where user_id=?";

    @Override
    public User insert(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getPsalt());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getRsalt());
            ps.setString(6, user.getStatus());
            ps.setTimestamp(7, user.getRegisteredAt());
            
            ps.executeUpdate();
            
            ResultSet genKeys = ps.getGeneratedKeys();
            if(genKeys.next()) {
                int userId = genKeys.getInt(1);
                user.setId(userId);
            }
            genKeys.close();
            return user;
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return null;
    }

    @Override
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setPsalt(rs.getString("psalt"));
                user.setRole(rs.getString("role"));
                user.setRsalt(rs.getString("rsalt"));
                user.setStatus(rs.getString("status"));
                user.setRegisteredAt(rs.getTimestamp("registeredAt"));
                
                users.add(user);
            }
            rs.close();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return users;
    }

    @Override
    public User selectById(int id) {
        User user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY_ID);
            
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                user = new User();
                user.setId(id);
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setPsalt(rs.getString("psalt"));
                user.setRole(rs.getString("role"));
                user.setRsalt(rs.getString("rsalt"));
                user.setStatus(rs.getString("status"));
                user.setRegisteredAt(rs.getTimestamp("registeredAt"));
            }
            rs.close();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return user;
    }

    @Override
    public User selectByUsername(String username) {
        User user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY_USERNAME);
            
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(username);
                user.setPassword(rs.getString("password"));
                user.setPsalt(rs.getString("psalt"));
                user.setRole(rs.getString("role"));
                user.setRsalt(rs.getString("rsalt"));
                user.setStatus(rs.getString("status"));
                user.setRegisteredAt(rs.getTimestamp("registeredAt"));
            }
            rs.close();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return user;
    }

    @Override
    public boolean update(User user) {
        
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE);
            
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getPsalt());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getRsalt());
            ps.setString(5, user.getStatus());
            ps.setTimestamp(6, user.getRegisteredAt());
            ps.setInt(7, user.getId());
            
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0){
                return true;
            }
            
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return false;
    }

    @Override
    public boolean updatePassword(String username, String password, String salt) {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE_PASSWORD);
            
            ps.setString(1, password);
            ps.setString(2, salt);
            ps.setString(3, username);
            
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0){
                return true;
            }
            
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return false;
    }

    @Override
    public boolean updateRole(String username, String role, String salt) {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE_ROLE);
            
            ps.setString(1, role);
            ps.setString(2, salt);
            ps.setString(3, username);
            
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0){
                return true;
            }
            
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return false;
    }

    @Override
    public boolean updateStatus(String username, String status) {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE_STATUS);
            
            ps.setString(1, status);
            ps.setString(2, username);
            
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0){
                return true;
            }
            
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_DELETE_BY_ID);
            
            ps.setInt(1, id);
            
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0){
                return true;
            }
            
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return false;
    }

    @Override
    public boolean deleteByUsername(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_DELETE_BY_USERNAME);
            
            ps.setString(1, username);
            
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0){
                return true;
            }
            
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return false;
    }

    @Override
    public List<RoleUserApp> selectRoleApp(int userId) {
        
        List<RoleUserApp> roles = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ROLE_APP);
            
            ps.setInt(1, userId);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                RoleUserApp role = new RoleUserApp();
                role.setUserId(userId);
                role.setAppId(rs.getInt("app_id"));
                role.setRole(rs.getString("role"));
                role.setSalt(rs.getString("salt"));
                
                roles.add(role);
            }
            rs.close();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return roles;
    }

    @Override
    public List<User> selectByAppId(int appId) {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY_APP_ID);
            
            ps.setInt(1, appId);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setStatus(rs.getString("status"));
                user.setRegisteredAt(rs.getTimestamp("registeredAt"));
                
                users.add(user);
            }
            rs.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) { }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        return users;
    }
    
}
