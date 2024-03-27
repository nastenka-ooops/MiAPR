import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Logic {
    public static final int numberOfAttributes = 3;
    public static final int numberOfClasses = 3;
    public static final int numberOfObjectsInClass = 1;
    public static final int randomBound=20;

    public List<PerceptronClass> classes = new ArrayList<>();
    public List<PerceptronObject> weights = new ArrayList<>();
    public List<PerceptronObject> decisionsFunctions = new ArrayList<>();
    public List<Integer> decisions = new ArrayList<>();
    Random random = new Random(1);
    public void createClasses(){
        for (int i = 0; i < numberOfClasses; i++) {
            PerceptronClass tempClass = new PerceptronClass();
            for (int j = 0; j < numberOfObjectsInClass; j++) {
                PerceptronObject tempObject = new PerceptronObject();
                for (int k = 0; k < numberOfAttributes; k++) {
                    tempObject.attributes.add(random.nextInt(randomBound)- randomBound/2);
                }
                tempClass.objects.add(tempObject);
            }
            classes.add(tempClass);
        }
        initializeWeights();
        initializeDecisions();
    }
    private void initializeWeights(){
        for (int i = 0; i < numberOfClasses; i++) {
            PerceptronObject tempObject = new PerceptronObject();
            for (int j = 0; j < numberOfAttributes; j++) {
                tempObject.attributes.add(0);
            }
            weights.add(tempObject);
        }
    }

    private void initializeDecisions(){
        for (int i = 0; i < numberOfClasses; i++) {
            decisions.add(0);
        }
    }

    private int multiplication(PerceptronObject object, PerceptronObject weight){
        int result=0;
        for (int i = 0; i < numberOfAttributes; i++) {
            result+=weight.attributes.get(i)*object.attributes.get(i);
        }
        return result;
    }

    private boolean correctWeights(PerceptronObject object, PerceptronObject weight, int classNumber) {
        int d = multiplication(object, weight);
        boolean result = false;

        for (int i = 0; i < numberOfClasses; i++) {
            decisions.set(i, multiplication(object, weights.get(i)));

            if (i != classNumber) {
                int currD = decisions.get(i);
                if (d <= currD) {
                    changeWeight(object, weights.get(i), -1);
                    result = true;
                }
            }
        }
        if (result){
            changeWeight(object, weight, 1);
        }
        return result;
    }

    private void changeWeight(PerceptronObject object, PerceptronObject weight, int sign){
        for (int i = 0; i < numberOfAttributes; i++) {
            weight.attributes.set(i,weight.attributes.get(i) + sign*object.attributes.get(i));
        }
    }

    public void findDecisionFunctions(){
        boolean isMissclassification = true;
        while (isMissclassification) {
            for (int i = 0; i < numberOfClasses; i++) {
                var tempClass = classes.get(i);
                var tempWeight = weights.get(i);
                for (int j = 0; j < numberOfObjectsInClass; j++) {
                    var tempObject = tempClass.objects.get(j);
                    isMissclassification = correctWeights(tempObject, tempWeight, i);
                }
            }
        }
        decisionsFunctions = weights;
    }

    public void writeClasses(){
        for (int i = 0; i < numberOfClasses; i++) {
            for (int j = 0; j < numberOfObjectsInClass; j++) {
                System.out.println(classes.get(i).objects.get(j).toString());
            }
        }
        System.out.println();
    }
    public void writeDecisionsFunctions(){
        for (int i = 0; i < numberOfClasses; i++) {
            System.out.print("f" + i + "=");
            for (int j = 0; j < numberOfAttributes; j++) {
                System.out.print(decisionsFunctions.get(i).attributes.get(j) + "*x" + j+"+");
            }
            System.out.println("\u0008");
        }
    }

    public int relateToClass(PerceptronObject object){
        int maxValue = 0;
        int maxIndex = 0;

        for (int i = 0; i < numberOfAttributes; i++) {
            maxValue += object.attributes.get(i)*decisionsFunctions.get(0).attributes.get(i);
        }
        for (int i = 1; i < numberOfClasses; i++) {
            int tempValue = 0;
            for (int j = 0; j < numberOfAttributes; j++) {
                tempValue += object.attributes.get(j)*decisionsFunctions.get(i).attributes.get(j);
            }
            if (tempValue>maxValue){
                maxValue=tempValue;
                maxIndex=i;
            }
        }
        return maxIndex;
    }

    public boolean isDecisionFunctionsCorrect(){
        for (int i = 0; i < numberOfClasses; i++) {
            PerceptronClass tempClass = classes.get(i);
            for (int j = 0; j < numberOfObjectsInClass; j++) {
                PerceptronObject tempObject = tempClass.objects.get(j);
                if(relateToClass(tempObject)!=i){
                    return false;
                }
            }
        }
        return true;
    }
}