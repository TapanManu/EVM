package Party;

import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import evm.Error;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Tapan
 */
public class Party {
    private long pid;
    private static String partyname;
    private long seats;
    Party(long pid,String partyname){
        this.pid = pid;
        //instead of party symbol we use pid
        this.partyname = partyname;
        this.seats = 0;
    }

    Party(String party) throws IOException {
        this.partyname=party;
        party();
    }
    public long getPid(){
        return pid;
    }
    public String getPartyName(){
        return partyname;
    }
    public long getSeats(){
        return seats;
    }
    public void incrSeat(){
        ++seats;
    }
    public static String addParty(String partyname,Party p)throws IOException{
        long id=0;
        boolean status = false;
          try{
              BufferedReader br = new BufferedReader(
                      new FileReader("files/partyid_counter.txt"));
              id = Long.parseLong(br.readLine());
              p = new Party(id,partyname);
              status = true;
              br.close();
          }
          catch(IOException e){
              new Error("File not Found!");
          }
          try{
              FileWriter w = new FileWriter("files/partyid_counter.txt");
              w.write(String.valueOf(id+1)); 
              w.close();
          }
          catch(IOException ie){
              new Error("File not Found!");
          }
          if(status)
              return id + " " + partyname + " " + "0";
          else
              return null;
    }
    public static void party() throws IOException{
       try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
               "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
            Statement stmt=con.createStatement();  
      
            String s= partyname ;
            Party p=null;
            String result[] = addParty(s,p).split(" ");
            if(result!=null){
                String out="Insert into Party values('"+result[0]
                                        +"','"+result[1]+"','"+0+"')";
                stmt.executeUpdate(out);
                Error.display("New Party added");
            }
       }
       catch(ClassNotFoundException|SQLException e){
           new Error("error");
       }
}
}
