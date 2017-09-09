/*********************************************************************
*    Mathew Peet                                     02-Sep-2003     *
*                                                                    *
*    Toy Program which gives the next number in a series,            *
*    next = r * current (1 - current)				     *                                                                
*                                                                    *
*********************************************************************/

#include <stdio.h>

int main()
{
	double x_orig, x, r;
	int i;

//initialise variables	


	x_orig = 0.5;  

              
	for (r = 0; r < 4; r = r + 0.001){
		x = x_orig;
		for (i = 0; i < 500; i++) {	
			x = r * x * (1 - x);
			printf("%lf\t%lf\t%d\n",r,x,i);
		}		
	}
}

