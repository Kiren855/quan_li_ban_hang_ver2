package quan_li_san_pham.productController;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import mySQLConnect.SQLProduct;
import quan_li_san_pham.products.Product;

import java.net.URL;
import java.util.ResourceBundle;


public class ProductController implements Initializable {
    public static ProductController instance;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productID;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, Integer> productPrice;
    @FXML
    private TableColumn<Product, Integer> productQuantity;
    @FXML
    private TableColumn<Product, String> productNote;
    @FXML
    private TableColumn<Product, String> productType;
    @FXML
    private AnchorPane qlProduct;
    @FXML
    private AnchorPane addProduct;
    @FXML
    private AnchorPane editProduct;
    @FXML
    private AnchorPane detailProduct;
    @FXML
    private TextField searchProductField;
    private ObservableList<Product> list = SQLProduct.getDatabase();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        qlProduct.setVisible(true);
        addProduct.setVisible(false);
        editProduct.setVisible(false);
        detailProduct.setVisible(false);
        productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productNote.setCellValueFactory(new PropertyValueFactory<>("productNote"));
        productType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        productTable.setItems(list);

    }
    public void setAnchorPaneVisible(){
        addProduct.setVisible(false);
        editProduct.setVisible(false);
        detailProduct.setVisible(false);
    }
    public void refreshTable(){
        list = SQLProduct.getDatabase();
        productTable.setItems(list);
        productTable.refresh();
    }
    public void addProductHandle(){
        addProduct.setVisible(true);
    }

    public void editProductHandle(){
        Product select = productTable.getSelectionModel().getSelectedItem();
       if(select != null){
            ProductEditController.instance.Init(select);
            editProduct.setVisible(true);
       }
    }
    public void deleteProductHandle(){
        Product select = productTable.getSelectionModel().getSelectedItem();
        SQLProduct.deteteProduct(select);
        list = SQLProduct.getDatabase();
        productTable.setItems(list);
        productTable.refresh();
    }
    public void detailProductHandle(){
        Product select = productTable.getSelectionModel().getSelectedItem();
        if(select != null){
            ProductDetailController.instance.init(select);
            detailProduct.setVisible(true);
        }
    }
    public void searchProductHandle(){
         String key = searchProductField.getText().toLowerCase();
        ObservableList<Product> tmp = list.filtered(product ->
                product.getProductName().toLowerCase().contains(key)
        );
        productTable.setItems(tmp);
        productTable.refresh();
    }

}
