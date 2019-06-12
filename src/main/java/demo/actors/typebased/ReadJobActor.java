package demo.actors.typebased;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.actors.groupbased.ComputeDataActor;
import demo.actors.groupbased.EvaluateDataActor;
import demo.datatypes.BaseCommand;

public class ReadJobActor extends AbstractActor {
    private final ActorRef computeDataActor;
    private final ActorRef evaluateDataActor;

    static public Props props(ActorRef computeDataActor, ActorRef evaluateDataActor) {
        return Props.create(ReadJobActor.class, () -> new ReadJobActor(computeDataActor, evaluateDataActor));
    }

    static public class ComputeDataActorProxy extends BaseCommand {
        public ComputeDataActorProxy(String message) {
            super(message);
        }
    }

    static public class EvaluateDataActorProxy extends BaseCommand {
        public EvaluateDataActorProxy(String message) {
            super(message);
        }
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public ReadJobActor(ActorRef computeDataActor, ActorRef evaluateDataActor) {
        this.computeDataActor = computeDataActor;
        this.evaluateDataActor = evaluateDataActor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ComputeDataActorProxy.class, cmd -> {
                    computeDataActor.tell(new ComputeDataActor.ComputeDataInvokerCommand(cmd.getMessage()), getSelf());
                })
                .match(EvaluateDataActorProxy.class, cmd -> {
                    evaluateDataActor.tell(new EvaluateDataActor.EvaluateDataActorInvokerCommand(cmd.getMessage()), getSelf());
                })
                .build();
    }
}

