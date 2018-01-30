package com.example.tr.myapplication;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.tr.myapplication.api.Api;
import com.example.tr.myapplication.autoutils.AutoOptions;
import com.example.tr.myapplication.autoutils.AutoUtils;
import com.example.tr.myapplication.utils.DynamicTimeFormat;
import com.example.tr.myapplication.utils.NetWorkManager;
import com.example.tr.myapplication.utils.SpUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


public class MyAPP extends Application {
    private static Context mContext;
    private static String TAG = MyAPP.class.getSimpleName();
    private  MyAPP app;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        app = this;
        //这个不能删,这是初始化SP
        SpUtils.init(app);

        /**
         * 实际项目中这个init第一个参数传入主机地址，后面一个服务器返回的code ,我这里是1 成功
         */
        NetWorkManager.init(Api.BaseUrl,1,getInstances());
        NetWorkManager.setOpenApiException(true);

    }
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.grey_color1, android.R.color.white);//全局设置主题颜色
//                return new BezierRadarHeader(context);
                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));

            }
        });

//        设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });


    }



    public  MyAPP getInstances() {
        return app;
    }

    public static Context getContext() {
        return mContext;
    }
}

