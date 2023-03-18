package Model;

import java.util.ArrayList;

public class User {
    private ArrayList<Chat> chats = new ArrayList<>();
    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<PrivateChat> privateChats = new ArrayList<>();
    private ArrayList<Channel> channels = new ArrayList<>();
    private String id;
    private String name;
    private String password;

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void addChat(Chat chat) {
        chats.add(chat);
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void addChannel(Channel channel) {
        channels.add(channel);

    }

    public void addPrivateChat(PrivateChat pv) {
        privateChats.add(pv);
    }

    public Group getGroupById(String id) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getId().equals(id)) {
                return groups.get(i);
            }
        }
        return null;
    }

    public Channel getChannelById(String id) {
        for (int i = 0; i < channels.size(); i++) {
            if (channels.get(i).getId().equals(id)) {
                return channels.get(i);
            }
        }
        return null;
    }

    public PrivateChat getChatById(String id) {
        for (int i = 0; i < privateChats.size(); i++) {
            if (privateChats.get(i).getId().equals(id)) {
                return privateChats.get(i);
            }
        }
        return null;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void updateChats(Chat chat) {
        chats.remove(chat);
        chats.add(chat);
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
