package com.dongyangbike.app.base;

import android.app.Application;
import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.http.HttpHeaders;
import tech.gujin.toast.ToastUtil;

public class BaseApplication extends Application {

    private static BaseApplication sInstance;
    public static LocationClient mLocationClient;
    LocationClientOption option = new LocationClientOption();
    private final  String tempCoor = "bd09ll";

    public static synchronized BaseApplication getInstance() {
        return sInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        SDKInitializer.initialize(this);
        mLocationClient = new LocationClient(this.getApplicationContext());
        initLocation();
        initHttp(this);
        ToastUtil.initialize(this, ToastUtil.Mode.REPLACEABLE);
    }

    private void initLocation() {
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType(tempCoor);//可选，默认gcj02，设置返回的定位结果坐标系，
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setScanSpan(1000*60*5);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setPriority(LocationClientOption.GpsFirst);  //设置GPS优先
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }

    LatLng currentLatLng = null;

    public LatLng getCurrentLatLng() {
        return currentLatLng;
    }

    public void setCurrentLatLng(LatLng currentLatLng) {
        this.currentLatLng = currentLatLng;
    }

    public static void initHttp(Context context) {

        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(context.getApplicationContext()));
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new LoggerInterceptor("YIBO"));   //添加打印LOG的拦截器
        builder.cookieJar(cookieJar)
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))//内存Cookie
//                .retryOnConnectionFailure(true)
                .connectTimeout(15000L, TimeUnit.MILLISECONDS)  //连接超时
//                .writeTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(15000L, TimeUnit.MILLISECONDS);     //读取超时

        OkHttpUtils.initClient(builder.build());
    }

}
