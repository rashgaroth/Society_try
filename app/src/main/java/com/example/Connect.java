package com.example;

public class Connect {
    public static String IP_CONNECT = "http://societyhub.xyz/config/society_php/";
    public static String REGIST_CONNECT = "http://societyhub.xyz/config/society_php/register.php";
    public static String LOGIN_CONNECT = "http://societyhub.xyz/config/society_php/login.php";
    public String connectAPI(){
        return IP_CONNECT;
    }

    public String Regist(){
        return REGIST_CONNECT;
    }

    public String Login(){
        return LOGIN_CONNECT;
    }
}
