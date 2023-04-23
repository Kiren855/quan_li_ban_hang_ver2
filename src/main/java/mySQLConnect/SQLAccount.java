package mySQLConnect;

import java.sql.*;

public class SQLAccount {
    public static Connection DBConnect(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quan_li_ban_hang","root", "096270");
        }catch (ClassNotFoundException | SQLException e){
            e.fillInStackTrace();
        }
        return connection;
    }

    public static boolean checkAccount(String username, String pass){
        boolean check = true;
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = DBConnect();
            String sql = "select * from account where username = ? and password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2,pass);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next())
                check = false;

        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null)
                    connection.close();
                if(statement != null)
                    statement.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
        return check;
    }
    public static boolean checkAccount(String user){
        Connection connection = null;
        PreparedStatement statement = null;
        boolean check = false;
        try{
            connection = DBConnect();
            String sql = "select * from account where username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,user);
            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.next()){
                check = true;
            }

        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null) connection.close();
                if(statement != null) statement.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
        return check;
    }

    public static void insertAccount(String user, String pass){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DBConnect();
            String sql = "insert into account(username,password) values(?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,user);
            statement.setString(2,pass);

            statement.executeUpdate();

        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null) connection.close();
                if(statement != null) statement.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
    }
}
