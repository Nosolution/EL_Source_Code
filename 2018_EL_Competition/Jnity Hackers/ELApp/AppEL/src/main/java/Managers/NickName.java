package Managers;

public class NickName {
    private String nickName;

    private NickName() {

    }

    private NickName(String string) {
        this.nickName = string;
    }

    public static NickName getNick(String string) {
        return new NickName(string);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
