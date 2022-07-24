package isobar.panasonic.scenarios.th_th;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.bo.method.customer.BOEditCustomerPage;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.MyAddressPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TH_TH_AddDefaultAddressInAdminTest extends SeleniumTest {
    private BOEditCustomerPage boEditCustomerPage;
    private AccountInformationPage accountInformationPage;
    private MyAddressPage myAddressPage;
    private Address address, addressEmpty, addressWithoutPhone;
    private StringUtility stringUtility;
    private CustomerInformation cus;
    private static final String MSG_REQUIRED = "This is a required field.";
    private static final String MSG_SAVE_SUCCESS = "You saved the customer.";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        address = DataTest.getDefaultShippingAddress();
        address.setStreet(stringUtility.getRandomCharacter(15));
        address.setFirstName(stringUtility.getRandomCharacter(10));
        address.setLastName(stringUtility.getRandomCharacter(10));
        addressWithoutPhone = DataTest.getDefaultShippingAddress();
        addressWithoutPhone.setMobileNumber("");
        addressEmpty = new Address.Builder().firstName("").company("").lastName("").telephone("").mobileNumber("").country("Thailand").state("Please select a region, state or province.").city("").street("").zip("").build();
        cus = DataTest.getCustomer5();
    }

    @Test
    public void addNewDefaultAddressEmpty() {
        boEditCustomerPage = launchBOSite().viewAllCustomersPage().editCustomer(cus);
        boEditCustomerPage.expandAddressTab();
        boEditCustomerPage.addressesTab.addNewAddresses();
        boEditCustomerPage.addressesTab.fillInAddress(addressEmpty);
        boEditCustomerPage.saveAndContinue();
        Assert.assertEquals(boEditCustomerPage.addressesTab.getFirstNameError(), MSG_REQUIRED);
        Assert.assertEquals(boEditCustomerPage.addressesTab.getZipError(), MSG_REQUIRED);
        Assert.assertEquals(boEditCustomerPage.addressesTab.getStateError(), MSG_REQUIRED);
        Assert.assertEquals(boEditCustomerPage.addressesTab.getStreetError(), MSG_REQUIRED);
        boEditCustomerPage.addressesTab.fillInAddress(addressWithoutPhone);
        boEditCustomerPage.saveAndContinue();
        Assert.assertEquals(boEditCustomerPage.addressesTab.getMobilePhoneError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "addNewDefaultAddressEmpty")
    public void addNewDefaultAddress() {
        boEditCustomerPage.addressesTab.fillInAddress(address);
        boEditCustomerPage.addressesTab.setDefaultBillingAddress(address, true);
        boEditCustomerPage.addressesTab.setDefaultShippingAddress(address, true);
        boEditCustomerPage.saveAndContinue();
        Assert.assertEquals(boEditCustomerPage.getSuccessMsg(), MSG_SAVE_SUCCESS);
        boEditCustomerPage.expandAddressTab();
        Assert.assertTrue(boEditCustomerPage.addressesTab.validateAddressExists(address));
        Assert.assertTrue(boEditCustomerPage.addressesTab.checkDefaultShippingAddress(address));
        Assert.assertTrue(boEditCustomerPage.addressesTab.checkDefaultBillingAddress(address));
    }

    @Test(dependsOnMethods = "addNewDefaultAddress")
    public void checkDefaultAddressOnFO() {
        launchFOSite();
        accountInformationPage = headerPage.login(cus);
        myAddressPage = accountInformationPage.manageAddresses();
        Assert.assertTrue(myAddressPage.checkDefaultShippingAddress(address));
        myAddressPage.emptyAddress();
    }
}
