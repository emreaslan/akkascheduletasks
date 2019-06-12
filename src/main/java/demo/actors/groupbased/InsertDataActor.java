package demo.actors.groupbased;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.datatypes.BaseCommand;

public class InsertDataActor extends AbstractActor {

    static public Props props() {
        return Props.create(InsertDataActor.class, () -> new InsertDataActor());
    }

    static public class InsertDataActorInvokerCommand extends BaseCommand {
        public InsertDataActorInvokerCommand(String message) {
            super(message);
        }
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private InsertDataActor() {

    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(InsertDataActorInvokerCommand.class, cmd -> {
                    log.info(cmd.getMessage());
                })
                .build();
    }
}