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

import Crypto.Decrypt;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
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
        public static ArrayList<Boolean> election = new ArrayList<Boolean>();
        public static int count(String str) throws IOException 
        {
            File f1=new File(str); //Creation of File Descriptor for input file
            int linecount=0;            //Intializing linecount as zero
            FileReader fr=new FileReader(f1);  //Creation of File Reader object
            BufferedReader br = new BufferedReader(fr);    //Creation of File Reader object
            String s;              
            while((s=br.readLine())!=null)    //Reading Content from the file line by line
            {
              linecount++;               //For each line increment linecount by one 

            }
            fr.close();
            return linecount;//Print the line count
        }
        public Result(String c){
            this.con=c;
            System.out.println(con);
            try{
                total=false;
                resu();
            }
            catch(IOException e){
                new Error("IO Error");
            }
        }
        public Result(){
            try{
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
              BufferedReader r = new BufferedReader(new FileReader("files/candidate.txt"));
        	String line;
        	while((line=r.readLine())!=null){
        	     String[] list = line.split(" ",5);
                     if(list[list.length-2].equals(cons)){
                         count++;
                     }
                }
        	r.close(); 
        	}
        catch (IOException e){
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
              BufferedReader r = new BufferedReader(new FileReader("files/voter.txt"));
        	String line;
        	while((line=r.readLine())!=null){
        	     String[] list = line.split(" ",5);
                     if(list[list.length-1].equals(cons)){
                         totalcount++;
                     }
                }
        	r.close(); 
        	}
        catch (IOException e){
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
	 
	public static void declare(ArrayList<String> cons,Candidate[] c,int length) throws IOException{
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
        
		//declaring winner for each constituencies
		try(BufferedReader br = new BufferedReader(new FileReader("files/candidate.txt"))){
		for(int j=0;j<cons.size();j++)
			for(int i=0;i<length;i++)
				if((c[i].getVotes() == max[j]) && (c[i].cons().equals(cons.get(j)))){
                                        complete = false;
					if(flag==0){
					    while((line=br.readLine())!=null){
					    if(line.split(" ",5)[0].equals(c[i].vid())){
					    System.out.println(c[i].cons()+" "+con);
                                            //System.out.println(con.equals(c[i].cons()));
                                            if(con!=null){
                                            if(con.equals(c[i].cons())){
                                                Result.w = (max[j]-secmax[j]);
                                            }
                                            }
                                            
					    if(isLoneCandidate(cons.get(j))){                        
                                                        System.out.println("Winner:"+line.split(" ",5)[1]+" of "+c[i].party()+" of "+c[i].cons()+" as no other candidate registered");
					        	complete = true;
					        }
                                            else if(max[j]>0){
                                                        System.out.println("Winner:"+line.split(" ",5)[1]+" of "+c[i].party()+" of "+c[i].cons()+" by "+(max[j]-secmax[j])+" votes ");
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
				
       }
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
           BufferedReader r = new BufferedReader(new FileReader("files/party.txt"));
           String p;
           while((p=r.readLine())!=null){
           	 pr.add(p.split(" ")[1]);
           }
           int t;
           for(String s:pr){
                 t = Collections.frequency(party,s);
                 seats.add(String.valueOf(t));
           	 System.out.println(s+" "+t);
           }
        }	
        catch(IOException ei){
        	new Error("File not found");
        }
        new Pie(seats,pr,total);
	}
    public static void addVotes(Candidate[] c,int length,ArrayList<String> cons)throws IllegalBlockSizeException, InvalidKeyException, 
                                NoSuchPaddingException, BadPaddingException{
    	try{
            new Decrypt("files/votes.encrypted",privateKey);
    	    RandomAccessFile b = new RandomAccessFile("files/votes.decrypted","r");
            String line;
            //int j=0;
            int notavotes=0;
            for(int i=0;i<length;i++){
            	b.seek(0);
            while((line=b.readLine())!=null){
            	if((line.split(" ",2)[0].equals(c[i].vid())) &&(line.split(" ",2)[1].equals(c[i].cons()))){
            		c[i].incrVotes();
                        
                }
                else if(line.split(" ",2)[0].equals("NOTA"))
                        notavotes++;
                
            }
            if(con!=null){
            if(con.equals(c[i].cons())){
               
               ++cnt;
               l.add(c[i].getVotes()+"");
               t.add(c[i].party()+"");
               
            }
            }
            
            System.out.println(c[i].vid()+ " " +c[i].getVotes()+" "+c[i].cons());
        }
            //System.out.println("set:"+l.size()+t.size());
            if(con!=null){
            ++cnt;
            l.add(String.valueOf(notavotes));
            t.add("NOTA");
            }
            
        declare(cons,c,length);

    	}
    	catch(IOException e){
    		new Error("no file output");
    	}
    }

	
	public void resu() throws IOException{               
		//read from database about unique constituencies
		ArrayList<String> cons = new ArrayList<String>();
        try{
        	RandomAccessFile b = new RandomAccessFile("files/constituency.txt","r");
        	String line;
        	while((line=b.readLine())!=null){
        		cons.add(line.split(" ",3)[1]);
                        
                }
        	b.close();
        	}
        catch(IOException e){
        	new Error("error");
        }
        //count lines in candidate file
        int count = 0;
        try{
        	BufferedReader r = new BufferedReader(new FileReader("files/candidate.txt"));
        	String line;
        	while((line=r.readLine())!=null)
        	     count++;
        	r.close(); 
        	}
        catch (IOException e){
        	new Error("error processing file");
        }	
        try{
           RandomAccessFile r = new RandomAccessFile("files/candidate.txt","r");
        	Candidate c[] = new Candidate[count];
        	String line;
        	int j=0; 
                
                
        	for(int i=0;i<cons.size();i++){
        		r.seek(0);   //reset back to 0 (assumed)
        		//check for file reset pointer and no file output error
        		j=0;
                        if(con!=null){
                        cnt=0;
                        l.clear();
                        t.clear();
                        }
                    
        	    while((line=r.readLine())!=null){
        	    	//loading candidate data
                    String[] list = line.split(" ",5);
                    if(list[list.length-2].equals(cons.get(i))){
                    	//System.out.println(list[0]+" "+list[list.length-2]);
                    	c[j++] = new Candidate(list[0],list[list.length-2],list[list.length-1]);
                    	}
        	    }
                try{
                    
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
                

        }
        catch(IOException e){
        	new Error("error file");
        }
        //Res r = new Res(w,cnt,l);
   
        Pie p = new Pie(w,cnt,l,t,total);
        float g,q,per;
        g=count("files/polled.txt");
        q=count("files/voter.txt");
        //System.out.print(count("files/polled.txt"));
        per = g/q;
        System.out.println("Polled percentage : "+per);
	}
        
	}
