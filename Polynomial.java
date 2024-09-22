public class Polynomial {
    double[] poly;

    public Polynomial() {
        this.poly = new double[1];
        this.poly[0] = 0;
    }

    public Polynomial(double[] coeff) {
        this.poly = new double[coeff.length];
        for(int i=0; i<coeff.length; i++) {
            this.poly[i] = coeff[i];
        }
    }

    public Polynomial add(Polynomial poly2) {
        Polynomial poly3 = new Polynomial();
        poly3.poly = new double[poly2.poly.length];
        for(int i=0; i<this.poly.length; i++) {
            poly3.poly[i] = this.poly[i] + poly2.poly[i];
        }
        for(int i=this.poly.length; i<poly2.poly.length; i++) {
            poly3.poly[i] = poly2.poly[i];
        }
        return poly3;
    }

    public double evaluate(double number) {
        double result = 0;
        for(int i=0; i<this.poly.length; i++) {
            result = result + ((Math.pow(number, i))*poly[i]);
        } return result;
    }

    public boolean hasRoot(double x) {
        double result = evaluate(x);
        if(result == 0) {return true;}
        else {return false;}
    }
}