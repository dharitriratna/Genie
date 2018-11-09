package com.example.user.genie.handler;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Techinflo on 17-09-16.
 */
public class NewworkHandlerImage {
     public static String getNetworkData(String urlTo, String userid,String category,String price,
                                         String desc,String phone,String address, String filepath) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urlTo);
        try{
            //use multipart entity
            MultipartEntity multipartEntity=new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE);
            File sourceFile = new File(filepath);

            // Adding file data to http body
            multipartEntity.addPart("file", new FileBody(sourceFile));
            multipartEntity.addPart("user_id", new StringBody(userid));
            multipartEntity.addPart("category", new StringBody(category));
            multipartEntity.addPart("price", new StringBody(price));
            multipartEntity.addPart("description", new StringBody(desc));
            multipartEntity.addPart("phone", new StringBody(phone));
            multipartEntity.addPart("address", new StringBody(address));


            httppost.setEntity(multipartEntity);

            // Making server call
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();

            if(response!=null){
                InputStream instream = r_entity.getContent();
                String result = convertStreamToString(instream);
                instream.close();
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private static String convertStreamToString(InputStream is) {
        //convert to string
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
