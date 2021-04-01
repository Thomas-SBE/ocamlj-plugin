package org.tsbe.camlj.console;

import java.awt.*;

public class NewLineAWTEvent extends AWTEvent {

    public static final int EVENT_ID = AWTEvent.RESERVED_ID_MAX + 8888;

    private String message;
    private int message_type = 0;

    public static int NORMAL = 0;
    public static int ERROR = 1;
    public static int HEADER = 2;

    public NewLineAWTEvent(CamlInterface source, String new_message, int message_type) {
        super(source, EVENT_ID);
        this.message = new_message;
        this.message_type = message_type;

    }

    public String getMessage(){
        return message;
    }

    public int getMessageType(){
        return message_type;
    }


}

