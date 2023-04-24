package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private final Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("hmm.jpg")));
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Quản lí sản phẩm");
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.show();
        stage.setOnCloseRequest(event ->{
            event.consume();
            Logout(stage);
        });

    }
    public void Logout(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng Xuất");
        alert.setHeaderText("Bạn có muốn thoát ra khỏi đây!");

        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("Đăng xuất thành công!");
            stage.close();
        }
    }
}
