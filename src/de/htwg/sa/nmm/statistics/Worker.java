package de.htwg.sa.nmm.statistics;

import akka.actor.Props;
import akka.actor.UntypedActor;
import de.htwg.sa.nmm.model.IField;
import de.htwg.sa.nmm.model.IGamefield;
import scala.collection.mutable.ArraySeq;

public class Worker extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Work) {
            Work work = (Work) message;
            Result result = checkForMill(work);
            getSender().tell(result, getSelf());
        } else {
            unhandled(message);
        }
    }

    private Result checkForMill(Work message) {
        IGamefield gamefield = message.gamefield;
        int grid = message.grid;
        for (int index = 0; index < gamefield.index(); index++) {
            IField field = gamefield.field(grid, index);
            if (gamefield.mill(field)) {
                return new Result(grid, true);
            }
        }
        return new Result(grid, false);
    }

    public static Props createWorker() {
        return Props.create(Worker.class, new ArraySeq<Object>(0));
    }

}
