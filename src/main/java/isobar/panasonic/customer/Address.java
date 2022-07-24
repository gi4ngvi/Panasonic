package isobar.panasonic.customer;

public class Address {
    String name;
    String company;
    String city;
    String district;
    String ward;
    String street;
    String telephone;
    String country;
    String state;
    String firstName;
    String lastName;
    String zip;
    String mobileNumber;
    String addressName;

    public Address(Builder builder) {
        this.name = builder.name;
        this.company = builder.company;
        this.city = builder.city;
        this.district = builder.district;
        this.ward = builder.ward;
        this.street = builder.street;
        this.telephone = builder.telephone;
        this.country = builder.country;
        this.state = builder.state;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.zip = builder.zip;
        this.mobileNumber = builder.mobileNumber;
        this.addressName = builder.addressName;
    }

    public String getName() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void company(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void city(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void district(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void ward(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTelephone() {
        return telephone;
    }

    public void telephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address country(String coutry) {
        this.country = coutry;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public static class Builder {
        String name;
        String company;
        String city;
        String district;
        String ward;
        String street;
        String telephone;
        String country;
        String state;
        String firstName;
        String lastName;
        String zip;
        String mobileNumber;
        String addressName;

        public Builder addressName(String addressName){
            this.addressName = addressName;
            return this;
        }

        public Builder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder company(String company) {
            this.company = company;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder district(String district) {
            this.district = district;
            return this;
        }

        public Builder ward(String ward) {
            this.ward = ward;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder state(String state){
            this.state = state;
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

        public Builder zip(String zip) {
            this.zip = zip;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
