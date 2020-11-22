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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			new Error("Invalid constituency!!!",true);
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
                        Class.forName("com.mysql.jdbc.Driver");  
                        Connection con=DriverManager.getConnection(  
                        "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
                          
                        Statement stmt=con.createStatement();  
                        ResultSet rs=stmt.executeQuery("select * from Candidate");  
			String consti,vid,cname,party;
			while(rs.next())
			{
				consti = rs.getString("cons");
                                vid = rs.getString("vid");
                                cname = rs.getString("cand_name");
                                party=rs.getString("pticket");
				//System.out.println(s[s.length-2]);
				if(consti.equals(constituency))
				{
					str.append(vid+" ");
//					sb.append(++candidateCount);
					sb.append(" ");
					sb.append("<"+cname+"("+party+")>");
					
					sb.append("\n");
				}
			}
                        con.close();
		}
		catch(SQLException|ClassNotFoundException e)
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
		
		
                System.out.println(c+" "+constituency+" "+voterID);
		try{
                        Class.forName("com.mysql.jdbc.Driver");  
                        Connection con=DriverManager.getConnection(  
                        "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
                          
                        Statement stmt=con.createStatement();  
                        ResultSet rs=stmt.executeQuery("select * from Polled"); 
                        String ls;
                        while(rs.next())
			{
                                ls = rs.getString("pvid");
				if(ls.equals(voterID))
				{
					new Error("Already voted",true);		//invalid case
					return false;
				}
			}
                        
                        
                        Class.forName("com.mysql.jdbc.Driver");  
                          
                          
                        stmt=con.createStatement();  
                        rs=stmt.executeQuery("select * from Voter"); 
                        String l,s;
                        int found=0;
                        while(rs.next())
			{
                                l = rs.getString("Voter_ID");
                                s = rs.getString("Constituency");
				if(l.equals(voterID))
				{
                                        found=1;
					if(s.equals(constituency))
                                            break;
                                        else{
                                           new Error("Constituency mismatch",true);
						return false; 
                                        }
				}
             
			}
                        if(found==0){
                            new Error("Voter not found",true);			//invalid case
                            return false;
                        }
                  
                    	//votes received
				//voters who already committed
	
//			System.out.println("Enter your Voter Id : ");
//			voterID=sc.nextLine();
			//System.out.println(voterID);
			
                        System.out.println(constituency+" "+candidates);
                        System.out.println(sb);
			new CandList(constituency, candidates, new String(sb));
			addVoter(voterID);
                        con.close();
		}
		catch(SQLException|ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally{
			return true;
		}
	}
	
	private void addVoter(String v)
	{
		
		try{
                        Class.forName("com.mysql.jdbc.Driver");  
                        Connection con=DriverManager.getConnection(  
                        "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
                          
                        Statement stmt=con.createStatement();  
                        String out="Insert into Polled values('"+v+"')";
                        stmt.executeUpdate(out);
			con.close();
		}
		catch(SQLException|ClassNotFoundException e)
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