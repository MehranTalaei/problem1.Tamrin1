package Model;

import java.util.ArrayList;

public class Messenger {
    private static ArrayList<Channel> channels=new ArrayList<>();
    private static ArrayList<Group> groups=new ArrayList<>();
    private static ArrayList<User> users=new ArrayList<>();
    private static User currentUser;

    public static void addGroup(Group group) {
        groups.add(group);
        return;
    }

    public static void addChannel(Channel channel) {
        channels.add(channel);
        return;
    }

    public static void addUser(User user) {
        users.add(user);
        return;
    }

    public static Group getGroupById(String id) {
        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            if (group.getId().equals(id)) {
                return group;
            }
        }
        return null;
    }

    public static Channel getChannelById(String id) {
        for (int i = 0; i < channels.size(); i++) {
            Channel channel = channels.get(i);
            if (channel.getId().equals(id)) {
                return channel;
            }
        }
        return null;
    }

    public static PrivateChat getMemberById(String id) {

        return null;
    }

    public static ArrayList<Channel> getChannels() {
        return channels;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static User getUserById(String id) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

}
