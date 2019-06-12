package demo.actors.typebased;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import demo.actors.groupbased.InsertDataActor;
import demo.actors.groupbased.UpdateDataActor;
import demo.datatypes.BaseCommand;

public class WriteJobActor extends AbstractActor {
    private final ActorRef insertDataActor;
    private final ActorRef updateDataActor;

    static public Props props(ActorRef insertDataActor, ActorRef updateDataActor) {
        return Props.create(WriteJobActor.class, () -> new WriteJobActor(insertDataActor, updateDataActor));
    }

    static public class InsertDataActorProxy extends BaseCommand {
        public InsertDataActorProxy(String message) {
            super(message);
        }
    }

    static public class UpdateDataActorProxy extends BaseCommand {
        public UpdateDataActorProxy(String message) {
            super(message);
        }
    }

    private WriteJobActor(ActorRef insertDataActor, ActorRef updateDataActor) {
        this.insertDataActor = insertDataActor;
        this.updateDataActor = updateDataActor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(InsertDataActorProxy.class, cmd -> {
                    insertDataActor.tell(new InsertDataActor.InsertDataActorInvokerCommand(cmd.getMessage()), getSelf());
                })
                .match(UpdateDataActorProxy.class, cmd -> {
                    updateDataActor.tell(new UpdateDataActor.UpdateDataInvokerCommand(cmd.getMessage()), getSelf());
                })
                .build();
    }
}
