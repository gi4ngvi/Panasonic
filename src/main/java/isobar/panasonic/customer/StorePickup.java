package isobar.panasonic.customer;

public class StorePickup {

    private String storeName;
    private String country;
    private String state;
    private String city;
    private String zipCode;
    private String street1;
    private String street2;
    private String street3;
    private String phoneNumber;

    public StorePickup(Builder builder) {
        this.storeName = builder.storeName;
        this.country = builder.country;
        this.state = builder.state;
        this.city = builder.city;
        this.zipCode = builder.zipCode;
        this.street1 = builder.street1;
        this.street2 = builder.street2;
        this.street3 = builder.street3;
        this.phoneNumber = builder.phoneNumber;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getStreet3() {
        return street3;
    }

    public void setStreet3(String street3) {
        this.street3 = street3;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static class Builder {
        private String storeName;
        private String country;
        private String state;
        private String city;
        private String zipCode;
        private String street1;
        private String street2;
        private String street3;
        private String phoneNumber;

        public Builder storeName(String storeName) {
            this.storeName = storeName;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder street1(String street1) {
            this.street1 = street1;
            return this;
        }

        public Builder street2(String street2) {
            this.street2 = street2;
            return this;
        }

        public Builder street3(String street3) {
            this.street3 = street3;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public StorePickup build() {
            return new StorePickup(this);
        }
    }


}
