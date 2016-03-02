/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.dao.impl.jdbc;

import com.vng.fresher.za.Database;
import com.vng.fresher.za.dao.ProductDao;
import com.vng.fresher.za.model.Product;
import com.vng.fresher.za.model.RoleUserApp;
import com.vng.fresher.za.util.Helper;
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
public class ProductJdbcDao implements ProductDao {

    private final String SQL_INSERT_PRODUCT = "insert into app(name, url, timeout, status, registeredAt) values(?,?,?,?,?)";
    private final String SQL_INSERT_ROLE = "insert into user_app(user_id, app_id, role, salt) values(?,?,?,?)";
    private final String SQL_SELECT_BY_ID = "select name, url, timeout, status, registeredAt from app where id=?";
    private final String SQL_SELECT_ALL = "select id, name, url, timeout, status, registeredAt from app";
    private final String SQL_SELECT_BY_USER = "select id, name, url, timeout, status, registeredAt from app, user_app where id=app_id and user_id=?";
    private final String SQL_SELECT_BY_OWNER = "select id, name, url, timeout, status, registeredAt from app where owner=?";
    private final String SQL_UPDATE = "update app set name=?, timeout=?, status=?, registeredAt=? where id=?";
    private final String SQL_UPDATE_ROLE = "update user_app set role=?, salt=? where user_id=? and app_id=?";
    private final String SQL_UPDATE_TIMEOUT = "update app set timeout=? where id=?";
    private final String SQL_UPDATE_STATUS = "update app set status=? where id=?";
    private final String SQL_DELETE_BY_ID = "delete from app where id=?";
    private final String SQL_DELETE_ROLE = "delete from user_app where app_id=?";
    private final String SQL_DELETE_ROLE_BY_USER_ID_AND_APP_ID = "delete from user_app where user_id=? and app_id=?";
    private final String SQL_DELETE_BY_OWNER = "delete from app where owner=?";
    private final String SQL_SELECT_ROLE_USER_APP = "select role, salt from user_app where user_id=? and app_id=?";
    private final String SQL_SELECT_ROLE_USER_APP_BY_USER_ID = "select app_id, role, salt from user_app where user_id=?";
    private final String SQL_SELECT_ROLE_USER_APP_BY_APP_ID = "select user_id, role, salt from user_app where app_id=?";

    @Override
    public Product insert(int userId, Product product) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getUrl());
            ps.setInt(3, product.getTimeout());
            ps.setString(4, product.getStatus());
            ps.setTimestamp(5, product.getRegisteredAt());

            ps.executeUpdate();

            ResultSet genKeys = ps.getGeneratedKeys();
            if (genKeys.next()) {
                int appId = genKeys.getInt(1);
                String salt = Helper.createSalt();
                String role = Helper.encrypt(userId + appId + "admin" + salt);

                product.setId(appId);
                ps = conn.prepareStatement(SQL_INSERT_ROLE);
                ps.setInt(1, userId);
                ps.setInt(2, appId);
                ps.setString(3, role);
                ps.setString(4, salt);

                genKeys.close();

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    return product;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return null;
    }

    @Override
    public Product selectById(int id) {
        Product product = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY_ID);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setId(id);
                product.setName(rs.getString("name"));
                product.setUrl(rs.getString("url"));
                product.setTimeout(rs.getInt("timeout"));
                product.setStatus(rs.getString("status"));
                product.setRegisteredAt(rs.getTimestamp("registeredAt"));
                product.setOwner(rs.getString("owner"));
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return product;
    }

    @Override
    public List<Product> selectALl() {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ALL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setUrl(rs.getString("url"));
                product.setTimeout(rs.getInt("timeout"));
                product.setStatus(rs.getString("status"));
                product.setRegisteredAt(rs.getTimestamp("registeredAt"));

                products.add(product);
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return products;
    }

    @Override
    public List<Product> selectByUser(int userId) {

        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY_USER);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setUrl(rs.getString("url"));
                product.setTimeout(rs.getInt("timeout"));
                product.setStatus(rs.getString("status"));
                product.setRegisteredAt(rs.getTimestamp("registeredAt"));

                products.add(product);
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return products;
    }

    @Override
    public List<Product> selectByOwner(String owner) {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY_OWNER);

            ps.setString(1, owner);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setUrl(rs.getString("url"));
                product.setTimeout(rs.getInt("timeout"));
                product.setStatus(rs.getString("status"));
                product.setRegisteredAt(rs.getTimestamp("registeredAt"));
                product.setOwner(owner);

                products.add(product);
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return products;
    }

    @Override
    public boolean update(Product product) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setString(1, product.getName());
            ps.setInt(2, product.getTimeout());
            ps.setString(3, product.getStatus());
            ps.setTimestamp(4, product.getRegisteredAt());
            ps.setInt(5, product.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_DELETE_ROLE);

            ps.setInt(1, id);

            ps.executeUpdate();
            ps = conn.prepareStatement(SQL_DELETE_BY_ID);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteByOwner(String owner) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_DELETE_BY_OWNER);

            ps.setString(1, owner);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return false;
    }

    @Override
    public List<RoleUserApp> selectRoleUserAppByUserId(int userId) {

        List<RoleUserApp> roles = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ROLE_USER_APP_BY_USER_ID);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RoleUserApp role = new RoleUserApp();
                role.setUserId(userId);
                role.setAppId(rs.getInt("app_id"));
                role.setRole(rs.getString("role"));
                role.setSalt(rs.getString("salt"));

                roles.add(role);
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return roles;
    }

    @Override
    public RoleUserApp selectRoleUserApp(int userId, int appId) {

        RoleUserApp role = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ROLE_USER_APP);

            ps.setInt(1, userId);
            ps.setInt(2, appId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                role = new RoleUserApp();
                role.setUserId(userId);
                role.setAppId(appId);
                role.setRole(rs.getString("role"));
                role.setSalt(rs.getString("salt"));
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return role;
    }

    @Override
    public boolean insertRoleUserApp(RoleUserApp role) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_INSERT_ROLE);
            
            ps.setInt(1, role.getUserId());
            ps.setInt(2, role.getAppId());
            ps.setString(3, role.getRole());
            ps.setString(4, role.getSalt());
            
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteRoleUserApp(int userId, int appId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_DELETE_ROLE_BY_USER_ID_AND_APP_ID);
            
            ps.setInt(1, userId);
            ps.setInt(2, appId);
            
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return false;
    }

    @Override
    public List<RoleUserApp> selectRoleUserAppByAppId(int appId) {
        List<RoleUserApp> roles = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Database.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ROLE_USER_APP_BY_APP_ID);
            ps.setInt(1, appId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RoleUserApp role = new RoleUserApp();
                role.setUserId(rs.getInt("user_id"));
                role.setAppId(appId);
                role.setRole(rs.getString("role"));
                role.setSalt(rs.getString("salt"));

                roles.add(role);
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return roles;
    }

}
