package org.example.StudentSystem;


import org.example.Check.Regex;
import org.example.Login.ConnectionSql;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author 刘瑞麒
 * 时间     2026/5/8  15:57
 */
public class Main {
    public String index = "----------------欢迎来到学生管理系统----------------\n" + "1：添加学生\n" + "2：查询学生\n" + "3：删除学生\n" + "4：查询学生课程\n" + "5：回收站\n" + "10：退出\n" + "请输入您的选择";
    boolean flag = true;
    List<StudentBasicalInformation> removedStudentInformation = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void Mune() {
        while (flag) {
            System.out.println(index);
            String s_;
            s_ = sc.next();
            switch (s_) {               //s_用字符串，不要数字，避免输入异常
                case "1":
                    String idcard = null;
                    String name = null;
                    String age = null;
                    String group = null;
                    String hometown = null;
                    boolean _1 = true;
                    while (_1) {
                        Regex regex = new Regex();
                        System.out.println("请输入学生信息：");
                        System.out.println("学号：(12位纯数字)");
                        idcard = sc.next();
                        if (ContinueMune(idcard)) break;
                        if (!regex.checkIDcard(idcard)) {
                            System.out.println("学号不规范");
                            continue;
                        }
                        System.out.println("姓名：");
                        name = sc.next();
                        if (ContinueMune(name)) break;
                        if (!regex.checkName(name)) {
                            System.out.println("姓名不规范");
                            continue;
                        }
                        System.out.println("年龄：");
                        age = sc.next();
                        if (ContinueMune(age)) break;
                        if (!regex.checkAge(age)) {
                            System.out.println("年龄不规范");
                            continue;
                        }
                        System.out.println("班级(比如：人工智能2501)：");
                        group = sc.next();
                        if (ContinueMune(group)) break;
                        if (!regex.checkClass(group)) {
                            System.out.println("班级不规范");
                            continue;
                        }
                        System.out.println("生源地(省份--全称)：");
                        hometown = sc.next();
                        if (ContinueMune(hometown)) break;
                        if (!regex.checkHometown(hometown)) {
                            System.out.println("省份名不规范");
                            continue;
                        }
                        //将数据传入数据库处理
                        boolean b = new ConnectionSql(name, idcard, age, group, hometown).setStudentBasicalInformation();
                        if (b) {
                            System.out.println("OK，已录入");
                            _1 = false;
                        }
                    }
                    break;
                case "2":
                    String find = null;
                    boolean _2 = true;
                    while (_2) {
                        System.out.println("请输入要查询的学生学号或姓名：");
                        find = sc.next();
                        if (ContinueMune(find)) break;
                        //将数据传入数据库处理
                        StudentBasicalInformation s = null;
                        try {
                            s = new ConnectionSql(find).getStudentBasicalInformation();
                            _2 = false;
                            System.out.println(s);
                        } catch (Exception e) {
                            System.out.println("查询失败");
                        }
                    }
                    break;
                case "3":
                    String remove = null;
                    boolean _3 = true;
                    StudentBasicalInformation s = null;
                    while (_3) {
                        System.out.println("请输入要删除的学生学号或姓名：");
                        find = sc.next();
                        if (ContinueMune(find)) break;
                        try {
                            s = new ConnectionSql(find).deleteStudentInformation();
                            _3 = false;
                            if (s != null) {
                                removedStudentInformation.add(s);
                                System.out.println("已删除该学生基本信息，您可以在缓存模块中恢复学生数据");
                            }
                        } catch (Exception e) {
                            System.out.println("删除失败");
                        }
                    }
                    break;
                case "4":
                    System.out.println("请输入要处理的学生学号或姓名：");
                    find = sc.next();
                    if (ContinueMune(find)) break;
                    //---------------1，正则判断输入是否合规；2，判断数据库中是否有该学生---------------------
                    System.out.println("-----");
                    System.out.println("1.周一");
                    System.out.println("2.周二");
                    System.out.println("3.周三");
                    System.out.println("4.周四");
                    System.out.println("5.周五");
                    System.out.println("6.周六");
                    System.out.println("7.周日");
                    System.out.println("-----");
                    System.out.println("你要添加哪一天的课表？（1~7代表周一到周五）");
                    int weekdays_id = sc.nextInt();
                    //校验 weekdays_id
                    //。。。
                    if (ContinueMune(find)) break;

                    break;
                case "5":
                    //增强for循环遍历removedStudentInformation
                    for (StudentBasicalInformation stu : removedStudentInformation) {
                        System.out.println(stu);
                    }
                    break;
                case "10":
                    while (true) {
                        System.out.println("您确定要退出吗？(确定程序请输入quit或exit，继续程序请输入continue)");
                        String Q = sc.next();
                        if (ContinueMune(Q)) break;
                        if (Q.equals("quit") || Q.equals("exit")) {
                            flag = false;
                            JOptionPane.showMessageDialog(null, "Bye");
                            System.exit(0);
                            break;
                        } else {
                            System.out.println("错误输入，再来！");
                        }
                    }
                    break;
                default:
                    System.out.println("输入错误");   //s_匹配不上，说明管理员输入错误
            }
        }
    }

    public boolean ContinueMune(String str) {
        if (str.equals("continue")) {
            return true;
        }
        return false;
    }
}
