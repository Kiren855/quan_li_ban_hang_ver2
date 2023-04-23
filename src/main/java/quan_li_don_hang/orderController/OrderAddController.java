package quan_li_don_hang.orderController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import mySQLConnect.SQLOrder;
import mySQLConnect.SQLProduct;
import quan_li_don_hang.orders.Customers;
import quan_li_don_hang.orders.Orders;
import quan_li_san_pham.products.Product;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OrderAddController implements Initializable {

    @FXML
    TextField addCustomerIDField;
    @FXML
    TextField addNameField;
    @FXML
    TextField addEmailField;
    @FXML
    TextField addPhoneField;
    @FXML
    TextField addAddressField;
    @FXML
    private TableView<Product> listProduct;
    @FXML
    private TableColumn<Product,String> Name;
    @FXML
    private TableColumn<Product,Integer> Quantity;
    @FXML
    private TableColumn<Product,Integer> Price;
    @FXML
    private Label totalQuantityLabel;
    @FXML
    private Label totalAmountLabel;

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productID;
    @FXML
    private TableColumn<Product,String> productName;
    @FXML
    private TableColumn<Product,Integer> productPrice;
    @FXML
    private TableColumn<Product,Integer> productQuantity;
    @FXML
    private TableColumn<Product,String> productNote;
    @FXML
    private TableColumn<Product,String> productType;
    private final ObservableList<Product> list = FXCollections.observableArrayList();
    private  ObservableList<Product> listP = SQLProduct.getDatabase();
    @FXML
    private TextField quantityField;
    @FXML
    private AnchorPane addOrder;
    @FXML
    private AnchorPane addProduct;
    public static OrderAddController instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        addOrder.setVisible(true);
        addProduct.setVisible(false);
        listProduct.setItems(list);
        productTable.setItems(listP);
        Name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        Price.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productNote.setCellValueFactory(new PropertyValueFactory<>("productNote"));
        productType.setCellValueFactory(new PropertyValueFactory<>("productType"));


    }
    public void refreshTable(){
        listP = SQLProduct.getDatabase();
        productTable.setItems(listP);
        productTable.refresh();
    }
    public void reset(){
        list.clear();
        listProduct.setItems(list);
        listProduct.refresh();
        addCustomerIDField.setText("");
        addNameField.setText("");
        addEmailField.setText("");
        addPhoneField.setText("");
        addAddressField.setText("");
        totalQuantityLabel.setText("00");
        totalAmountLabel.setText("00");

    }
    public void addHandle(){
        Orders newOrder = new Orders();
        Customers customer = new Customers();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String order_id = "ORD" + formattedDate;
        newOrder.setOrderID(order_id);

        if(addCustomerIDField.getText().equals("")){
            alert.setHeaderText("Chưa nhập ID khách hàng");
            alert.showAndWait();
            return;
        }else
            customer.setCustomerID((addCustomerIDField.getText()));
        if(addNameField.getText().equals("")){
            alert.setHeaderText("Chưa nhập tên");
            alert.showAndWait();
            return;
        }else
            customer.setCustomerName(addNameField.getText());
        if(addEmailField.getText().equals("")){
            alert.setHeaderText("Chưa nhập email");
            alert.showAndWait();
            return;
        }else
            customer.setCustomerEmail((addEmailField.getText()));
        if(addPhoneField.getText().equals("")){
            alert.setHeaderText("Chưa nhập số điện thoại");
            alert.showAndWait();
            return;
        }else
            customer.setCustomerPhone(addPhoneField.getText());
        if(addAddressField.getText().equals("")){
            alert.setHeaderText("Chưa nhập địa chỉ");
            alert.showAndWait();
            return;
        }else
            customer.setCustomerAddress(addAddressField.getText());

        newOrder.setCustomer(customer);

        if(list.isEmpty()){
            alert.setHeaderText("Danh sách sản phẩm không thể trống");
            alert.showAndWait();
            return;
        }else
            newOrder.getProduct().addAll(list);

        newOrder.setOrderDate(now);

        newOrder.setTotalQuantity(Integer.parseInt(totalQuantityLabel.getText()));
        newOrder.setOrderPrice(Integer.parseInt(totalAmountLabel.getText()));
        newOrder.setOrderStatus("Chưa giao");

        SQLOrder.insertOrder(newOrder);
        ObservableList<Product> tmp = productTable.getItems();
        for(Product x : tmp){
            SQLProduct.UpdateProduct(x);
        }
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("ADD");
        alert1.setHeaderText("Thêm đơn hàng thành công");
        alert1.showAndWait();
        reset();
        OrderController.instance.setAnchorPaneVisible();
        OrderController.instance.refreshTable();
    }
    public void cancelHandle(){
        OrderController.instance.setAnchorPaneVisible();
        OrderController.instance.refreshTable();
        reset();

    }
    public void addProductHandle(){
         addOrder.setVisible(false);
         addProduct.setVisible(true);
    }
    public void deleteProductHandle(){
        Product select = listProduct.getSelectionModel().getSelectedItem();
        list.remove(select);
        int totalQ = Integer.parseInt(totalQuantityLabel.getText());
        int totalA = Integer.parseInt(totalAmountLabel.getText());
        totalQuantityLabel.setText(String.valueOf(totalQ - select.getProductQuantity()));
        totalAmountLabel.setText(String.valueOf(totalA - select.getProductPrice()));

        ObservableList<Product> tmp = productTable.getItems();
        for(Product x : tmp){
            if(x.getProductId().equals(select.getProductId())){
                x.setProductQuantity(x.getProductQuantity() + select.getProductQuantity());
            }
        }
        productTable.setItems(tmp);
        listProduct.setItems(list);
        listProduct.refresh();
        productTable.refresh();
    }
    public void addProductButton(){
        Product select = productTable.getSelectionModel().getSelectedItem();
        if(select != null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
             Product product = select.Copy();
             int quantity;
             if(quantityField.getText().equals("")){
                 alert.setHeaderText("Chưa nhập số lượng sản phẩm");
                 alert.showAndWait();
                 return;
             }else{
                 try{
                     int check = Integer.parseInt(quantityField.getText());
                     if(check > 0){
                         quantity = check;
                     }else {
                         alert.setHeaderText("Số lượng phải > 0");
                         alert.showAndWait();
                         return;
                     }
                 }catch (NumberFormatException e){
                     alert.setHeaderText("Số lương chứa kí tự không hợp lệ");
                     alert.showAndWait();
                     return;
                 }
             }

             int quantityInStock = select.getProductQuantity();
             if(quantity <= quantityInStock){
                 quantityField.setText("");
                 product.setProductQuantity(quantity);
                 select.setProductQuantity(quantityInStock - quantity);

                 for(Product x : list){
                     if(select.getProductId().equals(x.getProductId())){
                         x.setProductQuantity(x.getProductQuantity() + quantity);
                         x.setProductPrice(x.getProductPrice() + (select.getProductPrice() * quantity));
                         product = x;
                         break;
                     }
                 }

                 if(!list.contains(product)){
                     product.setProductPrice(quantity * product.getProductPrice());
                     list.add(product);
                 }
                 int totalA = Integer.parseInt(totalAmountLabel.getText());
                 int totalQ = Integer.parseInt(totalQuantityLabel.getText());

                 totalA = totalA + product.getProductPrice();
                 totalQ = totalQ + quantity;

                 totalAmountLabel.setText(String.valueOf(totalA));
                 totalQuantityLabel.setText(String.valueOf(totalQ));

                 listProduct.setItems(list);
                 listProduct.refresh();
                 productTable.refresh();
                 addOrder.setVisible(true);
                 addProduct.setVisible(false);
             }else{
                 Alert alert1 = new Alert(Alert.AlertType.ERROR);
                 alert1.setTitle("ERROR");
                 alert1.setHeaderText("Số lượng không đủ");
                 alert1.showAndWait();
             }

        }
    }
    public void cancelProductButton(){
        addOrder.setVisible(true);
        addProduct.setVisible(false);
    }
}
