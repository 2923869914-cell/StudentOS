package org.example;


/**
 * @author 刘瑞麒
 * 时间     2026/6/1  13:14
 */
public interface CheckInput {
    boolean checkName(String name);
    boolean checkIDcard(String IDcard);
    boolean checkAge(String age);
    boolean checkClass(String class_);
    boolean checkHometown(String hometown);
}
