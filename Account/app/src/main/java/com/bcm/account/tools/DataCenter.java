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
        } else if(str.equals("zhuanru")){
            return R.mipmap.icon_trans_in;
        }else if(str.equals("zhuanchu")){
            return R.mipmap.icon_trans_out;
        }else if(str.equals("jieru")){
            return R.mipmap.icon_money_in;
        }else if(str.equals("jiechu")){
            return  R.mipmap.icon_money_out;
        }else if (str.equals("pingzhang")){
            return R.mipmap.icon_trans_input;
        }
        return imgUri;
    }

    public static String  getImageText(String str) {
        String imgText = "其他";
        if (str.equals("gongzi")) {
            return "工资";
        } else if (str.equals("shenghuo")) {
            return "生活费";
        } else if (str.equals("linghua")) {
            return "零花钱";
        } else if (str.equals("jianzhi")) {
            return "兼职";
        } else if (str.equals("touzi")) {
            return "投资";
        } else if (str.equals("jiangjin")) {
            return "奖金";
        } else if (str.equals("baoxiao")) {
            return "报销";
        } else if (str.equals("xianjin")) {
            return "现金";
        } else if (str.equals("alipay")) {
            return "支付宝";
        } else if (str.equals("caipiao")) {
            return "彩票";
        } else if (str.equals("qita")) {
            return "其他";
        } else if (str.equals("yiban")) {
            return "一般";
        } else if (str.equals("canyin")) {
            return "餐饮";
        } else if (str.equals("jiaotong")) {
            return "交通";
        } else if (str.equals("yinpin")) {
            return "饮品";
        } else if (str.equals("suiguo")) {
            return "水果";
        } else if (str.equals("lingshi")) {
            return "零食";
        } else if (str.equals("maicai")) {
            return "买菜";
        } else if (str.equals("yifu")) {
            return "衣服";
        } else if (str.equals("riyong")) {
            return "日用品";
        } else if (str.equals("huafei")) {
            return "话费";
        } else if (str.equals("hufu")) {
            return "护肤";
        } else if (str.equals("fangzhu")) {
            return "房租";
        } else if (str.equals("dianying")) {
            return "电影";
        } else if (str.equals("taobao")) {
            return "淘宝";
        } else if (str.equals("suidian")) {
            return "水费";
        } else if (str.equals("kge")) {
            return "K歌";
        } else if(str.equals("zhuanru")){
            return "转入";
        }else if(str.equals("zhuanchu")){
            return "转出";
        }else if(str.equals("jieru")){
            return "借入";
        }else if(str.equals("jiechu")){
            return "借出";
        }else if(str.equals("pingzhang")){
            return "平账";
        }
        return imgText;
    }

    public static String getImageColor(String str){
        String imgColor = "#39AB7A";
        if (str.equals("gongzi")) {
            return "#39AB7A";
        } else if (str.equals("shenghuo")) {
            return "#D0B95B";
        } else if (str.equals("linghua")) {
            return "#879274";
        } else if (str.equals("jianzhi")) {
            return "#5EB0C5";
        } else if (str.equals("touzi")) {
            return "#FE8002";
        } else if (str.equals("jiangjin")) {
            return "#ED9241";
        } else if (str.equals("baoxiao")) {
            return "#6566A9";
        } else if (str.equals("xianjin")) {
            return "#BD7876";
        } else if (str.equals("alipay")) {
            return "#2BC0FA";
        } else if (str.equals("caipiao")) {
            return "#FE6B6C";
        } else if (str.equals("qita")) {
            return "#A46A78";
        } else if (str.equals("yiban")) {
            return "#3DA6D5";
        } else if (str.equals("canyin")) {
            return "#BFAB55";
        } else if (str.equals("jiaotong")) {
            return "#A58880";
        } else if (str.equals("yinpin")) {
            return "#B42811";
        } else if (str.equals("suiguo")) {
            return "#6EAB6D";
        } else if (str.equals("lingshi")) {
            return "#EE4179";
        } else if (str.equals("maicai")) {
            return "#5CC090";
        } else if (str.equals("yifu")) {
            return "#FC567C";
        } else if (str.equals("riyong")) {
            return "#07ACE6";
        } else if (str.equals("huafei")) {
            return "#B386AF";
        } else if (str.equals("hufu")) {
            return "#D26AA7";
        } else if (str.equals("fangzhu")) {
            return "#AC244A";
        } else if (str.equals("dianying")) {
            return "#92654E";
        } else if (str.equals("taobao")) {
            return "#DB6130";
        } else if (str.equals("suidian")) {
            return "#45A7E4";
        } else if (str.equals("kge")) {
            return "#DD3131";
        }
        return imgColor;
    }

    // 钱包钱数
    public static String walletId = "";
    public static String cashMoney = "";
    public static String debitMoney = "";
    public static String creditMoney = "";
    public static String joinMoney ="";
    public static String loanMoney ="";

}
