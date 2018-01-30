package com.example.tr.myapplication.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.library.PinnedHeaderItemDecoration;
import com.example.library.callback.OnHeaderClickAdapter;
import com.example.library.customview.LoadingView;
import com.example.library.utils.NetworkUtils;
import com.example.tr.myapplication.R;
import com.example.tr.myapplication.factory.JSONFactory;
import com.example.tr.myapplication.model.BillkEntity;
import com.example.tr.myapplication.presenter.BillActivityPresenter;
import com.example.tr.myapplication.presenter.ViewBase;
import com.example.tr.myapplication.utils.JsonUtils;
import com.example.tr.myapplication.utils.To;
import com.example.tr.myapplication.view.adapter.BillAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by TR on 2018/1/28.
 * 账单明细列表 如有疑问至邮箱 1964451275@qq.com
 */

public class BillActivity extends BaseActivity<BillActivityPresenter> implements LoadingView.OnRetryListener, ViewBase<String> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_views)
    RecyclerView mRecyclerView;
    @BindView(R.id.bill_loading)
    LoadingView mLoadingView;
    private BillAdapter mAdapter;


    @Override
    protected BillActivityPresenter onCreatePresenter() {
        return new BillActivityPresenter(this, this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_billk;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        mRecyclerView.setVisibility(View.GONE);
        switch (id) {
            case R.id.to_bill:
                initData();
                break;
            case R.id.no_data:
                mAdapter.getData().clear();
                mLoadingView.notifyDataChanged(LoadingView.State.empty);
                break;

            case R.id.to_data:
                initData();
                break;

            case R.id.network_err:
                mAdapter.getData().clear();
                mLoadingView.notifyDataChanged(LoadingView.State.error);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mPresenter.getBillData();
    }

    @Override
    protected void initEvent() {
        mLoadingView.setOnRetryListener(this);

        OnHeaderClickAdapter clickAdapter = new OnHeaderClickAdapter() {

            @Override
            public void onHeaderClick(View view, int id, int position) {
                switch (id) {
                    case R.id.details_rl:
                        // case OnItemTouchListener.HEADER_ID:
                        To.ShowToast(BillActivity.this, "头部年月: " + mAdapter.getData().get(position).billHeaderTime);
                        Log.i(TAG, "onHeaderClick: " + "支出: " + mAdapter.getData().get(position).spending);
                        Log.i(TAG, "onHeaderClick: " + "收入: " + mAdapter.getData().get(position).income);

                        break;
                    case R.id.details_image:
                        To.ShowToast(BillActivity.this, "点击了时间图标");
                        break;
                }
            }
        };


        PinnedHeaderItemDecoration mHeaderItemDecoration = new PinnedHeaderItemDecoration.
                Builder(BillkEntity.BillInfo.TYPE_HEADER).setDividerId(R.drawable.divider).enableDivider(true)
                .setClickIds(R.id.details_image, R.id.details_rl).disableHeaderClick(false)
                .setHeaderClickListener(clickAdapter)
                .disableHeaderClick(false)
                .setHeaderImageListener(new PinnedHeaderItemDecoration.OnHeaderisShowImageLister() {
                    @Override
                    public void isShow(View view, int position) {
                        Log.i(TAG, "isShow: 当前到达顶部的为 : " + position);
                        ((ImageView) view.findViewById(R.id.details_image)).setVisibility(View.VISIBLE);

                    }
                })
                .create();
        mRecyclerView.addItemDecoration(mHeaderItemDecoration);

        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.details_item_r) {
                    To.ShowToast(BillActivity.this, "点击了类型二条目的类型为: " + mAdapter.getData().get(position).getCreated_at());
                    Log.i("BaseActivity", "onItemChildClick: " + position);
                }
            }

            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int i) {

            }
        });


        // 因为添加了1个头部，他是不在clickAdapter.getData这个数据里面的，所以这里要设置数据的偏移值告知ItemDecoration真正的数据索引
        mHeaderItemDecoration.setDataPositionOffset(mAdapter.getHeaderLayoutCount());
    }


    /**
     * 无网络点击回掉
     */
    @Override
    public void onRetry() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            To.ShowToast(this, "请确保网络正常");
            return;
        }
        initData();
    }

    @Override
    public void showDialog() {
        mLoading.show();
    }

    @Override
    public void onNetworks() {

    }

    @Override
    public void hideDialog() {
        mLoading.dismiss();
    }

    @Override
    public void onSuccess(String bean) {
        List<BillkEntity.BillInfo> data = new ArrayList<>();
        BillkEntity billkEntity = JSONFactory.fromJson(JsonUtils.getJson(this, "rasking.json"), BillkEntity.class);


        data.add(new BillkEntity.BillInfo(BillkEntity.BillInfo.TYPE_HEADER, "2018年1月", "100.01", "28"));
        for (BillkEntity.BillInfo info : billkEntity.January_list) {
            info.setItemType(BillkEntity.BillInfo.TYPE_DATA);
            data.add(info);
        }
        data.add(new BillkEntity.BillInfo(BillkEntity.BillInfo.TYPE_HEADER, "2018年12月", "100.02", "27"));
        for (BillkEntity.BillInfo info : billkEntity.december_list) {
            info.setItemType(BillkEntity.BillInfo.TYPE_DATA);
            data.add(info);
        }
        data.add(new BillkEntity.BillInfo(BillkEntity.BillInfo.TYPE_HEADER, "2018年11月", "100.03", "26"));
        for (BillkEntity.BillInfo info : billkEntity.november_list) {
            info.setItemType(BillkEntity.BillInfo.TYPE_DATA);
            data.add(info);
        }
        data.add(new BillkEntity.BillInfo(BillkEntity.BillInfo.TYPE_HEADER, "2018年10月", "100.04", "25"));
        for (BillkEntity.BillInfo info : billkEntity.october_list) {
            info.setItemType(BillkEntity.BillInfo.TYPE_DATA);
            data.add(info);
        }


        mAdapter = new BillAdapter(data);
        mRecyclerView.setAdapter(mAdapter);

    }
}
