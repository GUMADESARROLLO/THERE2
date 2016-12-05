package com.desarrollo.guma.there2;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis.perez on 29/11/2016.
 */

public class JSONParser
{
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    static JSONArray jsonArray;

    static JSONArray jArray;


    public JSONParser(){}

    //funcion get json from url, haciendo uso de un metodo HTTP POST o GET
    //public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params)
    public JSONArray makeHttpRequest(String url, String method, List<NameValuePair> params)
    {
        //hacer el HTTP Request
        try
        {
            //Verificar el método solicitado
            if (method == "POST")
            {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
            else if(method == "GET")
            {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params,"utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                //JSONArray jsonArray = new JSONArray(new String(responseBody));
                is = httpEntity.getContent();
                //jArray = new JSONArray(httpResponse);

                BufferedReader streamReader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                StringBuilder responseStrBuileder = new StringBuilder();
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuileder.append(inputStr);

                new JSONArray(responseStrBuileder.toString());

                jArray  = new JSONArray(responseStrBuileder.toString());
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();


        }
        catch (Exception e)
        {
            Log.e("Buffer Error","Error Convirtiendo " + e.toString());
        }

        try
        {
            jObj = new JSONObject(json);
        }
        catch(JSONException e)
        {
            Log.e("JSON Parser","Error parsing data " + e.toString());
        }
        return jArray;
        //return jObj;
    }

}
