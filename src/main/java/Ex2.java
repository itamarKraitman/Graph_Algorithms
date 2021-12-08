package main.java;
import main.java.Algorithms.DWGraphAlgo;
//import main.java.GUI.Menu;
import main.java.GUI.UserWindow;
import main.java.api.DirectedWeightedGraph;
import main.java.api.DirectedWeightedGraphAlgorithms;
import main.java.GraphClass.DWGraph;
import javax.swing.*;
import com.google.gson.Gson;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 implements Runnable{
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    // TODO: implement test for this
    public static DirectedWeightedGraph getGrapg(String json_file) {
        return new DWGraph(json_file);
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    // TODO: implement test for this
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new DWGraphAlgo();
        ans.init(getGrapg(json_file));
        return ans;
    }
    /**
     * This static function will run your GUI using the json file.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    // TODO: start implementing GUI & test it
    public static void runGUI(String json_file) {
        DWGraph graph = new DWGraph(json_file);
        DWGraphAlgo graphAlgo = new DWGraphAlgo();
        graphAlgo.init(graph);
        UserWindow window = new UserWindow(graphAlgo);
    }

    public static void main(String[] args) {
        runGUI("src/main/java/data/G1.json");
    }

    @Override
    public void run() {

    }
}