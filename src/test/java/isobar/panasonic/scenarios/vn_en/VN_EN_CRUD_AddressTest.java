package isobar.panasonic.scenarios.vn_en;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.MyAddressPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VN_EN_CRUD_AddressTest extends SeleniumTest {
    private CustomerInformation cus;
    private Address address, addressEmpty, addressDefault, addressEdit;
    private MyAddressPage myAddressPage;
    private AccountInformationPage accountInformationPage;
    private StringUtility stringUtility;
    private static final String MSG_SAVE_ADDRESS_SUCCESS = "You saved the address.";
    private static final String MSG_REQUIRED = "This is a required field.";
    private static final String MSG_STATE_REQUIRED = "Please select an option.";
    private static final String MSG_DELETE_SUCCESS = "You deleted the address.";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getCustomer2();
        address = DataTest.getDefaultShippingAddress();
        address.setName(stringUtility.getRandomCharacter(5));
        address.setCompany(stringUtility.getRandomCharacter(5));
        address.setStreet(stringUtility.getRandomCharacter(10));
        addressDefault = DataTest.getDefaultShippingAddress();
        addressDefault.setName(stringUtility.getRandomCharacter(5));
        addressDefault.setCompany(stringUtility.getRandomCharacter(5));
        addressDefault.setStreet(stringUtility.getRandomCharacter(10));
        addressEdit = DataTest.getDefaultShippingAddress();
        addressEdit.setName(stringUtility.getRandomCharacter(5));
        addressEdit.setCompany(stringUtility.getRandomCharacter(5));
        addressEdit.setStreet(stringUtility.getRandomCharacter(10));
        addressEmpty = new Address.Builder().name("").telephone("").company("").city("Please select a region, state or province.").district("").ward("").street("").zip("").build();
    }

    @Test
    public void addNewAddressWithEmptyInfo() {
        headerPage.login(cus);
        accountInformationPage = headerPage.gotoAccountInfo();
        myAddressPage = accountInformationPage.manageAddresses();
        myAddressPage.addNewAddress();
        myAddressPage.fillInAddress(addressEmpty);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getFullNameError(), MSG_REQUIRED);
        Assert.assertEquals(myAddressPage.getTelephoneError(), MSG_REQUIRED);
        Assert.assertEquals(myAddressPage.getCityError(), MSG_STATE_REQUIRED);
        Assert.assertEquals(myAddressPage.getStreetError(), MSG_REQUIRED);
        Assert.assertEquals(myAddressPage.getWardError(), MSG_REQUIRED);
        Assert.assertEquals(myAddressPage.getDistrictError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "addNewAddressWithEmptyInfo")
    public void addNewAddressWithValidInfo(){
        myAddressPage.fillInAddress(address);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getSuccessMsg(), MSG_SAVE_ADDRESS_SUCCESS);
        Assert.assertTrue(myAddressPage.validateAddressExisted(address));
    }

    @Test(dependsOnMethods = "addNewAddressWithValidInfo")
    public void addDefaultAddress(){
        myAddressPage.addNewAddress();
        myAddressPage.fillInAddress(addressDefault);
        myAddressPage.setDefaultShippingAddress(true);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getSuccessMsg(), MSG_SAVE_ADDRESS_SUCCESS);
        Assert.assertTrue(myAddressPage.checkDefaultShippingAddress(addressDefault));
    }

    @Test(dependsOnMethods = "addDefaultAddress")
    public void editAddress(){
        myAddressPage.editAddress(address);
        myAddressPage.fillInAddress(addressEdit);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getSuccessMsg(), MSG_SAVE_ADDRESS_SUCCESS);
        Assert.assertTrue(myAddressPage.validateAddressExisted(addressEdit));
    }

    @Test(dependsOnMethods = "editAddress")
    public void deleteAddress(){
        myAddressPage.deleteAddress(addressEdit);
        Assert.assertEquals(myAddressPage.getSuccessMsg(), MSG_DELETE_SUCCESS);
        Assert.assertFalse(myAddressPage.validateAddressExisted(addressEdit));
    }
}