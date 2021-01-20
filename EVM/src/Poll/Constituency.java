//constituency class
//require a function to edit file entry for a specific constituency
//party_ticket&voter needs to be adjusted so that only valid constituencies are accepted as results

package Poll;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import evm.Error;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Constituency
{
    //private static int no_of_constituency=0;
    private int id;
    private String name;
    private long population;
    
    public Constituency() throws IOException
    {
        this("",0);
    }
    public Constituency(String name) throws IOException
    {
        this(name,0);
    }
    public Constituency(String name , long population) throws IOException
    {
        this.name=name;
        setID();
        this.population=population;
        writeToFile();
    }

    public void setID()
    {
        try{
            File f=new File("files/id.txt");
            BufferedReader br=new BufferedReader(new FileReader(f));
            id=Integer.parseInt(br.readLine());
            //System.out.println(id);
            br.close();
            FileWriter fw=new FileWriter(f);
            fw.write(""+(id+1));
            fw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void writeToFile() throws IOException
    {
        try{
            File f=new File("files/constituency.txt");
            FileWriter fw=new FileWriter(f,true);
            String str=id+" "+name+" "+population+"\n";
            fw.write(str);
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static boolean checkValid(String con,long pop){
       try{
			Class.forName("com.mysql.jdbc.Driver");  
                        Connection conss=DriverManager.getConnection(  
                        "jdbc:mysql://localhost:3306/evm?useSSL=false","tapan","tapan*1234");  
                          
                        Statement stmt=conss.createStatement();  
                        Statement st = conss.createStatement();
                        ResultSet rs=stmt.executeQuery("select * from constituency");  
                        while(rs.next()){  
                            String cons = rs.getString("cname");
                            System.out.println(con + "   " + cons);
                            if(con.equals(cons)){
                                return true;            //valid constituency
                            }
                        } 
                        
                        String in = "insert into constituency values('"+con+"',"+pop+")";
                        st.executeUpdate(in);
                        conss.close();  
                        
		}
		catch(ClassNotFoundException|SQLException s){
			System.out.println("no tables found");
			return false;
		}
		return false;
	}
    
    /*public static void addCons(String args[])
    {
        Scanner sc = new Scanner(System.in);
        String con;
        long pop;
        //System.out.println("get the constituency name");
        con = sc.next();
       // System.out.println("get the current population");
        pop = sc.nextLong();
        Boolean b = checkValid(con.toLowerCase());
        if(b)
             new Constituency(con,pop);
        else
            new Error("existing constituency! Error!!");
    }*/
    public String getName()
    {
        return name;
    }
    public int getID()
    {
        return id;
    }
    public long getPopulation()
    {
        return population;
    }
}

