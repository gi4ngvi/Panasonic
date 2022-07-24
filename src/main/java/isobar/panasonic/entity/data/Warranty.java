package isobar.panasonic.entity.data;

public class Warranty {
    String productCategory;
    String productName;
    String productSKU;
    String warrantyCardNumber;
    String serialNumber;
    String dateOfPurchase;
    String supportedDocument;
    String promotionCode;
    int rewardPointBonus;

    public Warranty(Builder builder) {
        this.productCategory = builder.productCategory;
        this.productName = builder.productName;
        this.productSKU = builder.productSKU;
        this.warrantyCardNumber = builder.warrantyCardNumber;
        this.serialNumber = builder.serialNumber;
        this.dateOfPurchase = builder.dateOfPurchase;
        this.supportedDocument = builder.supportedDocument;
        this.promotionCode = builder.promotionCode;
        this.rewardPointBonus = builder.rewardPointBonus;
    }

    public int getRewardPointBonus() {
        return rewardPointBonus;
    }

    public void setRewardPointBonus(int rewardPointBonus) {
        this.rewardPointBonus = rewardPointBonus;
    }

    public void setProductSKU(String productSKU) {
        this.productSKU = productSKU;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getSupportedDocument() {
        return supportedDocument;
    }

    public String getWarrantyCardNumber() {
        return warrantyCardNumber;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setSupportedDocument(String supportedDocument) {
        this.supportedDocument = supportedDocument;
    }

    public String getProductSKU() {
        return productSKU;
    }

    public void setWarrantyCardNumber(String warrantyCardNumber) {
        this.warrantyCardNumber = warrantyCardNumber;
    }

    public static class Builder {
        String productCategory;
        String productName;
        String productSKU;
        String warrantyCardNumber;
        String serialNumber;
        String dateOfPurchase;
        String supportedDocument;
        String promotionCode;
        int rewardPointBonus;

        public Builder productCategory(String productCategory) {
            this.productCategory = productCategory;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder productSKU(String productSKU) {
            this.productSKU = productSKU;
            return this;
        }

        public Builder warrantyCardNumber(String warrantyCardNumber) {
            this.warrantyCardNumber = warrantyCardNumber;
            return this;
        }

        public Builder serialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
            return this;
        }

        public Builder promotionCode(String promotionCode) {
            this.promotionCode = promotionCode;
            return this;
        }

        public Builder supportedDocument(String supportedDocument) {
            this.supportedDocument = supportedDocument;
            return this;
        }

        public Builder dateOfPurchase(String dateOfPurchase) {
            this.dateOfPurchase = dateOfPurchase;
            return this;
        }

        public Builder rewardPointBonus(int rewardPointBonus) {
            this.rewardPointBonus = rewardPointBonus;
            return this;
        }

        public Warranty build() {
            return new Warranty(this);
        }
    }
}
