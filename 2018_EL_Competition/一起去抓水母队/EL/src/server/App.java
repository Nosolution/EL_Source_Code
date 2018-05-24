package server;

public class App {
    public static void main(String[] args) {
        AppWheel appWheel = new AppWheel();

        appWheel.load("SigninServer", new SigninServer(), 23330);
        appWheel.load("SchoolServer", new SchoolServer() ,23331);
        appWheel.load("LoginServer", new LoginServer(), 23332);

        appWheel.load("EstablishmentServer", new EstablishmentServer(), 23333);
        appWheel.load("LessonServer", new LessonServer(), 23334);
        appWheel.load("DismissionServer", new DismissionServer(), 23335);
        appWheel.load("DissolutionServer", new DissolutionServer(), 23336);
        appWheel.load("ParticipationServer", new ParticipationServer(), 23337);
        appWheel.load("LeaveServer", new LeaveServer(), 23338);

        appWheel.load("RecordServer", new RecordServer(), 23339);

        new Thread((LoginServer) appWheel.appServers.get("LoginServer")).start();
        new Thread((SchoolServer) appWheel.appServers.get("SchoolServer")).start();
        new Thread((SigninServer) appWheel.appServers.get("SigninServer")).start();

        new Thread((EstablishmentServer) appWheel.appServers.get("EstablishmentServer")).start();
        new Thread((LessonServer) appWheel.appServers.get("LessonServer")).start();
        new Thread((DismissionServer) appWheel.appServers.get("DismissionServer")).start();
        new Thread((DissolutionServer) appWheel.appServers.get("DissolutionServer")).start();
        new Thread((ParticipationServer) appWheel.appServers.get("ParticipationServer")).start();
        new Thread((LeaveServer) appWheel.appServers.get("LeaveServer")).start();

        new Thread((RecordServer) appWheel.appServers.get("RecordServer")).start();
    }
}
