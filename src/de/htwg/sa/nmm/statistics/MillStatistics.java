package de.htwg.sa.nmm.statistics;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import de.htwg.sa.nmm.model.IGamefield;

public class MillStatistics {


    ActorSystem actorSystem = ActorSystem.create("millStatistics");
    ActorRef master = actorSystem.actorOf(Master.createMaster(), "master");

    public void checkStatistics(IGamefield gamefield) {
        master.tell(gamefield, ActorRef.noSender());
    }

}
