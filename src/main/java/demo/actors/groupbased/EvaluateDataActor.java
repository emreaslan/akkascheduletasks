package demo.actors.groupbased;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.datatypes.BaseCommand;

public class EvaluateDataActor extends AbstractActor {
    static public Props props() {
        return Props.create(EvaluateDataActor.class, () -> new EvaluateDataActor());
    }

    static public class EvaluateDataActorInvokerCommand extends BaseCommand {
        public EvaluateDataActorInvokerCommand(String message) {
            super(message);
        }
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private EvaluateDataActor() {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EvaluateDataActorInvokerCommand.class, cmd -> {
                    log.info(cmd.getMessage());
                })
                .build();
    }
}