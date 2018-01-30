package com.example.tr.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tr.myapplication.model.PinnedHeaderEntity;
import com.example.tr.myapplication.presenter.BasePresenter;
import com.example.tr.myapplication.view.BaseActivity;
import com.example.tr.myapplication.view.BillActivity;
import com.example.tr.myapplication.view.adapter.BaseHeaderAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("billHeader");
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.to_bill:
                startActivity(new Intent(this, BillActivity.class));
                break;
            case R.id.no_data:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
