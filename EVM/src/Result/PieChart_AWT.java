package Result;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robinhood and Tapan Manu
 */
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JDialog;
import java.awt.IllegalComponentStateException;
import evm.Error;
import evm.admin_panel;
import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.SwingUtilities;

class ResultWindow{
    
    public void createWindow(ArrayList<String>l,ArrayList<String>r){
        
        SwingUtilities.invokeLater(new Runnable(){
           public void run(){
                JFrame frame = new JFrame("Stats");
                

                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(500,500);
                
                frame.setVisible(true);
                JLabel[] ll = new JLabel[l.size()+1];
                JLabel[] rr = new JLabel[r.size()+1];
               for(int i=0;i<l.size()+1;i++){
                    int j = i*100;
                    if(i!=l.size()){
                        rr[i] = new JLabel();
                        ll[i] = new JLabel();
                        frame.add(rr[i]);
                        frame.add(ll[i]);
                        
                        
                        rr[i].setText(r.get(i));
                        ll[i].setText(l.get(i));
                        rr[i].setBounds(100,100+j/2,100,100);
                        ll[i].setBounds(250,100+j/2,100,100);
                    }
                    else{
                        ll[i] = new JLabel();
                        rr[i] =  new JLabel();
                        ll[i].setBounds(250,450,50,50);
                        frame.add(ll[i]);
                    }
                    
                }
                
           }
        });
    }
    
    
    
}


class Pie{
    public ArrayList<String> l=new ArrayList<String>();
    public ArrayList<String> r=new ArrayList<String>();
    public ArrayList<Integer> in = new ArrayList<Integer>();
    public boolean total = false;
    public Pie(long w,int cnt,ArrayList<String> l,ArrayList<String> r,
            boolean total){
        this.l=l;
        this.r=r;
        this.total=total;
        System.out.println(w+" "+cnt+" "+l+" "+r);
       
        PieChart_AWT.data(w,cnt,l,r,total);
        
        //PieChart_AWT demo = new PieChart_AWT( "Candidate" ); 
    }
    public Pie(ArrayList<String> l,ArrayList<String> r,boolean total){
        this.l=l;
        this.r=r;
        this.total = total;
        PieChart_AWT.data(l,r,total);
    }
    public Pie(ArrayList<Integer> in,boolean total,ArrayList<String> r){
        this.in=in;
        this.r=r;
        this.total = total;
        PieChart_AWT.data(in,total,r);
    }
}
 
public class PieChart_AWT extends JFrame{
    public static long w;
    public static int cnt;
    public static ArrayList<String> l=new ArrayList<String>();
    public static ArrayList<String> r=new ArrayList<String>();
    public static ArrayList<Integer> in = new ArrayList<Integer>();
    
    public static String title = null;
    public static boolean flag=false;
    public static boolean msgflag=false;
    public static boolean dispflag=false;
    public static boolean total = false;
    public static boolean pollflag = false;
    
   public PieChart_AWT( String title ) {
      super( title ); 
      try{
      setContentPane(createDemoPanel( ));
      
      if(dispflag){
          this.dispose();
      }
      }
      catch(NullPointerException  |IllegalComponentStateException e){
          System.out.println("nothing");
      }
   }

