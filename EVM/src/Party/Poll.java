package Party;

import Poll.Valid;
import evm.Trials;
import evm.nt_elg;
//import evm.Gui.val_cons;
//import evm.Gui.voted;
//import evm.Gui.layout;

import java.io.BufferedReader;
import java.util.Scanner;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;

/**
 * Poll
 */

public class Poll {

	public String constituency;
	private StringBuffer sb=new StringBuffer("");
	private int candidateCount=0;
        public String voterID;

	public Poll(String constituency, String vid)
	{
		this.constituency=constituency;
                this.voterID=vid;
		boolean valid = Valid.validCons(constituency);
		if(!valid)
		{
			new nt_elg().setVisible(true);
                        return ;
		}
		System.out.println("Valid");
		String candidates=getCandidates();
		//System.out.println(candidates);
		vote(candidates, constituency);
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
					sb.append(++candidateCount);
					sb.append(" ");
					for(int i=1;i<s.length;i++)
					{
						if(i==s.length-2)
							continue;
						sb.append(s[i]+" ");
					}
					sb.append("\n");
				}
			}
                        
             
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return new String(str);
	}

	private boolean vote(String c, String constituency)
	{
		String key="abcd";
		char ch='y';
		Scanner sc=new Scanner(System.in);
		String candidates[]=c.split(" ");
		BufferedReader br,br2;
		String line;
		int vote;
		try{
			br= new BufferedReader(new FileReader("files/voter.txt"));	//votes received
			br2= new BufferedReader(new FileReader("files/polled.txt"));	//voters who already committed
		
			do
			{
				//System.out.println("Enter your Voter Id : ");
				//voterID=sc.nextLine();
				System.out.println(voterID);
                                while((line=br2.readLine())!=null)
				{
					if(line.equals(voterID))
					{
						System.out.println("Already voted");
						return false;
					}
				}
				while((line=br.readLine())!=null)
				{
					String v[]=line.split(" ");
					if(v[0].equals(voterID))
						if(v[v.length-1].equals(constituency))
							break;
						else
						{
							System.out.println("Constituency mismatch");
							return false;
						}
				}
				if(line==null)
				{
					System.out.println("Voter not found");
					return false;
				}
                                Trials t = new Trials(candidateCount,sb);
				System.out.println(" "+constituency+" \n\n\tEnter your vote(1-"+candidateCount+") :\n");
				System.out.println(sb);
				vote=sc.nextInt();
				fileWrite(vote,candidates);
				addVoter(voterID);
				sc.nextLine();
				System.out.println("anymore voters? : ");
				ch=sc.nextLine().charAt(0);
				if(ch=='n')
				{
					System.out.print("Enter password : ");
					if(!(sc.nextLine().equals(key)))
					{
						ch='y';
					}
				}
			}while(ch!='n');
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
	private void fileWrite(int vote,String id[])
	{
		FileWriter fw;
		try{
			fw=new FileWriter("files/poll.txt",true);
			fw.write(id[vote-1]);
			fw.write(" "+constituency+"\n");
			fw.close();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			try{
				fw=new FileWriter("files/poll.txt",true);
				fw.write("Invalid "+constituency+"\n");
				fw.close();
			}
			catch(IOException h)
			{
				h.printStackTrace();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String args[])
		{
                    
		}
}