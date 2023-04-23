package quan_li_san_pham.productController;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import mySQLConnect.SQLProduct;
import quan_li_san_pham.products.Books;
import quan_li_san_pham.products.Clothes;
import quan_li_san_pham.products.Electronics;
import quan_li_san_pham.products.Product;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ProductAddController implements Initializable {
    @FXML
    private TextField addProductNameField;
    @FXML
    private TextField addProductPriceField;
    @FXML
    private TextField addProductQuantityField;
    @FXML
    private TextField addProductNoteField;

    @FXML
    private ChoiceBox<String> addProductChoiceBox;
    @FXML
    private AnchorPane addBooksAnchorPane;
    @FXML
    private AnchorPane addClothesAnchorPane;
    @FXML
    private AnchorPane addElectronicsAnchorPane;
    @FXML
    private TextField addElectBrandField;
    @FXML
    private TextField addElectModelField;
    @FXML
    private TextField addElectWPField;
    @FXML
    private TextField addClothesBrandField;
    @FXML
    private TextField addClothesSizeField;
    @FXML
    private TextField addClothesStyleField;
    @FXML
    private TextField addClothesMaterialField;
    @FXML
    private RadioButton radioNam;
    @FXML
    private RadioButton radioNu;
    @FXML
    private TextField addBooksAuthorField;
    @FXML
    private TextField addBooksPublisherField;
    @FXML
    private TextField addBooksLanguageField;
    @FXML
    private TextField addBooksNumPageField;


    private final String[] listType = {"Books", "Clothes", "Electronics"};
    private Product newProduct;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductChoiceBox.getItems().addAll(listType);
        addBooksAnchorPane.setVisible(false);
        addClothesAnchorPane.setVisible(false);
        addElectronicsAnchorPane.setVisible(false);
        addProductChoiceBox.setOnAction(this::getType);

    }
    public void getType(ActionEvent event){
        String type = addProductChoiceBox.getValue();
        if(type.equals("Books")){
            addBooksAnchorPane.setVisible(true);
            addClothesAnchorPane.setVisible(false);
            addElectronicsAnchorPane.setVisible(false);
        }
        if(type.equals("Clothes")){
            addBooksAnchorPane.setVisible(false);
            addClothesAnchorPane.setVisible(true);
            addElectronicsAnchorPane.setVisible(false);
        }
        if(type.equals("Electronics")){
            addBooksAnchorPane.setVisible(false);
            addClothesAnchorPane.setVisible(false);
            addElectronicsAnchorPane.setVisible(true);
        }
    }

    private void resetField(){
        addProductNameField.setText("");
        addProductPriceField.setText("");
        addProductQuantityField.setText("");
        addProductNoteField.setText("");
        addProductChoiceBox.getSelectionModel().select("");

        addBooksAuthorField.setText("");
        addBooksPublisherField.setText("");
        addBooksLanguageField.setText("");
        addBooksNumPageField.setText("");
        addBooksAnchorPane.setVisible(false);

        addClothesBrandField.setText("");
        addClothesSizeField.setText("");
        addClothesStyleField.setText("");
        addClothesMaterialField.setText("");
        radioNam.setSelected(false);
        radioNu.setSelected(false);
        addClothesAnchorPane.setVisible(false);

        addElectBrandField.setText("");
        addElectModelField.setText("");
        addElectWPField.setText("");
        addElectronicsAnchorPane.setVisible(false);

    }
    public void cancelHandle()  {
       ProductController.instance.setAnchorPaneVisible();
       resetField();
    }

    public void addHandle(){
        String type = addProductChoiceBox.getValue();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
         if(type == null){
            alert.setHeaderText("Chưa chọn loại sản phẩm");
            alert.showAndWait();
            return;
        }
        else if(type.equals("Books")){

            addBooksAnchorPane.setVisible(true);
            addClothesAnchorPane.setVisible(false);
            addElectronicsAnchorPane.setVisible(false);

            Books res = new Books();
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String product_id = "PB" + formattedDate;
            res.setProductId(product_id);

            if(addProductNameField.getText().equals("")){
                alert.setHeaderText("Chưa nhập tên");
                alert.showAndWait();
                return;
            }else
                res.setProductName(addProductNameField.getText());

            if(addProductNoteField.getText().equals("")){
                alert.setHeaderText("Chưa nhập ghi chú");
                alert.showAndWait();
                return;
            }else
                res.setProductNote(addProductNoteField.getText());

            res.setProductType(type);

            if(addProductPriceField.getText().equals("")){
                alert.setHeaderText("Chưa nhập giá");
                alert.showAndWait();
                return;
            }else{
                int price;
                try{
                    price = Integer.parseInt(addProductPriceField.getText());
                    if(price < 0){
                        alert.setHeaderText("Giá không được < 0");
                        alert.showAndWait();
                        return;
                    }else{
                      res.setProductPrice(price);
                    }
                }catch (NumberFormatException e){
                    alert.setHeaderText("Giá không hợp lệ");
                    alert.showAndWait();
                    return;
                }
            }
             if(addProductQuantityField.getText().equals("")){
                 alert.setHeaderText("Chưa nhập số lượng");
                 alert.showAndWait();
                 return;
             }else{
                 int quantity;
                 try{
                     quantity = Integer.parseInt(addProductQuantityField.getText());
                     if(quantity < 0){
                         alert.setHeaderText("Số lượng không được < 0");
                         alert.showAndWait();
                         return;
                     }else{
                         res.setProductQuantity(quantity);
                     }
                 }catch (NumberFormatException e){
                     alert.setHeaderText("Số lượng không hợp lệ");
                     alert.showAndWait();
                     return;
                 }
             }
            if(addBooksAuthorField.getText().equals("")){
                alert.setHeaderText("Chưa nhập tác giả");
                alert.showAndWait();
                return;
            }else
                res.setAuthor(addBooksAuthorField.getText());
            if(addBooksPublisherField.getText().equals("")){
                alert.setHeaderText("Chưa nhập nhà xuất bản");
                alert.showAndWait();
                return;
            }else
                res.setPublisher(addBooksPublisherField.getText());
            if(addBooksLanguageField.getText().equals("")){
                alert.setHeaderText("Chưa nhập bản dịch sách");
                alert.showAndWait();
                return;
            }else
                 res.setLanguage(addBooksLanguageField.getText());

            if(addBooksNumPageField.getText().equals("")){
                 alert.setHeaderText("Chưa nhập số trang");
                 alert.showAndWait();
                 return;
            }else{
                 int numPage;
                 try{
                     numPage = Integer.parseInt(addBooksNumPageField.getText());
                     if(numPage < 0){
                         alert.setHeaderText("Số lượng trang không được < 0");
                         alert.showAndWait();
                         return;
                     }else{
                         res.setNumPage(numPage);
                     }
                 }catch (NumberFormatException e){
                     alert.setHeaderText("Số lượng trang không hợp lệ");
                     alert.showAndWait();
                     return;
                 }
            }
            newProduct = res;
        }
        else if(type.equals("Clothes")){
            addBooksAnchorPane.setVisible(false);
            addClothesAnchorPane.setVisible(true);
            addElectronicsAnchorPane.setVisible(false);

            Clothes res = new Clothes();
             LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
             String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
             String product_id = "PC" + formattedDate;
             res.setProductId(product_id);

             if(addProductNameField.getText().equals("")){
                 alert.setHeaderText("Chưa nhập tên");
                 alert.showAndWait();
                 return;
             }else
                 res.setProductName(addProductNameField.getText());

             if(addProductNoteField.getText().equals("")){
                 alert.setHeaderText("Chưa nhập ghi chú");
                 alert.showAndWait();
                 return;
             }else
                 res.setProductNote(addProductNoteField.getText());

             res.setProductType(type);

             if(addProductPriceField.getText().equals("")){
                 alert.setHeaderText("Chưa nhập giá");
                 alert.showAndWait();
                 return;
             }else{
                 int price;
                 try{
                     price = Integer.parseInt(addProductPriceField.getText());
                     if(price < 0){
                         alert.setHeaderText("Giá không được < 0");
                         alert.showAndWait();
                         return;
                     }else{
                         res.setProductPrice(price);
                     }
                 }catch (NumberFormatException e){
                     alert.setHeaderText("Giá không hợp lệ");
                     alert.showAndWait();
                     return;
                 }
             }
             if(addProductQuantityField.getText().equals("")){
                 alert.setHeaderText("Chưa nhập số lượng");
                 alert.showAndWait();
                 return;
             }else{
                 int quantity;
                 try{
                     quantity = Integer.parseInt(addProductQuantityField.getText());
                     if(quantity < 0){
                         alert.setHeaderText("Số lượng không được < 0");
                         alert.showAndWait();
                         return;
                     }else{
                         res.setProductQuantity(quantity);
                     }
                 }catch (NumberFormatException e){
                     alert.setHeaderText("Số lượng không hợp lệ");
                     alert.showAndWait();
                     return;
                 }
             }
            if(addClothesBrandField.getText().equals("")){
                alert.setHeaderText("Chưa nhập thương hiệu");
                alert.showAndWait();
                return;
            }else
                res.setBrand(addClothesBrandField.getText());
            if(addClothesSizeField.getText().equals("")){
                alert.setHeaderText("Chưa nhập size");
            }else
                res.setSize(addClothesSizeField.getText());
            if(addClothesStyleField.getText().equals("")){
                alert.setHeaderText("Chưa nhập style");
                alert.showAndWait();
                return;
            }else
                res.setStyle(addClothesStyleField.getText());

            if(addClothesMaterialField.getText().equals("")){
                alert.setHeaderText("Chưa nhập chất liệu");
                alert.showAndWait();
                return;
            }else
                res.setMaterial(addClothesMaterialField.getText());

            if(radioNam.isSelected() && !radioNu.isSelected())
                res.setGender("Nam");
            else if(!radioNam.isSelected() && radioNu.isSelected())
                res.setGender("Nữ");
            else if(radioNam.isSelected() && radioNu.isSelected()){
                alert.setHeaderText("Chỉ được chọn 1 trong 2 giới tính");
                alert.showAndWait();
                return;
            }
            else{
                alert.setHeaderText("Chưa chọn giới tính");
                alert.showAndWait();
                return;
            }
            newProduct = res;
        }
        else if(type.equals("Electronics")){
            addBooksAnchorPane.setVisible(false);
            addClothesAnchorPane.setVisible(false);
            addElectronicsAnchorPane.setVisible(true);

            Electronics res = new Electronics();
             LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
             String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
             String product_id = "PE" + formattedDate;
             res.setProductId(product_id);

             if(addProductNameField.getText().equals("")){
                 alert.setHeaderText("Chưa nhập tên");
                 alert.showAndWait();
                 return;
             }else
                 res.setProductName(addProductNameField.getText());

             if(addProductNoteField.getText().equals("")){
                 alert.setHeaderText("Chưa nhập ghi chú");
                 alert.showAndWait();
                 return;
             }else
                 res.setProductNote(addProductNoteField.getText());

             res.setProductType(type);

             if(addProductPriceField.getText().equals("")){
                 alert.setHeaderText("Chưa nhập giá");
                 alert.showAndWait();
                 return;
             }else{
                 int price;
                 try{
                     price = Integer.parseInt(addProductPriceField.getText());
                     if(price < 0){
                         alert.setHeaderText("Giá không được < 0");
                         alert.showAndWait();
                         return;
                     }else{
                         res.setProductPrice(price);
                     }
                 }catch (NumberFormatException e){
                     alert.setHeaderText("Giá không hợp lệ");
                     alert.showAndWait();
                     return;
                 }
             }
             if(addProductQuantityField.getText().equals("")){
                 alert.setHeaderText("Chưa nhập số lượng");
                 alert.showAndWait();
                 return;
             }else{
                 int quantity;
                 try{
                     quantity = Integer.parseInt(addProductQuantityField.getText());
                     if(quantity < 0){
                         alert.setHeaderText("Số lượng không được < 0");
                         alert.showAndWait();
                         return;
                     }else{
                         res.setProductQuantity(quantity);
                     }
                 }catch (NumberFormatException e){
                     alert.setHeaderText("Số lượng không hợp lệ");
                     alert.showAndWait();
                     return;
                 }
             }
            if(addElectBrandField.getText().equals("")){
                alert.setHeaderText("Chưa nhập thương hiệu");
                alert.showAndWait();
                return;
            }else
                res.setBrand(addElectBrandField.getText());
            if(addElectModelField.getText().equals("")){
                alert.setHeaderText("Chưa nhập model");
                alert.showAndWait();
                return;
            }else
                res.setModel(addElectModelField.getText());

            if(addElectWPField.getText().equals("")){
                alert.setHeaderText("Chưa nhập thời gian bảo hành");
                alert.showAndWait();
                return;
            }else
                res.setWarrantyPeriod(addElectWPField.getText());
            newProduct = res;
        }

            SQLProduct.insertProduct(newProduct);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("ADD");
            alert1.setHeaderText("Thêm sản phẩm thành công");
            alert1.showAndWait();
            ProductController.instance.refreshTable();
            ProductController.instance.setAnchorPaneVisible();
            resetField();
    }
}
