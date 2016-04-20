package de.htwg.sa.nmm.statistics;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;
import de.htwg.sa.nmm.model.IGamefield;
import scala.collection.mutable.ArraySeq;

public class Master extends UntypedActor {

    private ActorRef workerRouter;

    public Master() {
        workerRouter = getContext().actorOf(Worker.createWorker()
                                                  .withRouter(new RoundRobinRouter(3)), "workerRouter");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof IGamefield) {
            handleGamefieldMessage((IGamefield) message);
        } else if (message instanceof Result) {
            handleResultMessage((Result)message);
        } else {
            unhandled(message);
        }
    }

    private void handleResultMessage(Result message) {
        int grid = message.grid;
        boolean mill = message.mill;
        System.out.println("Mill on grid " + Integer.toString(grid) + ": " + Boolean.toString(mill));
    }

    private void handleGamefieldMessage(IGamefield message) {
        for (int g = 0; g < message.grids(); g++) {
            Work work = new Work(message, g);
            workerRouter.tell(work, getSelf());
        }
    }

    public static Props createMaster() {
        return Props.create(Master.class, new ArraySeq<>(0));
    }
}
