package Poll;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;


public class Valid{
	public static boolean validCons(String consname){
		try{
			Class.forName("com.mysql.jdbc.Driver");  
                        Connection con=DriverManager.getConnection(  
                        "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
                          
                        Statement stmt=con.createStatement();  
                        ResultSet rs=stmt.executeQuery("select * from constituency");  
                        while(rs.next()){  
                            String cons = rs.getString("cname");
                            if(consname.equals(cons)){
                                return true;            //valid constituency
                            }
                        } 
                        
                        con.close();  
                        
		}
		catch(ClassNotFoundException|SQLException s){
			System.out.println("no tables found");
			return false;
		}
		return false;
	}
}