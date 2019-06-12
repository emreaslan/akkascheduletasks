package demo;

import akka.actor.*;
import demo.actors.groupbased.ComputeDataActor;
import demo.actors.groupbased.EvaluateDataActor;
import demo.actors.groupbased.InsertDataActor;
import demo.actors.groupbased.UpdateDataActor;
import demo.actors.typebased.ReadJobActor;
import demo.actors.typebased.WriteJobActor;
import demo.datatypes.GroupType;
import demo.datatypes.JobType;

import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class JobProcessor {
    private JobType previousJobType = JobType.NONE;
    private ActorSystem system;
    private Inbox inboxInsert, inboxUpdate, inboxWrite, inboxCompute, inboxEvaluate, inboxRead;
    private ActorRef insertDataActor;
    private ActorRef updateDataActor;
    private ActorRef writeJobActor;
    private ActorRef computeDataActor;
    private ActorRef evaluateDataActor;
    private ActorRef readJobActor;

    private static final Duration terminateDuration = Duration.ofSeconds(2);

    public ActorSystem getSystem() {
        return system;
    }

    private void createWriteJobs() {
        insertDataActor = system.actorOf(InsertDataActor.props());
        inboxInsert.watch(insertDataActor);
        updateDataActor = system.actorOf(UpdateDataActor.props());
        inboxUpdate.watch(updateDataActor);
        writeJobActor = system.actorOf(WriteJobActor.props(insertDataActor, updateDataActor));
        inboxWrite.watch(writeJobActor);
    }

    private void createReadJobs() {
        computeDataActor = system.actorOf(ComputeDataActor.props());
        inboxCompute.watch(computeDataActor);
        evaluateDataActor = system.actorOf(EvaluateDataActor.props());
        inboxEvaluate.watch(evaluateDataActor);
        readJobActor = system.actorOf(ReadJobActor.props(computeDataActor, evaluateDataActor));
        inboxRead.watch(readJobActor);
    }

    public JobProcessor() {
        system = ActorSystem.create("demo");
        inboxInsert = Inbox.create(system);
        inboxCompute = Inbox.create(system);
        inboxEvaluate = Inbox.create(system);
        inboxRead = Inbox.create(system);
        inboxUpdate = Inbox.create(system);
        inboxWrite = Inbox.create(system);
        createWriteJobs();
        createReadJobs();
    }

    private void completeJobs(JobType jobType) throws TimeoutException {
        if (jobType.equals(JobType.WRITE)) {
            terminateWriteJobs();
            createWriteJobs();
        } else if (jobType.equals(JobType.READ)) {
            terminateReadJobs();
            createReadJobs();
        }
    }


    private void terminateActor(ActorRef actor, Inbox inbox) throws TimeoutException {
        actor.tell(PoisonPill.getInstance(), ActorRef.noSender());
        AtomicBoolean isTerminated = new AtomicBoolean(false);
        while (!isTerminated.get()) {
            Object result = inbox.receive(terminateDuration);
            if (result instanceof Terminated) {
                isTerminated.set(true);
            }
        }
    }

    private void terminateWriteJobs() throws TimeoutException {
        terminateActor(writeJobActor, inboxWrite);
        terminateActor(insertDataActor, inboxInsert);
        terminateActor(updateDataActor, inboxUpdate);
    }

    private void terminateReadJobs() throws TimeoutException {
        terminateActor(readJobActor, inboxRead);
        terminateActor(computeDataActor, inboxCompute);
        terminateActor(evaluateDataActor, inboxEvaluate);
    }

    public void processJob(Job job) throws TimeoutException {
        JobType jobType = job.getJobType();
        GroupType groupType = job.getGroupType();
        String message = job.getCommand().getMessage();

        if (!previousJobType.equals(jobType)) {
            completeJobs(jobType);
        }

        if (jobType.equals(JobType.WRITE)) {

            if (groupType.equals(GroupType.INSERT)) {
                writeJobActor.tell(new WriteJobActor.InsertDataActorProxy(message), ActorRef.noSender());
            } else {
                writeJobActor.tell(new WriteJobActor.UpdateDataActorProxy(message), ActorRef.noSender());
            }
        } else if (jobType.equals(JobType.READ)) {

            if (groupType.equals(GroupType.COMPUTE)) {
                readJobActor.tell(new ReadJobActor.ComputeDataActorProxy(message), ActorRef.noSender());
            } else {
                readJobActor.tell(new ReadJobActor.EvaluateDataActorProxy(message), ActorRef.noSender());
            }
        }
        previousJobType = jobType;
    }
}
