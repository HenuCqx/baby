package com.myxh.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.myxh.baby.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    ImageView mImg;
    ListView mListView;
    TextView mTitle ,mLocation ,mDescrion ,mNumber ,mTv;
    EditText mEd;
    InputMethodManager imm;
    static String userComment[] = null;
    static String cComment[] = null;
    static String timeComment[] = null;
    static int imgComment[] = null;
    private List<Map<Integer,Object>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title" );
        String description = intent.getStringExtra("description");
        String location = intent.getStringExtra("location");
        int img = intent.getIntExtra("position" ,0);

        mImg = (ImageView)findViewById(R.id.img);
        mTitle = (TextView)findViewById(R.id.title);
        mLocation = (TextView)findViewById(R.id.location);
        mDescrion = (TextView)findViewById(R.id.description);
        mNumber = (TextView)findViewById(R.id.number);
        mListView = (ListView)findViewById(R.id.listView);
        mTv = (TextView)findViewById(R.id.tv);
        mEd = (EditText)findViewById(R.id.ed);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imgComment = new int[]{R.drawable.ic_kindergarten_1, R.drawable.ic_kindergarten_2, R.drawable.ic_kindergarten_3,
                R.drawable.ic_kindergarten_4, R.drawable.ic_kindergarten_5, R.drawable.ic_kindergarten_6, R.drawable.ic_kindergarten_7,
                R.drawable.ic_kindergarten_8, R.drawable.ic_kindergarten_9, R.drawable.ic_kindergarten_10, R.drawable.ic_kindergarten_11,
                R.drawable.ic_kindergarten_12, R.drawable.ic_kindergarten_13, R.drawable.ic_kindergarten_14, R.drawable.ic_kindergarten_15 ,
                R.drawable.ic_kindergarten_1, R.drawable.ic_kindergarten_2, R.drawable.ic_kindergarten_3,
                R.drawable.ic_kindergarten_4, R.drawable.ic_kindergarten_5, R.drawable.ic_kindergarten_6, R.drawable.ic_kindergarten_7,
                R.drawable.ic_kindergarten_8, R.drawable.ic_kindergarten_9, R.drawable.ic_kindergarten_10, R.drawable.ic_kindergarten_11,
                R.drawable.ic_kindergarten_12, R.drawable.ic_kindergarten_13, R.drawable.ic_kindergarten_14, R.drawable.ic_kindergarten_15};
        userComment = new String[]{
                "嘿系哦也",
                "月光宝盒",
                "小萝卜",
                "兔子宝贝儿",
                "叶梦星空",
                "孩子他妈最漂亮",
                "哎呀哦为",
                "佳人不在",
                "爱我家傻蛋",
                "宝宝不哭"};
        cComment = new String[]{"老师Q群上线的时间也非常少.不知道老师是否可以安排下比较合适的时间，让家长可以了解宝宝在幼儿园的情况",
                "老师教的画等也挺好的.我们可以通过这个窗口看到宝宝在幼儿园的学习活动情况",
                "我家的宝宝刚进幼儿园半年了，感觉老师很有耐心，每天中午都会给我打电话汇报孩子在幼儿园的情况",
                "我家宝宝在这里学会了自己主动撒尿,穿脱衣裤.吃饭.收拾玩具.虽然在家里还不够自觉,但还是可以看到他的进步和老师所花费的心血",
                "园长很有耐心，一直给我们普及幼儿的知识，感觉不错，实话实说",
                "幼儿园都很不错，老师们都很照顾孩子！",
                "这个园的老师和园长很好呀，我家孩子在幼儿园生活了3年",
                "孩子在幼儿园玩得很开心，也很感谢老师一直照顾孩子",
                "儿子去第一天晚上回家就告诉我不去幼儿园，说怕老师，老师打胳膊，我让你看孩子，教育孩子",
                "老师素质差，孩子去了后没有任何进步，还添了一堆坏毛病"};

        timeComment = new String[]{
                "2018.04.09","2018.02.23","2018.01.15","2017.11.21","2017.11.14","2017.09.05","2017.06.30",
                "2017.03.15","2017.01.28","2016.10.22" };

        mImg.setImageResource(imgComment[img-1]);
        mTitle.setText(title);
        mLocation.setText(location);
        mDescrion.setText(description);

        dataList = new ArrayList<Map<Integer, Object>>();
        for(int i = 0 ;i< 10 ;i++){
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, userComment[i]);
            map.put(2, timeComment[i]);
            map.put(3, cComment[i]);
            dataList.add(map);
        }
        CommentAdapter adapter = new CommentAdapter(CommentActivity.this, dataList);
        mListView.setAdapter(adapter);
        mNumber.setText(String.valueOf(dataList.size()) + "条评论");

        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEd.getText() != null && !("".equals(mEd.getText().toString()))){
                    Map<Integer, Object> map = new HashMap<Integer, Object>();
                    map.put(1, "静静她妈咪");
                    Calendar calendar = Calendar.getInstance();
                    //年
                    int year = calendar.get(Calendar.YEAR);
                    //月
                    int month = calendar.get(Calendar.MONTH)+1;
                    String dayString = null;
                    String monthString = null;
                    if(month<10){
                        monthString = "0" + String.valueOf(month);
                    }else {
                       monthString = String.valueOf(month);
                    }
                    //日
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    if (day<10){
                        dayString = "0" + String.valueOf(day);
                    }else {
                        dayString = String.valueOf(day);
                    }
                    map.put(2, String.valueOf(year) + "." + monthString +"." + dayString);
                    map.put(3, mEd.getText().toString());
                    dataList.add(0 ,map);
                    CommentAdapter adapter = new CommentAdapter(CommentActivity.this, dataList);
                    mListView.setAdapter(adapter);
                    mNumber.setText(String.valueOf(dataList.size()) + "条评论");
                    mEd.setText(null);
                    mEd.clearFocus();
                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
    }

}


class CommentAdapter extends BaseAdapter {
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
    public CommentAdapter(Context context, List<Map<Integer, Object>> dataList) {
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
        view = inflater.inflate(R.layout.layout_comment_item, null);
        // 指向布局文件内部组件
        TextView user = (TextView) view.findViewById(R.id.user);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView comment =(TextView)view.findViewById(R.id.comment);
        // 增加额外变量

        // 获取数据显示在各组件
        Map<Integer, Object> map = dataList.get(index);
        user.setText((String) map.get(1));
        time.setText((String) map.get(2));
        comment.setText((String)map.get(3));
        return view;
    }

}
