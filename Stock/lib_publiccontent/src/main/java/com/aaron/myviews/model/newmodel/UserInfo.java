package com.aaron.myviews.model.newmodel;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 用户信息 /user/login
 */
public class UserInfo {

    private static final String GESTURE_PWD_IS_SET = "1";
    private static final String GESTURE_IS_START = "1";
    private static final String OPENID_LOGIN = "1";

    public static final String STAFF_GENERAL = "0";
    public static final String STAFF_VIP = "1";
    public static final String STAFF_INTERNAL = "2";

    private String tele;
    private String name;
    private String nick;
    private String user_cls;
    private String sex;
    private String birth;
    private String head_pic;
    private String person_sign;
    private String is_set_gesture_pwd;
    private String is_start_gesture;
    private String is_openid_login;
    private String is_staff;

    /**
     * 绑定手机号
     * @return
     */
    public String getTele() {
        return tele;
    }

    /**
     * 真实姓名
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 昵称
     * @return
     */
    public String getNick() {
        return nick;
    }

    public String getUserClass() {
        return user_cls;
    }

    public String getSex() {
        return sex;
    }

    public String getBirth() {
        return birth;
    }

    public String getHeadPicture() {
        return head_pic;
    }

    public String getPersonSign() {
        return person_sign;
    }

    public boolean isSetGesturePwd() {
        if (is_set_gesture_pwd.equals(GESTURE_PWD_IS_SET)) {
            return true;
        }
        return false;
    }

    public boolean isStartGesture() {
        if (is_start_gesture.equals(GESTURE_IS_START)) {
            return true;
        }
        return false;
    }

    public boolean isOpenIdLogin() {
        if (is_openid_login.equals(OPENID_LOGIN)) {
            return true;
        }
        return false;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setUserClass(String user_cls) {
        this.user_cls = user_cls;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setHeadPicture(String head_pic) {
        this.head_pic = head_pic;

    }

    public void setPersonSign(String person_sign) {
        this.person_sign = person_sign;
    }

    public String getStaff() {
        return is_staff;
    }




    /**
     * 网络头像图片保存到本地
     */

    public InputStream getImageStream(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return conn.getInputStream();
        }
        return null;
    }

    /* private void saveBitmap() {
        try {
            Bitmap mBitmap;
            mBitmap = BitmapFactory.decodeStream(getImageStream(head_pic));
            File dirFile =new File(AppConfig.ADDRESS_OF_USERHEADPIC);
            Log.e("uploadPhoto", "uploadPhoto: " + dirFile + "_____________" );
            if(!dirFile.exists()){
                dirFile.mkdir();
            }
            File myCaptureFile = new File(dirFile +AppConfig.NAME_OF_USERHEADPIC);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletebitmap() {
        File file=new File(AppConfig.ADDRESS_OF_USERHEADPIC+AppConfig.NAME_OF_USERHEADPIC);
        file.delete();
        Log.e("uploadPhoto", "uploadPhoto: "+"deletepicture" );
    }*/
}
