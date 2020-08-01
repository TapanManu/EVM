package Poll;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Crypto.Decrypt;
import Crypto.Encrypt;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;

/**
 *
 * @author tapan
 */
class Trials extends javax.swing.JFrame 
{       //public class
    
    public String constituency="tkm";
    private int c,size=0;
    private StringBuffer sb;
    private String candidates[];
    ArrayList<javax.swing.JButton> cand;    

    public Trials() {
        //initComponents();
    }
    public Trials(int cc,StringBuffer s){
        /*this();
        this.c=cc;
        this.sb=s;
        System.out.println("\n\n\tEnter your votessss(1-"+c+") :\n");
        System.out.println(sb);*/
    }                   
    private void initComponents(String []sb)
    {
        cand= new ArrayList<javax.swing.JButton>();
        for(String x:sb)
        {
            cand.add(new JButton(x));
        }
        
        //str[10]="NOTA";
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        int i=0;
        for(JButton j:cand)
        {
            j.addActionListener(new java.awt.event.ActionListener() 
            {
                public void actionPerformed(java.awt.event.ActionEvent evt) 
                {
                    candActionPerformed(evt);
                }
            });
            j.setBounds(100,100+i,100,100);
            i+=100;
            add(j);
        }
        setVisible(true);
        setSize(400, 400);
        setLayout(null);
    }      
    public static void shuffle(String filename) throws IOException{
		ArrayList<String> str = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		while((line=br.readLine())!=null){
			str.add(line);
		}
		br.close();
		FileWriter w = new FileWriter(filename);
		Collections.shuffle(str);
		w.write(str.get(0)+"\n");
		w.close();
		FileWriter ow = new FileWriter(filename,true);
		for(int i=1;i<str.size();i++)
			ow.write(str.get(i)+"\n");
		ow.close();
	}
	private void fileWrite(int vote)throws IllegalBlockSizeException, InvalidKeyException, 
                                NoSuchPaddingException, BadPaddingException
	{
		FileWriter fw;
		try{
			fw=new FileWriter("files/votes.txt",true);
			fw.write(candidates[vote]);
			fw.write(" "+constituency+"\n");
			fw.close();
                        shuffle("files/votes.txt");
			new Encrypt("files/votes.txt");
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			try{
				fw=new FileWriter("files/votes.txt",true);
				fw.write("Invalid "+constituency+"\n");
				fw.close();
                                shuffle("files/votes.txt");
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
    
    private void candActionPerformed(java.awt.event.ActionEvent evt) { 
        JButton b = (JButton)evt.getSource();
        //System.out.println("hello"+b.getText());
        for(int i=0;i<cand.size();i++)
        {
            if(b==cand.get(i))
            {
                System.out.println("hello"+b.getText());
                try{
                fileWrite(i);
                }
                catch(IllegalBlockSizeException|InvalidKeyException|NoSuchPaddingException|
                    BadPaddingException e){
                System.out.println("crypto error");
            }
                this.dispose();
                Vote v = new Vote();
                v.setVisible(true);
            }
        }           
        
    }                                      

    public void trial(String constituency,String [] candidates,String sb) {

        try 
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) 
            {
                if ("Nimbus".equals(info.getName())) 
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex) 
        {
            //java.util.logging.Logger.getLogger(Trial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (InstantiationException ex) 
        {
            //java.util.logging.Logger.getLogger(Trial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) 
        {
            //java.util.logging.Logger.getLogger(Trial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            //java.util.logging.Logger.getLogger(Trial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new Trials().setVisible(true);
            }
        });
    this.constituency=constituency;
    //System.out.println(sb);
    sb=sb+"NOTA\n ";
    //System.out.println(sb);
    this.candidates=candidates;
    initComponents(sb.split("\n"));
    }
}