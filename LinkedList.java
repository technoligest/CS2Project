public class LinkedList 
{
    private Node front;
    private int count;
    
    //constructor	
    public LinkedList()
    {
    	front = null;
    	count = 0;
    }
    
    //get the current size of the list
    public int size()
    {
    	return count;
    }
    //clear the list
    public void clear()
    {
    	front = null;
	count=0;
    }
    //new method added - get the first node
    public Node getFront()
    {
	return front;
    }
    //scan the list and print contents
    public void enumerate()
    {
    	Node curr = front;
	while (curr!=null)
	{
            System.out.print(curr);
            curr = curr.getNext();
	}
	System.out.println(".");
    }
    

    
    //add a node to the end
    public void add(Income income)
    {
	Node n = new Node(income, null);
	Node curr = front;
	if (front==null)
            front = n;
	else
	{
            while (curr.getNext()!=null)
		curr = curr.getNext();
            curr.setNext(n);
        }
        count++;		
    }
    public void add(Expense expense)
    {
	Node n = new Node(expense, null);
	Node curr = front;
	if (front==null)
            front = n;
	else
	{
            while (curr.getNext()!=null)
		curr = curr.getNext();
            curr.setNext(n);
        }
        count++;		
    }

    //add a node at a given index
    public void add(int index, Income income)
    {
	if (index<0 || index>size())
            System.out.println("Can't add. Index out of bounds");
	else
	{   
            if (index==0){
                Node n = new Node(income, front);
                front=n;
                count++;
            }   
            else
            {
		Node curr = front;
		for(int i=0; i<index-1; i++)
                    curr = curr.getNext();
		Node n = new Node(income,curr.getNext());
		curr.setNext(n);
		count++;
            }
	}		
    }
    public void add(int index, Expense expense)
    {
	if (index<0 || index>size())
            System.out.println("Can't add. Index out of bounds");
	else
	{
            if (index==0){
		Node n = new Node(expense, front);
                front=n;
                count++;
            }
            else
            {
		Node curr = front;
		for(int i=0; i<index-1; i++)
                    curr = curr.getNext();
		Node n = new Node(expense,curr.getNext());
		curr.setNext(n);
		count++;
            }
	}		
    }
    
     public void set(int index, Income income)
    {
        if (index<0 || index>size())
            System.out.println("Can't add. Index out of bounds");
	else
	{
            Node curr = front;
            for(int i=0; i<index; i++)
                curr = curr.getNext();
            curr.setIncome(income);
	}
    }
     public void set(int index, Expense expense)
    {
        if (index<0 || index>size())
            System.out.println("Can't add. Index out of bounds");
	else
	{
            Node curr = front;
            for(int i=0; i<index; i++)
                curr = curr.getNext();
            curr.setExpense(expense);
	}
    }
    
    //search for a given data and return the index of the node (first occurrence)
    //return -1 if not found
    public int contains(Income income)
    {
    	Node curr = front;
	boolean found = false;
	int index = -1;
	while (curr!=null && !found)
	{
            index++;
            if(curr.getIncome()!=null){
                if ((curr.getIncome().getInvoiceNum()).equalsIgnoreCase(income.getInvoiceNum())) {//.isEqual(income)){
                    found=true;
                    break;
                }
            }
            curr= curr.getNext();
	}
	if (!found)
            return -1;
	else
            return index;
    }
    
    public int contains(Expense expense)
    {
            Node curr = front;
            boolean found = false;
            int index = -1;
            while (curr!=null&&!found)
            {
                index++;
                if(curr.getExpense()!=null){
                    if (curr.getExpense().isEqual(expense)){
                        found=true;
                        break;
                    }
                }
                curr= curr.getNext();
            }
            if (!found)
                return -1;
            else
                return index;

    }
   
    //remove a node at a given index
    public void remove(int index)
    {
        if (index<0 || index>=size())
            System.out.println("Can't remove. Index out of bounds");
        else if (index==0){
            front=front.getNext();
            count--;
        }
        else
        {
            Node curr = front;
            for(int i=0;i<index-1;i++)
                curr = curr.getNext();
            curr.setNext(curr.getNext().getNext());
            count--;
        }
    }
    //get a node data given an index
    public Node getNode(int index)
    {
        if(index>count-1 || index<0){
            return null;
        }
        else{
            Node curr = front;
            int i=0;
            while (i!=index)
            {
                curr=curr.getNext();
                i++;
            }
            return curr;
        }
    }
}
