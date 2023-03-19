package ViewAndControler;

import Model.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatMenu {
    Chat chat;
    User currentUser;

    private static Pattern sendMessageCommand = Pattern.compile("send a message c (?<message>.+)");
    private static Pattern addMemberCommand = Pattern.compile("add member i (?<id>\\S+)\\s*");
    private static Pattern showMessagesCommand = Pattern.compile("show all messages");
    private static Pattern showMembersCommand = Pattern.compile("show all members");
    private static Pattern back = Pattern.compile("back");

    public void run(Scanner scanner,Chat chat) {
        //Scanner scanner = new Scanner(System.in);
        String command=scanner.nextLine();
        if (command.matches(sendMessageCommand.pattern())) {
            System.out.println(sendMessage(sendMessageCommand.matcher(command)));
            this.run(scanner,chat);
        }
        else if (command.matches(addMemberCommand.pattern())) {
            System.out.println(addMember(addMemberCommand.matcher(command)));
            this.run(scanner,chat);
        }
        else if (command.matches(showMessagesCommand.pattern())) {
            System.out.println(showMessages());
            this.run(scanner,chat);
        }
        else if (command.matches(showMembersCommand.pattern())) {
            System.out.println(showMembers());
            this.run(scanner,chat);
        }
        else if (command.matches(back.pattern())) {
            MessageMenu messageMenu = new MessageMenu();
            messageMenu.setCurrentUser(currentUser);
            messageMenu.run(scanner);
        }
        else {
            System.out.println("Invalid command!");
            this.run(scanner,chat);
        }
        return;
    }

    private String showMessages() {
        String ans = new String();
        ans += "messages:";
        for (Message message : chat.getMessages()) {
            String buff = "\n"+message.getOwner().getName() + "(" + message.getOwner().getId() + "): \"" + message.getContent() + "\"";
            ans+=buff;
        }
        return ans;
    }

    private String showMembers() {
        if (chat instanceof PrivateChat) {
            return "Invalid command!";
        }
        String ans = new String();
        ans += "Members:";
        for (int i = 0; i < chat.getMembers().size(); i++) {
            User member = chat.getMembers().get(i);
            String buff = "\nname: " + member.getName() + ", id: " + member.getId();
            if (chat.getOwner().equals(member)) {
                buff += " *owner";
            }
            ans += buff;
        }
        return ans;
    }

    private String addMember(Matcher matcher) {
        matcher.find();
        String id = matcher.group("id");
        if (chat instanceof PrivateChat) {
            return "Invalid command!";
        } else if (currentUser != chat.getOwner()) {
            return "You don't have access to add a member!";
        } else if (Messenger.getUserById(id) == null) {
            return "No user with this id exists!";
        } else if (chat.getUserById(id) != null) {
            return "This user is already in the chat!";
        } else {
            User target = Messenger.getUserById(id);
            if (chat instanceof Group) {
                Message message=new Message(currentUser,target.getName()+" has been added to the group!");
                chat.addMessage(message);
            }
            target.addChat(chat);
            if (chat instanceof Group) {
                target.addGroup((Group) chat);
            } else if (chat instanceof Channel) {
                target.addChannel((Channel) chat);
            }
            chat.addMember(target);
            return "User has been added successfully!";
        }
    }

    private String sendMessage(Matcher matcher) {
        matcher.find();
        String MessageContent = matcher.group("message");
        if ((chat instanceof Channel) && (!chat.getOwner().equals(currentUser))) {
            return "You don't have access to send a message!";
        } else {
            Message newMessage = new Message(currentUser, MessageContent);
            chat.addMessage(newMessage);
            return "Message has been sent successfully!";
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
