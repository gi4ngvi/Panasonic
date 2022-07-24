package isobar.panasonic.customer;

public class AuthenticalAccount {
    private String username;
    private String password;

    public static class Builder {
        private String username;
        private String password;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public AuthenticalAccount build() {
            return new AuthenticalAccount(this);
        }
    }

    public AuthenticalAccount(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
