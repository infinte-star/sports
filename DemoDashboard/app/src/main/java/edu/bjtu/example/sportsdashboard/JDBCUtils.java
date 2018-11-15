package edu.bjtu.example.sportsdashboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JDBCUtils {
    /**
     * 获得jdbc连接
     */
    static Connection connection=null;
    public static Connection getConnetion() throws Exception {
        //加载jdbc驱动
        Class.forName("com.mysql.jdbc.Driver");
        //创建连接数据库的路径
        String url = "jdbc:mysql://localhost/android_login?user=root&password=12345";
        //通过url获得与数据库的连接
        connection = DriverManager.getConnection(url);
        return connection;
    }

    public static void close(Connection connection,PreparedStatement ps,ResultSet rs) {
        //一定要确保关闭连接，以下关闭步骤是参照官方文档的，有权威性
        if(rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps!=null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
