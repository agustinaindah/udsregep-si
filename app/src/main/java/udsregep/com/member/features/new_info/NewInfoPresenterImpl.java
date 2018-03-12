package udsregep.com.member.features.new_info;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import udsregep.com.member.UdSregepApp;
import udsregep.com.member.models.BaseResponse;
import udsregep.com.member.models.ItemInfo;
import udsregep.com.member.utils.ApiService;
import udsregep.com.member.utils.Helper;
import udsregep.com.member.utils.ServiceInterface;

/**
 * Created by agustinaindah on 01/08/2017.
 */

public class NewInfoPresenterImpl implements NewInfoPresenter {

    private View mView;

    public NewInfoPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getInformation(final int page) {
        UdSregepApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getInformation(page);
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
                    JsonObject jsonData = Helper.parseToJsonObject(data);
                    JsonArray jsonRes = jsonData.get("result").getAsJsonArray();
                    ArrayList<ItemInfo> itemInfos = new ArrayList<ItemInfo>();
                    for (JsonElement element : jsonRes){
                        ItemInfo itemInfo =
                                Helper.getGsonInstance().fromJson(element, ItemInfo.class);
                        itemInfos.add(itemInfo);
                    }
                    mView.showInformation(itemInfos, page);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void responseFailed(Response<BaseResponse> response) {
                try {
                    JsonObject jsonRes = Helper.parseToJsonObject(response.errorBody().string());
                    mView.showMessage(jsonRes.get("msg").getAsString());
                } catch (Exception e) {
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
