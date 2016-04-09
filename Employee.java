
import java.util.Random;

public abstract class Employee 
{
   
    
    //instance variables    
    private String name;
    private String password;
    private Date birth;
    private Date date;//date started workiing
   
    //constructor
    public Employee(String name, Date date, Date birth, String password)
    {
        
        this.name = name;
        this.date = date;
        this.birth = birth;
        this.password = password;
        
    }

    //set methods
    public void setName(String n)
    {
        name = n;
    }
     public void setPassword(String p)
    {
        password = p;
    }
    public void setBirth(Date b)
    {
        birth = b;
    }
    public void setDate(Date d)
    {
        date = d;
    }
    
   
    //get methods
    public String getName()
    {
        return name;
    }
    public String getPassword()
    {
        return password;
    }
     public Date getBirth()
    {
        return birth;
    }
    public Date getDate()
    {
        return date;
    }

    
}