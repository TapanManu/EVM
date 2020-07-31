package Voter;


import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Tapan
 */
public class Validity {
    public static isDuplicate(String[] w,String[] list){
        return ()
    }
    public static int valid(String[] list) throws IOException{
        try{
        BufferedReader br = new BufferedReader(new FileReader("files/voter.txt"));
        String line;
        int valid=1;
        //name cons dob passed here
        while((line=br.readLine())!=null){
            //System.out.println(line);
            String[] w = line.split(" ",6);
            //System.out.println(w[0]);
            //should check all lines whether new candidate list is valid
            //System.out.println(partyPlace(w,list));
            //System.out.println(candidateParty(w,list));
            if(!((partyPlace(w,list)) && (candidateParty(w,list))))
                valid=0;
        }
        return valid;
        }
        catch(IOException ie){
            System.out.println("cannot find");
        }
        return -1;  //error occurred!
    }
    
}
