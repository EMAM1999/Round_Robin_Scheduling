/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package round.robin.scheduling;
import static round.robin.scheduling.ROUNDROBINSCHEDULING.systemTime;

/**

 @author EMAM
 */
public class Job {

//      data entered
      private final String id;
      private final int arrivalTime;
      private final int CPUTime;

//      job submission
      private boolean submited;
      private int submissionTime;

//      data entered
      private int CPUTimeLeft;

//      job completion
      private boolean completed;
      private int completionTime;
//      after finish data 
//      private int waitingTime;// (completionTime - arrivalTime) - (CPUTime-CPUTimeLeft)
//      private int turnAroundTime;// completionTime - arrivalTime

      public Job(String id, int arrivalTime, int CPUTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.CPUTime = CPUTime;
            //                    default values
            initValues();
      }

      public int getArrivalTime() {
            return arrivalTime;
      }

      public int getCPUTime() {
            return CPUTime;
      }

      public String getId() {
            return id;
      }

      private void initValues() {
            this.CPUTimeLeft = this.CPUTime;
//            this.waitingTime = 0;
            this.submited = false;
            this.completed = false;
      }

      public boolean tick() {
            if ( completed ) {
                  return false;
            }
            CPUTimeLeft--;
            if ( CPUTimeLeft <=0 ) {
                  completed = true;
                  completionTime = systemTime+1;
            }
            return true;
      }

      public int getWaitingTime() {
            return (getTurnAroundTime()) - (CPUTime - CPUTimeLeft);
      }

      public int getTurnAroundTime() {
            int endTime = completed ? completionTime : systemTime;
            return endTime - arrivalTime;
      }

      public int getCPUTimeLeft() {
            return CPUTimeLeft;
      }

      public int getCompletionTime() {
            return completionTime;
      }

      public int getSubmissionTime() {
            return submissionTime;
      }

      public boolean isCompleted() {
            return completed;
      }

      public boolean isSubmited() {
            return submited;
      }

//      public int decreaseCPUTimeLeft(int i) {
//            if ( i > this.CPUTimeLeft ) {
//                  this.CPUTimeLeft = 0;
//            } else {
//                  this.CPUTimeLeft -= i;
//            }
//            if ( this.CPUTimeLeft == 0 ) {
//                  finished = true;
//            }
//            return this.CPUTimeLeft;
//      }
//      public int increaseWaitingTime(int i) {
//            this.waitingTime += i;
//            this.turnAroundTime += i;
//            this.completionTime += i;
//            return this.waitingTime;
//      }
}
