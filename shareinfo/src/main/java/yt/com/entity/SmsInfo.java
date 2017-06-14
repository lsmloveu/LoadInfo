package yt.com.entity;

/**
 * author  : LSM
 * time    : 2017/06/09
 * function: 短信实体类
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 */

public class SmsInfo {
    private String smsContact;
    private String  smsContext;
    private String smsDate;
    private String smsType;

    public String getSmsContact() {
        return smsContact;
    }

    public void setSmsContact(String smsContact) {
        this.smsContact = smsContact;
    }

    public String getSmsContext() {
        return smsContext;
    }

    public void setSmsContext(String smsContext) {
        this.smsContext = smsContext;
    }

    public String getSmsDate() {
        return smsDate;
    }

    public void setSmsDate(String smsDate) {
        this.smsDate = smsDate;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }
}
