//package com.commucation.demo.util;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.Scanner;
//
//public class Conn {
//
//    //数据库预加载信息
//    static String driverClass="oracle.jdbc.driver.OracleDriver"; //oracle的驱动
//    //连接oracle路径方式 ”orcl“是要建立连接的数据库名 1521端口
//
//    public static Connection getConnection() throws ClassNotFoundException, SQLException, FileNotFoundException, IllegalAccessException, InstantiationException {
//
//        String url="";
//        String user = "";
//        String password = "";
//        File file =new File("D:/programfiles/important/info.txt");
//        Scanner input=new Scanner(file);
//        while(input.hasNext()){
//            url = input.nextLine();
//            user = input.nextLine();
//            password = input.nextLine();
//        }
//        input.close();
//        Connection connection = null;
//
//        Class.forName(driverClass).newInstance();
//        connection = DriverManager.getConnection(url, user, password);
//
//        System.out.println("Oracle数据库连接成功，用户："+ user);
//
//        return connection;
//    }
//
//}