package quan_li_don_hang.orders;
import quan_li_san_pham.products.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Orders {
    private String orderID;
    private Customers customer;
    private ArrayList<Product> product;
    private LocalDateTime orderDate;
    private int totalQuantity;
    private int orderPrice;
    private String orderStatus;
    public Orders(){
        product = new ArrayList<>();
    }

    public Orders(String orderID, Customers customer, ArrayList<Product> product, LocalDateTime orderDate, int totalQuantity, int orderPrice, String status) {
        this.orderID = orderID;
        this.customer = customer;
        this.product = product;
        this.orderDate = orderDate;
        this.totalQuantity = totalQuantity;
        this.orderPrice = orderPrice;
        this.orderStatus = status;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }

    public void addProduct(Product product){
        this.product.add(product);
    }
    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
