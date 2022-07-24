package isobar.panasonic.page.fo.method.utility;

import isobar.panasonic.components.Label;
import isobar.panasonic.components.Link;
import isobar.panasonic.components.Menu;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.Country;
import isobar.panasonic.entity.data.LocationStore;
import isobar.panasonic.entity.data.SiteCode;
import isobar.panasonic.entity.product.Product;
import isobar.panasonic.page.fo.locator.utility.HeaderLocator;
import isobar.panasonic.page.fo.method.account.*;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.checkout.CheckoutPage;
import isobar.panasonic.page.fo.method.home.HomePage;
import isobar.panasonic.page.fo.method.home.HomePageEmployee;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.*;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class HeaderPage extends HeaderLocator {

    private HandleLoading handleLoading;
    private static final int TIME_OUT = 10;

    public HeaderPage(WebDriver driver) {
        super(driver);
        handleLoading = new HandleLoading(driver);
    }
    /*
        login
     */

    public boolean isGuest() {
        try {
            waitUtility.waitForPageLoad();
            waitUtility.waitUntilVisibilityOf(weLogin, 3);
        } catch (TimeoutException ex) {
            return false;
        }
        return true;
    }

    public HeaderPage logout() {
        if (!isGuest()) {
            expandCustomerSwitcher();
            waitUtility.waitUntilToBeClickAble(weLogout);
            weLogout.click();
            waitUtility.waitForPageLoad();
            actionUtility.sleep(5000);
            waitUtility.waitForPageLoad();
            Panasonic.checkStoreView(driver);
            selectStore();
        }
        return new HeaderPage(driver);
    }


    public AccountInformationPage login(CustomerInformation cus) {
        enterLoginInfo(cus);
        gotoAccountInfo();
        selectStore();
        waitUtility.waitForPageLoad();
        Panasonic.checkStoreView(driver);
        return new AccountInformationPage(driver);
    }

    private void enterLoginInfo(CustomerInformation cus) {
        actionUtility.sleep(1000);
        waitUtility.waitForPageLoad();
        selectStore();

        if (!isGuest() && !checkCustomerName(cus))
            logout();

        if (isGuest()) {
            actionUtility.sleep(1000);
            waitUtility.waitForPageLoad();
            if(!isLoginPopupDisplayed()) {
                waitUtility.waitUntilToBeClickAble(weLogin);
                weLogin.click();
            }
            new LoginPopupPage(driver).fillInLoginAndSubmit(cus);
            handleLoading.handleAjaxLoading();
        }
    }

    private boolean isLoginPopupDisplayed() {
        String atrributeValue = "";
        try {
            atrributeValue = weLoginPopup.getAttribute("class");
        } catch (NoSuchElementException e) {
            // Added try/catch to get browser log message for debug
            ReportUtility.getInstance().logError(e.getMessage());
            List<LogEntry> logs = driver.manage().logs().get(LogType.BROWSER).getAll();
            for (LogEntry log : logs) {
                ReportUtility.getInstance().logError("Console error message: " + log.getMessage());
            }
        }
        return atrributeValue.contains("_show");
    }

    public boolean checkCustomerName(CustomerInformation cus) {
        String gui, data;
        expandCustomerSwitcher();
        gui = weCustomerName.getText().trim();
        switch (DataTest.getCountry()) {
            case MY:
                data = cus.getFirstName() + " " + cus.getLastName();
                break;
            case ID:
            case TH:
                data = cus.getTitle() + " " + cus.getName();
                break;
            default:
                data = cus.getName();
        }
        return gui.equals(data);
    }

    public LoginPopupPage expandLoginPopup() {
        if (isGuest()) {
            waitUtility.waitUntilToBeClickAble(weLogin);
            weLogin.click();
        }
        return new LoginPopupPage(driver);
    }

    public RegisterPage register() {
        expandLoginPopup();
        waitUtility.waitUntilVisibilityOf(weRegister);
        weRegister.click();
        return new RegisterPage(driver);
    }

    public AccountInformationPage register(CustomerInformation cus) {
        if (isGuest()) {
            expandLogin();
            fillInRegistrationAndSubmit(cus);
        }
        return new AccountInformationPage(driver);
    }

    public void expandLogin() {
        waitUtility.waitUntilToBeClickAble(weLogin);
        weLogin.click();
    }

    public void fillInRegistrationAndSubmit(CustomerInformation cus) {
        waitUtility.waitUntilToBeClickAble(weRegisterSubmit);
        enterRegisterFirstname(cus.getName());
        enterRegisterEmail(cus.getEmail());
        enterRegisterPassword(cus.getPassword());
        enterRegisterPasswordconfirm(cus.getPasswordConfirm());
        submitRegister();
    }
    /*
        Bag
     */

    private boolean isCartEmpty() {
        waitUtility.waitForPageLoad();
        handleLoading.handleAjaxLoading();
        return (getCartCount().equals("")) ? true : false;
    }

    public ProductListPage search(String key) {
        String url = driver.getCurrentUrl();
        waitUtility.waitUntilToBeClickAble(weSearch);
        weSearch.clear();
        weSearch.sendKeys(key);
        weSearch.sendKeys(Keys.ENTER);
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        // ReportUtility.getInstance().logInfo("URL: " + driver.getCurrentUrl());
        return new ProductListPage(driver);
    }

    public void showMiniCart() {
        actionUtility.sleep(1000);
        waitUtility.waitUntilToBeClickAble(weShowCart);
        actionUtility.mouseClick(weShowCart);
        waitUtility.waitUntilToBeClickAble(weViewCart);
    }

    public ShoppingCartPage viewCart() {
        showMiniCart();
        waitUtility.waitUntilToBeClickAble(weViewCart);
        weViewCart.click();
        Panasonic.checkStoreView(driver);
        return new ShoppingCartPage(driver);
    }

    public void clearCart() {
        WebElement binBtn;
        String currentQty;
        if (!isCartEmpty()) {
            showMiniCart();
            List<WebElement> listEle = driver.findElements(By.cssSelector(DELETE_ALL_PRODUCT));
            while (!listEle.isEmpty()) {
                currentQty = getCartCount();
                waitUtility.waitForPageLoad();
                binBtn = listEle.get(0);
                waitUtility.waitUntilToBeClickAble(binBtn);
                binBtn.click();
                actionUtility.sleep(500);
                waitUtility.waitUntilToBeClickAble(weAcceptDeletion);
                weAcceptDeletion.click();
                waitForCartCountUpdated(currentQty);
                listEle = driver.findElements(By.cssSelector(DELETE_ALL_PRODUCT));
            }
            closeMiniCart();
        }
    }

    private void closeMiniCart() {
        if (weShowCart.getAttribute("class").contains("active")) {
            weMiniCartClose.click();
            waitUtility.waitForValueOfAttributeDoesNotContains(weShowCart, "class", "active");
        }
    }

    public void changeSite(SiteCode siteCode) {
        if (isRegionSelected(siteCode)) return;
        waitUtility.waitUntilToBeClickAble(weLang);
        weLang.click();
        Link changeSite = new Link(driver, String.format(LINK_SITE_LANGUAGE, siteCode.getSiteLanguage()));
        waitUtility.waitUntilToBeClickAble(changeSite.getWebElement());
        changeSite.click();
        waitUtility.waitForValueOfAttributeContains(weLang, "innerText", siteCode.toString());
    }

    public void selectStore(LocationStore store) {
        waitUtility.waitForPageLoad();
        Label label = new Label(driver, String.format(SELECT_STORE, store.toString()));

        try {
            waitUtility.waitUntilVisibilityOf(label.getWebElement(), TIME_OUT);
            label.click();
            actionUtility.sleep(1000);
            waitUtility.waitForPageLoad();
            waitUtility.waitUntilVisibilityOf(weCustomerName);
            handleLoading.handleAjaxLoading();
        } catch (TimeoutException ex) {
            ReportUtility.getInstance().logInfo(String.format("Location %s has been set", store.toString()));
        } catch (NoSuchElementException ex) {
            ReportUtility.getInstance().logInfo("No such SelectStore popup");
        }
    }

    public void selectStore() {
        if (DataTest.getCountry() == Country.VN) {
            selectStore(LocationStore.HOCHIMINH);
                /*
                workaround for issue: store location popup has been display 2 time.
                * */
          /*  try {
                if (weChooseLocationPopup.isDisplayed()) {
                    ReportUtility.getInstance().logInfo(String.format("workaround for issue: store location popup has been display 2 time."));
                    selectStore(LocationStore.HOCHIMINH);
                }
            } catch (NoSuchElementException e) {
                ReportUtility.getInstance().logInfo(String.format("Location has been set"));
            }*/
        }
    }

    public boolean isRegionSelected(SiteCode sitecode) {
        if (weLang.getText().indexOf(sitecode.toString()) != -1)
            return true;
        return false;
    }

    public AccountInformationPage gotoAccountInfo() {
        new HeaderPage(driver).selectStore();
        expandCustomerSwitcher();
        waitUtility.waitForElementToBeRefreshedAndClickAble(weAccountInfo);
        weAccountInfo.click();
        return new AccountInformationPage(driver);
    }

    public MyWishlistPage gotoWishlist() {
        expandCustomerSwitcher();
        String url = driver.getCurrentUrl();
        waitUtility.waitUntilToBeClickAble(weWishlist);
        weWishlist.click();
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        return new MyWishlistPage(driver);
    }

    public SendInvitationPage gotoSendInvitationPage() {
        expandCustomerSwitcher();
        waitUtility.waitUntilToBeClickAble(weSendInvite);
        weSendInvite.click();
        waitUtility.waitForPageLoad();
        return new SendInvitationPage(driver);
    }

    public void expandCustomerSwitcher() {
        if(!isGuest() && !weAccountPopup.getAttribute("class").contains("active")) {
            waitUtility.waitUntilToBeClickAble(weCustomerSwitcher);
            weCustomerSwitcher.click();
            waitUtility.waitForValueOfAttributeContains(weAccountPopup, "class", "active");
        }
    }

    public ProductDetailsPage gotoPDP(Product product) {
        driver.get(DataTest.getHomeURL() + product.getUrl());
        return new ProductDetailsPage(driver);
    }


    public void submitRegister() {
        actionUtility.scrollToElement(weRegisterSubmit, 0, -500);
        weRegisterSubmit.click();
    }

    public String getCartCount() {
        waitUtility.waitUntilVisibilityOf(weMiniShowCart);
        //handleLoading.handleAjaxLoading();
        try {
            return weCartCountNum.getText();
        } catch (TimeoutException ex) {
            return "0";
        }
    }

    public void waitForCartCountUpdated(String oldNum) {
        handleLoading.handleAjaxLoading();
        String expectedValue = oldNum.equals("") ? "0" : oldNum;
        waitUtility.waitForValueOfAttributeDoesNotContains(weCartCountNum, "innerText", expectedValue);
    }

    public void waitProductAddedToCart(String count) {
        try {
            waitUtility.waitForValueOfAttribute(weCartCountNum, "innerText", count);
        } catch (TimeoutException e) {
            ReportUtility.getInstance().logInfo("Wait for cart count failed.");
        }
    }

    public void enterRegisterFirstname(String key) {
        weRegisterFirstname.clear();
        weRegisterFirstname.sendKeys(key);
    }

    public void enterRegisterLastname(String key) {
        weRegisterLastname.clear();
        weRegisterLastname.sendKeys(key);

    }

    public void enterRegisterEmail(String key) {
        weRegisterEmail.clear();
        weRegisterEmail.sendKeys(key);
    }

    public void enterRegisterBirthdaymonth(String key) {
        Select select = new Select(weRegisterBirthdaymonth);
        select.selectByVisibleText(key);
    }

    public void enterRegisterBirthdayyear(String key) {
        Select select = new Select(weRegisterBirthdayyear);
        select.selectByVisibleText(key);
    }

    public void enterRegisterPassword(String key) {
        weRegisterPassword.clear();
        weRegisterPassword.sendKeys(key);
    }

    public void enterRegisterPasswordconfirm(String key) {
        weRegisterPasswordconfirm.clear();
        weRegisterPasswordconfirm.sendKeys(key);
    }

    public String getErrorPopupRequired() {
        Alert alert = driver.switchTo().alert();
        try {
            return alert.getText();
        } catch (Exception ex) {
            return "";
        } finally {
            alert.accept();
        }
    }

    public String getErrorMessage() {
        waitUtility.waitUntilVisibilityOf(weErrorMsg);
        return weErrorMsg.getText();
    }

    public String getSuccessMessage() {
        waitUtility.waitUntilVisibilityOf(weSuccessMsg);
        return weSuccessMsg.getText();
    }

    public RegisterPage gotoRegisterPage() {
        actionUtility.sleep(500);
        String url = driver.getCurrentUrl();
        driver.get(DataTest.getHomeURL() + "customer/account/create");
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        return new RegisterPage(driver);
    }

    public ComparePage gotoComparePage() {
        waitUtility.waitUntilToBeClickAble(weCompare);
        weCompare.click();
        return new ComparePage(driver);
    }

    public ProductListPage gotoCategory(String category) {
        Menu menu = new Menu(driver, MENU_ITEM);
        menu.selectMenuItem(category);
        return new ProductListPage(driver);
    }

    private void openMainCategorySiteML(String menu) {
        Label weMenuML = new Label(driver, String.format(MENU_ITEM, menu));
        waitUtility.waitUntilToBeClickAble(weMenuML.getWebElement());
        actionUtility.mouseMove(weMenuML.getWebElement());
    }

    public CheckoutPage proceedToCheckout() {
        String url = driver.getCurrentUrl();
        waitUtility.waitUntilToBeClickAble(weCheckout);
        weCheckout.click();
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        return new CheckoutPage(driver);
    }

    public ShoppingCartPage proceedToCheckoutSiteML() {
        String url = driver.getCurrentUrl();
        waitUtility.waitUntilToBeClickAble(weCheckout);
        weCheckout.click();
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        return new ShoppingCartPage(driver);
    }

    public boolean checkProduct(Product product) {
        boolean status = checkProductPrice(product);
        status &= checkProductQty(product);
        return status;
    }

    private boolean checkProductPrice(Product product) {
        String gui, data;
        Label wePrice = new Label(driver, String.format(PRODUCT_PRICE, product.getName()));
        if (!wePrice.isDisplay()) {
            ReportUtility.getInstance().logFail(String.format("field price exc tax of %s has been hidden", product.getName()));
            return false;
        }
        gui = wePrice.getText();
        data = PriceUtility.convertPriceToString(product.getPrice());
        ReportUtility.getInstance().logInfo(String.format("Checking price of product: %s - actual: %s - expected: %s", product.getName(), gui, data));
        return gui.equals(data);
    }

    private boolean checkProductQty(Product product) {
        Label weQty = new Label(driver, String.format(PRODUCT_QTY, product.getName()));
        if (!weQty.isDisplay()) {
            ReportUtility.getInstance().logFail(String.format("field qty of %s has been hidden", product.getName()));
            return false;
        }
        Select quantity = new Select(weQty.getWebElement());
        int qty = Integer.parseInt(quantity.getFirstSelectedOption().getText());
        ReportUtility.getInstance().logInfo(String.format("Checking qty of product %s - actual: %s - expected: %d", product.getName(), qty, product.getQty()));
        return qty == product.getQty();
    }

    public ShoppingCartPage gotoShoppingCartPage() {
        String url = driver.getCurrentUrl();
        waitUtility.waitUntilToBeClickAble(weViewCart);
        weViewCart.click();
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        handleLoading.handleAjaxLoading(2);
        return new ShoppingCartPage(driver);
    }

    public WarrantyPage gotoWarrantyPage() {
        waitUtility.waitUntilToBeClickAble(weWarranty);
        weWarranty.click();
        waitUtility.waitForPageLoad();
        return new WarrantyPage(driver);
    }

    private boolean isProductCompareEmpty() {
        try {
            waitUtility.waitForPageLoad();
            return !weCompare.isDisplayed();
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public void emptyProductCompare() {
        if (!isProductCompareEmpty()) {
            gotoComparePage().deleteAllCompare();
        }
    }

    public Object loginSiteML(CustomerInformation cus) {
        enterLoginInfo(cus);
        if(cus.getGroup().equals("General")) {
            return new HomePage(driver);
        } else if(cus.getGroup().equals("Panasonic Employee")) {
            return new HomePageEmployee(driver);
        } else {
            return null;
        }
    }

    public void closeCookiePopup() {
        try {
            waitUtility.refreshAndWaitUntilVisibilityOf(weCloseCookiePopup);
            weCloseCookiePopup.click();
        } catch (TimeoutException e) {
            ReportUtility.getInstance().logInfo("Cookie popup is not displayed.");
        }
    }
}
