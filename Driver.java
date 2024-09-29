import java.io.File;

public class Driver {
    public static void main(String [] args) {
        double [] c1 = {1,-2,3};
        double [] c2 = {0,1,2};
        
        
        double [] c3 = {3,-1,4};
        double [] c4 = {0,1,2};

        Polynomial p1 = new Polynomial(c1, c2);
        Polynomial p2 = new Polynomial(c3, c4);

        Polynomial p3 = p1.multiply(p2);
        File file = new File("polynomial.txt");
        p3.saveToFile("polynomial.txt");
    }
}