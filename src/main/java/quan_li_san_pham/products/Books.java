package quan_li_san_pham.products;

import java.io.Serializable;

public class Books extends Product implements Serializable {
    private String author;
    private String publisher;
    private String language;
    private int numPage;

    public Books(String id, String name, int price,int quantity, String note ,String type,String author, String publisher, String language, int page) {
        super(id,name,price,quantity,note,type);
        this.author = author;
        this.publisher = publisher;
        this.language = language;
        this.numPage = page;
    }
    public Books(){}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getNumPage() {return numPage;}

    public void setNumPage(int page) {
        this.numPage = page;
    }

    @Override
    public Product Copy() {
        return new Books(this.productId,this.productName,this.productPrice,
                this.productQuantity,this.productNote,this.productType,
                this.author,this.publisher,this.language,this.numPage);
    }
}
