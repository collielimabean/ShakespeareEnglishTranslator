import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;

public class ShakespeareGUI implements ActionListener, KeyListener
{
	
	private static final Insets insets = new Insets(0, 0, 0, 0);
	private final String DEFAULT_STATE = "None Selected";
	
	private JFrame frame;
	private JSplitPane split;
	
	private JPanel leftPanel;
	private JPanel rightPanel;

	private JButton shakespearify = new JButton("Shakespearify");
	
	private JLabel outPath = new JLabel(DEFAULT_STATE);
	private JLabel inPath = new JLabel(DEFAULT_STATE);
	
	private final JButton loadFile = new JButton("Select File Location");
	private final JButton selectOutput = new JButton("Select Output Location");
	
	private JScrollPane inputPane;
	private JScrollPane outputPane;
	
	private ArrayList<Character> typedIn = new ArrayList<Character>();
	
	public ShakespeareGUI()
	{
		
		//frame initialization
		frame = new JFrame("Shakespeare Translator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		
		//add actionlisteners
		shakespearify.addActionListener(this);	
		loadFile.addActionListener(this);
		selectOutput.addActionListener(this);
		
		//intialize text areas
		JTextArea inputText = new JTextArea();
		inputText = new JTextArea();
		inputText.setEditable(true);
		inputText.setLineWrap(true);
		inputText.setWrapStyleWord(true);
		inputText.addKeyListener(this);
		
		inputPane = new JScrollPane(inputText);
		inputPane.setFocusable(true);
		
		JTextArea outputText = new JTextArea();
		outputText = new JTextArea();
		outputText.setEditable(false);
		outputText.setLineWrap(true);
		outputText.setWrapStyleWord(true);
		
		outputPane = new JScrollPane(outputText);
		
		//add elements
		leftPanel.setLayout(new GridBagLayout());
		rightPanel.setLayout(new GridBagLayout());

		//left side
		addComponent(leftPanel, shakespearify, 0, 0, 1, 10);
		addComponent(leftPanel, selectOutput, 0, 12, 1, 1);
		addComponent(leftPanel, outPath, 0, 13, 1, 1);
		
		//right side
		addComponent(rightPanel, inputPane, 0, 0, 1, 10);
		addComponent(rightPanel, loadFile, 0, 11, 1, 1);
		addComponent(rightPanel, inPath, 0, 12, 1, 1);
		addComponent(rightPanel, outputPane, 0, 13, 1, 1);
		
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);		
		
		frame.add(split);
	}
	
	private void addComponent(Container parent, Component component, int gridx, int gridy,
		      int gridwidth, int gridheight) 
	{
		    GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
		        GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 0, 0);
		    
		    parent.add(component, gbc);
	}
	
	public void show()
	{
		frame.setSize(750, 500);
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{	
		Object src = arg0.getSource();
		
		if(src == shakespearify)
		{
			
			if(!(outPath.getText().equals(DEFAULT_STATE) || inPath.equals(DEFAULT_STATE)))
			{
				
				//Read in text file
				ReadFile r = new ReadFile(inPath.getText());
				r.process();
				
				//get words
				ArrayList<String[]> words = r.getWords();
				
				//print input text to screen
				
				JTextArea c = (JTextArea) inputPane.getViewport().getView();
				
				for(String[] line : words)
				{
					
					for(String word : line)
					{
						
						c.append(word + " ");
						
					}
					
					c.append("\n");
					
				}
				
				JTextArea current = (JTextArea) inputPane.getViewport().getView();
				current = c;	
				
				outputPane.validate();	
				
				//translate
				ArrayList<String[]> converted = Translator.translate(words);
				
				//null check
				if(converted == null)
				{
					JOptionPane.showMessageDialog(frame, "Translator messed up \n" 
							+ "java.lang.NullPointerException");
				}
				
				else
				{
					
					//print converted text to save location
					PrintFile p = new PrintFile(outPath.getText(), converted);
					p.print();
					
					c = (JTextArea) outputPane.getViewport().getView();
			
					for(String[] line : converted)
					{
						
						for(String word : line)
						{
							
							c.append(word + " ");
							
						}
						
						c.append("\n");
						
					}
					
					current = (JTextArea) outputPane.getViewport().getView();
					current = c;
					
					outputPane.validate();
					
					//notify when complete
					JOptionPane.showMessageDialog(frame, "Done translating!");
					
				}
			}
			
			else
			{
				JOptionPane.showMessageDialog(frame, "Please select an input file and an output location.");
			}
			
		}
		
		else
		{
			
			JFileChooser c = new JFileChooser();
			
			if(src == loadFile)
			{
				int returnVal = c.showOpenDialog(frame);
				
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					String filePath = c.getSelectedFile().getAbsolutePath();
					
					if(filePath.endsWith(".txt"))
					{
						inPath.setText(filePath);
						inPath.validate();
					}
					
					else
					{
						JOptionPane.showMessageDialog(frame, "Choose a .txt file!");
					}

				}
			}
			
			else
			{
				
				int returnVal = c.showSaveDialog(frame);
				
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					String filePath = c.getSelectedFile().getAbsolutePath();
					
					filePath += ".txt";
					
					outPath.setText(filePath);
					outPath.validate();
				}
				
			}
			
		}
		
	}

	public void keyPressed(KeyEvent arg0) 
	{	}

	public void keyReleased(KeyEvent arg0) 
	{	
		
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
		{
	
			String rawText = "";
			
			for(Character ch : typedIn)
			{
				
				rawText += Character.toString(ch);
				
			}
			
			String[] container = new String[1];
			container[0] = rawText;
			
			ArrayList<String[]> superContainer = new ArrayList<String[]>();
			superContainer.add(container);
			
			superContainer = Translator.translate(superContainer);
			
			JTextArea newTextHolder = (JTextArea) outputPane.getViewport().getView();
			
			newTextHolder.append("\n");
			
			for(String[] l : superContainer)
			{
				
				
				for(String w : l)
				{
					
					newTextHolder.append(w + " ");
					
				}
				
				newTextHolder.append("\n");
				
			}
			
			JTextArea current = (JTextArea) outputPane.getViewport().getView();
			current = newTextHolder;
			outputPane.validate();
			
			typedIn = new ArrayList<Character>();
		}
		
		
	}

	public void keyTyped(KeyEvent arg0)
	{
		
		Character ch = new Character(arg0.getKeyChar());
		
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
		{
			this.keyReleased(arg0);
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			typedIn.remove(typedIn.size() - 1);
		}
		
		else
		{
			System.out.println(typedIn);
			typedIn.add(ch);
		}
		
	}

	
}
