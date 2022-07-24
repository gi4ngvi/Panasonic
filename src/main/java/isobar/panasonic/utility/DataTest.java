package isobar.panasonic.utility;

import com.relevantcodes.extentreports.LogStatus;
import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.AuthenticalAccount;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.customer.StorePickup;
import isobar.panasonic.entity.data.*;
import isobar.panasonic.entity.product.BundleProduct;
import isobar.panasonic.entity.product.ConfigurationProduct;
import isobar.panasonic.entity.product.SingleProduct;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by hieu.nguyen on 6/4/2018.
 */

public class DataTest {
    private static JSONObject products, account, jsonData, environment, creditCard, warranty, coupon;
    private static File file;
    private static AuthenticalAccount boAccount1;
    private static String reportPath;
    private static Platform platform;
    private static int thread_count = 1;
    private static ThreadLocal<SiteCode> siteCode = new ThreadLocal<>();
    private static Map<SiteCode, Country> country = new HashMap<>();
    private static boolean liveStockStatus = false;

    public synchronized static void init() {
        try {

            /*
                   products
             */

            file = new File(System.getProperty("user.dir") + "\\product.json");
            String content = FileUtils.readFileToString(file, "utf-8");
            products = new JSONObject(content);

            /*
                    account
             */

            file = new File(System.getProperty("user.dir") + "\\account.json");
            content = FileUtils.readFileToString(file, "utf-8");
            account = new JSONObject(content);
              /*
                creditCard
             */
            file = new File(System.getProperty("user.dir") + "\\card.json");
            content = FileUtils.readFileToString(file, "utf-8");

            creditCard = new JSONObject(content);
             /*
                   environment
             */
            file = new File(System.getProperty("user.dir") + "\\environment.json");
            content = FileUtils.readFileToString(file, "utf-8");
            environment = new JSONObject(content);
            /*
                creditCard
             */
            file = new File(System.getProperty("user.dir") + "\\card.json");
            content = FileUtils.readFileToString(file, "utf-8");
            creditCard = new JSONObject(content);
              /*
                warranty
             */
            file = new File(System.getProperty("user.dir") + "\\warranty.json");
            content = FileUtils.readFileToString(file, "utf-8");
            warranty = new JSONObject(content);
              /*
                coupon
             */
            file = new File(System.getProperty("user.dir") + "\\coupon.json");
            content = FileUtils.readFileToString(file, "utf-8");
            coupon = new JSONObject(content);
            platform = Platform.PC;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static SingleProduct getSingleProductTest() {
        jsonData = products.getJSONObject(getSiteCode().getCode()).getJSONObject("sp");
        SingleProduct product = SingleProduct.builder().name(jsonData.getString("name")).sku(jsonData.getString("sku")).price(jsonData.getInt("price")).category(jsonData.getString("category")).url(jsonData.getString("url")).qty(jsonData.getInt("qty")).build();
        if (getSiteCode() == SiteCode.VN_VN) {
            product.setId(jsonData.getString("id"));
        }
        return product;
    }

    public synchronized static SingleProduct getSingleProductTest1() {
        jsonData = products.getJSONObject(getSiteCode().getCode()).getJSONObject("sp1");
        SingleProduct product = SingleProduct.builder().name(jsonData.getString("name")).sku(jsonData.getString("sku")).price(jsonData.getInt("price")).category(jsonData.getString("category")).url(jsonData.getString("url")).qty(jsonData.getInt("qty")).build();
        if (getSiteCode() == SiteCode.VN_VN) {
            product.setId(jsonData.getString("id"));
        }
        return product;
    }

    public synchronized static BundleProduct getBundleProductTest() {
        jsonData = products.getJSONObject(getSiteCode().getCode()).getJSONObject("bundle");
        BundleProduct bundleProduct;
        ArrayList<SingleProduct> productList = new ArrayList<>();
        SingleProduct productItem;
        JSONObject jProductItem;
        JSONArray jsonBundleItems = jsonData.getJSONArray("bundle_items");
        for (int i = 0; i < jsonBundleItems.length(); i++) {
            jProductItem = jsonBundleItems.getJSONObject(i);
            productItem = SingleProduct.builder().name(jProductItem.getString("name")).sku(jProductItem.getString("sku")).
                    qty(jProductItem.getInt("qty")).price(jProductItem.getInt("price")).build();
            productList.add(productItem);
        }
        bundleProduct = BundleProduct.builder().listSingleProducts(productList).name(jsonData.getString("name")).category(jsonData.getString("category")).
                url(jsonData.getString("url")).price(jsonData.getInt("price")).sku(jsonData.getString("sku")).qty(jsonData.getInt("qty")).build();
        if (getSiteCode() == SiteCode.VN_VN) {
            bundleProduct.setId(jsonData.getString("id"));
        }
        return bundleProduct;
    }

    public synchronized static ConfigurationProduct getConfigurationProduct() {
        jsonData = products.getJSONObject(getSiteCode().getCode()).getJSONObject("cp");
        return ConfigurationProduct.builder().name(jsonData.getString("name"))
                .sku(jsonData.getString("sku"))
                .category(jsonData.getString("category"))
                .url(jsonData.getString("url"))
                .color(jsonData.getString("color"))
                .price(jsonData.getInt("price"))
                .qty(jsonData.getInt("qty"))
                .subSKU(jsonData.getString("sub_sku"))
                .build();
    }
    /*
        Account BO
     */

    public static AuthenticalAccount getBOAccount01() {
        if (boAccount1 == null) {
            jsonData = account.getJSONObject("bo").getJSONObject("user1");
            boAccount1 = new AuthenticalAccount.Builder().username(jsonData.getString("user"))
                    .password(jsonData.getString("pass"))
                    .build();
        }
        return boAccount1;
    }



    /*
        Account FO
     */

    public static Address getDefaultBillingAddress() {
        Country country = getCountry();
        jsonData = account.getJSONObject("fo").getJSONObject(getSiteCode().getCode()).getJSONObject("user1").getJSONObject("billingAddress");
        Address address = new Address.Builder()
                .street(jsonData.getString("street"))
                .telephone(jsonData.getString("telephone"))
                .country(jsonData.getString("country"))
                .build();
        if (country == Country.VN) {
            address.setCity(jsonData.getString("city"));
            address.setName(jsonData.getString("name"));
            address.setDistrict(jsonData.getString("district"));
            address.setWard(jsonData.getString("ward"));
            address.setCompany(jsonData.getString("company"));
        } else if (country == Country.ID) {
            address.setCity(jsonData.getString("city"));
            address.setName(jsonData.getString("name"));
            address.setState(jsonData.getString("state"));
            address.setZip(jsonData.getString("zip"));
            address.setCompany(jsonData.getString("company"));
        } else if (country == Country.TH) {
            address.setFirstName(jsonData.getString("firstName"));
            address.setLastName(jsonData.getString("lastName"));
            address.setState(jsonData.getString("state"));
            address.setZip(jsonData.getString("zip"));
        } else if (country == Country.MY) {
            address.setFirstName(jsonData.getString("firstName"));
            address.setLastName(jsonData.getString("lastName"));
            address.setState(jsonData.getString("state"));
            address.setZip(jsonData.getString("zip"));
            address.setCity(jsonData.getString("city"));
            address.setCompany(jsonData.getString("company"));
            address.setAddressName(jsonData.getString("addressName"));
            address.setMobileNumber(jsonData.getString("mobileNumber"));
        }
        return address;
    }

    public synchronized static CustomerInformation getDefaultCustomerTest() {
        Country country = getCountry();
        jsonData = account.getJSONObject("fo").getJSONObject(getSiteCode().getCode()).getJSONObject("user1");
        CustomerInformation cus = new CustomerInformation.Builder()
                .email(jsonData.getString("email"))
                .password(jsonData.getString("pass"))
                .passwordConfirm(jsonData.getString("passConfirm"))
                .dateOfBirth(jsonData.getString("dateOfBirth"))
                .build();
        if (country == Country.VN) {
            cus.setName(jsonData.getString("name"));
        } else if (country == Country.TH) {
            cus.setFirstName(jsonData.getString("firstName"));
            cus.setLastName(jsonData.getString("lastName"));
            cus.setTitle(jsonData.getString("title"));
            cus.setNickName(jsonData.getString("nickName"));
        } else if (country == Country.ID) {
            cus.setName(jsonData.getString("name"));
            cus.setTitle(jsonData.getString("title"));
            cus.setNickName(jsonData.getString("nickName"));
            cus.setTypeOfIdentityCard(jsonData.getString("typeOfIdentityCard"));
            cus.setIdentityCardNumber(jsonData.getString("identityCardNumber"));
        } else if (country == Country.MY) {
            cus.setFirstName(jsonData.getString("firstName"));
            cus.setLastName(jsonData.getString("lastName"));
            cus.setNickName(jsonData.getString("nickName"));
            cus.setTypeOfIdentityCard(jsonData.getString("typeOfIdentityCard"));
            cus.setIdentityCardNumber(jsonData.getString("identityCardNumber"));
            cus.setMaritalStatus(jsonData.getString("maritalStatus"));
            cus.setRace(jsonData.getString("race"));
            cus.setGroup(jsonData.getString("group"));
        }
        return cus;
    }

    public static CustomerInformation getRandomCustomerTest() {
        Country country = getCountry();
        StringUtility stringUtility = new StringUtility();
        CustomerInformation cus = new CustomerInformation.Builder()
                .email(stringUtility.getRandomCharacter(10) + System.currentTimeMillis() + "@mailinator.com")
                .password("Abcd@123456789")
                .passwordConfirm("Abcd@123456789")
                .dateOfBirth("1990-10-10")
                .build();
        if (country == Country.VN) {
            cus.setName(stringUtility.getRandomCharacter(10));
        } else if (country == Country.TH) {
            cus.setFirstName(stringUtility.getRandomCharacter(5));
            cus.setLastName(stringUtility.getRandomCharacter(5));
            cus.setTitle("Mr.");
            cus.setNickName(stringUtility.getRandomCharacter(10));
        } else if (country == Country.ID) {
            cus.setName(stringUtility.getRandomCharacter(10));
            cus.setTitle("Mr.");
            cus.setNickName(stringUtility.getRandomCharacter(10));
            cus.setSubscribeNewsletter(false);
            cus.setTypeOfIdentityCard("New IC");
            cus.setIdentityCardNumber("264345066");
        } else if (country == Country.MY) {
            cus.setFirstName(stringUtility.getRandomCharacter(10));
            cus.setLastName(stringUtility.getRandomCharacter(5));
            cus.setNickName(stringUtility.getRandomCharacter(10));
            cus.setTypeOfIdentityCard("New IC");
            cus.setIdentityCardNumber("264345066");
            cus.setSubscribeNewsletter(false);
            cus.setMessagingService(false);
            cus.setMaritalStatus("Single");
            cus.setRace("Malay");
        }
        return cus;
    }

    public static Address getRandomAddress() {
        long randomID = System.currentTimeMillis();
        Address address = getDefaultShippingAddress();
        address.name("name_" + randomID);
        address.setStreet("street_" + randomID);
        address.company("company_" + randomID);
        return address;
    }

    public static String getGmailCredential() {
        return System.getProperty("user.dir") + "\\client_secret_pana01.json";
    }

    public static String getForgotPasswordCredential() {
        return System.getProperty("user.dir") + "\\client_secret_pana02.json";
    }

    public synchronized static Address getDefaultShippingAddress() {
        Country country = getCountry();
        jsonData = account.getJSONObject("fo").getJSONObject(getSiteCode().getCode()).getJSONObject("user1").getJSONObject("shippingAddress");
        Address address = new Address.Builder()
                .street(jsonData.getString("street"))
                .telephone(jsonData.getString("telephone"))
                .country(jsonData.getString("country"))
                .build();
        if (country == Country.VN) {
            address.setCity(jsonData.getString("city"));
            address.setName(jsonData.getString("name"));
            address.setDistrict(jsonData.getString("district"));
            address.setWard(jsonData.getString("ward"));
            address.setCompany(jsonData.getString("company"));
            address.setMobileNumber(jsonData.getString("mobileNumber"));
        } else if (country == Country.ID) {
            address.setCity(jsonData.getString("city"));
            address.setName(jsonData.getString("name"));
            address.setState(jsonData.getString("state"));
            address.setZip(jsonData.getString("zip"));
            address.setCompany(jsonData.getString("company"));
            address.setMobileNumber(jsonData.getString("mobileNumber"));
        } else if (country == Country.TH) {
            address.setFirstName(jsonData.getString("firstName"));
            address.setLastName(jsonData.getString("lastName"));
            address.setState(jsonData.getString("state"));
            address.setZip(jsonData.getString("zip"));
            address.setMobileNumber(jsonData.getString("mobileNumber"));
        } else if (country == Country.MY) {
            address.setFirstName(jsonData.getString("firstName"));
            address.setLastName(jsonData.getString("lastName"));
            address.setState(jsonData.getString("state"));
            address.setZip(jsonData.getString("zip"));
            address.setCity(jsonData.getString("city"));
            address.setCompany(jsonData.getString("company"));
            address.setAddressName(jsonData.getString("addressName"));
            address.setMobileNumber(jsonData.getString("mobileNumber"));
        }
        return address;
    }

    public static CustomerInformation getCustomer2() {
        Country country = getCountry();
        jsonData = account.getJSONObject("fo").getJSONObject(getSiteCode().getCode()).getJSONObject("user2");
        CustomerInformation cus = new CustomerInformation.Builder()
                .email(jsonData.getString("email"))
                .password(jsonData.getString("pass"))
                .passwordConfirm(jsonData.getString("passConfirm"))
                .dateOfBirth(jsonData.getString("dateOfBirth"))
                .build();
        if (country == Country.VN) {
            cus.setName(jsonData.getString("name"));
        } else if (country == Country.TH) {
            cus.setFirstName(jsonData.getString("firstName"));
            cus.setLastName(jsonData.getString("lastName"));
            cus.setTitle(jsonData.getString("title"));
            cus.setNickName(jsonData.getString("nickName"));
        } else if (country == Country.ID) {
            cus.setName(jsonData.getString("name"));
            cus.setTitle(jsonData.getString("title"));
            cus.setNickName(jsonData.getString("nickName"));
            cus.setTypeOfIdentityCard(jsonData.getString("typeOfIdentityCard"));
            cus.setIdentityCardNumber(jsonData.getString("identityCardNumber"));
        } else if (country == Country.MY) {
            cus.setFirstName(jsonData.getString("firstName"));
            cus.setLastName(jsonData.getString("lastName"));
            cus.setNickName(jsonData.getString("nickName"));
            cus.setTypeOfIdentityCard(jsonData.getString("typeOfIdentityCard"));
            cus.setIdentityCardNumber(jsonData.getString("identityCardNumber"));
            cus.setMaritalStatus(jsonData.getString("maritalStatus"));
            cus.setRace(jsonData.getString("race"));
        }
        return cus;
    }

    public static CustomerInformation getCustomer3() {
        jsonData = account.getJSONObject("fo").getJSONObject(getSiteCode().getCode()).getJSONObject("user3");
        return new CustomerInformation.Builder()
                .name(jsonData.getString("name"))
                .email(jsonData.getString("email"))
                .password(jsonData.getString("pass"))
                .passwordConfirm(jsonData.getString("passConfirm"))
                .dateOfBirth(jsonData.getString("dateOfBirth"))
                .build();
    }

    public static CustomerInformation getCustomer4() {
        jsonData = account.getJSONObject("fo").getJSONObject(getSiteCode().getCode()).getJSONObject("user4");
        return new CustomerInformation.Builder()
                .name(jsonData.getString("name"))
                .email(jsonData.getString("email"))
                .password(jsonData.getString("pass"))
                .passwordConfirm(jsonData.getString("passConfirm"))
                .dateOfBirth(jsonData.getString("dateOfBirth"))
                .build();
    }

    public static CustomerInformation getCustomer5() {
        Country country = getCountry();
        jsonData = account.getJSONObject("fo").getJSONObject(getSiteCode().getCode()).getJSONObject("user5");
        CustomerInformation cus = new CustomerInformation.Builder()
                .email(jsonData.getString("email"))
                .password(jsonData.getString("pass"))
                .build();
        if (country == Country.TH || country == Country.MY) {
            cus.setFirstName(jsonData.getString("firstName"));
            cus.setLastName(jsonData.getString("lastName"));
        } else {
            cus.setName(jsonData.getString("name"));
        }
        return cus;
    }

    public static CustomerInformation getCustomerForgotPassword() {
        Country country = getCountry();
        jsonData = account.getJSONObject("fo").getJSONObject(getSiteCode().getCode()).getJSONObject("cus_fg_pass");
        CustomerInformation cus = new CustomerInformation.Builder()
                .email(jsonData.getString("email"))
                .password(jsonData.getString("pass"))
                .passwordConfirm(jsonData.getString("passConfirm"))
                .dateOfBirth(jsonData.getString("dateOfBirth"))
                .build();
        if (country == Country.VN) {
            cus.setName(jsonData.getString("name"));
        } else if (country == Country.TH) {
            cus.setFirstName(jsonData.getString("firstName"));
            cus.setLastName(jsonData.getString("lastName"));
            cus.setTitle(jsonData.getString("title"));
        } else if (country == Country.ID) {
            cus.setName(jsonData.getString("name"));
            cus.setTitle(jsonData.getString("title"));
        } else if (country == Country.MY) {
            cus.setFirstName(jsonData.getString("firstName"));
            cus.setLastName(jsonData.getString("lastName"));
        }
        return cus;
    }

    public static StorePickup getDefaultStorePickup() {
        //Only used for MY_EN
        jsonData = account.getJSONObject("fo").getJSONObject(getSiteCode().getCode()).getJSONObject("user1").getJSONObject("storePickup");
        StorePickup storePickup = new StorePickup.Builder().storeName(jsonData.getString("storeName"))
                .country(jsonData.getString("country"))
                .state(jsonData.getString("state"))
                .city(jsonData.getString("city"))
                .zipCode(jsonData.getString("zipCode"))
                .street1(jsonData.getString("street1"))
                .street2(jsonData.getString("street2"))
                .street3(jsonData.getString("street3"))
                .phoneNumber(jsonData.getString("phoneNumber"))
                .build();
        return storePickup;
    }


    public static void setReportPath(String path) {
        reportPath = path;
    }

    public static String getReportPath() {
        if (reportPath == null || reportPath.equals(""))
            return "\\test-output\\report\\";
        return reportPath;
    }

    public static void setPlatform(Platform pf) {
        platform = pf;
    }

    public static Platform getPlatform() {
        return platform;
    }

    /*
        Get thread count
    */

    public synchronized static void setThreadCount(int threadCount) {
        thread_count = threadCount;
    }

    public synchronized static int getThreadCount() {
        return thread_count;
    }

    public static String getMonthNumberOfCreditCard(String month) {
        return month.split("-")[0].trim();
    }

    public static int getTaxRate() {
        return environment.getJSONObject("tax").getInt(getCountry().getCode());
    }

    public static int calculateTax(int price) {
        return price * DataTest.getTaxRate() / 100;
    }

    public synchronized static void setCountry(String countryStr) {
        Country countrySet;
        switch (countryStr) {
            case "vn":
                countrySet = Country.VN;
                break;
            case "th":
                countrySet = Country.TH;
                break;
            case "id":
                countrySet = Country.ID;
                break;
            case "my":
                countrySet = Country.MY;
                break;
            default:
                countrySet = null;
                break;
        }
        country.put(getSiteCode(), countrySet);
    }

    public synchronized static Country getCountry() {
        return country.get(getSiteCode());
    }

    public synchronized static void setSiteCode(String siteCodeStr) {
        SiteCode siteCodeSet;
        switch (siteCodeStr) {
            case "vn_vn":
                siteCodeSet = SiteCode.VN_VN;
                break;
            case "vn_en":
                siteCodeSet = SiteCode.VN_EN;
                break;
            case "th_th":
                siteCodeSet = SiteCode.TH_TH;
                break;
            case "th_en":
                siteCodeSet = SiteCode.TH_EN;
                break;
            case "id_en":
                siteCodeSet = SiteCode.ID_EN;
                break;
            case "id_ba":
                siteCodeSet = SiteCode.ID_BA;
                break;
            case "my_en":
                siteCodeSet = SiteCode.MY_EN;
                break;
            default:
                siteCodeSet = null;
                break;
        }
        siteCode.set(siteCodeSet);
    }

    public synchronized static SiteCode getSiteCode() {
        return siteCode.get();
    }

    public static void extractJSLogs(WebDriver driver) {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        String str;
        for (LogEntry entry : logEntries) {
            str = entry.getMessage();
            if (entry.getLevel() != Level.INFO && entry.getLevel() != Level.FINE
                    && !str.contains("jquery-migrate") && !str.contains("For more information")
                    && !str.contains("Expected to start loader but did not find one in the dom")
                    && !str.contains("moment.js 290")
                    && !str.contains("maps.googleapis")
                    && !str.contains("favicon.ico")
                    && !str.contains("This is discouraged and will be removed in upcoming major release"))
                ReportUtility.getInstance().log(LogStatus.WARNING, new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
        }
    }

    public static String getHomeURL() {
        return environment.getJSONObject("dev").getString(getCountry().getCode());
    }

    public static String formatAddress(Address address) {
        return String.format("%s\n" +
                "%s\n" +
                "%s, %s, %s, %s\n" +
                "%s\n" +
                "T: %s", address.getName(), address.getCompany(), address.getStreet(), address.getWard(), address.getDistrict(), address.getCity(), address.getCountry(), address.getTelephone());
    }

    public static JSONObject initConfig() {
        File file;
        String content;
        if (environment != null) {
            return environment;
        }
        try {
            file = new File(System.getProperty("user.dir") + "\\environment.json");
            content = FileUtils.readFileToString(file, "utf-8");
            jsonData = new JSONObject(content);
            return jsonData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAdminURL() {
        return environment.getJSONObject("dev").getString("bo");
    }

    public static CreditCard getVisaTest1() {
        jsonData = creditCard.getJSONObject("Visa1");
        return new CreditCard.Builder().creditCardName(jsonData.getString("name")).creditCardNumber(jsonData.getString("number"))
                .securityCode(jsonData.getString("cvv")).expMonth(jsonData.getString("month")).expYear(jsonData.getString("year"))
                .password3D(jsonData.getString("password"))
                .build();
    }

    public static CreditCard getVisaTest2() {
        jsonData = creditCard.getJSONObject("Visa2");
        return new CreditCard.Builder().creditCardName(jsonData.getString("name")).creditCardNumber(jsonData.getString("number"))
                .securityCode(jsonData.getString("cvv")).expMonth(jsonData.getString("month")).expYear(jsonData.getString("year"))
                .password3D(jsonData.getString("password"))
                .build();
    }

    public static CreditCard getVCBCardTest() {
        jsonData = creditCard.getJSONObject("VCB");
        return new CreditCard.Builder().creditCardName(jsonData.getString("name")).creditCardNumber(jsonData.getString("number"))
                .securityCode(jsonData.getString("cvv")).expMonth(jsonData.getString("month")).expYear(jsonData.getString("year"))
                .password3D(jsonData.getString("password"))
                .build();
    }

    public static CreditCard getABBCardTest() {
        jsonData = creditCard.getJSONObject("ABB");
        return new CreditCard.Builder().creditCardName(jsonData.getString("name")).creditCardNumber(jsonData.getString("number"))
                .expMonth(jsonData.getString("month")).expYear(jsonData.getString("year"))
                .build();
    }

    public static Warranty getWarranty() {
        DateUtility dateUtility = new DateUtility();
        jsonData = warranty.getJSONObject(getSiteCode().getCode()).getJSONObject("product1");
        return new Warranty.Builder().productCategory(jsonData.getString("productCategory"))
                .productName(jsonData.getString("productName"))
                .productSKU(jsonData.getString("productSKU"))
                .serialNumber(jsonData.getString("serialNumber"))
                .rewardPointBonus(jsonData.getInt("rewardPointBonus"))
                .warrantyCardNumber(jsonData.getString("warrantyCardNumber"))
                .supportedDocument(System.getProperty("user.dir") + "\\document.PNG")
                .dateOfPurchase(dateUtility.formatDate(dateUtility.getPlusDate(-1), "yyyy-MM-dd")).build();
    }

    /*
     * config in fo field  Send data to SMAP: Yes
     * */

    public static Warranty getWarranty2() {
        DateUtility dateUtility = new DateUtility();
        jsonData = warranty.getJSONObject(getSiteCode().getCode()).getJSONObject("product2");
        return new Warranty.Builder().productCategory(jsonData.getString("productCategory"))
                .productName(jsonData.getString("productName"))
                .productSKU(jsonData.getString("productSKU"))
                .serialNumber(jsonData.getString("serialNumber"))
                .rewardPointBonus(jsonData.getInt("rewardPointBonus"))
                .warrantyCardNumber(jsonData.getString("warrantyCardNumber"))
                .supportedDocument(System.getProperty("user.dir") + "\\document.PNG")
                .dateOfPurchase(dateUtility.formatDate(dateUtility.getPlusDate(-1), "yyyy-MM-dd")).build();
    }

    public static Coupon getCouponTest1() {
        jsonData = coupon.getJSONObject(getCountry().getCode()).getJSONObject("coupon1");
        return new Coupon.Builder().code(jsonData.getString("code")).name(jsonData.getString("name")).conditions(jsonData.getString("conditions")).
                rule(jsonData.getString("rule")).discount_amount(jsonData.getInt("discount_amount")).build();
    }

    /*
     * workaround for issue: https://bluecom.atlassian.net/browse/PNSNC-803
     * */

    public static String translateStateTHToEN(String stateTHTH) {
        if (stateTHTH.equals("กรุงเทพมหานคร")) {
            return "Bangkok";
        }
        return stateTHTH;
    }

    public static String translateYesNoOption(boolean option) {
        switch (getSiteCode()) {
            case ID_BA:
                return option ? "Tak" : "Iya";
            default:
                return option ? "Yes" : "No";
        }
    }

    public static String getEVN() {
        return environment.getString("env");
    }

    public static void setBackupLiveStockStatus(boolean status) {
        liveStockStatus = status;
    }

    public static boolean getBackupLiveStockStatus() {
        return liveStockStatus;
    }
}
