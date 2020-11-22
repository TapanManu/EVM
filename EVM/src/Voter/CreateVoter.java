/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Voter;

import Poll.Valid;
import Crypto.Encrypt;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.FileWriter;
import evm.Error;
//import Voter.Voter ;   //compile in parent directory of voter i.e in src otherwise error

//voter validity and removal of duplicates can be done only with address field provided!!
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import javax.crypto.IllegalBlockSizeException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;

class VoteEligibile extends Exception{
	public VoteEligibile(String s){
		super(s);
	}
}

class NewVote{
        public static String s,c,dob;
        public static long uid;
        public NewVote(String s,int id,String dob,String con) throws IOException{
            this.s=s;
            this.uid=id;
            this.dob=dob;
            this.c=con;
            try{
            vote();
            }
            catch(IllegalBlockSizeException|InvalidKeyException|
                    NoSuchPaddingException|BadPaddingException f){
                System.out.println("crypto error");
            }
        }
	public static void getAgeDiff(GregorianCalendar a,GregorianCalendar b) throws VoteEligibile{
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
            if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
                   (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
                                    diff--;
                                }
        if(diff<18)
        	throw new VoteEligibile("under 18 years of age !\nnot eligible for application");
	}

	public static void vote() throws IOException,IllegalBlockSizeException,InvalidKeyException,
										 NoSuchPaddingException,BadPaddingException{
		try{
			Class.forName("com.mysql.jdbc.Driver");  
                        Connection con=DriverManager.getConnection(  
                        "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
                        Statement stmt=con.createStatement();  
			int count=1;
			GregorianCalendar g ;
			while(count<=1){
				//data should be read from aadhar database or enter manually
				String[] date=dob.split("/",3);
				try{
                                    //System.out.println("hello");
				g = new GregorianCalendar(Integer.parseInt(date[0]),Integer.parseInt(date[1])-1,Integer.parseInt(date[2]));
                                getAgeDiff(g,new GregorianCalendar());
                                }
                               catch(VoteEligibile e){
                                    new Error(e.getMessage());
                                    count++;
                                    continue;
                                }
                if(Valid.validCons(c)){
				Voter v = new Voter(uid,s,g,c);
				//String output = v.getVoterId()+ " " + s + " " + dob +" "+c;
                                String out="Insert into Voter values('"+v.getVoterId()
                                        +"','"+s+"','"+dob+"','"+c.toLowerCase()+"')";
                                stmt.executeUpdate(out);
				//out.write(output);
				//out.write("\n");
                                Error.display("Successfully added");
			    }
			    else{
			    	new Error("unidentified constituency");
			    }
				++count;
			}
		}
		catch(ClassNotFoundException|SQLException e){
			new Error("The system has detected some failure!");
		}
                //encrypt the database instead of files
            new Encrypt("files/voter.txt");
	}
}