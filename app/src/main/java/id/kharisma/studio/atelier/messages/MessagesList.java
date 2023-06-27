package id.kharisma.studio.atelier.messages;

public class MessagesList {
    private String name, mobile, lastMessage, chatKey;
    private int unseenMessages;

    public MessagesList(String name, String mobile, String lastMessage, int unseenMessages, String chatKey){
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
        this.chatKey = chatKey;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public String getChatKey() {
        return chatKey;
    }
}
