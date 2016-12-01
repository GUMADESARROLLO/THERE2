package com.desarrollo.guma.there2;

import android.os.Environment;

import java.io.File;

/**
 * Created by analista3.unimarksa on 14/11/2016.
 */

public class ClssURL
{
    //private static String SERVER = "192.168.1.78";
    private static String SERVER = "165.98.75.219:8448";
    //private static String SERVER = "192.168.1.155:8080";
    private static String URL_CLENTES= "http://"+ SERVER +"/rest_ppts_uma/CLIENTES.php";
    private static String URL_VENDEDOR="http://"+ SERVER + "/rest_ppts_uma/VENDEDORES.php";

    private static String DIR_DB = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator;

    public ClssURL() { }
    public static String getURL_CLENTES() { return URL_CLENTES; }

    public static String getURL_VENDEDOR() { return  URL_VENDEDOR; }

    public static String getDIR_DB() { return  DIR_DB; }

}
