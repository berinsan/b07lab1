import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    double[] coeff;
    double[] power;

    public Polynomial() {
        this.coeff = new double[1];
        this.power = new double[1];
        this.coeff[0] = 0;
        this.power[0] = 0;
    }

    public Polynomial(double[] c, double[] p) {
        this.coeff = new double[c.length];
        for(int i=0; i<c.length; i++) {
            this.coeff[i] = c[i];
        }
        this.power = new double[p.length];
        for(int i=0; i<c.length; i++) {
            this.power[i] = p[i];
        }
    }

    public Polynomial add(Polynomial poly2) {
        Polynomial poly3 = new Polynomial();
        poly3.coeff = new double[this.coeff.length + poly2.coeff.length];
        poly3.power = new double[this.power.length + poly2.power.length];

        for(int i=0; i<this.coeff.length; i++) {
            poly3.coeff[i] = this.coeff[i];
        }
        for(int i=0; i<this.power.length; i++) {
            poly3.power[i] = this.power[i];
        }

        int k = poly3.power.length;

        for (int i = 0; i < poly2.coeff.length; i++) {
            boolean found = false;

            for (int j = 0; j < k; j++) {
                if (poly2.power[i] == poly3.power[j]) {
                    poly3.coeff[j] += poly2.coeff[i];
                    found = true;
                    break;
                }
            }

            if (!found) {
                poly3.coeff[k] = poly2.coeff[i];
                poly3.power[k] = poly2.power[i];
                k++;
            }
        }
        return poly3;
    }

    public Polynomial multiply(Polynomial poly2) {
        int polyLength = this.coeff.length * poly2.coeff.length;
        double coeffResults[] = new double[polyLength];
        double powerResults[] = new double[polyLength];

        // Keep track of the number of terms added to the final polynomial
        int finalLength = 0;
        // Iterate over this polynomial
        for(int i=0; i<this.coeff.length; i++) {
            // Iterate over the second polynomial
            for(int j=0; j<poly2.coeff.length; j++) {
                double resultCoeff = this.coeff[i] * poly2.coeff[j];
                double resultPower = this.power[i] + poly2.power[j];

                // Iterate over final polynomial list to check for same power
                boolean found = false;
                for(int k = 0; k < finalLength; k++) {
                    if(powerResults[k] == resultPower) {
                        coeffResults[k] += resultCoeff;
                        found = true;
                        break;
                    }

                }
                if (!found) {
                    coeffResults[finalLength] = resultCoeff;
                    powerResults[finalLength] = resultPower;
                    finalLength++;
                }
            }
        }
    
        double[] finalCoeff = new double[finalLength];
        double[] finalPower = new double[finalLength];
        for (int i = 0; i < finalLength; i++) {
            finalCoeff[i] = coeffResults[i];
            finalPower[i] = powerResults[i];
        }
        return new Polynomial(finalCoeff, finalPower);
    }

    public double evaluate(double number) {
        double result = 0;
        for (int i = 0; i < this.coeff.length; i++) {
            result += this.coeff[i] * Math.pow(number, this.power[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        if(this.evaluate(x)==0) {return true;}
        return false;
    }

    public Polynomial(File file) throws IOException{
        Scanner scanner = new Scanner(file);
        String polyString = scanner.nextLine();
        scanner.close();

        String[] parts = polyString.split("(?=[+-])");

        double[] finalCoeff = new double[parts.length];
        double[] finalPower = new double[parts.length];

        for(String part : parts) {
            int i = 0;
            if (part.contains("x")) {
                String[] terms = part.split("x");
                
                if(part.charAt(1)=="x"){
                    if(part.charAt(0)=="-") {
                        double coefficient = -1;
                    } else {
                        double coefficient = 1;
                    }
                } else {
                    double coefficient = terms[0].parseDouble();
                }

                if(part.charAt(-1)=="x") {
                    double power = 1;
                } else {
                    double power = terms[1].parseDouble();
                }

                finalCoeff[i] = coefficient;
                finalPower[i] = power;
                i++;
            }
        }
        this.coeff = new double[finalCoeff.length];
        this.power = new double[finalPower.length];

        for(int k=0; k<finalCoeff.length; k++) {
            this.coeff[k] = finalCoeff[k];
            this.power[k] = finalPower[k];
        }
    }
    public void saveToFile(String fileName) {
        StringBuilder polyString = new StringBuilder();
        int i = 0;
    
        if (this.coeff[0] == 1) {
            polyString.append("x");
            i++;
        } else if (this.coeff[0] == -1) {
            polyString.append("-x");
            i++;
        }
    
        for (; i < this.coeff.length; i++) {
            if (this.coeff[i] == 1) {
                polyString.append("x");
            } else if (this.coeff[i] == -1) {
                polyString.append("-x");
            } else {
                if(this.coeff[i]>0) {
                    if(i==0) {
                        polyString.append(this.coeff[i]);
                    } else { 
                        polyString.append("+").append(this.coeff[i]);
                    }
                } else if (this.coeff[i]<0) {
                    polyString.append(this.coeff[i]);
                } else {
                    continue;
                }
                polyString.append("x");
            }
            if(!(this.coeff[i] == 0)) {
                if (this.power[i] == 0) {
                    polyString.deleteCharAt(polyString.length() -1);
                } else if(this.power[i] == 1) {
                    continue;
                } else {
                    polyString.append(this.power[i]);
                }
            } else {
                polyString.deleteCharAt(polyString.length() -1);
            }
        }
    
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(polyString.toString());
            writer.close();
            System.out.println("Polynomial saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error while writing to file: " + fileName);
            e.printStackTrace();
        }
    }
    
}