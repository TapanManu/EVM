
package Candidate;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Tapan
 */
public class Test {
    public static boolean partyplace(String[] w,String[] list){
        //diff candidate with same party and cons are not allowed
        return !(w[w.length-3].equals(list[1]) && w[w.length-2].equals(list[2]));
        }
    public static boolean candidateparty(String[] w,String[] list){
        return ((w[w.length-2].equals(list[2]) && w[0].equals(list[0]))||
                !w[0].equals(list[0]));
    }
    public static void main(String[] args) throws IOException{
        try{
        BufferedReader br = new BufferedReader(new FileReader("files/candidate.txt"));
        String line;
        String[] list = {"120","kollam","cpi"};
        while((line=br.readLine())!=null){
            String[] w = line.split(" ",6);
            int valid=0;
            System.out.println(partyplace(w,list) && candidateparty(w,list));
        }
        }
        catch(IOException ie){
            System.out.println("cannot find");
        }
    }
    
}
