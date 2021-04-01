package org.tsbe.camlj.settings;

import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.sun.istack.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppSettingsComponent {

    private final JPanel mainPanel;
    private final JBTextField ocaml_fl = new JBTextField();
    private final JLabel info_text = new JLabel();
    private final JButton ocaml_fl_select_dir = new JButton("Browse for ocaml directory...");

    private final JFileChooser fc = new JFileChooser();

    public AppSettingsComponent(){

        ocaml_fl_select_dir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc.setDialogTitle("Open Ocaml Installation location");
                fc.setDialogType(JFileChooser.DIRECTORIES_ONLY);
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);
                int returnval = fc.showOpenDialog(mainPanel);
                if(returnval == JFileChooser.APPROVE_OPTION){
                    File dir = fc.getSelectedFile();
                    String path = dir.getPath();
                    if(Files.isDirectory(Paths.get(path + "\\bin")) && Files.isDirectory(Paths.get(path + "\\lib\\ocaml"))){
                        ocaml_fl.setText(path);
                        info_text.setText("Refresh the interpreter by clicking the red refresh icon in the toolbar.");
                    }else{
                        info_text.setText("Selected directory does not meet plugin requirements ...\\bin ...\\lib\\ocaml.");
                    }
                }
            }
        });

        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(info_text)
                .addLabeledComponent(new JLabel("Ocaml Installation Location : "), ocaml_fl, 1, false)
                .addComponent(ocaml_fl_select_dir)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public void setOcamlLocationText(@NotNull String location){
        ocaml_fl.setText(location);
    }

    public String getOcamlLocationText(){
        return ocaml_fl.getText();
    }

    public JComponent getPreferedFocusedComponent(){
        return ocaml_fl;
    }

    public JPanel getPanel(){
        return mainPanel;
    }

    public JButton getFetchDirButton(){
        return ocaml_fl_select_dir;
    }

}
