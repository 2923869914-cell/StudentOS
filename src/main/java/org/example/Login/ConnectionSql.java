package org.example.Login;

import org.example.StudentSystem.StudentBasicalInformation;
import java.sql.*;
import org.example.Check.Regex;

public class ConnectionSql{
    public static int flagLogin;
    public static int flagRegister;
    //项目用到的sql语句
    private final static String SQL1 = "select * from manager where name = ? AND password = ? ";  //查登录用户名和密码的sql
    private final static String SQL2 = "insert into student_tb( IDcard, name, age, Mygroup, hometown)  values (?,?,?,?,?)"; // 查学生基本信息的sql
    private final static String SQL3 = "select * from student_tb where name = ? union select * from student_tb where IDcard = ? "; //删除学生基本信息sql
    private final static String SQL4 = "delete from student_tb where name = ? OR  IDcard = ? ";
    private final static String SQL5 = "select * from manager where name = ?";
    private final static String SQL6 = "insert into manager(name, password) values (?,?)";
    private final static String SQL7 = "select s_tb.name '学生姓名', c.name '课程'  from student_tb s_tb,weekdays w,course c\n" +
            "         where s_tb.IDcard in (select s_c.student_IDcard from student_course s_c where s_c.student_IDcard = ?)\n" +
            "        AND w.id in (select s_c.weekdays_id from student_course s_c where s_c.weekdays_id = ?)  \n" +
            "           And c.id in (select s_c.course_id from student_course s_c where s_c.student_IDcard = ?\n" +
            "                                                                                  AND s_c.weekdays_id = ?)";
    //jdbc三大件
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    //与数据库交互的参数
    private String $name;
    private String $password;
    private String stuName;
    private String stuID;
    private String stuAge;
    private String stuGroup;
    private String stuHometown;
    private String find;
    private String remove;
    //数据库连接地址、账号、密码
    private final static String URL = "jdbc:mysql://192.168.93.1:3306/student_db?useSSL=false&serverTimezone=UTC";
    private final static String USER = "lrq";
    private final static String PASSWORD = "LLL268426zxcvbnm";

    //------构造器：获取用户输入的姓名和密码，进行校验
    public ConnectionSql(String $name, String $password) {
        this.$name = $name;
        this.$password = $password;
    }
    //-----构造器：获取管理员输入的学生基本信息
    public ConnectionSql(String stuName, String stuID, String stuAge, String stuGroup, String stuHometown) {
        this.stuName = stuName;
        this.stuID = stuID;
        this.stuAge = stuAge;
        this.stuGroup = stuGroup;
        this.stuHometown = stuHometown;
    }
    //-----构造器：获取管理员输入的查询(删除)命令
    public ConnectionSql(String find){
        this.find = find;
    }
    //无参构造器
    public ConnectionSql(){};

    public void ConnectSQL() throws Exception {

        // 1. 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. 获取连接
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //检查登录的方法
    public boolean CheckLogin(){
      if (!Regex.RegexLoginNameAndPassword(this.$name,this.$password)){
          flagLogin = 0;   //被正则过滤，flagLogin == 0
          return false;
      }
        try {
            ConnectSQL();   //调用连接数据库函数
        // 3. 创建执行对象（prepareStatement防止sql注入）
            stmt = conn.prepareStatement(SQL1);
            stmt.setString(1,this.$name);
            stmt.setString(2,this.$password);

            //最终的sql语句：select * from manager where name = this.$name  AND password = this.$password
            rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            }else{
                //important!!!
                flagLogin = 1;
                return false;
            }
        } catch (Exception e) {
            flagLogin = 404;   //出现异常，flagLogin == 404
            throw new RuntimeException(e);
        }
    }
    //注册
    public boolean CheckedRegister(String name){
        try {
            ConnectSQL();
            stmt = conn.prepareStatement(SQL5);
            stmt.setString(1,name);
            rs = stmt.executeQuery();
            if (rs.next()){
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean Register(String name, String password){
        //如果没有注册
        if (!Regex.RegexLoginNameAndPassword(name,password)){
            flagRegister = 0;  //被正则过滤， flagRegister == 0
            return false;
        }
        try {
            if(CheckedRegister(name)){
                flagRegister = 2;  //已经注册， flagRegister == 2
               return false;
            }
            stmt = conn.prepareStatement(SQL6);
            stmt.setString(1,name);
            stmt.setString(2,password);
            int r = stmt.executeUpdate();
            if(r > 0){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            flagRegister = 404;  //出现异常，flagRegister == 404
            throw new RuntimeException(e);
        }
    }
    //录入学生信息的方法
    public boolean setStudentBasicalInformation(){
        try {
            ConnectSQL();   //调用连接数据库函数
            // 3. 创建执行对象（prepareStatement防止sql注入）
            stmt = conn.prepareStatement(SQL2);
            stmt.setString(1,stuID);
            stmt.setString(2,stuName);
            stmt.setString(3,stuAge);
            stmt.setString(4,stuGroup);
            stmt.setString(5,stuHometown);
            int r =  stmt.executeUpdate();

            if(r > 0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("录入失败，请重新操作");
            return false;
        }
    }
    //获取学生基本信息的方法
    public StudentBasicalInformation getStudentBasicalInformation(){
        try {
            ConnectSQL();   //调用连接数据库函数
            // 3. 创建执行对象（prepareStatement防止sql注入）
            stmt = conn.prepareStatement(SQL3);
            stmt.setString(1,find);
            stmt.setString(2,find);

            rs = stmt.executeQuery();
            if(rs.next()){
                StudentBasicalInformation studentBasicalInformation = new StudentBasicalInformation(rs.getString(5),rs.getString(1),rs.getString(2),rs.getString(6),rs.getString(3));
                return studentBasicalInformation;
            }else{
                System.out.println("查询失败");
                return null;
            }
        } catch (Exception e) {
            System.out.println("删除失败");
        }
        return null;
    }

    //删除学生基本信息的方法，并将学生信息存入集合，作为缓存
    public StudentBasicalInformation  deleteStudentInformation(){
        try {
            ConnectSQL();   //调用连接数据库函数
            //查询
            PreparedStatement stmt1 = conn.prepareStatement(SQL3);
            stmt1.setString(1,find);
            stmt1.setString(2,find);
            rs = stmt1.executeQuery();
            //删除
            stmt = conn.prepareStatement(SQL4);
            stmt.setString(1,find);
            stmt.setString(2,find);

            stmt.executeUpdate();
            if (rs.next()){
             return  new StudentBasicalInformation(rs.getString(5),rs.getString(1),rs.getString(2),rs.getString(6),rs.getString(3));
            }else{
                System.out.println("查询失败，没有这个学生");
                return null;
            }
        } catch (Exception e) {
            System.out.println("删除失败");
        }
        return null;
    }
   public class StuCourse {
        String name;
        String[] course;

       public StuCourse(String name, String[] course) {
           this.name = name;
           this.course = course;
       }
   }

    public StuCourse  selectStudentCourse(String find1,String find2) throws Exception{
        ConnectSQL();   //调用连接数据库函数
        //查询
        PreparedStatement stmt = conn.prepareStatement(SQL7);
        stmt.setString(1,find1);
        stmt.setString(2,find2);
        stmt.setString(3,find1);
        stmt.setString(4,find2);
        rs = stmt.executeQuery();
        return null;
    }

}