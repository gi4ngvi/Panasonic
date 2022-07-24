package isobar.panasonic.entity.data;

import isobar.panasonic.utility.DataTest;

public enum OrderStatus {
    PROCESSING, PENDING, CANCEL;

    public String getStatus() {
        SiteCode siteCode = DataTest.getSiteCode();
        switch (siteCode) {
            case ID_EN:
            case TH_EN:
            case VN_EN:
                return "order status: " + getOrderStatusEN(this);
            case MY_EN:
                return "order status: " + getOrderStatusMY_EN(this);
            case ID_BA:
                return "order status: " + getOrderStatusID_BA(this);
            case VN_VN:
                return "order status: " + getOrderStatusVN_VN(this);
            case TH_TH:
                return "order status: " + getOrderStatusTH_TH(this);
            default:
                return null;
        }
    }

    private String getOrderStatusTH_TH(OrderStatus status) {
        if (status == PENDING) {
            return "รอการยืนยัน";
        } else if (status == CANCEL) {
            return "cancel";
        } else {
            return null;
        }
    }

    private String getOrderStatusEN(OrderStatus status) {
        if (status == PENDING) {
            return "pending";
        } else if (status == CANCEL) {
            return "cancel";
        } else {
            return null;
        }
    }

    private String getOrderStatusMY_EN(OrderStatus status) {
        if (status == PENDING) {
            return "order recieved";
        } else if (status == CANCEL) {
            return "cancel";
        } else {
            return null;
        }
    }

    private String getOrderStatusID_BA(OrderStatus status) {
        if (status == PENDING) {
            return "menunggu keputusan";
        } else if (status == CANCEL) {
            return "cancel";
        } else {
            return null;
        }
    }

    private String getOrderStatusVN_VN(OrderStatus status) {
        if (status == PENDING) {
            return "đang chờ xử lý";
        } else if (status == CANCEL) {
            return "đã hủy";
        } else {
            return null;
        }
    }
}
