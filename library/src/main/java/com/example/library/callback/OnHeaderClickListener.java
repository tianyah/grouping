package com.example.library.callback;

import android.view.View;

/**
 * <p>顶部标签点击监听</p>
 */
public interface OnHeaderClickListener {

    void onHeaderClick(View view, int id, int position);

    void onHeaderImageShow(View view, int id, int position);

    void onHeaderLongClick(View view, int id, int position);

    void onHeaderDoubleClick(View view, int id, int position);
}
