public abstract class Calculator implements Operable {
    protected String name;
    public Calculator(String name) {
        this.name = name;
    }
    public abstract void displayType();
    @Override
    public double add(double a, double b){
        return a + b;
    }
    @Override
    public double subtract(double a, double b){
        return a - b;
    }
    @Override
    public double multiply(double a, double b){
        return a * b;
    }
    @Override
    public double divide(double a, double b){
        double result = 0;
        try {
            if (b == 0) {
                throw new ArithmeticException("Division by zero");
            } else {
                result = a / b;
            }
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }
    @Override
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
    @Override
    public double sqrt(double a) {
        double result = 0;
        try {
            if (a < 0) {
                throw new ArithmeticException("Square root of negative number");
            } else {
                result = Math.sqrt(a);
            }
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }
    @Override
    public double log(double a) {
        double result = 0;
        try {
            if (a <= 0) {
                throw new ArithmeticException("Input must be greater than 0");
            } else {
                result = Math.log(a);
            }
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }
    @Override
    public double log10(double a) {
        double result = 0;
        try {
            if (a <= 0) {
                throw new ArithmeticException("Input must be greater than 0");
            } else {
                result = Math.log10(a);
            }
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }
    @Override
    public double sin(double degrees) {
        return Math.sin(Math.toRadians(degrees));
    }
    @Override
    public double cos(double degrees) {
        return Math.cos(Math.toRadians(degrees));
    }
    @Override
    public double tan(double degrees) {
        return Math.tan(Math.toRadians(degrees));
    }
}
