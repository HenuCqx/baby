package com.myxh.coolshopping.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.myxh.coolshopping.R;
import com.myxh.coolshopping.network.HttpListener;
import com.myxh.coolshopping.ui.activity.LocationActivity;
import com.myxh.coolshopping.ui.base.BaseFragment;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AroundFragment extends BaseFragment implements HttpListener<String>, View.OnClickListener {

    private static final String MENU_DATA_KEY = "name";
    private Toolbar mToolbar;
    private TextView mSupplierListTitleTv;
    private ImageView mSupplierListCartIv;
    private FrameLayout mTitleBarLayout;
    private TextView mSupplierListTvProduct;
    private TextView mSupplierListTvSort;
    private TextView mSupplierListTvActivity;
    private LinearLayout mSupplierListProduct;
    private LinearLayout mSupplierListSort;
    private LinearLayout mSupplierListActivity;
    private ListView mListView;
    private PullToRefreshListView mListZeYuan;

    private List<Map<String, String>> mMenuData1;
    private List<Map<String, String>> mMenuData2;
    private List<Map<String, String>> mMenuData3;

    private List<Map<Integer,Object>> dataList;
    private ListView mPopListView;
    private PopupWindow mPopupWindow;
    private SimpleAdapter mMenuAdapter1;
    private SimpleAdapter mMenuAdapter2;
    private SimpleAdapter mMenuAdapter3;
    private int supplierMenuIndex = 0;

    static int img[] = null;
    static String title[] = null;
    static String description[] = null;
    static String location[] = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_around, null);

        initData();
        initView(view);
        initPopMenu();
        return view;
    }

    private void initData() {
        mMenuData1 = new ArrayList<>();
        String[] products = getResources().getStringArray(R.array.supplier_product);
        for (int i = 0; i < products.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put(MENU_DATA_KEY, products[i]);
            mMenuData1.add(map);
        }
        mMenuData2 = new ArrayList<>();
        String[] sorts = getResources().getStringArray(R.array.supplier_sort);
        for (int i = 0; i < sorts.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put(MENU_DATA_KEY, sorts[i]);
            mMenuData2.add(map);
        }
        mMenuData3 = new ArrayList<>();
        String[] activitys = getResources().getStringArray(R.array.supplier_activity);
        for (int i = 0; i < activitys.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put(MENU_DATA_KEY, activitys[i]);
            mMenuData3.add(map);
        }

        dataList = new ArrayList<Map<Integer, Object>>();
        img = new int[]{R.drawable.ic_kindergarten_1, R.drawable.ic_kindergarten_2, R.drawable.ic_kindergarten_3,
                R.drawable.ic_kindergarten_4, R.drawable.ic_kindergarten_5, R.drawable.ic_kindergarten_6, R.drawable.ic_kindergarten_7,
                R.drawable.ic_kindergarten_8, R.drawable.ic_kindergarten_9, R.drawable.ic_kindergarten_10, R.drawable.ic_kindergarten_11,
                R.drawable.ic_kindergarten_12, R.drawable.ic_kindergarten_13, R.drawable.ic_kindergarten_14, R.drawable.ic_kindergarten_15};
        title = new String[]{"新中华幼儿园",
                "未来之星幼儿园",
                "123蒙氏多元幼儿园",
                "动100国际幼儿园",
                "周氏阳光幼儿园",
                "交运星幼儿园",
                "福龙幼儿园",
                "小智慧家幼儿园",
                "幸福一家幼儿园",
                "未来创造高级幼儿园",
                "优启稚慧幼儿园",
                "美之翼MZYOU幼儿园",
                "启蒙幼儿园",
                "贝迪尔BEDEAR幼儿园",
                "小天使幼儿园" };
        description = new String[]{" 幼儿园毗邻民心河，环境优美、空气清新，户外活动场地宽敞，配有大型玩具和橡胶地垫。园内环境布置丰富多彩，富有特色充分体现了环境的教育性和儿童化特点。",
                "一切为了孩子，创建一所让幼儿健康快乐成长，使家长安心放心工作，促教师和谐进取的一流幼儿园。",
                "以优化的教育环境、和谐进取的教职工队伍，培养健康活泼、快乐自信、积极向上的健康儿童为目标，努力打造优质一流的幼儿园。",
                "以爱为本，关注幼儿身心健康，努力实施健康、快乐的教育。",
                "让孩子快乐、让家长放心、让社会满意",
                "幼儿食谱由专人制订，结合幼儿生理特点和生长发育的特殊需要，优化组合，合理搭配食物，使膳食中的各种营养全面、平衡、适量",
                "以蒙特梭利的教育理念为基础，关注每一位幼儿的成长过程",
                "环境优美，设施齐全，功能多样。配有新风换气、中央空调、净化饮水、触摸一体机、交互投影、自动钢琴、幼儿娱乐等设施",
                "幼儿园是美国北卡罗利国际幼儿园姐妹园，被评为“社会组织评估AAAAA等级”单位，“中国十佳特色幼儿",
                "创建一流的示范幼儿园。 课程愿景：构建“四化”课程体系，促进师幼和谐发展",
                "拥有一支逐步走向年轻化、专业化、高素质、高学历、有思想、有特长、会思考、会研究的师资队伍",
                "引领孩子回归生活、回归自然、体验温情。在丰富多彩的生活中感悟、探索、学习，自由成长，适宜生存，释放生命的灵性",
                " 幼儿园毗邻民心河，环境优美、空气清新，户外活动场地宽敞，配有大型玩具和橡胶地垫。",
                "每班幼儿活动室、盥洗室、寝室一体化的空间方便了幼儿生活和游戏，园内环境布置丰富多彩，富有特色充分体现了环境的教育性和儿童化特点。" ,
                "园内多功能厅、美术教室、幼儿阅读室等功能教室，从多方面满足了幼儿发展的需要。"};

        location = new String[]{
                "龙亭北路南段33号","体育路15号","包公湖北路希望小学对面","金明大道北段164号","金明西街尚客优旁边","集英街与体育路交叉路口","公园路18号",
                "迎宾路与向阳路交叉口","向阳路第一高中旁边","育红街44号","工农路公民广场","益农街北段3号","建设路东段","青年路西段","文明街58号" };

        for(int i = 0 ;i< 15 ;i++){
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, img[i]);
            map.put(2, title[i]);
            map.put(3, description[i]);
            map.put(4 ,location[i]);
            dataList.add(map);
        }
        for(int i = 0 ;i< 15 ;i++){
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, img[i]);
            map.put(2, title[i]);
            map.put(3, description[i]);
            map.put(4 ,location[i]);
            dataList.add(map);
        }


    }

    @Override
    public void onSucceed(int what, Response<String> response) {
    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.around_toolbar);
        mToolbar.inflateMenu(R.menu.around_title_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.around_toolbar_menu_map:
                        openActivity(LocationActivity.class);
                        break;
                }
                return true;
            }
        });

        mSupplierListTitleTv = (TextView) view.findViewById(R.id.around_supplier_list_title_tv);
        mSupplierListCartIv = (ImageView) view.findViewById(R.id.around_supplier_list_cart_iv);
        mTitleBarLayout = (FrameLayout) view.findViewById(R.id.around_titleBar_layout);
        mSupplierListTvProduct = (TextView) view.findViewById(R.id.around_supplier_list_tv_product);
        mSupplierListTvSort = (TextView) view.findViewById(R.id.around_supplier_list_tv_sort);
        mSupplierListTvActivity = (TextView) view.findViewById(R.id.around_supplier_list_tv_activity);
        mSupplierListProduct = (LinearLayout) view.findViewById(R.id.around_supplier_list_product);
        mSupplierListProduct.setOnClickListener(this);
        mSupplierListSort = (LinearLayout) view.findViewById(R.id.around_supplier_list_sort);
        mSupplierListSort.setOnClickListener(this);
        mSupplierListActivity = (LinearLayout) view.findViewById(R.id.around_supplier_list_activity);
        mSupplierListActivity.setOnClickListener(this);
        mListView = (ListView) view.findViewById(R.id.zeyuan);

        mListZeYuan =(com.handmark.pulltorefresh.library.PullToRefreshListView)view.findViewById(R.id.zeyuan);
        MyAdapter adapter = new MyAdapter(getActivity(), dataList);
        mListZeYuan.setAdapter(adapter);
    }

    private void initPopMenu() {
        View popView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_popwin_supplier_list, null);
        mPopListView = (ListView) popView.findViewById(R.id.popwin_list_view);
        mPopupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mSupplierListTvProduct.setTextColor(getResources().getColor(R.color.around_supplier_title_color));
                mSupplierListTvSort.setTextColor(getResources().getColor(R.color.around_supplier_title_color));
                mSupplierListTvActivity.setTextColor(getResources().getColor(R.color.around_supplier_title_color));
            }
        });

        popView.findViewById(R.id.popwin_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        mMenuAdapter1 = new SimpleAdapter(getActivity(), mMenuData1, R.layout.item_popwin_list,
                new String[]{"name"}, new int[]{R.id.item_popwin_tv});
        mMenuAdapter2 = new SimpleAdapter(getActivity(), mMenuData2, R.layout.item_popwin_list,
                new String[]{"name"}, new int[]{R.id.item_popwin_tv});
        mMenuAdapter3 = new SimpleAdapter(getActivity(), mMenuData3, R.layout.item_popwin_list,
                new String[]{"name"}, new int[]{R.id.item_popwin_tv});
        mPopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (supplierMenuIndex) {
                    case 0:
                        mSupplierListTvProduct.setText(mMenuData1.get(i).get(MENU_DATA_KEY));
                        mPopupWindow.dismiss();
                        break;
                    case 1:
                        mSupplierListTvSort.setText(mMenuData2.get(i).get(MENU_DATA_KEY));
                        mPopupWindow.dismiss();
                        break;
                    case 2:
                        mSupplierListTvActivity.setText(mMenuData3.get(i).get(MENU_DATA_KEY));
                        mPopupWindow.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.around_supplier_list_product:
                mSupplierListTvProduct.setTextColor(getResources().getColor(R.color.around_supplier_title_selected_color));
                mPopListView.setAdapter(mMenuAdapter1);
                mPopupWindow.showAsDropDown(mSupplierListProduct, 0, 2);
                supplierMenuIndex = 0;
                break;
            case R.id.around_supplier_list_sort:
                mSupplierListTvSort.setTextColor(getResources().getColor(R.color.around_supplier_title_selected_color));
                mPopListView.setAdapter(mMenuAdapter2);
                mPopupWindow.showAsDropDown(mSupplierListSort, 0, 2);
                supplierMenuIndex = 1;
                break;
            case R.id.around_supplier_list_activity:
                mSupplierListTvActivity.setTextColor(getResources().getColor(R.color.around_supplier_title_selected_color));
                mPopListView.setAdapter(mMenuAdapter3);
                mPopupWindow.showAsDropDown(mSupplierListActivity, 0, 2);
                supplierMenuIndex = 2;
                break;
            default:
                break;
        }
    }
}


