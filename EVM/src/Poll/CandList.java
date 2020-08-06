package Poll;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import evm.Error;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Crypto.Decrypt;
import Crypto.Encrypt;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.swing.JOptionPane;

/**
 *
 * @author tapan
 */
public class CandList  extends javax.swing.JFrame 
{       //public class
    
    public String constituency="tkm";
    private int c,size=0;
    private StringBuffer sb;
    public String [] s;
    private String candidates[];
    public JTextArea textArea;
    public JFrame frame;
    ArrayList<javax.swing.JButton> cand; 
    public static boolean flag=false;
      
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
                                if(flag)
                                    fw.write("NOTA "+constituency+"\n");
                                else
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
                if(b.getText().equals("NOTA")){
                    flag=true;
                }
                try{
                fileWrite(i);
                }
                catch(IllegalBlockSizeException|InvalidKeyException|NoSuchPaddingException|
                    BadPaddingException e){
                new Error("crypto error");
            }
                frame.dispose();
                Vote v = new Vote();
                v.setVisible(true);
            }
        }           
        
    }                                      

    public CandList(String constituency,String [] candidates,String sb) {

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
       
    this.constituency=constituency;
    this.candidates=candidates;
    sb=sb+"NOTA\n";
    s=sb.split("\n");
    //System.out.println(sb);
    
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        JPanel layout = new JPanel(new GridBagLayout());
        layout.setBorder(new EmptyBorder(0, 0, 0, 0));
        JPanel btnPanel = new JPanel(new GridLayout(10, 1, 50, 30));
        JTextArea textArea = new JTextArea();
        JPanel jp=new JPanel();
        cand= new ArrayList<javax.swing.JButton>();
        for(String x:s)
        {
            cand.add(new JButton(x));
        }
        
        //str[10]="NOTA";
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
            btnPanel.add(j);
        }
        layout.add(btnPanel);
        // add text to it; we want to make it scroll
        //textArea.setText("xx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\n");

        // create a scrollpane, givin it the textarea as a constructor argument
        JScrollPane scrollPane = new JScrollPane(layout);

        // now add the scrollpane to the jframe's content pane, specifically
        // placing it in the center of the jframe's borderlayout
        JFrame frame = new JFrame();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // make it easy to close the application
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent w){
                int a = JOptionPane.showConfirmDialog(frame,"Are you sure ?");
                if(a==JOptionPane.YES_OPTION){
                    flag=false;
                    try{
                        fileWrite(cand.size()+1);
                    }
                    catch(IllegalBlockSizeException|InvalidKeyException|NoSuchPaddingException|
                    BadPaddingException e){
                new Error("crypto error");
            }
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
        // set the frame size (you'll usually want to call frame.pack())
        frame.setSize(new Dimension(400, 400));

        // center the frame
        frame.setLocationRelativeTo(null);

        //initComponents(sb.split("\n"),textArea,frame);
        frame.setVisible(true);
        this.frame=frame;
    }
}