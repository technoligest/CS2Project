public abstract class MoneyExchange 
{
    //attributes
    private Date date;
    private double amount;
    private String description;
    private String signedBy;
    
    //constructor
    public MoneyExchange(Date date, double amount, String description, String signedBy){
        this.date=date;
        this.amount=amount;
        this.description=description;
        this.signedBy=signedBy;
    }
    
    //get methods
    public double getAmount(){
        return amount;
    }
    public Date getDate(){
        return date;
    }
    public String getDescription(){
        return description;
    }
    public String getSignedBy(){
        return signedBy;
    }
    
    //no set methods because the values can not be chanegd after they are created.
    //The only way is to delete the moeny exchange and amke a new one  
}