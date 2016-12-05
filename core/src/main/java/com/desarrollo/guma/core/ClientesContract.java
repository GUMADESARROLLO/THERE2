package com.desarrollo.guma.core;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by luis.perez on 25/11/2016.
 * Esta Clase provee las constantes y URIs necesarias para trabjar con el
 * ClientesProvider
 */

public final class ClientesContract
{
    public static final String AUTHORITY = "com.desarrollo.guma.core.provider";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri CLIENTES_URI = Uri.withAppendedPath(ClientesContract.BASE_URI,"/clientes");
    /**
     * MIME Types
     */
    public static final String URI_TYPE_CLIENTE_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE +
            "/com.desarrollo.guma.core.provider.clientes";
    public static final String URI_TYPE_CLIENTE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE +
            "/com.desarrollo.guma.core.provider.clientes";

    public static final class ClientesColumns implements  BaseColumns
    {
        private ClientesColumns() {}

        public static final String CLIENTE = "CLIENTE";
        public static final String NOMBRE = "NOMBRE";
        public static final String DIRECCION = "DIRECCION";
        public static final String TELEFONO1 = "TELEFONO1";
        public static final String ESTADO = "ESTADO";

        public static final String DEFAULT_SORT_ORDER = NOMBRE + " ASC";

    }
}
