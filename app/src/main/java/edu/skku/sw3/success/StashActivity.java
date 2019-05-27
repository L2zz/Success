package edu.skku.sw3.success;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

    int editcheck = 0;
    int tabselect = 0;
    int i;
    String categorytag = "";
    LinearLayout container;

    ListView listview1;
    ListView listview2;
    ListView listview3;
    ListView listview4;
    ListView listview5;
    ListView sublistview;

    ArrayList<Integer> BGarray;
    ArrayList<ListItem> list_1;
    ArrayList<ListItem> list_2;
    ArrayList<ListItem> list_3;
    ArrayList<ListItem> list_4;
    ArrayList<ListItem> list_5;
    ArrayList<ListItem> sublist;

    ListAdapter ladapter;

    private RecyclerView categoryListView, categorySubListView;
    private CategoryAdapter categoryAdapter, categorySubAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stash);

        back = findViewById(R.id.back);
        edit = findViewById(R.id.edit);
        del = findViewById(R.id.delete);
        container = (LinearLayout)findViewById(R.id.container);
        categoryListView = findViewById(R.id.stash_category);
        categorySubListView = findViewById(R.id.stash_category_sub);

        list_1 = new ArrayList<ListItem>();
        ListItem LItem = new ListItem("예시1","20190529","공지사항");
        list_1.add(LItem);
        LItem = new ListItem("예시2","190512","취업");
        list_1.add(LItem);

        list_2 = new ArrayList<ListItem>();
        LItem = new ListItem("예시3","190511","공지사항");
        list_2.add(LItem);
        LItem = new ListItem("예시4","190512","취업");
        list_2.add(LItem);

        list_3 = new ArrayList<ListItem>();
        LItem = new ListItem("예시5","190511","공지사항");
        list_3.add(LItem);
        LItem = new ListItem("예시6","190512","취업");
        list_3.add(LItem);

        list_4 = new ArrayList<ListItem>();
        LItem = new ListItem("예시7","190524","공지사항");
        list_4.add(LItem);
        LItem = new ListItem("예시8","190524","취업");
        list_4.add(LItem);

        list_5 = new ArrayList<ListItem>();
        LItem = new ListItem("예시9","190524","공지사항");
        list_5.add(LItem);
        LItem = new ListItem("예시10","190524","취업");
        list_5.add(LItem);

        back.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        setCategory();

        edit.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(editcheck == 0) {
                    editcheck++;
                    BGarray = new ArrayList<Integer>();
                    if(tabselect == 1){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.container_layout, container, true);
                        listview1 = findViewById(R.id.list_container);
                        ladapter = new ListAdapter(StashActivity.this,list_1);
                        listview1.setAdapter(ladapter);
                        listview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        for(i = 0;i < list_1.size() ; i++){
                            BGarray.add(i,0);
                        }
                        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(BGarray.get(position) == 0){
                                    view.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                                    BGarray.set(position,1);
                                }
                                else if(BGarray.get(position) == 1){
                                    view.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                                    BGarray.set(position,0);
                                }
                            }
                        });

                    }else if(tabselect == 2){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.container_layout, container, true);
                        listview2 = findViewById(R.id.list_container);
                        ladapter = new ListAdapter(StashActivity.this,list_2);
                        listview2.setAdapter(ladapter);
                        listview2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        for(i = 0;i < list_2.size() ; i++){
                            BGarray.add(i,0);
                        }
                        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(BGarray.get(position) == 0){
                                    view.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                                    BGarray.set(position,1);
                                }
                                else if(BGarray.get(position) == 1){
                                    view.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                                    BGarray.set(position,0);
                                }
                            }
                        });
                    }else if(tabselect == 3){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.container_layout, container, true);
                        listview3 = findViewById(R.id.list_container);
                        ladapter = new ListAdapter(StashActivity.this,list_3);
                        listview3.setAdapter(ladapter);
                        listview3.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        for(i = 0;i < list_3.size() ; i++){
                            BGarray.add(i,0);
                        }
                        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(BGarray.get(position) == 0){
                                    view.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                                    BGarray.set(position,1);
                                }
                                else if(BGarray.get(position) == 1){
                                    view.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                                    BGarray.set(position,0);
                                }
                            }
                        });
                    }else if(tabselect == 4){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.container_layout, container, true);
                        listview4 = findViewById(R.id.list_container);
                        ladapter = new ListAdapter(StashActivity.this,list_4);
                        listview4.setAdapter(ladapter);
                        listview4.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        for(i = 0;i < list_4.size() ; i++){
                            BGarray.add(i,0);
                        }
                        listview4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(BGarray.get(position) == 0){
                                    view.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                                    BGarray.set(position,1);
                                }
                                else if(BGarray.get(position) == 1){
                                    view.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                                    BGarray.set(position,0);
                                }
                            }
                        });
                    }else if(tabselect == 5){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.container_layout, container, true);
                        listview5 = findViewById(R.id.list_container);
                        ladapter = new ListAdapter(StashActivity.this,list_3);
                        listview5.setAdapter(ladapter);
                        listview5.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        for(i = 0;i < list_5.size() ; i++){
                            BGarray.add(i,0);
                        }
                        listview5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(BGarray != null){
                                    if(BGarray.get(position) == 0){
                                        view.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                                        BGarray.set(position,1);
                                    }
                                    else if(BGarray.get(position) == 1) {
                                        view.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                                        BGarray.set(position, 0);
                                    }
                                }
                            }
                        });
                    }
                    del.setVisibility(View.VISIBLE);
                    del.setClickable(true);
                }else{
                    editcheck--;
                    BGarray = new ArrayList<Integer>();
                    del.setVisibility(View.INVISIBLE);
                    del.setClickable(false);
                    if(tabselect == 1){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.container_layout, container, true);
                        listview1 = findViewById(R.id.list_container);
                        ladapter = new ListAdapter(StashActivity.this,list_1);
                        listview1.setAdapter(ladapter);
                    }else if(tabselect == 2){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.container_layout, container, true);
                        listview2 = findViewById(R.id.list_container);
                        ladapter = new ListAdapter(StashActivity.this,list_2);
                        listview2.setAdapter(ladapter);
                    }else if(tabselect == 3){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.container_layout, container, true);
                        listview3 = findViewById(R.id.list_container);
                        ladapter = new ListAdapter(StashActivity.this,list_3);
                        listview3.setAdapter(ladapter);
                    }else if(tabselect == 4){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.container_layout, container, true);
                        listview4 = findViewById(R.id.list_container);
                        ladapter = new ListAdapter(StashActivity.this,list_4);
                        listview4.setAdapter(ladapter);
                    }else if(tabselect == 5){
                        container.removeAllViews();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inflater.inflate(R.layout.container_layout, container, true);
                        listview5 = findViewById(R.id.list_container);
                        ladapter = new ListAdapter(StashActivity.this,list_5);
                        listview5.setAdapter(ladapter);
                    }
                }
            }
        });

        del.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(editcheck == 1){
                    BGarray = new ArrayList<Integer>();
                    if(tabselect == 1){
                        SparseBooleanArray sb = listview1.getCheckedItemPositions();
                        if(sb.size() !=0){
                            for(int i = listview1.getCount() - 1; i >= 0; i--){
                                if(sb.get(i)){
                                    list_1.remove(i);
                                }
                            }
                            listview1.clearChoices();
                            ladapter = new ListAdapter(StashActivity.this,list_1);
                            listview1.setAdapter(ladapter);
                            for(i=0;i<list_1.size();i++){
                                BGarray.add(i,0);
                            }
                        }
                    }
                    else if(tabselect == 2){
                        SparseBooleanArray sb = listview2.getCheckedItemPositions();
                        if(sb.size() !=0){
                            for(int i = listview2.getCount() - 1; i >= 0; i--){
                                if(sb.get(i)){
                                    list_2.remove(i);
                                }
                            }
                            listview2.clearChoices();
                            ladapter = new ListAdapter(StashActivity.this,list_2);
                            listview2.setAdapter(ladapter);
                            for(i=0;i<list_2.size();i++){
                                BGarray.add(i,0);
                            }
                        }
                    }
                    else if(tabselect == 3){
                        SparseBooleanArray sb = listview3.getCheckedItemPositions();
                        if(sb.size() !=0){
                            for(int i = listview3.getCount() - 1; i >= 0; i--){
                                if(sb.get(i)){
                                    list_3.remove(i);
                                }
                            }
                            listview3.clearChoices();
                            ladapter = new ListAdapter(StashActivity.this,list_3);
                            listview3.setAdapter(ladapter);
                            for(i=0;i<list_3.size();i++){
                                BGarray.add(i,0);
                            }
                        }
                    }
                    else if(tabselect == 4){
                        SparseBooleanArray sb = listview4.getCheckedItemPositions();
                        if(sb.size() !=0){
                            for(int i = listview4.getCount() - 1; i >= 0; i--){
                                if(sb.get(i)){
                                    list_4.remove(i);
                                }
                            }
                            listview4.clearChoices();
                            ladapter = new ListAdapter(StashActivity.this,list_4);
                            listview4.setAdapter(ladapter);
                            for(i=0;i<list_4.size();i++){
                                BGarray.add(i,0);
                            }
                        }
                    }
                    else if(tabselect == 5){
                        SparseBooleanArray sb = listview5.getCheckedItemPositions();
                        if(sb.size() !=0){
                            for(int i = listview5.getCount() - 1; i >= 0; i--){
                                if(sb.get(i)){
                                    list_5.remove(i);
                                }
                            }
                            listview5.clearChoices();
                            ladapter = new ListAdapter(StashActivity.this,list_5);
                            listview5.setAdapter(ladapter);
                            for(i=0;i<list_5.size();i++){
                                BGarray.add(i,0);
                            }
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
        categoryList.add("성균관대");
        categoryList.add("학사공지");
        categoryList.add("소프트웨어");
        categoryList.add("정보통신대학");
        categoryList.add("학생지원팀");

        categoryAdapter = new CategoryAdapter(this, categoryList, new CategoryAdapter.CategoryOnClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                categoryAdapter.setLastSelectedIndex(position);
                categoryAdapter.notifyDataSetChanged();
                if(editcheck == 1){
                    editcheck--;
                    del.setVisibility(View.INVISIBLE);
                    del.setClickable(false);
                }

                if(position == 1){
                    tabselect = 1;
                    container.removeAllViews();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.container_layout, container, true);
                    listview1 = findViewById(R.id.list_container);
                    ladapter = new ListAdapter(StashActivity.this,list_1);
                    listview1.setAdapter(ladapter);
                }
                else if(position == 2){
                    tabselect = 2;
                    container.removeAllViews();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.container_layout, container, true);
                    listview2 = findViewById(R.id.list_container);
                    ladapter = new ListAdapter(StashActivity.this,list_2);
                    listview2.setAdapter(ladapter);
                }
                else if(position == 3){
                    tabselect = 3;
                    container.removeAllViews();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.container_layout, container, true);
                    listview3 = findViewById(R.id.list_container);
                    ladapter = new ListAdapter(StashActivity.this,list_3);
                    listview3.setAdapter(ladapter);
                }
                else if(position == 4){
                    tabselect = 4;
                    container.removeAllViews();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.container_layout, container, true);
                    listview4 = findViewById(R.id.list_container);
                    ladapter = new ListAdapter(StashActivity.this,list_4);
                    listview4.setAdapter(ladapter);
                }
                else if(position == 5){
                    tabselect = 5;
                    container.removeAllViews();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.container_layout, container, true);
                    listview5 = findViewById(R.id.list_container);
                    ladapter = new ListAdapter(StashActivity.this,list_5);
                    listview5.setAdapter(ladapter);
                }
                initSubCategory(position);
            }
        });
        categoryListView.setAdapter(categoryAdapter);
    }

    // 사이트 내부 공지사항 종류를 설정합니다.
    private void initSubCategory(int pos) {
        categorySubListView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categorySubListView.setLayoutManager(layoutManager);

        ArrayList<String> categorySubList = new ArrayList<>();
        categorySubList.add("공지사항");
        categorySubList.add("취업");
        categorySubAdapter = new CategoryAdapter(this, categorySubList, new CategoryAdapter.CategoryOnClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                categorySubAdapter.setLastSelectedIndex(position);
                categorySubAdapter.notifyDataSetChanged();
                if(editcheck == 1){
                    del.setVisibility(View.INVISIBLE);
                    del.setClickable(false);
                }
                sublist = new ArrayList<ListItem>();
                if(position == 1){
                    if(tabselect == 1){
                        for(int i = 0; i < list_1.size(); i++){
                            if(list_1.get(i).getCategory() == "공지사항"){
                                sublist.add(list_1.get(i));
                            }
                        }
                    }
                    else if(tabselect == 2){
                        for(int i = 0; i < list_2.size(); i++){
                            if(list_2.get(i).getCategory() == "공지사항"){
                                sublist.add(list_2.get(i));
                            }
                        }
                    }
                    else if(tabselect == 3){
                        for(int i = 0; i < list_3.size(); i++){
                            if(list_3.get(i).getCategory() == "공지사항"){
                                sublist.add(list_3.get(i));
                            }
                        }
                    }
                    else if(tabselect == 4){
                        for(int i = 0; i < list_4.size(); i++){
                            if(list_4.get(i).getCategory() == "공지사항"){
                                sublist.add(list_4.get(i));
                            }
                        }
                    }
                    else if(tabselect == 5){
                        for(int i = 0; i < list_5.size(); i++){
                            if(list_5.get(i).getCategory() == "공지사항"){
                                sublist.add(list_5.get(i));
                            }
                        }
                    }
                }
                else if(position == 2){
                    if(tabselect == 1){
                        for(int i = 0; i < list_1.size(); i++){
                            if(list_1.get(i).getCategory() == "취업"){
                                sublist.add(list_1.get(i));
                            }
                        }
                    }
                    else if(tabselect == 2){
                        for(int i = 0; i < list_2.size(); i++){
                            if(list_2.get(i).getCategory() == "취업"){
                                sublist.add(list_2.get(i));
                            }
                        }
                    }
                    else if(tabselect == 3){
                        for(int i = 0; i < list_3.size(); i++){
                            if(list_3.get(i).getCategory() == "취업"){
                                sublist.add(list_3.get(i));
                            }
                        }
                    }
                    else if(tabselect == 4){
                        for(int i = 0; i < list_4.size(); i++){
                            if(list_4.get(i).getCategory() == "취업"){
                                sublist.add(list_4.get(i));
                            }
                        }
                    }
                    else if(tabselect == 5){
                        for(int i = 0; i < list_5.size(); i++){
                            if(list_5.get(i).getCategory() == "취업"){
                                sublist.add(list_5.get(i));
                            }
                        }
                    }
                }
                container.removeAllViews();
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.container_layout, container, true);
                sublistview = findViewById(R.id.list_container);
                ladapter = new ListAdapter(StashActivity.this,sublist);
                sublistview.setAdapter(ladapter);
            }
        });
        categorySubListView.setAdapter(categorySubAdapter);
    }

}
