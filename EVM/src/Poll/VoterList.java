package Poll;

import evm.Error;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tapan
 */
public class VoterList {
    private static String userName="tapan";
    private static String password="tapan*1234";
    public static String generate(String cons){
        String list="<html><body><h2>Voter List("+cons.toUpperCase()+")</h2><table><tr><th>Voter ID</th><th>Name</th><th>DOB</th></tr>";
        try{
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection con=DriverManager.getConnection(  
               "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");
            Statement stmt=con.createStatement();  
      
            String s="select cname from constituency where cname='"+cons+"'";
            
            ResultSet rs=stmt.executeQuery(s);
            if (rs.next() == false){
                new Error("No such constituency");
                return "error";
            }
            s= "select * from Voter where constituency='"+cons+"'";
            rs=stmt.executeQuery(s);
            if (rs.next() == false){
                new Error("No voters in this constituency");
            }
            else{
                String v=rs.getString("Voter_id");
                String n=rs.getString("Name");
                Date dob=rs.getDate("dob");
                System.out.println(v + " " + n + " " + dob);
                list+=("<tr><td>"+v+"</td><td>"+n+"</td><td>"+dob+"</td></tr>");
            }
            while(rs.next()){
                String v=rs.getString("Voter_id");
                String n=rs.getString("Name");
                Date dob=rs.getDate("dob");
                System.out.println(v + " " + n + " " + dob);
                list+=("<tr><td>"+v+"</td><td>"+n+"</td><td>"+dob+"</td></tr>");
            }
            list+="</table></body></html>";
            return list;
       }
       catch(ClassNotFoundException|SQLException e){
           new Error("error");
       }
        return "Cannot generate list";
    }
    /*public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        String s=sc.nextLine();
        System.out.println(generate(s));
    }*/
    
}
