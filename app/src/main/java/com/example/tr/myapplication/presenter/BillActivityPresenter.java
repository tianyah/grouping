package com.example.tr.myapplication.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.example.tr.myapplication.R;
import com.example.tr.myapplication.model.BillActivityModel;
import com.example.tr.myapplication.model.BillkEntity;
import com.example.tr.myapplication.utils.To;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by TR on 2018/1/28.
 */

public class BillActivityPresenter extends BasePresenter {
    private ViewBase mView;
    private BillActivityModel mModel;
    private Context mContext;
    private TimePickerView pvCustomTime;

    public BillActivityPresenter(ViewBase mview, Context context) {
        this.mView = mview;
        this.mContext = context;
        mModel = new BillActivityModel();
    }


    public void getBillData() {


        mView.showDialog();
        String s = "";

        mView.onSuccess(s);

        mView.hideDialog();


        /**
         *这里实际换成你需要的
         */
//        mModel.getData()
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        addSubscribe(d);
//                    }
//
//                    @Override
//                    public void onNext(String billkEntity) {
//                        mView.onSuccess(billkEntity);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        onComplete();
//                        To.ShowToast(mContext, e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                        mView.hideDialog();
//                    }
//                });
    }

}
