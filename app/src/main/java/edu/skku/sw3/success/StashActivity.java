package edu.skku.sw3.success;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/*
    TODO : (Stash) Naming 다시하기 (file 이름, id)
    TODO : (Stash) Layout 다듬기
    TODO : (Stash) View.OnClickListener onClickSubCategory 함수 정의
 */

public class StashActivity extends AppCompatActivity {
    ImageButton back;
    ImageButton edit;
    Button del;
//    Button btn1;
//    Button btn2;
//    Button btn3;

    //int sstash
    //int cstash
    //int jstash

    int editcheck = 0;
    int editselect = 0;
    int tabselect = 1;

    LinearLayout container;

    ListView listview1;
    ListView listview2;
    ListView listview3;

    ArrayList<HashMap<String,String>> list_1;
    ArrayList<HashMap<String,String>> list_2;
    ArrayList<HashMap<String,String>> list_3;
    HashMap<String,String> listcontent;

    SimpleAdapter simpleAdapter1;
    SimpleAdapter simpleAdapter2;
    SimpleAdapter simpleAdapter3;

    private RecyclerView categoryListView, categorySubListView;
    private CategoryAdapter categoryAdapter, categorySubAdapter;
    // 사이트 지정후 내부 항목을 보여줍니다.
    private View.OnClickListener onClickCategory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /* Action after click main category */
            String str = (String) v.getTag();
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            setSubCategory();
        }
    };
    private View.OnClickListener onClickSubCategory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 사이트 내부 공지사항 종류를 선택했을 때 행동
            String str = (String) v.getTag();
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stash);

        back = findViewById(R.id.back);
        edit = findViewById(R.id.edit);
        del = findViewById(R.id.delete);
