package mySQLConnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import quan_li_san_pham.products.Books;
import quan_li_san_pham.products.Clothes;
import quan_li_san_pham.products.Electronics;
import quan_li_san_pham.products.Product;

import java.sql.*;

public class SQLProduct {
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

    public static ObservableList<Product> getDatabase(){
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Product> list = FXCollections.observableArrayList();
        try{
            connection = DBConnect();
            String sql = "select product.*, book.*, clothes.*, electronics.* from product " +
                    "left join book on book.product_id = product.product_id " +
                    "left join clothes on clothes.product_id = product.product_id " +
                    "left join electronics on electronics.product_id = product.product_id ";
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                String id = result.getString("product_id");
                String name = result.getString("product_name");
                int price = Integer.parseInt(result.getString("product_price"));
                int quantity = Integer.parseInt(result.getString("product_quantity"));
                String note = result.getString("product_note");
                String type = result.getString("product_type");

                if(type.equals("Books")){
                    String author = result.getString("author");
                    String publisher = result.getString("publisher");
                    String language = result.getString("language");
                    int numPage = Integer.parseInt(result.getString("num_page"));

                    Product p = new Books(id,name,price,quantity,note,type,author,publisher,language,numPage);
                    list.add(p);
                }
                if(type.equals("Clothes")){
                    String brand = result.getString("clothes.brand");
                    String size = result.getString("size");
                    String style = result.getString("style");
                    String material = result.getString("material");
                    String gender = result.getString("gender");

                    Product p = new Clothes(id,name,price,quantity,note,type,brand,size, style,material, gender);
                    list.add(p);
                }
                if(type.equals("Electronics")){
                    String brand = result.getString("electronics.brand");
                    String model = result.getString("model");
                    String period = result.getString("warranty_period");
                    Product p = new Electronics(id,name,price,quantity,note,type,brand,model,period);
                    list.add(p);
                }

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
        return list;
    }
    public static void insertProduct(Product product){
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        PreparedStatement statement4 = null;

        try{
            connection = DBConnect();
            String sql = "insert into product values(?,?,?,?,?,?);";
            statement1 = connection.prepareStatement(sql);
            statement1.setString(1,product.getProductId());
            statement1.setString(2,product.getProductName());
            statement1.setInt(3,product.getProductPrice());
            statement1.setInt(4,product.getProductQuantity());
            statement1.setString(5,product.getProductNote());
            statement1.setString(6,product.getProductType());
            statement1.executeUpdate();

            if(product instanceof Books res){
                sql = "insert into book values(?,?,?,?,?)";
                statement2 = connection.prepareStatement(sql);
                statement2.setString(1, res.getProductId());
                statement2.setString(2, res.getAuthor());
                statement2.setString(3, res.getPublisher());
                statement2.setString(4, res.getLanguage());
                statement2.setInt(5,res.getNumPage());
                statement2.executeUpdate();
            }
            if(product instanceof Clothes res){
                sql = "insert into clothes values(?,?,?,?,?,?)";
                statement3 = connection.prepareStatement(sql);
                statement3.setString(1, res.getProductId());
                statement3.setString(2, res.getBrand());
                statement3.setString(3, res.getSize());
                statement3.setString(4, res.getStyle());
                statement3.setString(5, res.getMaterial());
                statement3.setString(6, res.getGender());
                statement3.executeUpdate();
            }
            if(product instanceof Electronics res){
                sql = "insert into electronics values(?,?,?,?)";
                statement4 = connection.prepareStatement(sql);
                statement4.setString(1, res.getProductId());
                statement4.setString(2, res.getBrand());
                statement4.setString(3, res.getModel());
                statement4.setString(4, res.getWarrantyPeriod());
                statement4.executeUpdate();
            }
        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(statement1 != null) statement1.close();
                if(statement2 != null) statement2.close();
                if(statement3 != null) statement3.close();
                if(statement4 != null) statement4.close();
                if(connection != null) connection.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
    }

    public static void deteteProduct(Product product){
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        PreparedStatement statement4 = null;
        PreparedStatement statement5 = null;
        PreparedStatement statement6 = null;
        try{
            connection = DBConnect();

            String sql1 = "set foreign_key_checks = 0";
            statement1 = connection.prepareStatement(sql1);
            statement1.executeUpdate();

            String sql2 = "delete from product where product_id = ?";
            statement2 = connection.prepareStatement(sql2);
            statement2.setString(1,product.getProductId());
            statement2.executeUpdate();

            if(product instanceof Books){
                String sql3 = "delete from book where product_id = ?";
                statement3 = connection.prepareStatement(sql3);
                statement3.setString(1,product.getProductId());
                statement3.executeUpdate();
            }
            if(product instanceof Clothes){
                String sql4 = "delete from clothes where product_id = ?";
                statement4 = connection.prepareStatement(sql4);
                statement4.setString(1,product.getProductId());
                statement4.executeUpdate();
            }
            if(product instanceof Electronics){
                String sql5 = "delete from electronics where product_id = ?";
                statement5 = connection.prepareStatement(sql5);
                statement5.setString(1,product.getProductId());
                statement5.executeUpdate();
            }

            String sql6 = "set foreign_key_checks = 1";
            statement6 = connection.prepareStatement(sql6);
            statement6.executeUpdate();

        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null) connection.close();
                if(statement1 != null) connection.close();
                if(statement2 != null) connection.close();
                if(statement3 != null) connection.close();
                if(statement4 != null) connection.close();
                if(statement5 != null) connection.close();
                if(statement6 != null) connection.close();

            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
    }
    public static void UpdateProduct(Product product){
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        PreparedStatement statement4 = null;
        PreparedStatement statement5  = null;
        PreparedStatement statement6 = null;

        try{
            connection = DBConnect();
            String sql1 =  "set foreign_key_checks = 0";
            statement1 = connection.prepareStatement(sql1);
            statement1.executeUpdate();

            String sql2 = "update product set product_name = ?, " +
                    "product_price = ?, product_quantity = ?, product_note = ? " +
                    "where product_id = ?";

            statement2 = connection.prepareStatement(sql2);
            statement2.setString(1, product.getProductName());
            statement2.setInt(2,product.getProductPrice());
            statement2.setInt(3,product.getProductQuantity());
            statement2.setString(4,product.getProductNote());
            statement2.setString(5,product.getProductId());
            statement2.executeUpdate();

            if(product instanceof Books res) {
                String sql3 = "update book set author = ?, publisher = ?, " +
                        "language = ?, num_page = ? where product_id = ?";
                statement3 = connection.prepareStatement(sql3);
                statement3.setString(1, res.getAuthor());
                statement3.setString(2, res.getPublisher());
                statement3.setString(3, res.getLanguage());
                statement3.setInt(4, res.getNumPage());
                statement3.setString(5, res.getProductId());
                statement3.executeUpdate();
            }
            else if(product instanceof Clothes res){

                String sql4 = "update clothes set brand = ?, size = ?, " +
                        "style = ?, material = ?, gender = ? where product_id = ?";
                statement4 = connection.prepareStatement(sql4);
                statement4.setString(1,res.getBrand());
                statement4.setString(2,res.getSize());
                statement4.setString(3,res.getStyle());
                statement4.setString(4,res.getMaterial());
                statement4.setString(5,res.getGender());
                statement4.setString(6,res.getProductId());
                statement4.executeUpdate();

            }
            else if(product instanceof Electronics res){

                String sql5 = "update electronics set brand = ?, " +
                        "model = ?, warranty_period = ? where product_id = ?";
                statement5 = connection.prepareStatement(sql5);
                statement5.setString(1,res.getBrand());
                statement5.setString(2,res.getModel());
                statement5.setString(3,res.getWarrantyPeriod());
                statement5.setString(4,res.getProductId());
                statement5.executeUpdate();
            }
            String sql6 = "set foreign_key_checks = 1";
            statement6 = connection.prepareStatement(sql6);
            statement6.executeUpdate();

        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null) connection.close();
                if(statement1 != null) statement1.close();
                if(statement2 != null) statement2.close();
                if(statement3 != null) statement3.close();
                if(statement4 != null) statement4.close();
                if(statement5 != null) statement5.close();
                if(statement6 != null) statement6.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }

    }
    public static int TotalQuantityInStock(){
        Connection connection = null;
        PreparedStatement statement = null;
        int sum = 0;
        try{
            connection = DBConnect();
            String sql = "select sum(product_quantity) as 'quantity' from product";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                sum = resultSet.getInt("quantity");
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
        return sum;
    }
}
