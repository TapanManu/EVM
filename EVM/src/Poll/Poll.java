package Poll;

import evm.Error;
import evm.nt_elg;
//import evm.Gui.val_cons;
//import evm.Gui.voted;
//import evm.Gui.layout;
import Poll.Votes;

import java.util.ArrayList;
import java.util.Collections;

import java.io.BufferedReader;
import java.util.Scanner;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;

import Crypto.Decrypt;
import Crypto.Encrypt;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;

/**
/**
 * Poll
 */
public class Poll {

	public String constituency;
        public String a,b,c;
	private StringBuffer sb=new StringBuffer("");
        private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAUZV+tjiNBKhlBZbKBnzeugpdYPhh5PbHanjV0aQ+LF7vetPYhbTiCVqA3a+Chmge44+prlqd3qQCYra6OYIe7oPVq4mETa1c/7IuSlKJgxC5wMqYKxYydb1eULkrs5IvvtNddx+9O/JlyM5sTPosgFHOzr4WqkVtQ71IkR+HrAgMBAAECgYAkQLo8kteP0GAyXAcmCAkA2Tql/8wASuTX9ITD4lsws/VqDKO64hMUKyBnJGX/91kkypCDNF5oCsdxZSJgV8owViYWZPnbvEcNqLtqgs7nj1UHuX9S5yYIPGN/mHL6OJJ7sosOd6rqdpg6JRRkAKUV+tmN/7Gh0+GFXM+ug6mgwQJBAO9/+CWpCAVoGxCA+YsTMb82fTOmGYMkZOAfQsvIV2v6DC8eJrSa+c0yCOTa3tirlCkhBfB08f8U2iEPS+Gu3bECQQCrG7O0gYmFL2RX1O+37ovyyHTbst4s4xbLW4jLzbSoimL235lCdIC+fllEEP96wPAiqo6dzmdH8KsGmVozsVRbAkB0ME8AZjp/9Pt8TDXD5LHzo8mlruUdnCBcIo5TMoRG2+3hRe1dHPonNCjgbdZCoyqjsWOiPfnQ2Brigvs7J4xhAkBGRiZUKC92x7QKbqXVgN9xYuq7oIanIM0nz/wq190uq0dh5Qtow7hshC/dSK3kmIEHe8z++tpoLWvQVgM538apAkBoSNfaTkDZhFavuiVl6L8cWCoDcJBItip8wKQhXwHp0O3HLg10OEd14M58ooNfpgt+8D8/8/2OOFaR0HzA+2Dm";
	
        
        public Poll(String cand,String con,String id,StringBuffer s){
            System.out.println(cand+" "+con+" "+id);
            this.sb=s;
            try{
            vote(cand,con,id);
            }
            catch(IllegalBlockSizeException|InvalidKeyException|NoSuchPaddingException|
                    BadPaddingException e){
                new Error("crypto error");
            }
        }
	
	public Poll(String constituency)
	{
		this.constituency=constituency;
		boolean valid = Valid.validCons(constituency);
		if(!valid)
		{
			new Error("Invalid constituency!!!");
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
	public boolean vote(String c, String constituency, String voterID)throws IllegalBlockSizeException, InvalidKeyException, 
                                NoSuchPaddingException, BadPaddingException		//call this function to vote in an ongoing poll
	{
                
		String candidates[]=c.split(" ");
		BufferedReader br,br2;
		String line;
                System.out.println(c+" "+constituency+" "+voterID);
		try{
                        new Decrypt("files/voter.encrypted",privateKey);
			br= new BufferedReader(new FileReader("files/voter.decrypted"));	//votes received
			br2= new BufferedReader(new FileReader("files/polled.txt"));	//voters who already committed
	
//			System.out.println("Enter your Voter Id : ");
//			voterID=sc.nextLine();
			//System.out.println(voterID);
			while((line=br2.readLine())!=null)
			{
				if(line.equals(voterID))
				{
					new Error("Already voted");		//invalid case
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
						new Error("Constituency mismatch");
						return false;
					}
			}
			if(line==null)
			{
				new Error("Voter not found");			//invalid case
				return false;
			}
                        System.out.println(constituency+" "+candidates);
                        System.out.println(sb);
			new CandList(constituency, candidates, new String(sb));
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