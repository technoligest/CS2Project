public class Crew extends Employee
{
    private static int idNum = 0;
    //instance variables
    private double hourlyPay;
    private String ID;
    private int hoursWorked;
    
    //constructor
    public Crew(String name, Date date, Date birth, String password, double hourlyPay)
    {
        super(name,date,birth, password);
        this.hourlyPay=hourlyPay;
        idNum++;
        String temp= "C";
        for(int i=0; i<4-(idNum+"").length(); i++){
            temp+=0;
        }
        ID=temp+idNum;
    }
    
    //set methods
    public void setHourlyRate(double p)
    {
        hourlyPay = p;
    }
    public void setHoursWorked(int h)
    {
        hoursWorked = h;
    }
    public void setID(String i){
        ID = i;
    }
    
    //get methods
    @Override
    public String getName()
    {
        return super.getName();
    }
    public double getHourlyRate()
    {
        return hourlyPay;
    }
    public int getHoursWorked()
    {
        return hoursWorked;
    }
    public String getID()
    {
        return ID;
    }
    public String getPassword()
    {
        return super.getPassword();
    }
    
    //proper formatting
    public String toString()
    {
        return getName()+"-"+ID;
    }
}