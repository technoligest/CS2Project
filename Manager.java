public class Manager extends Employee 
{
    private static int idNum=0;
    
    //instance vars
    private double wage;
    private String ID;
    
    //constructor
    public Manager(String name, Date date, Date birth, String password, double wage)
    {
        super (name, date, birth, password);
        this.wage=wage;
        idNum++;
        String temp= "M";
        for(int i=0; i<4-(idNum+"").length(); i++){
            temp+=0;
        }
        ID=temp+idNum;
    }
    //set
    public void setWage(double w)
    {
        wage = w;
    }

    //get
    public double getWage()
    {
        return wage;
    }
    public String getID()
    {
        return ID;
    }
    public void setID(String i)
    {
        ID=i;
    }
    public String toString()
    {
        return getName()+"\t: "+ID;
    }
}