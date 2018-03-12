package udsregep.com.member.features;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindString;
import butterknife.BindView;
import udsregep.com.member.R;
import udsregep.com.member.base.BaseActivity;
import udsregep.com.member.features.form_submission.FormSubmissionActivity;
import udsregep.com.member.features.new_info.NewInfoActivity;
import udsregep.com.member.utils.Consts;

public class MainActivity extends BaseActivity {

    @BindView(R.id.layItemInfo)
    RelativeLayout layItemInfo;
    @BindView(R.id.layItemChat)
    RelativeLayout layItemChat;
    @BindView(R.id.layItemFormOrder)
    RelativeLayout layItemFormOrder;

    private ProgressDialog progressDialog;

    @BindString(R.string.loading)
    String strloading;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        initLoading();
        initAction();
    }

    private void initAction() {

        layItemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewInfoActivity.class);
                startActivity(intent);
            }
        });

        layItemChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(Consts.URL_CONNECT_WA);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        layItemFormOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormSubmissionActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(strloading);
    }

    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

/*
    public void ItemClick(View view){
        switch (view.getId()){
            case R.id.layItemChat:
                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=6285786757044");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.layItemFormOrder:
                break;
        }
    }*/

}
