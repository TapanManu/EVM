package Result;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robinhood
 */
import java.util.ArrayList;
import javax.swing.JPanel;

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
    
    public Pie(long w,int cnt,ArrayList<String> l,ArrayList<String> r){
        this.l=l;
        this.r=r;
        System.out.println(w+" "+cnt+" "+l+" "+r);
        PieChart_AWT.data(w,cnt,l,r);
        //PieChart_AWT demo = new PieChart_AWT( "Candidate" );  

    }
}
 
public class PieChart_AWT extends ApplicationFrame {
    public static long w;
    public static int cnt;
    public static ArrayList<String> l=new ArrayList<String>();
    public static ArrayList<String> r=new ArrayList<String>();
   
   public PieChart_AWT( String title ) {
      super( title ); 
      setContentPane(createDemoPanel( ));
   }
   public static void data(long w,int cnt,ArrayList<String> l,ArrayList<String> r){
       PieChart_AWT.w=w;
       PieChart_AWT.cnt=cnt;
       PieChart_AWT.l=l;
       PieChart_AWT.r=r;
       PieChart_AWT demo = new PieChart_AWT( "Candidate" );
       demo.setSize( 560 , 367 );    
       RefineryUtilities.centerFrameOnScreen( demo );    
       demo.setVisible( true );
       
   }
   
   public static PieDataset createDataset() {
      DefaultPieDataset dataset = new DefaultPieDataset( );
      for(int i=0;i<cnt;i++){
          dataset.setValue(r.get(i), new Double(Integer.parseInt(l.get(i))));
      }
//      dataset.setValue( "IPhone 5s" , new Double( 20 ) );  
//      dataset.setValue( "SamSung Grand" , new Double( 20 ) );   
//      dataset.setValue( "MotoG" , new Double( 40 ) );    
//      dataset.setValue( "Nokia Lumia" , new Double( 10 ) );  
      return dataset;         
   }
   
   private static JFreeChart createChart( PieDataset dataset ) {
      JFreeChart chart = ChartFactory.createPieChart(      
         "Candidates",   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         false);
      return chart;
   }
   
   public static JPanel createDemoPanel( ) {
      System.out.println(w+" "+cnt+" "+l+" "+r);
      JFreeChart chart = createChart(createDataset( ) );  
      return new ChartPanel( chart ); 
   }

   public static void hoi() {
      PieChart_AWT demo = new PieChart_AWT( "Candidate" );  
      demo.setSize( 560 , 367 );    
      RefineryUtilities.centerFrameOnScreen( demo );    
      demo.setVisible( true ); 
   }
}