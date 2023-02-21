package org.textEditor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Optional;
import java.util.Scanner;
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

        //attribute settings
        area = new JTextArea();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(area);
        frame.setSize(640, 480);


        //making the dropdown menu, going to examine why it's ordered this way in the example
        JMenuBar menu_main = new JMenuBar();
        JMenu menu_file = new JMenu("File");
        JMenuItem menuitem_new = new JMenuItem("New");
        JMenuItem menuitem_open = new JMenuItem("Open");
        JMenuItem menuitem_save = new JMenuItem("Save");
        JMenuItem menuitem_quit = new JMenuItem("Quit");

        menuitem_new.addActionListener(this);
        menuitem_open.addActionListener(this);
        menuitem_save.addActionListener(this);
        menuitem_quit.addActionListener(this);

        menu_main.add(menu_file);

        menu_file.add(menuitem_new);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_save);
        menu_file.add(menuitem_quit);

        frame.setJMenuBar(menu_main);
        frame.setVisible(true);

    }

    /**
     * This is taking care of the menu stuff for now.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e){
        StringBuilder ingest = new StringBuilder();
        //may need to override the getFSV function
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose destination.");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        String ae = e.getActionCommand();


        switch(ae){
            case "Open" -> {
                returnValue = jfc.showOpenDialog(null);
                if(returnValue == JFileChooser.APPROVE_OPTION){
                    Optional<File> fh = fileHandler(jfc);
                    File f;
                    if(fh.isPresent()){
                        f = new File(fh.get().getAbsolutePath());
                    } else break;
                    try{
                        FileReader read = new FileReader(f);
                        Scanner scan = new Scanner(read);
                        String line;
                        while(scan.hasNextLine()){
                            line = scan.nextLine() + "\n";
                            ingest.append(line);
                        }
                        area.setText(ingest.toString());
                    } catch(FileNotFoundException ex){
                        ex.printStackTrace();
                    }
                }
            }
            case "Save" -> {
                returnValue = jfc.showSaveDialog(null);
                try{

                    Optional<File> fh = fileHandler(jfc);
                    File f;
                    if(fh.isPresent()){
                        f = new File(fh.get().getAbsolutePath());
                    } else break;
                    FileWriter out = new FileWriter(f);
                    out.write(area.getText());
                    out.close();
                } catch(FileNotFoundException ex){
                    Component f = null;
                    JOptionPane.showMessageDialog(f, "File not found.");
                } catch(IOException ex){
                    Component f = null;
                    JOptionPane.showMessageDialog(f, "Error in 'Save' in actionperformed.");
                }
            }
            case "New" -> area.setText("");
            case "Quit" -> System.exit(0);
            default -> {
            }
        }
    }

    /**
     * In case someone selects cancel on the file selector. (yes i realize there is a cancel int val i can use)
     * @param jFileChooser To open the dialogue box.
     * @return Either a null value (if cancel is selected) or the File.
     */
    private Optional<File> fileHandler(JFileChooser jFileChooser){
        return Optional.ofNullable(jFileChooser.getSelectedFile());
    }
}
