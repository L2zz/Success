package edu.skku.sw3.success;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private EditText searchEdit;
    private ImageButton logoutBtn, searchBtn, draftBtn, setBtn;
    private RecyclerView siteListView, categoryListView;
    private ListView mainContent;
    private DrawerLayout drawer;
    private ListView drawerSiteListView;
    private Button drawerCancelBtn, drawerConfirmBtn;
    private TabAdapter siteAdapter, categoryAdapter;
    private DrawerSiteAdapter drawerAdapter;
    private LoadingDialog loadingDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    private FirebaseAuth mAuth;
    private FirebaseUser curUser;
    private DatabaseReference mDatabase;
    private ArrayList<Integer> userSiteKey;
    private ArrayList<String> userSiteList;
    private SparseBooleanArray userSiteBArray;
    private Site curSite;
    private Category curCategory;
    private ArrayList<ListItem> mainItemList;
    private ListAdapter mainAdapter;

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

        swipeRefreshLayout = findViewById(R.id.main_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initDrawer();
        initSite();

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

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainItemList = new ArrayList<>();
                mainAdapter = new ListAdapter(MainActivity.this, mainItemList);
                mainContent.setAdapter(mainAdapter);

                mDatabase.child("site/").addListenerForSingleValueEvent(searchListener);
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

        userSiteKey = new ArrayList<>();
        userSiteList = new ArrayList<>();

        siteAdapter = new TabAdapter(this, userSiteList, new TabAdapter.CategoryOnClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                siteAdapter.setLastSelectedIndex(position);
                siteAdapter.notifyDataSetChanged();
                setSite(userSiteList.get(position));
            }
        });
        siteListView.setAdapter(siteAdapter);

        mDatabase.child("user/"+curUser.getUid()+"/favorite/").addListenerForSingleValueEvent(userSiteListener);
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

        mainItemList = new ArrayList<>();
        mainAdapter = new ListAdapter(this, mainItemList);
        mainContent.setAdapter(mainAdapter);

        mainContent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("보관함에 추가");
                builder.setCancelable(false);
                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String key = mDatabase.child("user/"+curUser.getUid()+"/stash/").push().getKey();
                                mDatabase.child("user/"+curUser.getUid()+"/stash/"+key+"/siteKey/").setValue(curSite.getId());
                                mDatabase.child("user/"+curUser.getUid()+"/stash/"+key+"/categoryKey/").setValue(curCategory.getId());
                                mDatabase.child("user/"+curUser.getUid()+"/stash/"+key+"/articleKey/").setValue(mainItemList.get(position).getItemKey());
                                CookieBar.build(MainActivity.this)
                                        .setTitle("보관함")
                                        .setMessage("보관함에 "+mainItemList.get(position).getTitle()+ " 을 추가하였습니다")
                                        .setCookiePosition(CookieBar.TOP)
                                        .setBackgroundColor(R.color.colorPrimaryDark)
                                        .show();
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                return true;
            }
        });

        mainContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("web_url", mainItemList.get(position).getItemURL());
                startActivity(intent);
            }
        });

        mDatabase.child("site/"+curSite.getId()+"/category/"+curCategory.getId()+"/article/")
                .addListenerForSingleValueEvent(articleListener);
    }

    private void initDrawer() {
        userSiteBArray = new SparseBooleanArray();
        drawerAdapter = new DrawerSiteAdapter(availSiteList, userSiteBArray);
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
                SparseBooleanArray checkedItems = drawerAdapter.getSelected();

                for (int i = availSiteList.size()-1; i >= 0; i--) {
                    mDatabase.child("user/"+curUser.getUid()+"/favorite/" + i).setValue(null);
                    if (checkedItems.get(i)) {
                        mDatabase.child("user/"+curUser.getUid()+"/favorite/" + i + "/siteKey/").setValue(i);
                    }
                }
                categoryListView.setVisibility(View.GONE);
                initSite();
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

    private ValueEventListener userSiteListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            loadingDialog = new LoadingDialog(MainActivity.this);
            if (!loadingDialog.isShowing()) loadingDialog.show();

            for (DataSnapshot data: dataSnapshot.getChildren()) {
                userSiteKey.add(new Integer(data.getKey()));
                userSiteBArray.put(new Integer(data.getKey()), true);
            }
            drawerAdapter.notifyDataSetChanged();

            try {
                for (int i = 0; i < userSiteKey.size(); i++) {
                    siteAdapter.addItem(availSiteList.get(userSiteKey.get(i)).getTitle());
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (loadingDialog.isShowing()) loadingDialog.dismiss();
                }
            };
            timer.schedule(timerTask, 500);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private ValueEventListener articleListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            loadingDialog = new LoadingDialog(MainActivity.this);
            if (!loadingDialog.isShowing()) loadingDialog.show();
            for (DataSnapshot data: dataSnapshot.getChildren()) {
                mainAdapter.addItem(0, new ListItem(
                        data.child("title").getValue(String.class),
                        data.child("date").getValue(String.class),
                        data.child("url").getValue(String.class),
                        data.getKey()
                ));
            }

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (loadingDialog.isShowing()) loadingDialog.dismiss();
                }
            };
            timer.schedule(timerTask, 500);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private ValueEventListener searchListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            loadingDialog = new LoadingDialog(MainActivity.this);
            if (!loadingDialog.isShowing()) loadingDialog.show();
            if (searchEdit.getText().toString().replaceAll(" ", "").length() != 0) {
                for (DataSnapshot site: dataSnapshot.getChildren()) {
                    for (DataSnapshot category: site.child("category").getChildren()) {
                        for (DataSnapshot article: category.child("article").getChildren()) {
                            if (article.child("title").getValue(String.class).toLowerCase().
                                    contains(searchEdit.getText().toString().toLowerCase())) {
                                mainAdapter.addItem(0, new ListItem(
                                        article.child("title").getValue(String.class),
                                        article.child("date").getValue(String.class),
                                        article.child("url").getValue(String.class),
                                        article.getKey()
                                ));
                            }
                        }
                    }
                }
            }
            searchEdit.setText("");
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (loadingDialog.isShowing()) loadingDialog.dismiss();
                }
            };
            timer.schedule(timerTask, 500);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

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
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        try {
            setCategory(curCategory);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
