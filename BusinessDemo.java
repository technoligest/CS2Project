import javax.swing.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import java.util.*;
import java.io.*;

public class BusinessDemo extends JFrame implements ActionListener{
    
    JFrame mainFrame;
    private ImageIcon logo= new ImageIcon("SSED.jpg");
    private JButton open, create;
    private JLabel image;
    public BusinessDemo()
    {
        mainFrame = new JFrame();
        open = new JButton("Open Business From File");
        create = new JButton ("Create Business");
        image = new JLabel(logo);
        open.addActionListener(this);
        create.addActionListener(this);
        add(image).setBounds(120,10,260,300);
        add(open).setBounds(40,350,200,75);
        add(create).setBounds(280,350,200,75);
        setLayout(null);
        setTitle("Welcome");
        setSize(520,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        
    }
    public static void main(String [] args){
        BusinessDemo besinessD = new BusinessDemo();
    }
    
    public void actionPerformed(ActionEvent event) {
        if (event.getSource()== open)
        {
            String fileName =  JOptionPane.showInputDialog(null, "Input the file name");
            if (fileName!=null){
                try{
                    Scanner inputFile= new Scanner(new File(fileName));
                    StringTokenizer token;
                    String line;
                    //input all the business information
                    String name= inputFile.nextLine();
                    String pass= inputFile.nextLine();
                    String address= inputFile.nextLine();
                    String website= inputFile.nextLine();
                    String date=inputFile.nextLine();
                    String regisNum=inputFile.nextLine();
                    Business b = new Business (name,pass,regisNum,new Date(date));
                    if(!address.equals("Please Input."))
                        b.setAddress(address);
                    if(!website.equals("Please Input."))
                        b.setWebsite(website);

                    int i = 0;
                    //input all the managers in the business
                    line = inputFile.nextLine();
                    while(!line.equalsIgnoreCase("End of managers")){
                        try{
                            //getting the next line
                            
                            //tokenizing the line and looping through the line till no more words
                            token = new StringTokenizer(line, " ");
                            String tempID = token.nextToken();
                            String tempName = token.nextToken();
                            String tempDateBirth = token.nextToken();
                            String tempWage = token.nextToken();
                            String tempDateStarted = token.nextToken();
                            String tempPass = token.nextToken();
                            b.addManager(tempName,new Date(tempDateBirth),new Date(tempDateStarted),tempPass, Double.parseDouble(tempWage));
                            b.getManagerArray().get(i).setID(tempID);
                            line = inputFile.nextLine();
                        }
                        catch(Exception e){}
                    }
                    i = 0;
                    //input all the crew in the business
                    line = inputFile.nextLine();
                    while(!line.equalsIgnoreCase("End of crew")){
                        try{
                            //getting the next/or first line
                            //tokenizing the line and looping through the line till no more words
                            token = new StringTokenizer(line, " ");
                            String tempID = token.nextToken();
                            String tempName = token.nextToken();
                            String tempDateBirth = token.nextToken();
                            String tempWage = token.nextToken();
                            String tempDateStarted = token.nextToken();
                            String tempPass = token.nextToken();
                            b.addCrew(tempName,new Date(tempDateBirth),new Date(tempDateStarted),tempPass, Double.parseDouble(tempWage));
                            b.getCrewArray().get(i).setID(tempID);
                            line = inputFile.nextLine();
                        }
                        catch(Exception e){}
                    }
                    
                    //input all the income to the business.
                    line = inputFile.nextLine();
                    while(!line.equals("End")){
                        try{
                            token = new StringTokenizer(line, "$");
                            String tempInc=token.nextToken();
                            Double tempAmount = Double.parseDouble(token.nextToken());
                            String tempDate = token.nextToken();
                            String tempSigned = token.nextToken();
                            String tempUnknown = token.nextToken();
                            String tempDescription = token.nextToken();
                            if (tempAmount < 0)
                                b.addExpense(new Date(tempDate), tempAmount*(-1), tempUnknown, tempDescription, tempSigned, tempInc);
                            else{
                                b.addIncome(new Date(tempDate), tempAmount, tempUnknown, tempDescription, tempSigned);
                            } 
                        }
                        catch (Exception e){
                            
                        }
                        line=inputFile.nextLine();
                    }

                    //innitialize the app
                    BusinessGUI bus= new BusinessGUI(b);
                    setVisible(false);
                }
                catch(FileNotFoundException e){
                    JOptionPane.showMessageDialog(null,"File not found");
                }
            }
            
        }
        
        if (event.getSource()==create){
            boolean bool= false;
            String name = JOptionPane.showInputDialog(null, "Input the Buisness name: ");
            if (name==null){
                JOptionPane.showMessageDialog(null, "Nothing added");
            }
            else{
                String pass = JOptionPane.showInputDialog(null, "Input the password");
                if(pass==null){
                    JOptionPane.showMessageDialog(null, "Nothing added");
                }
                else{
                    String passCheck = JOptionPane.showInputDialog(null, "Input the password again: ");            
                    if(passCheck==null){
                        JOptionPane.showMessageDialog(null, "Nothing added");
                    }
                    else{
                        while (!pass.equals(passCheck)){
                            pass = JOptionPane.showInputDialog(null, "Passwords don't match. Input a password: ");            
                            if(pass==null)
                                break;
                            passCheck = JOptionPane.showInputDialog(null, "ReInput the new password: ");
                            if(passCheck==null)
                                break;
                        }
                        if (pass==null || passCheck==null){
                            JOptionPane.showMessageDialog(null, "Nothing added");
                        }
                        else{
                            String regisNum = JOptionPane.showInputDialog(null, "Input the registration number");
                            if (regisNum==null){
                                JOptionPane.showMessageDialog(null, "Nothing added");
                            }
                            else{
                                String date = JOptionPane.showInputDialog(null, "Enter Date Started working: DD/MM/YYYY");  
                                if(date==null){
                                    JOptionPane.showMessageDialog(null, "Nothing added");
                                }
                                else{
                                    while(!rightDate(date)){//checking date is in proper format by calling a method
                                        date = JOptionPane.showInputDialog(null, "format is wrong enter right format: DD/MM/YYYY");           
                                        if (date == null){
                                            JOptionPane.showMessageDialog(null, "Nothing added");
                                            break;
                                        }
                                    }
                                        System.out.println(date);
                                    if(date != null || !rightDate(date)){
                                        //date="01/01/2015";
                                        Date d1 =new Date(date);
                                        Business b = new Business (name, pass,regisNum, d1);
                                        BusinessGUI bus= new BusinessGUI(b);
                                        setVisible(false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }   
    }
    
    //check if the date is correct
    public boolean rightDate(String s){
        boolean result = true;
        String temp =s;
        if (s.length()!=10)
            return false;
        else{
            StringTokenizer token;
            String d,m,y;
            int d1,m1,y1;
            try{//tokenizing the string to year month and day format
                token = new StringTokenizer(s, "/");
                d=token.nextToken();
                m=token.nextToken();
                y=token.nextToken();
                d1=Integer.parseInt(d);
                m1=Integer.parseInt(m);
                y1=Integer.parseInt(y);
                if(m1>12 || m1<1 || d1>31 || d1<0 || y1<1 )
                    result = false;
            }
            catch(Exception e){
                result =false;
            }
            s=temp;
            return result;
        }
        
    }
    public boolean exitJOptionDates(String date){
        boolean bool = false;
        if (date == null){
            String quitting = JOptionPane.showInputDialog(null, "Yes to exit");
            if (quitting.equalsIgnoreCase("Yes"))
                bool = true;
        }
        else{
            while(!rightDate(date)){//checking date is in proper format by calling a method
                date = JOptionPane.showInputDialog(null, "format is wrong enter right format: DD/MM/YYYY");           
                if (date == null){
                    String quitting = JOptionPane.showInputDialog(null, "Yes to exit");
                    if (quitting.equalsIgnoreCase("Yes")){
                        bool = true;
                        break;
                    }
                }
            }
        }
        return bool;
    }
    public boolean exitJOptionScreen(String anything){
        String quitting;
        boolean bool =false;
        if (anything == null){
            quitting = JOptionPane.showInputDialog(null, "Yes to exit");
            if (quitting.equalsIgnoreCase("Yes"))
                bool = true;
        }
        return bool;
    }
}
