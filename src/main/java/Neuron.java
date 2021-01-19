import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Igor Motowidło (gottomy2)
 * Class for a Single Neuron is used to create NeuralNetwork Object.
 */

@Getter
@Setter
public class Neuron {
    private double[] weight,input;
    private double output,weightedSum;
    private int id,bias;
    private List<Neuron> parentsList;
    private ActivationInterface activationInterface;

    /**
     * Simple Single Neuron Constructor
     * @param weight weight array of single Neuron
     * @param bias constant bias of single Neuron
     * @param input array of inputs. can be empty if parentsList.size() == 2
     * @param parentsList array of Neuron objects, if size() == 2 then input is changed to both parents outputs in form of double[] array
     * @param activationInterface Class which implements ActivationInterface and contains activation function
     */
    public Neuron(double[] weight,int bias, double[]input,List<Neuron> parentsList, ActivationInterface activationInterface,int id){
        this.parentsList=parentsList;
        if(parentsList.size() == 2){
            this.input= new double[]{this.parentsList.get(0).output,this.parentsList.get(1).output};
        }
        else{
            this.input = input;
        }
        this.weight=weight;
        this.bias=bias;
        this.activationInterface=activationInterface;
        this.output = calcOutput();
        this.weightedSum=calcWeightedSum();
        this.id=id;
    }

    public int getId(){
        return this.id;
    }

    public List<Neuron> getParentsList(){
        return this.parentsList;
    }

    public double getOutput(){
        return this.output;
    }

    /**
     * Prints out params of Single Neutron
     */
    public void printParams(){
        System.out.println("----------");
        System.out.println("Neuron bias: " + bias);
        System.out.println("Neuron input: " + Arrays.toString(input));
        System.out.println("Neuron weight: " + Arrays.toString(weight));
        System.out.println("Neuron weighted sum: " + weightedSum);
        System.out.println("Parent1: " + (parentsList.size()==2  ? parentsList.get(0) : null));
        System.out.println("Parent2: " + (parentsList.size()==2 ? parentsList.get(1) : null));
        System.out.println("Output: " + output);
    }

    /**
     * Calculates the WeightedSum of Single Neuron
     * @return double WeightedSum
     */
    public double calcWeightedSum(){
        double weightedSum = 0.00;
        for(int i=0;i<weight.length;i++){
            weightedSum += weight[i] * input[i];
        }
        return weightedSum/3;
    }

    /**
     * Calculates the output of Single Neuron
     * @return double output based on the activation function.
     */
    public double calcOutput(){
        double output =0.00;
            for(int i=0;i<weight.length;i++){
                output+= weight[i] * input[i];
            }
            return activationInterface.activation(output+bias);
    }
}
