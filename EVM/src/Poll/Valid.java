package Poll;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Valid{
	public static boolean validCons(String consname) {
		try{
			BufferedReader br = new BufferedReader(new FileReader("files/constituency.txt"));
			String line;
			while((line=br.readLine())!=null){
				String name = line.split(" ",3)[1];
				if(consname.equals(name))
					return true;
			}
			br.close();
		}
		catch(IOException ie){
			System.out.println("no file");
			return false;
		}
		return false;
	}
}