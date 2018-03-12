package udsregep.com.member.base;

/**
 * Created by agustinaindah on 12/07/2017.
 */

public interface BaseView {

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void notConnect(String msg);
}
