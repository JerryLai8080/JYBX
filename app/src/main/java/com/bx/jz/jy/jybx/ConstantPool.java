package com.bx.jz.jy.jybx;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class ConstantPool {

    public static final int FridgeId = 1;
    public static final int UserID = 12345;

    private static final String BASE_URL = "http://112.124.102.114:8090/imgTest/";
//    public static final String BASE_URL = "http://192.168.199.182:8080/imgTest/";

    private static final String TEST_URL = "http://apitest.joyoung.com:8389";

    public static final String USER_SHARE = "login_share";
    public static final String CITYCODE = "101210101";
    public static final String SUCCESS = "1";

    public static String GOODSLIST = "http://112.124.102.114:8090/imgTest/in/ingredients!list";//菜品列表
    public static String GOODSRECOMMEND = BASE_URL + "in/img!getRecipeImgs";//菜品列表
    public static String WEATHER = BASE_URL+"in/ingredients!getWeatherBeanByCityCode";//天气
    public static String DELETEFOODS = BASE_URL+"in/ingredients!delete";//删除菜品列表item
    public static String NEWPHOTO = BASE_URL+"in/img!newlist";//获取最近4张照片
    public static String ALBUM = BASE_URL+"in/img!list";//获取图片集合
    public static String SIMILAR = BASE_URL+"in/ingredients!similar";//模糊查询食材名字
    public static String saveOrUpdate = BASE_URL+"in/ingredients!saveOrUpdate";//食材新增/修改接口

    public static String GETNEWCODE = TEST_URL+"/rms/v1/common/vcode?action=getNewCode";//获取登录验证码
    public static String MOBILELOGIN = TEST_URL+"/rms/v1/app/login?action=mobileLogin";//获取登录验证码

}
