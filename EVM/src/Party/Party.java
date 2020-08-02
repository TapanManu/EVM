package Party;

import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import evm.Error;
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
        party();//To change body of generated methods, choose Tools | Templates.
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
       System.out.println("");
       try(FileWriter out = 
               new FileWriter("files/party.txt",true))
       {
            Scanner sc = new Scanner(System.in);
            String s= partyname ;
            Party p=null;
            String result = addParty(s,p);
            if(result!=null){
                out.write(result);
                out.write("\n");
                Error.display("New Party added");
            }
       }
       catch(IOException e){
           new Error("error");
       }
}
}
