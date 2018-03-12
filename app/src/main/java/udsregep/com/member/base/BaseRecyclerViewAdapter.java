package udsregep.com.member.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by agustinaindah on 12/07/2017.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mData;
    protected Context mContext;

    public BaseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BaseRecyclerViewAdapter(List<T> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T get(int position) {
        return mData.get(position);
    }

    public boolean add(T item) {
        return mData.add(item);
    }

    public int indexOf(T item) {
        return mData.indexOf(item);
    }

    public void updateItemAt(int index, T item) {
        mData.set(index, item);
    }

    public void addAll(List<T> items) {
        mData.clear();
        for (T item : items) {
            mData.add(item);
        }
    }

    public void addAll(T[] items) {
        addAll(Arrays.asList(items));
    }

    public boolean remove(T item) {
        return mData.remove(item);
    }

    public T removeItemAt(int index) {
        return mData.remove(index);
    }

    public void clear() {
        mData.clear();
    }


}
