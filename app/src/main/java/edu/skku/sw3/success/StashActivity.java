package edu.skku.sw3.success;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
    TODO : (Stash) Naming 다시하기 (file 이름, id)
    TODO : (Stash) Layout 다듬기
    TODO : (Stash) View.OnClickListener onClickSubCategory 함수 정의
 */

public class StashActivity extends AppCompatActivity {
    ImageButton back;
    ImageButton edit;
    Button del;
    String ID = "user1"; // 통합시 LoginActivity에서 intent message로 ID를 전달받아주시면 됩니다.

    String title, date, subcategory, maincategory, itemURL;
    String key;

    int editcheck = 0;
    int tabselect = 0;
    int i;
    LinearLayout container;

    ListView mainstashlistview;
    ListView sublistview;

    ArrayList<Integer> BGarray;
    ArrayList<ListItem> mainstashlist;
    ArrayList<ListItem> sublist;

    ListAdapter ladapter;
    ListItem LItem;
    ArrayList<String> categoryList;

    public RecyclerView categoryListView, categorySubListView;
    public TabAdapter tabAdapter, categorySubAdapter;
    public DatabaseReference mPostReference;

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
        mPostReference = FirebaseDatabase.getInstance().getReference();

        categoryList = new ArrayList<>();
        getCategoryinDatabase();
        //DB에서 카테고리리스트를 채우고, setCategory를 실행. firebase 관련 함수들은 모두 addListenerForSingleEvent -> 한번만 실행됨

