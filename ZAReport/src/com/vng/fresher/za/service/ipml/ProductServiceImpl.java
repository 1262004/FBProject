/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.service.ipml;

import com.vng.fresher.za.dao.ProductDao;
import com.vng.fresher.za.dao.impl.jdbc.ProductJdbcDao;
import com.vng.fresher.za.model.Product;
import com.vng.fresher.za.model.RoleUserApp;
import com.vng.fresher.za.service.ProductService;
import com.vng.fresher.za.util.Helper;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author baonh2
 */
public class ProductServiceImpl implements ProductService {

    public static String STATUS_ENABLE = "enable";
    public static String STATUS_DISABLE = "disabled";

    private static final ProductDao productDao = new ProductJdbcDao();

    @Override
    public Product create(int userId, String name, String url, String owner) {
        if (Helper.validateProductName(name) && Helper.validateProductUrl(url)) {
            Timestamp registeredAt = new Timestamp(System.currentTimeMillis());
            Product product = new Product(0, name, url, 30, STATUS_ENABLE, registeredAt, owner);
            return productDao.insert(userId, product);
        }
        return null;
    }

    @Override
    public List<Product> getAllProduct() {
        return productDao.selectALl();
    }

    @Override
    public List<Product> getAllProductByUser(int userId) {
        return productDao.selectByUser(userId);
    }

    @Override
    public boolean updateProduct(int appId, String name, int timeout, String status, Timestamp registeredAt) {

        if (Helper.validateProductName(name) && Helper.validateProductTimeout(timeout) && Helper.validateProductStatus(status)) {
            Product product = productDao.selectById(appId);
            if (product != null) {
                product.setName(name);
                product.setTimeout(timeout);
                product.setStatus(status);
                product.setRegisteredAt(registeredAt);

                return productDao.update(product);
            }
        }
        return false;
    }

    @Override
    public boolean deleteProductById(int appId) {
        return productDao.deleteById(appId);
    }

    @Override
    public RoleUserApp getRoleOnApp(int userId, int appId) {
        RoleUserApp role = productDao.selectRoleUserApp(userId, appId);
        String sHash = Helper.encrypt(role.getUserId() + role.getAppId() + "admin" + role.getSalt());
        if (role.getRole().equals(sHash)) {
            role.setRole("admin");
        } else {
            role.setRole("reporter");
        }
        return role;
    }

    @Override
    public List<RoleUserApp> getRoleOnAppByUserId(int userId) {
        List<RoleUserApp> roles = productDao.selectRoleUserAppByUserId(userId);
        for (RoleUserApp role : roles) {
            String sHash = Helper.encrypt(role.getUserId() + role.getAppId() + "admin" + role.getSalt());
            if (role.getRole().equals(sHash)) {
                role.setRole("admin");
            } else {
                role.setRole("reporter");
            }
        }
        return roles;
    }

    @Override
    public boolean addProductReporter(int userId, int appId) {
        String salt = Helper.createSalt();
        String hash = Helper.encrypt(userId + appId + "reporter" + salt);
        RoleUserApp role = new RoleUserApp(userId, appId, hash, salt);
        return productDao.insertRoleUserApp(role);
    }

    @Override
    public boolean removeProductReporter(int userId, int appId) {
        return productDao.deleteRoleUserApp(userId, appId);
    }

    @Override
    public List<RoleUserApp> getRoleOnAppByAppId(int appId) {
        List<RoleUserApp> roles = productDao.selectRoleUserAppByAppId(appId);
        for (RoleUserApp role : roles) {
            String sHash = Helper.encrypt(role.getUserId() + role.getAppId() + "admin" + role.getSalt());
            if (role.getRole().equals(sHash)) {
                role.setRole("admin");
            } else {
                role.setRole("reporter");
            }
        }
        return roles;
    }

    @Override
    public boolean leaveProductReporter(int userId, int appId) {
        return productDao.deleteRoleUserApp(userId, appId);
    }

}
