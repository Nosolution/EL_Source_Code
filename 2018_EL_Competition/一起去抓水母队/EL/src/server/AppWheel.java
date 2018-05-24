package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class AppWheel {
    HashMap<String, AppServer> appServers= new HashMap<>();
    HashMap<String, AppUser> appUsers = new HashMap<>();
    HashMap<String, AppRoom> appRooms = new HashMap<>();
    void load(String name, AppServer appServer, int port) {
        try {
            appServer.serverSocket = new ServerSocket(port);
            appServer.setAppWheel(this);
            appServers.put(name, appServer);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