        back.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        edit.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(editcheck == 0) {
                    editcheck++;
                    BGarray = new ArrayList<Integer>();
                    container.removeAllViews();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.container_layout, container, true);
                    mainstashlistview = findViewById(R.id.list_container);
                    ladapter = new ListAdapter(StashActivity.this,mainstashlist);
                    mainstashlistview.setAdapter(ladapter);
                    mainstashlistview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    for(i = 0;i < mainstashlist.size() ; i++){
                        BGarray.add(i,0);
                    }
                    mainstashlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    del.setVisibility(View.VISIBLE);
                    del.setClickable(true);
                }else{
                    editcheck--;
                    BGarray = new ArrayList<Integer>();
                    del.setVisibility(View.INVISIBLE);
                    del.setClickable(false);
                    container.removeAllViews();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.container_layout, container, true);
                    mainstashlistview = findViewById(R.id.list_container);
                    ladapter = new ListAdapter(StashActivity.this,mainstashlist);
                    mainstashlistview.setAdapter(ladapter);
                }
            }
        });

        del.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(editcheck == 1){
                    BGarray = new ArrayList<Integer>();
                    SparseBooleanArray sb = mainstashlistview.getCheckedItemPositions();
                    if(sb.size() !=0){
                        for(int i = mainstashlistview.getCount() - 1; i >= 0; i--){
                            if(sb.get(i)){
                                delFirebasedata(mainstashlist.get(i));
                                mainstashlist.remove(i);
                            }
                        }
                        mainstashlistview.clearChoices();
                        ladapter = new ListAdapter(StashActivity.this,mainstashlist);
                        mainstashlistview.setAdapter(ladapter);
                        for(i=0;i<mainstashlist.size();i++){
                            BGarray.add(i,0);
                        }
                    }
                }
            }
        });

    }

    // 사이트 항목을 설정합니다
    public void setCategory() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryListView.setLayoutManager(layoutManager);

        tabAdapter = new TabAdapter(this, categoryList, new TabAdapter.CategoryOnClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                tabAdapter.setLastSelectedIndex(position);
                tabAdapter.notifyDataSetChanged();
                if(editcheck == 1){
                    editcheck--;
                    del.setVisibility(View.INVISIBLE);
                    del.setClickable(false);
                }
                tabselect = position + 1;
                getFirebaseDatabase();

                initSubCategory(position);
            }
        });
        categoryListView.setAdapter(tabAdapter);
    }

    // 사이트 내부 공지사항 종류를 설정합니다.
    public void initSubCategory(int pos) {
        categorySubListView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categorySubListView.setLayoutManager(layoutManager);

        ArrayList<String> categorySubList = new ArrayList<>();
        categorySubList.add("공지사항");
        categorySubList.add("취업");
        categorySubAdapter = new TabAdapter(this, categorySubList, new TabAdapter.CategoryOnClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                categorySubAdapter.setLastSelectedIndex(position);
                categorySubAdapter.notifyDataSetChanged();
                if(editcheck == 1){
                    del.setVisibility(View.INVISIBLE);
                    del.setClickable(false);
                }
                sublist = new ArrayList<ListItem>();
                if(position == 0){
                    for(int i = 0; i < mainstashlist.size(); i++){
                        if(mainstashlist.get(i).getSubCategory().equals("공지사항")){
                            sublist.add(mainstashlist.get(i));
                        }
                    }
                }
                else if(position == 1){
                    for(int i = 0; i < mainstashlist.size(); i++){
                        if(mainstashlist.get(i).getSubCategory().equals("취업")){
                            sublist.add(mainstashlist.get(i));
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
    public void getFirebaseDatabase() { //mainstashlist에 item을 검사해서 넣음
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mainstashlist = new ArrayList<ListItem>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FirebasePost get = postSnapshot.getValue(FirebasePost.class);
                    title = get.Title;
                    date = get.Date;
                    subcategory = get.SubCategory;
                    maincategory = get.MainCategory;
                    itemURL = get.ItemURL;
                    if(maincategory.equals(categoryList.get(tabselect-1))){
                        LItem = new ListItem(title, date, itemURL, new String());
                        if(!mainstashlist.contains(LItem)) {
                            mainstashlist.add(LItem);
                        }
                    }
                }
                container.removeAllViews();
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.container_layout, container, true);
                mainstashlistview = findViewById(R.id.list_container);
                ladapter = new ListAdapter(StashActivity.this,mainstashlist);
                mainstashlistview.setAdapter(ladapter);
                mainstashlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(editcheck == 0){
                            Uri url= Uri.parse(mainstashlist.get(position).getItemURL());
                            Intent intent= new Intent(Intent.ACTION_VIEW, url);
                            if (intent.resolveActivity(getPackageManager()) != null){
                                startActivity(intent);
                            }
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        };
        mPostReference.child("stash_items/"+ID).addListenerForSingleValueEvent(postListener);
    }

    public void delFirebasedata(final ListItem LI){ // item의 Key값을 받음(item node 이름이 Key) 이후 그것을 이용해서 제거
        ValueEventListener delListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FirebasePost get = postSnapshot.getValue(FirebasePost.class);
                    title = get.Title;
                    date = get.Date;
                    subcategory = get.SubCategory;
                    if(LI.getTitle().equals(title) && LI.getDate().equals(date) && LI.getSubCategory().equals(subcategory)){
                        key = get.Key;
                        mPostReference = FirebaseDatabase.getInstance().getReference();
                        Map<String, Object> childUpdates = new HashMap<>();
                        Map<String, Object> postvalues = null;
                        childUpdates.put("/stash_items/"+ID+"/"+key, postvalues);
                        mPostReference.updateChildren(childUpdates);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        mPostReference.child("stash_items/"+ID).addListenerForSingleValueEvent(delListener);
    }

    public void getCategoryinDatabase() { // DB에서 카테고리리스트를 채움
        ValueEventListener CategoryListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FirebasePost get = postSnapshot.getValue(FirebasePost.class);
                    maincategory = get.MainCategory;
                    for(i = 0; i <= categoryList.size(); i++){
                        if(!categoryList.contains(maincategory)){
                            categoryList.add(maincategory);
                        }
                    }
                }
                setCategory();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        mPostReference.child("stash_items/"+ID).addListenerForSingleValueEvent(CategoryListener);
    }

    private final ArrayList<Site> availSiteList = new ArrayList<>(
            Arrays.asList(
                    new Site(0, "성균관대학교", new ArrayList<>(
                            Arrays.asList(
                                    new Category(0, "전체"),
                                    new Category(1, "학사"),
                                    new Category(2, "입학"),
                                    new Category(3, "취업"),
                                    new Category(4, "채용/모집")
                            )
                    )),
                    new Site(1, "정보통신대학", new ArrayList<>(
                            Arrays.asList(
                                    new Category(0, "공지사항"),
                                    new Category(1, "세미나공지"),
                                    new Category(2, "취업정보")
                            )
                    )),
                    new Site(2, "반도체시스템", new ArrayList<>(
                            Arrays.asList(
                                    new Category(0, "공지사항")
                            )
                    )),
                    new Site(3, "공과대학", new ArrayList<>(
                            Arrays.asList(
                                    new Category(0, "전체"),
                                    new Category(1, "학사"),
                                    new Category(2, "채용/모집")
                            )
                    )),
                    new Site(4, "문과대학", new ArrayList<>(
                            Arrays.asList(
                                    new Category(0, "전체")
                            )
                    )),
                    new Site(5, "자연과학대학", new ArrayList<>(
                            Arrays.asList(
                                    new Category(0, "전체")
                            )
                    ))
            )
    );
}
