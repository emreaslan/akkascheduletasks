package demo.actors.groupbased;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.datatypes.BaseCommand;

public class ComputeDataActor extends AbstractActor {
    static public Props props() {
        return Props.create(ComputeDataActor.class, () -> new ComputeDataActor());
    }

    static public class ComputeDataInvokerCommand extends BaseCommand {
        public ComputeDataInvokerCommand(String message) {
            super(message);
        }
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ComputeDataActor() {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ComputeDataInvokerCommand.class, cmd -> {
                    log.info(cmd.getMessage());
                })
                .build();
    }

}
