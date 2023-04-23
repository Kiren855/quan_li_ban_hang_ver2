package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mySQLConnect.SQLAccount;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private AnchorPane login;
    @FXML
    private AnchorPane register;
    @FXML
    private TextField registerUser;
    @FXML
    private PasswordField registerPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login.setVisible(true);
        register.setVisible(false);
    }

    public void loginHandle(ActionEvent event) throws IOException {
        if(SQLAccount.checkAccount(usernameField.getText(), passwordField.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText("Đăng Nhập thành công");
            if(alert.showAndWait().get() == ButtonType.OK){
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/quan_li_ban_hang/Form.fxml")));
                stage.setScene(new Scene(root));
                stage.show();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().add(buttonType);
            alert.setTitle("Login");
            alert.setHeaderText("Thông tin username hoặc password không chính xác");
            alert.setContentText("Hãy nhập lại thông tin chính xác");
            alert.showAndWait();
        }
    }
    public void registerHandle() {
        login.setVisible(false);
        register.setVisible(true);
    }

    public void RegisterButton(){
        if(SQLAccount.checkAccount(registerUser.getText())){
            SQLAccount.insertAccount(registerUser.getText(),registerPass.getText());
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("ĐĂNG KÝ");
            alert1.setHeaderText("Đăng kí tài khoản thành công");
            alert1.showAndWait();
            login.setVisible(true);
            register.setVisible(false);

        }else{
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("ERROR");
            alert2.setHeaderText("Tài khoản đã tồn tại");
            alert2.showAndWait();
        }
    }
    public void ButtonBack(){
        login.setVisible(true);
        register.setVisible(false);
    }
}
