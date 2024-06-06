
package com.mycompany.texteditor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class TextEditor extends JFrame implements ActionListener {
    JTextArea area;
    JScrollPane pane;
    JCheckBoxMenuItem lineWrap;
    String text;
    JComboBox font;
    JSpinner fontsize;
    JButton button;
    Integer array[];
    String fontname[];
    UndoManager manager=new UndoManager(); 
    TextEditor(){
        setTitle("TextEditor");
        setBounds(0,0,1920,1080);
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem newdoc = new JMenuItem("New");
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        newdoc.addActionListener(this);
        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        open.addActionListener(this);
        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        save.addActionListener(this);
        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
        print.addActionListener(this);
        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0));
        exit.addActionListener(this);
        JMenuItem undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
        manager=new UndoManager();
        undo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    manager.undo();
                }
                catch(Exception ex){
                }
            
            }
        });
        area=new JTextArea();
        area.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
	    public void undoableEditHappened(UndoableEditEvent e) {
		manager.addEdit(e.getEdit());
	    }
	});
        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);
        file.add(undo);
        
        
        JMenu edit = new JMenu("Edit");
        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
        copy.addActionListener(this);
        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
        cut.addActionListener(this);
        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
        paste.addActionListener(this);
        JMenuItem selectall = new JMenuItem("Select All");
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
        selectall.addActionListener(this);
        edit.add(copy);
        edit.add(cut);
        edit.add(paste);
        edit.add(selectall);
        
        
        lineWrap= new JCheckBoxMenuItem("Word Wrap");
        //lineWrap.setPreferredSize(new Dimension(100,25));
        lineWrap.addActionListener(this);
        
        
        fontname=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        //array = new Integer[fontname.length];
       // for(int i=1;i<=fontname.length;i++) {
        //    array[i-1] = i;
       // }
        font = new JComboBox(fontname);
       // ComboBoxRenderer renderar = new ComboBoxRenderer();
        //font.setRenderer(renderar);
        font.addActionListener(this);
        //font.setSelectedItem("Arial");
        
        
        fontsize=new JSpinner();
        //fontsize.setPreferredSize(new Dimension(100,25));
        fontsize.setValue(20);
        fontsize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                area.setFont(new Font(area.getFont().getFamily(),Font.PLAIN,(int)fontsize.getValue()));
            }
        });
        
        button=new JButton("COLOR");
        button.addActionListener(this);
        
        menubar.add(file);
        menubar.add(edit);
        menubar.add(lineWrap);
        menubar.add(font);
        menubar.add(fontsize);
        menubar.add(button);
        setJMenuBar(menubar);
        
        
        area = new JTextArea();
        area.setFont(new Font("Arial",Font.PLAIN, 20));
        
        pane = new JScrollPane(area);
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane,BorderLayout.CENTER);
    }
        
  /*  public class ComboBoxRenderer extends JLabel implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            int offset = ((Integer)value).intValue() - 1;
            String name = fontname[offset];
            setText(name);
            setFont(new Font(name,Font.PLAIN,20));
            return this;
        }
    }*/
    
    
    
    public void actionPerformed(ActionEvent ae){
        
        
        if(ae.getActionCommand().equals("New")){
        }
        
        
        
        else if(ae.getActionCommand().equals("Open")){
            JFileChooser open = new JFileChooser();
            open.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter(".txt file","txt");
            open.addChoosableFileFilter(restrict);
            int action = open.showOpenDialog(this);
            if(action!=JFileChooser.APPROVE_OPTION){
                return;
            }
            File filename = open.getSelectedFile();
            BufferedReader reader = null;
            try{
                reader = new BufferedReader(new FileReader(filename));
                area.read(reader,null);
            }catch(Exception e){}
        }
        
        
        
        else if(ae.getActionCommand().equals("Save")){
            JFileChooser save = new JFileChooser();
            save.setApproveButtonText("Save");
            int action = save.showOpenDialog(this);
            if(action!=JFileChooser.APPROVE_OPTION){
                return;
            }
            File filename = new File(save.getSelectedFile()+".txt");
            BufferedWriter writer = null;
            try{
                writer = new BufferedWriter(new FileWriter(filename));
                area.write(writer);
            }catch(Exception e){}
        }
        
        else if(ae.getActionCommand().equals("Print")){
            try{
                area.print();
            }catch(Exception e){}
            
        }
        else if(ae.getActionCommand().equals("Exit")){
            System.exit(0);
            
        }
        /*else if(ae.getActionCommand().equals("Undo")){
            //String x=area.getText(1,area.getCaretPosition()-0);
            /*try {
                manager.undo();
            }
            catch (CannotUndoException ex) {
                ex.printStackTrace();
            }*
            area.replaceRange("",0,area.getCaretPosition());
            //area.replaceRange("",area.getCaretPosition()-1,area.getCaretPosition());
            
        }*/
        else if(ae.getActionCommand().equals("Copy")){
            text = area.getSelectedText();
        }
        else if(ae.getActionCommand().equals("Paste")){
            area.insert(text,area.getCaretPosition());
        }
        else if(ae.getActionCommand().equals("Cut")){
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(),area.getSelectionEnd());
            
        }
        else if(ae.getActionCommand().equals("Select All")){
            area.selectAll();
        }
        else if(ae.getActionCommand().equals("Word Wrap")){
            if(lineWrap.isSelected()){
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
            }
            else{
                area.setLineWrap(false);
                area.setWrapStyleWord(false);
            }
            
        }
        
        else if(ae.getSource()==font){
            area.setFont(new Font((String)font.getSelectedItem(),Font.PLAIN,area.getFont().getSize()));
        }
        
        else if(ae.getSource()==button){
            JColorChooser colorchooser=new JColorChooser();
            Color color = colorchooser.showDialog(null,"Choose a color",Color.black);
            
            area.setForeground(color);
            //String s=area.getSelectedText();
            
        }
        
    }
        
    public static void main(String[] args){
        new TextEditor().setVisible(true);
              
        
    }
}

    /*private void updateButtons() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
        
 /*JTextArea textArea = new JTextArea();
    JButton undo = new JButton("Undo");
    JButton redo = new JButton("Redo");
    KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_Z, Event.CTRL_MASK);
    KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_Y, Event.CTRL_MASK);

    UndoManager undoManager = new UndoManager();

    Document document = textArea.getDocument();
    document.addUndoableEditListener(new UndoableEditListener() {
        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            undoManager.addEdit(e.getEdit());
        }
    });

    // Add ActionListeners
    undo.addActionListener((ActionEvent e) -> {
        try {
            undoManager.undo();
        } catch (CannotUndoException cue) {}
    });
    redo.addActionListener((ActionEvent e) -> {
        try {
            undoManager.redo();
        } catch (CannotRedoException cre) {}*/   


