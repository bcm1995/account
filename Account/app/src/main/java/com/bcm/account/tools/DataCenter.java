package com.bcm.account.tools;

import com.bcm.account.R;

/**
 * Created by Bean on 2017/4/25.
 */

public class DataCenter {
    public static String[] outType =

            {
                    "一般",
                    "餐饮",
                    "交通",
                    "饮品",
                    "水果",
                    "零食",
                    "买菜",
                    "衣服",
                    "日用品",
                    "话费",
                    "护肤",
                    "房租",
                    "电影",
                    "淘宝",
                    "水电",
                    "K歌"
            };

    public static int[] outTypeImg = {
            R.mipmap.icon_yiban,
            R.mipmap.icon_wan,
            R.mipmap.icon_jiaoton,
            R.mipmap.icon_jiushui,
            R.mipmap.icon_apple,
            R.mipmap.icon_candy,
            R.mipmap.icon_vegetable,
            R.mipmap.icon_cloth,
            R.mipmap.icon_zhi,
            R.mipmap.icon_shouji,
            R.mipmap.icon_kouhong,
            R.mipmap.icon_fanzhu,
            R.mipmap.icon_dianying,
            R.mipmap.icon_taobao,
            R.mipmap.icon_water,
            R.mipmap.icon_kge,

    };

    public static String[] inType = {
            "工资", "生活费", "零花钱", "兼职", "投资", "奖金", "报销", "现金", "支付宝", "彩票", "其他"
    };

    public static int[] inTypeImg = {
            R.mipmap.icon_gongzi,
            R.mipmap.icon_shenghuofei,
            R.mipmap.icon_linghuaqian,
            R.mipmap.icon_jianzhi,
            R.mipmap.icon_licai,
            R.mipmap.icon_jiangjin,
            R.mipmap.icon_baoxiao,
            R.mipmap.icon_xianjin,
            R.mipmap.icon_zhifubao,
            R.mipmap.icon_caipiao,
            R.mipmap.icon_qita,
    };

    // 名称获取图像

    public static int getImageUri(String str) {
        int imgUri = R.mipmap.icon_qita;
        if (str.equals("gongzi")) {
            return R.mipmap.icon_gongzi;
        } else if (str.equals("shenghuo")) {
            return R.mipmap.icon_shenghuofei;
        } else if (str.equals("linghua")) {
            return R.mipmap.icon_linghuaqian;
        } else if (str.equals("jianzhi")) {
            return R.mipmap.icon_jianzhi;
        } else if (str.equals("touzi")) {
            return R.mipmap.icon_licai;
        } else if (str.equals("jiangjin")) {
            return R.mipmap.icon_jiangjin;
        } else if (str.equals("baoxiao")) {
            return R.mipmap.icon_baoxiao;
        } else if (str.equals("xianjin")) {
            return R.mipmap.icon_xianjin;
        } else if (str.equals("alipay")) {
            return R.mipmap.icon_zhifubao;
        } else if (str.equals("caipiao")) {
            return R.mipmap.icon_caipiao;
        } else if (str.equals("qita")) {
            return R.mipmap.icon_qita;
        } else if (str.equals("yiban")) {
            return R.mipmap.icon_yiban;
        } else if (str.equals("canyin")) {
            return R.mipmap.icon_food;
        } else if (str.equals("jiaotong")) {
            return R.mipmap.icon_jiaoton;
        } else if (str.equals("yinpin")) {
            return R.mipmap.icon_jiushui;
        } else if (str.equals("suiguo")) {
            return R.mipmap.icon_apple;
        } else if (str.equals("lingshi")) {
            return R.mipmap.icon_candy;
        } else if (str.equals("maicai")) {
            return R.mipmap.icon_vegetable;
        } else if (str.equals("yifu")) {
            return R.mipmap.icon_cloth;
        } else if (str.equals("riyong")) {
            return R.mipmap.icon_zhi;
        } else if (str.equals("huafei")) {
            return R.mipmap.icon_shouji;
        } else if (str.equals("hufu")) {
            return R.mipmap.icon_kouhong;
        } else if (str.equals("fangzhu")) {
            return R.mipmap.icon_fanzhu;
        } else if (str.equals("dianying")) {
            return R.mipmap.icon_dianying;
        } else if (str.equals("taobao")) {
            return R.mipmap.icon_taobao;
        } else if (str.equals("suidian")) {
            return R.mipmap.icon_water;
        } else if (str.equals("kge")) {
            return R.mipmap.icon_kge;
        }
        return imgUri;
    }

    ;

}
