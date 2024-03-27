import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Logic logic = new Logic();
        logic.createClasses();
        logic.writeClasses();
        logic.findDecisionFunctions();
        if (logic.isDecisionFunctionsCorrect()) {

            logic.writeDecisionsFunctions();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Enter vector for classification");

                PerceptronObject object = new PerceptronObject();
                for (int i = 0; i < Logic.numberOfAttributes; i++) {
                    object.attributes.add(scanner.nextInt());
                }

                System.out.println(object);
                System.out.println("This vector is relate to " + logic.relateToClass(object) + " class");
            }
        } else {
            System.out.println("Classification is incorrect");
        }
    }
}