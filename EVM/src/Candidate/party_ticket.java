package Candidate;

import java.io.FileWriter; 
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Calendar;
import Poll.Valid;


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
           	 System.out.println("no matching record");
           	 return null;
           }
            
        }
        catch(IOException e){
             System.out.println("error");
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
        public static String vid,cons,party,s = null;
        public Ticket(String s,String id,String party,String con) throws IOException{
            this.s=s;
            this.vid=id;
            this.party=party;
            this.cons=con;
            candi();
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
                    if(pname.equals(name)) //registered party 
                        return true;
                }
            }
            catch(IOException e){
                System.out.println("error processing");
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
        
                                if(!isValidParty(party)){
                                    System.out.println("unregistered party,\n application rejected");
                                    count++;
                                    continue;
                                }
				try{
                                   String[] date;
				  try{
				     date = IOn.getDate(vid).split("/",4);
                                     
			         }
			      catch(NullPointerException np){
                      System.out.println("No voter ID! Ineligible to apply");
                      count++;
                      continue;
			    }
				        g = new GregorianCalendar(Integer.parseInt(date[0]),Integer.parseInt(date[1])-1,Integer.parseInt(date[2]));
                getDiff(g,new GregorianCalendar());
                }
                catch(CandidateEligibility e){
                      System.out.println(e.getMessage());
                      count++;
                      continue;
                }
                catch(NumberFormatException ne){
                  System.out.println("error");
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
                String[] strArray = new String[]{String.valueOf(vid).toLowerCase(),cons.toLowerCase(),
                                                       party.toLowerCase(),s.toLowerCase()};
                System.out.println(strArray[0]);
                System.out.println(strArray[1]);
                System.out.println(strArray[2]);
                System.out.println(strArray[3]);
                int validity = Validity.valid(strArray);
                if(validity==1 && Valid.validCons(cons)){
                Candidate cd = new Candidate(vid,cons,party);
				String output = cd.vid()+ " " + s + " " + cons + " " + party ;
				out.write(output);
				out.write("\n");
			
			}
                else{
                    System.out.println("invalid registration");
                }
                ++count;
                        }
		}
		catch(IOException e){
			System.out.println("The system has detected some failure!");
		}
		
	}
}