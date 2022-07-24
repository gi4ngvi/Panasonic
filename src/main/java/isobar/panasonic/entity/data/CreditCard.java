package isobar.panasonic.entity.data;

public class CreditCard {
    private String creditCardName;
    private String creditCardNumber;
    private String expMonth;
    private String expYear;
    private String securityCode;
    private String password3D;

    public CreditCard(Builder builder) {
        this.creditCardName = builder.creditCardName;
        this.creditCardNumber = builder.creditCardNumber;
        this.expMonth = builder.expMonth;
        this.expYear = builder.expYear;
        this.securityCode = builder.securityCode;
        this.password3D = builder.password3D;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public String getPassword3D() {
        return password3D;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public void setPassword3D(String password3D) {
        this.password3D = password3D;
    }

    public static class Builder {
        private String creditCardName;
        private String creditCardNumber;
        private String expMonth;
        private String expYear;
        private String securityCode;
        private String password3D;

        public Builder creditCardName(String creditCardName) {
            this.creditCardName = creditCardName;
            return this;
        }

        public Builder creditCardNumber(String creditCardNumber) {
            this.creditCardNumber = creditCardNumber;
            return this;
        }

        public Builder expMonth(String expMonth) {
            this.expMonth = expMonth;
            return this;
        }

        public Builder expYear(String expYear) {
            this.expYear = expYear;
            return this;
        }

        public Builder securityCode(String securityCode) {
            this.securityCode = securityCode;
            return this;
        }

        public Builder password3D(String password3D) {
            this.password3D = password3D;
            return this;
        }

        public CreditCard build() {
            return new CreditCard(this);
        }
    }
}
