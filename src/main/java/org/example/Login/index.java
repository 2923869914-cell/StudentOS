package org.example.Login;

import org.example.StudentSystem.Main;

import javax.swing.*;
import java.awt.*;
/**
 * @author 刘瑞麒
 * 时间 2026/5/16 14:31
 * 学生管理系统登录界面（优化版）
 */
public class index {
    public static void main(String[] args) throws Exception{
        // 1. 创建窗口
        JFrame jf = new JFrame("学生管理系统");
        jf.setSize(800, 600);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null); // 居中显示
        jf.setLayout(null); // 绝对布局

        // 2. 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("使用说明");
        JMenu menu2 = new JMenu("版权声明");
        menuBar.add(menu1);
        menuBar.add(menu2);
        jf.setJMenuBar(menuBar); // 必须用setJMenuBar，不能add

        // 3. 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(new Color(230, 240, 255));

        // 4. 标题
        JLabel titleLabel = new JLabel("学生管理系统登录页面");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setBounds(260, 80, 400, 50);
        panel.add(titleLabel);

        // 5. 用户名标签 + 输入框
        JLabel nameLabel = new JLabel("实　名：");
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        nameLabel.setBounds(230, 280, 80, 30);
        panel.add(nameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(300, 280, 250, 30);
        panel.add(usernameField);

        // 6. 密码标签 + 密码框
        JLabel pwdLabel = new JLabel("密　码：");
        pwdLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        pwdLabel.setBounds(230, 330, 80, 30);
        panel.add(pwdLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(300, 330, 250, 30);
        panel.add(passwordField);

        // 7. 功能按钮
        JButton loginBtn = new JButton("登录");
        JButton registerBtn = new JButton("注册");
        JButton clearBtn = new JButton("清空");

        loginBtn.setBounds(280, 400, 80, 35);
        registerBtn.setBounds(380, 400, 80, 35);
        clearBtn.setBounds(480, 400, 80, 35);

        panel.add(loginBtn);
        panel.add(registerBtn);
        panel.add(clearBtn);

        //登录点击事件
        loginBtn.addActionListener (e -> {
            String uname = usernameField.getText();
            String upwd = new String(passwordField.getPassword());

//---------------------------------------------   判断登录是否成功 ----------------------------------------------------------------------------------------------------------------
            if(uname.isEmpty() || upwd.isEmpty()){
                JOptionPane.showMessageDialog(null,"账号密码不能为空");
                return;
            }
            if (new ConnectionSql (uname, upwd).CheckLogin()){
                JOptionPane.showMessageDialog(null,"你好，"+uname+"，欢迎进入学生管理系统");
                new Main().Mune();//启动学生管理系统
            }else{
                if (ConnectionSql.flagLogin == 0) {
                    JOptionPane.showMessageDialog(null, "输入的姓名和密码不符合格式，请仔细阅读使用说明");
                } else if (ConnectionSql.flagLogin == 404){
                    JOptionPane.showMessageDialog(null, "登录失败，请检查数据库连接");
                }else {
                    JOptionPane.showMessageDialog(null, "密码错误！！！hacker！！！");
                }
            }
        });
        //注册点击事件
        registerBtn.addActionListener(e -> {
            String uname = usernameField.getText();
            String upwd = new String(passwordField.getPassword());

            if (uname.isEmpty() || upwd.isEmpty()) {
                JOptionPane.showMessageDialog(null, "账号密码不能为空");
                return;
            }

            try {
                if (new ConnectionSql().Register(uname, upwd)) {
                    //清空文本框
                    usernameField.setText("");
                    passwordField.setText("");
                    JOptionPane.showMessageDialog(null, "注册成功，请使用该账号登录");
                } else {
                    if (ConnectionSql.flagRegister == 0){
                        JOptionPane.showMessageDialog(null, "或者输入的姓名和密码不符合格式，请仔细阅读使用说明");
                    }else if (ConnectionSql.flagRegister == 2){
                        JOptionPane.showMessageDialog(null, "注册失败，该用户已存在");
                    }else if (ConnectionSql.flagRegister == 404){
                        JOptionPane.showMessageDialog(null, "注册失败，请检查数据库连接");
                    }else {
                        JOptionPane.showMessageDialog(null, "注册失败！！！hacker！！！");
                    }
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, "注册失败，请检查数据库连接");
            }
        });
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//清空
        clearBtn.addActionListener(e->{
            usernameField.setText("");
            passwordField.setText("");
        });
        // 8. 把面板加入窗口
        jf.add(panel);
        // 显示窗口
        jf.setVisible(true);
    }
}
