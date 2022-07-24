package isobar.panasonic.entity.product;

import java.util.ArrayList;

public class BundleProduct extends Product {
    private ArrayList<SingleProduct> singleProducts;

    public BundleProduct() {
        super();
    }

    public static abstract class Builder<T extends Builder<T>> extends Product.Builder<T> {
        private ArrayList<SingleProduct> singleProducts;
        private String optionTitle;
        private double optionPrice;

        public T listSingleProducts(ArrayList<SingleProduct> singleProducts) {
            this.singleProducts = singleProducts;
            return self();
        }

        public BundleProduct build() {
            return new BundleProduct(this);
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

    protected BundleProduct(Builder<?> builder) {
        super(builder);
        this.singleProducts = builder.singleProducts;
    }

    @Override
    protected void setProductType() {
        type = ProductType.BUNDLE;
    }

    public ArrayList<SingleProduct> getListSingleProducts() {
        return singleProducts;
    }

    public void setSingleProducts(ArrayList<SingleProduct> singleProducts) {
        this.singleProducts = singleProducts;
    }
}
