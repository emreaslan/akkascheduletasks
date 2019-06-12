# Akka Schedule Tasks
Implemented Akka based solution of a problem.

There are two kinds of jobs as WriteJobs and ReadJobs. These jobs can not be processed together. Each job has two kinds of job groups. 
Job and group hierarchy is listed below.

- WriteJobs
  - InsertData
  - UpdateData
- ReadJobs
  - ComputeData
  - EvaluateData

# Why Akka is Used
I used Akka because of simplecity, performance issues and adaptations to other tools. Akka is designed according to Actor Model patterns to enable 
concurrent programming efortlessly and scalable. Akka has a lot of components to be work as integrated to other popular tools like Kafka.

# Implementation
Job is generated and is sent to the JobProcessor. JobProcessor delegates Job to the actors (WriteJob or ReadJob) according to the type of the job. 
If new comer Job's type is different from previous job, JobProcessor waits previous actors to finish their job. 

InsertData, UpdateData, ComputeData and EvaluateData actors process one job one time. WriteJobs and ReadJobs delegates jobs to actors. 
InsertData and UpdataData actor can run concurrently. ComputeData and EvaluateData can run concurrently.

# Usage - Console Output Example
```
Please enter number of jobs to be created and processed: 
1000
Please enter probability of jobs to being created as WriteJob [0-100](*not guaranteed): 
25
.
.
.
.
.
[INFO] [06/12/2019 16:11:04.959] [demo-akka.actor.default-dispatcher-2] [akka://demo/user/$Ks] WriteJob Insert JOB_ID: 988 INSERT_ID: 150
[INFO] [06/12/2019 16:11:04.960] [demo-akka.actor.default-dispatcher-12] [akka://demo/user/$Ns] ReadJob Compute JOB_ID: 989 COMPUTE_ID: 387
[INFO] [06/12/2019 16:11:04.961] [demo-akka.actor.default-dispatcher-2] [akka://demo/user/$Os] ReadJob Evaluate JOB_ID: 990 EVALUATE_ID: 337
[INFO] [06/12/2019 16:11:04.961] [demo-akka.actor.default-dispatcher-2] [akka://demo/user/$Os] ReadJob Evaluate JOB_ID: 991 EVALUATE_ID: 338
[INFO] [06/12/2019 16:11:04.962] [demo-akka.actor.default-dispatcher-14] [akka://demo/user/$Qs] WriteJob Insert JOB_ID: 993 INSERT_ID: 151
[INFO] [06/12/2019 16:11:04.962] [demo-akka.actor.default-dispatcher-11] [akka://demo/user/$Rs] WriteJob Update JOB_ID: 992 UPDATE_ID: 117
[INFO] [06/12/2019 16:11:04.963] [demo-akka.actor.default-dispatcher-16] [akka://demo/user/$Ts] ReadJob Compute JOB_ID: 994 COMPUTE_ID: 388
[INFO] [06/12/2019 16:11:04.963] [demo-akka.actor.default-dispatcher-6] [akka://demo/user/$Us] ReadJob Evaluate JOB_ID: 995 EVALUATE_ID: 339
[INFO] [06/12/2019 16:11:04.963] [demo-akka.actor.default-dispatcher-16] [akka://demo/user/$Ts] ReadJob Compute JOB_ID: 1000 COMPUTE_ID: 389
[INFO] [06/12/2019 16:11:04.963] [demo-akka.actor.default-dispatcher-6] [akka://demo/user/$Us] ReadJob Evaluate JOB_ID: 996 EVALUATE_ID: 340
[INFO] [06/12/2019 16:11:04.963] [demo-akka.actor.default-dispatcher-6] [akka://demo/user/$Us] ReadJob Evaluate JOB_ID: 997 EVALUATE_ID: 341
[INFO] [06/12/2019 16:11:04.963] [demo-akka.actor.default-dispatcher-6] [akka://demo/user/$Us] ReadJob Evaluate JOB_ID: 998 EVALUATE_ID: 342
[INFO] [06/12/2019 16:11:04.963] [demo-akka.actor.default-dispatcher-6] [akka://demo/user/$Us] ReadJob Evaluate JOB_ID: 999 EVALUATE_ID: 343
```
