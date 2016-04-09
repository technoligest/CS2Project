import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.*;
public class BusinessGUI extends JFrame implements ActionListener
{    //creating vars and objects
    private Business b;
    int Employee = -1;//0 for owner, 1 for manager, 2for crew
    static String tempID = "";
    String tempPassword= "";      
    //GUI attributes
    private JFrame ownerFrame, managerFrame, crewFrame,editEmployeeFrame,IncExpPanel,businessFrame;
    private JButton AddRemoveIncExp, AddRemoveWorker, reports, viewBusiness,signOut,editBusiness, addCrew,removeCrew,viewReport,addMang,removeMang;
    private JButton save,saveAs,change, change1, change2, change3,signIn, Next, addInc, addExp, removeInc, removeExp, yearly, monthly,daily,changePass;
    private JLabel enterID, enterPass, welcome, image,error; 
    private JTextField inputID, inputPass;
    private ImageIcon logo= new ImageIcon("SSED.jpg");
    private ImageIcon logoSmall= new ImageIcon("SSED - Copy.jpg");
    private ImageIcon logoMed= new ImageIcon("SSED-Med.jpg");
    public BusinessGUI(Business b){
        //resetting values
        this.b = b;
        Employee = -1;
        tempID = "";
        tempPassword = "";
        //creating all the objects      
        inputID = new JTextField(10);
        inputID.setFont(new Font("Serif", Font.BOLD, 15));
        inputPass = new JTextField(10);
        inputPass.setFont(new Font("Serif", Font.BOLD, 15));
        signIn = new JButton("Sign in");
        signIn.setFont(new Font(Font.SANS_SERIF, Font.BOLD,15));
        enterID = new JLabel(" Enter ID");
        enterID.setFont(new Font("Serif", Font.BOLD, 18));
        enterPass =new JLabel ("Enter Password:");
        enterPass.setFont(new Font("Serif", Font.BOLD, 18));
        error = new JLabel("Don't Match");
        error.setFont(new Font("Serif", Font.BOLD, 18));
        welcome = new JLabel("Welcome!");
        welcome.setFont(new Font("Serif", Font.BOLD, 30));
        image = new JLabel(logo);        
        setLayout(null);//sets layout of the frame to null
        //adding action listener to the objects that need it
        signIn.addActionListener(this);
        //adding the objects to the panel in proper place   
        add(welcome).setBounds(250,10,300,60);
        add(image).setBounds(100,75,400,250);;
        add(enterID).setBounds(100,360,200,50);
        add(enterPass).setBounds(55,420,200,50);
        add(error).setBounds(200,480,200,50);
        add(inputID).setBounds(210,360,200,50);
        add(inputPass).setBounds(210,420,200,50);
        add(signIn).setBounds(420,390,100,50);
        //hiding few objects
        enterPass.setVisible(true);
        inputPass.setVisible(true);
        error.setVisible(false);
        //creating the frame
        setTitle("Business");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
       //setting the main panels of the project to false for visibility
        managerFrame = new JFrame();
        crewFrame = new JFrame();
        ownerFrame = new JFrame();
        managerFrame.setVisible(false);
        crewFrame.setVisible(false);
        ownerFrame.setVisible(false);
    }
    public void actionPerformed(ActionEvent event){
        //display the main frame and check for user match ID when signin button is pressed
        if (event.getSource() == signIn){
            tempID = inputID.getText();//get the id from the textfield
            tempPassword = inputPass.getText();//get password from input textfield
                //checks if id stard with M --> user is manager
            if (!tempID.equals("")){
                if (tempID.charAt(0)=='M'){
                    if (b.getManager(tempID) != null){
                        if (b.getManager(tempID).getPassword().equalsIgnoreCase(tempPassword)){
                            Employee = 1;
                            runManagerScreen(b.getManager(tempID));
                        }
                        else
                            error.setVisible(true);
                        
                    }
                    error.setVisible(true);
                }
                //checks if user is employee and set variable to 2
                else if (tempID.charAt(0)=='C'){
                    if (b.getCrew(tempID) != null){
                        if (b.getCrew(tempID).getPassword().equalsIgnoreCase(tempPassword)){
                            Employee = 2;
                            runCrewScreen(b.getCrew(tempID));
                        }
                        else
                            error.setVisible(true);
                    }
                    else
                        error.setVisible(true);
                }
                //checks for owner's id, since user is not manager or crew
                else if (b.getName().equalsIgnoreCase(tempID)){
                    if (b.getPassword().equalsIgnoreCase(tempPassword)){
                        Employee=0;
                        runOwnerScreen();
                    }
                    else
                        error.setVisible(true);
                }
                else
                    error.setVisible(true);
            }
            else
                error.setVisible(true);
        }
        
        //if sign out button is pressed in any frame, repaint main frame and close everything else
        if (event.getSource()==signOut){
            if (managerFrame.isVisible()) managerFrame.setVisible(false);
            if (crewFrame.isVisible()) crewFrame.setVisible(false);
            if (ownerFrame.isVisible()) ownerFrame.setVisible(false);
            //b = new Business("SmatFlow","1234","123456",new Date(01,01,1970));
            BusinessGUI bus = new BusinessGUI(b);
        }
        
        if (event.getSource()==save){
            try{
                b.save(b.getName());
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error occured while saving, please try again.");
            }
            
        }
        if (event.getSource()==saveAs){
            String fname = JOptionPane.showInputDialog(null, "Input file name to save to");
            if (fname==null){
                JOptionPane.showMessageDialog(null, "Not saved.");
            }
            else{
                try{
                    b.save(fname);
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Error occured while saving, please try again.");
                }
            }
            
        }
        //if button is pressed call specific method
        if (event.getSource() == AddRemoveWorker){
            editEmployees();
        }
        //display workers report when button is pressed from editEmployee frame
        if (event.getSource()==viewReport){
            editEmployeeFrame.setVisible(false);
            b.getTable();
            //JOptionPane.showMessageDialog(null, new JTextArea(b.displayCrewReport()));
        }
        //add new crew when button is pressed from editEmployee frame
        if (event.getSource()==addCrew){
            //inputting the information of the user and adding crew to list
            boolean bool=true;
            String name = JOptionPane.showInputDialog(null, "Input the crew's name");
            if(name==null){
                JOptionPane.showMessageDialog(null, "Nothing added");
            }
            else{
                String pass = JOptionPane.showInputDialog(null, "Input the crew's password");
                if(pass==null){
                    JOptionPane.showMessageDialog(null, "Nothing added");
                }
                else{
                    String passCheck = JOptionPane.showInputDialog(null, "ReInput the crew's password");
                    if(passCheck==null){
                        JOptionPane.showMessageDialog(null, "Nothing added");
                    }
                    else{
                        while(!pass.equals(passCheck)){//checking that password is okay
                            pass = JOptionPane.showInputDialog(null, "Passwords dont match, Input the password");
                            if(pass==null){
                                break;
                            }
                            passCheck = JOptionPane.showInputDialog(null, "ReInput the crew's password");
                            if(passCheck==null)
                                break;
                        }
                        if(pass==null || passCheck==null){
                            JOptionPane.showMessageDialog(null, "Nothing added");
                        }
                        else{
                        
                            String date = JOptionPane.showInputDialog(null, "Input the crew's birthday as following: DD/MM/YYYY");            
                            
                            if (date == null){
                                JOptionPane.showMessageDialog(null, "Nothing added");
                            }
                            else{
                                while(!rightDate(date)){//checking date is in proper format by calling a method
                                    date = JOptionPane.showInputDialog(null, "Format is wrong enter right format: DD/MM/YYYY");           
                                    if (date == null){
                                        JOptionPane.showMessageDialog(null, "Nothing added");
                                        break;
                                    }
                                }
                                if(date==null){
                                    JOptionPane.showMessageDialog(null, "Nothing added");
                                }
                                else{
                                    String dateStarted = JOptionPane.showInputDialog(null, "Input the date "+name+" started working as following: DD/MM/YYYY");            
                                   
                                    if (dateStarted == null){
                                        JOptionPane.showMessageDialog(null, "Nothing added");
                                    }
                                    else{
                                        while(!rightDate(dateStarted)){//checking date is in proper format by calling a method
                                            dateStarted = JOptionPane.showInputDialog(null, "Format is wrong enter right format: DD/MM/YYYY");           
                                            if (dateStarted == null){
                                                JOptionPane.showMessageDialog(null, "Nothing added");
                                                break;

                                            }
                                        }
                                        if(dateStarted==null){
                                            JOptionPane.showMessageDialog(null, "Nothing added");
                                        }
                                        else{
                                            String amount = JOptionPane.showInputDialog(null, "Input hourly paid rate");  
                                            if(amount==null)
                                                    JOptionPane.showMessageDialog(null, "Nothing added");
                                            else{
                                                while(!rightAmount(amount)){
                                                    if(amount==null)
                                                        JOptionPane.showMessageDialog(null, "Nothing added");
                                                    else
                                                        amount = JOptionPane.showInputDialog(null, "Number not formated properly, reInput hourly paid rate"); 
                                                }
                                                b.addCrew(name, new Date(dateStarted), new Date(date) , pass, Double.parseDouble(amount));
                                                JOptionPane.showMessageDialog(null, (name +" - "+ b.getCrewArray().get(b.getCrewArray().size()-1).getID()+" is added."));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        //remove existed crew by a given id when button is pressed from editEmployee frame
        if (event.getSource()==removeCrew){
            String id = JOptionPane.showInputDialog(null, "Input the crew's id to remove");
            if(id==null){
                JOptionPane.showMessageDialog(null, "Nothing removed");
            }
            else{
                boolean found = false; 
                ArrayList<Crew> c = b.getCrewArray();
                while (!found){
                    for (int i =0; i<c.size();i++){
                        if (c.get(i).getID().equalsIgnoreCase(id)){
                            String tempName = b.getCrew(id).getName();
                            b.getCrewArray().remove(i);
                            JOptionPane.showMessageDialog(null, (tempName +" - "+ id +" is removed."));
                            found = true;
                            break;
                        }
                    }
                    if (found ==false){
                        id = JOptionPane.showInputDialog(null, "Crew not found, Input the correct id to remove");
                        if (id==null){
                            JOptionPane.showMessageDialog(null, "Nothing removed");
                            found=true;
                        }
                    }
                    
                }   
            }
            
        }
        
        //add new manager when button is pressed from editEmployee frame
        if (event.getSource()==addMang){
            //inputting manager information, and checking for error in inputting data
            String name = JOptionPane.showInputDialog(null, "Input the manager's name");
            if (name==null){
                JOptionPane.showMessageDialog(null, "Nothing added");
            }
            else{
                String pass = JOptionPane.showInputDialog(null, "Input the manager's password");
                if(pass==null){
                    JOptionPane.showMessageDialog(null, "Nothing added");
                }
                else{
                    String passCheck = JOptionPane.showInputDialog(null, "ReInput the manager's password");
                    if(passCheck==null){
                        JOptionPane.showMessageDialog(null, "Nothing added");
                    }
                    else{
                        while(!pass.equals(passCheck)){//checking that password is okay
                            pass = JOptionPane.showInputDialog(null, "Passwords dont match, Input the password");
                            if(pass==null)
                                break;
                            passCheck = JOptionPane.showInputDialog(null, "ReInput the manager's password");
                            if(passCheck==null)
                                break;
                        }
                        if(pass==null || passCheck==null){
                            JOptionPane.showMessageDialog(null, "Nothing added");
                        }
                        else{
                            String date = JOptionPane.showInputDialog(null, "Input the manager's birthday as following: DD/MM/YYYY");            
                            
                            if (date == null){
                                JOptionPane.showMessageDialog(null, "Nothing added");
                            }
                            else{
                                while(!rightDate(date)){//checking date is in proper format by calling a method
                                    date = JOptionPane.showInputDialog(null, "Format is wrong enter right format: DD/MM/YYYY");           
                                    if (date == null){
                                        JOptionPane.showMessageDialog(null, "Nothing added");
                                        break;
                                    }
                                }
                                if (date==null){
                                   JOptionPane.showMessageDialog(null, "Nothing added"); 
                                }
                                else{
                                String dateStarted = JOptionPane.showInputDialog(null, "Input the date "+name+" started working as following: DD/MM/YYYY");            
                                
                                if (dateStarted == null){
                                    JOptionPane.showMessageDialog(null, "Nothing added");
                                }
                                else{
                                    while(!rightDate(dateStarted)){//checking date is in proper format by calling a method
                                        dateStarted = JOptionPane.showInputDialog(null, "Format is wrong enter right format: DD/MM/YYYY");           
                                        if (dateStarted == null){
                                            JOptionPane.showMessageDialog(null, "Nothing added");
                                            break;
                                        }
                                    }
                                    if(dateStarted==null){
                                        JOptionPane.showMessageDialog(null, "Nothing added");
                                    }
                                    else{
                                        String amount = JOptionPane.showInputDialog(null, "Input Wage");  
                                        if(amount==null){
                                            JOptionPane.showMessageDialog(null, "Nothing added");
                                        }
                                        else{
                                            while(!rightAmount(amount)){
                                                amount = JOptionPane.showInputDialog(null, "Number not formated properly, reInput hourly paid rate"); 
                                                if(amount==null){
                                                    JOptionPane.showMessageDialog(null, "Nothing added");
                                                    break;
                                                }
                                            }
                                            if(amount!= null){
                                                b.addManager(name, new Date(dateStarted), new Date(date) , pass, Double.parseDouble(amount));
                                                JOptionPane.showMessageDialog(null, (name +" - "+ b.getManagerArray().get(b.getManagerArray().size()-1).getID()+" is added."));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        }
          
        //remove manager by given id when button is pressed from editEmployee frame
        if (event.getSource()==removeMang){
            String id = JOptionPane.showInputDialog(null, "Input the crew's id to remove");
            if(id!=null){
                boolean found=false;
                ArrayList<Manager> m = b.getManagerArray();
                while (!found){
                    for (int i =0; i<m.size();i++){
                        if (m.get(i).getID().equalsIgnoreCase(id)){
                            String tempName = b.getManager(id).getName();
                            b.getManagerArray().remove(i);
                            JOptionPane.showMessageDialog(null,(tempName+" - "+ id +" is removed."));
                            found = true;
                            break;
                        }
                    }
                    if (!found){
                        id = JOptionPane.showInputDialog(null, "Manager not found, Input the correct id to remove");
                        if (id==null){
                            JOptionPane.showMessageDialog(null, "Nothing added");
                            break;
                        }
                    }
                    
                }   
            }
        }
        //display the business information when get busInfo button is pressed from the Manager or Crew screen
        if (event.getSource()==viewBusiness){
            //create frame with specific attributes
            JFrame businessFrame = new JFrame();
            JLabel imagelogo = new JLabel(logoMed);
            businessFrame.setLayout(null);
            //adding objects after creating them to the frame in specific place
            businessFrame.add(imagelogo).setBounds(155,10,200,200);
            if (b.getRegisNum()==null)
                businessFrame.add(new JLabel("Registration Number: Please ask manager to input the number")).setBounds(100,240,380,40);
            else
                businessFrame.add(new JLabel("Registration Number: "+b.getRegisNum())).setBounds(100,240,200,40);
            businessFrame.add(new JLabel("Address: "+b.getAddress())).setBounds(100,320,300,40);
            businessFrame.add(new JLabel("Website: "+b.getWebsite())).setBounds(100,400,200,40);
            businessFrame.add(new JLabel("Number of Employees: "+(b.getNumOfCrew()+b.getNumOfManagers()))).setBounds(100,480,200,40);
            businessFrame.add(new JLabel("Date Started: "+b.getDateStarted())).setBounds(100,560,200,40);
            businessFrame.add(new JLabel("Close to exit. ")).setBounds(320,610,200,40);
            //set frame info and properties
            businessFrame.setTitle("Business Info(M/C)");
            businessFrame.setSize(500,700);
            businessFrame.setLocationRelativeTo(null);
            businessFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            businessFrame.setVisible(true);
            businessFrame.setResizable(false);
        }
        //display business info when button is pressed from owber screen
        if (event.getSource()==editBusiness){
            //create new frame with specific attributes
            businessFrame = new JFrame();
            JLabel imagelogo = new JLabel(logoMed);
            businessFrame.setLayout(null);
            change = new JButton("Change Name");
            change1 = new JButton("Change Reg #");
            change2 = new JButton("Change Address");
            change3 = new JButton("Change Website");
            change.addActionListener(this);
            change1.addActionListener(this);
            change2.addActionListener(this);
            change3.addActionListener(this);
            //addigng attributes to proper places in the frame
            businessFrame.add(imagelogo).setBounds(275,10,200,200);
            businessFrame.add(new JLabel("Business Name: "+b.getName())).setBounds(100,240,200,40);
            businessFrame.add(change).setBounds(490,250,200,20);
            if (b.getRegisNum()==null)
                businessFrame.add(new JLabel("Registration Number: Please ask manager to input the number")).setBounds(100,290,380,40);
            else
                businessFrame.add(new JLabel("Registration Number: "+b.getRegisNum())).setBounds(100,290,200,40);
            businessFrame.add(change1).setBounds(490,300,200,20);
            businessFrame.add(new JLabel("Address: "+b.getAddress())).setBounds(100,340,300,40);
            businessFrame.add(change2).setBounds(490,350,200,20);
            businessFrame.add(new JLabel("Website: "+b.getWebsite())).setBounds(100,390,200,40);
            businessFrame.add(change3).setBounds(490,400,200,20);
            businessFrame.add(new JLabel("Number of Employees: "+(b.getNumOfCrew()+b.getNumOfManagers()))).setBounds(100,440,200,40);
            businessFrame.add(new JLabel("Date Started: "+b.getDateStarted())).setBounds(100,490,200,40);
            businessFrame.add(new JLabel("Close to exit. ")).setBounds(320,580,200,40);
            
            //setting frame info and properties
            businessFrame.setTitle("Business Info(O)");
            businessFrame.setSize(750,700);
            businessFrame.setLocationRelativeTo(null);
            businessFrame.setLocation(300, 10);
            businessFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            businessFrame.setVisible(true);
            businessFrame.setResizable(false);
        }
        
        if (event.getSource()==change){
            String name = JOptionPane.showInputDialog(null, "Input the new Name:");
            if (name==null){
                JOptionPane.showMessageDialog(null, "Name not changed");
            }
            else{
                b.setName(name);
                businessFrame.add(new JLabel("Reopen to see new info. ")).setBounds(320,610,200,40);
            }
        }
        //change information for registration # when button is pressed from editbusiness info frame by owner
        if (event.getSource()==change1){
            String regnum = JOptionPane.showInputDialog(null, "Input the new Registration num:");
            if (regnum==null){
                JOptionPane.showMessageDialog(null, "Registration number not updated");
            }
            else{
                b.setRegisNum(regnum);
                businessFrame.add(new JLabel("Reopen to see updated info. ")).setBounds(320,610,200,40);
            }
        }
        
        //change address when button is pressed from editbusiness info frame by owner
        if (event.getSource()==change2){
            String address = JOptionPane.showInputDialog(null, "Input the new Address:");
            if (address== null){
                JOptionPane.showMessageDialog(null, "Address not updated.");
            }
            else{
                b.setAddress(address);
                businessFrame.add(new JLabel("Reopen to see updated info. ")).setBounds(320,610,200,40);
            }
        }
        //change website when button is pressed from editbusiness info frame by owner
        if (event.getSource()==change3){
            String website = JOptionPane.showInputDialog(null, "Input the new Website:");
            if (website==null){
                JOptionPane.showMessageDialog(null, "Website not updated.");
            }
            else{
                b.setWebsite(website);
                JOptionPane.showMessageDialog(null, "Reopen to see updated info. ");
            }
        }
        //open a new panel by calling specific method when button is pressed from any employee frame
        if(event.getSource()==AddRemoveIncExp){
            editIncExp();
        }
        //add income to business total income when button pressed from IncExpPanel 
        if (event.getSource()==addInc){
            //get incomes data and check for errors then add it
            boolean bool = false;
            String date = JOptionPane.showInputDialog(null, "Input the date of transaction as following: DD/MM/YYYY");            
            if (date == null){
                JOptionPane.showMessageDialog(null, "nothing added");
            }
            else{
                while(!rightDate(date)){//checking date is in proper format by calling a method
                    date = JOptionPane.showInputDialog(null, "Format is wrong enter right format: DD/MM/YYYY");           
                    if (date == null){
                        break;
                    }
                }
                if (date==null){
                    JOptionPane.showMessageDialog(null, "Nothing added");
                }
                else{
                    String amount = JOptionPane.showInputDialog(null, "Input the amount of transaction");
                    if(amount==null){
                        JOptionPane.showMessageDialog(null, "Nothing added");
                    }
                    else{
                        while(!rightAmount(amount)){
                            amount = JOptionPane.showInputDialog(null, "Number not formated properly, reInput hourly paid rate"); 
                            if(amount==null){
                                break;
                            }
                        }
                        if(amount==null){
                            JOptionPane.showMessageDialog(null, "Nothing added");
                        }
                        else{
                            String costumer  = JOptionPane.showInputDialog(null, "Input the Costumer's name");
                            if(costumer==null){
                                JOptionPane.showMessageDialog(null, "Nothing added");
                            }
                            else{
                                String description  = JOptionPane.showInputDialog(null, "Input the Description of transaction");
                                if(description == null){
                                    JOptionPane.showMessageDialog(null, "Nothing added");
                                }
                                else{
                                    b.addIncome(new Date(date), Double.parseDouble(amount), costumer, description, b.employeeIDs(tempID));
                                    JOptionPane.showMessageDialog(null, "Income was successfully added.");
                                }
                            }
                        }
                    }
                }
            }
        }
        //remove income with given data from business total income when button pressed from IncExpPanel 
        if(event.getSource()==removeInc){
            //getting income data to remove with checking for errors
            String inv = JOptionPane.showInputDialog(null, "Input the invoice number to remove");            
            if (inv==null){
                JOptionPane.showMessageDialog(null, "Nothing removed.");
            }
            else{
                String date = JOptionPane.showInputDialog(null, "Input the date of income report to remove as following: DD/MM/YYYY");            
                
                if (date == null){
                    JOptionPane.showMessageDialog(null, "Nothing removed.");
                }
                else{
                    while(!rightDate(date)){//checking date is in proper format by calling a method
                        date = JOptionPane.showInputDialog(null, "Format is wrong enter right format: DD/MM/YYYY");           
                        if (date == null){
                            JOptionPane.showMessageDialog(null, "Nothing removed.");
                            break;
                        }
                    }
                    if(date!=null){
                        Date d = new Date(date);
                        if (b.getDailyReport(d.getYear(), d.getMonth(),d.getDay()) != null){
                            b.getDailyReport(d.getYear(), d.getMonth(),d.getDay()).removeIncome(inv);
                            JOptionPane.showMessageDialog(null, "Income was successfully removed.");
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Income not found."); 
                    }
                }
            }
        }
        //add expense to business total expense when button pressed from IncExpPanel 
        if(event.getSource()==addExp){
            //get expense data and check for errors then add it
            boolean bool=false;
            String date = JOptionPane.showInputDialog(null, "Input the date of transaction as following: DD/MM/YYYY");            
            if (date == null){
                JOptionPane.showMessageDialog(null, "Nothing added");
            }
            else{
                while(!rightDate(date)){//checking date is in proper format by calling a method
                    date = JOptionPane.showInputDialog(null, "Format is wrong enter right format: DD/MM/YYYY");           
                    if (date == null){
                        JOptionPane.showMessageDialog(null, "Nothing added");
                        break;
                    }
                }
                if(date !=null){
                    String amount = JOptionPane.showInputDialog(null, "Input the amount of transaction");
                    if(amount==null){
                        JOptionPane.showMessageDialog(null, "Nothing added");
                    }
                    else{
                        while(!rightAmount(amount)){
                            amount = JOptionPane.showInputDialog(null, "Number not formated properly, reInput hourly paid rate"); 
                            if(amount==null){
                                JOptionPane.showMessageDialog(null, "Nothing added");
                                break;
                            }
                        }
                    
                        if(amount!=null){
                            String from  = JOptionPane.showInputDialog(null, "Input the name of the company paid to");
                            if (from==null){
                                JOptionPane.showMessageDialog(null, "Nothing added");
                            }
                            else{
                                String description  = JOptionPane.showInputDialog(null, "Input the Description of transaction");
                                if (description==null){
                                    JOptionPane.showMessageDialog(null, "Nothing added");
                                }
                                else{
                                    String inv = JOptionPane.showInputDialog(null, "Input the invoice");
                                    if(inv==null){
                                        JOptionPane.showMessageDialog(null, "Nothing added");
                                    }
                                    else{
                                        Expense ex = new Expense(new Date(date), Double.parseDouble(amount), from, description, b.employeeIDs(tempID), inv);
                                        Date d = new Date (date);
                                        try{
                                            b.addExpense(ex.getDate(),ex.getAmount(),ex.getFrom(),ex.getDescription(),ex.getSignedBy(),ex.getInvoiceNum());
                                            JOptionPane.showMessageDialog(null, "Expense was successfully added .");
                                        }
                                        catch(Exception e){
                                            System.out.println(e);
                                            e.printStackTrace();
                                        } 
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //remove expense with given data from business total income when button pressed from IncExpPanel 
        if (event.getSource()==removeExp){
            //getting expense data to remove with checking for errors
            
            String date = JOptionPane.showInputDialog(null, "Input the date of transaction as following to remove: DD/MM/YYYY");            
            if (date == null){
                JOptionPane.showMessageDialog(null, "Nothing removed");
            }
            else{
                while(!rightDate(date)){//checking date is in proper format by calling a method
                    date = JOptionPane.showInputDialog(null, "Format is wrong enter right format: DD/MM/YYYY");           
                    if (date == null){
                        JOptionPane.showMessageDialog(null, "Nothing removed");
                    }
                }
                if(date!=null){
                    Date d1 = new Date(date);
                    if (b.getDailyReport(d1.getYear(), d1.getMonth(),d1.getDay()) != null){
                        String inv = JOptionPane.showInputDialog(null, "Input the invoice number");
                        if(inv==null){
                            JOptionPane.showMessageDialog(null, "Nothing removed.");
                        }
                        else{
                            Date d = new Date(date);
                            if(b.getDailyReport(d.getYear(), d.getMonth(),d.getDay()).removeExpense(inv))
                                JOptionPane.showMessageDialog(null, "Expense was successfully removed.");
                            else
                                JOptionPane.showMessageDialog(null, "Expense was not found.");
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Expense Date is wrong, not found .");
                }
            }
        }
        //create new frame to get more data when button is pressed from owner or manager main screen
        if(event.getSource()==reports){
            //creating frame and attributes
            JFrame reportFrame = new JFrame();
            JLabel imagelogo = new JLabel(logoMed);
            reportFrame.setLayout(null);
            yearly = new JButton("View Yearly Report");
            monthly = new JButton("View Monthly Report");
            daily = new JButton("View Daily Report");
            yearly.addActionListener(this);
            monthly.addActionListener(this);
            daily.addActionListener(this);
            //placing objects in proper place and adding them to frame
            reportFrame.add(imagelogo).setBounds(150,10,200,200);
            reportFrame.add(yearly).setBounds(150,250,200,50);
            reportFrame.add(monthly).setBounds(150,330,200,50);
            reportFrame.add(daily).setBounds(150,410,200,50);
            reportFrame.add(new JLabel("Close to exit.")).setBounds(380,495,100,50);
            //setting frame's info and properties
            reportFrame.setTitle("Reports");
            reportFrame.setSize(500,600);
            reportFrame.setLocationRelativeTo(null);
            reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            reportFrame.setVisible(true);
            reportFrame.setResizable(false);
        }
        //display yearly report when button pressed from report frame 
        if(event.getSource()==yearly){
            //inputting year and checking for inputting errors
            String year = JOptionPane.showInputDialog(null, "Input the year to display report: ");            
            if (year== null){
                
            }
            else{
                
                while(!rightYear(year)){
                    year = JOptionPane.showInputDialog(null, "Error, Input year again");
                    if (year == null){
                        JOptionPane.showMessageDialog(null, "Nothing added");
                        break;
                    }
                }
                if (year!=null){
                    try{
                        if (b.getYearReport(Integer.parseInt(year))==null)
                            JOptionPane.showMessageDialog(null, "Year report does not exist. ");
                        else{  
                            b.getYearReport(Integer.parseInt(year)).getTable();
                            //JOptionPane.showMessageDialog(null, new JTextArea(b.getYearReport(Integer.parseInt(year)).displayReport()));
                        }
                            
                    }
                    catch(Exception e){
                        System.out.println(e);
                        e.printStackTrace();
                    }
                }
            }
        }
        //display monthly report when button pressed from report frame 
        if(event.getSource()==monthly){
            //inputting year,month and checking for inputting errors
            boolean bool = false;
            String year = JOptionPane.showInputDialog(null, "Input the year to display report: ");            
            if (year==null){
                
            }
            else{
                while(!rightYear(year)){//calling method for checking if right formatting year
                    year = JOptionPane.showInputDialog(null, "Error, Input year again");
                    if (year==null)
                            break;
                }
                          
                if (year!=null){
                    String month = JOptionPane.showInputDialog(null, "Input the month to display report: ");            
                    if(month!=null){
                        while(!rightMonth(month) ){//calling method for checking if right formatting month
                            
                                month = JOptionPane.showInputDialog(null, "Error, Input month again"); 
                            if (month==null){
                                
                                break;
                            }
                        }
                        if (month!=null){
                            if (b.getMonthlyReport((Integer.parseInt(year)), (Integer.parseInt(month)))==null)
                                JOptionPane.showMessageDialog(null, "Month report does not exist. ");
                            else    
                                b.getMonthlyReport((Integer.parseInt(year)), (Integer.parseInt(month))).getTable();
                        }
                    }
                }
            }
        }
        
        //display daily report when button pressed from report frame 
        if (event.getSource()==daily){
            //inputting full date and checking for inputting error
            boolean bool = false;
            String date = JOptionPane.showInputDialog(null, "Input the date of transaction as following: DD/MM/YYYY");            
            if (date == null){
                
            }
            else{
                while(!rightDate(date)){//checking date is in proper format by calling a method
                    date = JOptionPane.showInputDialog(null, "Format is wrong enter right format: DD/MM/YYYY");           
                    if (date == null){
                        
                    }
                }
                if (date!=null){
                    Date d = new Date(date);
                    if (b.getDailyReport(d.getYear(), d.getMonth(), d.getDay()) ==null)
                        JOptionPane.showMessageDialog(null, "Day report does not exist. ");
                    else
                       b.getDailyReport(d.getYear(), d.getMonth(), d.getDay()).getTable(); 
                }
            }
        }  
        if(event.getSource()==changePass){
            String tempPass = "";
            String temp = "";
            
            if (Employee == 0){
                temp = JOptionPane.showInputDialog(null, "Input the new password: ");
                if(temp==null){
                    JOptionPane.showMessageDialog(null, "Password not changed. ");
                }
                else{
                    tempPass = JOptionPane.showInputDialog(null, "ReInput the password: ");            
                    if(tempPass==null){
                        JOptionPane.showMessageDialog(null, "Password not changed. ");
                    }
                    else{
                        while (!temp.equals(tempPass)){
                            temp = JOptionPane.showInputDialog(null, "Passwords don't match. Input a password: ");            
                            if(temp==null)
                                break;
                            
                            tempPass = JOptionPane.showInputDialog(null, "ReInput the password: ");
                            if(tempPass==null)
                                break;
                        }
                        if (temp!=null && tempPass !=null && temp.equals(tempPass))
                            b.setPassword(temp);
                    }
                }
            }    
            else if (Employee == 1){
                temp = JOptionPane.showInputDialog(null, "Input the new password: ");
                if(temp==null){
                    JOptionPane.showMessageDialog(null, "Password not changed. ");
                }
                else{
                    tempPass = JOptionPane.showInputDialog(null, "ReInput the password: ");            
                    if(tempPass==null){
                        JOptionPane.showMessageDialog(null, "Password not changed. ");
                    }
                    else{
                        while (!temp.equals(tempPass)){
                            temp = JOptionPane.showInputDialog(null, "Passwords don't match. Input a password: ");            
                            if(temp==null)
                                break;
                            
                            tempPass = JOptionPane.showInputDialog(null, "ReInput the password: ");
                            if(tempPass==null)
                                break;
                        }
                        if (temp!=null && tempPass !=null && temp.equals(tempPass))
                            b.getManager(tempID).setPassword(temp);
                    }
                }
            }
            else{
                temp = JOptionPane.showInputDialog(null, "Input the new password: ");
                if(temp==null){
                    JOptionPane.showMessageDialog(null, "Password not changed. ");
                }
                else{
                    tempPass = JOptionPane.showInputDialog(null, "ReInput the password: ");            
                    if(tempPass==null){
                        JOptionPane.showMessageDialog(null, "Password not changed. ");
                    }
                    else{
                        while (!temp.equals(tempPass)){
                            temp = JOptionPane.showInputDialog(null, "Passwords don't match. Input a password: ");            
                            if(temp==null)
                                break;
                            
                            tempPass = JOptionPane.showInputDialog(null, "ReInput the password: ");
                            if(tempPass==null)
                                break;
                        }
                        if (temp!=null && tempPass !=null && temp.equals(tempPass))
                            b.getCrew(tempID).setPassword(temp);
                    }
                }
            }
            
        }
    }
   
    
    //open the manager frame when method is called
    public void runManagerScreen(Manager m){
        //creating all the objects    
        AddRemoveIncExp = new JButton("Add/Remove Income/Expense");
        AddRemoveWorker = new JButton("Add/Remove Crew");
        reports = new JButton("Reports");
        viewBusiness = new JButton("View Business Info");
        signOut = new JButton("Sign Out");
        save = new JButton("Save");
        saveAs = new JButton("Save As");
        changePass = new JButton("Change Password");
        JLabel imagelogo = new JLabel(logoSmall);
        JLabel welcomename = new JLabel("   Welcome "+m.getName());
        welcomename.setFont(new Font( "Arial", Font.BOLD ,20));
        //adding action listener to the objects that need it
        AddRemoveIncExp.addActionListener(this);
        AddRemoveWorker.addActionListener(this);
        reports.addActionListener(this);
        viewBusiness.addActionListener(this);
        changePass.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        signOut.addActionListener(this);
        //adding the objects to the frame    
        managerFrame.setLayout(null);
        managerFrame.add(welcomename).setBounds(100, 10, 200, 50);
        managerFrame.add(AddRemoveIncExp).setBounds(70,100,250,50);
        managerFrame.add(AddRemoveWorker).setBounds(70,170,250,50);
        managerFrame.add(viewBusiness).setBounds(70, 310, 250, 50);
        managerFrame.add(reports).setBounds(70,240,250,50);
        managerFrame.add(changePass).setBounds(70,380,250,50);
        managerFrame.add(saveAs).setBounds(38,480,100,50);
        managerFrame.add(save).setBounds(148,480,100,50);
        managerFrame.add(signOut).setBounds(258,480,100,50);
        managerFrame.add(imagelogo).setBounds(10,10,50,50);
        //creating the frame with its properties
        managerFrame.setTitle("Manager");
        managerFrame.setSize(400,600);
        managerFrame.setResizable(false);
        managerFrame.setLocationRelativeTo(null);
        managerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerFrame.setVisible(true);
        setVisible(false);//making the main frame disappear for nice looking screen
    }
    //open crew's frame when method is called
    public void runCrewScreen(Crew c){
        //creating all the objects      
        AddRemoveIncExp = new JButton("Add/Remove Income/Expense");
        daily = new JButton("Get Daily Report");
        viewBusiness = new JButton("View Business Info");
        signOut = new JButton("Sign Out");
        save = new JButton("Save");
        saveAs = new JButton("Save As");
        changePass = new JButton("Change Password");
        JLabel imagelogo = new JLabel(logoSmall);
        JLabel welcomeName = new JLabel("Welcome "+c.getName());
        welcomeName.setFont(new Font( "Arial", Font.BOLD ,20));
        //adding action listener to the objects that need it
        AddRemoveIncExp.addActionListener(this);
        daily.addActionListener(this);
        viewBusiness.addActionListener(this);
        changePass.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        signOut.addActionListener(this);
        //adding the objects to the frame
        crewFrame.add(welcomeName).setBounds(120, 10, 200, 50);
        crewFrame.add(imagelogo).setBounds(10,10,80,80);
        crewFrame.add(AddRemoveIncExp).setBounds(60,120,250,50);
        crewFrame.add(daily).setBounds(60,190,250,50);
        crewFrame.add(viewBusiness).setBounds(60, 260, 250, 50);
        crewFrame.add(changePass).setBounds(60,330,250,50);
        crewFrame.add(save).setBounds(132,420,100,50);
        crewFrame.add(saveAs).setBounds(23,420,100,50);
        crewFrame.add(signOut).setBounds(243,420,100,50);
        //creating the frame with its properties
        crewFrame.setLayout(null);
        crewFrame.setTitle("Crew");
        crewFrame.setSize(375,530);
        crewFrame.setLocationRelativeTo(null);
        crewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crewFrame.setVisible(true);
        crewFrame.setResizable(false);
        setVisible(false);//making the main frame disappear for nice looking screen
    }
    //open owner's frame when method is called
    public void runOwnerScreen(){
        //creating all the objects      
        AddRemoveIncExp = new JButton("Add/Remove Income/Expense");
        AddRemoveWorker = new JButton("Add/Remove Worker");
        reports = new JButton("Reports");
        signOut = new JButton("Sign Out");
        save = new JButton("Save");
        saveAs = new JButton("Save As");
        changePass = new JButton("Change Password");
        JLabel imagelogo = new JLabel(logoSmall);
        JLabel welcome1 = new JLabel("Welcome Owner");
        editBusiness = new JButton("Edit Business Info");
        //replacing each object in proper place
        welcome1.setFont(new Font("Arial", Font.BOLD, 23));
        //adding action listener to the objects that need it
        AddRemoveIncExp.addActionListener(this);
        AddRemoveWorker.addActionListener(this);
        reports.addActionListener(this);
        editBusiness.addActionListener(this);
        changePass.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        signOut.addActionListener(this);
        //adding the objects to the frame
        ownerFrame.setLayout(null);
        ownerFrame.add(imagelogo).setBounds(10,10,80,80);
        ownerFrame.add(welcome1).setBounds(130,10,250,50);
        ownerFrame.add(AddRemoveIncExp).setBounds(100,100,250,50);
        ownerFrame.add(AddRemoveWorker).setBounds(100,170,250,50);
        ownerFrame.add(reports).setBounds(100,240,250,50);
        ownerFrame.add(editBusiness).setBounds(100,310,250,50);
        ownerFrame.add(changePass).setBounds(100,380,250,50);
        ownerFrame.add(save).setBounds(173,460,100,50);
        ownerFrame.add(saveAs).setBounds(63,460,100,50);
        ownerFrame.add(signOut).setBounds(283,460,100,50);
        //creating the frame with its properties
        ownerFrame.setTitle("Owner");
        ownerFrame.setSize(450,575);
        ownerFrame.setLocationRelativeTo(null);
        ownerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ownerFrame.setVisible(true);
        ownerFrame.setResizable(false);
        setVisible(false);//making the main frame disappear for nice looking
    }
    //crate a new frame for editing business's employee when method is called
    public void editEmployees(){
        //create new frame with its proper attributes
        editEmployeeFrame = new JFrame();
        JLabel imagelogo = new JLabel(logoSmall);
        addCrew = new JButton("Add Crew");
        removeCrew = new JButton("Remove Crew");
        addMang = new JButton("Add Manager");
        removeMang = new JButton("Remove Manager");
        viewReport = new JButton("View Report");
        //add objects to frame
        editEmployeeFrame.add(imagelogo).setBounds(10,10,80,80);
        editEmployeeFrame.add(addCrew).setBounds(90,190,200,50);
        editEmployeeFrame.add(removeCrew).setBounds(310,190,200,50);
        if (Employee ==0){//iff user is owner than add more features and replace buttons
            editEmployeeFrame.add(addMang).setBounds(90,260,200,50);
            editEmployeeFrame.add(removeMang).setBounds(310,260,200,50);
        }
        editEmployeeFrame.add(viewReport).setBounds(190,120,200,50);
        editEmployeeFrame.add(new JLabel("Close to exit. ")).setBounds(470,375,200,40);
        //adding action listener to the buttons so they can be used
        addCrew.addActionListener(this);
        removeCrew.addActionListener(this);
        addMang.addActionListener(this);
        removeMang.addActionListener(this);
        viewReport.addActionListener(this);
        //creating the frame with its properties
        editEmployeeFrame.setTitle("Edit Employees");
        editEmployeeFrame.setLayout(null);
        editEmployeeFrame.setSize(580,450);
        editEmployeeFrame.setLocationRelativeTo(null);
        editEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editEmployeeFrame.setVisible(true);        
        editEmployeeFrame.setResizable(false);
    }
    //opening Income Expense panel 
    public void editIncExp(){
        //creating new frame with specific objects
        IncExpPanel = new JFrame();
        JLabel imagelogo = new JLabel(logoMed);
        addInc = new JButton("Add Income");
        removeInc = new JButton("Remove Income");
        addExp = new JButton("Add Expense");
        removeExp = new JButton("Remove Expense");
        //add objects to frame
        IncExpPanel.add(imagelogo).setBounds(190,10,200,150);
        //check who is using it, and add proper buttons and features
        if (Employee == 2){
            IncExpPanel.add(addInc).setBounds(180,190,200,50);
            IncExpPanel.add(addExp).setBounds(180,300,200,50);
        }
        else{
            IncExpPanel.add(addInc).setBounds(90,190,200,50);
            IncExpPanel.add(addExp).setBounds(90,300,200,50);
        }
        if(Employee !=2){
            IncExpPanel.add(removeInc).setBounds(310,190,200,50);
            IncExpPanel.add(removeExp).setBounds(310,300,200,50);
        }
        IncExpPanel.add(new JLabel("Close to exit. ")).setBounds(470,375,200,40);
        //adding action listener to buttons so they will be pressable
        addInc.addActionListener(this);
        removeInc.addActionListener(this);
        addExp.addActionListener(this);
        removeExp.addActionListener(this);
        //setting frame properties
        IncExpPanel.setTitle("Edit Income Expense");
        IncExpPanel.setLayout(null);
        IncExpPanel.setSize(580,450);
        IncExpPanel.setLocationRelativeTo(null);
        IncExpPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        IncExpPanel.setVisible(true);        
        IncExpPanel.setResizable(false);
    }
    //checks if date is in right formatting and no letter 
    public boolean rightDate(String s){
        boolean result = true;
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
            return result;
        }   
    }
    
    //checks if number to parse is only # with no letters
    public boolean rightAmount(String s){
        boolean result = true;
        try{
            double d = Double.parseDouble(s);
        }
        catch(Exception e){
            result =false;
        }
        return result;
    }
    
    //checks if year is valid and only #'s
    public boolean rightYear(String y){
        boolean result = true;
        try{
            int i = Integer.parseInt(y);
            if (i<0 )
                result = false;
        }
        catch(Exception e){
            result =false;
        }
        return result;
    }
    //checks if month is valid and only #'s with no letters
    public boolean rightMonth(String m){
        boolean result = true;
        try{
            int i = Integer.parseInt(m);
            if (i<0 || i>12)
                result = false;
        }
        catch(Exception e){
            result =false;
        }
        return result;
    }
    //THE MAIN method to run the full program
}
