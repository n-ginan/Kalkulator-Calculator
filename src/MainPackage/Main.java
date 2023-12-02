package MainPackage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	JFrame frame = new JFrame();
	JPanel mainPanel = new JPanel();
	JPanel outputPanel = new JPanel();
	JPanel spacePanel = new JPanel();
	JPanel inputPanel = new JPanel();
	JPanel numbersPanel = new JPanel();
	JPanel operatorsPanel = new JPanel();
	JTextField calculation = new JTextField(20);
	JLabel answer = new JLabel("0");
	Border frameBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	Border transBorder = BorderFactory.createLineBorder(new Color(0, 0, 0, 0));
	CompoundBorder compoundBorder = new CompoundBorder(frameBorder, transBorder);
	
	//---------Number-Buttons---------\\
	
	JButton button1 = new JButton("1");
	JButton button2 = new JButton("2");
	JButton button3 = new JButton("3");
	JButton button4 = new JButton("4");
	JButton button5 = new JButton("5");
	JButton button6 = new JButton("6");
	JButton button7 = new JButton("7");
	JButton button8 = new JButton("8");
	JButton button9 = new JButton("9");
	JButton button0 = new JButton("0");
	JButton buttonNegPos = new JButton("±");
	JButton buttonClear = new JButton("C");
	
	//---------Operator-Buttons---------\\
	
	JButton buttonAdd = new JButton("+");
	JButton buttonSub = new JButton("-");
	JButton buttonMul = new JButton("*");
	JButton buttonDiv = new JButton("/");
	JButton buttonDeci = new JButton(".");
	JButton buttonEqual = new JButton("=");

	Main() {
		frame.setTitle("Kalkulator");
		frame.setResizable(false);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		List<JButton> numberButtons = new ArrayList<>(Arrays.asList(button1, button2,
															  button3, button4,
															  button5, button6,
															  button7, button8,
															  button9, button0,
															  buttonNegPos, buttonClear));
		
		List<JButton> operatorButtons = new ArrayList<>(Arrays.asList(buttonAdd, buttonSub,
																	buttonMul, buttonDiv,
																	buttonDeci, buttonEqual));
																	
		
		mainPanel.setBorder(compoundBorder);
		mainPanel.setBackground(new Color(40, 42, 54));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		//--------OUTPUT-PANEL--------\\
		
		outputPanel.setLayout(null);
		outputPanel.setBackground(null);
		outputPanel.setPreferredSize(new Dimension(250, 100));
		
		calculation.setSize(400, 50);
		calculation.setBorder(null);
		calculation.setEditable(false);
		calculation.setFont(new Font("Oswald", Font.BOLD, 36));
		calculation.setForeground(Color.WHITE);
		calculation.setBackground(new Color(0, 0, 0, 0));
		calculation.setCaretColor(new Color(0, 0, 0, 0));
		
		answer.setSize(400, 100);
		answer.setVerticalAlignment(JLabel.BOTTOM);
		answer.setHorizontalAlignment(JLabel.RIGHT);
		answer.setFont(new Font("Oswald", Font.BOLD, 36));
		answer.setForeground(Color.WHITE);
		
		outputPanel.add(calculation);
		outputPanel.add(answer);
		
		//--------OUTPUT-PANEL--------\\
		
		spacePanel.setPreferredSize(new Dimension(10, 10));
		spacePanel.setBackground(null);
		
		//--------INPUT-PANEL--------\\
		
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
		inputPanel.setBackground(new Color(0, 0, 0, 0));
		inputPanel.setPreferredSize(new Dimension(400, 165)); //Change the y-dimension for other buttons in the future
		
		inputPanel.add(numbersPanel);
		inputPanel.add(operatorsPanel);
		
		numbersPanel.setLayout(new GridLayout(3, 0));
		numbersPanel.setBackground(new Color(40, 42, 54));
		numbersPanel.setPreferredSize(new Dimension(250, 200));
		
		operatorsPanel.setLayout(new GridLayout(3, 0));
		operatorsPanel.setBackground(new Color(40, 42, 54));
		operatorsPanel.setPreferredSize(new Dimension(120, 350));
		
		numberButtons.forEach((button) -> {
			button.setPreferredSize(new Dimension(60, 50));
			button.setFont(new Font("Oswald", Font.BOLD, 13));
			button.setBorderPainted(false);
			button.setBackground(new Color(0, 0, 0, 0));
			button.setForeground(Color.WHITE);
			button.setFocusable(false);
			numbersPanel.add(button);
		});
		
		operatorButtons.forEach((button) -> {
			button.setPreferredSize(new Dimension(60, 50));
			button.setFont(new Font("Oswald", Font.BOLD, 20));
			button.setBorderPainted(false);
			button.setBackground(new Color(0, 0, 0, 0));
			button.setForeground(Color.WHITE);
			button.setFocusable(false);
			operatorsPanel.add(button);
		});
		
		//--------INPUT-PANEL--------\\
	
		
		//--------BUTTON-EVENTS--------\\
		
		
		for (int i = 0; i < 10; i++) {
			final int index = i + 1;
			if (i == 9) {				
				numberButtons.get(i).addActionListener((e) -> calculation.setText(calculation.getText() + 0));
				break;
			}
			numberButtons.get(i).addActionListener((e) -> calculation.setText(calculation.getText() + index));
		}
		
		buttonClear.addActionListener((e) -> calculation.setText(null));
		
		char[] arithmeticSymbols = {'+', '-', '*', '/', '.'};
		
		for (int i = 0; i < 5; i++) {
			final char symbol = arithmeticSymbols[i];
			operatorButtons.get(i).addActionListener((e) -> calculation.setText(calculation.getText() + symbol));
		}
		
		buttonEqual.addActionListener((e) -> { 
			Stack<Character> operators = new Stack<>();
			Stack<Double> operands = new Stack<>();
			StringBuilder tokens = new StringBuilder(calculation.getText());
			StringBuffer tokensToBuffer = new StringBuffer();
			
			for (int i = 0; i < tokens.length(); i++) {
				if (Character.isDigit(tokens.charAt(i)) || tokens.charAt(i) == '.') {
					tokensToBuffer.append(tokens.charAt(i));
					if (i == tokens.length() - 1) {			
						String extractedTokens = tokensToBuffer.toString();
						operands.push(Double.parseDouble(extractedTokens));
						tokensToBuffer.delete(0, tokensToBuffer.length());
					}
				} else {
					operators.push(tokens.charAt(i));
					String extractedTokens = tokensToBuffer.toString();
					operands.push(Double.parseDouble(extractedTokens));
					tokensToBuffer.delete(0, tokensToBuffer.length());
				}
			}
			
			double solution = 0;
			
			while (!operands.isEmpty() && !operators.isEmpty()) { //ITO NAMAN TESTINGIN MO BOIIII
				char pop = operators.pop();
				switch (pop) {
					case '+' -> {
						double operand2 = operands.pop();
						solution = operands.pop() + operand2;
						operands.push(solution);
						}
					case '-' -> {
						double operand2 = operands.pop();
						solution = operands.pop() - operand2;
						operands.push(solution);
					}
					case '*' -> {
						double operand2 = operands.pop();
						solution = operands.pop() * operand2;
						operands.push(solution);			
					}
					case '/' -> {
						double operand2 = operands.pop();
						solution = operands.pop() / operand2;	
						operands.push(solution);
					}
				}
				if (operands.size() == 1) {
					solution = operands.pop();
				}
			}
			
			if (solution == 69.0) {
				answer.setText("DANIEL EUT ANDREA");
				calculation.setText(null);
			} else {
				answer.setText(Double.toString(solution));
				calculation.setText(null);
			}
	});
		
		calculation.setFocusable(true);
		
		calculation.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) { 
				char key = e.getKeyChar();
				switch (key) {
				case '1', '2', '3', '4', '5',
					 '6', '7', '8', '9', '0' -> calculation.setText(calculation.getText() + Character.getNumericValue(key));
				case '=', '-', '*', '/', '.' -> {
					if (key == '=') {
						key = '+';
					}
					calculation.setText(calculation.getText() + key);
				}
				case '\\' -> calculation.setText(null);
				}
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (calculation.getText().length() > 0) {
					calculation.setText(calculation.getText().substring(0, calculation.getText().length() - 1));
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Stack<Character> operators = new Stack<>();
					Stack<Double> operands = new Stack<>();
					StringBuilder tokens = new StringBuilder(calculation.getText());
					StringBuffer tokensToBuffer = new StringBuffer();
					
					for (int i = 0; i < tokens.length(); i++) {
						if (Character.isDigit(tokens.charAt(i)) || tokens.charAt(i) == '.') {
							tokensToBuffer.append(tokens.charAt(i));
							if (i == tokens.length() - 1) {			
								String extractedTokens = tokensToBuffer.toString();
								operands.push(Double.parseDouble(extractedTokens));
								tokensToBuffer.delete(0, tokensToBuffer.length());
							}
						} else {
							operators.push(tokens.charAt(i));
							String extractedTokens = tokensToBuffer.toString();
							operands.push(Double.parseDouble(extractedTokens));
							tokensToBuffer.delete(0, tokensToBuffer.length());
						}
					}
					
					double solution = 0;
					
					while (!operands.isEmpty() && !operators.isEmpty()) { //ITO NAMAN TESTINGIN MO BOIIII
						char pop = operators.pop();
						switch (pop) {
							case '+' -> {
								double operand2 = operands.pop();
								solution = operands.pop() + operand2;
								operands.push(solution);
								}
							case '-' -> {
								double operand2 = operands.pop();
								solution = operands.pop() - operand2;
								operands.push(solution);
							}
							case '*' -> {
								double operand2 = operands.pop();
								solution = operands.pop() * operand2;
								operands.push(solution);			
							}
							case '/' -> {
								double operand2 = operands.pop();
								solution = operands.pop() / operand2;	
								operands.push(solution);
							}
						}
						if (operands.size() == 1) {
							solution = operands.pop();
						}
					}
					
					if (solution == 69.0) {
						answer.setText("DANIEL EUT ANDREA");
						calculation.setText(null);
					} else {
						answer.setText(Double.toString(solution));
						calculation.setText(null);
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		//--------BUTTON-EVENTS--------\\
		
		
		mainPanel.add(outputPanel);
		mainPanel.add(spacePanel);
		mainPanel.add(inputPanel);
		
		frame.add(mainPanel);
		frame.pack();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
