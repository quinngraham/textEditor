package org.textEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TextEdit extends JFrame implements ActionListener {

    private static JTextArea area;
    private static JFrame frame;
    private static int returnValue = 0;


    public TextEdit(){
        run();
    }


    public void run(){
        frame = new JFrame("Text Editor");

        //set the look and feel (?, LNF) of the application
        //for now i'm going to default to the host system
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException |UnsupportedLookAndFeelException ex){
            Logger.getLogger(TextEdit.class.getName()).log(Level.SEVERE, "Error setting the LNF", ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){

    }
}
