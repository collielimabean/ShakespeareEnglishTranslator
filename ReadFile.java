import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class ReadFile 
{
	
	private File raw;
	private ArrayList<String[]> words;
	
	public ReadFile(String filepath)
	{
		
		raw = new File(filepath);
		
	}
	
	public void process()
	{
		
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(raw));
			
            String dataRow = reader.readLine(); 
              
            words = new ArrayList<String[]>(); 
              
            while (dataRow != null) 
            { 
                  
                words.add(dataRow.split(" ")); 
                  
                 dataRow = reader.readLine();  
            } 
              
             reader.close(); 
		} 
		
		catch (FileNotFoundException e) 
		{
			JOptionPane.showMessageDialog(null, "java.io.FileNotFoundException");
		}
		
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "java.io.IOException");
		}
		
	}
	
	public ArrayList<String[]> getWords()
	{
		return words;
	}
	
}
