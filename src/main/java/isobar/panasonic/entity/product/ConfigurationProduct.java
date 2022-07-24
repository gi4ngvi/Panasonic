package isobar.panasonic.entity.product;

public class ConfigurationProduct extends Product {
    private String color;
    private String subSKU;

    protected ConfigurationProduct(Builder<?> builder) {
        super(builder);
        this.color = builder.color;
        this.subSKU = builder.subSKU;
    }

    public static Builder<?> builder() {
        return new Builder2();
    }

    public String getColor() {
        return color;
    }

    public String getSubSKU() {
        return subSKU;
    }

    @Override
    protected void setProductType() {
        type = ProductType.CONFIGURATION;
    }

    public static abstract class Builder<T extends Builder<T>> extends Product.Builder<T> {
        private String color;
        private String subSKU;

        public T color(String color) {
            this.color = color;
            return self();
        }

        public T subSKU(String subSKU) {
            this.subSKU = subSKU;
            return self();
        }

        public ConfigurationProduct build() {
            return new ConfigurationProduct(this);
        }
    }

    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }
}
