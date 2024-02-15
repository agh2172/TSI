public class Paint implements Comparable<Paint>{

    double size;
    double area;
    double price;

    public Paint(double s, double a, double p){
        size = s;
        area = a;
        price = p;
    }

    public double getSize() {
        return size;
    }

    public double getArea() {
        return area;
    }

    public double getPrice() {
        return price;
    }

    public double pricePerMeter(){
        return price/area;
    }

    public int compareTo(Paint other){
        return Double.compare(this.pricePerMeter(), other.pricePerMeter());
    }

    public String toString(){
        return "Size: " + size + "L, Covers: " + area + "m^2, Price: £" + price;
    }
}
