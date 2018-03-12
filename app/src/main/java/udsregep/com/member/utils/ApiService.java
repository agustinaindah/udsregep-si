package udsregep.com.member.utils;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import udsregep.com.member.models.BaseResponse;

/**
 * Created by agustinaindah on 12/07/2017.
 */

public interface ApiService {

    @POST("si-sregep/api")
    Call<BaseResponse> postInquiry(@Body JsonObject jsonData);

    @GET("udsregep/wp-jsoninformasiterbaru")
    Call<BaseResponse> getInformation(@Query("page") int page);
}
