package isobar.panasonic.scenarios.vn_vn;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.MyAddressPage;
import isobar.panasonic.page.fo.method.account.RegisterPage;
import isobar.panasonic.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CRUD_AddressTest extends SeleniumTest {
    private CustomerInformation cus;
    private Address address, addressEdit, defaultAddress, addressEmpty;
    private AccountInformationPage accountInformationPage;
    private RegisterPage registerPage;
    private MyAddressPage myAddressPage;
    private static final String REQUIRED_FIELD = "Đây là trường bắt buộc.";
    private static final String REQUIRED_SELECTION = "Xin vui lòng chọn một tùy chọn.";
    private static final String ADDRESS_SAVED = "Đã cập nhật địa chỉ.";
    private static final String ADDRESS_DELETED = "Bạn đã xoá các địa chỉ.";

    @Override
    protected void preCondition() {
        cus = DataTest.getRandomCustomerTest();
        defaultAddress = DataTest.getDefaultShippingAddress();
        address = new Address.Builder()
                .name("name_" + RUN_ID)
                .company("company_" + RUN_ID)
                .city("TP. Hồ Chí Minh")
                .district("12")
                .ward("Tân Chánh Hiệp")
                .street("street_" + RUN_ID)
                .telephone(RUN_ID)
                .country("Việt Nam")
                .build();
        RUN_ID += "_edited";
        addressEdit = new Address.Builder()
                .name("name_" + RUN_ID)
                .company("company_" + RUN_ID)
                .city("TP. Hồ Chí Minh")
                .district("12")
                .ward("Tân Chánh Hiệp")
                .street("street_" + RUN_ID)
                .telephone(RUN_ID)
                .country("Việt Nam")
                .build();
        addressEmpty = new Address.Builder().name("").telephone("").company("").city("Vui lòng chọn khu vực, Tỉnh hay thành phố.").district("").ward("").street("").zip("").build();
    }

    @Test()
    public void register() {
        headerPage.logout();
        registerPage = headerPage.register();
        accountInformationPage = registerPage.fillInRegistrationAndSubmit(cus);
    }

    @Test(dependsOnMethods = "register")
    public void addDefaultAddress() {
        myAddressPage = accountInformationPage.manageAddresses();
        myAddressPage.fillInAddress(defaultAddress);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getSuccessMsg(), ADDRESS_SAVED);
        Assert.assertTrue(myAddressPage.checkDefaultShippingAddress(defaultAddress));
        Assert.assertTrue(myAddressPage.checkDefaultBillingAddress(defaultAddress));
    }

    @Test(dependsOnMethods = "addDefaultAddress", alwaysRun = true)
    public void addEmptyAddress() {
        accountInformationPage = headerPage.gotoAccountInfo();
        myAddressPage = accountInformationPage.manageAddresses();
        myAddressPage.addNewAddress();
        myAddressPage.fillInAddress(addressEmpty);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getFullNameError(), REQUIRED_FIELD);
        Assert.assertEquals(myAddressPage.getTelephoneError(), REQUIRED_FIELD);
        Assert.assertEquals(myAddressPage.getCityError(), REQUIRED_SELECTION);
        Assert.assertEquals(myAddressPage.getStreetError(), REQUIRED_FIELD);
        Assert.assertEquals(myAddressPage.getWardError(), REQUIRED_FIELD);
        Assert.assertEquals(myAddressPage.getDistrictError(), REQUIRED_FIELD);
    }

    @Test(dependsOnMethods = "addEmptyAddress", alwaysRun = true)
    public void addAddress() {
        myAddressPage.fillInAddress(address);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getSuccessMsg(), ADDRESS_SAVED);
        Assert.assertTrue(myAddressPage.validateAddressExisted(address));
    }

    @Test(dependsOnMethods = "addAddress")
    public void validateCreated() {
        headerPage.logout();
        headerPage.login(cus);
        myAddressPage = accountInformationPage.manageAddresses();
        Assert.assertTrue(myAddressPage.validateAddressExisted(address));
    }

    @Test(dependsOnMethods = "validateCreated")
    public void updateAddress() {
        myAddressPage.editAddress(address);
        myAddressPage.fillInAddress(addressEdit);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getSuccessMsg(), ADDRESS_SAVED);
        Assert.assertTrue(myAddressPage.validateAddressExisted(addressEdit));
    }

    @Test(dependsOnMethods = "updateAddress")
    public void validateUpdated() {
        headerPage.logout();
        headerPage.login(cus);
        myAddressPage = accountInformationPage.manageAddresses();
        Assert.assertTrue(myAddressPage.validateAddressExisted(addressEdit));
    }

    @Test(dependsOnMethods = "validateUpdated")
    public void deleteAddress() {
        myAddressPage.deleteAddress(addressEdit);
        Assert.assertEquals(myAddressPage.getSuccessMsg(), ADDRESS_DELETED);
        Assert.assertFalse(myAddressPage.validateAddressExisted(addressEdit));
    }

    @Test(dependsOnMethods = "deleteAddress")
    public void validateDeleted() {
        headerPage.logout();
        headerPage.login(cus);
        myAddressPage = accountInformationPage.manageAddresses();
        Assert.assertFalse(myAddressPage.validateAddressExisted(addressEdit));
    }
}
