public class Income extends MoneyExchange
{
    //attributes
    private String customer;
    private static int invoice=0;
    private String invoiceNum="";
    
    //constructor
    public Income(Date date, double amount, String customer, String description, String signedBy){
        super(date, amount, description, signedBy);
        this.customer=customer;
        for(int i=0; i<5-(invoice+"").length(); i++){
            invoiceNum+=0;
        }
        invoiceNum += (++invoice);
    }
    
    //get method
    public String getCustomer()
    {
        return customer;
    }
    
    public String getInvoiceNum()
    {
        return invoiceNum;
    }
    
    //set method
    public void setCustomer(String c)
    {
        customer = c;
    }
    
    //see if two incomes are the same
    public boolean isEqual(Income income){
        /*return getAmount()==income.getAmount() &&
               customer.equals(income.getCustomer()) && 
               getDescription().equals(income.getDescription()) &&
               getSignedBy().equals(income.getSignedBy()) &&
               getDate().getYear()==income.getDate().getYear() &&
               getDate().getMonth()==income.getDate().getMonth() &&
               getDate().getDay()==income.getDate().getDay();*/
        return income.getInvoiceNum().equalsIgnoreCase(invoiceNum);
    }
    
    public boolean isEqual(Expense in){
        return false;
    }
    
    public String toString(){
        return "Income of "+getAmount()+" on "+ getDate()+" taken by "+getSignedBy();
    }
}