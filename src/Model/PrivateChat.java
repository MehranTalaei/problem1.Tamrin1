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
}
