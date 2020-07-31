package Poll;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author robinhood
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
	private void fileWrite(int vote)
	{
		FileWriter fw;
		try{
			fw=new FileWriter("files/votes.txt",true);
			fw.write(candidates[vote]);
			fw.write(" "+constituency+"\n");
			fw.close();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			try{
				fw=new FileWriter("files/votes.txt",true);
				fw.write("Invalid "+constituency+"\n");
				fw.close();
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
                fileWrite(i);
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