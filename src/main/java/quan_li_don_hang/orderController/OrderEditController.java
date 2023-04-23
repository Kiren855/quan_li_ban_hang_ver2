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
import quan_li_don_hang.orders.Orders;
import quan_li_san_pham.products.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderEditController implements Initializable {
    public static OrderEditController instance;
    @FXML
    TextField editCustomerIDField;
    @FXML
    TextField editNameField;
    @FXML
    TextField editEmailField;
    @FXML
    TextField editPhoneField;
    @FXML
    TextField editAddressField;
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
    private ObservableList<Product> listP = SQLProduct.getDatabase();
    @FXML
    private TextField quantityField;
    private Orders orders;
    @FXML
    private AnchorPane editOrder;
    @FXML
    private AnchorPane editProduct;
    @FXML
    private CheckBox CheckStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        editOrder.setVisible(true);
        editProduct.setVisible(false);
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
    private String oldID;
    void init(Orders select){
       this.orders = select;
        oldID = orders.getCustomer().getCustomerID();
        editCustomerIDField.setText(orders.getCustomer().getCustomerID());
        editNameField.setText(orders.getCustomer().getCustomerName());
        editEmailField.setText(orders.getCustomer().getCustomerEmail());
        editPhoneField.setText(orders.getCustomer().getCustomerPhone());
        editAddressField.setText(orders.getCustomer().getCustomerAddress());
        list.clear();
        list.addAll(orders.getProduct());
        listProduct.setItems(list);
        listProduct.refresh();
        totalAmountLabel.setText(String.valueOf(orders.getOrderPrice()));
        totalQuantityLabel.setText(String.valueOf(orders.getTotalQuantity()));
        CheckStatus.setSelected(!orders.getOrderStatus().equals("Chưa giao"));
        listP = SQLProduct.getDatabase();
        productTable.setItems(listP);
        productTable.refresh();

    }

    public void editHandle(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        orders.getProduct().clear();

        if(editCustomerIDField.getText().equals("")){
            alert.setHeaderText("Chưa nhập ID khách hàng");
            alert.showAndWait();
            return;
        }else
            orders.getCustomer().setCustomerID(editCustomerIDField.getText());
        if(editNameField.getText().equals("")){
            alert.setHeaderText("Chưa nhập tên");
            alert.showAndWait();
            return;
        }else
            orders.getCustomer().setCustomerName(editNameField.getText());
        if(editEmailField.getText().equals("")){
            alert.setHeaderText("Chưa nhập email");
            alert.showAndWait();
            return;
        }else
            orders.getCustomer().setCustomerEmail((editEmailField.getText()));
        if(editPhoneField.getText().equals("")){
            alert.setHeaderText("Chưa nhập số điện thoại");
            alert.showAndWait();
            return;
        }else
            orders.getCustomer().setCustomerPhone(editPhoneField.getText());
        if(editAddressField.getText().equals("")){
            alert.setHeaderText("Chưa nhập địa chỉ");
            alert.showAndWait();
            return;
        }else
            orders.getCustomer().setCustomerAddress(editAddressField.getText());

        if(list.isEmpty()) {
            alert.setHeaderText("Danh sách sản phẩm không thể trống");
            alert.showAndWait();
            return;
        }else {
            orders.getProduct().addAll(list);
        }

        orders.setTotalQuantity(Integer.parseInt(totalQuantityLabel.getText()));
        orders.setOrderPrice(Integer.parseInt(totalAmountLabel.getText()));

        if(CheckStatus.isSelected()){
            orders.setOrderStatus("Đã giao");
        }

        SQLOrder.updateOrder(orders, oldID);

        ObservableList<Product> tmp = productTable.getItems();
        for(Product x : tmp){
            SQLProduct.UpdateProduct(x);
        }
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("EDIT");
        alert1.setHeaderText("Sửa đơn hàng thành công");
        alert1.showAndWait();
        OrderController.instance.setAnchorPaneVisible();
        OrderController.instance.refreshTable();

    }
    public void cancelHandle(){
        OrderController.instance.setAnchorPaneVisible();
        OrderController.instance.refreshTable();
    }
    public void addProductHandle(){
        editOrder.setVisible(false);
        editProduct.setVisible(true);
    }
    public void deleteProductHandle(){
        try{
            Product select = listProduct.getSelectionModel().getSelectedItem();
            list.remove(select);
            int totalA = Integer.parseInt(totalAmountLabel.getText());
            int totalQ = Integer.parseInt(totalQuantityLabel.getText());
            totalAmountLabel.setText(String.valueOf(totalA - select.getProductPrice()));
            totalQuantityLabel.setText(String.valueOf(totalQ - select.getProductQuantity()));
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
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Danh sách sản phẩm rỗng");
            alert.showAndWait();
        }
    }
    public void addProductButton(){
        Product select = productTable.getSelectionModel().getSelectedItem();
        if(select != null){
            Product product = select.Copy();
            int quantity;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
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
                product.setProductQuantity(quantity);
                select.setProductQuantity(quantityInStock - quantity);
                quantityField.setText("");
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
                editOrder.setVisible(true);
                editProduct.setVisible(false);
            }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("ERROR");
                alert1.setHeaderText("Số lượng không đủ");
                alert1.showAndWait();
            }
        }
    }
    public void cancelProductButton(){
        editOrder.setVisible(true);
        editProduct.setVisible(false);
    }
}
