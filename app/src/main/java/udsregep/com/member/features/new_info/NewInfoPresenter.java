package udsregep.com.member.features.new_info;

import java.util.List;

import udsregep.com.member.base.BaseView;
import udsregep.com.member.models.ItemInfo;

/**
 * Created by agustinaindah on 01/08/2017.
 */

public interface NewInfoPresenter {

    void getInformation(int page);

    interface View extends BaseView{
        void showInformation(List<ItemInfo> itemInfos, int page );
    }
}
