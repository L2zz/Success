package edu.skku.sw3.success;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText searchEdit;
    private ImageButton searchBtn, storeBtn, setBtn;
    private RecyclerView categoryListView, categorySubListView;
    private ListView mainContent;

    private DrawerLayout drawer;
    private ListView drawerContent;
    private Button drawerCancelBtn, drawerConfirmBtn;

    private CategoryAdapter categoryAdapter, categorySubAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEdit = findViewById(R.id.main_search_edit);
        searchBtn = findViewById(R.id.main_search_btn);
        storeBtn = findViewById(R.id.main_store_btn);
        setBtn = findViewById(R.id.main_set_btn);
        categoryListView = findViewById(R.id.main_category);
        categorySubListView = findViewById(R.id.main_category_sub);
        mainContent = findViewById(R.id.main_content_list);
        drawer = findViewById(R.id.main_container);
        drawerContent = findViewById(R.id.main_drawer_content);
        drawerCancelBtn = findViewById(R.id.main_drawer_cancel_btn);
        drawerConfirmBtn = findViewById(R.id.main_drawer_confirm_btn);

        initCategory();
        setDrawer();

        storeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StashActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initCategory() {
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

        categoryAdapter = new CategoryAdapter(this, categoryList, new CategoryAdapter.CategoryOnClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                categoryAdapter.setLastSelectedIndex(position);
                categoryAdapter.notifyDataSetChanged();
                initSubCategory(position);
            }
        });
        categoryListView.setAdapter(categoryAdapter);
    }

    private void initSubCategory(int pos) {
        categorySubListView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categorySubListView.setLayoutManager(layoutManager);

        // Get sub category using pos
        ArrayList<String> categorySubList = new ArrayList<>();
        categorySubList.add("취업");
        categorySubList.add("공지사항");

        categorySubAdapter = new CategoryAdapter(this, categorySubList, new CategoryAdapter.CategoryOnClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                categorySubAdapter.setLastSelectedIndex(position);
                categorySubAdapter.notifyDataSetChanged();
            }
        });
        categorySubListView.setAdapter(categorySubAdapter);
    }

    private void setDrawer() {
        final String[] items = {"소프트웨어", "학사공지", "전자전기", "정보통신"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        drawerContent.setAdapter(adapter);

        drawerContent.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), new Integer(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        drawerCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.RIGHT);
            }
        });

        drawerConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Set target sites */
                drawer.closeDrawer(Gravity.RIGHT);
            }
        });

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });
    }
}
