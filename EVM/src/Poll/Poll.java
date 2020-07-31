package Poll;

import evm.nt_elg;
import evm.val_cons;
import evm.voted;
import evm.layout;
import Poll.Votes;

import java.io.BufferedReader;
import java.util.Scanner;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;

/**
/**
 * Poll
 */
public class Poll {

	public String constituency;
        public String a,b,c;
	private StringBuffer sb=new StringBuffer("");
        
        public Poll(String cand,String con,String id,StringBuffer s){
            System.out.println(cand+" "+con+" "+id);
            this.sb=s;
            vote(cand,con,id);
        }
	
	public Poll(String constituency)
	{
		this.constituency=constituency;
		boolean valid = Valid.validCons(constituency);
		if(!valid)
		{
			System.out.println("Invalid constituency!!!");
			return;
		}
		System.out.println("Valid");
                Votes v = new Votes();
		String candidates=getCandidates();
                System.out.println(candidates);
                v.setConst(constituency,candidates,sb);
                v.setVisible(true);
            	//System.out.println("Enter voterId : ");		//testing stuff
		//vote(candidates, constituency,new Scanner(System.in).nextLine());
	}

	private String getCandidates()
	{
		StringBuffer str=new StringBuffer("");
		String x;
		try{
			BufferedReader br=new BufferedReader(new FileReader("files/candidate.txt"));
			while((x=br.readLine())!=null)
			{
				String s[]=x.split(" ",5);		//temp change
				//System.out.println(s[s.length-2]);
				if((s[s.length-2]).equals(constituency))
				{
					str.append(s[0]+" ");
//					sb.append(++candidateCount);
					sb.append(" ");
					sb.append("<"+s[s.length-1]+">");
					for(int i=1;i<s.length-2;i++)
					{
						sb.append(" "+s[i]);
                                                //System.out.println(sb);
					}
					sb.append("\n");
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
                //System.out.println(sb);
		return new String(str);
	}
	public boolean vote(String c, String constituency, String voterID)		//call this function to vote in an ongoing poll
	{
                
		String candidates[]=c.split(" ");
		BufferedReader br,br2;
		String line;
                System.out.println(c+" "+constituency+" "+voterID);
		try{
			br= new BufferedReader(new FileReader("files/voter.txt"));	//votes received
			br2= new BufferedReader(new FileReader("files/polled.txt"));	//voters who already committed
	
//			System.out.println("Enter your Voter Id : ");
//			voterID=sc.nextLine();
			//System.out.println(voterID);
			while((line=br2.readLine())!=null)
			{
				if(line.equals(voterID))
				{
					System.out.println("Already voted");		//invalid case
					return false;
				}
			}
			while((line=br.readLine())!=null)
			{
				String v[]=line.split(" ");
				if(v[0].equals(voterID))
					if(v[v.length-1].equals(constituency))		//invalid case
						break;
					else
					{
						System.out.println("Constituency mismatch");
						return false;
					}
			}
			if(line==null)
			{
				System.out.println("Voter not found");			//invalid case
				return false;
			}
                        System.out.println(constituency+" "+candidates);
                        System.out.println(sb);
			new Trials().trial(constituency, candidates, new String(sb));
			addVoter(voterID);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally{
			return true;
		}
	}
	
	private void addVoter(String v)
	{
		FileWriter fw;
		try{
			fw=new FileWriter("files/polled.txt",true);
			fw.write(v);
			fw.write("\n");
			fw.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String args[])
		{
//                        Poll p = new Poll("kannur");
//                        p.vote("AAA0000001 AAA0000003 AAA0000013 AAA0000012 AAA0000006","kannur","AAA0000009");
                        //System.out.println("hello");
		}
}