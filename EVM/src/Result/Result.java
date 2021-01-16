package Result;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import Candidate.Candidate;
import java.util.Collections;
import evm.Error;
import java.util.HashMap;

import Crypto.Decrypt;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.crypto.BadPaddingException;

public class Result {
        public static int cnt=0;
        public static ArrayList<String> l = new ArrayList<String>();
        public static ArrayList<String> t = new ArrayList<String>();
        public static long w=0;
        public static int k=0;
        public static String con="";
        public static boolean complete=false;
        public static boolean total = false;
       /* public static HashMap<String,Boolean> election = new HashMap<String,Boolean>();
        
        public static void setStatus(String con){
            election.put(con,true);
        }
        public static boolean getStatus(String can){
            if(election.containsKey(con)){
                return election.get(con);
            }
            return false;
        }*/
        public static void clear_result(){
            try{
                FileWriter fw = new FileWriter("files/party_win.txt");
                fw.write("");
                fw.close();
            }
            catch(IOException i){
                i.printStackTrace();
            }
        }
        public static int count(String str) throws IOException 
        {
            try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection conn=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  

            Statement stmt=conn.createStatement(); 
            ResultSet rs=stmt.executeQuery("select * from "+ str);
            
            int linecount=0;            //Intializing linecount as zero
           
            String s;              
            while(rs.next())    //Reading Content from the file line by line
            {
              linecount++;               //For each line increment linecount by one 

            }
            conn.close();
            return linecount;//Print the line count
            }
            catch(SQLException |ClassNotFoundException e){
                System.out.println(e);
            }
            return 0;
        }
        public Result(String c){
            this.con=c;
            System.out.println(con);
            try{
                clear_result();
                total=false;
                resu();
            }
            catch(IOException e){
                new Error("IO Error");
            }
        }
        public Result(){
            try{
            clear_result();
            this.con=null;
            total=true;
            resu();
            party_declare();
            }
            catch(IOException e){
                new Error("no total results");
            }
        }
        public static boolean isLoneCandidate(String cons){
            int count=0;
            try{
                Class.forName("com.mysql.jdbc.Driver");  
                Connection con=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  

                Statement stmt=con.createStatement();  
                ResultSet rs=stmt.executeQuery("select * from Candidate");  
              
        	String line;
        	while(rs.next()){
        	     line = rs.getString("cons");
                     if(line.equals(cons)){
                         count++;
                     }
                }
        	
        	}
        catch (ClassNotFoundException|SQLException e){
        	new Error("error processing file");
        }	  
            return count==1;   
        }
         public static void pollCount(String cons){
             ArrayList<String> polled = new ArrayList<String>();
             ArrayList<Integer> count = new ArrayList<Integer>();
            int pollcount=0;
            int totalcount=0;
            if(cons==null){
                new Error("no constituency provided");
                return;
            }
            try{
              BufferedReader r = new BufferedReader(new FileReader("files/votes.txt"));
        	String line;
        	while((line=r.readLine())!=null){
        	     String[] list = line.split(" ",2);
                     if(list[list.length-1].equals(cons)){
                         pollcount++;
                     }
                }
        	r.close(); 
        	}
        catch (IOException e){
        	new Error("error processing file");
        }	  
            try{
                Class.forName("com.mysql.jdbc.Driver");  
                Connection conn=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  

                Statement stmt=conn.createStatement();  
                ResultSet rs=stmt.executeQuery("select * from Voter");  
             
        	String line;
        	while(rs.next()){
        	     line = rs.getString("Constituency");
                     if(line.equals(cons)){
                         totalcount++;
                     }
                }
        	conn.close();
        	}
        catch (SQLException|ClassNotFoundException e){
        	new Error("error processing file");
        }	
            int remaining = totalcount - pollcount;
            polled.add("Polled");
            count.add(pollcount);
            polled.add("Not Polled");
            count.add(remaining);
            new Pie(count,false,polled);
        }
        private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAUZV+tjiNBKhlBZbKBnzeugpdYPhh5PbHanjV0aQ+LF7vetPYhbTiCVqA3a+Chmge44+prlqd3qQCYra6OYIe7oPVq4mETa1c/7IuSlKJgxC5wMqYKxYydb1eULkrs5IvvtNddx+9O/JlyM5sTPosgFHOzr4WqkVtQ71IkR+HrAgMBAAECgYAkQLo8kteP0GAyXAcmCAkA2Tql/8wASuTX9ITD4lsws/VqDKO64hMUKyBnJGX/91kkypCDNF5oCsdxZSJgV8owViYWZPnbvEcNqLtqgs7nj1UHuX9S5yYIPGN/mHL6OJJ7sosOd6rqdpg6JRRkAKUV+tmN/7Gh0+GFXM+ug6mgwQJBAO9/+CWpCAVoGxCA+YsTMb82fTOmGYMkZOAfQsvIV2v6DC8eJrSa+c0yCOTa3tirlCkhBfB08f8U2iEPS+Gu3bECQQCrG7O0gYmFL2RX1O+37ovyyHTbst4s4xbLW4jLzbSoimL235lCdIC+fllEEP96wPAiqo6dzmdH8KsGmVozsVRbAkB0ME8AZjp/9Pt8TDXD5LHzo8mlruUdnCBcIo5TMoRG2+3hRe1dHPonNCjgbdZCoyqjsWOiPfnQ2Brigvs7J4xhAkBGRiZUKC92x7QKbqXVgN9xYuq7oIanIM0nz/wq190uq0dh5Qtow7hshC/dSK3kmIEHe8z++tpoLWvQVgM538apAkBoSNfaTkDZhFavuiVl6L8cWCoDcJBItip8wKQhXwHp0O3HLg10OEd14M58ooNfpgt+8D8/8/2OOFaR0HzA+2Dm";
	 
	public static void declare(ArrayList<String> cons,Candidate[] c,int length){
        long max[]=new long[cons.size()];
		long secmax[] = new long[cons.size()];
        String line;
        int flag=0;

		for(int i=0;i<length;i++){
			for(int j=0;j<cons.size();j++){
			    if (c[i].cons().equals(cons.get(j))){
				    if(c[i].getVotes()>max[j]){
				    	secmax[j]=max[j];
					    max[j]=c[i].getVotes();
					    //System.out.println(c[i].vid());
				    }
				    else if(c[i].getVotes()>secmax[j] && c[i].getVotes()<max[j])
				 	    secmax[j]=c[i].getVotes();
				 }
		                }
		            }
                
		try{//declaring winner for each constituencies
                Class.forName("com.mysql.jdbc.Driver");  
                Connection conn=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  

                Statement stmt=conn.createStatement();  
                ResultSet rs=stmt.executeQuery("select * from Candidate");
                
		//try(BufferedReader br = new BufferedReader(new FileReader("files/candidate.txt"))){
		for(int j=0;j<cons.size();j++){
                        System.out.println(length);
			for(int i=0;i<length;i++){
                             
				if((c[i].getVotes() == max[j]) && (c[i].cons().equals(cons.get(j)))){
                                   
                                        complete = false;
					if(flag==0){
					    while(rs.next()){
                                                String s = rs.getString("vid");
                                                System.out.println(s+" "+c[i].vid());
                                                if(s.equals(c[i].vid())){
                                                    
                                                System.out.println(c[i].cons()+" "+con);
                                            //System.out.println(con.equals(c[i].cons()));
                                            if(con!=null){
                                            if(con.equals(c[i].cons())){
                                                Result.w = (max[j]-secmax[j]);
                                            }
                                            }
                                            String name = rs.getString("cand_name");
					    if(isLoneCandidate(cons.get(j))){                        
                                                        System.out.println("Winner:"+name+" of "+c[i].party()+" of "+c[i].cons()+" as no other candidate registered");
					        	complete = true;
					        }
                                            else if(max[j]>0){
                                                        System.out.println("Winner:"+name+" of "+c[i].party()+" of "+c[i].cons()+" by "+(max[j]-secmax[j])+" votes ");
					    		complete = true;
					        }
                                                k++;
                             
					        Boolean writeflag=false;
					        if(j==0)
					        	writeflag=false;    //prevent multiplewriting of same content
					        else
					        	writeflag=true;
					        try(FileWriter w = new FileWriter("files/party_win.txt",writeflag)){
                                                    if(complete)
					        	w.write(c[i].party()+" "+c[i].cons()+"\n");
					        	w.close();
					        }
					        catch(IOException ioe){
					        	new Error("File not Found");
					        }	   	
					    flag=1;
					    break;
					}
					}
					}
				
       }}}
                conn.close();
   }
   catch(SQLException|ClassNotFoundException e){
       System.out.println(e);
   }
               
}
        public static void party_declare() throws IOException{
		ArrayList<String> party = new ArrayList<String>();
		ArrayList<String> pr = new ArrayList<String>();
                ArrayList<String> seats = new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("files/party_win.txt"));
			String line;
			
			while((line=br.readLine())!=null){
				party.add(line.split(" ")[0]);
			}
		}
		catch(IOException ix){
			new Error("File not found1");
		}
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            Connection conn=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  

            Statement stmt=conn.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from Party"); 
           
           String p;
           while(rs.next()){
           	 pr.add(rs.getString("pname"));
           }
           pr.add("independent");
           int t;
           for(String s:pr){
                 t = Collections.frequency(party,s);
                 seats.add(String.valueOf(t));
           	 System.out.println(s+" "+t);
           }
           conn.close();
        }	
        catch(SQLException|ClassNotFoundException ei){
        	new Error("Table not found");
        }
        new Pie(seats,pr,total);
	}
    public static void addVotes(Candidate[] c,int length,ArrayList<String> cons)throws IllegalBlockSizeException, InvalidKeyException, 
                                NoSuchPaddingException, BadPaddingException{
    	try{
            new Decrypt("files/votes.encrypted",privateKey);
    	    RandomAccessFile b = new RandomAccessFile("files/votes.decrypted","r");
            String line;
            boolean flag=false;
            //int j=0;
            int notavotes[]=new int[length];
            for(int i=0;i<length;i++){
            	b.seek(0);
            while((line=b.readLine())!=null){
            	if((line.split(" ",2)[0].equals(c[i].vid())) &&(line.split(" ",2)[1].equals(c[i].cons()))){
            		c[i].incrVotes();
                        
                }
                else if(line.split(" ",2)[0].equals("NOTA") &&(line.split(" ",2)[1].equals(c[i].cons())))
                        notavotes[i]++;
                
            }
            if(con!=null){
            if(con.equals(c[i].cons())){
               
               ++cnt;
               l.add(c[i].getVotes()+"");
               t.add(c[i].party()+"");
               
            }
            }
            if(con!=null && !flag){
            ++cnt;
            l.add(String.valueOf(notavotes[i]));
            t.add("NOTA");
            flag=true;
            }
            
            System.out.println(c[i].vid()+ " " +c[i].getVotes()+" "+c[i].cons());
            //setStatus(c[i].cons());
        }
            //System.out.println("set:"+l.size()+t.size());
            
            
        declare(cons,c,length);

    	}
    	catch(IOException e){
    		new Error("no file output");
    	}
    }

	
	public void resu() throws IOException{               
		//read from database about unique constituencies
                
		ArrayList<String> cons = new ArrayList<String>();
        try{    Class.forName("com.mysql.jdbc.Driver");  
                Connection conn=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  

                Statement stmt=conn.createStatement();  
                ResultSet rs=stmt.executeQuery("select * from constituency"); 
        	
        	String line;
        	while(rs.next()){
                    line = rs.getString("cname");
                    cons.add(line);
                    System.out.println(cons);
                }
        	conn.close();
        	}
        catch(SQLException|ClassNotFoundException e){
        	new Error("cons error");
        }
        //count lines in candidate file
        int count = 0;
        try{
                Class.forName("com.mysql.jdbc.Driver");  
                Connection conn=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  

                Statement stmt=conn.createStatement();  
                ResultSet rs=stmt.executeQuery("select * from Candidate");
        	
        	while(rs.next())
        	     count++;
        	conn.close(); 
        	}
        catch (SQLException|ClassNotFoundException e){
        	new Error("error processing cands file");
        }	
        try{
            Class.forName("com.mysql.jdbc.Driver");  
                Connection conn=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  

                Statement stmt=conn.createStatement();  
                
           
        	Candidate c[] = new Candidate[count];
        	String line;
        	int j=0; 
                int flag=0;
                
        	for(int i=0;i<cons.size();i++){
        		  //reset back to 0 (assumed)
        		//check for file reset pointer and no file output error
        		j=0;
                        if(con!=null){
                        cnt=0;
                        l.clear();
                        t.clear();
                        }
                    //stmt=conn.createStatement();  
                    ResultSet rs=stmt.executeQuery("select * from Candidate");
        	    while(rs.next()){
        	    	//loading candidate data
                       line = rs.getString("cons");
                       System.out.println(line + cons.get(i));
           
                    if(line.equals(cons.get(i))){
                    	//System.out.println("xyzzzzzzzzz");
                        flag=1;
                    	c[j++] = new Candidate(rs.getString("vid"),line,rs.getString("pticket"));
                    	}
        	    }
                    
                try{
                System.out.println("jay:"+j);
                addVotes(c,j,cons); 
                }
                catch(IllegalBlockSizeException|InvalidKeyException|NoSuchPaddingException|BadPaddingException e){
                	new Error("crypto error");
                }
                if(con!=null){
                if(cons.get(i).equals(con))
                    break;
                }
                
                //System.out.println("PARTY_RESULTS");
        	//party_declare();
        	}
                
         conn.close();
        }
        catch(SQLException|ClassNotFoundException e){
        	new Error("error file");
        }
        //Res r = new Res(w,cnt,l);
   
        Pie p = new Pie(w,cnt,l,t,total);
        float g,q,per;
        g=count("Polled");
        q=count("Voter");
        //System.out.print(count("files/polled.txt"));
        per = g/q;
        System.out.println("Polled percentage : "+per);
	}
        
	}
