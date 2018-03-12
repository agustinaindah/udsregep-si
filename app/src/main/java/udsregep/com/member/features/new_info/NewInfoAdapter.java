package udsregep.com.member.features.new_info;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udsregep.com.member.R;
import udsregep.com.member.base.BaseRecyclerViewAdapter;
import udsregep.com.member.models.ItemInfo;

/**
 * Created by agustinaindah on 01/08/2017.
 */

public class NewInfoAdapter extends BaseRecyclerViewAdapter<ItemInfo> {

    public NewInfoAdapter(List<ItemInfo> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_new_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        renderData(get(position),(ViewHolder) holder);
    }

    private void renderData(final ItemInfo itemInfo, ViewHolder holder) {
        holder.txtItemInfoTitle.setText(itemInfo.getPostTitle());
        holder.txtItemInfoContent.setText(itemInfo.getPostContent());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txtItemInfoTitle)
        TextView txtItemInfoTitle;
        @BindView(R.id.txtItemInfoContent)
        TextView txtItemInfoContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
