import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /* Checks if a String is empty ("") or null. */
    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    /* Counts how many times the substring appears in the larger string. */
    public static int countMatches(String text, String str) {
        if (isEmpty(text) || isEmpty(str)) {
            return 0;
        }

        int index = 0, count = 0;
        while (true) {
            index = text.indexOf(str, index);
            if (index != -1) {
                count ++;
                index += str.length();
            } else {
                break;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        class Activation implements ActivationInterface {
            @Override
            public double activation(double x) {
                return 1 / (1 + Math.exp(-x));
            }
        }



        //Declaration of weight arrays:
        double[] weight1 = {1, 2};
        double[] weight2 = {2, 3};
        double[] weight3 = {3, 4};

        //Declaration of input arrays:
        double[] input1 = {1, 2};
        double[] input2 = {2, 3};
        double[] input3 = {3, 4};

        //Initializing Neuron objects to create NeuralNetwork
        ActivationInterface activationInterface = new Activation();
        List<Neuron> parentsList = new ArrayList<Neuron>();
        List<Neuron> parentsList2 = new ArrayList<Neuron>();
        Neuron neuron1 = new Neuron(weight1, 3, input1, parentsList, activationInterface,0);
        Neuron neuron2 = new Neuron(weight2, 4, input2, parentsList, activationInterface,1);
        parentsList2.add(neuron1);
        parentsList2.add(neuron2);
        Neuron neuron3 = new Neuron(weight3, 5, input3, parentsList2, activationInterface,2);

        List<Neuron> neuronList = new ArrayList<Neuron>();
        neuronList.add(neuron1);
        neuronList.add(neuron2);
        neuronList.add(neuron3);

        //Initializing NeuralNetwork object:
        NeuralNetwork neuralNetwork = new NeuralNetwork(neuronList);

        //Printing Network data and generating graph:
        neuralNetwork.printNetwork();
        MutableValueGraph<Integer,Double> graph = neuralNetwork.generateGraph(neuron3);
        String stringGraph = graph.toString();
        String edges = stringGraph.substring(stringGraph.indexOf("edges:"),stringGraph.length());
        System.out.println("============");
        System.out.println("Graph:");
        System.out.println(edges);
        int count = countMatches(edges,"->");
        List<String> connections = new ArrayList<String>();
//        System.out.println(count);


        for (int i = 0; i < count; i++) {
            String connectionString = edges.substring(edges.indexOf("->") - 2,edges.indexOf("=")-1);
//            System.out.println(connectionString);
            connections.add(connectionString);
            String deletionString = edges.substring(5,edges.indexOf(","));
            edges = edges.replace(deletionString,"");
//            System.out.println(edges);
        }
        System.out.println("Connections: " + connections);
        String finalGraph = "digraph NeuralNetwork\n" +
                "{";
        System.out.println();
        for(int i = 0; i < connections.size();i++){
            finalGraph += '"' + "N" + connections.get(i).substring(connections.get(i).indexOf("->") -2 , connections.get(i).indexOf("->")) + '"' + " ->" + '"' + "N" +
                    connections.get(i).substring(connections.get(i).indexOf("->")+3) + '"' + "\n";
        }
        finalGraph += "}";
        System.out.println(finalGraph);
    }
}