package quan_li_san_pham.productController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import mySQLConnect.SQLProduct;
import quan_li_san_pham.products.Books;
import quan_li_san_pham.products.Clothes;
import quan_li_san_pham.products.Electronics;
import quan_li_san_pham.products.Product;
import java.net.URL;
import java.util.ResourceBundle;
public class ProductEditController implements Initializable {
    @FXML
    private TextField editProductNameField;
    @FXML
    private TextField editProductPriceField;
    @FXML
    private TextField editProductQuantityField;
    @FXML
    private TextField editProductNoteField;
    @FXML
    private AnchorPane editBooksAnchorPane;
    @FXML
    private AnchorPane editClothesAnchorPane;
    @FXML
    private AnchorPane editElectAnchorPane;
    @FXML
    private TextField editElectBrandField;
    @FXML
    private TextField editElectModelField;
    @FXML
    private TextField editElectWPField;
    @FXML
    private TextField editClothesBrandField;
    @FXML
    private TextField editClothesSizeField;
    @FXML
    private TextField editClothesStyleField;
    @FXML
    private TextField editClothesMaterialField;
    @FXML
    private RadioButton radioNam;
    @FXML
    private RadioButton radioNu;
    @FXML
    private TextField editBooksAuthorField;
    @FXML
    private TextField editBooksPublisherField;
    @FXML
    private TextField editBooksLanguageField;
    @FXML
    private TextField editBooksNumPageField;
    public static ProductEditController instance;
    private Product selectP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        editBooksAnchorPane.setVisible(false);
        editClothesAnchorPane.setVisible(false);
        editElectAnchorPane.setVisible(false);
    }
    public void Init(Product select){
            this.selectP = select;
            editProductNameField.setText(select.getProductName());
            editProductPriceField.setText(String.valueOf(select.getProductPrice()));
            editProductQuantityField.setText(String.valueOf(select.getProductQuantity()));
            editProductNoteField.setText(select.getProductNote());

            switch (select.getProductType()) {
                case "Books" -> {
                    Books res = (Books) select;
                    editBooksAuthorField.setText(res.getAuthor());
                    editBooksPublisherField.setText(res.getPublisher());
                    editBooksLanguageField.setText(res.getLanguage());
                    editBooksNumPageField.setText(String.valueOf(res.getNumPage()));
                    editBooksAnchorPane.setVisible(true);
                    editClothesAnchorPane.setVisible(false);
                    editElectAnchorPane.setVisible(false);
                }
                case "Clothes" -> {
                    Clothes res = (Clothes) select;
                    editClothesBrandField.setText(res.getBrand());
                    editClothesSizeField.setText(res.getSize());
                    editClothesStyleField.setText(res.getStyle());
                    editClothesMaterialField.setText(res.getMaterial());
                    if(res.getGender().equals("Nam")){
                        radioNam.setSelected(true);
                        radioNu.setSelected(false);
                    }
                    if(res.getGender().equals("Nữ")){
                        radioNu.setSelected(true);
                        radioNam.setSelected(false);
                    }
                    editBooksAnchorPane.setVisible(false);
                    editClothesAnchorPane.setVisible(true);
                    editElectAnchorPane.setVisible(false);
                }
                case "Electronics" -> {
                    Electronics res = (Electronics) select;
                    editElectBrandField.setText(res.getBrand());
                    editElectModelField.setText(res.getModel());
                    editElectWPField.setText(res.getWarrantyPeriod());
                    editBooksAnchorPane.setVisible(false);
                    editClothesAnchorPane.setVisible(false);
                    editElectAnchorPane.setVisible(true);
                }
            }
        }
    private void resetAnchorPane(){
        editBooksAnchorPane.setVisible(false);
        editClothesAnchorPane.setVisible(false);
        editElectAnchorPane.setVisible(false);
    }
    public void confirmHandle(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");

        if(selectP.getProductType().equals("Books")){
            Books editProduct = new Books();

            editProduct.setProductId(selectP.getProductId());

            if(editProductNameField.getText().equals("")){
                alert.setHeaderText("Không được để trống tên");
                alert.showAndWait();
                return;
            }else
                editProduct.setProductName(editProductNameField.getText());

            if(editProductNoteField.getText().equals("")){
                alert.setHeaderText("Không được để trống ghi chú");
                alert.showAndWait();
                return;
            }else
                editProduct.setProductNote(editProductNoteField.getText());

            editProduct.setProductType(selectP.getProductType());

            if(editProductPriceField.getText().equals("")){
                alert.setHeaderText("Giá không được để trống");
                alert.showAndWait();
                return;
            }else{
                int price;
                try{
                    price = Integer.parseInt(editProductPriceField.getText());
                    if(price < 0){
                        alert.setHeaderText("Giá không được < 0");
                        alert.showAndWait();
                        return;
                    }else{
                        editProduct.setProductPrice(price);
                    }
                }catch (NumberFormatException e){
                    alert.setHeaderText("Giá không hợp lệ");
                    alert.showAndWait();
                    return;
                }
            }
            if(editProductQuantityField.getText().equals("")){
                alert.setHeaderText("Không được để trống số lượng");
                alert.showAndWait();
                return;
            }else{
                int quantity;
                try{
                    quantity = Integer.parseInt(editProductQuantityField.getText());
                    if(quantity < 0){
                        alert.setHeaderText("Số lượng không được < 0");
                        alert.showAndWait();
                        return;
                    }else{
                        editProduct.setProductQuantity(quantity);
                    }
                }catch (NumberFormatException e){
                    alert.setHeaderText("Số lượng không hợp lệ");
                    alert.showAndWait();
                    return;
                }
            }
            if(editBooksAuthorField.getText().equals("")){
                alert.setHeaderText("Không được để trống tác giả");
                alert.showAndWait();
                return;
            }else
                editProduct.setAuthor(editBooksAuthorField.getText());

            if(editBooksPublisherField.getText().equals("")){
                alert.setHeaderText("Không được để trống nhà xuất bản");
                alert.showAndWait();
                return;
            }else
                editProduct.setPublisher(editBooksPublisherField.getText());

            if(editBooksLanguageField.getText().equals("")){
                alert.setHeaderText("Không được để trống ngôn ngữ");
                alert.showAndWait();
                return;
            }else
                editProduct.setLanguage(editBooksLanguageField.getText());

            if(editBooksNumPageField.getText().equals("")){
                alert.setHeaderText("Không được để trống số trang");
                alert.showAndWait();
                return;
            }else{
                int numPage;
                try{
                    numPage = Integer.parseInt(editProductQuantityField.getText());
                    if(numPage < 0){
                        alert.setHeaderText("Số lượng trang không được < 0");
                        alert.showAndWait();
                        return;
                    }else{
                        editProduct.setNumPage(numPage);
                    }
                }catch (NumberFormatException e){
                    alert.setHeaderText("Số lượng trang không hợp lệ");
                    alert.showAndWait();
                    return;
                }
            }
            selectP = editProduct;
        }
        if(selectP.getProductType().equals("Clothes")){
            Clothes editProduct = new Clothes();
            editProduct.setProductId(selectP.getProductId());

            if(editProductNameField.getText().equals("")){
                alert.setHeaderText("Không được để trống tên");
                alert.showAndWait();
                return;
            }else
                editProduct.setProductName(editProductNameField.getText());

            if(editProductNoteField.getText().equals("")){
                alert.setHeaderText("Không được để trống ghi chú");
                alert.showAndWait();
                return;
            }else
                editProduct.setProductNote(editProductNoteField.getText());

            editProduct.setProductType(selectP.getProductType());

            if(editProductPriceField.getText().equals("")){
                alert.setHeaderText("Giá không được để trống");
                alert.showAndWait();
                return;
            }else{
                int price;
                try{
                    price = Integer.parseInt(editProductPriceField.getText());
                    if(price < 0){
                        alert.setHeaderText("Giá không được < 0");
                        alert.showAndWait();
                        return;
                    }else{
                        editProduct.setProductPrice(price);
                    }
                }catch (NumberFormatException e){
                    alert.setHeaderText("Giá không hợp lệ");
                    alert.showAndWait();
                    return;
                }
            }
            if(editProductQuantityField.getText().equals("")){
                alert.setHeaderText("Không được để trống số lượng");
                alert.showAndWait();
                return;
            }else{
                int quantity;
                try{
                    quantity = Integer.parseInt(editProductQuantityField.getText());
                    if(quantity < 0){
                        alert.setHeaderText("Số lượng không được < 0");
                        alert.showAndWait();
                        return;
                    }else{
                        editProduct.setProductQuantity(quantity);
                    }
                }catch (NumberFormatException e){
                    alert.setHeaderText("Số lượng không hợp lệ");
                    alert.showAndWait();
                    return;
                }
            }
            if(editClothesBrandField.getText().equals("")){
                alert.setHeaderText("Không được để trống Brand");
                alert.showAndWait();
                return;
            }else
                editProduct.setBrand(editClothesBrandField.getText());

            if(editClothesSizeField.getText().equals("")){
                alert.setHeaderText("Không được để trống size");
                alert.showAndWait();
                return;
            }else
                editProduct.setSize(editClothesSizeField.getText());

            if(editClothesStyleField.getText().equals("")){
                alert.setHeaderText("Không được để trống Style");
                alert.showAndWait();
                return;
            }else
                editProduct.setStyle(editClothesStyleField.getText());

            if(editClothesMaterialField.getText().equals("")){
                alert.setHeaderText("Không được để trống Material");
                alert.showAndWait();
                return;
            }else
                editProduct.setMaterial(editClothesMaterialField.getText());

            if(radioNam.isSelected() && !radioNu.isSelected())
                editProduct.setGender("Nam");
            else if(!radioNam.isSelected() && radioNu.isSelected())
                editProduct.setGender("Nữ");
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

            selectP = editProduct;
        }

        if(selectP.getProductType().equals("Electronics")){
            Electronics editProduct = new Electronics();

            if(editProductNameField.getText().equals("")){
                alert.setHeaderText("Không được để trống tên");
                alert.showAndWait();
                return;
            }else
                editProduct.setProductName(editProductNameField.getText());

            if(editProductNoteField.getText().equals("")){
                alert.setHeaderText("Không được để trống ghi chú");
                alert.showAndWait();
                return;
            }else
                editProduct.setProductNote(editProductNoteField.getText());

            editProduct.setProductType(selectP.getProductType());

            if(editProductPriceField.getText().equals("")){
                alert.setHeaderText("Giá không được để trống");
                alert.showAndWait();
                return;
            }else{
                int price;
                try{
                    price = Integer.parseInt(editProductPriceField.getText());
                    if(price < 0){
                        alert.setHeaderText("Giá không được < 0");
                        alert.showAndWait();
                        return;
                    }else{
                        editProduct.setProductPrice(price);
                    }
                }catch (NumberFormatException e){
                    alert.setHeaderText("Giá không hợp lệ");
                    alert.showAndWait();
                    return;
                }
            }
            if(editProductQuantityField.getText().equals("")){
                alert.setHeaderText("Không được để trống số lượng");
                alert.showAndWait();
                return;
            }else{
                int quantity;
                try{
                    quantity = Integer.parseInt(editProductQuantityField.getText());
                    if(quantity < 0){
                        alert.setHeaderText("Số lượng không được < 0");
                        alert.showAndWait();
                        return;
                    }else{
                        editProduct.setProductQuantity(quantity);
                    }
                }catch (NumberFormatException e){
                    alert.setHeaderText("Số lượng không hợp lệ");
                    alert.showAndWait();
                    return;
                }
            }
            if(editElectBrandField.getText().equals("")){
                alert.setHeaderText("Không được để trống Brand");
                alert.showAndWait();
                return;
            }else
                editProduct.setBrand(editElectBrandField.getText());
            if(editElectModelField.getText().equals("")){
                alert.setHeaderText("Không được để trống Model");
                alert.showAndWait();
                return;
            }else
                editProduct.setModel(editElectModelField.getText());

            if(editElectWPField.getText().equals("")){
                alert.setHeaderText("Không được để trống thời gian bảo hành");
                alert.showAndWait();
                return;
            }else
                editProduct.setWarrantyPeriod(editElectWPField.getText());

            selectP = editProduct;
        }

        SQLProduct.UpdateProduct(selectP);
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Edit");
        alert1.setHeaderText("Sửa thành công");
        alert1.showAndWait();
        ProductController.instance.refreshTable();
        ProductController.instance.setAnchorPaneVisible();

    }
    public void cancelHandle(){
        resetAnchorPane();
        ProductController.instance.setAnchorPaneVisible();
    }
}
