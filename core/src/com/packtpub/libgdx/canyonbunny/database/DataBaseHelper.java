package com.packtpub.libgdx.canyonbunny.database;
import java.sql.*;
/**
 * Created by anxi.xue on 2018/4/6.
 */

public class DataBaseHelper {
    public static void main (String[] arg) {

        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
