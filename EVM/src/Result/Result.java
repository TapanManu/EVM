package Result;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import Candidate.Candidate;

public class Result {
        public static int cnt=0;
        public static ArrayList<String> l = new ArrayList<String>();
        public static ArrayList<String> t = new ArrayList<String>();
        public static long w=0;
        public static String con="";
        public Result(String c){
            this.con=c;
            System.out.println(con);
            resu();
        }
	public static void declare(ArrayList<String> cons,Candidate[] c,int length) throws IOException{
        long max[]=new long[cons.size()];
		long secmax[] = new long[cons.size()];
        String line;
        int flag=0;

		for(int i=0;i<length;i++){
			for(int j=0;j<cons.size();j++){
			    if (c[i].cons().equals(cons.get(j))){
				    if(c[i].getVotes()>max[j]){
				    	secmax[j]=max[j];
					    max[j]=c[i].getVotes();
					    //System.out.println(c[i].vid());
				    }
				    else if(c[i].getVotes()>secmax[j] && c[i].getVotes()<max[j])
				 	    secmax[j]=c[i].getVotes();
				 }
		                }
		            }
        
		//declaring winner for each constituencies
		try(BufferedReader br = new BufferedReader(new FileReader("files/candidate.txt"))){
		for(int j=0;j<cons.size();j++)
			for(int i=0;i<length;i++)
				if((c[i].getVotes() == max[j]) && (c[i].cons().equals(cons.get(j)))){					
					if(flag==0){
					    while((line=br.readLine())!=null){
					    if(line.split(" ",5)[0].equals(c[i].vid())){
					    System.out.println(c[i].cons()+" "+con);
                                            //System.out.println(con.equals(c[i].cons()));
                                            if(con.equals(c[i].cons())){
                                                Result.w = (max[j]-secmax[j]);
                                            }
					    System.out.println("Winner:"+line.split(" ",5)[1]+" of "+c[i].cons()+" by "+(max[j]-secmax[j])+" votes ");
					    flag=1;
					    break;
					}
					}
					}
				
       }
   }
}
    public static void addVotes(Candidate[] c,int length,ArrayList<String> cons){
    	try{
    		RandomAccessFile b = new RandomAccessFile("files/poll.txt","r");
            String line;
            //int j=0;
            for(int i=0;i<length;i++){
            	b.seek(0);
            while((line=b.readLine())!=null){
            	if((line.split(" ",2)[0].equals(c[i].vid())) &&(line.split(" ",2)[1].equals(c[i].cons())))
            		c[i].incrVotes();
            }
            if(con.equals(c[i].cons())){
               ++cnt;
               l.add(c[i].getVotes()+"");
               t.add(c[i].vid()+"");
            }
            
            System.out.println(c[i].vid()+ " " +c[i].getVotes()+" "+c[i].cons());
            
        }
        declare(cons,c,length);

    	}
    	catch(IOException e){
    		System.out.println("no file output");
    	}
    }

	
	public void resu() {               
		//read from database about unique constituencies
		ArrayList<String> cons = new ArrayList<String>();
        try{
        	RandomAccessFile b = new RandomAccessFile("files/constituency.txt","r");
        	String line;
        	while((line=b.readLine())!=null)
        		cons.add(line.split(" ",3)[1]);
        	b.close();
        	}
        catch(IOException e){
        	System.out.println("error");
        }
        //count lines in candidate file
        int count = 0;
        try{
        	BufferedReader r = new BufferedReader(new FileReader("files/candidate.txt"));
        	String line;
        	while((line=r.readLine())!=null)
        	     count++;
        	r.close(); 
        	}
        catch (IOException e){
        	System.out.println("error processing file");
        }	
        try{
           RandomAccessFile r = new RandomAccessFile("files/candidate.txt","r");
        	Candidate c[] = new Candidate[count];
        	String line;
        	int j=0; 
        	for(int i=0;i<cons.size();i++){
        		r.seek(0);   //reset back to 0 (assumed)
        		//check for file reset pointer and no file output error
        		j=0;
        	    while((line=r.readLine())!=null){
        	    	//loading candidate data
                    String[] list = line.split(" ",5);
                    if(list[list.length-2].equals(cons.get(i))){
                    	//System.out.println(list[0]+" "+list[list.length-2]);
                    	c[j++] = new Candidate(list[0],list[list.length-2],list[list.length-1]);
                    	}
        	    }
                addVotes(c,j,cons); 
        	}

        }
        catch(IOException e){
        	System.out.println("error file");
        }
        //Res r = new Res(w,cnt,l);
        Pie p = new Pie(w,cnt,l,t);
		
		}
	}
