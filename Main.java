import java.util.Scanner;
public class Main {
        public static void main(String[] args) {
        ScientificCalculator calc = new ScientificCalculator("SciCalc");
        LogManager logManager = new LogManager();
        Scanner sc = new Scanner(System.in);
        String logFile = "sci_calc_logs.ser";
        logManager.loadLogs(logFile);
        calc.displayType();
        while (true) {
            System.out.println("\nChoose operation:");
            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.println("5. Power (base^exponent)");
            System.out.println("6. Square Root");
            System.out.println("7. Natural Log (ln)");
            System.out.println("8. Log base 10");
            System.out.println("9. Sin (degrees)");
            System.out.println("10. Cos (degrees)");
            System.out.println("11. Tan (degrees)");
            System.out.println("12. Show Logs");
            System.out.println("13. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            if (choice == 13) {
                logManager.saveLogs(logFile);
                System.out.println("Exiting...");
                break;
            }
            if (choice == 12) {
                logManager.showLogs();
                continue;
            }
            double a, b, result = 0;
            String operation = "";
            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter first number: ");
                        a = sc.nextDouble();
                        System.out.print("Enter second number: ");
                        b = sc.nextDouble();
                        result = calc.add(a, b);
                        operation = String.format("Add: %.4f + %.4f = %.4f", a, b, result);
                        break;
                    case 2:
                        System.out.print("Enter first number: ");
                        a = sc.nextDouble();
                        System.out.print("Enter second number: ");
                        b = sc.nextDouble();
                        result = calc.subtract(a, b);
                        operation = String.format("Subtract: %.4f - %.4f = %.4f", a, b, result);
                        break;
                    case 3:
                        System.out.print("Enter first number: ");
                        a = sc.nextDouble();
                        System.out.print("Enter second number: ");
                        b = sc.nextDouble();
                        result = calc.multiply(a, b);
                        operation = String.format("Multiply: %.4f * %.4f = %.4f", a, b, result);
                        break;
                    case 4:
                        System.out.print("Enter first number: ");
                        a = sc.nextDouble();
                        System.out.print("Enter second number: ");
                        b = sc.nextDouble();
                        result = calc.divide(a, b);
                        operation = String.format("Divide: %.4f / %.4f = %.4f", a, b, result);
                        break;
                    case 5:
                        System.out.print("Enter base: ");
                        a = sc.nextDouble();
                        System.out.print("Enter exponent: ");
                        b = sc.nextDouble();
                        result = calc.power(a, b);
                        operation = String.format("Power: %.4f ^ %.4f = %.4f", a, b, result);
                        break;
                    case 6:
                        System.out.print("Enter number: ");
                        a = sc.nextDouble();
                        result = calc.sqrt(a);
                        operation = String.format("Sqrt: sqrt(%.4f) = %.4f", a, result);
                        break;
                    case 7:
                        System.out.print("Enter number: ");
                        a = sc.nextDouble();
                        result = calc.log(a);
                        operation = String.format("Natural Log: ln(%.4f) = %.4f", a, result);
                        break;
                    case 8:
                        System.out.print("Enter number: ");
                        a = sc.nextDouble();
                        result = calc.log10(a);
                        operation = String.format("Log10: log10(%.4f) = %.4f", a, result);
                        break;
                    case 9:
                        System.out.print("Enter angle in degrees: ");
                        a = sc.nextDouble();
                        result = calc.sin(a);
                        operation = String.format("Sin: sin(%.4f°) = %.4f", a, result);
                        break;
                    case 10:
                        System.out.print("Enter angle in degrees: ");
                        a = sc.nextDouble();
                        result = calc.cos(a);
                        operation = String.format("Cos: cos(%.4f°) = %.4f", a, result);
                        break;
                    case 11:
                        System.out.print("Enter angle in degrees: ");
                        a = sc.nextDouble();
                        result = calc.tan(a);
                        operation = String.format("Tan: tan(%.4f°) = %.4f", a, result);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        continue;
                }
                System.out.println("Result: " + result);
                logManager.addLog(operation);

            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
