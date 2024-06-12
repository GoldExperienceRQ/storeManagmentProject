package org.example;


public class Message {
    private final int cType;
    private final int bUserId;
    private final int product_id;
    private final int price;
    private final int quantity;
    private final String message;
    private static byte[] key = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};

    public Message(Builder builder){
        this.cType = builder.cType;
        this.bUserId = builder.bUserId;
        this.product_id = builder.product_id;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.message = builder.message;
    }

    public int getcType(){
        return cType;
    }
    public int getbUserId(){
        return bUserId;
    }
    public int getProduct_id(){
        return product_id;
    }
    public String getText(){
        return message;
    }
    public int getPrice(){
        return price;
    }
    public int getQuantity(){
        return quantity;
    }
    public static byte[] getKey(){
        return key;
    }

    public static class Builder{

        private int cType;
        private int bUserId;
        private int product_id;
        private int price;
        private int quantity;
        private String message;

        public Builder( int bUserId, int cType, String message){
           this.cType = cType;
           this.bUserId = bUserId;
           this.message = message;
        }
        public Builder product_id(int product_id){
            this.product_id = product_id;
            return this;
        }
        public Builder price(int price){
            this.price = price;
            return this;
        }
        public Builder quantity(int quantity){
            this.quantity = quantity;
            return this;
        }
        public Message build(){
            return new Message(this);
        }
    }
}
