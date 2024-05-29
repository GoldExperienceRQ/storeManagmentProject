package org.example;


public class Message {
    private int cType;
    private int bUserId;
    private final String message;
    private static byte[] key = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};

    public Message(int cType, int bUserId, String message){
        this.cType = cType;
        this.bUserId = bUserId;
        this.message = message;
    }

    public int getcType(){
        return cType;
    }
    public int getbUserId(){
        return bUserId;
    }
    public String getText(){
        return message;
    }
    public static byte[] getKey(){
        return key;
    }

}
