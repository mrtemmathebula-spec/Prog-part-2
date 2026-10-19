package com.mycompany.loginandregistration;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private static int totalMessages = 0;

    public static ArrayList<Message> sentMessages = new ArrayList<>();
    public static ArrayList<Message> storedMessages =  new ArrayList<>();
    public static ArrayList<Message> disregardedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();

    public static ArrayList<String> messageIDs =  new ArrayList<>();

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    public Message(int messageNumber, String recipient, String message) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;

        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {

        Random random = new Random();
        long number = 1000000000L + (long)(random.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }

    public boolean checkMessageID() {

        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {

        if(recipient.startsWith("+27") && recipient.length() <= 12) {

            return "Cell phone number successfully captured.";
        }

        return "Cell phone number incorrectly formatted or does not contain international code.";
    }

    public String createMessageHash() {

        String[] words = message.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        return ( messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String sentMessage() {

        Scanner input = new Scanner(System.in);

        System.out.println("""
                
                Choose an option:
                
                1) Send Message
                2) Disregard Message
                3) Store Message
                """);

        System.out.print("Enter choice: ");

        int choice = Integer.parseInt( input.nextLine());

        switch(choice) {

            case 1:
                totalMessages++;
                sentMessages.add(this);
                messageIDs.add(messageID);
                messageHashes.add(messageHash);
                
                return "Message successfully sent.";
               

            case 2:
                disregardedMessages.add(this);
                return "Message disregarded.";

            case 3:
                storedMessages.add(this);
                messageIDs.add(messageID);
                messageHashes.add(messageHash);
                storeMessage();
                return "Message successfully stored.";

            default:

                return "No valid option selected.";
        }
    }

    public String printMessages() {
        return "Message ID: " + messageID + "\nMessage Hash: " + messageHash + "\nRecipient: "  + recipient + "\nMessage: " + message;
    }

    public static int returnTotalMessages() {

        return totalMessages;
    }

    public void storeMessage() {

        try {

            FileWriter writer =  new FileWriter(  "storedMessages.json",true);

            writer.write(
                    "{\n" +
                    "\"MessageID\":\"" + messageID + "\",\n" +
                    "\"MessageHash\":\"" + messageHash +"\",\n" +
                    "\"Recipient\":\"" + recipient + "\",\n" +
                    "\"Message\":\"" + message + "\"\n" +
                    "}\n\n"
            );

            writer.close();

            System.out.println("Message stored successfully.");

        }
        catch(IOException e) {

            System.out.println( "Error storing message.");
        }
    }

 

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }
}