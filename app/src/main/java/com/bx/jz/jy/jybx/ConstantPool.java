package com.bx.jz.jy.jybx;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class ConstantPool {

    private static final String BASE_URL = "http://192.168.199.180:8080/imgTest/";
    public static final String USER_SHARE = "login_share";
    public static final String SUCCESS = "1";

    public static String GOODSLIST = BASE_URL + "in/ingredients!list";//菜品列表
    public static String GOODSRECOMMEND = BASE_URL + "in/img!getRecipeImgs";//菜品列表
    public static String WEATHER = BASE_URL+"in/ingredients!getWeatherBeanByCityCode";//天气

}
