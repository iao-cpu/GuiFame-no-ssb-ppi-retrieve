package no.ssb.ppi.retrieve;

import java.util.Properties;

import com.fame.timeiq.Scalar;
import com.fame.timeiq.TiqView;
import com.fame.timeiq.functors.date.ShiftYear;
import com.fame.timeiq.functors.operators.Divide;
import com.fame.timeiq.functors.operators.Multiply;
import com.fame.timeiq.functors.operators.Subtract;
import com.fame.timeiq.functors.stats.Annpct;
import com.fame.timeiq.functors.stats.Cave;
import com.fame.timeiq.functors.stats.Csum;
import com.fame.timeiq.functors.stats.Diff;
import com.fame.timeiq.functors.stats.Mavec;
import com.fame.timeiq.functors.stats.Mstddev;
import com.fame.timeiq.functors.stats.Msum;
import com.fame.timeiq.functors.stats.Mvar;
import com.fame.timeiq.functors.stats.Pct;
import com.fame.timeiq.functors.stats.Mave;


public class TiqFormula {
	
	public Pct pct;
	public Annpct annpct;
	public Cave cave;
	public Divide divide;
	public ShiftYear shiftyear;
	public Multiply multiply;
	public Subtract subtract;
	public Diff diff;
	public Mave mave;
	public Mstddev mstddev;
	public Csum csum;
	public Mavec mavec;
	public Mvar mvar;
	public Msum msum;
	public Scalar s, scalar;
	public Subtract subtract1;
	
	
	public TiqFormula()
	{
		
	}
	

//Pct functor
public TiqView getPct(TiqView pctFunc)
{
	
	pct = new Pct(pctFunc);
	
	return pct;
	
}

// getAnnpct
public TiqView getAnnpct(TiqView annpctFunc)
{
	annpct = new Annpct(annpctFunc);
	
	return annpct;
}

// Cave functor
public TiqView getCave(TiqView caveFunc)
{
	cave = new Cave(caveFunc);
	return cave;
}

// ytypct functor
public TiqView getYtypct(TiqView ytypctFunc)
{
	
	return null;
}

// divide functor
public TiqView getDivide(TiqView funcView1, TiqView funcView2)
{
	
	divide = new Divide(funcView1, funcView2);
	return divide;
	
}

// ShiftYear 
public TiqView getShiftYear(TiqView funcView1, int y)
{
	shiftyear = new ShiftYear(funcView1, y);
	return shiftyear;
	
}


// Multiply
public TiqView getMultiply(TiqView divideView, TiqView scalar)
{
	
	scalar = new Scalar(new Integer(100));
	multiply = new Multiply (divideView, scalar);	
	return multiply;
	
}


// Subtract
public TiqView getSubtract(TiqView divideView, TiqView s)
 {
	s = new Scalar( new Integer (1));
	
	try {
        subtract = new Subtract( divideView, s);
    }

    catch (Exception e){
            System.out.println(e);
    }
    return subtract;
 }


// Diff functor
public TiqView getDiff(TiqView diffView)
{
	diff = new Diff(diffView);
	return diff;
}

// Mave functor
public TiqView getMave(TiqView maveFunc, int span)
{

       try{

           mave = new Mave(maveFunc, span);

       }

       catch (Exception e){

             System.out.println(e);
       }

       return mave;

}

// Mstddev functor
public TiqView getMstddev(TiqView mstFunc, int s)
{

       try{

            mstddev = new Mstddev(mstFunc, s);

       }

       catch (Exception e){

             System.out.println(e);
       }

      return mstddev;

 }

// Mvar functor
/*public TiqView getMvar(TiqView mvarFunc)
{
	try{
		
		mvar = new Mvar(mvarFunc);
	
	}
	catch (Exception e)
	{
		System.out.println();
	}
	
	return mvar;
}*/

// Csum functor
public TiqView getCsum(TiqView csum)
{
	try{
		
		csum = new Csum(csum);
	}
	catch (Exception e)
	{
		System.out.println();
	}
	
	return csum;
}

// Mavec functor
public TiqView getMavec(TiqView mavecFunc)
{
	try{
		
		mavec = new Mavec(mavecFunc , 1);
		
	}
	catch (Exception e){
		
		System.out.println();
	}
	
	return mavec;
	
}

// Mstddev functor
public TiqView getMstddev(TiqView mstddevFunc)
{
	try{
		
		mstddev = new Mstddev(mstddevFunc, 1);
	}
	catch (Exception e)
	{
		System.out.println();
	}
	
	return mstddev;

}


// Msum functor
public TiqView getMsum(TiqView msumFunc)
{
	try{
		
		msum = new Msum(msumFunc, 2);
		
	}
	catch (Exception e)
	{
		System.out.println();
	}
	
	return msum;
}

// Mvar functor
public TiqView getMvar(TiqView mvarFunc)
{
	try{
		
		mvar = new Mvar(mvarFunc, 1);
			
	}
	catch (Exception e)
	{
		System.out.println();
	
	}
	
	return mvar;
}


// Ytydiff functor
public TiqView getSubtractDiff(TiqView skift_view, TiqView skift_view1)
{
     
	try {

           subtract1 = new Subtract(skift_view, skift_view1);
       }
       catch (Exception e){}

       return subtract1;
}

// Set Common baseYear
public void setCommonBaseYear(TiqView v)
{
	
}


}