package com.sora.iadapter.library;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24 0024.company keydom
 */

public abstract class IAdapter<T, VB extends ViewDataBinding> extends RecyclerView.Adapter<IHolder<VB>> {
    private List<T> mData, mOldData;
    public int NO_MAX_SIZE = -1;
    private int headCount = 0;

    public IAdapter() {
    }

    public void setData(final List<T> data) {
        if (data == null) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
            mData.addAll(data);
            notifyItemRangeInserted(0, data.size());
        } else {
            mOldData = new ArrayList<>(mData);
            mData.clear();
            mData.addAll(data);
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mOldData.size();
                }

                @Override
                public int getNewListSize() {
                    return mData.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return IAdapter.this.areItemsTheSame(mOldData, mData, oldItemPosition, newItemPosition);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return IAdapter.this.areContentsTheSame(mOldData, mData, oldItemPosition, newItemPosition);
                }
            });
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public IHolder<VB> onCreateViewHolder(ViewGroup parent, int viewType) {
        VB vb = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(viewType), parent, false);
        convertListener(vb);
        return new IHolder<VB>(vb);
    }

    /**
     * 设置回调
     *
     * @param binding
     */
    protected void convertListener(@NonNull VB binding) {
    }

    protected abstract int getLayoutId(int viewType);

    @Override
    public void onBindViewHolder(IHolder<VB> holder, int position) {
        T data = null;
        if (position >= getHeadCount()) {
            data = getData().get(position - getHeadCount());
            holder.binding.setVariable(getDataBRId(holder.getItemViewType()), data);
            holder.binding.executePendingBindings();
        }
        Log.d("IAdapter", "position=" + position + "******" + (data == null));
        convert(holder.binding, position, data);
    }

    protected abstract int getDataBRId(int itemViewType);

    protected void convert(@NonNull VB binding, int position, T t) {
    }

    /**
     * @return 获取heander的数量
     */
    public int getHeadCount() {
        return headCount;
    }

    @Override
    public int getItemCount() {
        return (mData == null ? 0 : (getMaxSize() <= NO_MAX_SIZE) ? mData.size() : Math.min(mData.size(), getMaxSize())) + getHeadCount();
    }

    public List<T> getData() {
        return mData;
    }

    /**
     * 判断Item类型是否相等。
     * @param oldData 旧数据
     * @param data 新数据
     * @param oldItemPosition 旧数据的游标
     * @param newItemPosition 新数据的游标
     * @return 比较结果，默认比较采用Adapter.getItemViewType()
     */
    protected boolean areItemsTheSame(List<T> oldData, List<T> data, int oldItemPosition, int newItemPosition) {
        return getItemViewType(oldItemPosition) == getItemViewType(newItemPosition);
    }

    /**
     * 比较两个item的内容是否相等。
     * @param oldData 旧数据
     * @param data 新数据
     * @param oldItemPosition 旧数据的游标
     * @param newItemPosition 新数据的游标
     * @return 比较结果，默认比较采用Object.equels()
     */
    protected boolean areContentsTheSame(List<T> oldData, List<T> data, int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition).equals(data.get(newItemPosition));
    }

    public void notifyItemChanged(T data) {
        notifyItemChanged(getData().indexOf(data));
    }

    /**
     * @return 设置header view的数量。有时候List中并没有header的相关数据
     */
    public int getMaxSize() {
        return NO_MAX_SIZE;
    }



}
