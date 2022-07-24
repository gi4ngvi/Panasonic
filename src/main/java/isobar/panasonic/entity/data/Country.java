package isobar.panasonic.entity.data;


public enum Country {
    VN, EN, TH, ID, MY;

    @Override
    public String toString() {
        switch (this) {
            case VN:
                return "Vietnamese";
            case EN:
                return "English";
            case ID:
                return "Indonesia";
            case TH:
                return "Thailand";
            case MY:
                return "Malaysia";
            default:
                return null;
        }
    }

    public String getTitle() {
        switch (this) {
            case VN:
                return "Vietnamese";
            case EN:
                return "English";
            case ID:
                return "Indonesia";
            case TH:
                return "Thailand";
            case MY:
                return "Malaysia";
            default:
                return null;
        }
    }

    public String getCode() {
        switch (this) {
            case VN:
                return "vn";
            case EN:
                return "en";
            case TH:
                return "th";
            case ID:
                return "id";
            case MY:
                return "my";
            default:
                return null;
        }
    }
}

