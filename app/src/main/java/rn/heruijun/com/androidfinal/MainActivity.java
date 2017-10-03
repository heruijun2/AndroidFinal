package rn.heruijun.com.androidfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rn.heruijun.com.androidfinal.adapter.HomeAdapter;
import rn.heruijun.com.androidfinal.inc.OnItemClickListener;
import rn.heruijun.com.androidfinal.view.deco.DividerGridItemDecoration;
import rn.heruijun.com.androidfinal.view.deco.DividerItemDecoration;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView tv_close;
    private List<String> mList;
    RecyclerView mRecyclerView;
    HomeAdapter mHomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView() {
        tv_close = (TextView) this.findViewById(R.id.tv_close);
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        mToolbar.setTitle("Toolbar");
        setSupportActionBar(mToolbar);
        //是否给左上角图标的左边加上一个返回的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setTitle("带进度的对话框");
                        progressDialog.setMessage("加载中...");
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                        break;
                    case R.id.action_share:
                        Toast.makeText(MainActivity.this, "action_share", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //设置侧或布局
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.id_drawerlayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        //使用Patette
        setPatette();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        //设置GridView
        // setGridView();
        //设置ListView
        setListView();
        //设置瀑布流
//        setWaterfallView();
    }

    private void setPatette() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(swatch.getRgb()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void setListView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdapter = new HomeAdapter(this, mList);
        setLister();
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    public void setGridView() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdapter = new HomeAdapter(this, mList);
        setLister();
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    private void setLister() {
        mHomeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Toast.makeText(MainActivity.this, "点击第" + position + "条", Toast.LENGTH_SHORT).show();
                Intent intent = null;
                if (position == 0) {
                    intent = new Intent(MainActivity.this, ViewMoveActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    intent = new Intent(MainActivity.this, MyWebviewActivity.class);
                    startActivity(intent);
                } else if (position == 2) {
                    intent = new Intent(MainActivity.this, DownloadActivity.class);
                    startActivity(intent);
                } else if (position == 3) {
                    intent = new Intent(MainActivity.this, VasSonicActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, final int position) {
//                new AlertDialog.Builder(MainActivity.this).setTitle("确认删除吗？")
//                        .setNegativeButton("取消", null)
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                mHomeAdapter.removeData(position);
//                            }
//                        }).show();
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        String l_1 = "View滑动";
        mList.add(l_1);
        String l_2 = "简单的webview";
        mList.add(l_2);
        String l_3 = "多线程下载";
        mList.add(l_3);
        String l_4 = "VasSonic研究";
        mList.add(l_4);
    }
}
