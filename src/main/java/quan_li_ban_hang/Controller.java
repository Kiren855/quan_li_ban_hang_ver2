package quan_li_ban_hang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane quanliProduct;
    @FXML
    private AnchorPane quanliOrder;
    @FXML
    private AnchorPane quanliThongKe;
    @FXML
    private AnchorPane homeForm;
    @FXML
    private Button qlProducts;
    @FXML
    private Button qlOrders;
    @FXML
    private Button home;
    @FXML
    private Button qlThongKe;
    @FXML
    private Button logout;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quanliProduct.setVisible(false);
        quanliOrder.setVisible(false);
        homeForm.setVisible(true);
        quanliThongKe.setVisible(false);
        home.setStyle("-fx-background-color: linear-gradient(to bottom right, #c6eceb, #90c0bd)");
        qlProducts.setStyle("-fx-background-color: transparent");
        qlOrders.setStyle("-fx-background-color: transparent");
        qlThongKe.setStyle("-fx-background-color: transparent");
        logout.setStyle("-fx-background-color: transparent");
    }
    public void switchProduct(){
        quanliProduct.setVisible(true);
        quanliOrder.setVisible(false);
        homeForm.setVisible(false);
        quanliThongKe.setVisible(false);
        qlProducts.setStyle("-fx-background-color: linear-gradient(to bottom right, #c6eceb, #90c0bd)");
        qlOrders.setStyle("-fx-background-color: transparent");
        qlThongKe.setStyle("-fx-background-color: transparent");
        home.setStyle("-fx-background-color: transparent");
        logout.setStyle("-fx-background-color: transparent");
    }
    public void switchOrder(){
        quanliProduct.setVisible(false);
        quanliOrder.setVisible(true);
        homeForm.setVisible(false);
        quanliThongKe.setVisible(false);
        qlOrders.setStyle("-fx-background-color: linear-gradient(to bottom right, #c6eceb, #90c0bd)");
        qlProducts.setStyle("-fx-background-color: transparent");
        home.setStyle("-fx-background-color: transparent");
        qlThongKe.setStyle("-fx-background-color: transparent");
        logout.setStyle("-fx-background-color: transparent");
    }
    public void switchHome(){
        quanliProduct.setVisible(false);
        quanliOrder.setVisible(false);
        homeForm.setVisible(true);
        quanliThongKe.setVisible(false);
        home.setStyle("-fx-background-color: linear-gradient(to bottom right, #c6eceb, #90c0bd)");
        qlProducts.setStyle("-fx-background-color: transparent");
        qlOrders.setStyle("-fx-background-color: transparent");
        qlThongKe.setStyle("-fx-background-color: transparent");
        logout.setStyle("-fx-background-color: transparent");
    }

    public void switchTK(){
        quanliProduct.setVisible(false);
        quanliOrder.setVisible(false);
        homeForm.setVisible(false);
        quanliThongKe.setVisible(true);
        qlThongKe.setStyle("-fx-background-color: linear-gradient(to bottom right, #c6eceb, #90c0bd)");
        qlProducts.setStyle("-fx-background-color: transparent");
        home.setStyle("-fx-background-color: transparent");
        qlOrders.setStyle("-fx-background-color: transparent");
        logout.setStyle("-fx-background-color: transparent");
    }
    public void logOut(ActionEvent event) throws IOException {

        logout.setStyle("-fx-background-color: linear-gradient(to bottom right, #c6eceb, #90c0bd)");
        qlProducts.setStyle("-fx-background-color: transparent");
        home.setStyle("-fx-background-color: transparent");
        qlOrders.setStyle("-fx-background-color: transparent");
        qlThongKe.setStyle("-fx-background-color: transparent");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LOGOUT");
        alert.setHeaderText("Bạn có muốn đăng xuất");
        if(alert.showAndWait().get() == ButtonType.OK){
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login/Login.fxml")));
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
