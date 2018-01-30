# grouping
Rxjava+Retrofit+MVP+AOP 类似支付宝微信账单明细列表

## 话不多说先看效果
<br/>![image](https://github.com/tianyah/grouping/blob/master/pic/GIF.gif) </br>





 #                                          怎么用？

			

#### 1. 你的实体类需要实现MultiItemEntity接口


```javascript 
public static class BillInfo implements MultiItemEntity 
```



#### 2. 你需要写一个适配器继承BaseMultiItemQuickAdapter

```javascript

    
  //直接继承BaseMultiItemQuickAdapter单独实现一个适配器的写法

public class BillAdapter extends BaseMultiItemQuickAdapter<BillkEntity.BillInfo, BaseViewHolder> {

    public BillAdapter(List<BillkEntity.BillInfo> data) {
        super(data);
        //这里设置头部界面
        addItemType(BillkEntity.BillInfo.TYPE_HEADER, R.layout.details_head_item);
        //这里设置你的item界面
        addItemType(BillkEntity.BillInfo.TYPE_DATA, R.layout.details_item);
    }

     //根据类型绑定数据
    @Override
    protected void convert(BaseViewHolder helper, BillkEntity.BillInfo item) {
        switch (helper.getItemViewType()) {
               //头部
            case BillkEntity.BillInfo.TYPE_HEADER:
                helper.setText(R.id.item_time, item.billHeaderTime);
                helper.setText(R.id.spending,"支出¥ "+item.spending);
                helper.setText(R.id.income,"收入¥: "+item.income);
                break;
                
                //item数据
            case BillkEntity.BillInfo.TYPE_DATA:


                helper.setText(R.id.trading, item.getBill_name());
                
                //helper.setText(R.id.details_time, StringUtils.getStrTime(item.getCreated_at()));
                helper.setText(R.id.details_time, item.getCreated_at());
                if (item.getMode().equals("0")) {                  
                    ((TextView)helper.getView(R.id.details_item_amount)).
                    setTextColor(mContext.getResources().getColor(R.color.details_text));
                    
                    helper.setText(R.id.details_item_amount, "+" + item.getAmount());
                }
                if (item.getMode().equals("1")) {
                    ((TextView) helper.getView(R.id.details_item_amount)).
                    setTextColor(mContext.getResources().getColor(R.color.black));
                    
                    helper.setText(R.id.details_item_amount, "-" + item.getAmount());
                }
                helper.addOnClickListener(R.id.details_item_r);

                break;

        }
    }
}

```

####  接下来在主界面初始化数据 如果你需要当无网络情况显示网络异常，可以用LoadingView

```javascript
  mPresenter.getBillData(); 
  
LoadingView提供了setEmptyView(View);
                setErrView(View);
                setOnRetryListener(View);


  ```


#### item 点击事件

```javascript
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.details_item_r) {
                    To.ShowToast(BillActivity.this, "点击了类型二条目的类型为: " +
                    mAdapter.getData(). get(position).getCreated_at());  
                    
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
   ```
    
    
 ###  无网络点击
 ```javascript
     /**
     * 无网络点击回调
     */
    @Override
    public void onRetry() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            To.ShowToast(this, "请确保网络正常");
            return;
        }
        initData();
} ```

###  无网络点击
###   接下来在主界面初始化数据 如果你需要当无网络情况显示网络异常，可以用LoadingView   

