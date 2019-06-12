package demo.actors.groupbased;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.datatypes.BaseCommand;

public class UpdateDataActor extends AbstractActor {

    static public Props props() {
        return Props.create(UpdateDataActor.class, () -> new UpdateDataActor());
    }

    static public class UpdateDataInvokerCommand extends BaseCommand {
        public UpdateDataInvokerCommand(String message) {
            super(message);
        }
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private UpdateDataActor() {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(UpdateDataInvokerCommand.class, cmd -> {
                    log.info(cmd.getMessage());
                })
                .build();
    }
}
