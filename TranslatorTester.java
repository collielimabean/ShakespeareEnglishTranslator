import javax.swing.SwingUtilities;


public class TranslatorTester 
{
	
	public static void main(String[] args)
	{
		
		SwingUtilities.invokeLater(new Runnable()
		{
			
			public void run()
			{
				ShakespeareGUI g = new ShakespeareGUI();
				g.show();
			}
			
		});
		
	}
	
}
