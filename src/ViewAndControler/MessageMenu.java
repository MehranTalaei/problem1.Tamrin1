package ViewAndControler;

import Model.*;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Name;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageMenu {
    private Chat chat;
    private User currentUser;

    private static Pattern showAllChannelsCommand = Pattern.compile("show all channels");
    private static Pattern createChannelCommand = Pattern.compile("create new channel\\s+i\\s+(?<id>\\S+)\\s+n\\s+(?<name>\\S+)\\s*");
    private static Pattern joinChannelCommand = Pattern.compile("join channel\\s+i\\s+(?<id>\\S+)\\s*");
    private static Pattern showChatsCommand = Pattern.compile("show my chats");
    private static Pattern createGropuCommand = Pattern.compile("create new group i (?<id>\\S+)\\s+n\\s+(?<name>\\S+)\\s*");
    private static Pattern startPvCommand = Pattern.compile("start a new private chat with i (?<id>\\S+)");
    private static Pattern logout = Pattern.compile("logout");
    private static Pattern enterChatCommand = Pattern.compile("enter\\s+(?<chatType>\\S+)\\s+i\\s+(?<id>\\S+)\\s*");
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        if (command.matches(showAllChannelsCommand.pattern())) {
            System.out.println(showAllChannels());
            this.run();
        }
        else if (command.matches(createChannelCommand.pattern())) {
            System.out.println(createChannel(createChannelCommand.matcher(command)));
            this.run();
        }
        else if (command.matches(joinChannelCommand.pattern())) {
            System.out.println(joinChannel(joinChannelCommand.matcher(command)));
            this.run();
        }
        else if (command.matches(showChatsCommand.pattern())) {
            System.out.println(showChats());
            this.run();
        }
        else if (command.matches(createGropuCommand.pattern())) {
            System.out.println(createGroup(createGropuCommand.matcher(command)));
            this.run();
        }
        else if (command.matches(startPvCommand.pattern())) {
            System.out.println(createPrivateChat(startPvCommand.matcher(command)));
            this.run();
        }
        else if (command.matches(logout.pattern())) {
            System.out.println("Logged out");
            LoginMenu loginMenu = new LoginMenu();
            loginMenu.run();
        }
        else if (command.matches(enterChatCommand.pattern())) {
            String output=enterChat(enterChatCommand.matcher(command));
            System.out.println(output);
            if (output.matches("You have successfully entered the chat!")) {
                ChatMenu chatMenu=new ChatMenu();
                chatMenu.setCurrentUser(currentUser);
                //////
                Matcher mat=enterChatCommand.matcher(command);
                mat.find();
                String id = mat.group("id");
                String name = mat.group("chatType");
                if (name.equalsIgnoreCase("private chat")) {
                    name = "privateChat";
                }
                for (int i = 0; i < currentUser.getChats().size() - 1; i++) {
                    Chat currentUserChat = currentUser.getChats().get(i);
                    if (currentUserChat.getClass().getSimpleName().equalsIgnoreCase(name)) {
                        if (currentUserChat.getId().equals(id)) {
                            chatMenu.setChat(currentUser.getChats().get(i));
                        }
                    }
                }
                //////
                chatMenu.run();
            } else {
                this.run();
            }
        }
        else {
            System.out.println("Invalid command!");
            this.run();
        }
        return;
    }

    private String showAllChannels() {
        String ans = new String();
        ans += "All channels:";
        for (int i = 0; i < Messenger.getChannels().size(); i++) {
            Channel channel=Messenger.getChannels().get(i);
            String buff = "\n" + (i + 1) + ". " + channel.getName() + ", id: " + channel.getId() + ", members: " + channel.getMembers().size();
            ans+=buff;
        }
        return ans;
    }

    private String showChats() {
        String ans = new String();
        ans += "Chats:";
        int counter=1;
        for (int i = currentUser.getChats().size() - 1; i >= 0; i--) {
            Chat chat = currentUser.getChats().get(i);
            String type;
            if (chat instanceof Group) {
                type = "group";
            } else if (chat instanceof PrivateChat) {
                type = "private chat";
            } else if (chat instanceof Channel) {
                type = "channel";
            } else {
                type = new String();
            }
            String buff="\n"+counter+". "+chat.getName()+", id: "+chat.getId()+", "+type;
            ans+=buff;
            counter++;
        }
        return ans;
    }

    private String enterChat(Matcher matcher) {
        matcher.find();
        String chatType = matcher.group("chatType");
        String id = matcher.group("id");
        boolean flag=true;
        if (chatType.equalsIgnoreCase("private chat")) {
            chatType = "privateChat";
        }
        for (Chat currentUserChat : currentUser.getChats()) {
            if (currentUserChat.getId().equals(id)) {
                if (currentUserChat.getClass().getSimpleName().equalsIgnoreCase(chatType)) {
                    flag=false;
                }
            }
        }
        if (flag) {
            return "You have no " + chatType + " with this id!";
        } else {
            return "You have successfully entered the chat!";
        }
    }

    private String createChannel(Matcher matcher) {
        matcher.find();
        String id = matcher.group("id");
        String name = matcher.group("name");
        if (!name.matches("\\w+")) {
            return "Channel name's format is invalid!";
        } else if (Messenger.getChannelById(id) != null) {
            return "A channel with this id already exists!";
        } else {
            Channel newChannel = new Channel(currentUser, id, name);
            newChannel.addMember(currentUser);
            currentUser.addChat(newChannel);
            currentUser.addChannel(newChannel);
            Messenger.addChannel(newChannel);
            return "Channel " + name + " has been created successfully!";
        }
    }

    private String createGroup(Matcher matcher) {
        matcher.find();
        String id = matcher.group("id");
        String name = matcher.group("name");
        if (!name.matches("\\w+")) {
            return "Group name's format is invalid!";
        } else if (Messenger.getGroupById(id) != null) {
            return "A group with this id already exists!";
        } else {
            Group newGroup = new Group(currentUser, id, name);
            newGroup.addMember(currentUser);
            currentUser.addGroup(newGroup);
            currentUser.addChat(newGroup);
            Messenger.addGroup(newGroup);
            return "Group "+name+" has been created successfully!";
        }
    }

    private String createPrivateChat(Matcher matcher) {
        matcher.find();
        String id = matcher.group("id");
        if (currentUser.getChatById(id) != null) {
            return "You already have a private chat with this user!";
        } else if (Messenger.getUserById(id) == null) {
            return "No user with this id exists!";
        } else {
            User second = Messenger.getUserById(id);
            if (second.equals(currentUser)) {
                PrivateChat pv = new PrivateChat(currentUser, id, currentUser.getName());/////////
                currentUser.addPrivateChat(pv);
                pv.addMember(currentUser);
            } else {
                PrivateChat pv1 = new PrivateChat(currentUser, id, second.getName());
                PrivateChat pv2 = new PrivateChat(second, currentUser.getId(), currentUser.getName());
                second.addPrivateChat(pv1);
                currentUser.addChat(pv2);
                pv2.addMember(second);
                pv2.addMember(currentUser);
                pv1.addMember(currentUser);
                pv1.addMember(second);
            }
            String name=second.getName();
            return "Private chat with "+ name +" has been started successfully!";
        }
    }

    private String joinChannel(Matcher matcher) {
        matcher.find();
        String id = matcher.group("id");
        if (currentUser.getChannelById(id) != null) {
            return "You're already a member of this channel!";
        } else if (Messenger.getChannelById(id) == null) {
            return "No channel with this id exists!";
        } else {
            Channel target = Messenger.getChannelById(id);
            target.addMember(currentUser);
            currentUser.addChannel(target);
            currentUser.addChat(target);
            return "You have successfully joined the channel!";
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
