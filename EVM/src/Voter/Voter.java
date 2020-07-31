/*
Voter class
*create a file named "counter.txt" with initial counter value(AAA0000000) in the same folder, copy path below in functions setVoterId() and getCounter()
*/

package Voter;
import java.util.GregorianCalendar;
import java.io.*;


public class Voter
{
    private long uid;
    private String voter_id;
    private String name;
    private String constituency;
    private GregorianCalendar dob;
    public Voter(){
       // setVoterId();
    }
    public Voter(long uid){
        this();
        this.uid=uid;
    }
    
    public Voter(long uid , String name ,GregorianCalendar dob,String cons)  //not preferred
    {
        setName(name);
        setDob(dob);
        setUid(uid);
        setCons(cons);
        setVoterId();
    }
    public void setCons(String cons){
        this.constituency = cons;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    
    public void setDob(GregorianCalendar dob)
    {
        this.dob=dob;
    }
    
    public void setUid(long uid)
    {
        this.uid=uid;
    }
    
    private void setVoterId()      
    { 
        int x;
        voter_id=getCounter();
        System.out.println("files/voter id:"+voter_id);
        String s=voter_id;
        char c[]=s.toCharArray();
        int num=0;
        for(int i=3;i<10;i++)
        {
            num=num*10+c[i]-48;
        }
        num=(num+1)%10000000;
        System.out.println(num);
        if(num==0)
        {
            if(c[2]=='Z')       //all voterids are of the form "XXXDDDDDDD": X:Capital letter(A-Z) D:digits(0-9)
            {
                if(c[1]=='Z')
                {
                    if(c[0]=='Z')
                    {
                        System.out.println("out of id patterns");
                    }
                    else
                    {
                        c[0]=(char)(c[0]+1);
                    }
                }
                else
                {
                    c[1]=(char)(c[1]+1);
                }
            }
            else
            {
                c[2]=(char)(c[2]+1);
            }
        }
        for(int i=9;i>2;i--)
        {
            try{
                c[i] = String.valueOf(num).charAt(i+Integer.toString(num).length()-10); 
            }
            catch(StringIndexOutOfBoundsException e)
            {
                //System.out.println("out");
                c[i]='0';
            }
        }
        try
        {
            FileWriter f=new FileWriter("files/counter.txt");      //substitute path of text file here
                f.write(c);
                f.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private String getCounter()
    {
        char c[]=new char[10];
        try
        {
            FileReader f=new FileReader("files/counter.txt");      //substitute path of text file here
            for(int i=0;i<10;i++)
            {
                c[i]=(char)(f.read());
            }
            f.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return String.valueOf(c);
        }
    }
    
    private long getUid()
    {
        return uid;                                              // whether or not uid is to be returned is a question unanswered left for discussion; thus made private
    }
   public String getName()
   {
       return name;
   }
    public GregorianCalendar getDob()
   {
       return dob;
   }
    public String getCons(){
        return constituency;
    }
    public String getVoterId()
   {
       return voter_id;
   }
   /* public static void main(String[] args){
        Voter v1 = new Voter(100,"Tapan Manu",new GregorianCalendar(2000,03,20));
        Voter v2 = new Voter(101,"S J",new GregorianCalendar(2000,02,01));
        System.out.print("v1:"+v1.getVoterId());
        System.out.print("v2:"+v2.getVoterId());
    }*/
}
