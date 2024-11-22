package org.example.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author almacro
 */
public class CaptureUI {    
    private static final int FIELD_WIDTH = 20;
    private static JTextField staticTextField;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        
        staticTextField = new JTextField(FIELD_WIDTH);
        staticTextField.setText("Static field");
        
        JTextField localTextField = new JTextField(FIELD_WIDTH);
        localTextField.setText("Local variable");
        
        JButton helloButton = new JButton("Say hello");
        
        // use a regular anonymous class
        helloButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                staticTextField.setText("Hello, world!");
                localTextField.setText("Hello, world!");
            }
        });
        
        // use a lambda block
        JButton goodbyeButton = new JButton("Say Goodbye");
        goodbyeButton.addActionListener( event -> {
            staticTextField.setText("Goodbye, world!");
            localTextField.setText("Goodbye, world!");
        });
        ;
        
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(staticTextField);
        contentPane.add(localTextField);
        contentPane.add(helloButton);
        contentPane.add(goodbyeButton);
        
        // staticTextField = null;
        // localTextField = null;
        
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
