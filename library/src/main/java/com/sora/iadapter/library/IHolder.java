package com.sora.iadapter.library;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/7/24 0024.company keydom
 */

public class IHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public VB binding;

    public IHolder(VB vb) {
        super(vb.getRoot());
        binding = vb;
    }
}
