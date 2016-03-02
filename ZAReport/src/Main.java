/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import authen.qrcode.webserver.QRCodeServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author quanvd
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server(1987);

        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.setContextPath("/");
        servletContext.addServlet(IndexHandler.class, "");
        servletContext.addServlet(DirectHandler.class, "/direct");
        servletContext.addServlet(QRCodeServlet.class, "/QRCode");
        ContextHandler contextHandler = new ContextHandler("/");
        contextHandler.setResourceBase("public/");
        contextHandler.setHandler(new ResourceHandler());

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{servletContext, contextHandler});

        server.setHandler(handlers);
        server.start();
        System.out.println(("Server started"));
        long num = 30;
        System.out.println(System.currentTimeMillis() - num * 86400000);
        System.out.println(System.currentTimeMillis());
        server.join();
        //getOAuthCode("178822032455124", "http://dev.apifb.zapps.vn:1989/", "abc");
        // GetAppAccess_token("178822032455124", "b492acac516c7fc8ca76193471bf7e02");
        //Test();
    }

    public static void Test() {
        String jsonString = "{\"object\":\"page\",\"entry\":[{\"id\":\"934500339950506\",\"time\":1446723242,\"changes\":[{\"field\":\"feed\",\"value\":{\"item\":\"status\",\"verb\":\"add\",\"post_id\":\"934500339950506_934565053277368\",\"sender_id\":934500339950506,\"published\":1,\"created_time\":1446723242,\"message\":\"ddasdas\",\"sender_name\":\"C\\u00e2u l\\u1ea1c b\\u1ed9 Future\"}}]},{\"id\":\"934500339950506\",\"time\":1446723248,\"changes\":[{\"field\":\"feed\",\"value\":{\"item\":\"status\",\"verb\":\"add\",\"post_id\":\"934500339950506_934565123277361\",\"sender_id\":934500339950506,\"published\":1,\"created_time\":1446723248,\"message\":\"sssssss\",\"sender_name\":\"C\\u00e2u l\\u1ea1c b\\u1ed9 Future\"}}]}]}  ";
        JSONObject json = null;
        try {
            json = (JSONObject) new JSONParser().parse(jsonString);
        } catch (ParseException ex) {
            Logger.getLogger(IndexHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONArray objs = (JSONArray) json.get("entry");

        Iterator<JSONObject> iterator = objs.iterator();

        while (iterator.hasNext()) {
            JSONObject idObj = (JSONObject) iterator.next();
            JSONArray chanels = (JSONArray) idObj.get("changes");

            Iterator<JSONObject> iterator1 = chanels.iterator();

            while (iterator1.hasNext()) {
                JSONObject idObj1 = (JSONObject) iterator1.next();
                //   System.out.println(idObj1);
                JSONObject id = (JSONObject) idObj1.get("value");
                System.out.println("message=" + id.get("message"));

                String sender = (String) id.get("sender_name");

                System.out.println("sender=" + sender);
            }

        }
    }

    public static String getOAuthCode(String appID, String redirectUrl, String state) throws Exception {
        String url = "https://www.facebook.com/dialog/oauth?client_id=" + appID + "&redirect_uri=" + redirectUrl + "&scope=manage_pages%2Cpublish_stream&state=" + state;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        String USER_AGENT = "Mozilla/5.0";
        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();
    }

    public static void GetAppAccess_token(String appID, String appSecret) throws IOException {
        String url = "https://graph.facebook.com/oauth/access_token?client_id=" + appID + "&client_secret=" + appSecret + "&grant_type=client_credentials";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        String USER_AGENT = "Mozilla/5.0";
        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    public static void GetPageAccess_token(String longLiveToken) throws IOException {
        String url = "https://graph.facebook.com/me/accounts?access_token=" + longLiveToken;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        String USER_AGENT = "Mozilla/5.0";
        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    public static void AppSubscriptions(String appId, String callbackUrl, String token) throws IOException {
        PostMethod method = new PostMethod("https://graph.facebook.com/" + appId + "/subscriptions?callback_url=" + callbackUrl + "&object=page&fields=feed&verify_token=streamInit&method=POST&access_token="
                + token);

        HttpClient httpClient = new HttpClient();

        if (method != null) {
            int statusCode = httpClient.executeMethod(method);
            String response = new String(method.getResponseBody());
            if (statusCode == HttpStatus.SC_OK) {

                //Completed streaming initiation
            } else {
                //Streaming initialization failed"
            }
        }
    }

    public static void PageSubscriptions(String appId, String pageId, String callbackUrl, String pageToken, String v) throws IOException {
        PostMethod method = new PostMethod("https://graph.facebook.com/" + pageId + "/tabs?app_id=" + appId
                + "&method=POST&access_token=" + pageToken);

        HttpClient httpClient = new HttpClient();

        if (method != null) {
            int statusCode = httpClient.executeMethod(method);
            String response = new String(method.getResponseBody());
            if (statusCode == HttpStatus.SC_OK) {

                //Completed streaming initiation
            } else {
                //Streaming initialization failed"
            }
        }
    }
}
