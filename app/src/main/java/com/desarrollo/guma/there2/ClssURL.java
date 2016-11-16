package com.desarrollo.guma.there2;

/**
 * Created by analista3.unimarksa on 14/11/2016.
 */

public class ClssURL
{
    private static String SERVER = "192.168.1.155:8080";
    private static String URL_Asyn= "http://"+ SERVER +"/APIREST/FacturaPuntosUma.php";

    public ClssURL() { }
    public static String getURL_Asyn() { return URL_Asyn; }

}
