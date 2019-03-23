package com.dongyangbike.app.base;

public class ApiConstant {

    // WX_PAY  API_KEY
    public static final  String API_KEY="79ad3ff7af3b75d1848f755c9003bc2c";

    public static String BASE_URL = "http://140.143.131.31:9090";
    public static String SMS_SEND = "/api/sms/send";
    public static String REGISTER = "/api/register/registerSubmit";
    public static String SMS_LOGIN = "/api/login/smsLogin";
    public static String PWD_LOGIN = "/api/login/passwordLogin";
    public static String FORGET_PWD = "/api/register/forgetPassword";
    public static String ORDERS = "/api/order/myOrder";
    public static String GET_CITY = "/api/city/getLongitudeAndLatitudeCity";
    public static String SEARCH = "/api/parkingLot/getByNameLikeList";
    public static String NEAR_STATIONS = "/api/parkingLot/nearbyParkingLotPage";
    public static String GET_USER_INFO = "/api/userInfo/getByMobile";
    public static String DO_RECHARGE = "/api/pay/recharge";
    public static String DO_WITHDRAW = "/api/pay/cashWithdrawal";
    public static String GET_PARK_DETAIL = "/api/parkingLot/getByLotId";

}
