package isobar.panasonic.customer;

public class CustomerInformation implements Cloneable {
    String name;
    String email;
    String password;
    String passwordConfirm;
    Address shippingAddress;
    Address billingAddress;
    String firstName;
    String lastName;
    String title;
    String nickName;
    String typeOfIdentityCard;
    String identityCardNumber;
    String dateOfBirth;
    String maritalStatus;
    String race;
    boolean subscribeNewsletter;
    boolean messagingService;
    String group;

    private CustomerInformation(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.passwordConfirm = builder.passwordConfirm;
        this.shippingAddress = builder.shippingAddress;
        this.billingAddress = builder.billingAddress;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.title = builder.title;
        this.nickName = builder.nickName;
        this.typeOfIdentityCard = builder.typeOfIdentityCard;
        this.identityCardNumber = builder.identityCardNumber;
        this.dateOfBirth = builder.dateOfBirth;
        this.maritalStatus = builder.maritalStatus;
        this.subscribeNewsletter = builder.subscribeNewsletter;
        this.messagingService = builder.messagingService;
        this.race = builder.race;
        this.group = builder.group;
    }

    public String getName() {
        return name;
    }

    public CustomerInformation setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CustomerInformation setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CustomerInformation setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public CustomerInformation setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
        return this;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setShippingAddress(Address address) {
        this.shippingAddress = address;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setBillingAddress(Address address) {
        this.billingAddress = address;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public String getTypeOfIdentityCard() {
        return typeOfIdentityCard;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public void setTypeOfIdentityCard(String typeOfIdentityCard) {
        this.typeOfIdentityCard = typeOfIdentityCard;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setSubscribeNewsletter(boolean subscribeNewsletter) {
        this.subscribeNewsletter = subscribeNewsletter;
    }

    public boolean isSubscribeNewsletter() {
        return subscribeNewsletter;
    }

    public boolean isMessagingService() {
        return messagingService;
    }

    public void setMessagingService(boolean messagingService) {
        this.messagingService = messagingService;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "{Name: " + name + " - email: " + email + " - password: " + password + "}";
    }

    @Override
    public CustomerInformation clone() {
        try {
            return (CustomerInformation) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    public static class Builder {
        String name;
        String email;
        String password;
        String passwordConfirm;
        Address shippingAddress;
        Address billingAddress;
        String firstName;
        String lastName;
        String title;
        String nickName;
        String typeOfIdentityCard;
        String identityCardNumber;
        String dateOfBirth;
        String maritalStatus;
        String race;
        boolean subscribeNewsletter;
        boolean messagingService;
        String group;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder passwordConfirm(String passwordConfirm) {
            this.passwordConfirm = passwordConfirm;
            return this;
        }

        public Builder shippingAddress(Address shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public Builder billingAddress(Address billingAddress) {
            this.billingAddress = billingAddress;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder nickName(String nickName){
            this.nickName = nickName;
            return this;
        }

        public Builder typeOfIdentityCard(String typeOfIdentityCard){
            this.typeOfIdentityCard = typeOfIdentityCard;
            return this;
        }

        public Builder identityCardNumber(String identityCardNumber){
            this.identityCardNumber = identityCardNumber;
            return this;
        }

        public Builder dateOfBirth(String dateOfBirth){
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder maritalStatus(String maritalStatus){
            this.maritalStatus = maritalStatus;
            return this;
        }

        public Builder subscribeNewsletter(boolean subscribeNewsletter){
            this.subscribeNewsletter = subscribeNewsletter;
            return this;
        }

        public Builder messagingService(boolean messagingService){
            this.messagingService = messagingService;
            return this;
        }

        public Builder race(String race) {
            this.race = race;
            return this;
        }

        public Builder group(String group) {
            this.group = group;
            return this;
        }

        public CustomerInformation build() {
            return new CustomerInformation(this);
        }
    }
}