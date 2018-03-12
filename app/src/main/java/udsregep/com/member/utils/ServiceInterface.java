package udsregep.com.member.utils;

import retrofit2.Call;
import retrofit2.Response;
import udsregep.com.member.models.BaseResponse;

/**
 * Created by agustinaindah on 12/07/2017.
 */

public interface ServiceInterface {

    Call<BaseResponse> callBackResponse(ApiService apiService);

    void showProgress();

    void hideProgress();

    void responseSuccess(Response<BaseResponse> response);

    void responseFailed(Response<BaseResponse> response);

    void failed(Throwable t);
}
