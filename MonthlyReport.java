import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
public class MonthlyReport 
{
    //instance vars
    int month;
    int year;
    DailyReport[] reports;
    
    //constructor
    public MonthlyReport(int month, int year){
        this.month=month;
        this.year=year;
        int temp;
        if(month==2)
            temp=28;
        else if(month==4 || month==6 || month==9 || month==11)
            temp=30;
        else
            temp=31;
        reports= new DailyReport[temp];
    }
    
    //get methods
    public int getMonth(){
        return month;
    }
    public int getYear(){
        return year;
    }
    public DailyReport[] getReport(){
        return reports;
    }
    //input the day of the month to get the report of that day
    public DailyReport getDailyReport(int i){
        if(i>reports.length || i<1){
            return null;
        }
        else 
            return reports[i-1];
    }
    
    //set methods
    public void setMonth(int i){
        month=i;
    }
    public void setYear(int i){
        year=i;
    }
   
    public String displayReport(){
        String result="Amount\t\tDate\t\tSignedBy\t\tcustomer\t\tDescription\n";
        //cycle through all the monthly reports
            for(int j=0; j<reports.length; j++){
                if(reports[j]!=null){
                    for(int k=0; k<reports[j].getReport().size(); k++){
                        if (reports[j].getReport().getNode(k).getIncome()!=null){
                            result+= "$"+reports[j].getReport().getNode(k).getIncome().getAmount()+"\t\t";
                            result+= reports[j].getReport().getNode(k).getIncome().getDate()+"\t\t";
                            result+= reports[j].getReport().getNode(k).getIncome().getSignedBy()+"\t\t";
                            result+= reports[j].getReport().getNode(k).getIncome().getCustomer()+"\t\t";
                            result+= reports[j].getReport().getNode(k).getIncome().getDescription()+"\t";
                            result+= "\n";
                        }
                        else{
                            result+= "-$"+reports[j].getReport().getNode(k).getExpense().getAmount()+"\t\t";
                            result+= reports[j].getReport().getNode(k).getExpense().getDate()+"\t\t";
                            result+= reports[j].getReport().getNode(k).getExpense().getSignedBy()+"\t\t";
                            result+= reports[j].getReport().getNode(k).getExpense().getFrom()+"\t\t";
                            result+= reports[j].getReport().getNode(k).getExpense().getDescription()+"\t";
                            result+= "\n";
                        }
                    }
                    result+= "\n";
                }
            }
            result+= "\n";
        return result;
    }
    
    
    public void getTable(){
        String [] columns = {"Invoice num", "Amount", "Date","Signed By", "Customer/paid to","Description"};
        String[][] data= new String [reports.length][6];
        int z=0;
        for(int j=0; j<reports.length; j++){
            if(reports[j]!=null){
                for(int k=0; k<reports[j].getReport().size(); k++){
                    if (reports[j].getReport().getNode(k).getIncome()!=null){
                        data[z][0]= reports[j].getReport().getNode(k).getIncome().getInvoiceNum();
                        data[z][1]= reports[j].getReport().getNode(k).getIncome().getAmount()+"";
                        data[z][2]= reports[j].getReport().getNode(k).getIncome().getDate().save();
                        data[z][3]= reports[j].getReport().getNode(k).getIncome().getSignedBy();
                        data[z][4]= reports[j].getReport().getNode(k).getIncome().getCustomer();
                        data[z][5]= reports[j].getReport().getNode(k).getIncome().getDescription();
                        z++;
                    }
                    else{
                        data[z][0]= reports[j].getReport().getNode(k).getIncome().getInvoiceNum();
                        data[z][1]= "-$"+reports[j].getReport().getNode(k).getExpense().getAmount()+"";
                        data[z][2]= reports[j].getReport().getNode(k).getExpense().getDate().save();
                        data[z][3]= reports[j].getReport().getNode(k).getExpense().getSignedBy();
                        data[z][4]= reports[j].getReport().getNode(k).getExpense().getFrom();
                        data[z][5]= reports[j].getReport().getNode(k).getExpense().getDescription();
                        z++;
                    }
                }
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
            tables.setTitle("Monthly Report");
            tables.setSize(900,150);
            tables.setLocationRelativeTo(null);
            tables.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tables.setVisible(true);
            tables.setResizable(false);
    }
    
}