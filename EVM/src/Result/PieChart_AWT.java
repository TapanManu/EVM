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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

class Pie{
    public ArrayList<String> l=new ArrayList<String>();
    public ArrayList<String> r=new ArrayList<String>();
    public ArrayList<String> msg=new ArrayList<String>();
    
    public Pie(long w,int cnt,ArrayList<String> l,ArrayList<String> r,
            ArrayList<String> msg){
        this.l=l;
        this.r=r;
        System.out.println(w+" "+cnt+" "+l+" "+r);
       
        PieChart_AWT.data(w,cnt,l,r,msg);
        
        //PieChart_AWT demo = new PieChart_AWT( "Candidate" );  

    }
}
 
public class PieChart_AWT extends ApplicationFrame {
    public static long w;
    public static int cnt;
    public static ArrayList<String> l=new ArrayList<String>();
    public static ArrayList<String> r=new ArrayList<String>();
    public static ArrayList<String> msg=new ArrayList<String>();
    public static String title = null;
    static PieChart_AWT  demo;
   
   public PieChart_AWT( String title ) {
      super( title ); 
      try{
      setContentPane(createDemoPanel( ));
      }
      catch(NullPointerException  |IllegalComponentStateException e){
          System.out.println("nothing");
      }
   }

   public static void data(long w,int cnt,ArrayList<String> l,ArrayList<String> r,
   ArrayList<String> msg){
       PieChart_AWT.w=w;
       PieChart_AWT.cnt=cnt;
       PieChart_AWT.l=l;
       PieChart_AWT.r=r;
       PieChart_AWT.msg=msg;
       
       demo = new PieChart_AWT( "Candidate" );
       demo.setSize( 560 , 367 );
       
       RefineryUtilities.centerFrameOnScreen( demo );    
       demo.setVisible( true );
       
   }
   
   public static PieDataset createDataset() {
      int max=0;
      DefaultPieDataset dataset = new DefaultPieDataset( );
      JDialog d;
      JLabel lab;
      if(cnt==0){
          title = "Invalid Constituency,No Election done ";
      }
      for(int i=0;i<cnt;i++){
          int value = Integer.parseInt(l.get(i));
          dataset.setValue(r.get(i), new Double(value));
          if(value>max){
              max=value;
              title = r.get(i)+ " won the election by "+w+" votes";
          } 
          if(max==0){
              title = "Single Candidate only";
          }
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
      chart = createChart(createDataset( ));
      return new ChartPanel( chart ); 
   }
   
   public static void alternatePanel(){
       JPanel jp = new JPanel();
       
   }

   public static void hoi() {
      PieChart_AWT demo = new PieChart_AWT( "Candidate" );  
      demo.setSize( 560 , 367 );    
      RefineryUtilities.centerFrameOnScreen( demo ); 
      
      demo.setVisible( true ); 
   }
}