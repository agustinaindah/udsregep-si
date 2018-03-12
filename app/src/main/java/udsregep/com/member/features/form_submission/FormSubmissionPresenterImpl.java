package udsregep.com.member.features.form_submission;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.HEAD;
import udsregep.com.member.UdSregepApp;
import udsregep.com.member.models.BaseResponse;
import udsregep.com.member.utils.ApiService;
import udsregep.com.member.utils.Helper;
import udsregep.com.member.utils.ServiceInterface;

/**
 * Created by agustinaindah on 13/07/2017.
 */

public class FormSubmissionPresenterImpl implements FormSubmissionPresenter {

    private View mView;

    public FormSubmissionPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void postInquiry(final JsonObject jsonData) {
        boolean cancel = mView.validate();
        if (!cancel){
            UdSregepApp.getInstance().service(new ServiceInterface() {
                @Override
                public Call<BaseResponse> callBackResponse(ApiService apiService) {
                    return apiService.postInquiry(jsonData);
                }

                @Override
                public void showProgress() {
                    mView.showProgress();
                }

                @Override
                public void hideProgress() {
                    mView.hideProgress();
                }

                @Override
                public void responseSuccess(Response<BaseResponse> response) {
                    try {
                        String data = Helper.getGsonInstance().toJson(response.body().getData());
                        JsonObject jsonRes = Helper.parseToJsonObject(data);
                        mView.success(jsonRes);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void responseFailed(Response<BaseResponse> response) {
                    try {
                        JsonObject jsonRes = Helper.parseToJsonObject(response.errorBody().string());
                        mView.showMessage(jsonRes.get("msg").getAsString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable t) {
                    mView.notConnect(t.getLocalizedMessage());
                }
            });
        }
    }
}
