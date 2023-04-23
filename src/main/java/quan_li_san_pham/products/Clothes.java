package quan_li_san_pham.products;

import java.io.Serializable;

public class Clothes extends Product implements Serializable {
    private String brand;
    private String size;
    private String style;

    private String material;
    private String Gender;
    public Clothes(){}
    public Clothes(String id, String name, int price, int quantity, String note,String type,String brand, String size,String style, String material, String gender){
        super(id,name,price,quantity,note,type);
        this.style = style;
        this.size = size;
        this.material = material;
        this.Gender = gender;
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public Product Copy() {
        return new Clothes(this.productId, this.productName, this.productPrice,
                this.productQuantity,this.productNote,this.productType,
                this.brand,this.size,this.style,this.material,this.Gender);
    }
}
