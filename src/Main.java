/*
	function:在导入Excel的时候密码第一位为0时Excel自动将0抛弃；脚本将所有5位数密码更改为0+原5位数密码，即“身份证后6位”；
	author:龙猫
	date:2018/10/16
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main implements user{

    public static void main(String[] args) {
        Connection conn = null ;
        Statement myStatement = null;
        ResultSet mySet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Class.forName("com.mysql.jdbc.Driver ");
            conn = DriverManager.getConnection(url,user,password);		//账号密码，自定义
            myStatement = conn.createStatement();				//建立JDBC连接
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
        	int time = 0,max = 1;		//设置运行次数
			mySet = myStatement.executeQuery("select * from users where length(password) < 6;");		//向数据库查询记录
            while (mySet.next() & time < max) {
				String uid = mySet.getString("uid");				//获取数据库的值
				String password = mySet.getString("password");
				System.out.println("重置前学号为：" + uid + ",密码为：" + password);
				password = "0" + password;			//将密码前面 + “0”
				String sql = "update users set password = '" + password + "' where uid = '" + uid + "';";		//将要插入数据库的语句
				myStatement.executeUpdate(sql);		//将语句插入数据库
				System.out.println("已成功重置学号为：" + uid + "的用户密码为：" + password);
				time++;
				mySet = myStatement.executeQuery("select * from users where length(password) < 6;");		//重新查询
			}
        }catch(Exception e){
            e.printStackTrace();
        }finally {
        	try {
				mySet.close();
			}catch (Exception e){
        		e.printStackTrace();
			}
		}
    }
}
