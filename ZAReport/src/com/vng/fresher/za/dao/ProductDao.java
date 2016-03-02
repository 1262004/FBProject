/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.dao;

import com.vng.fresher.za.model.Product;
import com.vng.fresher.za.model.RoleUserApp;
import java.util.List;

/**
 *
 * @author baonh2
 */
public interface ProductDao{
    
    public Product insert(int userId, Product product);
    public Product selectById(int id);
    public List<Product> selectALl();
    public List<Product> selectByUser(int userId);
    public List<Product> selectByOwner(String owner);
    public boolean update(Product product);
    public boolean deleteById(int id);
    public boolean deleteByOwner(String owner);
    public List<RoleUserApp> selectRoleUserAppByUserId(int userId);

    public RoleUserApp selectRoleUserApp(int userId, int appId);
    public boolean insertRoleUserApp(RoleUserApp role);

    public boolean deleteRoleUserApp(int userId, int appId);

    public List<RoleUserApp> selectRoleUserAppByAppId(int appId);
}
