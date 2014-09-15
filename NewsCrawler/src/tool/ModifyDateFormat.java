package tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Vector;

public class ModifyDateFormat {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Vector<String[]> all = new Vector<String[]>();
		File targetFile = new File("E:\\Apple2.txt"); 
		try {
			String line = "";
			
			String entity[];
			DecimalFormat twoDeg = new DecimalFormat("00");
			BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(targetFile)));
			while((line=br.readLine())!=null)
			{
				String []addEntity = new String [3];
				String content = "";
				entity = line.split("\t");
				entity[0] = entity[0].split("年")[0]
			    		  +"-"+twoDeg.format(Integer.parseInt(entity[0].split("年")[1].split("月")[0]))
			    		  +"-"+twoDeg.format(Integer.parseInt(entity[0].split("年")[1].split("月")[1].replace("日","")));
				for(int cont = 2;cont<(entity.length)-2;cont++)
				{
					content = content + entity[cont] +"     ";
				}
				addEntity[0] = entity[0];
				addEntity[1] = entity[1];
				addEntity[2] = content;
				all.add(addEntity);
				
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
        BufferedWriter bw = new BufferedWriter(fw);
		for(int count = 0 ;count<all.size();count++)
		{
			if(all.get(count).length<3)
			{
				bw.append(all.get(count)[0]+"\t"+all.get(count)[1]+"\n");
			}
			else
				bw.append(all.get(count)[0]+"\t"+all.get(count)[1]+"\t"+all.get(count)[2]+"\n");
		}
		
		bw.close();
	}

}
