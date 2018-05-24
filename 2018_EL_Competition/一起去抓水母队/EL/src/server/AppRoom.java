package server;

import java.io.IOException;
import java.util.ArrayList;

public class AppRoom {
    ArrayList<AppUser> members = new ArrayList<>();
    AppRoom(AppUser appUser) {
        members.add(appUser);
    }
    void tellMembers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder("members");
        for (AppUser appUser : members)
            stringBuilder.append(" ").append(appUser.name);
        for (AppUser appUser : members) {
            System.out.println(appUser.name);
            appUser.dataOutputStream.writeUTF(stringBuilder.toString());
        }
    }
    void tellLesson(String lesson) throws IOException {
        for (AppUser appUser : members)
            appUser.dataOutputStream.writeUTF("lesson " + lesson);
    }
}
