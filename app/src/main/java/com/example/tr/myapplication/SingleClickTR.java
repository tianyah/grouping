package com.example.tr.myapplication;
import android.view.View;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import java.util.Calendar;


/**
 * Created by TR on 2018/1/26.
 */
@Aspect
public class SingleClickTR {

    private static final int MIN_CLICK_DELAY_TIME = 800;
    private static int TIME_TAG = R.id.time_click;
    private static final String TAG="SingleClick";
    @Pointcut("execution(@com.example.library.callback.MyClick * *(..))")
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs())
            if (arg instanceof View) view = (View) arg;
        /*
          如果view不为空的遍历添加的注解
         */
        if (view != null) {
            Object tag = view.getTag(TIME_TAG);
            long lastClickTime = ((tag != null) ? (long) tag : 0);
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                view.setTag(TIME_TAG, currentTime);
                joinPoint.proceed();//执行原方法
            }
        }
    }
}
