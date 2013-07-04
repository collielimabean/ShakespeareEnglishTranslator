import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class PrintFile 
{
	
	private String path;
	private ArrayList<String[]> words;
	
	public PrintFile(String filepath, ArrayList<String[]> words)
	{
		
		path = filepath;
		
		this.words = words;
		
	}
	
	public void print()
	{
		
		try 
		{
			PrintWriter writer = new PrintWriter(new FileOutputStream(path));
			
			for(String[] line : words)
			{
				
				for(String word : line)
				{
					
					writer.print(word + " ");
					
				}
				
				writer.println();
				
			}
			
			writer.close();
			
		} 
		
		catch (FileNotFoundException e) 
		{
			JOptionPane.showMessageDialog(null, "Could not print \n" 
					+ "java.io.FileNotFoundException");
			
		}
		
		
	}
	
}
