package com.sora.library;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * @author Zheng Yang on 2018/3/27 20:11
 */

public class RecyclerViewBindings {
    @BindingAdapter({"app:data"})
    public static <T> void setData(RecyclerView recyclerView, List<T> data) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof IAdapter) {
            ((IAdapter) adapter).setData(data);
        } else {
            throw new ClassCastException("RecyclerView的Adapter不是com.sora.library.IAdapter类型");
        }
    }
}
