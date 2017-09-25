package my.kalkulator;
 
// priorytet(String) zwraca priorytet dla poszczególnych operatorów.
 
public class Priorytet extends KalkulatorUI
{
    public int priorytet(String opPro)
    {      
        if (opPro.equals("+") || opPro.equals("-"))
        {
            return 1;
        }
        else if (opPro.equals("*") || opPro.equals("/"))
        {
            return 2;
        }
        else
        {
            return 0;
        }              
    }    
}