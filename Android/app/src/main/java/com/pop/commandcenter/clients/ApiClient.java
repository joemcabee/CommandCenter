package com.pop.commandcenter.clients;

import android.content.Context;
import android.util.Log;

import com.pop.commandcenter.constants.RemoteAuth;
import com.pop.commandcenter.models.ServiceRequest;
import com.pop.commandcenter.models.ServiceResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joemc on 7/22/2017.
 */

public class ApiClient {

    public ServiceResponse httpGet(Context context, ServiceRequest request) {
        return httpConnection(context, request, "GET");
    }

    public ServiceResponse httpPost(Context context, ServiceRequest request) {
        return httpConnection(context, request, "POST");
    }

    private ServiceResponse httpConnection(Context context, ServiceRequest request, String httpMethod) {
        ServiceResponse response = new ServiceResponse();
        response.setSuccess(true);
        int timeoutInSeconds = 10;

        try {
            URL myURL = new URL(request.toString());
            HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();
            String basicAuth = RemoteAuth.getBasicAuthValue(context);

            myURLConnection.setRequestProperty ("Authorization", basicAuth);
            myURLConnection.setRequestMethod(httpMethod);
            myURLConnection.setUseCaches(false);
            myURLConnection.setConnectTimeout(timeoutInSeconds * 1000);
            myURLConnection.setReadTimeout(timeoutInSeconds * 1000);
            myURLConnection.setDoInput(true);
            myURLConnection.setDoOutput(true);

            try {
                InputStream inputStream = myURLConnection.getInputStream();;
                OutputStream outputStream = null;

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder inputStringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    inputStringBuilder.append(line).append("\n");
                }

                bufferedReader.close();

                response.setStatusCode(myURLConnection.getResponseCode());
                response.setData(inputStringBuilder.toString());

                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            }
            finally {
                myURLConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            response.setException(e);
            response.setSuccess(false);
        }

        return response;
    }

    public ServiceResponse httpGetNew(Context context, ServiceRequest request){
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setService(request.getService());

        StringBuilder sb= new StringBuilder();
        HttpClient client= new DefaultHttpClient();
        HttpGet httpget = new HttpGet(request.getUrl());

        try {
            HttpResponse response = client.execute(httpget);
            StatusLine sl = response.getStatusLine();
            int sc = sl.getStatusCode();
            serviceResponse.setStatusCode(sc);

            if (sc==200)
            {
                serviceResponse.setSuccess(true);

                HttpEntity ent = response.getEntity();
                InputStream inpst = ent.getContent();
                BufferedReader rd= new BufferedReader(new InputStreamReader(inpst));
                String line;

                while ((line=rd.readLine())!=null)
                {
                    sb.append(line);
                }

                serviceResponse.setData(sb.toString());
            }
            else
            {
                serviceResponse.setSuccess(false);
                Log.e("log_tag","I didn't  get the response!");
            }
        }
        catch (Exception e) {
            serviceResponse.setException(e);
            e.printStackTrace();
        }

        return serviceResponse;
    }
}
