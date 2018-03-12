package udsregep.com.member.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;
import udsregep.com.member.R;
import udsregep.com.member.utils.Consts;

/**
 * Created by agustinaindah on 12/07/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(setView());
        ButterKnife.bind(this);
        onActivityCreated(savedInstanceState);
    }

    protected abstract void onActivityCreated(Bundle savedInstanceState);

    protected abstract int setView();

    public void gotoFragment(FragmentManager fm, Fragment fragment, boolean addBackStack, String tag) {
        FragmentTransaction ft = fm.beginTransaction();

        ft.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
        );
        ft.replace(R.id.fragmentContainer, fragment, tag);

        if (addBackStack)
            ft.addToBackStack(null);

        ft.commit();
    }

    public void gotoFragment(FragmentManager fm, Fragment fragment, boolean addBackStack) {
        gotoFragment(fm, fragment, addBackStack, Consts.FRAGMENT);
    }

    public void gotoFragment(FragmentManager fm, Fragment fragment) {
        gotoFragment(fm, fragment, false);
    }

    public void gotoActivity(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        if (isFinish) finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void gotoActivity(Class<?> cls) {
        gotoActivity(cls, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
