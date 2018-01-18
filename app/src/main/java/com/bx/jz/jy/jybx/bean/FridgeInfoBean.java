package com.bx.jz.jy.jybx.bean;

/**
 * author: zhy
 * email: 760982661@qq.com
 * date: 2018/1/18 0018.
 * desc:
 */

public class FridgeInfoBean {

    private String msg;
    private int code;
    private int type;
    private RefrigeratorBean refrigerator;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public RefrigeratorBean getRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(RefrigeratorBean refrigerator) {
        this.refrigerator = refrigerator;
    }

    public static class RefrigeratorBean {

        private int abnormity;
        private String addTime;
        private byte[] data;
        private int freeze;
        private int heterotherm;
        private int id;
        private int isDelete;
        private String pattern;
        private int refrigerate;
        private String refrigeratorCode;
        private String refrigeratorName;
        private int refrigeratorid;
        private String updateTime;

        public int getAbnormity() {
            return abnormity;
        }

        public void setAbnormity(int abnormity) {
            this.abnormity = abnormity;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public int getFreeze() {
            return freeze;
        }

        public void setFreeze(int freeze) {
            this.freeze = freeze;
        }

        public int getHeterotherm() {
            return heterotherm;
        }

        public void setHeterotherm(int heterotherm) {
            this.heterotherm = heterotherm;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public int getRefrigerate() {
            return refrigerate;
        }

        public void setRefrigerate(int refrigerate) {
            this.refrigerate = refrigerate;
        }

        public String getRefrigeratorCode() {
            return refrigeratorCode;
        }

        public void setRefrigeratorCode(String refrigeratorCode) {
            this.refrigeratorCode = refrigeratorCode;
        }

        public String getRefrigeratorName() {
            return refrigeratorName;
        }

        public void setRefrigeratorName(String refrigeratorName) {
            this.refrigeratorName = refrigeratorName;
        }

        public int getRefrigeratorid() {
            return refrigeratorid;
        }

        public void setRefrigeratorid(int refrigeratorid) {
            this.refrigeratorid = refrigeratorid;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
