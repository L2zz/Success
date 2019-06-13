package edu.skku.sw3.success;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

public class StashActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ImageButton backBtn, editBtn;
    private RecyclerView siteListView, categoryListView;
    private ListView stashContent;
    private TabAdapter siteAdapter, categoryAdapter;
    private LoadingDialog loadingDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    private FirebaseAuth mAuth;
    private FirebaseUser curUser;
    private DatabaseReference mDatabase;
    private ArrayList<Integer> userSiteKey;
    private ArrayList<Integer> userCategoryKey;
    private ArrayList<String> userArticleKey;
    private ArrayList<String> userSiteList;
    private Site curSite;
    private Category curCategory;
    private ArrayList<ListItem> stashItemList;
    private ListAdapter stashAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stash);

        backBtn = findViewById(R.id.back);
        editBtn = findViewById(R.id.edit);
        siteListView = findViewById(R.id.stash_category);
        categoryListView = findViewById(R.id.stash_category_sub);
        stashContent = findViewById(R.id.stash_content);

        swipeRefreshLayout = findViewById(R.id.stash_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initSite();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSite() {
        categoryListView.setVisibility(View.GONE);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        siteListView.setLayoutManager(layoutManager);

        userSiteKey = new ArrayList<>();
        userCategoryKey = new ArrayList<>();
        userArticleKey = new ArrayList<>();
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

        mDatabase.child("user/"+curUser.getUid()+"/stash/").addListenerForSingleValueEvent(userSiteListener);
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

        final ArrayList<String> categoryList = new ArrayList<>();
        for (Integer catKey: userCategoryKey) {
            for (Category category: curSite.getCategories()) {
                if (catKey.equals(category.getId())) categoryList.add(category.getTitle());
            }
        }

        categoryAdapter = new TabAdapter(this, categoryList, new TabAdapter.CategoryOnClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                categoryAdapter.setLastSelectedIndex(position);
                categoryAdapter.notifyDataSetChanged();
                for (Category category: curSite.getCategories()) {
                    if (categoryList.get(position).equals(category.getTitle())) {
                        setCategory(category);
                    }
                }
            }
        });
        categoryListView.setAdapter(categoryAdapter);
    }

    private void setCategory(Category targetCategory) {
        curCategory = targetCategory;

        stashItemList = new ArrayList<>();
        stashAdapter = new ListAdapter(this, stashItemList);
        stashContent.setAdapter(stashAdapter);

        stashContent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StashActivity.this);
                builder.setTitle("보관함에서 삭제");
                builder.setCancelable(false);
                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase.child("user/"+curUser.getUid()+"/stash/")
                                        .addListenerForSingleValueEvent(rmListener);
                                stashItemList.remove(position);
                                stashAdapter.notifyDataSetChanged();
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

        stashContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stashItemList.get(position).getItemURL()));
                startActivity(intent);
            }
        });

        Log.d("Test", "site/"+curSite.getId()+"/category/"+curCategory.getId()+"/article/");
        mDatabase.child("site/"+curSite.getId()+"/category/"+curCategory.getId()+"/article/")
                .addListenerForSingleValueEvent(articleListener);
    }

    private ValueEventListener userSiteListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            loadingDialog = new LoadingDialog(StashActivity.this);
            if (!loadingDialog.isShowing()) loadingDialog.show();

            for (DataSnapshot data: dataSnapshot.getChildren()) {
                userSiteKey.add(new Integer(data.child("siteKey").getValue().toString()));
                userCategoryKey.add(new Integer(data.child("categoryKey").getValue().toString()));
                userArticleKey.add(data.child("articleKey").getValue().toString());
            }

            TreeSet<Integer> tmp1 = new TreeSet<>(userSiteKey);
            userSiteKey = new ArrayList<>(tmp1);
            TreeSet<Integer> tmp2 = new TreeSet<>(userCategoryKey);
            userCategoryKey = new ArrayList<>(tmp2);

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
            loadingDialog = new LoadingDialog(StashActivity.this);
            if (!loadingDialog.isShowing()) loadingDialog.show();

            for (DataSnapshot data: dataSnapshot.getChildren()) {
                for (String key: userArticleKey) {
                    if (key.equals(data.getKey())) {
                        stashAdapter.addItem(0, new ListItem(
                                data.child("title").getValue(String.class),
                                data.child("date").getValue(String.class),
                                data.child("url").getValue(String.class),
                                data.getKey()
                        ));
                    }
                }
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

    private ValueEventListener rmListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            String rmKey = null;
            for (DataSnapshot data: dataSnapshot.getChildren()) {
                if (data.child("siteKey").getValue().toString().equals(curSite.getId().toString())
                && data.child("categoryKey").getValue().toString().equals(curCategory.getId().toString())) {
                    rmKey = data.getKey();
                    break;
                }
            }
            if (rmKey != null) {
                mDatabase.child("user/"+curUser.getUid()+"/stash/"+rmKey).removeValue();
            }
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
        setCategory(curCategory);
    }
}
