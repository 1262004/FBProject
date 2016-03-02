/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import hapax.Template;
import hapax.TemplateDictionary;
import hapax.TemplateException;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.jetty.server.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author baonh
 */
public class IndexHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map parametersMap = request.getParameterMap();
        if (parametersMap.size() > 0) {
            if (request.getParameter("hub.mode").equals("subscribe") && request.getParameter("hub.verify_token").equals("abc")) {
                String responseToClient = request.getParameter("hub.challenge");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(responseToClient);
                response.getWriter().flush();
                response.getWriter().close();  //"Completed streaming setup on your url"
            }
        }
        System.out.print(request.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(request.getContentType());

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        System.out.println(body); // JSON STRING

        JSONObject json = null;
        try {
            json = (JSONObject) new JSONParser().parse(body);
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
            // write file
            try {
                String content = body;
                File file = new File("/root/filename.txt");
                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                System.out.println("Done");
            } catch (IOException e) {
                e.printStackTrace();
            }
//         // Read from request
//    StringBuilder buffer = new StringBuilder();
//    BufferedReader reader = request.getReader();
//    String line;
//    while ((line = reader.readLine()) != null) {
//        buffer.append(line);
//    }
//    String data = buffer.toString();

//        String passedSignature = request.getHeader("X-Hub-Signature").substring(5);
//        
//       Mac hmac = null;
//        try {
//            hmac = Mac.getInstance("HmacSHA1");
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(IndexHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            hmac.init(new SecretKeySpec("b492acac516c7fc8ca76193471bf7e02".getBytes(Charset.forName("UTF-8")), "HmacSHA1"));
//        } catch (InvalidKeyException ex) {
//            Logger.getLogger(IndexHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        char[] calculatedSignature = Hex.encodeHex(hmac.doFinal((message.getBytes(Charset.forName("UTF-8")))));
//        
//        
//
//System.out.println("Calculated sigSHA1: " + calculatedSignature + " passedSignature: " + passedSignature); 
//        
//
//    }
        }
    }
}
