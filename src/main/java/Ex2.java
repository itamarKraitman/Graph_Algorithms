package main.java;
import main.java.Algorithms.DWGraphAlgo;
//import main.java.main.java.GUI.Menu;
import main.java.GUI.*;
import main.java.api.DirectedWeightedGraph;
import main.java.api.DirectedWeightedGraphAlgorithms;
import main.java.GraphClass.DWGraph;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        try {
            return new DWGraph(json_file);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new DWGraph();
        }
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new DWGraphAlgo();
        ans.init(getGrapg(json_file));
        return ans;
    }
    /**
     * This static function will run your main.java.GUI using the json file.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms graphAlgo = getGrapgAlgo(json_file);
        new FrameWindow(graphAlgo);
    }

    public static void main(String[] args) {
        runGUI(args[0]);
    }
    }
