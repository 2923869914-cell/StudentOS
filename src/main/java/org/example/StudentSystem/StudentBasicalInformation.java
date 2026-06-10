package org.example.StudentSystem;


/**
 * @author 刘瑞麒
 * 时间     2026/5/16  21:10
 */
public class StudentBasicalInformation{
    private String idCard;
    private String name;
    private  String age;
    private String group; // "xx专业xx班"
    private String hometown;

    public StudentBasicalInformation(String idCard, String name, String age, String group, String hometown) {
        this.idCard = idCard;
        this.name = name;
        this.age = age;
        this.group = group;
        this.hometown = hometown;
    }
    public StudentBasicalInformation(){}
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    @Override
    public String toString() {
        String s = "- - - - - -学生信息- - - - - - - \n"+"姓名："+name+"\t\n学号："+idCard+"\t\n年龄："+age+"\t\n班级："+group+"\t\n家乡："+hometown+"\t\n- - - - - - - - - - - - - - - - - -";
        return s;
    }
}