   public static void data(long w,int cnt,ArrayList<String> l,
           ArrayList<String> r,boolean total){
       PieChart_AWT.w=w;
       PieChart_AWT.cnt=cnt;
       PieChart_AWT.l=l;
       PieChart_AWT.r=r;
       PieChart_AWT.total = total;
       flag=false;
       dispflag=false;
       pollflag=false;
       PieChart_AWT  demo;

       demo = new PieChart_AWT( "Candidate" );
       demo.setSize( 560 , 367 );
       
       RefineryUtilities.centerFrameOnScreen( demo );
       demo.setVisible(!msgflag && !total);
       demo.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
       /*demo.addWindowListener(new WindowAdapter(){
           public void windowClosing(WindowEvent w){
               admin_panel a = new admin_panel();
               a.setVisible(true);
               demo.dispose();
               dispflag=true;
           }
       });*/
   }
   public static void data(ArrayList<Integer> in,boolean total,
           ArrayList<String> r){
       PieChart_AWT.cnt=r.size();
       PieChart_AWT.in=in;
       PieChart_AWT.r=r;
       PieChart_AWT.total = total;
       flag=true;
       dispflag=false;
       pollflag=true;
       
       PieChart_AWT  polldemo;
       polldemo = new PieChart_AWT( "Constituency wise Polled Stats" );
       polldemo.setSize( 560 , 367 );
       
       RefineryUtilities.centerFrameOnScreen( polldemo );    
       polldemo.setVisible( !msgflag && flag );
       polldemo.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      /* seatdemo.addWindowListener(new WindowAdapter(){
           public void windowClosing(WindowEvent w){
               admin_panel a = new admin_panel();
               a.setVisible(true);
               seatdemo.dispose();
               dispflag=true;
           }
       });*/
       
   }
    public static void data(ArrayList<String> l,
           ArrayList<String> r,boolean total){
       PieChart_AWT.cnt=r.size();
       PieChart_AWT.l=l;
       PieChart_AWT.r=r;
       PieChart_AWT.total = total;
       flag=true;
       dispflag=false;
       pollflag=false;
       //new ResultWindow().createWindow(l, r);
       PieChart_AWT  seatdemo;
       seatdemo = new PieChart_AWT( "Total Seats" );
       seatdemo.setSize( 560 , 367 );
       
       RefineryUtilities.centerFrameOnScreen( seatdemo );    
       seatdemo.setVisible( !msgflag && flag );
       seatdemo.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
       
       new ResultWindow().createWindow(l,r);
      /* seatdemo.addWindowListener(new WindowAdapter(){
           public void windowClosing(WindowEvent w){
               admin_panel a = new admin_panel();
               a.setVisible(true);
               seatdemo.dispose();
               dispflag=true;
           }
       });*/
       
   }
   
   public static PieDataset createDataset() {
    msgflag=false;
      int max=0;
      DefaultPieDataset dataset = new DefaultPieDataset( );
      JDialog d;
      JLabel lab;
      if(pollflag){
          for(int i=0;i<cnt;i++){
              dataset.setValue(r.get(i),new Double(in.get(i)));
          }          
          title="polling stats";
          
      }
      else{
      if(flag){
          title="Total seats";
      }
      if(!flag && (l.size()==1 || r.size()==1) && !total){
          title = "Invalid Constituency,No Election done ";
          msgflag=true;
      }  
      if(l.size()>1 && r.size()>1){
      for(int i=0;i<cnt;i++){
          int value = Integer.parseInt(l.get(i));
          dataset.setValue(r.get(i), new Double(value));
          if(value>max && !flag  && !r.get(i).equals("NOTA") && !total){
              max=value;
              
              title = r.get(i)+ " won the election by "+w+" votes";
              msgflag=false;
              
              
          } 
          if(r.size()==2 && !flag && !r.get(i).equals("NOTA")&& !total){
              title="Single Candidate "+ r.get(i)+ " only";
              msgflag=true;
          }
          if(max==0 && !flag && r.size()!=2 && !total){
              title = "No Election done so far!";
              msgflag=true;
          }
          
      }
      if(!flag)
         if(!total)
            new ResultWindow().createWindow(l,r);
      }

      
      if(msgflag)
           Error.display(title);
      }

      return dataset;           
   }
   
   private static JFreeChart createChart( PieDataset dataset ) {
      JFreeChart chart = ChartFactory.createPieChart(      
         title,   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         false);
      return chart;
   }
   
   public static JPanel createDemoPanel( ) {
      System.out.println(w+" "+cnt+" "+l+" "+r);
      JFreeChart chart;
      PieDataset p = createDataset();
      if(!msgflag){
           chart = createChart(p);
           
              
      }
      else
           chart = null;
      return new ChartPanel( chart ); 
   }
   
   
   
   /*public static void hoi() {
      PieChart_AWT demo = new PieChart_AWT( "Candidate" );  
      demo.setSize( 560 , 367 );    
      RefineryUtilities.centerFrameOnScreen( demo ); 
      
      demo.setVisible( true ); 
   }*/
}

