package GUI;

/*
	Class comment.
*/

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import Listeners.TextActionListener;
import Player.Player;
import java.util.Stack;

public class Output extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField tf;
	private JPanel input_panel;
	private JPanel game_info_panel;
	private MapPanel map_panel;
	private int gameinfoheight = 100;
	private JLabel game_info = new JLabel("Game Information", SwingConstants.CENTER);
	private JLabel input = new JLabel("User input");
	private Dimension map_size;
	private Stack<String> inputHistory = new Stack<String>();
	
	public Output() {
		
		//Set map dimensions using current screensize
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int map_width = (int) (screensize.getWidth() - 150);
		int map_height = (int) (screensize.getHeight() - gameinfoheight - 150);
		this.map_size = new Dimension(map_width, map_height);
		
		this.setTitle("Risk");
	
		//create panels
		this.game_info_panel = new JPanel();
		this.input_panel = new JPanel();
		this.map_panel = new MapPanel(map_size);
		
		//create text field to be added to user input panel.
		this.tf = new JTextField("");
		tf.setPreferredSize(new Dimension(400,24));
		
		//add the labels to the panels
		game_info_panel.add(game_info);
		input_panel.add(input);
		
		//add text field to user input panel.
		input_panel.add(tf);
		
		//create a new panel which consists of panels "game_info_panel" and "input_panel" on top of one another
		JSplitPane bottom_panels = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
		        true, game_info_panel, input_panel);
		        
		//create a new panel which consists of "bottom_panels" beneath "map_panel"
		JSplitPane full_gui= new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
		            true, map_panel, bottom_panels);
		
		//set dimensions for panels
		map_panel.setPreferredSize(map_size);
		game_info.setPreferredSize(new Dimension(map_width, gameinfoheight));
		
		//prevent panels from being resizeable
		bottom_panels.setEnabled(false);
		game_info_panel.setEnabled(false);
		full_gui.setEnabled(false);
	 	
	 	//add gui to jframe
		this.getContentPane().add(full_gui);
		
		//set what happens when "X" is clicked in right top corner.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		//ensures window can't be resized and dimensions aren't ruined.
		this.setResizable(false);
		//make gui visible
		this.setVisible(true);
 
		// Text field stuff
		tf.addActionListener(new TextActionListener(this));
//		tf.addKeyListener(new KeyAdapter() {
//			
//			@Override
//			public void keyReleased(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					
//					if (!getTextField().getText().isEmpty()) {
//						
//						setEnabled(true);
//						
//						// store contents of JTextField to string, print string to console and empty JTextField.
//						String text = getTextField().getText();
//						updateGameInfoPanel(text);
//						addInputToHistory(text);
//						printStack();
//						getTextField().setText(""); 
//					}
//				}
//			}
//		});
		
	}
	
	//FOR TESTING ONLY. REMOVE ONCE MECHANISM HAS BEEN DEVISED FOR PRINTING HISTORY TO PANEL
	public void printStack() {
		for(String x: inputHistory){
			System.out.println(x);
		}
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	//takes user input as argument and adds it to the stack "inputHistory"
	public void addInputToHistory(String input) {
		inputHistory.add(input);
	}
	
	// returns the top element of the stack (used to store the inputs).
	public String getPreviousInputFromStack() {
		return inputHistory.peek();
	}
	
	public boolean isInputStackEmpty() {
		return inputHistory.isEmpty();
	}
	
	public void popInputStack() {
		inputHistory.pop();
	}
	
	// relies on 'game_info' JLabel being declared outside constructor.
	public void updateGameInfoPanel(String updatedText) {
		game_info.setText(updatedText);
	}
	
	//used to make TextField accessible from Listener class
	public JTextField getTextField(){
		return this.tf;
	}
	
	//change Armies displayed on a particular country
	public void setArmies(Player player, int country_index, Integer armysize){
		for (Country country : map_panel.getCountries()){
			if (country.getID() == country_index){
				this.map_panel.setArmies(country, player, armysize);
			}
		}	
	}
	
}