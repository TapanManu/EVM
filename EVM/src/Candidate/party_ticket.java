package Candidate;

import java.io.FileWriter; 
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Calendar;
import Poll.Valid;
import evm.Error;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;


class IOn{
    public static String getDate(String searchid) throws IOException{
    	try{
			Class.forName("com.mysql.jdbc.Driver"); 
                        int flag=0;
                        Connection con=DriverManager.getConnection(  
                        "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
                          
                        Statement stmt=con.createStatement();  
                        ResultSet rs=stmt.executeQuery("select * from Voter"); 
                        String id;
                        while(rs.next()){  
                            id = rs.getString("Voter_ID");
                            if(id.equals(searchid)){
                                flag=1;
                                return rs.getDate("dob").toString();
                            }
                        } 
                        
                        con.close();  
           if(flag==0){
           	 new Error("no matching record");
           	 return null;
           }
            
        }
        catch(SQLException|ClassNotFoundException e){
             new Error("error");
             return null;
    	}
    	return null;
    }
}

class CandidateEligibility extends Exception{
	public CandidateEligibility(String s){
		super(s);
	}
}

class Ticket {
        public static String vid,cons,party = null;
        public Ticket(String id,String party,String con) throws IOException{
            //this.s=s;
            this.vid=id;
            this.party=party;
            if(party==null || party.equals(""))
                this.party="independent";
            this.cons=con;
            candi();
        }
        public static String getName(String searchid){
            try{
            Class.forName("com.mysql.jdbc.Driver"); 
                        int flag=0;
                        Connection con=DriverManager.getConnection(  
                        "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
                          
                        Statement stmt=con.createStatement();  
                        ResultSet rs=stmt.executeQuery("select * from Voter"); 
                        String id;
                        while(rs.next()){  
                            id = rs.getString("Voter_ID");
                            if(id.equals(searchid)){
                                flag=1;
                                return rs.getString("Name");
                            }
                        } 
                        
                        con.close();  
           if(flag==0){
           	 new Error("no matching record");
           	 return null;
           }
        }
        catch(SQLException|ClassNotFoundException e){
             new Error("error");
             return null;
    	}
    	return null;
        }
	public static void getDiff(GregorianCalendar a,GregorianCalendar b) throws CandidateEligibility{
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
            if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
                   (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
                                    diff--;
                                }
        if(diff<25)
        	throw new CandidateEligibility("under 25 years of age !\nnot eligible for application");
	}
        public static boolean isValidParty(String pname) throws IOException{
            try{
                //checking if party provided is registered party or not
                Class.forName("com.mysql.jdbc.Driver");  
                Connection con=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
                //here sonoo is database name, root is username and password  
                Statement stmt=con.createStatement();  
                ResultSet rs=stmt.executeQuery("select * from Party"); 
                String p;
                while(rs.next())  {
                    p = rs.getString("pname").toLowerCase();
                    if(pname.equals(p) || pname.equals("independent")) //registered party 
                        return true;
                }     
                con.close();  
            }
            catch(SQLException|ClassNotFoundException e){
                new Error("error processing");
            }
            return false;
        }
	public static void candi() throws IOException{
		try{
                        Class.forName("com.mysql.jdbc.Driver");  
                        Connection con=DriverManager.getConnection(  
                        "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
                         int valid=1;
                        Statement stmt=con.createStatement(); 
			int count=0;
			long cid=0;
			GregorianCalendar g;
			while(count<1){
				//data should be read from aadhar database
                                //System.out.print("value:"+party);
                                if(!isValidParty(party.toLowerCase())){
                                    new Error("unregistered party,\n application rejected");
                                    count++;
                                    continue;
                                }
				try{
                                   String[] date;
				  try{
				     date = IOn.getDate(vid).split("-",4);
                                     
			         }
			      catch(NullPointerException np){
                      new Error("No voter ID! Ineligible to apply");
                      count++;
                      continue;
			    }
		g = new GregorianCalendar(Integer.parseInt(date[0]),Integer.parseInt(date[1])-1,Integer.parseInt(date[2]));
                getDiff(g,new GregorianCalendar());
                }
                catch(CandidateEligibility e){
                      new Error(e.getMessage());
                      count++;
                      continue;
                }
                catch(NumberFormatException ne){
                  new Error("number error");
                  count++;
                  continue;
                }
                /*try{
                  BufferedReader br = new BufferedReader(
                      new FileReader("C:\\Users\\Tapan\\Documents\\NetBeansProjects\\EVM\\src\\Candidate\\"
                              + "candidateid.txt"));
                     cid = Long.parseLong(br.readLine());
                     br.close();
                }   
                catch(IOException e){
                    System.out.println("file not found error");
                }
                */
                String str=null;
                try{
                str = getName(vid);
                }
                catch(NullPointerException y){
                    new Error("No matching record");
                }
                String[] strArray = new String[]{String.valueOf(vid).toLowerCase(),cons.toLowerCase(),
                                                       party.toLowerCase(),str.toLowerCase()};
                System.out.println(strArray[0]);
                System.out.println(strArray[1]);
                System.out.println(strArray[2]);
                System.out.println(strArray[3]);
                int validity = ValidCandidate.valid(strArray);
                if(validity==1 && Valid.validCons(cons.toLowerCase())){
                Candidate cd = new Candidate(vid,cons,party);
				
				 String out="Insert into Candidate values('"+cd.vid()
                                        +"','"+str+"','"+cons.toLowerCase()+"','"+party.toLowerCase()+"')";
                                stmt.executeUpdate(out);
                                Error.display("Candidate added");
			}
                else{
                    new Error("invalid registration");
                }
                ++count;
                        }
		}
		catch(SQLException|ClassNotFoundException e){
			new Error("The system has detected some failure!");
		}
		
	}
}