package com.desarrollo.guma.there2;

/**
 * Created by analista3.unimarksa on 14/11/2016.
 */

public class ClssURL
{
    //private static String SERVER = "192.168.1.78";
    private static String SERVER = "165.98.75.219:8448";
    private static String URL_CLENTES= "http://"+ SERVER +"/rest_ppts_uma/CLIENTES.php";

    public ClssURL() { }
    public static String getURL_CLENTES() { return URL_CLENTES; }

}
