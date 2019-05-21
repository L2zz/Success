package edu.skku.sw3.success;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText searchEdit;
    private ImageButton searchBtn, storeBtn, setBtn;
    private RecyclerView categoryListView, categorySubListView;
    private ListView contentList;

    private MainCategoryAdapter categoryAdapter, categorySubAdapter;
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
            /* Action after click sub category */
            String str = (String) v.getTag();
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
    };

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
        contentList = findViewById(R.id.main_content_list);

        setCategory();
    }

    public void setCategory() {
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
        categoryList.add("기이이이이이이인 내용");

        categoryAdapter = new MainCategoryAdapter(this, categoryList, onClickCategory);
        categoryListView.setAdapter(categoryAdapter);
    }

    public void setSubCategory() {
        categorySubListView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categorySubListView.setLayoutManager(layoutManager);

        ArrayList<String> categorySubList = new ArrayList<>();
        categorySubList.add("취업");
        categorySubList.add("공지사항");
        categorySubAdapter = new MainCategoryAdapter(this, categorySubList, onClickSubCategory);
        categorySubListView.setAdapter(categorySubAdapter);
    }
}
