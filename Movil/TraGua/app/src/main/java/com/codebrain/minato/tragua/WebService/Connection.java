package com.codebrain.minato.tragua.WebService;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by username on 11/25/2017.
 */

public class Connection {
    private URL url;
    private HttpURLConnection httpURLConnection;
    String method;

    public Connection()
    {
        this.url = null;
        this.httpURLConnection = null;
        method = "GET";
    }

    public Connection(String url, String method) throws MalformedURLException
    {
        this.url = new URL("192.168.1.1/" + url);
        this.method = method;
    }

    public void startConnection() throws IOException
    {
        httpURLConnection = (HttpURLConnection)this.url.openConnection();
        httpURLConnection.setRequestMethod(this.method);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.connect();
    }

    public void sendParameters(JSONObject object) throws IOException
    {
        OutputStream outputStream = this.httpURLConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(outputStream, "UTF-8")
        );

        writer.write(object.toString());
        writer.flush();
        writer.close();
    }

    public void getResponse()
    {

    }
}
