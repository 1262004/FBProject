/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler.acount;

import com.vng.fresher.za.AppConfig;
import com.vng.fresher.za.handler.BaseHandler;
import com.vng.fresher.za.model.User;
import hapax.Template;
import hapax.TemplateDictionary;
import hapax.TemplateException;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author quanvd
 */
public class LoginHandler extends BaseHandler {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        if(isLogined(req)) {
            resp.sendRedirect("/");
        } else {
            TemplateDictionary dictionary = TemplateDictionary.create();
            try {
                TemplateLoader loader = new TemplateResourceLoader(AppConfig.VIEWS);
                Template template = loader.getTemplate("login");
                resp.getWriter().println(template.renderToString(dictionary));
            } catch (TemplateException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        if(isLogined(req)) {
            resp.sendRedirect("/");
        } else {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            User user = userService.login(username, password);
            if(user != null) {
                HttpSession session = req.getSession();
                session.setAttribute(USER, user);
                resp.sendRedirect("/");
            } else {
                TemplateDictionary dictionary = TemplateDictionary.create();
                dictionary.setVariable("error", "The username and password you entered don't macth.");
                try {
                    TemplateLoader loader = new TemplateResourceLoader(AppConfig.VIEWS);
                    Template template = loader.getTemplate("login");
                    resp.getWriter().println(template.renderToString(dictionary));
                } catch (TemplateException ex){ }
            }
        }
    }
}
