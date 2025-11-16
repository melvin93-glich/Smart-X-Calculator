import java.io.Serializable;
public interface Operable extends Serializable {
    double add(double a, double b);
    double subtract(double a, double b);
    double multiply(double a, double b);
    double divide(double a, double b);
    double power(double base, double exponent);
    double sqrt(double a);
    double log(double a);
    double log10(double a);
    double sin(double degrees);
    double cos(double degrees);
    double tan(double degrees);
}