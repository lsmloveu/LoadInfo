package yt.com.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import yt.com.entity.AppInfoBean;
import yt.com.entity.TelInfoBean;

/**
 * author  : LSM
 * time    : 2017/06/07
 * function:
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 */

public class SecretShow {
    //应用获取
    public static String getAppInfo(Context context,int sign) {
        String appInfo1="";
        String appInfo2="";
        ArrayList<AppInfoBean> appInfoBeanList= SecretAnalysis.getAppInfo(context);
        for(int i=0; i<appInfoBeanList.size(); i++) {
            AppInfoBean appInfoBean=appInfoBeanList.get(i);
            String appType=appInfoBean.getAppType();
            String appName=appInfoBean.getAppName();
            if(appType.equals("1")) {
                appInfo1+=appName+",";
            } else  {
                appInfo2+=appName+",";
            }
        }
        if(sign==1) {
            if(!appInfo1.equals("")) {
                return appInfo1.substring(0, appInfo1.length() - 1);
            } else {
                return  appInfo1;
            }
        } else {
            if(!appInfo2.equals("")) {
                return appInfo2.substring(0, appInfo2.length() - 1);
            } else {
                return  appInfo2;
            }
        }
    }
    //通讯录
    public static String getTelInfo0(Context context)    {
        String result="";
        ArrayList<TelInfoBean> telInfoBeanList= SecretAnalysis.getContact(context);
        for(int i=0; i<telInfoBeanList.size(); i++) {
            TelInfoBean telInfoBean=telInfoBeanList.get(i);
            String telName=telInfoBean.getTelName();
            String telNum=telInfoBean.getTelNum();
            result+=telName+":"+telNum+"|";
        }
        if(!result.equals(""))  {
            return result.substring(0,result.length()-1);
        } else {
            return  result;
        }
    }

}
