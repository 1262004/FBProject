/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.handler;

import com.vng.fresher.za.AppConfig;
import hapax.Template;
import hapax.TemplateDictionary;
import hapax.TemplateException;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author baonh2
 */
public class TestHanlder extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateDictionary dictionary = TemplateDictionary.create();
        dictionary.setVariable("heo", "Con heo");
        try {
            TemplateLoader loader = new TemplateResourceLoader(AppConfig.VIEWS);
            Template template = loader.getTemplate("test");
            resp.getWriter().println(template.renderToString(dictionary));
        } catch (TemplateException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
