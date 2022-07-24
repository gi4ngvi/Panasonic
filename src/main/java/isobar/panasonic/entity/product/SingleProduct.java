package isobar.panasonic.entity.product;

public class SingleProduct extends Product {

    public SingleProduct() {
        super();
    }

    public static abstract class Builder<T extends Builder<T>> extends Product.Builder<T> {
        public SingleProduct build() {
            return new SingleProduct(this);
        }
    }

    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }

    public static Builder<?> builder() {
        return new Builder2();
    }

    protected SingleProduct(Builder<?> builder) {
        super(builder);
    }

    @Override
    protected void setProductType() {
        type = ProductType.SINGLE;
    }
}
