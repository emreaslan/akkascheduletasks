package demo;

import demo.datatypes.BaseCommand;
import demo.datatypes.GroupType;
import demo.datatypes.JobType;

import java.util.Random;

public class Job {
    private static int ID = 0;
    private static int ID_INSERT = 0;
    private static int ID_UPDATE = 0;
    private static int ID_COMPUTE = 0;
    private static int ID_EVALUATE = 0;

    private int jobId;
    private int groupId;
    private JobType jobType;
    private GroupType groupType;
    private BaseCommand command;

    public JobType getJobType() {
        return jobType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public BaseCommand getCommand() {
        return command;
    }

    public static int getNumberOfInsertJobs() {
        return ID_INSERT;
    }

    public static int getNumberOfUpdateJobs() {
        return ID_UPDATE;
    }

    public static int getNumberOfComputeJobs() {
        return ID_COMPUTE;
    }

    public static int getNumberOfEvaluateJobs() {
        return ID_EVALUATE;
    }

    private static JobType generateJobTypeRandom(int percentageOfBeingWriteJob) {
        Random rand = new Random();
        int type = rand.nextInt(100);
        if (type >= (100 - percentageOfBeingWriteJob)) {
            return JobType.WRITE;
        }
        return JobType.READ;
    }

    private GroupType generateGroupTypeRandom() {
        Random rand = new Random();
        int group = (rand.nextInt(2) + 1) * this.jobType.getValue();
        return GroupType.valueOf(group);
    }

    private BaseCommand generateCommand() {
        String message;

        if (groupType.equals(GroupType.INSERT)) {
            message = "WriteJob Insert JOB_ID: " + this.jobId + " INSERT_ID: " + this.groupId;
        } else if (groupType.equals(GroupType.UPDATE)) {
            message = "WriteJob Update JOB_ID: " + this.jobId + " UPDATE_ID: " + this.groupId;
        } else if (groupType.equals(GroupType.COMPUTE)) {
            message = "ReadJob Compute JOB_ID: " + this.jobId + " COMPUTE_ID: " + this.groupId;
        } else if (groupType.equals(GroupType.EVALUATE)) {
            message = "ReadJob Evaluate JOB_ID: " + this.jobId + " EVALUATE_ID: " + this.groupId;
        } else {
            message = "";
        }
        return new Command(message);
    }

    private void assignGroupId() {
        if (groupType.equals(GroupType.INSERT)) {
            groupId = ++ID_INSERT;
        } else if (groupType.equals(GroupType.UPDATE)) {
            groupId = ++ID_UPDATE;
        } else if (groupType.equals(GroupType.COMPUTE)) {
            groupId = ++ID_COMPUTE;
        } else if (groupType.equals(GroupType.EVALUATE)) {
            groupId = ++ID_EVALUATE;
        }
    }

    public Job(int percentageOfBeingWriteJob) {

        jobId = ++ID;
        this.jobType = generateJobTypeRandom(percentageOfBeingWriteJob);
        this.groupType = generateGroupTypeRandom();
        assignGroupId();
        this.command = generateCommand();
    }

    class Command extends BaseCommand {
        Command(String message) {
            super(message);
        }
    }
}