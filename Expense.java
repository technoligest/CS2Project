public class Expense extends MoneyExchange
{
    //attributes
    private String from;//where the epense came from
    private String invoiceNum;
    
    //constructor
    public Expense(Date date, double amount, String from, String description, String signedBy, String in){
        super(date, amount, description, signedBy);
        this.from=from;
        invoiceNum = in;
    }
    
    //get method
    public String getFrom()
    {
        return from;
    }
    public String getInvoiceNum(){
        return invoiceNum;
    }
    
    //set method
    public void setCustomer(String c)
    {
        from = c;
    }
    public void setInvoiceNum(String i){
        invoiceNum=i;
    }
    
    //see if two incomes are the same
    /*public boolean isEqual(Expense expense){
        return getAmount()==expense.getAmount() &&
               invoiceNum.equals(expense.getInvoiceNum()) &&
               from.equals(expense.getFrom()) && 
               getDescription().equals(expense.getDescription()) &&
               getSignedBy().equals(expense.getSignedBy()) &&
               getDate().getYear()==expense.getDate().getYear() &&
               getDate().getMonth()==expense.getDate().getMonth() &&
               getDate().getDay()==expense.getDate().getDay();
    }*/
    public boolean isEqual(Expense ex){
        return ex.getInvoiceNum().equalsIgnoreCase(invoiceNum) && ex.getFrom().equalsIgnoreCase(from);
    }
    
    public boolean isEqual(Income in){
        return false;
    }
    
    public String toString(){
        return "Expense of "+getAmount()+" on "+ getDate()+" from "+ from;
    }

}