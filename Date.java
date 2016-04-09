
import java.util.StringTokenizer;

public class Date 
{
    //instance vars
    private int month;
    private int day;
    private int year;
    
    private String [] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    //constructor
    public Date(int d, int m, int y)
    {
        day = d;
        month = m;
        year = y;
        
    }
    public Date (String i){
        StringTokenizer token;
        token = new StringTokenizer(i, "/");
        day=Integer.parseInt(token.nextToken());
        month=Integer.parseInt(token.nextToken());
        year=Integer.parseInt(token.nextToken());
        
    }
    //get and set methods for all the attributes
    public int getYear(){
        return year;
    }
    public int getDay(){
        return day;
    }
    public int getMonth(){
        return month;
    }
    
    //return a format of the date for the saving feature
    public String save(){
        String result="";
        if(day<10)
            result+="0"+day;
        else 
            result+=day;
        result+= "/";
        if(month<10)
            result+="0"+month;
        else
            result+=month;
        result+= "/";
        result+=year;
        return result;
    }
    
    public void setMonth(int month){
        this.month= month;
    }
    public void setDay(int day){
        this.day=day;
    }
    public void setYear(int year){
        this.year=year;
    }
    //priting in proper format 
    public String toString()
    {
        return monthNames[month-1]+" "+day+", "+year;
    }
}
