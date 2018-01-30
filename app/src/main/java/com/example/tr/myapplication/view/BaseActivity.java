package com.example.tr.myapplication.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import com.example.tr.myapplication.presenter.BasePresenter;
import com.example.tr.myapplication.utils.To;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.subjects.BehaviorSubject;


/**
 * 抽象Activity
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements
        LifecycleProvider<ActivityEvent>, DialogInterface.OnDismissListener {


    public static final String TAG = BaseActivity.class.getSimpleName();
    public static final int RC_Permission_CODE = 1;

    /**
     * 这个泛型在Activity的时候需要用接口继承BasePresenter
     */
    protected P mPresenter;
    protected RecyclerView rv;
    private Unbinder unbinder;
    protected Dialog mLoading;


    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();


    /**
     * 记录处于前台的Activity
     */
    @SuppressLint("StaticFieldLeak")
    private static BaseActivity mForegroundActivity = null;
    /**
     * 记录所有活动的Activity
     */
    private static final List<BaseActivity> mActivities = new LinkedList<BaseActivity>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        enqueue(this);
        this.lifecycleSubject.onNext(ActivityEvent.CREATE);
        //写死竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        ImmersionBar.with(this).init(); //初始化，默认透明状态栏和黑色导航栏
//        AutoUtils.auto(this);
        mLoading = To.createLoadingDialog(this);
        mLoading.setOnDismissListener(this);


        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
        }
        initFindViewById();
        initView();
        initData();
        initEvent();
    }

    protected abstract P onCreatePresenter();




    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return this.lifecycleSubject.asObservable();
    }


    @NonNull
    @CheckResult

    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(this.lifecycleSubject, event);
    }


    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(this.lifecycleSubject);
    }


    protected abstract int getContentView();


    @Override
    protected void onResume() {
        mForegroundActivity = this;
        super.onResume();
        this.lifecycleSubject.onNext(ActivityEvent.RESUME);
    }


    @Override
    protected void onStart() {
        super.onStart();
        this.lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onPause() {
        mForegroundActivity = null;
        this.lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        this.lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    /**
     * 注销事件防止内存泄漏
     */
    @Override
    protected void onDestroy() {
        mForegroundActivity = null;
        this.lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
//        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
        dequeue(this);
    }


    protected abstract void initView();

    protected abstract void initData();

    protected void initActionBar() {

    }

    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        for (Activity activity : mActivities) {
            activity.finish();
        }
    }

    /**
     * 关闭所有Activity，除了参数传递的Activity
     */
    public static void finishAll(BaseActivity except) {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            if (activity != except)
                activity.finish();
        }
    }


    /**
     * 入栈
     *
     * @param activity
     */
    public static void enqueue(BaseActivity activity) {
        mActivities.add(activity);
    }

    /**
     * 出栈
     */
    private void dequeue(BaseActivity pBaseActivity) {
        if (mActivities.contains(pBaseActivity)) {
            mActivities.remove(pBaseActivity);
        }
    }


    /**
     * 是否有启动的Activity
     */
    public static boolean hasActivity() {
        return mActivities.size() > 0;
    }

    /**
     * 获取当前处于前台的activity
     */
    public static BaseActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    /**
     * 获取当前处于栈顶的activity，无论其是否处于前台
     */
    public static BaseActivity getCurrentActivity() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        if (copy.size() > 0) {
            return copy.get(copy.size() - 1);
        }
        return null;
    }

    protected void initFindViewById() {

    }

    protected void initEvent() {

    }

    /**
     * 子类需要用到就返回true
     *
     * @return
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    /**
     * 退出应用
     */
    public static void exitApp() {
        finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass, boolean isfinish) {
        openActivity(pClass, null, isfinish);

        if (isfinish) {
            finish();
        }
    }


    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle, boolean isfinish) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (isfinish) {
            finish();
        }
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        if (dialog != null && mPresenter != null) mPresenter.remove();
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param pAction
     * @param pBundle
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

}

