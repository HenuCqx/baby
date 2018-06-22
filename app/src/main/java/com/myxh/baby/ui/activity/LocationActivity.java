package com.myxh.baby.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.myxh.baby.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private View mView = null;
    MapView mMapView;
    BaiduMap mBaiduMap;

    // UI相关
    RadioGroup.OnCheckedChangeListener radioButtonListener;
    TextView requestLocButton;
    TextView mTvCancel ,mTvOk;

    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;
    static double mL1;
    static double mL2;

    @SuppressLint("CutPasteId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String signal = AppInfoUtils.getSingInfo(getApplicationContext(), getPackageName(), AppInfoUtils.SHA1);
        Log.d("cqxcqxcqx" ,signal);
        //在申请之前，我们要判断一下是否为安卓6.0机型
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermission();
        }
        //解决地图打开无显示的问题
        if (!"generic".equalsIgnoreCase(Build.BRAND)) {
            SDKInitializer.initialize(getApplicationContext());
        }
        setContentView(R.layout.activity_location);
        requestLocButton = (TextView) findViewById(R.id.button1);
        mTvOk = (TextView)findViewById(R.id.tv_ok);
        mTvCancel = (TextView)findViewById(R.id.tv_cancel);
        mTvOk.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        requestLocButton.setText("普通");
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        requestLocButton.setText("跟随");
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.overlook(0);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                        break;
                    case COMPASS:
                        requestLocButton.setText("普通");
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));
                        MapStatus.Builder builder1 = new MapStatus.Builder();
                        builder1.overlook(0);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
                        break;
                    case FOLLOWING:
                        requestLocButton.setText("罗盘");
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);

        RadioGroup group = (RadioGroup) this.findViewById(R.id.radioGroup);
        radioButtonListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.defaulticon) {
                    // 传入null则，恢复默认图标
                    mCurrentMarker = null;
                    mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                            mCurrentMode, true, null));
                }
                if (checkedId == R.id.customicon) {
                    // 修改为自定义marker
                    mCurrentMarker = BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_location);
                    mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                            mCurrentMode, true, mCurrentMarker,
                            accuracyCircleFillColor, accuracyCircleStrokeColor));
                }
            }
        };
        group.setOnCheckedChangeListener(radioButtonListener);

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    void setMarker(){
        mBaiduMap.clear();
        //设置坐标点
        LatLng point1 = new LatLng(mL1 + 0.008000, mL2 - 0.003000);
        LatLng point2 = new LatLng(mL1 + 0.030000, mL2 + 0.039000);
        LatLng point3 = new LatLng(mL1 - 0.040000, mL2 - 0.028400);
        LatLng point4 = new LatLng(mL1 + 0.020000, mL2 + 0.015000);
        LatLng point5 = new LatLng(mL1 + 0.025655, mL2 - 0.015454);
        LatLng point6 = new LatLng(mL1 - 0.018100, mL2 + 0.010000);

        mView = LayoutInflater.from(LocationActivity.this).inflate(R.layout.layout_baidu_map_item, null);
        ImageView mImg = (ImageView)mView.findViewById(R.id.img);
        TextView mTv = (TextView)mView.findViewById(R.id.tv);

        mImg.setImageDrawable(LocationActivity.this.getResources().getDrawable(R.drawable.ic_kindergarten_1));
        mTv.setText("新中华幼儿园");
        BitmapDescriptor bitmapDescriptor1 = BitmapDescriptorFactory
                .fromView(mView);

        mImg.setImageDrawable(LocationActivity.this.getResources().getDrawable(R.drawable.ic_kindergarten_5));
        mTv.setText("周氏阳光幼儿园");
        BitmapDescriptor bitmapDescriptor2 = BitmapDescriptorFactory
                .fromView(mView);

        mImg.setImageDrawable(LocationActivity.this.getResources().getDrawable(R.drawable.ic_kindergarten_8));
        mTv.setText("小智慧家幼儿园");
        BitmapDescriptor bitmapDescriptor3 = BitmapDescriptorFactory
                .fromView(mView);

        mImg.setImageDrawable(LocationActivity.this.getResources().getDrawable(R.drawable.ic_kindergarten_11));
        mTv.setText("优启稚慧幼儿园");
        BitmapDescriptor bitmapDescriptor4 = BitmapDescriptorFactory
                .fromView(mView);

        mImg.setImageDrawable(LocationActivity.this.getResources().getDrawable(R.drawable.ic_kindergarten_13));
        mTv.setText("启蒙幼儿园");
        BitmapDescriptor bitmapDescriptor5 = BitmapDescriptorFactory
                .fromView(mView);

        mImg.setImageDrawable(LocationActivity.this.getResources().getDrawable(R.drawable.ic_kindergarten_15));
        mTv.setText("小天使幼儿园");
        BitmapDescriptor bitmapDescriptor6 = BitmapDescriptorFactory
                .fromView(mView);

        OverlayOptions ooA1 = new MarkerOptions().position(point1)
                .icon(bitmapDescriptor1).zIndex(9).draggable(true);
        OverlayOptions ooA2 = new MarkerOptions().position(point2)
                .icon(bitmapDescriptor2).zIndex(9).draggable(true);
        OverlayOptions ooA3 = new MarkerOptions().position(point3)
                .icon(bitmapDescriptor3).zIndex(9).draggable(true);
        OverlayOptions ooA4 = new MarkerOptions().position(point4)
                .icon(bitmapDescriptor4).zIndex(9).draggable(true);
        OverlayOptions ooA5 = new MarkerOptions().position(point5)
                .icon(bitmapDescriptor5).zIndex(9).draggable(true);
        OverlayOptions ooA6 = new MarkerOptions().position(point6)
                .icon(bitmapDescriptor6).zIndex(9).draggable(true);

        //创建OverlayOptions的集合
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();

        options.add(ooA1);
        options.add(ooA2);
        options.add(ooA3);
        options.add(ooA4);
        options.add(ooA5);
        options.add(ooA6);
        //在地图上批量添加
        mBaiduMap.addOverlays(options);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * 确认和取消按钮的点击事件监听
     * @author  cqx
     * create at 2018/5/22 23:03
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_ok :
                finish();
                break;
            case R.id.tv_cancel :
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mL1 = location.getLatitude();
            mL2 = location.getLongitude();
            setMarker();
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(14.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener((SensorEventListener) this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener((SensorEventListener) this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //第一次被拒绝后，第二次访问时，向用户说明为什么需要此权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(this, "开启后使用定位功能", Toast.LENGTH_SHORT).show();
            }
            //若权限没有开启，则请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        }
    }

    //当用户选择接受或者拒绝时，申请权限会执行一个回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PackageManager.PERMISSION_GRANTED) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //请求权限成功,做相应的事情 } else {
                //请求失败则提醒用户
                Toast.makeText(LocationActivity.this, "请求权限失败！", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

class AppInfoUtils {

    public final static String SHA1 = "SHA1";

    /**
     * 返回一个签名的对应类型的字符串
     *
     * @param context
     * @param packageName
     * @param type
     *
     * @return
     */
    public static String getSingInfo(Context context, String packageName, String type) {
        String tmp = null;
        Signature[] signs = getSignatures(context, packageName);
        for (Signature sig : signs) {
            if (SHA1.equals(type)) {
                tmp = getSignatureString(sig, SHA1);
                break;
            }
        }
        return tmp;
    }

    /**
     * 返回对应包的签名信息
     *
     * @param context
     * @param packageName
     *
     * @return
     */
    public static Signature[] getSignatures(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取相应的类型的字符串（把签名的byte[]信息转换成16进制）
     *
     * @param sig
     * @param type
     *
     * @return
     */
    public static String getSignatureString(Signature sig, String type) {
        byte[] hexBytes = sig.toByteArray();
        String fingerprint = "error!";
        try {
            MessageDigest digest = MessageDigest.getInstance(type);
            if (digest != null) {
                byte[] digestBytes = digest.digest(hexBytes);
                StringBuilder sb = new StringBuilder();
                for (byte digestByte : digestBytes) {
                    sb.append((Integer.toHexString((digestByte & 0xFF) | 0x100)).substring(1, 3));
                }
                fingerprint = sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return fingerprint;
    }
}
