package com.example.tr.myapplication.presenter;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ${creators} MX
 * on ${project}
 * Base类
 */

public class BasePresenter<V extends BaseView,M extends BaseModel> {

    protected V mView;
    protected M mModel;
    protected static final String TAG = BasePresenter.class.getSimpleName();

    private CompositeDisposable mCompositeSubscription;
    private Disposable subscription;

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable ();
        }
        this.subscription = subscription;
        mCompositeSubscription.add(this.subscription);
    }

    public void remove(){
        if (mCompositeSubscription != null) {
            if (subscription!=null) {
                mCompositeSubscription.remove(subscription);
            }
        }
    }

    public void unSubscribe() {
        if (mView != null) {
            mView = null;
        }
        if (mCompositeSubscription != null) {
//            mCompositeSubscription.clear();
            mCompositeSubscription.dispose();
        }
    }
}
