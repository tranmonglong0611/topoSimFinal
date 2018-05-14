package topo.jellyFish;

import java.io.*;

public class JellyFishGraphTest {
    public static void main(String args[]) {
//        JellyFishGraph jl = new JellyFishGraph(50, 12, 8);
//        System.out.println(jl.toString());
//
//        jl.writeToFile("jellyFishGraphTest.txt");
//        int sum = 0;
//
//        for(int sw : jl.switches()) {
//            sum += jl.degree(sw);
//            System.out.println(jl.degree(sw));
//        }
//
//
//        System.out.println("Total degree: " + sum);

        try {
            FileInputStream fi = new FileInputStream(new File("graphSaving/jellyFishGraphTest.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
           JellyFishTopology graph = (JellyFishTopology) oi.readObject();

            System.out.println(graph.toString());
            oi.close();
            fi.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }catch(IOException e) {
            System.out.println("IO Exception");
        }catch(ClassNotFoundException e) {
            System.out.println("Class not found");
        }
    }
}
