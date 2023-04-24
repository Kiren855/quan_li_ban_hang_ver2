package mySQLConnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import quan_li_don_hang.orders.Customers;
import quan_li_don_hang.orders.Orders;
import quan_li_san_pham.products.Books;
import quan_li_san_pham.products.Clothes;
import quan_li_san_pham.products.Electronics;
import quan_li_san_pham.products.Product;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SQLOrder {
    private static Connection DBConnect(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quan_li_ban_hang","root", "096270");
        }catch (ClassNotFoundException | SQLException e){
            e.fillInStackTrace();
        }
        return connection;
    }
    public static ObservableList<Orders> getDatabase(){
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Orders> list = FXCollections.observableArrayList();
        Map<String,Orders> ordersMap = new HashMap<>();
        try{
            connection = DBConnect();
            String sql = """
                    select orders.*, customers.*, orderdetails.*, product.* , book.*, clothes.* , electronics.* from orders
                    inner join customers  on orders.customer_id = customers.customer_id
                    inner join orderdetails  on orders.order_id = orderdetails.order_id
                    inner join product on orderdetails.product_id = product.product_id
                    left join book on product.product_id = book.product_id
                    left join clothes on product.product_id = clothes.product_id
                    left join electronics  on product.product_id = electronics.product_id""";
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                String orderID = result.getString("orders.order_id");
                Orders order = ordersMap.get(orderID);

                if(order == null){
                    order = new Orders();
                    order.setOrderID(orderID);
                    order.setOrderDate(result.getTimestamp("order_date").toLocalDateTime());
                    order.setTotalQuantity(result.getInt("total_quantity"));
                    order.setOrderPrice(result.getInt("order_price"));
                    order.setOrderStatus(result.getString("status"));

                    Customers customer = new Customers();
                    customer.setCustomerID(result.getString("customer_id"));
                    customer.setCustomerName(result.getString("customer_name"));
                    customer.setCustomerEmail(result.getString("customer_email"));
                    customer.setCustomerPhone(result.getString("customer_phone"));
                    customer.setCustomerAddress(result.getString("customer_address"));
                    order.setCustomer(customer);

                    ordersMap.put(orderID, order);
                }
                String id = result.getString("product_id");
                String name = result.getString("product_name");
                int price = Integer.parseInt(result.getString("product_price"));
                int quantity = Integer.parseInt(result.getString("quantity"));
                String note = result.getString("product_note");
                String type = result.getString("product_type");

                if(type.equals("Books")){
                    String author = result.getString("author");
                    String publisher = result.getString("publisher");
                    String language = result.getString("language");
                    int numPage = Integer.parseInt(result.getString("num_page"));

                    Product p = new Books(id,name,price,quantity,note,type,author,publisher,language,numPage);
                    ordersMap.get(orderID).getProduct().add(p);
                }
                if(type.equals("Clothes")){
                    String brand = result.getString("clothes.brand");
                    String size = result.getString("size");
                    String style = result.getString("style");
                    String material = result.getString("material");
                    String gender = result.getString("gender");

                    Product p = new Clothes(id,name,price,quantity,note,type,brand,size, style,material, gender);
                    ordersMap.get(orderID).getProduct().add(p);
                }
                if(type.equals("Electronics")){
                    String brand = result.getString("electronics.brand");
                    String model = result.getString("model");
                    String period = result.getString("warranty_period");
                    Product p = new Electronics(id,name,price,quantity,note,type,brand,model,period);
                    ordersMap.get(orderID).getProduct().add(p);
                }

            }
            list.addAll(ordersMap.values());
        }
        catch (SQLException e){
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
    public static boolean checkCustID(Customers customers){
        Connection connection = null;
        PreparedStatement statement = null;
        boolean check = false;
        try{
            connection = DBConnect();
            String sql = "select * from customers where customer_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,customers.getCustomerID());
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next())
                check = true;

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

    private static void insertProduct(String orderID, Product product){
         Connection connection = null;
         PreparedStatement statement1 = null;
         PreparedStatement statement2 = null;
         PreparedStatement statement3 = null;

         try{
             connection = DBConnect();
             String sql1 = "set foreign_key_checks = 0 ";
             statement1 = connection.prepareStatement(sql1);
             statement1.executeUpdate();

             String sql2 = "insert into orderdetails(order_id, product_id, quantity, product_price) " +
                           "values(?,?,?,?) ";

             statement2 = connection.prepareStatement(sql2);
             statement2.setString(1,orderID);
             statement2.setString(2,product.getProductId());
             statement2.setInt(3,product.getProductQuantity());
             statement2.setInt(4,product.getProductPrice());
             statement2.executeUpdate();

             String sql3 = "set foreign_key_checks = 1 ";
             statement3 = connection.prepareStatement(sql3);
             statement3.executeUpdate();
         }catch (SQLException e){
             e.fillInStackTrace();
         }finally {
             try{
                 if(connection != null) connection.close();
                 if(statement1 != null) statement1.close();
                 if(statement2 != null) statement2.close();
                 if(statement3 != null) statement3.close();
             }catch (SQLException e){
                 e.fillInStackTrace();
             }
         }
    }
    public static void insertOrder(Orders orders){

        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        PreparedStatement statement4 = null;
        try{
            connection = DBConnect();
            String sql1 = "set foreign_key_checks = 0";
            statement1 = connection.prepareStatement(sql1);
            statement1.executeUpdate();

            String sql2 = "insert into orders " +
                          "values(?,?,?,?,?,?)";
            statement2 = connection.prepareStatement(sql2);
            statement2.setString(1,orders.getOrderID());
            statement2.setString(2,orders.getCustomer().getCustomerID());
            Timestamp timestamp = Timestamp.valueOf(orders.getOrderDate());
            statement2.setTimestamp(3,timestamp);
            statement2.setInt(4,orders.getTotalQuantity());
            statement2.setInt(5,orders.getOrderPrice());
            statement2.setString(6,orders.getOrderStatus());
            statement2.executeUpdate();
            Customers customers = orders.getCustomer();

            if(checkCustID(customers)){
                String sql3 = "insert into customers " +
                              "values(?,?,?,?,?)";
                statement3 = connection.prepareStatement(sql3);
                statement3.setString(1,customers.getCustomerID());
                statement3.setString(2,customers.getCustomerName());
                statement3.setString(3,customers.getCustomerEmail());
                statement3.setString(4,customers.getCustomerPhone());
                statement3.setString(5,customers.getCustomerAddress());
                statement3.executeUpdate();
            }

            for(Product x : orders.getProduct()){
                insertProduct(orders.getOrderID(),x);
            }

            String sql4 = "set foreign key_checks = 1";
            statement4 = connection.prepareStatement(sql4);
            statement4.executeUpdate();



        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null) connection.close();
                if(statement1 != null) statement1.close();
                if(statement2 != null) statement2.close();
                if(statement3 != null) statement3.close();
                if(statement4 != null) statement4.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
    }

    public static void updateOrder(Orders orders, String customerID){

        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        PreparedStatement statement4 = null;
        PreparedStatement statement5 = null;
        try{
            connection = DBConnect();
            String sql1 = "set foreign_key_checks = 0";
            statement1 = connection.prepareStatement(sql1);
            statement1.executeUpdate();

            String sql2 = "update orders set customer_id = ?, total_quantity = ?, " +
                    " order_price = ?, status = ? where order_id = ?";

            statement2 = connection.prepareStatement(sql2);
            statement2.setString(1,orders.getCustomer().getCustomerID());
            statement2.setInt(2,orders.getTotalQuantity());
            statement2.setInt(3,orders.getOrderPrice());
            statement2.setString(4,orders.getOrderStatus());
            statement2.setString(5,orders.getOrderID());
            statement2.executeUpdate();

            if(!checkCustID(orders.getCustomer()) && !orders.getCustomer().getCustomerID().equals(customerID)){
                String sql3 = "update customers set customer_name = ?, " +
                        "customer_email = ?, customer_phone = ?, customer_address = ? where customer_id = ?";
                statement3 = connection.prepareStatement(sql3);
                statement3.setString(1,orders.getCustomer().getCustomerName());
                statement3.setString(2,orders.getCustomer().getCustomerEmail());
                statement3.setString(3,orders.getCustomer().getCustomerPhone());
                statement3.setString(4,orders.getCustomer().getCustomerAddress());
                statement3.setString(5,orders.getCustomer().getCustomerID());
                statement3.executeUpdate();
            }
            else if(!checkCustID(orders.getCustomer()) && orders.getCustomer().getCustomerID().equals(customerID)){
                String sql3 = "update customers set customer_name = ?, " +
                        "customer_email = ?, customer_phone = ?, customer_address = ? where customer_id = ?";
                statement3 = connection.prepareStatement(sql3);
                statement3.setString(1,orders.getCustomer().getCustomerName());
                statement3.setString(2,orders.getCustomer().getCustomerEmail());
                statement3.setString(3,orders.getCustomer().getCustomerPhone());
                statement3.setString(4,orders.getCustomer().getCustomerAddress());
                statement3.setString(5,customerID);
                statement3.executeUpdate();
            }
            else {
                String sql3 = "insert into customers " +
                        "values(?,?,?,?,?)";
                statement3 = connection.prepareStatement(sql3);
                statement3.setString(1,orders.getCustomer().getCustomerID());
                statement3.setString(2,orders.getCustomer().getCustomerName());
                statement3.setString(3,orders.getCustomer().getCustomerEmail());
                statement3.setString(4,orders.getCustomer().getCustomerPhone());
                statement3.setString(5,orders.getCustomer().getCustomerAddress());
                statement3.executeUpdate();
            }

            String sql4 = "delete from orderdetails where order_id = ?";
            statement4 = connection.prepareStatement(sql4);
            statement4.setString(1,orders.getOrderID());
            statement4.executeUpdate();

            for (Product product : orders.getProduct()) {
                String sql5 = "INSERT INTO orderdetails(order_id, product_id, quantity, product_price) " +
                              "VALUES (?, ?, ?, ?) ";
                PreparedStatement statement = connection.prepareStatement(sql5);
                statement.setString(1, orders.getOrderID());
                statement.setString(2, product.getProductId());
                statement.setInt(3, product.getProductQuantity());
                statement.setInt(4, product.getProductPrice());
                statement.executeUpdate();
            }

            String sql6 = "set foreign_key_checks = 1";
            statement5 = connection.prepareStatement(sql6);
            statement5.executeUpdate();

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
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }

    }

    public static void deleteOrder(Orders order){
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        PreparedStatement statement4 = null;
        try{
            connection = DBConnect();
            String sql1 = "set foreign_key_checks = 0";
            statement1 = connection.prepareStatement(sql1);
            statement1.executeUpdate();

            String sql2 = "delete from orders where order_id = ?";
            statement2 = connection.prepareStatement(sql2);
            statement2.setString(1,order.getOrderID());
            statement2.executeUpdate();

            String sql3 = "delete from orderdetails where order_id = ?";
            statement3 = connection.prepareStatement(sql3);
            statement3.setString(1,order.getOrderID());
            statement3.executeUpdate();

            String sql4 = "set foreign_key_checks = 1";
            statement4 = connection.prepareStatement(sql4);
            statement4.executeUpdate();

        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try{
                if(connection != null) connection.close();
                if(statement1 != null) statement1.close();
                if(statement2 != null) statement2.close();
                if(statement3 != null) statement3.close();
                if(statement4 != null) statement4.close();
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
    }
    public static Map<Integer,Double> getRevenue(int year){
        Connection connection = null;
        PreparedStatement statement = null;
        Map<Integer,Double> map = new LinkedHashMap<>();
        map.put(1,0.0);
        map.put(2,0.0);
        map.put(3,0.0);
        map.put(4,0.0);
        map.put(5,0.0);
        map.put(6,0.0);
        map.put(7,0.0);
        map.put(8,0.0);
        map.put(9,0.0);
        map.put(10,0.0);
        map.put(11,0.0);
        map.put(12,0.0);

        try{
            connection = DBConnect();
            String sql = "SELECT MONTH(order_date) AS month, SUM(order_price) AS revenue FROM orders " +
                    "WHERE YEAR(order_date) = ? AND MONTH(order_date) BETWEEN 1 AND 12 " +
                    "GROUP BY MONTH(order_date)";

            statement = connection.prepareStatement(sql);
            statement.setInt(1,year);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int month = resultSet.getInt("month");
                double revenue = resultSet.getDouble("revenue");
                revenue = revenue / 1000000;
                map.put(month,revenue);
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
        return map;
    }
    public static int getQuantitySold(){
        Connection connection = null;
        PreparedStatement statement = null;
        int sum = 0;
        try{
            connection = DBConnect();
            String sql = "select sum(total_quantity) as 'quantity' from orders";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                sum = resultSet.getInt("quantity");

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
    public static Map<String,Integer> ProductMoiLoai(){
        Connection connection = null;
        PreparedStatement statement = null;
        Map<String,Integer> map = new LinkedHashMap<>();
        map.put("Books", 0);
        map.put("Clothes",0);
        map.put("Electronics",0);
        try{
            connection = DBConnect();
            String sql = """
                    select o.product_type as 'product_type', sum(orderdetails.quantity) as 'quantity' from orderdetails
                    inner join product o on orderdetails.product_id = o.product_id
                    group by product_type""";

            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String id = resultSet.getString("product_type");
                int quantity = resultSet.getInt("quantity");
                map.put(id,quantity);
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
        return map;
    }
    public static int getTotalOrders(){
        Connection connection = null;
        PreparedStatement statement = null;
        int sum = 0;
        try {
            connection = DBConnect();
            String sql = "select * from orders";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                sum++;
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
