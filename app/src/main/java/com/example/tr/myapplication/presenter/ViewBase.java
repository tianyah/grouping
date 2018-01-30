package com.example.tr.myapplication.presenter;

/**
 * Created by maxu on 2017/12/26.
 */

public interface ViewBase<D> extends BaseView {
    /**
     * 回调成功
     * @param data
     */
    void onSuccess(D data);

}
