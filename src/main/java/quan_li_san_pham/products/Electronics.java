package quan_li_san_pham.products;

import java.io.Serializable;
public class Electronics extends Product implements Serializable {
    private String brand;
    private String model;
    private String warrantyPeriod;
    public Electronics(){}
    public Electronics(String id, String name, int price, int quantity, String note, String type, String brand, String model, String warrantyPeriod){
        super(id,name,price,quantity,note,type);
        this.brand = brand;
        this.model = model;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public Product Copy() {
        return new Electronics(this.productId, this.productName, this.productPrice,
                this.productQuantity, this.productNote, this.productType,
                this.brand, this.model, this.warrantyPeriod);
    }
}
