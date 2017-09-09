package my.kalkulator;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.BasicStroke;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.*;
 
public class Kalkulator{
 
    /**
     * @param args
     */
    public static void main(String[] args) 
    { 
        SmpWindow2 wnd = new SmpWindow2();
        wnd.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        wnd.setVisible(true);
        wnd.setBounds( 70, 70, 500, 450);
        wnd.setTitle( "Kalkulator" );
    }
 
}
 
class DrawWndPane2 extends JPanel implements ActionListener
{
	boolean reset = false;
   JButton button1;
   JButton button2;
   JButton button3;
   JButton button4;
   JButton button5;
   JButton button6;
   JButton button7;
   JButton button8;
   JButton button9;
   JButton button0;
   JButton dodawanie;
   JButton odejmowanie;
   JButton mnozenie;
   JButton dzielenie;
   JButton buttonrownania;
   JButton kasowanie;
   JTextArea OknoWynik;
   JLabel label;
   String text;
   String  message;
   JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
   JTextArea jTextArea1 = new javax.swing.JTextArea();
   
   
   DrawWndPane2()
   {
       super();
 
       // Wy³¹czanie automatycznego pozycjonowania komponentów
       setLayout( null );
 
       // Utworzenie komponentów i dodanie ich do panelu okna
	   text = "";
	   label = new JLabel(text);
       OknoWynik = new JTextArea("");
       button1 = new JButton( "1" );
       button2 = new JButton( "2" );
       button3 = new JButton( "3" ); 
       button4 = new JButton( "4" );
       button5 = new JButton( "5" );
       button6 = new JButton( "6" );
       button7 = new JButton( "7" );
       button8 = new JButton( "8" );
       button9 = new JButton( "9" );
       button0 = new JButton( "0" );
       buttonrownania = new JButton("=");
       dodawanie = new JButton("+");
       odejmowanie = new JButton("-");
       mnozenie = new JButton("*");
       dzielenie = new JButton("/");
       kasowanie = new JButton("C");
       button1.setBounds( 100, 100, 70, 30 );
       button2.setBounds( 190, 100, 70, 30 );
       button3.setBounds( 280, 100, 70, 30 ); 
       button4.setBounds( 100, 150, 70, 30 );
       button5.setBounds( 190, 150, 70, 30 );
       button6.setBounds( 280, 150, 70, 30 );
       button7.setBounds( 100, 200, 70, 30 );
       button8.setBounds( 190, 200, 70, 30 );
       button9.setBounds( 280, 200, 70, 30 );
       button0.setBounds( 190, 250, 70, 30 );
       kasowanie.setBounds(100,250,70,30);
       buttonrownania.setBounds(280,250,70,30);
       dodawanie.setBounds(360,100,70,30);
       odejmowanie.setBounds(360,150,70,30);
       mnozenie.setBounds(360,200,70,30);
       dzielenie.setBounds(360,250,70,30);
       OknoWynik.setBounds(170,25,200,50);//x,y, szer, dlugosc
	   label.setBounds(170,300,200,50);
	   //panel scrollowania
	   //-------------------------------------------
	   jScrollPane1.setBounds(170,350,250,50);
	   jTextArea1.setEditable(false);
	   OknoWynik.setEditable(false);
       jTextArea1.setColumns(20);
       jTextArea1.setRows(5);
       jScrollPane1.setViewportView(jTextArea1);
       //------------------------------------------
       add(jScrollPane1);
	   add(label);
       add( button1 );
       add( button2 );
       add( button3 );
       add (button4);
       add (button5);
       add (button6);
       add (button7);
       add (button8);
       add (button9);
       add (button0);
       add (buttonrownania);
       add (dodawanie);
       add (odejmowanie);
       add (mnozenie);
       add (dzielenie);
       add (OknoWynik);
       add (kasowanie);
 
       message = "Nie nacisnieto zadnego przycisku";
 
       // Dodawanie s³uchacza zdarzeñ - tego samego dla wszystkich przycisków
       button1.addActionListener( new ActionListener(){
		   public void actionPerformed(ActionEvent evt){
			  text=OknoWynik.getText();
			  OknoWynik.setText(text);
			  text += 1;
			  OknoWynik.setText(text);	   
			  }
		   
	   } );
       button2.addActionListener( new ActionListener(){
    	   public void actionPerformed(ActionEvent evt){
 			  text=OknoWynik.getText(); 
 			  OknoWynik.setText(text);
    		   text +=2;
    		   OknoWynik.setText(text);
    	   }
       });
       button3.addActionListener( new ActionListener(){
    	   public void actionPerformed(ActionEvent evt){
 			  text=OknoWynik.getText();
 			 OknoWynik.setText(text);
    		   text +=3;
    		   OknoWynik.setText(text);
    	   }
       }
       ); 
       button4.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent evt){
 			  text=OknoWynik.getText();
 			 OknoWynik.setText(text);
    		   text +=4;
    		   OknoWynik.setText(text); 
    	   }
       });
       button5.addActionListener( new ActionListener(){
    	   public void actionPerformed(ActionEvent evt){
 			  text=OknoWynik.getText();
 			 OknoWynik.setText(text);
    		   text +=5;
    		   OknoWynik.setText(text);
    	   }
       });
       button6.addActionListener( new ActionListener(){
    	   public void actionPerformed (ActionEvent evt){
 			  text=OknoWynik.getText();
 			 OknoWynik.setText(text);
    		   text +=6;
    		   OknoWynik.setText(text);
    	   }
       });
       button7.addActionListener( new ActionListener (){
    	   public void actionPerformed(ActionEvent evt){
 			  text=OknoWynik.getText();
 			 OknoWynik.setText(text);
    		   text +=7;
    		   OknoWynik.setText(text);
    	   }
       });
       button8.addActionListener( new ActionListener(){
    	   public void actionPerformed(ActionEvent evt){
 			  text=OknoWynik.getText();
 			 OknoWynik.setText(text);
    		   text +=8;
    		   OknoWynik.setText(text);
    	   }
       });
       button9.addActionListener( new ActionListener(){
    	   public void actionPerformed(ActionEvent evt){
 			  text=OknoWynik.getText();
 			 OknoWynik.setText(text);
    		   text +=9;
    		   OknoWynik.setText(text);
    	   }
       });
       button0.addActionListener( new ActionListener(){
    	   public void actionPerformed(ActionEvent evt){
    		   
    		   if(text.equals("")){ OknoWynik.setText(text);
 			  jTextArea1.setText(text);}
    		   else
    			   {
    			   text=OknoWynik.getText();
    			   OknoWynik.setText(text);
    			   text +=0;
    			   OknoWynik.setText(text);
    			   }
    	   }
       });
       kasowanie.addActionListener(
    		   new ActionListener(){
    			   public void actionPerformed(ActionEvent evt){
    				   text = "";
    	    		   OknoWynik.setText(text);
    	 			  jTextArea1.setText(text);
    			   }
    		   }
    		   );
       dodawanie.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent evt){
    		   if(text.equals("")|| text.charAt(text.length()-1) == ' '){ 
    			   OknoWynik.setText(text);
    			   jTextArea1.setText(text);}
    		   else
    			   {
    			   text=OknoWynik.getText();
    			   OknoWynik.setText(text);
    			   text += " + ";
    			   OknoWynik.setText(text);
    			   }
    	   }
       });
       odejmowanie.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent evt){
    		   if(text.equals("")|| text.charAt(text.length()-1) == ' '){
    			   OknoWynik.setText(text);
    				  jTextArea1.setText(text);
    		   }
    		   else
    			   {
    			   text=OknoWynik.getText();
    			   OknoWynik.setText(text);
    			   text +=" - ";
    			   OknoWynik.setText(text);
    			   }
    	   }
       });
       mnozenie.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent evt){
    		   if(text.equals("")|| text.charAt(text.length()-1) == ' '){
    			   OknoWynik.setText(text);
    				  jTextArea1.setText(text);
    		   }
    		   else
    			   {
    			   text=OknoWynik.getText();
    			   OknoWynik.setText(text);
    			   text +=" * ";
    			   OknoWynik.setText(text);
    			   }
    	   }
       });
       dzielenie.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent evt){
    		   if(text.equals("") || text.charAt(text.length()-1) == ' '  ){
    			   OknoWynik.setText(text);
    				  jTextArea1.setText(text);
    		   }
    		   else
    			   {
    			   text=OknoWynik.getText();
    			   OknoWynik.setText(text);
    			   text +=" / ";
    			   OknoWynik.setText(text);
    			   }
    	   }
       });
       buttonrownania.addActionListener(new ActionListener (){
    	   public void actionPerformed(ActionEvent evt){
    		   text = Oper.cut(text);
    		   jTextArea1.setText(text);     		  
    		   text = "";
    	   }
       });
   }
 
   public void paintComponent( Graphics g)
   {
       super.paintComponent(g);
   }
 
   public void actionPerformed( ActionEvent event)
   {
   } 
}
 
class SmpWindow2 extends JFrame  
{
 
    public SmpWindow2()
    {       
        // Pozyskanie dostepu do powierzchni rysowania okna
        Container  contents = getContentPane();
        contents.add( new DrawWndPane2() );     
    }
 
}
