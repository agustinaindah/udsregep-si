package udsregep.com.member.features.new_info;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import udsregep.com.member.R;
import udsregep.com.member.base.BaseActivity;
import udsregep.com.member.models.ItemInfo;
import udsregep.com.member.utils.CallbackInterface;
import udsregep.com.member.utils.Consts;
import udsregep.com.member.utils.EndlessScrollListener;
import udsregep.com.member.utils.Helper;

/**
 * Created by agustinaindah on 01/08/2017.
 */

public class NewInfoActivity extends BaseActivity implements NewInfoPresenter.View {

    @BindView(R.id.rvNewInfo)
    RecyclerView rvNewInfo;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.btnError)
    Button btnError;
    @BindView(R.id.layError)
    LinearLayout layError;

    private NewInfoPresenter mPresenter;
    private int lastCount = 0;
    private LinearLayoutManager linearLayoutManager;
    private NewInfoAdapter mAdapter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Informasi Terbaru");

        mPresenter = new NewInfoPresenterImpl(this);
        mPresenter.getInformation(Consts.FIRST_PAGE);

        linearLayoutManager = new LinearLayoutManager(this);
        rvNewInfo.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int nextPage) {
                if (lastCount == Consts.LIMIT){
                    mPresenter.getInformation(Consts.FIRST_PAGE);
                }
            }
        });
    }

    @Override
    protected int setView() {
        return R.layout.activity_new_info;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        }, 2000);
    }

    @Override
    public void showMessage(String msg) {
       /* Helper.createAlert(this, "Info", msg);*/
        Helper.createAlert(this, "Info", msg, true, new CallbackInterface() {
            @Override
            public void callback() {
                mPresenter.getInformation(Consts.FIRST_PAGE);
            }
        });
    }

    @Override
    public void notConnect(String msg) {
        layError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnError)
    public void reload(){
        layError.setVisibility(View.GONE);
        mPresenter.getInformation(Consts.FIRST_PAGE);
    }

    @Override
    public void showInformation(List<ItemInfo> itemInfos, int page) {
        lastCount = itemInfos.size();
        if (page == Consts.FIRST_PAGE){
            txtNoData.setVisibility((itemInfos.size()==0) ? View.VISIBLE : View.GONE);

            mAdapter = new NewInfoAdapter(itemInfos, this);

            rvNewInfo.setHasFixedSize(true);
            rvNewInfo.setLayoutManager(linearLayoutManager);
            rvNewInfo.setAdapter(mAdapter);
            rvNewInfo.setNestedScrollingEnabled(false);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom);
            rvNewInfo.startAnimation(animation);

        } else {
            for (ItemInfo item : itemInfos){
                mAdapter.add(item);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
