/*
 * Name: Jason Der
 * Description: A javax swing implementation of very basic RPN calculator
 */

 //import the necessary classes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Stack;

public class Calculator implements ActionListener{

    JFrame frame;
    JTextField textField;
    JButton[] numberButtons = new JButton[10];
    JButton[] operationButtons = new JButton[10];
    JButton enterButton, clearButton, signButton, powerButton;
    JButton additionButton, subtractionButton, multiplicationButton, divisionButton, decimalButton, deleteButton;
    JPanel panel;
    Stack <Double> stack;

    Font font = new Font("Courier New", Font.BOLD, 12);

    boolean decimalTyped = false, negative = false, on = true;
    final int MAX_STACK_SIZE = 9;

    // constructor that instanciates all elements of the calculator including buttons
    Calculator(){
        stack = new Stack<Double>();

        frame = new JFrame("Calculator");
        frame.setLayout(null);
        frame.setSize(400, 550);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        textField = new JTextField();
        textField.setEditable(false);
        textField.setFont(font);
        textField.setBounds(25, 25, 350, 50);

        // create and instanciate all the necessary buttons
        additionButton = new JButton("+");
        subtractionButton = new JButton("-");
        multiplicationButton = new JButton("x");
        divisionButton = new JButton("÷");
        deleteButton = new JButton("←");
        decimalButton = new JButton(".");

        enterButton = new JButton("ENTER");
        clearButton = new JButton("CLEAR");
        signButton = new JButton("CHS");
        powerButton = new JButton("PWR");

        // add the operation buttons into the operationButtons array
        operationButtons[0] = additionButton;
        operationButtons[1] = subtractionButton;
        operationButtons[2] = multiplicationButton;
        operationButtons[3] = divisionButton;
        operationButtons[4] = deleteButton;
        operationButtons[5] = decimalButton;
        operationButtons[6] = enterButton;
        operationButtons[7] = clearButton;
        operationButtons[8] = signButton;
        operationButtons[9] = powerButton;

        // setup the operation buttons
        for (int i = 0; i < 10; i++){
            operationButtons[i].addActionListener(this);
            operationButtons[i].setFont(font);
            operationButtons[i].setFocusable(false);
        }

        // add the number buttons into the numberButtons array and setup the buttons
        for (int j = 0; j < 10; j++){
            numberButtons[j] = new JButton(String.valueOf(j));
            numberButtons[j].addActionListener(this);
            numberButtons[j].setFont(font);
            numberButtons[j].setFocusable(false);
        }

        panel = new JPanel();
        panel.setBounds(25, 100, 350, 400);
        panel.setLayout(new GridLayout(5,4,10, 10));

        
        for (int i = 6; i < 10; i ++){
            panel.add(operationButtons[i]);
        }
        
        // add buttons to the panel
        panel.add(subtractionButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);

        panel.add(additionButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);

        panel.add(multiplicationButton);
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);

        panel.add(divisionButton);
        panel.add(numberButtons[0]);
        panel.add(decimalButton);
        panel.add(deleteButton);

        // add the textField and the panel
        frame.add(textField);
        frame.add(panel);

    }

    public static void main(String[] args) throws Exception {
        Calculator calc = new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int length = textField.getText().length();
        Double num1 = 0.0, num2 = 0.0, result = 0.0;
        
        for (int i = 0; i < 10; i ++){
            if (e.getSource() == numberButtons[i]){
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }

        if (e.getSource() == decimalButton){
            if (!decimalTyped){
                textField.setText(textField.getText().concat("."));
                decimalTyped = true;
            }
        }

        // will clear the textField
        if (e.getSource() == clearButton){
            textField.setText("");
            decimalTyped = false;
        }

        // changes the sign of input value in textField
        if (e.getSource() == signButton){

            if (!negative){
                textField.setText("-" + textField.getText());
                negative = true;
            }
            else{
                try{
                    textField.setText(textField.getText(1,length - 1));
                }catch (Exception p){
                    System.out.println("It would appear there is an error with changing the sign.");

                }
                negative = false;
            }
        }
         
        // will delete the last digit inputted
        if (e.getSource() == deleteButton){
            if (length > 0){
                try{

                    if (textField.getText(length-1, 1).equals(".")){
                        decimalTyped = false;

                    }
                    textField.setText(textField.getText(0,length - 1));
                }catch (Exception p){
                    System.out.println("It would appear there is an error with deleting.");
                }
            }
        }

        // will push the current value in the textField into the stack (max stack size is 10)
        if (e.getSource() == enterButton){
            if (stack.size() > MAX_STACK_SIZE){
                textField.setText("Stack Full");
                
            } else {
                stack.push(Double.valueOf(textField.getText()));
                textField.setText("");
                decimalTyped = false;
            }
        }
        
        // will check for which operator button is pressed and perform and return the result to the textField
        for (int j = 0; j < 4; j ++){
            if (e.getSource() == operationButtons[j]){

                try{
                    num2 = stack.pop();
                    num1 = stack.pop();

                } catch (Exception p){
                    System.out.println("There is an error popping!");
                }
 
                if (e.getSource() == additionButton){
                    result = num1 + num2; 
                }
        
                if (e.getSource() == subtractionButton){
                    result = num1 - num2;
                }
        
                if (e.getSource() == multiplicationButton){
                    result = num1 * num2;
                }
        
                if (e.getSource() == divisionButton){
                    result = num1 / num2;
                }
                textField.setText(String.valueOf(result));
            }
        }

        // will simply toggle the visibility of the textField
        if (e.getSource() == powerButton){
            if (on){
                textField.setVisible(false);
                on = false;
            } else {
                textField.setVisible(true);
                on = true;
            }
        }
    
    }

}
