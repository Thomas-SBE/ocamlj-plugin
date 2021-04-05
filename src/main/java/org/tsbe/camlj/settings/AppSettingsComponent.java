package org.tsbe.camlj.settings;

import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.sun.istack.NotNull;
import org.apache.commons.lang.SystemUtils;
import org.tsbe.camlj.console.WindowCaml;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppSettingsComponent {

    private final JPanel mainPanel;
    private final JBTextField ocaml_fl = new JBTextField();
    private final JLabel info_text = new JLabel();
    private final JButton link_clickable = new JButton();
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

        link_clickable.setVisible(false);
        link_clickable.setEnabled(false);
        link_clickable.setBorderPainted(false);
        link_clickable.setContentAreaFilled(false);
        link_clickable.setForeground(WindowCaml.getInstance().fetchIJColor("FOLLOWED_HYPERLINK_ATTRIBUTES", false));

        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(info_text)
                .addComponent(link_clickable)
                .addLabeledComponent(new JLabel("Ocaml Installation Location : "), ocaml_fl, 1, false)
                .addComponent(ocaml_fl_select_dir)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

        if(!SystemUtils.IS_OS_WINDOWS){
            link_clickable.setVisible(true);
            link_clickable.setEnabled(true);
            link_clickable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            link_clickable.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{ Desktop.getDesktop().browse(new URI("https://thomas-sbe.github.io/ocamlj-plugin/installation/")); }
                    catch (Exception ex){}
                }
            });
            info_text.setText("<html>You are under Non-Windows Operating System, it is not required to define where OCaml is installed.<br>For more informations, see :</html>");
            link_clickable.setText("https://thomas-sbe.github.io/ocamlj-plugin/installation/");
            ocaml_fl.setEnabled(false);
            ocaml_fl_select_dir.setEnabled(false);
        }else{
            link_clickable.setVisible(true);
            link_clickable.setEnabled(true);
            link_clickable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            link_clickable.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{ Desktop.getDesktop().browse(new URI("https://github.com/Thomas-SBE/ocamlj-plugin#readme")); }
                    catch (Exception ex){}
                }
            });
            info_text.setText("<html>For setting up instructions, you can read the documentation here :</html>");
            link_clickable.setText("https://thomas-sbe.github.io/ocamlj-plugin/installation/");
        }
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
