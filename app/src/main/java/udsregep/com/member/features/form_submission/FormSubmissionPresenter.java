package udsregep.com.member.features.form_submission;

import com.google.gson.JsonObject;

import udsregep.com.member.base.BaseView;

/**
 * Created by agustinaindah on 13/07/2017.
 */

public interface FormSubmissionPresenter {

    interface View extends BaseView{
        boolean validate();

        void success (JsonObject jsonData);

    }

    void postInquiry(JsonObject jsonData);
}
