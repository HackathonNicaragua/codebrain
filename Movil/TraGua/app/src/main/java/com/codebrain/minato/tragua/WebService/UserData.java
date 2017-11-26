package com.codebrain.minato.tragua.WebService;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by username on 11/25/2017.
 */

public class UserData {
    public static int codeResult = -1;

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

    public static boolean insertNewUser(String...args) throws MalformedURLException, IOException, JSONException
    {
        String data = "AddUser&username="+ args[0] +"&password="+ args[1] +"&firstname="+ args[2] +"&lastname="+ args[3] +"&email="+ args[4];
        Connection newConnection = new Connection(data, "GET");
        newConnection.startConnection();

        String result = newConnection.getResponse();

        if (result.compareTo("0") == 0)
        {
            //correcto
            return true;
        }
        else if (result.compareTo("0") == 2)
        {
            //no se reciben los parametros
            return false;
        }
        else if (result.compareTo("0") == 3)
        {
            //ya existe
            codeResult = 1;
            return false;
        }
        else if (result.compareTo("0") == 4)
        {
            //no se pudo registrar
            codeResult = 2;
            return false;
        }
        return false;
    }
}
