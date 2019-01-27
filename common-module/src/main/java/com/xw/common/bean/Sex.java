package com.xw.common.bean;

import com.google.gson.annotations.SerializedName;

public enum Sex {
    @SerializedName("0")
    UNKNOWN("未知") {
        @Override
        public String toString() {
            return "未知";
        }
    },
    @SerializedName("1")
    BOY("男") {
        @Override
        public String toString() {
            return "男";
        }
    },
    @SerializedName("2")
    GIRL("女") {
        @Override
        public String toString() {
            return "女";
        }
    },
    @SerializedName("3")
    PRIVARY("保密") {
        @Override
        public String toString() {
            return "保密";
        }
    };

    @Override
    public String toString() {
        return "保密";
    }

    public final String desc;

    Sex(String desc) {
        this.desc = desc;
    }


    public static Sex descConvert(String desc) {
        if (desc.equals(BOY.desc)) {
            return BOY;
        } else if (desc.equals(GIRL.desc)) {
            return GIRL;
        }
        return UNKNOWN;
    }
}
