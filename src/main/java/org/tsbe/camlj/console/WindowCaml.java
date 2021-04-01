package org.tsbe.camlj.console;

import com.intellij.application.options.colors.ColorAndFontDescription;
import com.intellij.application.options.colors.ColorSchemeActions;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.*;
import com.intellij.openapi.editor.colors.impl.AbstractColorsScheme;
import com.intellij.openapi.editor.colors.impl.EditorColorsSchemeImpl;
import com.intellij.openapi.editor.ex.util.EditorUIUtil;
import com.intellij.openapi.options.colors.ColorSettingsPages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBImageIcon;
import com.intellij.util.ui.UIUtil;
import groovyjarjarpicocli.CommandLine;
import org.tsbe.camlj.interfaces.NewLineListener;
import org.w3c.dom.Text;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowCaml implements NewLineListener {

    private JPanel container;
    private JTextPane sout;
    private JTextField sin;
    private JPanel sout_border;
    private JScrollPane scrollPane;
    private JButton button1;

    private EditorColorsScheme colorsScheme;

    private Thread icaml_t;

    private static WindowCaml INSTANCE;
    public static WindowCaml getInstance(){
        if(INSTANCE != null){ return INSTANCE; }
        else { INSTANCE = new WindowCaml(); return INSTANCE;}
    }

    public WindowCaml(){
        colorsScheme = EditorColorsManager.getInstance().getSchemeForCurrentUITheme();
        if(colorsScheme.equals(null))
        {
            colorsScheme = EditorColorsManager.getInstance().getScheme(EditorColorsManager.getInstance().getAllSchemes()[0].getName());
        }
        CamlInterface caml = CamlInterface.getInstance();
        sout.setFont(new Font("JetBrains Mono", Font.PLAIN, 12));
        sin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = sin.getText();
                sin.setText("");
                sin.grabFocus();
                addCommandLine(cmd);
                caml.sendCommand(cmd);
            }
        });
        Border b = new LineBorder(fetchIJColor("INLINE_PARAMETER_HINT", true), 1, false);
        sout_border.setBorder(b);
        sout_border.setBackground(fetchIJColor("INLINE_PARAMETER_HINT", true));
        sout.setBackground(fetchIJColor("INLINE_PARAMETER_HINT", true));
        scrollPane.setBackground(fetchIJColor("INLINE_PARAMETER_HINT", true));
        scrollPane.setBorder(new LineBorder(Color.BLACK, 0));
        sin.setBackground(fetchIJColor("INLINE_PARAMETER_HINT", true));
        sin.setBorder(new LineBorder(Color.BLACK, 0));
        caml.setListener(this);
        icaml_t = new Thread(CamlInterface.getInstance());
        icaml_t.start();

    }

    public void reloadCamlInterface(){
        appendToPanel("!#! Restarting core ...", fetchIJColor("DEFAULT_STRING", false), true);
        CamlInterface.getInstance().sendCommand("#quit;;");
        try{
            icaml_t.join();
        }catch (Exception ex){ex.printStackTrace();}
        icaml_t = new Thread(CamlInterface.getInstance());
        icaml_t.start();
    }

    public WindowCaml getSelf(){return this;}

    public JPanel getContent(){
        return container;
    }

    public void appendToPanel(String msg, Color color, boolean isBold) {
        StyledDocument doc = sout.getStyledDocument();
        Style style = sout.addStyle("ocaml_interpreter_style", null);
        style.addAttribute(StyleConstants.Bold, isBold);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), msg + "\r\n", style);
        } catch (BadLocationException e) {}
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }

    public void addLine(String s){
        appendToPanel(s, fetchIJColor("TEXT", false), false);
    }

    public void addHeaderLines(String s){
        appendToPanel(s, fetchIJColor("CONSOLE_GREEN_BRIGHT_OUTPUT", false), true);
        giveSpace(1);
    }

    public void addCommandLine(String s){
        addLine("# " + s);
    }

    public void addErrorLine(String s){
        appendToPanel(s, fetchIJColor("CONSOLE_RED_OUTPUT", false), true);
    }

    public void giveSpace(int lines){
        String f = "";
        for(int i = 0; i < lines; i++){
            f += "\r\n";
        }
        appendToPanel(f, Color.WHITE, false);
    }

    public void addStdoutLine(String s){
        if(s.startsWith("#")){
            s = s.replaceFirst("#", "");
            s = s.trim();
            appendToPanel(s, fetchIJColor("CONSOLE_MAGENTA_BRIGHT_OUTPUT", false), false);
        }else{
            appendToPanel(s, fetchIJColor("CONSOLE_MAGENTA_BRIGHT_OUTPUT", false), false);
        }
    }

    public Color fetchIJColor(String name, boolean isBackground){
        Color c = colorsScheme.getAttributes(TextAttributesKey.createTextAttributesKey(name)).getForegroundColor();
        if(c == null || isBackground){
            c = colorsScheme.getAttributes(TextAttributesKey.createTextAttributesKey(name)).getBackgroundColor();
        }
        if(c == null){
            c = colorsScheme.getColor(ColorKey.createColorKey(name));
        }
        return c;
    }

    public void clearConsole(){
        sout.setText("");
    }

    @Override
    public void nextLine(NewLineAWTEvent e) {
        switch (e.getMessageType()){
            case 0:
                addStdoutLine(e.getMessage()); break;
            case 1:
                addErrorLine(e.getMessage()); break;
            case 2:
                addHeaderLines(e.getMessage()); break;
        }
    }
}
