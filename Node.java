public class Node 
{
    //attributes
    private Income income;
    private Expense expense;
    private Node next;
    
    //constructor
    public Node(Income income, Node n)
    {
    	this.income = income;
	next = n;
    }
    public Node(Expense expense, Node n)
    {
    	this.expense = expense;
	next = n;
    }
    
    //accessor methods
    public Income getIncome()
    {
	return income;
    }
    public Expense getExpense()
    {
	return expense;
    }
    public Node getNext()
    {
    	return next;
    }
    
    //mutator methods
    public void setIncome(Income income)
    {
    	this.income = income;
    }
    public void setExpense(Expense expense)
    {
    	this.expense = expense;
    }
    public void setNext(Node n)
    {
    	next = n;
    }
    public String toString(){
        if(income==null){
            return expense.getAmount()+"-->";
        }
        else{
            return income.getAmount()+"-->";
        }
    }
}