//        btn1 = findViewById(R.id.skkuhome);
//        btn2 = findViewById(R.id.College);
//        btn3 = findViewById(R.id.job);
        container = (LinearLayout)findViewById(R.id.container);
        categoryListView = findViewById(R.id.stash_category);
        categorySubListView = findViewById(R.id.stash_category_sub);

        list_1 = new ArrayList<HashMap<String, String>>();
        listcontent = new HashMap<String, String>();
        listcontent.put("Title","성균관대 공지사항");
        listcontent.put("date","190511");
        list_1.add(listcontent);
        listcontent = new HashMap<String, String>();
        listcontent.put("Title","예시1");
        listcontent.put("date","190512");
        list_1.add(listcontent);

        list_2 = new ArrayList<HashMap<String, String>>();
        listcontent = new HashMap<String, String>();
        listcontent.put("Title","소프트웨어학과 공지사항");
        listcontent.put("date","190511");
        list_2.add(listcontent);
        listcontent = new HashMap<String, String>();
        listcontent.put("Title","예시2");
        listcontent.put("date","190512");
        list_2.add(listcontent);

        list_3 = new ArrayList<HashMap<String, String>>();
        listcontent = new HashMap<String, String>();
        listcontent.put("Title","학생인재개발팀 공지사항");
        listcontent.put("date","190511");
        list_3.add(listcontent);
        listcontent = new HashMap<String, String>();
        listcontent.put("Title","예시3");
        listcontent.put("date","190512");
        list_3.add(listcontent);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sub1, container, true);
        listview1 = findViewById(R.id.list1);
        simpleAdapter1 = new SimpleAdapter(StashActivity.this, list_1,android.R.layout.simple_list_item_2,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
        listview1.setAdapter(simpleAdapter1);

        back.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        setCategory();

//        btn1.setOnClickListener(new Button.OnClickListener(){
//            public void onClick(View v){
//                if(tabselect == 1){
//
//                }else if(editcheck == 1 && tabselect != 1){
//                    tabselect = 1;
//
//                    container.removeAllViews();
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    inflater.inflate(R.layout.sub1, container, true);
//                    listview1 = findViewById(R.id.list1);
//                    simpleAdapter1 = new SimpleAdapter(StashActivity.this, list_1,android.R.layout.simple_list_item_multiple_choice,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
//                    listview1.setAdapter(simpleAdapter1);
//                    listview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
//                }else{
//                    tabselect = 1;
//
//                    container.removeAllViews();
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    inflater.inflate(R.layout.sub1, container, true);
//                    listview1 = findViewById(R.id.list1);
//                    simpleAdapter1 = new SimpleAdapter(StashActivity.this, list_1,android.R.layout.simple_list_item_2,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
//                    listview1.setAdapter(simpleAdapter1);
//                }
//            }
//        });
//
//        btn2.setOnClickListener(new Button.OnClickListener(){
//            public void onClick(View v){
//                if(tabselect == 2){
//
//                }else if(editcheck == 1 && tabselect != 2){
//                    tabselect = 2;
//
//                    container.removeAllViews();
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    inflater.inflate(R.layout.sub2, container, true);
//                    listview2 = findViewById(R.id.list2);
//                    simpleAdapter2 = new SimpleAdapter(StashActivity.this, list_2,android.R.layout.simple_list_item_multiple_choice,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
//                    listview2.setAdapter(simpleAdapter2);
//                    listview2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//                }else{
//                    tabselect = 2;
//
//                    container.removeAllViews();
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    inflater.inflate(R.layout.sub2, container, true);
//                    listview2 = findViewById(R.id.list2);
//                    simpleAdapter2 = new SimpleAdapter(StashActivity.this, list_2,android.R.layout.simple_list_item_2,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
//                    listview2.setAdapter(simpleAdapter2);
//                }
//            }
//        });
//
//        btn3.setOnClickListener(new Button.OnClickListener(){
//            public void onClick(View v){
//                if(tabselect == 3){
//
//                }else if(editcheck == 1 && tabselect != 3){
//                    tabselect = 3;
//
//                    container.removeAllViews();
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    inflater.inflate(R.layout.sub3, container, true);
//                    listview3 = findViewById(R.id.list3);
//                    simpleAdapter3 = new SimpleAdapter(StashActivity.this, list_3,android.R.layout.simple_list_item_multiple_choice,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
//                    listview3.setAdapter(simpleAdapter3);
//                    listview3.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//                }
//                else{
//                    tabselect = 3;
//
//                    container.removeAllViews();
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    inflater.inflate(R.layout.sub3, container, true);
//                    listview3 = findViewById(R.id.list3);
//                    simpleAdapter3 = new SimpleAdapter(StashActivity.this, list_3,android.R.layout.simple_list_item_2,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
//                    listview3.setAdapter(simpleAdapter3);
//                }
//            }
//        });

        edit.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(editcheck == 0) {
                    editcheck++;
                    if(tabselect == 1){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.sub1, container, true);
                        listview1 = findViewById(R.id.list1);
                        simpleAdapter1 = new SimpleAdapter(StashActivity.this, list_1,android.R.layout.simple_list_item_multiple_choice,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
                        listview1.setAdapter(simpleAdapter1);
                        listview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    }else if(tabselect == 2){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.sub2, container, true);
                        listview2 = findViewById(R.id.list2);
                        simpleAdapter2 = new SimpleAdapter(StashActivity.this, list_2,android.R.layout.simple_list_item_multiple_choice,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
                        listview2.setAdapter(simpleAdapter2);
                        listview2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    }else{
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.sub3, container, true);
                        listview3 = findViewById(R.id.list3);
                        simpleAdapter3 = new SimpleAdapter(StashActivity.this, list_3,android.R.layout.simple_list_item_multiple_choice,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
                        listview3.setAdapter(simpleAdapter3);
                        listview3.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    }
                    del.setVisibility(View.VISIBLE);
                    del.setClickable(true);
                }else{
                    editcheck--;
                    del.setVisibility(View.INVISIBLE);
                    del.setClickable(false);
                    if(tabselect == 1){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.sub1, container, true);
                        listview1 = findViewById(R.id.list1);
                        simpleAdapter1 = new SimpleAdapter(StashActivity.this, list_1,android.R.layout.simple_list_item_2,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
                        listview1.setAdapter(simpleAdapter1);
                    }else if(tabselect == 2){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.sub2, container, true);
                        listview2 = findViewById(R.id.list2);
                        simpleAdapter2 = new SimpleAdapter(StashActivity.this, list_2,android.R.layout.simple_list_item_2,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
                        listview2.setAdapter(simpleAdapter2);
                    }else if(tabselect == 3){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.sub3, container, true);
                        listview3 = findViewById(R.id.list3);
                        simpleAdapter3 = new SimpleAdapter(StashActivity.this, list_3,android.R.layout.simple_list_item_2,new String[]{"Title","date"},new int[]{android.R.id.text1,android.R.id.text2});
                        listview3.setAdapter(simpleAdapter3);
                    }
                }
            }
        });

        del.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(editcheck == 1){
                    if(tabselect == 1){
                        SparseBooleanArray sb1 = listview1.getCheckedItemPositions();
                        if(sb1.size() !=0){
                            for(int i = listview1.getCount() - 1; i >= 0; i--){
                                if(sb1.get(i)){
                                    list_1.remove(i);
                                }
                            }
                            listview1.clearChoices();;
                            simpleAdapter1.notifyDataSetChanged();
                        }
                    }
                    else if(tabselect == 2){
                        SparseBooleanArray sb2 = listview2.getCheckedItemPositions();
                        if(sb2.size() !=0){
                            for(int i = listview2.getCount() - 1; i >= 0; i--){
                                if(sb2.get(i)){
                                    list_2.remove(i);
                                }
                            }
                            listview2.clearChoices();;
                            simpleAdapter2.notifyDataSetChanged();
                        }
                    }
                    else if(tabselect == 3){
                        SparseBooleanArray sb3 = listview3.getCheckedItemPositions();
                        if(sb3.size() !=0){
                            for(int i = listview3.getCount() - 1; i >= 0; i--){
                                if(sb3.get(i)){
                                    list_3.remove(i);
                                }
                            }
                            listview3.clearChoices();;
                            simpleAdapter3.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    // 사이트 항목을 설정합니다
    private void setCategory() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryListView.setLayoutManager(layoutManager);

        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("소프트웨어");
        categoryList.add("학사 공지");
        categoryList.add("정보통신대학");
        categoryList.add("성균관대 공홈");
        categoryList.add("학생지원팀");
        categoryList.add("전자전기");

        categoryAdapter = new CategoryAdapter(this, categoryList, onClickCategory);
        categoryListView.setAdapter(categoryAdapter);
    }

    // 사이트 내부 공지사항 종류를 설정합니다.
    private void setSubCategory() {
        categorySubListView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categorySubListView.setLayoutManager(layoutManager);

        ArrayList<String> categorySubList = new ArrayList<>();
        categorySubList.add("취업");
        categorySubList.add("공지사항");
        categorySubAdapter = new CategoryAdapter(this, categorySubList, onClickSubCategory);
        categorySubListView.setAdapter(categorySubAdapter);
    }
}
