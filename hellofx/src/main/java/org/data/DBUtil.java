
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.data;

import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bob50
 */
public class DBUtil {

    //https://blog.csdn.net/superdangbo/article/details/78732700  驱动器问题 mysql8.0以上
    // FXML is not a compiled language; you do not need to recompile the code to see the changes

    private static final String driver;
    private static final String URL;
    private static final String userName;
    private static final String Password;

    //Connection c1 = null;
    static{
        try
        {
            InputStream is = null;
            Properties ps  = new Properties();
            //is = JDBCUtils.class.getClassLoader().getResourceAsStream(""
            //		+ "com/jdbc/test/sql.properties");
            is = Resources.getResourceAsStream("jdbc.properties");
            //这种情况省略宝和getClassLoader()
            ps.load(is);
            driver = ps.getProperty("jdbc.driver");
            //			System.out.println(mysqlDriver);
            URL = ps.getProperty("jdbc.url");
            //			System.out.println(connectDatabase);
            userName = ps.getProperty("jdbc.username");
            //			System.out.println(userName);
            Password = ps.getProperty("jdbc.password");
            //			System.out.println(Password);
            //仅仅在静态代码段中加载一次驱动即可（在运行中）

        } catch ( IOException e)
        {
            // TODO Auto-generated catch block
            //System.out.println("配置文件加载失败"+e.getMessage());
            throw new RuntimeException("配置文件sql.properties加载失败"+e.getMessage());
            //return null;
        }
    }

//    private static String driver = "com.mysql.cj.jdbc.Driver";
//    //https://www.cnblogs.com/godwithus/p/9788790.html  时区问题
//    private static String URL = "jdbc:mysql://localhost:3306/studentdb?useSSL=false&useUnicode=true&characterEncoding=UTF8&serverTimezone=UTC";
    private static Connection con = null;
    private static Statement smt = null;
    private static ResultSet rs = null;

    private static Connection createConnection() {
        try {

            Class.forName(driver);
            return DriverManager.getConnection(URL, userName, Password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Can't load Driver");
        }
        return null;
    }

    public static int runUpdate(String sql) throws SQLException {
        int count = 0;
        if (con == null) {
            con = createConnection();
        }
        if (smt == null) {
            smt = con.createStatement();
        }

        count = smt.executeUpdate(sql);

        if (smt != null) {
            smt.close();
            smt = null;
        }
        if (con != null) {
            con.close();
            con = null;
        }
        return count;
    }


    public static ResultSet runQuery(String sql) throws SQLException {
        if (con == null) {
            con = createConnection();
        }
        if (smt == null) {
            smt = con.createStatement();
        }
        return smt.executeQuery(sql);
    }

    public static void realeaseAll() {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (smt != null) {
            try {
                smt.close();
                smt = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException ex) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static void closeConnection(Connection conn) {
        System.out.println("...");
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
