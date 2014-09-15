import java.util.Calendar;
import java.util.GregorianCalendar;


public class t {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GregorianCalendar start =new  GregorianCalendar(2014, 3, 22);
		String dateInUrl = new Integer(start.getInstance().getTime().getYear()).toString()
	    		+new Integer(start.getInstance().getTime().getMonth()).toString()
	    		+new Integer(start.getInstance().getTime().getDate()).toString();
		System.out.println(new Integer(start.get(Calendar.DATE)));
	}

}
