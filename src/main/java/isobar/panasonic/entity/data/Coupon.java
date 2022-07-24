package isobar.panasonic.entity.data;

public class Coupon {
    String name;
    String code;
    String conditions;
    String rule;
    int discount_amount;

    public Coupon(Builder builder) {
        this.name = builder.name;
        this.code = builder.code;
        this.conditions = builder.conditions;
        this.rule = builder.rule;
        this.discount_amount = builder.discount_amount;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getConditions() {
        return conditions;
    }

    public String getRule() {
        return rule;
    }

    public int getDiscount_amount() {
        return discount_amount;
    }

    public static class Builder {
        String name;
        String code;
        String conditions;
        String rule;
        int discount_amount;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder conditions(String conditions) {
            this.conditions = conditions;
            return this;
        }

        public Builder rule(String rule) {
            this.rule = rule;
            return this;
        }

        public Builder discount_amount(int discount_amount) {
            this.discount_amount = discount_amount;
            return this;
        }

        public Coupon build() {
            return new Coupon(this);
        }
    }
}

