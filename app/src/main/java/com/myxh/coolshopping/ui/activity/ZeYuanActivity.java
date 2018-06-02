package com.myxh.coolshopping.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.TwoStatePreference;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.myxh.coolshopping.R;
import com.myxh.coolshopping.ui.fragment.HomeFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZeYuanActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    LinearLayout mLOne , mLTwo ,mlThree;
    TextView mTOne , mTTwo ,mTThree;
    MyAdapter adapter;
    static int img1[] = null;
    static String title1[] = null;
    static String description1[] = null;
    static String code1[] = null;

    static int img2[] = null;
    static String title2[] = null;
    static String description2[] = null;
    static String code2[] = null;

    static int img3[] = null;
    static String title3[] = null;
    static String description3[] = null;
    static String code3[] = null;

    static String searchTxt = null;
    static boolean one = false;
    static boolean two = false;
    static boolean three = false;
    private List<Map<Integer,Object>> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_zeyuan);
        Intent intent = getIntent();
        searchTxt = intent.getStringExtra("search_txt");
        dataList = new ArrayList<Map<Integer, Object>>();
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLOne = (LinearLayout)findViewById(R.id.around_supplier_list_product);
        mLTwo = (LinearLayout)findViewById(R.id.around_supplier_list_sort);
        mlThree = (LinearLayout)findViewById(R.id.around_supplier_list_activity);
        mTOne = (TextView)findViewById(R.id.around_supplier_list_tv_product);
        mTTwo = (TextView)findViewById(R.id.around_supplier_list_tv_sort);
        mTThree = (TextView)findViewById(R.id.around_supplier_list_tv_activity);
        mLayoutManager = new LinearLayoutManager(ZeYuanActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        for(int i = 0 ;i< 15 ;i++){
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, img1[i]);
            map.put(2, title1[i]);
            map.put(3, description1[i]);
            map.put(4 ,code1[i]);
            dataList.add(map);
        }
        adapter = new MyAdapter(this, dataList ,searchTxt);
        mRecyclerView.setAdapter(adapter);
        one = true ;
        two = false;
        three = false;
        mTOne.setTextColor(this.getResources().getColor(R.color.home_title_bar_color));
        mTTwo.setTextColor(this.getResources().getColor(R.color.gray01));
        mTThree.setTextColor(this.getResources().getColor(R.color.gray01));
        /*WebView webView = (WebView)findViewById(R.id.around_listView);
        webView.loadUrl("file:///android_asset/index.html");*/
        mLOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!one){
                    dataList.clear();
                    for(int i = 0 ;i< 15 ;i++){
                        Map<Integer, Object> map = new HashMap<Integer, Object>();
                        map.put(1, img1[i]);
                        map.put(2, title1[i]);
                        map.put(3, description1[i]);
                        map.put(4 ,code1[i]);
                        dataList.add(map);
                    }
                    adapter = new MyAdapter(ZeYuanActivity.this, dataList ,searchTxt);
                    mRecyclerView.setAdapter(adapter);
                    one = true ;
                    two = false;
                    three = false;
                    mTOne.setTextColor(getResources().getColor(R.color.home_title_bar_color));
                    mTTwo.setTextColor(getResources().getColor(R.color.gray01));
                    mTThree.setTextColor(getResources().getColor(R.color.gray01));
                }
            }
        });

        mLTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!two){
                    dataList.clear();
                    for(int i = 0 ;i< 15 ;i++){
                        Map<Integer, Object> map = new HashMap<Integer, Object>();
                        map.put(1, img2[i]);
                        map.put(2, title2[i]);
                        map.put(3, description2[i]);
                        map.put(4 ,code2[i]);
                        dataList.add(map);
                    }
                    adapter = new MyAdapter(ZeYuanActivity.this, dataList ,searchTxt);
                    mRecyclerView.setAdapter(adapter);
                    one = false ;
                    two = true;
                    three = false;
                    mTOne.setTextColor(getResources().getColor(R.color.gray01));
                    mTTwo.setTextColor(getResources().getColor(R.color.home_title_bar_color));
                    mTThree.setTextColor(getResources().getColor(R.color.gray01));
                }
            }
        });

        mlThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!three){
                    dataList.clear();
                    for(int i = 0 ;i< 15 ;i++){
                        Map<Integer, Object> map = new HashMap<Integer, Object>();
                        map.put(1, img3[i]);
                        map.put(2, title3[i]);
                        map.put(3, description3[i]);
                        map.put(4 ,code3[i]);
                        dataList.add(map);
                    }
                    adapter = new MyAdapter(ZeYuanActivity.this, dataList ,searchTxt);
                    mRecyclerView.setAdapter(adapter);
                    one = false ;
                    two = false;
                    three = true;
                    mTOne.setTextColor(getResources().getColor(R.color.gray01));
                    mTTwo.setTextColor(getResources().getColor(R.color.gray01));
                    mTThree.setTextColor(getResources().getColor(R.color.home_title_bar_color));

                }
            }
        });
    }

    void initData(){
        img1 = new int[]{R.drawable.ic_kindergarten_1, R.drawable.ic_kindergarten_2, R.drawable.ic_kindergarten_3,
                R.drawable.ic_kindergarten_4, R.drawable.ic_kindergarten_5, R.drawable.ic_kindergarten_6, R.drawable.ic_kindergarten_7,
                R.drawable.ic_kindergarten_8, R.drawable.ic_kindergarten_9, R.drawable.ic_kindergarten_10, R.drawable.ic_kindergarten_11,
                R.drawable.ic_kindergarten_12, R.drawable.ic_kindergarten_13, R.drawable.ic_kindergarten_14, R.drawable.ic_kindergarten_15};
        title1 = new String[]{"新中华幼儿园",
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
        description1 = new String[]{" 幼儿园毗邻民心河，环境优美、空气清新，户外活动场地宽敞，配有大型玩具和橡胶地垫。园内环境布置丰富多彩，富有特色充分体现了环境的教育性和儿童化特点。",
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

        code1 =new String[]{
          "8.5" , "9.8" , "7.2" , "9.5" , "8.8" , "6.9" , "9.2" , "8.3" , "9.5" , "8.6" , "5.4" , "9.5" , "9.9" , "7.3"  ,"8.7"
        };

        img2 = new int[]{R.drawable.ic_kindergarten_13, R.drawable.ic_kindergarten_2, R.drawable.ic_kindergarten_9,
                R.drawable.ic_kindergarten_4, R.drawable.ic_kindergarten_12, R.drawable.ic_kindergarten_5, R.drawable.ic_kindergarten_15,
                R.drawable.ic_kindergarten_10, R.drawable.ic_kindergarten_1, R.drawable.ic_kindergarten_8, R.drawable.ic_kindergarten_11,
                R.drawable.ic_kindergarten_14, R.drawable.ic_kindergarten_3, R.drawable.ic_kindergarten_6, R.drawable.ic_kindergarten_11};
        title2 = new String[]{
                "启蒙幼儿园",
                "未来之星幼儿园",
                "新中华幼儿园",
                "福龙幼儿园",
                "动100国际幼儿园",
                "美之翼MZYOU幼儿园",
                "小天使幼儿园",
                "幸福一家幼儿园",
                "交运星幼儿园",
                "小智慧家幼儿园",
                "优启稚慧幼儿园",
                "未来创造高级幼儿园",
                "贝迪尔BEDEAR幼儿园",
                "123蒙氏多元幼儿园",
                "周氏阳光幼儿园"};
        description2 = new String[]{
                "一切为了孩子，创建一所让幼儿健康快乐成长，使家长安心放心工作，促教师和谐进取的一流幼儿园。",
                "以蒙特梭利的教育理念为基础，关注每一位幼儿的成长过程",
                "以优化的教育环境、和谐进取的教职工队伍，培养健康活泼、快乐自信、积极向上的健康儿童为目标，努力打造优质一流的幼儿园。",
                "以爱为本，关注幼儿身心健康，努力实施健康、快乐的教育。",
                "让孩子快乐、让家长放心、让社会满意",
                "幼儿食谱由专人制订，结合幼儿生理特点和生长发育的特殊需要，优化组合，合理搭配食物，使膳食中的各种营养全面、平衡、适量",
                "环境优美，设施齐全，功能多样。配有新风换气、中央空调、净化饮水、触摸一体机、交互投影、自动钢琴、幼儿娱乐等设施",
                "幼儿园是美国北卡罗利国际幼儿园姐妹园，被评为“社会组织评估AAAAA等级”单位，“中国十佳特色幼儿",
                "创建一流的示范幼儿园。 课程愿景：构建“四化”课程体系，促进师幼和谐发展",
                "引领孩子回归生活、回归自然、体验温情。在丰富多彩的生活中感悟、探索、学习，自由成长，适宜生存，释放生命的灵性",
                "拥有一支逐步走向年轻化、专业化、高素质、高学历、有思想、有特长、会思考、会研究的师资队伍",
                " 幼儿园毗邻民心河，环境优美、空气清新，户外活动场地宽敞，配有大型玩具和橡胶地垫。",
                "每班幼儿活动室、盥洗室、寝室一体化的空间方便了幼儿生活和游戏，园内环境布置丰富多彩，富有特色充分体现了环境的教育性和儿童化特点。" ,
                "园内多功能厅、美术教室、幼儿阅读室等功能教室，从多方面满足了幼儿发展的需要。",
                "幼儿园毗邻民心河，环境优美、空气清新，户外活动场地宽敞，配有大型玩具和橡胶地垫。园内环境布置丰富多彩，富有特色充分体现了环境的教育性和儿童化特点。"};
        code2 =new String[]{
                "9.9" , "9.8" , "9.5" , "9.5" , "9.5" , "9.2" , "8.8" , "8.7" , "8.6" , "8.5" , "8.3" , "7.3" , "7.2" , "6.9"  ,"5.4"
        };

        img3 = new int[]{R.drawable.ic_kindergarten_13, R.drawable.ic_kindergarten_4, R.drawable.ic_kindergarten_9,
                R.drawable.ic_kindergarten_2, R.drawable.ic_kindergarten_12, R.drawable.ic_kindergarten_5, R.drawable.ic_kindergarten_15,
                R.drawable.ic_kindergarten_10, R.drawable.ic_kindergarten_11, R.drawable.ic_kindergarten_8, R.drawable.ic_kindergarten_1,
                R.drawable.ic_kindergarten_6, R.drawable.ic_kindergarten_3, R.drawable.ic_kindergarten_14, R.drawable.ic_kindergarten_11};
        title3 = new String[]{
                "启蒙幼儿园",
                "新中华幼儿园",
                "福龙幼儿园",
                "未来之星幼儿园",
                "动100国际幼儿园",
                "美之翼MZYOU幼儿园",
                "小天使幼儿园",
                "幸福一家幼儿园",
                "交运星幼儿园",
                "小智慧家幼儿园",
                "优启稚慧幼儿园",
                "未来创造高级幼儿园",
                "贝迪尔BEDEAR幼儿园",
                "123蒙氏多元幼儿园",
                "周氏阳光幼儿园"};
        description3 = new String[]{
                "一切为了孩子，创建一所让幼儿健康快乐成长，使家长安心放心工作，促教师和谐进取的一流幼儿园。",
                "以优化的教育环境、和谐进取的教职工队伍，培养健康活泼、快乐自信、积极向上的健康儿童为目标，努力打造优质一流的幼儿园。",
                "以爱为本，关注幼儿身心健康，努力实施健康、快乐的教育。",
                "以蒙特梭利的教育理念为基础，关注每一位幼儿的成长过程",
                "让孩子快乐、让家长放心、让社会满意",
                " 幼儿园毗邻民心河，环境优美、空气清新，户外活动场地宽敞，配有大型玩具和橡胶地垫。",
                "每班幼儿活动室、盥洗室、寝室一体化的空间方便了幼儿生活和游戏，园内环境布置丰富多彩，富有特色充分体现了环境的教育性和儿童化特点。" ,
                "园内多功能厅、美术教室、幼儿阅读室等功能教室，从多方面满足了幼儿发展的需要。",
                "幼儿食谱由专人制订，结合幼儿生理特点和生长发育的特殊需要，优化组合，合理搭配食物，使膳食中的各种营养全面、平衡、适量",
                "环境优美，设施齐全，功能多样。配有新风换气、中央空调、净化饮水、触摸一体机、交互投影、自动钢琴、幼儿娱乐等设施",
                "幼儿园是美国北卡罗利国际幼儿园姐妹园，被评为“社会组织评估AAAAA等级”单位，“中国十佳特色幼儿",
                "拥有一支逐步走向年轻化、专业化、高素质、高学历、有思想、有特长、会思考、会研究的师资队伍",
                "创建一流的示范幼儿园。 课程愿景：构建“四化”课程体系，促进师幼和谐发展",
                "引领孩子回归生活、回归自然、体验温情。在丰富多彩的生活中感悟、探索、学习，自由成长，适宜生存，释放生命的灵性",
                "幼儿园毗邻民心河，环境优美、空气清新，户外活动场地宽敞，配有大型玩具和橡胶地垫。园内环境布置丰富多彩，富有特色充分体现了环境的教育性和儿童化特点。"};
        code3 =new String[]{
                "9.9" , "9.5" , "9.5" , "9.8" , "9.5" , "8.8" , "9.2" , "8.7" , "8.6" , "8.5" , "8.3" , "7.3" , "6.9" , "7.2"  ,"5.4"
        };
    }
}


/**
 * 自定义适配器
 *
 * @author qiangzi
 *
 */
class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Map<Integer, Object>> dataList;
    private Context context;
    private String searchTxt ;
    private LayoutInflater mInflater;

    /**
     * 有参构造
     *
     * @param context
     *            界面
     * @param dataList
     *            数据
     */
    public MyAdapter(Context context, List<Map<Integer, Object>> dataList ,String searchtTxt) {
        this.context = context;
        this.dataList = dataList;
        this.searchTxt = searchtTxt;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder  vh = null;
        mInflater = LayoutInflater.from(context);
        //判断viewtype类型返回不同Viewholder
        vh = new ViewHolder(mInflater.inflate(R.layout.layout_recyclerview_item, parent,false));
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Map<Integer, Object> map = dataList.get(position);
        String title = searchTxt + (String) map.get(2);
        holder.title.setText(title);
        holder.description.setText((String) map.get(3));
        holder.code.setText((String) map.get(4));
        if((Integer) map.get(1)!= null){
            holder.img.setImageResource((Integer) map.get(1));
        }
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView description;
        public TextView code;
        public ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            code =(TextView)itemView.findViewById(R.id.code);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
