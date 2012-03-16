package com.demo.client;
 
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
 
public class Gwtapp implements EntryPoint {
 
    public void onModuleLoad() {
        Button button = new Button("Send", new ClickHandler() {
            public void onClick(ClickEvent event) {
                Window.alert("Hello World!");
            }
        });
        button.setStyleName("sendButton");
        RootPanel.get("sendButtonContainer").add(button);
    }
}