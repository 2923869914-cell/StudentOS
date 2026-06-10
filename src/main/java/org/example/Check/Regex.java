package org.example.Check;


import org.example.CheckInput;

/**
 * @author 刘瑞麒
 * 时间     2026/5/24  17:27
 */
public class Regex implements CheckInput {
    public boolean checkName(String name){
        if (name.equals("")){
            return false;
        }
        return name.matches("[\\u4e00-\\u9fa5a-zA-Z]{2,20}");   //校验名字必须是2~20个汉字或英文字母
    }
    public static boolean RegexLoginNameAndPassword(String name,String password){
        String regexCName = "[\\u4E00-\\u9FA5]{2,10}";
        String regexEName = "[a-zA-Z]{2,20}";
        String regexPassword = "[a-zA-Z_\\d]{6,20}";
        return (name.matches(regexCName)||name.matches(regexEName)) && password.matches(regexPassword);
    }
    public boolean checkIDcard(String ID){
        if (ID.equals("")){
            return false;
        }
        return ID.matches("\\d{12}");  //校验学号必须是12位
    }
    public boolean checkAge(String age){
        if (age.equals("")){
            return false;
        }
        return age.matches("\\d{1,3}");   //校验年龄必须是1~3位数字
    }
    public boolean checkClass(String class_){
        if (class_.equals("")){
            return false;
        }
        return class_.matches("\\w{2,12}\\d{4}班");
    }
    public boolean checkHometown(String hometown){
        if (hometown.equals("")){
            return false;
        }
        return hometown.matches("(北京市|天津市|上海市|重庆市|河北省|山西省|辽宁省|吉林省|黑龙江省|江苏省|浙江省|安徽省|福建省|江西省|山东省|河南省|湖北省|湖南省|广东省|海南省|四川省|贵州省|云南省|陕西省|甘肃省|青海省|台湾省|内蒙古自治区|广西壮族自治区|西藏自治区|宁夏回族自治区|新疆维吾尔自治区|香港特别行政区|澳门特别行政区)");
    }
}
