package org.tsbe.camlj.console;


import org.apache.commons.lang.SystemUtils;
import org.tsbe.camlj.exception.OcamlJInterruptedException;
import org.tsbe.camlj.interfaces.NewLineListener;
import org.tsbe.camlj.settings.AppSettingsState;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class CamlInterface extends Component implements Runnable {

    private String camlHeader = "";
    private Process process = null;

    private BufferedWriter stdin;
    private BufferedReader stdout;

    public boolean running = true;

    private NewLineListener listener;
    private static EventQueue eq;

    private static CamlInterface INSTANCE;

    private boolean isFileRead = false;

    public void nextInIsRead(){isFileRead = true;}

    public static CamlInterface getInstance(){
        if(INSTANCE != null)
            return INSTANCE;
        else
        {INSTANCE = new CamlInterface(); return INSTANCE;}
    }

    private void defineCamlHeader(String h){
        this.camlHeader = h.trim();
    }

    public String getCamlHeader(){return camlHeader;}

    public CamlInterface(){
        INSTANCE = this;
        eq = Toolkit.getDefaultToolkit().getSystemEventQueue();
        enableEvents(0);
        WindowCaml.getInstance().addHeaderLines("Caml Core Loaded");
    }


    public void reload(){
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                AppSettingsState settings = AppSettingsState.getInstance();
                process = Runtime.getRuntime().exec(settings.OcamlLocation + "\\bin\\ocaml.exe -I \"" + settings.OcamlLocation + "\\lib\\ocaml\"");
            } else {
                process = Runtime.getRuntime().exec("ocaml");
            }
            stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            WindowCaml.getInstance().clearConsole();
        }catch (IOException e){
            WindowCaml.getInstance().addErrorLine("The plugin cannot find a suitable ocaml installation, check the settings of the plugin under : Tools -> OCamlJ.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void free(){
        try{
            stdin.flush();
            stdin.close();
            stdout.close();
            process.destroyForcibly();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        reload();
        if(process == null){return;}
        Scanner scanner = null;
        try {
            Thread.sleep(1000);
            scanner = new Scanner(stdout);
            String line;
            boolean isHeader = true;
            boolean isError = false;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if(!running){throw new OcamlJInterruptedException();}
                if (isHeader && !isFileRead) {
                    isHeader = false;
                    defineCamlHeader(line);
                    eq.postEvent(new NewLineAWTEvent(this, line, NewLineAWTEvent.HEADER));
                    continue;
                }
                if (line.startsWith("#")) {
                    isError = false;
                }
                if (line.startsWith("Error: ") || isError) {
                    isError = true;
                    eq.postEvent(new NewLineAWTEvent(this, line, NewLineAWTEvent.ERROR));
                } else {
                    eq.postEvent(new NewLineAWTEvent(this, line, NewLineAWTEvent.NORMAL));
                }
            }
        }catch (OcamlJInterruptedException e) {
            eq.postEvent(new NewLineAWTEvent(this, "!#! Core has stopped.", NewLineAWTEvent.ERROR));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            scanner.close();
            free();
            isFileRead = false;
        }
    }

    public void sendCommand(String command){
        try{
            command = command.trim();
            stdin.write(command);
            stdin.newLine();
            stdin.flush();
        }catch (Exception e){
            WindowCaml.getInstance().addErrorLine("!#! Could not send to process !");
            WindowCaml.getInstance().addErrorLine(e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendCommandWithAction(String command){
        WindowCaml.getInstance().addCommandLine(command);
        sendCommand(command);
    }

    public void processEvent(AWTEvent evt) {
        if (evt instanceof NewLineAWTEvent) {
            if (listener != null) {
                listener.nextLine((NewLineAWTEvent) evt);
            }
        } else {
            super.processEvent(evt);
        }
    }

    public void setListener(NewLineListener listener) {
        this.listener = listener;
    }
}
