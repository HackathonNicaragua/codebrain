package com.codebrain.minato.tragua.WebService;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by username on 11/25/2017.
 */

public class UserData {

    public static boolean checkLogin(String username, String password) throws MalformedURLException, IOException, JSONException
    {
        //Login&username=SideMaster&password=Programador
        String data = "Login&username=" + username + "&password="+password;
        Connection newConnection = new Connection(data,"GET");
        newConnection.startConnection();
        String result = newConnection.getResponse();

        if (result.compareTo("0") == 0)
        {
            return true;
        }
        else if (result.compareTo("0") == 2)
        {
            return false;
        }
        return false;
    }
}
