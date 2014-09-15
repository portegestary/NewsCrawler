package tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class DuplicateChecker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<String[]> all = new Vector<String[]>();
		try {
			String line = "";
			BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(new File("E:\\Liberty2.txt"))));
			while((line=br.readLine())!=null)
			{
				all.add(line.split("\t"));
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int matched = 0;
		for(int count = 0 ;count<all.size();count++)
		{
			for(int count_match = count+1;count_match<all.size();count_match++)
			{
				//System.out.println(all.get(count)[1]+"\t"+all.get(count_match)[1]+"\t"+all.get(count)[1].equals(all.get(count_match)[1]));
				if(all.get(count)[1].equals(all.get(count_match)[1]))
				{
					System.out.println(all.get(count)[1]+" is duplicated in "+count+" and "+count_match);
					matched++;
				}
			}
		}
		System.out.println("Checking Complete! " + matched + " matched!");
	}

}
