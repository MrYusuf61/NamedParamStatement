package com.MrYusuf.NamedParamStatementExample.Example;

import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;

public class SqlConnection {
    public static Connection getMsSqlConnection(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        try {
            String server = "";
            String port = "";
            String database = "";
            String username = "";
            String password = "";
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + server + ((port == null || port.equals("")) ? "" : ":" + port) + ";" + "databaseName=" + database +
                    ";user=" + username + ";password=" + password + ";");
        }catch (Exception e){
            System.out.println(e);
        }
        return conn;
    }
}
