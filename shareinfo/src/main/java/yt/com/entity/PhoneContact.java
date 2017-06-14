package yt.com.entity;

/**
 * author  : LSM
 * time    : 2017/06/09
 * function: 通话记录
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 */

public class PhoneContact {
    private String talkDate;
    private String talkType;
    private String talkContact;
    private String talkTime;

    public String getTalkDate() {
        return talkDate;
    }

    public void setTalkDate(String talkDate) {
        this.talkDate = talkDate;
    }

    public String getTalkType() {
        return talkType;
    }

    public void setTalkType(String talkType) {
        this.talkType = talkType;
    }

    public String getTalkContact() {
        return talkContact;
    }

    public void setTalkContact(String talkContact) {
        this.talkContact = talkContact;
    }

    public String getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(String talkTime) {
        this.talkTime = talkTime;
    }
}
