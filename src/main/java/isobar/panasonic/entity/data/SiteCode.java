package isobar.panasonic.entity.data;

public enum SiteCode {
    VN_VN, VN_EN, ID_EN, ID_BA, TH_TH, TH_EN, MY_EN;

    public String getCode() {
        switch (this) {
            case ID_EN:
                return "id_en";
            case ID_BA:
                return "id_ba";
            case TH_TH:
                return "th_th";
            case TH_EN:
                return "th_en";
            case VN_VN:
                return "vn_vn";
            case VN_EN:
                return "vn_en";
            case MY_EN:
                return "my_en";
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case ID_BA:
                return "ID";
            case TH_TH:
                return "TH";
            case VN_VN:
                return "VN";
            case VN_EN:
            case TH_EN:
            case MY_EN:
            case ID_EN:
                return "EN";
            default:
                return null;
        }
    }

    public String getSiteLanguage() {
        switch (this) {
            case ID_BA:
                return "Bahasa";
            case TH_TH:
                return "Thai";
            case VN_VN:
                return "Vietnamese";
            case VN_EN:
            case TH_EN:
            case MY_EN:
            case ID_EN:
                return "English";
            default:
                return null;
        }
    }

    public String changeToEN() {
        switch (this) {
            case TH_TH:
            case TH_EN:
                return "Thailand";
            case VN_VN:
            case VN_EN:
                return "Vietnam";
            case ID_EN:
            case ID_BA:
                return "Indonesia";
            case MY_EN:
                return "Malaysia";
            default:
                return null;
        }
    }

    public String getSite() {
        switch (this) {
            case MY_EN:
                return "Malaysia English";
            default:
                return "Please define";
        }
    }
}