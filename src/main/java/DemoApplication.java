import demo.Job;
import demo.JobProcessor;

import java.util.Scanner;

public class DemoApplication {


    public static void main(String[] args) {
        JobProcessor jobProcessor = new JobProcessor();
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter number of jobs to be created and processed: ");
        int numberOfJobs = in.nextInt();
        System.out.println("Please enter probability of jobs to being created as WriteJob [0-100](*not guaranteed): ");
        int percentageOfBeingWriteJob = in.nextInt();

        try {
            for (int i = 0; i < numberOfJobs; ++i) {
                Job job = new Job(percentageOfBeingWriteJob);
                jobProcessor.processJob(job);
            }

            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jobProcessor.getSystem().terminate();

        }
    }
}