/**
 * 自定义适配器
 *
 * @author qiangzi
 *
 */
class MyAdapter extends BaseAdapter {
    private List<Map<Integer, Object>> dataList;
    private Context context;

    /**
     * 有参构造
     *
     * @param context
     *            界面
     * @param dataList
     *            数据
     */
    public MyAdapter(Context context, List<Map<Integer, Object>> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public Object getItem(int index) {

        return dataList.get(index);
    }

    @Override
    public long getItemId(int index) {

        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup arg2) {
        /**
         * 根据listView工作原理，列表项的个数只创建屏幕第一次显示的个数。
         * 之后就不会再创建列表项xml文件的对象，以及xml内部的组件，优化内存，性能效率
         */
            // 给xml布局文件创建java对象
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_arround_list_zeyuan, null);
            // 指向布局文件内部组件
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView description = (TextView) view.findViewById(R.id.description);
            TextView location =(TextView)view.findViewById(R.id.location);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            // 增加额外变量

        // 获取数据显示在各组件
        Map<Integer, Object> map = dataList.get(index);
        title.setText((String) map.get(2));
        description.setText((String) map.get(3));
        location.setText(HomeFragment.mCityNameString + "市" + (String)map.get(4));
        if((Integer) map.get(1)!= null){
           img.setImageResource((Integer) map.get(1));
        }
        return view;
    }

}