package Voter;

import Poll.Valid;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.FileWriter; //used instead of Database to store temporary(voter's list) data 
//import Voter.Voter ;   //compile in parent directory of voter i.e in src otherwise error

//voter validity and removal of duplicates can be done only with address field provided!!

class VoteEligibility extends Exception{
	public VoteEligibility(String s){
		super(s);
	}
}

class NewVoter{
        public static String s,c,dob;
        public static long uid;
        public NewVoter(String s,int id,String dob,String con) throws IOException{
            this.s=s;
            this.uid=id;
            this.dob=dob;
            this.c=con;
            vote();
        }
	public static void getDiff(GregorianCalendar a,GregorianCalendar b) throws VoteEligibility{
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
            if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
                   (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
                                    diff--;
                                }
        if(diff<18)
        	throw new VoteEligibility("under 18 years of age !\nnot eligible for application");
	}

	public static void vote() throws IOException{
		try(FileWriter out = new FileWriter("files/voter.txt",true)){
			int count=1;
			GregorianCalendar g ;
			while(count<=1){
				//data should be read from aadhar database or enter manually
				String[] date=dob.split("/",3);
				try{
				g = new GregorianCalendar(Integer.parseInt(date[0]),Integer.parseInt(date[1])-1,Integer.parseInt(date[2]));
                getDiff(g,new GregorianCalendar());
                }
                catch(VoteEligibility e){
                      System.out.println(e.getMessage());
                      count++;
                      continue;
                }
                if(Valid.validCons(c)){
				Voter v = new Voter(uid,s,g,c);
				String output = v.getVoterId()+ " " + s + " " + dob +" "+c;
				out.write(output);
				out.write("\n");
			    }
			    else{
			    	System.out.println("unidentified constituency");
			    }
				++count;
			}
		}
		catch(IOException e){
			System.out.println("The system has detected some failure!");
		}
          

	}
}