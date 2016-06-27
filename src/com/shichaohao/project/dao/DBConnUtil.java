package com.shichaohao.project.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 用来配置、连接数据库
 * @author Think
 *
 */
public class DBConnUtil {
	private static String driver = ""; //定义连接信息
    private static String url = "";
    private static String user = "";
    private static String password = "";
   
    /**
     * 静态代码块用来读取数据库配置文件
     */
    static{
    	InputStream is = null;
    	try {
    		is = DBConnUtil.class.getClassLoader().getResourceAsStream("database.properties");
    		if (is == null) System.out.println("sss");
        	Properties p = new Properties();
			p.load(is);
			driver = p.getProperty("driverClassName");
			url = p.getProperty("url");
			user = p.getProperty("user");
			password = p.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
    }
    
    /**
     * 创建数据库的连接
     * @return 返回Connection对象
     */
    public static Connection getConn(){
    	Connection conn = null;
    	try {
    		Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return conn;
    }
    
    /**
     * 关闭数据库的连接
     * @param conn Connection对象
     * @param ps PreparedStatement对象
     * @param rs ResultSet对象
     */
    public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs){
    	if (conn != null){
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	if (ps != null){
    		try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	if (rs != null){
    		try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
}
