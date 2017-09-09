package my.kalkulator;
 
import java.util.*;
import java.io.*;
 
public class Kropka extends KalkulatorUI
{
    public int czyKropka(String x)
    {
        int y = 0;
        StringTokenizer st = new StringTokenizer(x, "1234567890");
       
        while (st.hasMoreTokens())
        {
            String s = st.nextToken();
           
            if (!s.equals("."))
            {
                y = 1;
                return y;
            }
            else
            {
                y = 2;
                return y;
            }
        }
        return y;
    }
}