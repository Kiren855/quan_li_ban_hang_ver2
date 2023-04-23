package quan_li_san_pham.products;
    public abstract class Product  {

        protected String productId;
        protected String productName;
        protected int productPrice;
        protected int productQuantity;
        protected String productNote;
        protected String productType;

        public Product(){}

        public Product(String productId, String productName, int productPrice, int productQuantity, String productNote, String productType) {
            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
            this.productQuantity = productQuantity;
            this.productNote = productNote;
            this.productType = productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }
        public String getProductType(){
            return productType;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(int productQuantity) {
            this.productQuantity = productQuantity;
        }
        public void setProductNote(String productNote){
            this.productNote = productNote;
        }

        public String getProductNote() {
            return productNote;
        }

        public abstract Product Copy();
    }
