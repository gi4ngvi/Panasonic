package isobar.panasonic.entity.data;

import isobar.panasonic.utility.DataTest;

public enum ShippingMethod {
    FreeShipping, Fixed, SelfCollection, StorePickup;

    @Override
    public String toString() {
        SiteCode siteCode = DataTest.getSiteCode();
        switch (this) {
            case FreeShipping:
                return toStringFreeShipping(siteCode);
            case Fixed:
                return "Fixed";
            case SelfCollection:
                return "Self-Collection";
            case StorePickup:
                return "Store Pickup";
            default:
                return null;
        }
    }

    public int getFee() {
        switch (this) {
            case Fixed:
                return 300000;
            case FreeShipping:
            case SelfCollection:
            case StorePickup:
            default:
                return 0;
        }
    }

    public String title() {
        SiteCode siteCode = DataTest.getSiteCode();
        switch (this) {
            case FreeShipping:
                return getTitleFreeShipping(siteCode);
            case Fixed:
                return "Flat Rate - Fixed";
            case SelfCollection:
                return getTitleSefCollection(siteCode);
            case StorePickup:
                return "Store Pickup Method - Store Pickup";
            default:
                return null;
        }
    }

    private String getTitleFreeShipping(SiteCode siteCode) {
        switch (siteCode) {
            case VN_VN:
                return "Phí giao hàng - Miễn phí";
            case VN_EN:
                return "Free Shipping - Free";
            case MY_EN:
                return "Free Shipping- Flat rate - Free";
            default:
                return "Free - Free Shipping";
        }
    }

    private String getTitleSefCollection(SiteCode siteCode) {
        switch (siteCode) {
            case TH_TH:
                return "ส่งฟรี - Self-Collection";
            case TH_EN:
                return "Free Shipping - Self-Collection";
            case MY_EN:
                return "Free1 - Self-Collection";
            default:
                return "Self-Collection - Self-Collection";
        }
    }

    private String toStringFreeShipping(SiteCode siteCode) {
        switch (siteCode) {
            case VN_VN:
                return "Miễn phí";
            default:
                return "Free";
        }
    }
}
