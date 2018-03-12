package udsregep.com.member.features;

import android.content.Intent;
import android.os.Bundle;

import udsregep.com.member.R;
import udsregep.com.member.base.BaseActivity;

/**
 * Created by agustinaindah on 12/07/2017.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                next();
            }
        },2500);
    }

    private void next() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int setView() {
        return R.layout.activity_splash;
    }
}
