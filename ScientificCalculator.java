public class ScientificCalculator extends Calculator {
    public ScientificCalculator(String name) {
        super(name);
    }
    @Override
    public void displayType() {
        System.out.println("Scientific Calculator: " + name);
    }
}