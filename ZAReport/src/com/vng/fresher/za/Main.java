/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za;

import com.vng.fresher.za.handler.IndexHandler;
import com.vng.fresher.za.handler.TestHanlder;
import com.vng.fresher.za.handler.acount.ChangePasswordHandler;
import com.vng.fresher.za.handler.acount.ChangeRoleHandler;
import com.vng.fresher.za.handler.acount.ChangeStatusHandler;
import com.vng.fresher.za.handler.acount.DeleteUserHandler;
import com.vng.fresher.za.handler.acount.GetProfileHandler;
import com.vng.fresher.za.handler.acount.GetUserListHandler;
import com.vng.fresher.za.handler.acount.LoginHandler;
import com.vng.fresher.za.handler.acount.LogoutHandler;
import com.vng.fresher.za.handler.acount.RegisterHandler;
import com.vng.fresher.za.handler.product.AddProductReporterHandler;
import com.vng.fresher.za.handler.product.GetProductListHandler;
import com.vng.fresher.za.handler.product.CreateProductHandler;
import com.vng.fresher.za.handler.product.DeleteProductHandler;
import com.vng.fresher.za.handler.product.GetProductReporterHandler;
import com.vng.fresher.za.handler.product.LeaveProductReporterHandler;
import com.vng.fresher.za.handler.product.RemoveProductReporterHandler;
import com.vng.fresher.za.handler.product.UpdateProductHandler;
import com.vng.fresher.za.handler.tracking.GetBrowserHandler;
import com.vng.fresher.za.handler.tracking.GetDeviceHandler;
import com.vng.fresher.za.handler.tracking.GetLanguageHandler;
import com.vng.fresher.za.handler.tracking.GetLocationHandler;
import com.vng.fresher.za.handler.tracking.GetOsHandler;
import com.vng.fresher.za.handler.tracking.GetPageOverviewHandler;
import com.vng.fresher.za.handler.tracking.GetReferalHandler;
import com.vng.fresher.za.handler.tracking.GetSiteOverviewHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 *
 * @author baonh2
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server(1993);
        
        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.setContextPath("/");
        servletContext.addServlet(IndexHandler.class, "");
        
        servletContext.addServlet(RegisterHandler.class, "/register");  //param: username & password
        servletContext.addServlet(LoginHandler.class, "/login");        //param: username & password
        servletContext.addServlet(LogoutHandler.class, "/logout");
        servletContext.addServlet(GetProfileHandler.class, "/user/profile");
        servletContext.addServlet(GetUserListHandler.class, "/user/list");
        servletContext.addServlet(ChangePasswordHandler.class, "/user/password"); //param: password
        servletContext.addServlet(ChangeRoleHandler.class, "/user/role");
        servletContext.addServlet(ChangeStatusHandler.class, "/user/status");
        servletContext.addServlet(DeleteUserHandler.class, "/user/delete");
        
        servletContext.addServlet(CreateProductHandler.class, "/app/add");      //param: name & url
        servletContext.addServlet(UpdateProductHandler.class, "/app/update");   //param: name & timeout & status & registeredAt
        servletContext.addServlet(DeleteProductHandler.class, "/app/delete");   //param: appId
        servletContext.addServlet(GetProductListHandler.class, "/app/list");
        servletContext.addServlet(GetProductReporterHandler.class, "/app/reporter/list");
        servletContext.addServlet(AddProductReporterHandler.class, "/app/reporter/add");
        servletContext.addServlet(RemoveProductReporterHandler.class, "/app/reporter/remove");
        servletContext.addServlet(LeaveProductReporterHandler.class, "/app/reporter/leave");
        
        servletContext.addServlet(GetSiteOverviewHandler.class, "/track/overview");   
        servletContext.addServlet(GetPageOverviewHandler.class, "/track/page");
        servletContext.addServlet(GetBrowserHandler.class, "/track/browser");
        servletContext.addServlet(GetDeviceHandler.class, "/track/device");
        servletContext.addServlet(GetOsHandler.class, "/track/os");
        servletContext.addServlet(GetLocationHandler.class, "/track/location");
        servletContext.addServlet(GetLanguageHandler.class, "/track/language");
        servletContext.addServlet(GetReferalHandler.class, "/track/referal");
        
        servletContext.addServlet(TestHanlder.class, "/test");
        
        
        ContextHandler contextHandler = new ContextHandler("/");
        contextHandler.setResourceBase("public/");
        contextHandler.setHandler(new ResourceHandler());
        
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { servletContext, contextHandler });
        
        server.setHandler(handlers);
        server.start();
        System.out.println(("Server started"));
        long num = 30;
        System.out.println(System.currentTimeMillis() - num * 86400000);
        System.out.println(System.currentTimeMillis());
        server.join();
    }
    
}
