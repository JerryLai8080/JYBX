package com.bx.jz.jy.jybx.bean;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class Album {
    private String img;
    private String name;

    public Album(String img, String name) {
        this.img = img;
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
