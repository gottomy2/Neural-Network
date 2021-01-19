import com.google.common.graph.Graph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.*;

public class NeuralNetwork {
    private List<Neuron> neuronList;

    public NeuralNetwork(List<Neuron> neuronList){
        this.neuronList = neuronList;
    }

    public void printState(){
        for(int i =0; i <neuronList.size();i++){
            System.out.println("Neuron nr " + i +": ");
            Neuron neuron = neuronList.get(i);
            neuron.printParams();
        }
    }

    public void printNetwork(){
        for(int i = 0;i<neuronList.size();i++){
            neuronList.get(i).printParams();
        }
    }

    public MutableValueGraph<Integer,Double> generateGraph(Neuron neuron) {
        int mainId = neuron.getId();

        MutableValueGraph<Integer, Double> weightedGraph = ValueGraphBuilder.directed().build();
        weightedGraph.addNode(mainId);
        List<Neuron> parentsList = neuron.getParentsList();

        for (int i = 0; i < parentsList.size(); i++) {
            weightedGraph.addNode(parentsList.get(i).getId());
            weightedGraph.putEdgeValue(parentsList.get(i).getId(), mainId, parentsList.get(i).getOutput());
        }
        return weightedGraph;
    }
}