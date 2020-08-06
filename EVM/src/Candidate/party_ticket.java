package Candidate;

import java.io.FileWriter; 
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Calendar;
import Poll.Valid;
import evm.Error;


class IOn{
    public static String getDate(String searchid) throws IOException{
    	try{
    	BufferedReader in = new BufferedReader(new FileReader("files/voter.txt"));
    	int flag=0;
    	String line;
    	while((line=in.readLine())!=null && !(searchid.equals(null)))  
           {  
               String[] lsplit =  line.split(" ",5);
               //System.out.println(lsplit[4]);
               if(lsplit[0].equals(searchid)){
               	 flag=1;
               	 return (lsplit[lsplit.length-2]);
               }
           }  
           if(flag==0){
           	 new Error("no matching record");
           	 return null;
           }
            
        }
        catch(IOException e){
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
        public static String getName(String id){
            String str=null;
            try{
            BufferedReader br = new BufferedReader(new FileReader("files/voter.txt"));
            String line;
            while((line=br.readLine())!=null){
                System.out.println(line.split(" ",5)[0]);
                System.out.println(id);
                if(line.split(" ",5)[0].equals(id)){
                    //System.out.print("hello");
                    str = line.split(" ",5)[1];
                    break;
                }

            }
            br.close();
            }
            catch(IOException |NullPointerException x){
                new Error("No Matching name found");
            }
            if(str==null)
                return null;
            return str;
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
                BufferedReader br = new BufferedReader(
                      new FileReader("files/party.txt"));
                String line ;
                while((line=br.readLine())!=null){
                    String name = line.split(" ",3)[1];
                    System.out.print(pname);
                    if(pname.equals(name) || pname.equals("independent")) //registered party 
                        return true;
                }
            }
            catch(IOException e){
                new Error("error processing");
            }
            return false;
        }
	public static void candi() throws IOException{
		try(FileWriter out = new FileWriter("files/candidate.txt",true)){
			int count=0;
			long cid=0;
			GregorianCalendar g;
			while(count<1){
				//data should be read from aadhar database
                                //System.out.print("value:"+party);
                                if(!isValidParty(party)){
                                    new Error("unregistered party,\n application rejected");
                                    count++;
                                    continue;
                                }
				try{
                                   String[] date;
				  try{
				     date = IOn.getDate(vid).split("/",4);
                                     
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
                  new Error("error");
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
                int validity = Validity.valid(strArray);
                if(validity==1 && Valid.validCons(cons)){
                Candidate cd = new Candidate(vid,cons,party);
				String output = cd.vid()+ " " + str + " " + cons + " " + party ;
				out.write(output);
				out.write("\n");
                                Error.display("Candidate added");
			}
                else{
                    new Error("invalid registration");
                }
                ++count;
                        }
		}
		catch(IOException e){
			new Error("The system has detected some failure!");
		}
		
	}
}