import java.lang.Math; 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; 

public class Polynomial{
    public double[] coefficients;

    public int [] exponents; 

    public Polynomial(){
        coefficients = new double[]{0};
        exponents = new int[]{0};
    }

    public Polynomial(double[] co, int[] ex){
        coefficients = co;
        exponents = ex; 
    }

    public Polynomial(File file) throws FileNotFoundException{
        Scanner newScanner = new Scanner(file); 
        String eqn = newScanner.nextLine(); 
        eqn = eqn.replace("-", "+-");
        String[] terms = eqn.split("+");

        double[] co = new double[terms.length];
        int[] ex = new int[terms.length]; 

        for (int i = 0; i < terms.length; i++){
            String[] splitTerms = terms[i].split("x");
            co[i] = Double.parseDouble(splitTerms[0]); 
            if (splitTerms.length == 1){
                exponents[i] = 0; 
            }
            else{
                exponents[i] = Integer.parseInt(splitTerms[1]); 
            }
        }
        this.coefficients = co; 
        this.exponents = ex; 
        newScanner.close();
    }

    public Polynomial add(Polynomial pn){
        if (pn == null){
            return null; 
        }

        int highexp = 0; 
        for (int exponent : this.exponents){
            if (exponent > highexp){
                highexp = exponent; 
            }
        }
        for (int exponent : pn.exponents){
            if (exponent > highexp){
                highexp = exponent; 
            }
        }

        double[] temp = new double[highexp+1];
        for (int i = 0; i < this.exponents.length; i++){
            temp[this.exponents[i]] += this.coefficients[i]; 
        }
        for (int i = 0; i < this.exponents.length; i++){
            temp[pn.exponents[i]] += pn.coefficients[i]; 
        }

        int len = 0; 
        for (double co : temp){
            if (co != 0){
                len++; 
            }
        }

        double[] resultCoefficients = new double[len];
        int[] resultExponents = new int[len];

        int count = 0; 
        for (int i = 0; i < temp.length; i++){
            if (temp[i] != 0){
                resultCoefficients[count] = temp[i]; 
                resultExponents[count] = i; 
                count++; 
            }
        }

        return new Polynomial(resultCoefficients, resultExponents);
    }

    public double evaluate(double db){
        double eval = 0; 
        for (int i=0; i<this.coefficients.length; i++)
            eval += this.coefficients[i] * Math.pow(db, exponents[i]);  
        return eval;  
    }
    
    public boolean hasRoot(double db){
        return this.evaluate(db) == 0; 
    }

    public Polynomial multiply(Polynomial polynomial2){

		if (exponents == null && polynomial2.exponents == null){		// if polynomial1 and polynomial2 are null, then return a new empty polynomial
			return new Polynomial();
		}

		int max_exponent = -1;
		for (int i = 0; i < this.exponents.length; i++){
            for (int j = 0; j < polynomial2.exponents.length; j++){
                max_exponent = Math.max(max_exponent, this.exponents[i] + polynomial2.exponents[j]);
            }
        }

		max_exponent+=1;
		double[] temp_coefficients = new double[max_exponent];
		for (int i = 0; i < max_exponent; i++){
			temp_coefficients[i] = 0;
		}


		for (int i = 0; i < exponents.length; i++){
			for (int j = 0; j < polynomial2.exponents.length; j++){
				temp_coefficients[this.exponents[i] + polynomial2.exponents[j]] += this.coefficients[i] * polynomial2.coefficients[j];
			}
		}

		int non_zero_terms = count_non_zero_terms(temp_coefficients);

		double[] result_coefficients = new double[non_zero_terms];
		int[] result_exponents = new int[non_zero_terms];
		int index = 0;

		for (int i = 0; i < temp_coefficients.length; i++){
			if (temp_coefficients[i] != 0){
				result_coefficients[index] = temp_coefficients[i];
				result_exponents[index] = i;
				index+=1;
			}
		}

		return new Polynomial(result_coefficients, result_exponents);


	}

	private int count_non_zero_terms(double[] coefficients){

		int count = 0;

		for (int i = 0; i < coefficients.length; i++){
			if (coefficients[i] != 0){
				count+=1;
			}
		}

		return count;

	}
    
    public void saveToFile(String str) throws IOException{
        FileWriter newFileWriter = new FileWriter(str); 
        for (int i = 0; i < coefficients.length; i++){
            if ((coefficients[i] > 0 && i==0)||coefficients[i] < 0){
                newFileWriter.write(Double.toString(coefficients[i])); 
            }
            else if (coefficients[i] > 0){
                newFileWriter.write("+" + coefficients[i]); 
            }
            if (exponents[i] != 0){
                newFileWriter.write("x" + exponents[i]);
            }
        }
        newFileWriter.close();
    }
}