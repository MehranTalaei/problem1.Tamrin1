package Model;

public class PrivateChat extends Chat{
    public PrivateChat(User owner, String id, String name) {
        super(owner, id, name);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getName() {
        return super.getName();
    }
    public String getPvId(User user) {
        if (getMembers().size() == 1) {
            return user.getId();
        }
        for (int i = 0; i < getMembers().size(); i++) {
            if (!getMembers().get(i).equals(user)) {
                return getMembers().get(i).getId();
            }
        }
        return null;
    }
    public String getPvName(User user) {
        if (getMembers().size() == 1) {
            return user.getName();
        }
        for (int i = 0; i < getMembers().size(); i++) {
            if (!getMembers().get(i).equals(user)) {
                return getMembers().get(i).getName();
            }
        }
        return null;
    }

    public User getSecond(User user) {
//        if (getMembers().size() == 1) {
//            return user;
//        }
//        for (int i = 0; i < getMembers().size(); i++) {
//            if (!getMembers().get(i).equals(user)) {
//                return getMembers().get(i);
//            }
 //       }
        return getOwner();
    }
}
