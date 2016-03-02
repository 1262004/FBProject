/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.dao;

import com.vng.fresher.za.model.RoleUserApp;
import com.vng.fresher.za.model.User;
import java.util.List;

/**
 *
 * @author baonh2
 */
public interface UserDao{
    
    public User insert(User user);
    public List<User> selectAll();
    public User selectById(int id);
    public User selectByUsername(String username);
    public boolean update(User user);
    public boolean updatePassword(String username, String password, String salt);
    public boolean updateRole(String username, String role, String salt);
    public boolean updateStatus(String username, String status);
    public boolean deleteById(int id);
    public boolean deleteByUsername(String username);
    public List<RoleUserApp> selectRoleApp(int userId);

    public List<User> selectByAppId(int appId);
}
