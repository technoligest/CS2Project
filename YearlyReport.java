import java.awt.Dimension;
import javax.swing.*;
public class YearlyReport
{
    //instance vars
    private int year;
    MonthlyReport [] reports = new MonthlyReport[12];
    //constructor
    public YearlyReport(int year)
    {
        this.year = year; 
    }
    public int getYear()
    {
        return year;
    }
    
    //get reports methods
    public MonthlyReport[] getReport(){
        return reports;
    }
    public MonthlyReport getMonthlyReport(int m)
    {
        if(m<1 || m>12)
            return null;
        else 
            return reports[m-1];
    }
    
    public DailyReport getDailyReport(int month, int day)
    {
        if(month<1 || month>12)
            return null;
        else if(reports[month-1]==null)
            return null;
        else
            return reports[month-1].getDailyReport(day);
    }
    
    //print a report of the year
    public String displayReport(){
        String result="Amount\t\tDate\t\tSignedBy\t\tcustomer/paid to\t\tDescription\n";
        //cycle through all the monthly reports
        for (int i= 0; i<reports.length; i++){
            if (reports[i]!= null){
                for(int j=0; j<reports[i].getReport().length; j++){
                    if(reports[i].getReport()[j]!=null){
                        for(int k=0; k<reports[i].getReport()[j].getReport().size(); k++){
                            if (reports[i].getReport()[j].getReport().getNode(k).getIncome()!=null){
                                result+= " $"+reports[i].getReport()[j].getReport().getNode(k).getIncome().getAmount()+"\t\t";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getIncome().getDate()+"\t\t";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getIncome().getSignedBy()+"\t\t";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getIncome().getCustomer()+"\t\t";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getIncome().getDescription()+"\t";
                                result+= "\n";
                               // System.out.print(1);
                            }
                            else{
                                result+= "-$"+reports[i].getReport()[j].getReport().getNode(k).getExpense().getAmount()+"\t\t";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getExpense().getDate()+"\t\t";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getExpense().getSignedBy()+"\t\t";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getExpense().getFrom()+"\t\t";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getExpense().getDescription()+"\t";
                                result+= "\n";
                                //System.out.print(2);
                            }
                        }
                    }
                }
                
            }
        }
        return result;
    }
    
    public void getTable(){
        String [] columns = {"Invoice num", "Amount", "Date","Signed By", "Customer/paid to","Description"};
        String[][] data= new String [reports.length][6];
        int z=0;
        for (int i= 0; i<reports.length; i++){
            if (reports[i]!= null){
                for(int j=0; j<reports[i].getReport().length; j++){
                    if(reports[i].getReport()[j]!=null){
                        for(int k=0; k<reports[i].getReport()[j].getReport().size(); k++){
                            if (reports[i].getReport()[j].getReport().getNode(k).getIncome()!=null){
                                data[z][0]= reports[i].getReport()[j].getReport().getNode(k).getIncome().getInvoiceNum();
                                data[z][1]= reports[i].getReport()[j].getReport().getNode(k).getIncome().getAmount()+"";
                                data[z][2]= reports[i].getReport()[j].getReport().getNode(k).getIncome().getDate().save();
                                data[z][3]= reports[i].getReport()[j].getReport().getNode(k).getIncome().getSignedBy();
                                data[z][4]= reports[i].getReport()[j].getReport().getNode(k).getIncome().getCustomer();
                                data[z][5]= reports[i].getReport()[j].getReport().getNode(k).getIncome().getDescription();
                                z++;
                            }
                            else{
                                data[z][0]= reports[i].getReport()[j].getReport().getNode(k).getExpense().getInvoiceNum();
                                data[z][1]= "-"+reports[i].getReport()[j].getReport().getNode(k).getExpense().getAmount();
                                data[z][2]= reports[i].getReport()[j].getReport().getNode(k).getExpense().getDate().save();
                                data[z][3]= reports[i].getReport()[j].getReport().getNode(k).getExpense().getSignedBy();
                                data[z][4]= reports[i].getReport()[j].getReport().getNode(k).getExpense().getFrom();
                                data[z][5]= reports[i].getReport()[j].getReport().getNode(k).getExpense().getDescription();
                                z++;
                                //System.out.print(2);
                            }
                        }
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
            tables.setTitle("Yearly Report");
            tables.setSize(900,150);
            tables.setLocationRelativeTo(null);
            tables.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tables.setVisible(true);
            tables.setResizable(false);
    }
    
    //get a format for a string with all the values in the yearly report
    public String save(){
        String result="";
        for (int i= 0; i<reports.length; i++){
            if (reports[i]!= null){
                for(int j=0; j<reports[i].getReport().length; j++){
                    if(reports[i].getReport()[j]!=null){
                        for(int k=0; k<reports[i].getReport()[j].getReport().size(); k++){
                            if (reports[i].getReport()[j].getReport().getNode(k).getIncome()!=null){
                                result+= reports[i].getReport()[j].getReport().getNode(k).getIncome().getInvoiceNum()+"$";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getIncome().getAmount()+"$";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getIncome().getDate().save()+"$";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getIncome().getSignedBy()+"$";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getIncome().getCustomer()+"$";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getIncome().getDescription()+"$\r\r\n";
                                
                                
                               // System.out.print(1);
                            }
                            else{
                                result+= reports[i].getReport()[j].getReport().getNode(k).getExpense().getInvoiceNum()+"$";
                                result+= "-"+reports[i].getReport()[j].getReport().getNode(k).getExpense().getAmount()+"$";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getExpense().getDate().save()+"$";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getExpense().getSignedBy()+"$";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getExpense().getFrom()+"$";
                                result+= reports[i].getReport()[j].getReport().getNode(k).getExpense().getDescription()+"$\r\n";
                                
                                //System.out.print(2);
                            }
                        }
                    }
                } 
            }
        }
        return result;
    }
    
    
}