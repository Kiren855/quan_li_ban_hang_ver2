package quan_li_thong_ke;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import mySQLConnect.SQLOrder;
import mySQLConnect.SQLProduct;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    private BarChart<String,Double> bieuDo;
    @FXML
    private Spinner<Integer> spinnerYear;
    @FXML
    private Label tongDonHang;
    @FXML
    private Label tongSp;
    @FXML
    private Label tongDB;
    @FXML
    private Label tongCL;
    @FXML
    private Label sachDaBan;
    @FXML
    private Label aoDaBan;
    @FXML
    private Label dienDaBan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Double> data = new XYChart.Series<>();
        data.setName("Doanh thu");

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2022,2100);
        valueFactory.setValue(2023);
        spinnerYear.setValueFactory(valueFactory);
        Map<Integer,Double> list = SQLOrder.getRevenue(2023);

       for(int i = 1;i <= 12;i++){
           data.getData().add(new XYChart.Data<>(String.valueOf(i),list.get(i)));
       }

        bieuDo.getData().add(data);
    }
    public void confirmTK(){
        XYChart.Series<String, Double> data = new XYChart.Series<>();
        data.setName("Doanh thu");
        Map<Integer,Double> list = SQLOrder.getRevenue(spinnerYear.getValue());

        for(int i = 1;i <= 12;i++){
            data.getData().add(new XYChart.Data<>(String.valueOf(i),list.get(i)));
        }
        bieuDo.getData().clear();
        bieuDo.getData().add(data);

        tongDonHang.setText(String.valueOf(SQLOrder.getTotalOrders()));
        tongCL.setText(String.valueOf(SQLProduct.TotalQuantityInStock()));
        tongDB.setText(String.valueOf(SQLOrder.getQuantitySold()));

        int CL = Integer.parseInt(tongCL.getText());
        int DB = Integer.parseInt(tongDB.getText());
        tongSp.setText(String.valueOf(CL + DB));

        Map<String,Integer> map = SQLOrder.ProductMoiLoai();
        sachDaBan.setText(String.valueOf(map.get("Books")));
        aoDaBan.setText(String.valueOf(map.get("Clothes")));
        dienDaBan.setText(String.valueOf(map.get("Electronics")));


    }
}
