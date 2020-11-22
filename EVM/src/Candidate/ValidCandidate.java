/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Candidate;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author tapan
 */
public class ValidCandidate {
    public static boolean partyPlace(String[] w,String[] list){
        //System.out.println("i am here 1 !");
        //diff candidate with same party and cons are not allowed
        //System.out.println(w.length);
       
        //convert to lowercase and check
        return !(w[w.length-2].equals(list[1]) && w[w.length-1].equals(list[2]));
        }
    public static boolean candidateParty(String[] w,String[] list){
        //candidate can contest only for same party,if diff party then terminate
        //System.out.println("i am here 2 !");
       
        return (w[w.length-1].equals(list[2]) && w[0].equals(list[0])||
                !w[0].equals(list[0]));
    }
    public static boolean idName(String id,String name){
        try{
            //checking if voter id and name are equal else application is rejected!
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
             
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from Voter");
            String r,s ;
            while(rs.next()){  
                r = rs.getString("Voter_ID");
                s = rs.getString("Name");
               
                if(r.equals(id) && s.equals(name)){
                    return true;
                }
            }
            con.close();  
         
         
         return false;
        }
        catch(SQLException|ClassNotFoundException ie){
            System.out.println("error");
        }
        return false;
        
    }
    //ID  CONS PARTY  NAME
    public static int valid(String[] list){
        try{
          Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
             int valid=1;
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from Candidate");
            String r,s,t,u;
            while(rs.next()){  
                r = rs.getString("vid").toLowerCase();
                s = rs.getString("cand_name").toLowerCase();
                t = rs.getString("cons").toLowerCase();
                u = rs.getString("pticket").toLowerCase();
                String w[] = new String[]{r,s,t,u};
                //should check all lines whether new candidate list is valid
                //System.out.println(partyPlace(w,list));

                //System.out.println(candidateParty(w,list));
                //System.out.print(list[2]);
            if(!((partyPlace(w,list)) && (candidateParty(w,list))))
                valid=0;
            }
        rs=stmt.executeQuery("select * from Candidate");
        String a,x,y,z;
        //check for independent
        while(rs.next()){
            a = rs.getString("vid").toLowerCase();
            x = rs.getString("cand_name").toLowerCase();
            y = rs.getString("cons").toLowerCase();
            z = rs.getString("pticket").toLowerCase();
            String[] w = new String[]{a,x,y,z};
            if(list[2].equals("independent")){
            if(list[0].equals(w[0])){
                System.out.println("entered");
                if(list[2].equals(w[w.length-1])){
                    if(list[1].equals(w[w.length-2])){
                         valid=0;
                         System.out.println("x");
                         break;
                    }    
                    else{
                         valid=1;
                         System.out.println("xx");
                    }    
                }
                else{
                    valid=0;
                    System.out.println("enteredxx");
                    break;
                }
                    
            }
            else
                valid=1;
        }
        }
        con.close();
        return valid;
        }
        catch(SQLException|ClassNotFoundException ie){
            System.out.println("cannot find");
        }
        return -1;  //error occurred!
    }
    
}
