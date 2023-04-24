package quan_li_don_hang.orderController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import mySQLConnect.SQLOrder;
import quan_li_don_hang.orders.Orders;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    public static OrderController instance;
    @FXML
    private TableView<Orders> orderTable;
    @FXML
    private TableColumn<Orders,String> orderID;
    @FXML
    private TableColumn<Orders,String> customerName;
    @FXML
    private TableColumn<Orders,LocalDateTime> orderDate;
    @FXML
    private TableColumn<Orders,Integer> totalQuantity;
    @FXML
    private TableColumn<Orders,Integer> orderPrice;
    @FXML
    private TableColumn<Orders,String> orderStatus;
    @FXML
    private AnchorPane qlOrder;
    @FXML
    private AnchorPane addOrder;
    @FXML
    private AnchorPane editOrder;
    @FXML
    private AnchorPane detailOrder;
    @FXML
    private TextField searchOrderField;
    private  ObservableList<Orders> ordersList = SQLOrder.getDatabase();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        qlOrder.setVisible(true);
        addOrder.setVisible(false);
        editOrder.setVisible(false);
        detailOrder.setVisible(false);
        orderTable.setItems(ordersList);
        orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        customerName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName())
        );
        orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalQuantity.setCellValueFactory(new PropertyValueFactory<>("totalQuantity"));
        orderPrice.setCellValueFactory(new PropertyValueFactory<>("orderPrice"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
    }
    public void addHandle(){
        qlOrder.setVisible(false);
        addOrder.setVisible(true);
        editOrder.setVisible(false);
        detailOrder.setVisible(false);
        OrderAddController.instance.refreshTable();
    }
    public void setAnchorPaneVisible(){
        qlOrder.setVisible(true);
        addOrder.setVisible(false);
        editOrder.setVisible(false);
        detailOrder.setVisible(false);
    }
    public void refreshTable(){
        ordersList = SQLOrder.getDatabase();
        orderTable.setItems(ordersList);
        orderTable.refresh();
    }
    public void editHandle(){
        Orders select = orderTable.getSelectionModel().getSelectedItem();
        if(select != null){
            OrderEditController.instance.init(select);
            qlOrder.setVisible(false);
            addOrder.setVisible(false);
            editOrder.setVisible(true);
            detailOrder.setVisible(false);
        }

    }
    public void deleteHandle(){
        Orders select = orderTable.getSelectionModel().getSelectedItem();
        if(select != null){
            SQLOrder.deleteOrder(select);
            ordersList = SQLOrder.getDatabase();
            orderTable.setItems(ordersList);
            orderTable.refresh();
        }
    }
    public void searchHandle(){
        String key = searchOrderField.getText().toLowerCase();
        ObservableList<Orders> tmp = ordersList.filtered(orders ->
                orders.getCustomer().getCustomerName().toLowerCase().contains(key)
                || orders.getCustomer().getCustomerID().toLowerCase().contains(key)
        );
        orderTable.setItems(tmp);
        orderTable.refresh();
    }
    public void detailHandle(){
        Orders select = orderTable.getSelectionModel().getSelectedItem();
        if(select != null){
            OrderDetailController.instance.init(select);
            detailOrder.setVisible(true);
        }
    }
}
