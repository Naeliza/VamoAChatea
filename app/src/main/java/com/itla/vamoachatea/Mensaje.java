package com.itla.vamoachatea;

public class Mensaje {
    private String sender;
    private String receiver;
    private String content;

    public Mensaje(){

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContet() {
        return content;
    }

    public void setContet(String contet) {
        this.content = contet;
    }

    public Mensaje(String sender, String receiver, String contet) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = contet;
    }
}
