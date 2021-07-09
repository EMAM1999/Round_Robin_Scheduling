/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package round.robin.scheduling;

import java.util.ArrayList;
import java.util.List;

import static round.robin.scheduling.ROUNDROBINSCHEDULING.systemTime;

/**

 @author EMAM
 */
public class RoundRobin {
//      ID   start   end 

      private String processingTable = "";

      private final List<Job> jobs;

      private double avgWaitingTime;
      private double avgTurnAroundTime;
      private int idleTime;
      private final int finishTime;

      private final int quantum;

      public RoundRobin(List<Job> jobs) {
            this(jobs, 4, 20);
      }

      public RoundRobin(List<Job> jobs, int quantum, int timePeriod) {
            this.jobs = new ArrayList<>();
            jobs.forEach(job -> this.jobs.add(job));
            this.quantum = quantum;
            this.finishTime = timePeriod;
            this.idleTime = 0;
      }

      public int getFinishTime() {
            return finishTime;
      }

      public int getIdleTime() {
            return idleTime;
      }

      public String getProcessingTable() {
            return processingTable;
      }

      public void run() {
            processingTable += "============================================\n";
            processingTable += "Process ID | start time | end time\n";
            processingTable += "============================================\n";
            setValuesInStart();
            while ( systemTime < this.finishTime ) {
                  boolean idle = true;
                  for ( Job job: jobs ) {
                        if ( job.getArrivalTime() <= systemTime && !job.isCompleted() ) {// if the job did arrives and didnot completed yet (still)
//                              How much time the job will take
                              int processTimeAllowed = Math.min(this.finishTime - systemTime, this.quantum);
                              int processTime = 0;
                              if ( job.getCPUTimeLeft() > processTimeAllowed ) {
                                    processTime = processTimeAllowed;
                              } else {
                                    processTime = job.getCPUTimeLeft();
                              }
//                              
                              for ( int j = 0; j < processTime; j++ ) {
                                    job.tick();
                                    systemTime++;
                              }
                              idle = false;
                              processingTable += "    " + job.getId() + "\t|\t" + (systemTime - processTime) + "\t|\t" + systemTime
                                      + "\n----------------------------------------\n";
                        }
                  }
                  if ( idle ) {
                        systemTime++;
                        idleTime++;
                  }

            }
            int sum = 0;
            this.avgTurnAroundTime = jobs
                    .stream()
                    .map(job -> job.getTurnAroundTime())
                    .reduce(sum, Math::addExact) / (double)jobs.size();
            sum = 0;
            this.avgWaitingTime = jobs
                    .stream()
                    .map(job -> job.getWaitingTime())
                    .reduce(sum, Math::addExact) / (double)jobs.size();

      }

      public String getProcessingInfo() {
            return getProcessingTable();
      }

      public String getFinishedJobsInfo() {
            String s = "";
            s += "============================================\n";
            s += "Process ID | Turnaround time | Waiting time\n";
            s += "============================================\n";
            s = jobs.stream().filter((job) -> (job.isCompleted())).map((job) -> "    " + job.getId() + "\t|\t" + job.getTurnAroundTime() + "\t|\t" + job.getWaitingTime()
                    + "\n----------------------------------------\n").reduce(s, String::concat);
            return s;
      }

      public String getSchedulingInfo() {
            String s = "";
            s += "============================================\n";
            s += "In Time Period " + finishTime + "\n";
            s += "\tCPU Utilization = " + getCPUUtilization() + " %\n";
            s += "\tThoughput = " + getThoughput() + "\n";
            s += "============================================\n";
            return s;
      }

      private int getThoughput() {
            int n = 0;
            for ( Job job: jobs ) {
                  if ( job.isCompleted() ) {
                        n++;
                  }
            }
            return n;
      }

      private void setValuesInStart() {
            int sysTime = jobs.get(0).getArrivalTime();
            systemTime = sysTime;
            idleTime += systemTime;
      }

      private boolean allFinish() {
            return getJobs().stream().noneMatch((job) -> (!job.isCompleted()));
      }

      public double getCPUUtilization() {
            return (1 - ((double)idleTime / systemTime)) * 100;
      }

      public double getAvgTurnAroundTime() {
            return avgTurnAroundTime;
      }

      public double getAvgWaitingTime() {
            return avgWaitingTime;
      }

      public List<Job> getJobs() {
            return jobs;
      }

      public int getQuantum() {
            return quantum;
      }

}
