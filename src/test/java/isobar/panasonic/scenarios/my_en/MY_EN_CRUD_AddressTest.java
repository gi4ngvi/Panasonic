package isobar.panasonic.scenarios.my_en;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.MyAddressPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MY_EN_CRUD_AddressTest extends SeleniumTest {
    private CustomerInformation cus;
    private Address address, addressDefault, addressEmpty, addressEdit;
    private StringUtility stringUtility;
    private AccountInformationPage accountInformationPage;
    private MyAddressPage myAddressPage;
    private String MSG_REQUIRED, MSG_STATE_REQUITED, MSG_SAVE_SUCCESS, MSG_DELETE_SUCCESS;

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getCustomer2();
        address = DataTest.getDefaultShippingAddress();
        address.setFirstName(stringUtility.getRandomCharacter(5));
        address.setLastName(stringUtility.getRandomCharacter(5));
        address.setAddressName(stringUtility.getRandomCharacter(5));
        address.setStreet(RUN_ID + stringUtility.getRandomCharacter(10));
        addressDefault = DataTest.getDefaultShippingAddress();
        addressDefault.setFirstName(stringUtility.getRandomCharacter(5));
        addressDefault.setLastName(stringUtility.getRandomCharacter(5));
        addressDefault.setStreet(stringUtility.getRandomCharacter(10));
        addressEmpty = DataTest.getDefaultShippingAddress();
        addressEmpty.setFirstName("");
        addressEmpty.setLastName("");
        addressEmpty.setState("Please select a region, state or province.");
        addressEmpty.city("");
        addressEmpty.setZip("");
        addressEmpty.setStreet("");
        addressEmpty.telephone("");
        addressEmpty.setAddressName("");
        addressEmpty.setMobileNumber("");
        addressEdit = DataTest.getDefaultShippingAddress();
        addressEdit.setFirstName(stringUtility.getRandomCharacter(5) + "_edit");
        addressDefault.setLastName(stringUtility.getRandomCharacter(5) + "_edit");
        addressDefault.setStreet(stringUtility.getRandomCharacter(10));
        addressDefault.setAddressName(stringUtility.getRandomCharacter(10));
        MSG_REQUIRED = "This is a required field.";
        MSG_STATE_REQUITED = "Please select an option.";
        MSG_SAVE_SUCCESS = "You saved the address.";
        MSG_DELETE_SUCCESS = "You deleted the address.";
    }

    @Test
    public void addAddressWithNullRequiredFields() {
        headerPage.login(cus);
        accountInformationPage = headerPage.gotoAccountInfo();
        myAddressPage = accountInformationPage.manageAddresses();
        myAddressPage.addNewAddress();
        myAddressPage.fillInAddress(addressEmpty);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getFirstNameError(), MSG_REQUIRED);
        Assert.assertEquals(myAddressPage.getLastNameError(), MSG_REQUIRED);
        Assert.assertEquals(myAddressPage.getStateError(), MSG_STATE_REQUITED);
        Assert.assertEquals(myAddressPage.getCityError(), MSG_REQUIRED);
        Assert.assertEquals(myAddressPage.getStreetError(), MSG_REQUIRED);
        Assert.assertEquals(myAddressPage.getMobileNumberError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "addAddressWithNullRequiredFields")
    public void addDefaultAddress() {
        myAddressPage.fillInAddress(addressDefault);
        myAddressPage.setDefaultBillingAddress(true);
        myAddressPage.setDefaultShippingAddress(true);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getSuccessMsg(), MSG_SAVE_SUCCESS);
        Assert.assertTrue(myAddressPage.checkDefaultShippingAddress(addressDefault));
        Assert.assertTrue(myAddressPage.checkDefaultShippingAddress(addressDefault));
    }

    @Test(dependsOnMethods = "addDefaultAddress")
    public void addNewAddressWithValidInfo() {
        myAddressPage.addNewAddress();
        myAddressPage.fillInAddress(address);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getSuccessMsg(), MSG_SAVE_SUCCESS);
        Assert.assertTrue(myAddressPage.validateAddressExisted(address));
    }

    @Test(dependsOnMethods = "addNewAddressWithValidInfo")
    public void editAddress() {
        myAddressPage.editAddress(address);
        myAddressPage.fillInAddress(addressEdit);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getSuccessMsg(), MSG_SAVE_SUCCESS);
        Assert.assertTrue(myAddressPage.validateAddressExisted(addressEdit));
    }

    @Test(dependsOnMethods = "editAddress")
    public void deleteAddress(){
        myAddressPage.deleteAddress(addressEdit);
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), MSG_DELETE_SUCCESS);
        Assert.assertFalse(accountInformationPage.validateAddressExisted(addressEdit));
    }
}
