package isobar.panasonic.entity.product;

public abstract class Product {
    protected String name;
    protected int price;
    protected String sku;
    protected String id;
    protected String category;
    protected ProductType type;
    protected String url;
    protected int qty;

    protected Product(Builder<?> builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.sku = builder.sku;
        this.id = builder.id;
        this.category = builder.category;
        this.url = builder.url;
        this.qty = builder.qty;
        setProductType();
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Product price(int price) {
        this.price = price;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public Product sku(String sku) {
        this.sku = sku;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public Product category(String category) {
        this.category = category;
        return this;
    }

    public ProductType getType() {
        return type;
    }

    public Product type(ProductType type) {
        this.type = type;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Product url(String url) {
        this.url = url;
        return this;
    }

    public int getQty() {
        return qty;
    }

    public Product qty(int qty) {
        this.qty = qty;
        return this;
    }

    protected abstract void setProductType();

    public static abstract class Builder<T extends Builder<T>> {
        protected String name;
        protected int price;
        protected String sku;
        protected String id;
        protected String category;
        protected String url;
        protected int qty;

        protected abstract T self();

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T price(int price) {
            this.price = price;
            return self();
        }

        public T sku(String sku) {
            this.sku = sku;
            return self();
        }

        public T id(String id) {
            this.id = id;
            return self();
        }

        public T category(String category) {
            this.category = category;
            return self();
        }

        public T url(String url) {
            this.url = url;
            return self();
        }

        public T qty(int qty) {
            this.qty = qty;
            return self();
        }
    }
}
