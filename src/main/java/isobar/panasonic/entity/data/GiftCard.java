package isobar.panasonic.entity.data;

public class GiftCard {
    String name;
    boolean redeemable;
    String website;
    double balance;

    public GiftCard(Builder builder) {
        this.name = builder.name;
        this.redeemable = builder.redeemable;
        this.website = builder.website;
        this.balance = builder.balance;
    }

    public String getName() {
        return name;
    }

    public boolean isRedeemable() {
        return redeemable;
    }

    public String getWebsite() {
        return website;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("Gift name: %s, Website: %s, Balance: %s", getName(), getWebsite(), getBalance());
    }

    public static class Builder {
        String name;
        boolean redeemable;
        String website;
        double balance;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder redeemable(boolean redeemable) {
            this.redeemable = redeemable;
            return this;
        }

        public Builder website(String website) {
            this.website = website;
            return this;
        }

        public Builder balance(double balance) {
            this.balance = balance;
            return this;
        }


        public GiftCard build() {
            return new GiftCard(this);
        }
    }
}
