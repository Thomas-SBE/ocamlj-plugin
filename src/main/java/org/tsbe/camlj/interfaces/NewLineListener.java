package org.tsbe.camlj.interfaces;

import org.tsbe.camlj.console.NewLineAWTEvent;

import java.util.EventListener;

public interface NewLineListener extends EventListener {

    public void nextLine(NewLineAWTEvent e);

}
