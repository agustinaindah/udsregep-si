package udsregep.com.member.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by agustinaindah on 01/08/2017.
 */

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener{
    private RecyclerView.LayoutManager mLayoutManager;
    private int currentPage = 1;
    private int prevItemCount = 0;
    private int lastVisibleItemPos;

    public EndlessScrollListener(RecyclerView.LayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int itemCount = mLayoutManager.getItemCount();

        if (itemCount >= prevItemCount) {

            if (mLayoutManager instanceof LinearLayoutManager) {

                lastVisibleItemPos = ((LinearLayoutManager) mLayoutManager)
                        .findLastCompletelyVisibleItemPosition();

            } else if (mLayoutManager instanceof GridLayoutManager) {

                lastVisibleItemPos = ((GridLayoutManager) mLayoutManager)
                        .findLastCompletelyVisibleItemPosition();

            } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {

                int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager)
                        .findLastVisibleItemPositions(null);
            }


            if (lastVisibleItemPos != RecyclerView.NO_POSITION
                    && lastVisibleItemPos == itemCount - 1) {

                currentPage++;
                onLoadMore(currentPage);

                prevItemCount = itemCount;
            }
        }
    }

    public abstract void onLoadMore(int nextPage);
}
