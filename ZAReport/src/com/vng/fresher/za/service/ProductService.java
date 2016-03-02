/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.service;

import com.vng.fresher.za.model.Product;
import com.vng.fresher.za.model.RoleUserApp;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author quanvd
 */
public interface ProductService {
    
    public Product create(int userId, String name, String url, String owner);
    public List<Product> getAllProduct();
    public List<Product> getAllProductByUser(int userId);
    public boolean updateProduct(int appId, String name, int timeout, String status, Timestamp registeredAt);
    public boolean deleteProductById(int appId);
    public RoleUserApp getRoleOnApp(int userId, int appId);
    public List<RoleUserApp> getRoleOnAppByUserId(int userId);

    public boolean addProductReporter(int userId, int appId);

    public boolean removeProductReporter(int id, int appId);

    public List<RoleUserApp> getRoleOnAppByAppId(int appId);

    public boolean leaveProductReporter(int userId, int appId);
    
}
