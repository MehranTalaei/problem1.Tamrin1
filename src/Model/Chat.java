package Model;

import java.util.ArrayList;

abstract public class Chat {
    private ArrayList<User> members = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private User owner;
    private String id;
    private String name;
    //private TypeOfChat type;
    public Chat(User owner, String id, String name) {
        this.owner = owner;
        this.id = id;
        this.name = name;
    }

    public void addMember(User user) {
        members.add(user);
        return;
    }

    public void addMessage(Message message) {
        messages.add(message);
        this.update();
        return;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public User getOwner() {
        return owner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getUserById(String id) {
        for (int i = 0; i < members.size(); i++) {
            User member = members.get(i);
            if (member.getId().equals(id)) {
                return member;
            }
        }
        return null;
    }

    private void update() {
        for (int i = 0; i < members.size(); i++) {
            members.get(i).updateChats(this);
        }
    }
}
