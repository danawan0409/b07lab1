import java.lang.reflect.Array;
import java.util.Arrays; 
import java.lang.Math; 

public class Polynomial{
    double[] coefficients; 
    public Polynomial(){
        coefficients = new double[]{0}; 
    }
    public Polynomial(double[] poly){
        coefficients = poly;
    }
    public Polynomial add(Polynomial pn){
        int maxLength = Math.max(this.coefficients.length, pn.coefficients.length);
        double[] resultCoefficients = new double[maxLength];

        for (int i = 0; i < this.coefficients.length; i++) {
            resultCoefficients[i] += this.coefficients[i];
        }

        for (int i = 0; i < pn.coefficients.length; i++) {
            resultCoefficients[i] += pn.coefficients[i];
        }

        return new Polynomial(resultCoefficients);
    }
    public double evaluate(double db){
        double eval = 0; 
        for (int i=0; i<this.coefficients.length; i++)
            eval += this.coefficients[i] * Math.pow(db, i);  
        return eval;  
    }
    public boolean hasRoot(double db){
        if (this.evaluate(db) == 0){
            return true;
        } 
        return false; 
    }
}