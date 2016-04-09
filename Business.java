import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class Business 
{
    public Business(String name, String pass, String regisNum, Date d){
        password=pass;
        this.name=name;
        registrationNum = regisNum;
        date = d;
        address = "Please Input.";
        website = "Please Input.";
    }
    //instance variables
    private String name;//also used as business ID
    private String password;
    private String address;
    private String website;
    private Date date;//date of business created
    private String registrationNum;

    private ArrayList<YearlyReport> reports = new ArrayList<YearlyReport>();
    private ArrayList<Manager> managers = new ArrayList<Manager>();
    private ArrayList<Crew> crew = new ArrayList<Crew>();
    
    //create a string with all the business info for the save functionality
    public void save(String n) throws IOException{
        //make sure all this info is entered properly before hand
        String result=name+"\r\n"+password+"\r\n"+address+"\r\n"+website+"\r\n"+date.save()+"\r\n"+registrationNum+"\r\n";
        
        //add all the managers to the string
        for (int i=0; i<managers.size(); i++)
            result+=(managers.get(i).getID()+" "+managers.get(i).getName()+" "+managers.get(i).getBirth().save()+" "+managers.get(i).getWage()+" "+managers.get(i).getDate().save()+" "+managers.get(i).getPassword()+"\r\n");
        result+="End of managers\r\n";
        
        //add all the employees to the string
        for (int i=0; i<crew.size(); i++)
            result+=(crew.get(i).getID()+" "+crew.get(i).getName()+" "+crew.get(i).getBirth().save()+" "+crew.get(i).getHourlyRate()+" "+crew.get(i).getDate().save()+" "+crew.get(i).getPassword()+"\r\n");
        result+="End of Crew\r\n";
        
        //add all the yearly reports to the string
        for (int i=0; i<reports.size(); i++)
            result+=reports.get(i).save();
        result+="End\r\n";
        
        //here you will create the file and save result into it
        try{
            String fileName = n;
            PrintWriter outputFile = new PrintWriter(new FileWriter(fileName));
            outputFile.println(result);
            JOptionPane.showMessageDialog(null, "Done Saving to file...");
            outputFile.close();
        }
        catch(Exception e){
            System.out.print("error");
        }
    }
    
    //set methods
    public void setRegisNum(String n)    {
        registrationNum = n;
    }
    public void setAddress(String a)    {
        address = a;
    }
    public void setWebsite(String w)    {
        website = w;
    }
    public void setPassword(String p){
        password = p;
    }
    public void setName(String n){
        name = n;
    }
    
    //get methods
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }

    public Date getDateStarted(){
        return date;
    }
    public String getRegisNum()    {
        return registrationNum;
    }
    public int getNumOfManagers()    {
        return managers.size();
    }
    public int getNumOfCrew()    {
        return crew.size();
    }
    public String getAddress()    {
        return address;
    }
    public String getWebsite()    {
        return website;
    }
    
    public Manager getManager(String id){
        Manager result=null;
        for (int i = 0; i<managers.size(); i++)
        {
            if (managers.get(i).getID().equals(id))
            {
                result=managers.get(i);
                break;
            }
        }
        return result;
    }
    public Crew getCrew(String id){
        Crew result=null;
        for (int i = 0; i<crew.size(); i++)
        {
            if (crew.get(i).getID().equalsIgnoreCase(id))
            {
                result=crew.get(i);
                break;
            }
        }
        return result;
    }
    public ArrayList<Crew> getCrewArray(){
        return crew;
    }
    public ArrayList<Manager> getManagerArray(){
        return managers;
    }
    
    public YearlyReport getYearReport(int year)    {
        YearlyReport result=null;
        if (reports.isEmpty())
            return null;
        else if( year>reports.get(reports.size()-1).getYear() || year<reports.get(0).getYear())
            return null;
        else
        {
            for (int i =0; i<reports.size(); i++)
            {
                if(year==reports.get(i).getYear()){
                    result=reports.get(i);
                    break;
                }
            }
        }
        return result;
    }
    public MonthlyReport getMonthlyReport(int year, int month)    {
        YearlyReport temp= null;
        if (reports.isEmpty() || year>reports.get(reports.size()-1).getYear() || year<reports.get(0).getYear())
            return null;
        else
        {
            for (int i =0; i<reports.size(); i++)
            {
                if(year==reports.get(i).getYear()){
                    temp=reports.get(i);
                    break;
                }
            }
        }
        if(temp!=null)
            return temp.getMonthlyReport(month);
        else
            return null;
    }
    public DailyReport getDailyReport(int year, int month, int day)    {
        YearlyReport temp= null;
        if (reports.isEmpty() || year>reports.get(reports.size()-1).getYear() || year<reports.get(0).getYear())
            return null;
        else
        {
            for (int i =0; i<reports.size(); i++)
            {
                if(year==reports.get(i).getYear()){
                    temp=reports.get(i);
                    break;
                }
            }
        }
        if(temp!=null){
            if(temp.getMonthlyReport(month)!=null){
                return temp.getMonthlyReport(month).getDailyReport(day);
            }
            else{
                return null;
            }
        }
        else 
            return null;
    }
    
    //additional methods
    public void addManager(String name, Date dateStartedWorking, Date birth, String password, double wage)    {
        managers.add(new Manager(name, dateStartedWorking, birth, password, wage));
    }
    public void addCrew(String name,Date dateStartedWorking,Date birth, String password, double hourlyPay)    {
        crew.add(new Crew(name, dateStartedWorking, birth, password, hourlyPay));
    }
    
    //adding income to the appropriate dailyReport class
    public void addIncome(Date date, double amount, String customer, String description, String signedBy){
        Income in=new Income(date, amount, customer, description, signedBy);
        if(getYearReport(date.getYear())==null){
           if(reports.isEmpty()){
               reports.add(new YearlyReport(date.getYear()));
               reports.get(0).getReport()[date.getMonth()-1]= new MonthlyReport(date.getMonth(),date.getYear());
               reports.get(0).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]= new DailyReport(date);
               reports.get(0).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addIncome(in);
           }
           else if(reports.get(reports.size()-1).getYear()<date.getYear()){
               reports.add(new YearlyReport(date.getYear()));
               reports.get(reports.size()-1).getReport()[date.getMonth()-1]= new MonthlyReport(date.getMonth(),date.getYear());
               reports.get(reports.size()-1).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]= new DailyReport(date);
               reports.get(reports.size()-1).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addIncome(in);
           }
           else {
               for(int i=0; i<reports.size(); i++){
                   if(reports.get(i).getYear()>date.getYear()){
                       reports.add(i, new YearlyReport(date.getYear()));
                       reports.get(i).getReport()[date.getMonth()-1]= new MonthlyReport(date.getMonth(),date.getYear());
                       reports.get(i).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]= new DailyReport(date);
                       reports.get(i).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addIncome(in);
                       break;
                    }      
               }
           }
        }
        else if(getYearReport(date.getYear()).getReport()[date.getMonth()-1]==null){
           getYearReport(date.getYear()).getReport()[date.getMonth()-1]= new MonthlyReport(date.getMonth(),date.getYear());
           getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]= new DailyReport(date);
           getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addIncome(in);
        }
        else if(getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]==null){
            getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]= new DailyReport(date);
            getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addIncome(in);
        }
        else{
            getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addIncome(in);
        }
    }
    public void addExpense(Date date, double amount, String from, String description, String signedBy, String in){
        Expense ex=new Expense(date, amount, from, description, signedBy, in);
        if(getYearReport(date.getYear())==null){
           if(reports.isEmpty()){
               reports.add(new YearlyReport(date.getYear()));
               reports.get(0).getReport()[date.getMonth()-1]= new MonthlyReport(date.getMonth(),date.getYear());
               reports.get(0).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]= new DailyReport(date);
               reports.get(0).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addExpense(ex);
           }
           else if(reports.get(reports.size()-1).getYear()<date.getYear()){
               reports.add(new YearlyReport(date.getYear()));
               reports.get(reports.size()-1).getReport()[date.getMonth()-1]= new MonthlyReport(date.getMonth(),date.getYear());
               reports.get(reports.size()-1).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]= new DailyReport(date);
               reports.get(reports.size()-1).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addExpense(ex);
           }
           else {
               for(int i=0; i<reports.size(); i++){
                   if(reports.get(i).getYear()>date.getYear()){
                       reports.add(i, new YearlyReport(date.getYear()));
                       reports.get(i).getReport()[date.getMonth()-1]= new MonthlyReport(date.getMonth(),date.getYear());
                       reports.get(i).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]= new DailyReport(date);
                       reports.get(i).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addExpense(ex);
                       break;
                    }      
               }
           }
        }
        else if(getYearReport(date.getYear()).getReport()[date.getMonth()-1]==null){
           getYearReport(date.getYear()).getReport()[date.getMonth()-1] = new MonthlyReport(date.getMonth(),date.getYear());
           getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]= new DailyReport(date);
           getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addExpense(ex);
        }
        else if(getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]==null){
            getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1]= new DailyReport(date);
            getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addExpense(ex);
        }
        else{
            getYearReport(date.getYear()).getReport()[date.getMonth()-1].getReport()[date.getDay()-1].addExpense(ex);
        }
          
    }    
 
    
    //displays a report of all the crew
    public String displayCrewReport(){
        //adding the managers
        String result="ID\t\tManager\t\tBirth\n";
    	for (int i=0; i<managers.size(); i++)
            result+=(managers.get(i).getID()+"\t\t"+managers.get(i).getName()+"\t\t"+managers.get(i).getBirth()+"\n");
        result+="\t\t***End of managers***\n\n";
      
        //adding the crew
        result+="ID\t\tCrew\t\tBirth\n";
    	for (int i=0; i<crew.size(); i++)
            result+=(crew.get(i).getID()+"\t\t"+crew.get(i).getName()+"\t\t"+crew.get(i).getBirth()+"\n");
        result+="\t\t***End of Crew***\n\n";
     
        return result;
    }
    public void getTable(){
        String [] columns = {"ID", "Name", "Birth Date","Date Started Working","Wage"};
        String[][] data= new String [managers.size()][5];
        int z=0;
        for (int i=0; i<managers.size(); i++){
            data[z][0]=managers.get(i).getID();
            data[z][1]=managers.get(i).getName();
            data[z][2]=managers.get(i).getBirth().save();
            data[z][3]=managers.get(i).getDate().save();
            data[z][4]=managers.get(i).getWage()+"";
            z++;
        }
        JTable jt = new JTable(data,columns){
            public boolean isCellEditable(int data, int columns){
                return false;
            }
        };
        jt.setPreferredScrollableViewportSize(new Dimension (450,100));
        jt.setFillsViewportHeight(true);

        JScrollPane jps = new JScrollPane(jt);
        JFrame tables = new JFrame();
        tables.add(jps);
        tables.setTitle("Managers' information");
        tables.setSize(900,150);
        tables.setLocation(150, 140);
        tables.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tables.setVisible(true);
        tables.setResizable(false);
        
        String [] columns1 = {"ID", "Name", "Birth Date","Date Started Working","Wage"};
        String[][] data1= new String [crew.size()][5];
        z=0;
        for (int i=0; i<crew.size(); i++){
            data1[z][0]=crew.get(i).getID();
            data1[z][1]=crew.get(i).getName();
            data1[z][2]=crew.get(i).getBirth().save();
            data1[z][3]=crew.get(i).getDate().save();
            data1[z][4]=crew.get(i).getHourlyRate()+"";
            z++;
        }
        JTable jt1 = new JTable(data1,columns1){
            public boolean isCellEditable(int data1, int columns1){
                return false;
            }
        };
        jt1.setPreferredScrollableViewportSize(new Dimension (450,100));
        jt1.setFillsViewportHeight(true);

        JScrollPane jps1 = new JScrollPane(jt1);
        JFrame tables1 = new JFrame();
        tables1.add(jps1);
        tables1.setTitle("Crews' information");
        tables1.setSize(900,150);
        tables1.setLocation(150, 350);
        tables1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tables1.setVisible(true);
        tables1.setResizable(false);
    }
    //return name of employee given the ID
    public String employeeIDs(String id){
        String result = "Owner";
        for (int i=0; i<managers.size();i++){
            if(managers.get(i).getID().equalsIgnoreCase(id)){
                result = managers.get(i).getName();
                break;
            }
        }
        if(result.equalsIgnoreCase("Owner")){
            for (int i=0; i<crew.size();i++){
                if(crew.get(i).getID().equalsIgnoreCase(id)){
                    result = crew.get(i).getName();
                    break;
                }
            }
        }
        return result;
    }
    
    //read a file given a name
    public String readFile(String fileName){
        String result="";
        try{
            Scanner inputFile = new Scanner(new File(fileName));
            while (inputFile.hasNext()){
		result+=inputFile.nextLine();
            }
	}
	catch(IOException e){
            System.out.print("error");
	}
        return null;
    }
    
    
    
    //proper formatting
    public String toString()
    {
        return "Business: "+name;
    }
    
   
}