package com.example.tr.myapplication.view.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.library.utils.FullSpanUtil;
import com.example.tr.myapplication.R;
import com.example.tr.myapplication.model.BillkEntity;

import java.util.List;

/**
 * 直接继承BaseMultiItemQuickAdapter单独实现一个适配器的写法
 */
public class BillAdapter extends BaseMultiItemQuickAdapter<BillkEntity.BillInfo, BaseViewHolder> {

    public BillAdapter(List<BillkEntity.BillInfo> data) {
        super(data);
        addItemType(BillkEntity.BillInfo.TYPE_HEADER, R.layout.details_head_item);
        addItemType(BillkEntity.BillInfo.TYPE_DATA, R.layout.details_item);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        FullSpanUtil.onAttachedToRecyclerView(recyclerView, this, BillkEntity.BillInfo.TYPE_HEADER);
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        FullSpanUtil.onViewAttachedToWindow(holder, this, BillkEntity.BillInfo.TYPE_HEADER);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillkEntity.BillInfo item) {
        switch (helper.getItemViewType()) {

            case BillkEntity.BillInfo.TYPE_HEADER:
                helper.setText(R.id.item_time, item.billHeaderTime);
                helper.setText(R.id.spending,"支出¥ "+item.spending);
                helper.setText(R.id.income,"收入¥: "+item.income);
                break;

            case BillkEntity.BillInfo.TYPE_DATA:


                helper.setText(R.id.trading, item.getBill_name());

//                helper.setText(R.id.details_time, StringUtils.getStrTime(item.getCreated_at()));
                helper.setText(R.id.details_time, item.getCreated_at());
                if (item.getMode().equals("0")) {
                    ((TextView) helper.getView(R.id.details_item_amount)).setTextColor(mContext.getResources().getColor(R.color.details_text));
                    helper.setText(R.id.details_item_amount, "+" + item.getAmount());
                }
                if (item.getMode().equals("1")) {
                    ((TextView) helper.getView(R.id.details_item_amount)).setTextColor(mContext.getResources().getColor(R.color.black));
                    helper.setText(R.id.details_item_amount, "-" + item.getAmount());
                }
                helper.addOnClickListener(R.id.details_item_r);

                break;

        }
    }

}
