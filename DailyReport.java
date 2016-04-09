
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DailyReport
{
    //attributes
    LinkedList income = new LinkedList();
    LinkedList expense = new LinkedList();
    LinkedList report = new LinkedList();//this aggrigates both reports
    private Date date;
    
    //constructor
    public DailyReport(Date date){
        this.date=date;
    }
    
    //still need to add sorting capabilities later
    //addition methods
    public void addIncome(Income in){
        income.add(in);
        report.add(in);
    }
    public void addExpense(Expense ex){
        expense.add(ex);
        report.add(0, ex);
    }
    
    //get methods
    public LinkedList getIncomeReport(){
        return income;
    }
    public LinkedList getExpenseReport(){
        return expense;
    }
    public LinkedList getReport(){
        return report;
    }
    
    //remove income or expense given the invoice number
    public boolean removeIncome(String in){
        boolean result=false;
        if(income.size()==0)
            System.out.println("Income not found");
        else {
            Income tempIncome;
            for (int i =0; i<income.size(); i++){
                if (income.getNode(i).getIncome().getInvoiceNum().equalsIgnoreCase(in)){
                    tempIncome = income.getNode(i).getIncome();
                    report.remove(report.contains(tempIncome));
                    income.remove(income.contains(tempIncome));
                    result=true;
                    break;
                }  
            }
        }
        return result;
    }
    public boolean removeExpense(String in){
        boolean result=false;
        if(expense.size()==0)
            result=false;
        else {
            Expense tempExpense;
            for (int i =0; i<expense.size(); i++){
                if (expense.getNode(i).getExpense().getInvoiceNum().equalsIgnoreCase(in)){
                    tempExpense = expense.getNode(i).getExpense();
                    report.remove(report.contains(tempExpense));
                    expense.remove(expense.contains(tempExpense));
                    result=true;
                    break;
                }  
            }
        }
        return result;
    }
    
    
    
    // return a string with all the values of the report
    public String displayReport(){
        String result="Amount\t\tDate\t\tSignedBy\t\tcustomer\t\tDescription\n";
        //cycle through all the monthly reports
        
        for(int k=0; k<report.size(); k++){
            if (report.getNode(k).getIncome()!=null){
                result+= " $"+report.getNode(k).getIncome().getAmount()+"\t\t";
                result+= report.getNode(k).getIncome().getDate()+"\t\t";
                result+= report.getNode(k).getIncome().getSignedBy()+"\t\t";
                result+= report.getNode(k).getIncome().getCustomer()+"\t\t";
                result+= report.getNode(k).getIncome().getDescription()+"\t";
                result+= "\n";
            }
            else{
                result+= "-$"+report.getNode(k).getExpense().getAmount()+"\t\t";
                result+= report.getNode(k).getExpense().getDate()+"\t\t";
                result+= report.getNode(k).getExpense().getSignedBy()+"\t\t";
                result+= report.getNode(k).getExpense().getFrom()+"\t\t";
                result+= report.getNode(k).getExpense().getDescription()+"\t";
                result+= "\n"; 
            }
        }
        result+= "\n";
        return result;
    }
    public void getTable(){
        String [] columns = {"Invoice num", "Amount", "Date","Signed By", "Customer/paid to","Description"};
        String[][] data= new String [report.size()][6];
        int z=0;
        for(int k=0; k<report.size(); k++){
            if (report.getNode(k).getIncome()!=null){
                data[z][0]= report.getNode(k).getIncome().getInvoiceNum();
                data[z][1]= report.getNode(k).getIncome().getAmount()+"";
                data[z][2]= report.getNode(k).getIncome().getDate().save();
                data[z][3]= report.getNode(k).getIncome().getSignedBy();
                data[z][4]= report.getNode(k).getIncome().getCustomer();
                data[z][5]= report.getNode(k).getIncome().getDescription();
                z++;
            }
            else{
                data[z][0]= report.getNode(k).getExpense().getInvoiceNum();
                data[z][1]= report.getNode(k).getExpense().getAmount()+"";
                data[z][2]= report.getNode(k).getExpense().getDate().save();
                data[z][3]= report.getNode(k).getExpense().getSignedBy();
                data[z][4]= report.getNode(k).getExpense().getFrom();
                data[z][5]= report.getNode(k).getExpense().getDescription();
                z++;
            }       
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
            tables.setTitle("Business");
            tables.setSize(900,150);
            tables.setLocationRelativeTo(null);
            tables.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tables.setVisible(true);
            tables.setResizable(false);
    }
    
}