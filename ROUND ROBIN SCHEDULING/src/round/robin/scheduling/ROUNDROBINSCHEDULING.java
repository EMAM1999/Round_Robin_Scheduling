/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package round.robin.scheduling;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**

 @author EMAM
 */
public class ROUNDROBINSCHEDULING {

      static int systemTime = 0;

      /**
       @param args the command line arguments
       * @throws java.io.IOException
       */
      public static void main(String[] args) throws IOException {
            List<Job> jobs = ReadDataFromFile("C:\\Users\\EMAM\\Desktop\\New folder (2)\\New Text Document.txt");
            RoundRobin rr = new RoundRobin(jobs, 5, 125);
            rr.run();
            System.out.println(rr.getProcessingInfo());
            System.out.println(rr.getFinishedJobsInfo());
            System.out.println(rr.getSchedulingInfo());
      }

      private static List<Job> ReadDataFromFile(String path) throws IOException {
            List<String> lines = Files.readAllLines(Paths.get(path));
            List<Job> jobs = new ArrayList<>();
            lines.stream().map((line) -> line.replaceAll(" ", "")).forEachOrdered((line) -> {
                  String id = line.split(",")[0];
                  int arrivalTime = Integer.parseInt(line.split(",")[1]);
                  int CPUTime = Integer.parseInt(line.split(",")[2]);
                  jobs.add(new Job(id, arrivalTime, CPUTime));
            });
            return jobs;
      }

}
