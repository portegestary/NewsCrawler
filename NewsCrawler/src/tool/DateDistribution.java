package tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

public class DateDistribution {
	private static String TARGETFILENAME = "E:\\Apple.txt"; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GregorianCalendar start =new  GregorianCalendar(2014, 3, 14);
		GregorianCalendar end =new  GregorianCalendar(2014, 9, 14);
		Vector<String> all = new Vector<String>();
		try {
			String line = "";
			String compareDate[];
			int dateCount = 0;
			BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(new File(TARGETFILENAME))));
			DecimalFormat twoDeg = new DecimalFormat("00");
			while((line=br.readLine())!=null)
			{
				all.add(line.split("\t")[0].split("  ")[0]);
			}
			for(;start.before(end)||start.equals(end);start.add(Calendar.DATE, 1))
			{
				for(int data=0;data<all.size();data++)
				{
					compareDate = all.get(data).split("-");
					//System.out.println(compareDate[2]+"-"+twoDeg.format(start.get(Calendar.DATE)));
					//System.out.println(compareDate[0]+"-"+compareDate[1]+"-"+compareDate[2]);
					//System.out.println(start.get(Calendar.YEAR)+"-"+twoDeg.format(start.get(Calendar.MONTH))+"-"+twoDeg.format(start.get(Calendar.DATE)));
					//System.out.println("============================");
					if(compareDate[0].equals(Integer.toString(start.get(Calendar.YEAR))) && 
							compareDate[1].equals(twoDeg.format(start.get(Calendar.MONTH))) &&
							compareDate[2].equals(twoDeg.format(start.get(Calendar.DATE))))
					{
						dateCount++;
						//System.out.println("here");
					}
				}
				
				System.out.println(start.get(Calendar.YEAR)+"-"
						+start.get(Calendar.MONTH)+"-"
						+start.get(Calendar.DATE)
						+"\t"+dateCount);
				
				dateCount = 0;
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
