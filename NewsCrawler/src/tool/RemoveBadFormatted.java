package tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Vector;

public class RemoveBadFormatted {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Vector<String[]> all = new Vector<String[]>();
		String path = "E:\\NEWS\\Liberty.txt";
		File targetFile = new File(path); 
		try {
			String line = "";
			
			String entity[];
			DecimalFormat twoDeg = new DecimalFormat("00");
			BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(targetFile),"UTF-8"));
			while((line=br.readLine())!=null)
			{
				entity = line.split("\t");
				if(entity.length == 4)
				{
					if(entity[0].indexOf("  ")>0)
						entity[0] = entity[0].substring(0,entity[0].indexOf("  "));
					all.add(entity);
				}
				
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int matched = 0;
		targetFile.delete();
		targetFile.createNewFile();
		FileWriter fw = new FileWriter(targetFile, true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),"UTF-8"));
        
		for(int count = 0 ;count<all.size();count++)
		{
			bw.append(all.get(count)[0]+"\t"+all.get(count)[1]+"\t"+all.get(count)[2]+"\t"+all.get(count)[3]+"\n");
		}
		
		bw.close();
	}

}
