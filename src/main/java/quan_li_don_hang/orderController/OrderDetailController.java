package quan_li_don_hang.orderController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import quan_li_don_hang.orders.Orders;
import quan_li_san_pham.products.Product;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OrderDetailController implements Initializable {
    public static OrderDetailController instance;
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
    private TextField OrderIDField;
    @FXML
    private TextField OrderDateField;
    @FXML
    private TextField OrderStatusField;

    @FXML
    private TextField CustomerIDField;
    @FXML
    private TextField NameField;
    @FXML
    private TextField EmailField;
    @FXML
    private TextField PhoneField;
    @FXML
    private TextField AddressField;


    private final ObservableList<Product> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        listProduct.setItems(list);

        Name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        Price.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

    }
    public void init(Orders select){
        OrderIDField.setText(select.getOrderID());
        String Date = select.getOrderDate().format(DateTimeFormatter.ofPattern("hh:mm:ss, dd/MM/yyyy"));
        OrderDateField.setText(Date);
        OrderStatusField.setText(select.getOrderStatus());
        CustomerIDField.setText(select.getCustomer().getCustomerID());
        NameField.setText(select.getCustomer().getCustomerName());
        EmailField.setText(select.getCustomer().getCustomerEmail());
        PhoneField.setText(select.getCustomer().getCustomerPhone());
        AddressField.setText(select.getCustomer().getCustomerAddress());
        totalQuantityLabel.setText(String.valueOf(select.getTotalQuantity()));
        totalAmountLabel.setText(String.valueOf(select.getOrderPrice()));
        list.clear();
        list.addAll(select.getProduct());
        listProduct.setItems(list);
        listProduct.refresh();
    }
    public void ConfirmHandle(){
        OrderController.instance.setAnchorPaneVisible();
    }
}
