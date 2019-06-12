package edu.skku.sw3.success;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private EditText searchEdit;
    private ImageButton logoutBtn, searchBtn, draftBtn, setBtn;
    private RecyclerView siteListView, categoryListView;
    private ListView mainContent;
    private DrawerLayout drawer;
    private ListView drawerSiteListView;
    private Button drawerCancelBtn, drawerConfirmBtn;
    private TabAdapter siteAdapter, categoryAdapter;
    private ArrayAdapter drawerAdapter;

    private ArrayList<String> userSiteList;
    private Site curSite;
    private Category curCategory;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutBtn = findViewById(R.id.main_logout_btn);
        searchEdit = findViewById(R.id.main_search_edit);
        searchBtn = findViewById(R.id.main_search_btn);
        draftBtn = findViewById(R.id.main_draft_btn);
        setBtn = findViewById(R.id.main_set_btn);
        siteListView = findViewById(R.id.main_site_tab);
        categoryListView = findViewById(R.id.main_category_tab);
        mainContent = findViewById(R.id.main_content_list);
        drawer = findViewById(R.id.main_container);
        drawerSiteListView = findViewById(R.id.main_drawer_site_list);
        drawerCancelBtn = findViewById(R.id.main_drawer_cancel_btn);
        drawerConfirmBtn = findViewById(R.id.main_drawer_confirm_btn);

        initSite();
        initDrawer();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = getResources().getStringArray(R.array.user_btn_operation);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("로그아웃");
                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                finish();
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            }
        });

        draftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StashActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initSite() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        siteListView.setLayoutManager(layoutManager);

        // =========== For test ============= //
        userSiteList = new ArrayList<>();
        userSiteList.add("성균관대학교");
        userSiteList.add("정보통신대학");
        userSiteList.add("반도체시스템");
        userSiteList.add("공과대학");
        userSiteList.add("문과대학");
        userSiteList.add("자연과학대학");
        // =========== For test ============= //

        // TODO:
        // DB에서 Favorite site 불러오기
        try {

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        ////////////////////////////////////////

        siteAdapter = new TabAdapter(this, userSiteList, new TabAdapter.CategoryOnClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                siteAdapter.setLastSelectedIndex(position);
                siteAdapter.notifyDataSetChanged();
                setSite(userSiteList.get(position));
            }
        });
        siteListView.setAdapter(siteAdapter);
    }

    private void setSite(String siteTitle) {
        for (Site site: availSiteList) {
            if (site.getTitle().equals(siteTitle)) {
                curSite = site;
                break;
            }
        }
        initCategory();
    }

    private void initCategory() {
        categoryListView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryListView.setLayoutManager(layoutManager);

        ArrayList<String> categoryList = new ArrayList<>();
        for (Category category: curSite.getCategories()) {
            categoryList.add(category.getTitle());
        }

        categoryAdapter = new TabAdapter(this, categoryList, new TabAdapter.CategoryOnClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                categoryAdapter.setLastSelectedIndex(position);
                categoryAdapter.notifyDataSetChanged();
                setCategory(curSite.getCategories().get(position));
            }
        });
        categoryListView.setAdapter(categoryAdapter);
    }

    private void setCategory(Category targetCategory) {
        curCategory = targetCategory;
        // TODO:
        // DB에서 category에 해당하는 articles 불러오기
        ////////////////////////////////////////////////
    }

    private void initDrawer() {
        drawerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, availSiteList);
        drawerSiteListView.setAdapter(drawerAdapter);

        drawerSiteListView.setOnItemClickListener(new ListView.OnItemClickListener() {
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
                SparseBooleanArray checkedItems = drawerSiteListView.getCheckedItemPositions();
                int count = drawerAdapter.getCount();

                //
                siteAdapter.removeAll();
                for (int i = 0; i < count; i++) {
                    if (checkedItems.get(i)) {
                        siteAdapter.addItem(drawerAdapter.getItem(i).toString());
                    }
                }
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
