import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static ArrayList<Paint> paints = new ArrayList<Paint>();

    public static void main(String[] args) {
        //not all rooms rectangular
        //find amount of paint (mL/L^2)
        //find cost of paint

        //paint prices from: https://www.farrow-ball.com/paint/hague-blue?sku=5051836295301&gad_source=1&gclid=Cj0KCQiA5rGuBhCnARIsAN11vgTBR7Ghsoys9n6H4YcTMLmdyScwszXIEnhL49sMDLR25-3T4AlrKWcaArnxEALw_wcB

        Paint paintS = new Paint(0.75, 8, 34);
        Paint paintM = new Paint(2.5, 30, 81);
        Paint paintL = new Paint(5, 60, 130);

        paints.add(paintS);
        paints.add(paintM);
        paints.add(paintL);



        //double cost = 5; //$5/pot, pick whatever
        //double size = 1; //pot of paint size in litres

        double newArea = 0;

        String shape = "RECT"; //RECT (rectangular), TRI (triangular prism), CIRC (circular), SEMI (semi circular), DOOR (door/window to subtract)
        boolean ceiling = true;

        boolean more_rooms = true;
        Scanner reader = new Scanner(System.in);

        System.out.println("Welcome to the paint calculator!\nPlease enter all input in format requested, and measurements in the metric system.");
        double total_area=0;

        while(more_rooms){
            System.out.println("Are there more rooms (Y/N)?");
            String input = reader.next();
            if(input.toUpperCase().trim().equals("N")){
                more_rooms=false;
                continue;
            }else{
                //Take input for next room
                System.out.println("What shape is the room?");
                System.out.println("Please enter RECT for rectangular or square room");
                System.out.println("Please enter CIRC for a circular room");
                System.out.println("Please enter SEMI for a semi-circular room");
                System.out.println("Please enter TRI for a triangular prism room");
                System.out.println("Please enter DOOR for a door you'd like to subtract an area");
                input = reader.next();
                shape = input.toUpperCase().trim();
                System.out.println("Do you want to paint the ceiling (Y/N)? Input anything for door");
                input = reader.next();
                ceiling = input.toUpperCase().trim().equals("Y");
                newArea = get_area(shape, ceiling);
                total_area += newArea;
                System.out.println("New room's area: " + newArea + " m^2");
                System.out.println("Total area: " + total_area + " m^2");
            }
        }

        System.out.println("Total area to be painted: " + total_area);

        generate_pricing(total_area);


    }

    //generates the price for the area to be painted
    public static double generate_pricing(double total_area){
        Scanner reader = new Scanner(System.in);
        String input;
        boolean addPaint  = true;
        boolean updated = false;

        System.out.println("There are currently three paints stored in our database:");
        for(Paint paint : paints){
            System.out.println(paint);
        }

        while(addPaint) {
            System.out.println("Would you like to add another paint? (Y/N)");
            input = reader.next();
            if (input.toUpperCase().trim().equals("Y")) {
                double s=0, a=0, p=0;
                boolean valid = true;

                //Add another paint
                System.out.println("What is the size of the can of paint (L)?");
                input = reader.next();

                try {
                    Double temp = Double.parseDouble(input);
                    s = temp;
                } catch (NumberFormatException e) {
                    valid = false;
                }

                if(!valid || s<=0){
                    System.out.println("Invalid input, please make sure the size is numerical and non-zero");
                    continue;
                }


                System.out.println("What is the area covered by the can of paint (m^2)?");
                input = reader.next();

                try {
                    Double temp = Double.parseDouble(input);
                    a = temp;
                } catch (NumberFormatException e) {
                    valid = false;
                }

                if(!valid || a<=0){
                    System.out.println("Invalid input, please make area covered the size is numerical and non-zero");
                    continue;
                }

                System.out.println("What is the price of a can of paint (m^2)?");
                input = reader.next();

                try {
                    Double temp = Double.parseDouble(input);
                    p = temp;
                } catch (NumberFormatException e) {
                    valid = false;
                }

                if(!valid || p<=0){
                    System.out.println("Invalid input, please make sure the price is numerical and non-zero");
                    continue;
                }

                paints.add(new Paint(s, a, p));
                updated = true;
            }else{
                addPaint=false;
            }
        }
        //paint database is now finalised
        //sort paints by price efficiency (price/meter)
        //System.out.println("Sorting paints");
        paints.sort(Paint::compareTo);
        //System.out.println("Paints sorted");
        if(updated){
            System.out.println("After updating these are the paints stored in our database:");
            for(Paint paint : paints){
                System.out.println(paint);
            }
        }
        //each index in suggestPaints is a counter for the corresponding index in paints
        int[] suggestedPaints = new int[paints.size()];
        System.out.println("Before while loop");
        while(total_area>0){
            for(int i=0; i<paints.size(); i++){
                //System.out.printf("i = " + i + "area left = " + (total_area-paints.get(i).area));
                if(total_area-paints.get(i).area>0||i==paints.size()-1){
                    //Use this paint as it is most economical
                    total_area-=paints.get(i).area;
                    suggestedPaints[i]++;
                    break;
                }
            }
        }

        //slight edge case issues - sometimes you get many of the smalls instead of a medium

        //suggestedPaints now contains the count for each paint
        System.out.println("These are the amounts of paint we suggest you use");
        double totalPrice=0;
        for(int i=0; i<suggestedPaints.length; i++){
            System.out.println(suggestedPaints[i] + "x:" + paints.get(i));
            totalPrice+=suggestedPaints[i]*paints.get(i).price;
        }
        System.out.println("This gives you a total price of: £" + totalPrice);

        return 0;
    }

    public static double get_area(String shape, boolean ceiling){
        //debug line System.out.println("In get_area");


        switch (shape) {
            case "RECT" -> {
                return Math.abs(get_rect(ceiling));
            }
            case "TRI" -> {
                return Math.abs(get_tri(ceiling));
            }
            case "CIRC" -> {
                return Math.abs(get_circ(ceiling));
            }
            case "SEMI" -> {
                return Math.abs(get_semi(ceiling));
            }
            case "DOOR" -> {
                return get_door();
            }
            default -> {
                //shape not found
                System.out.println("Unsupported room shape, please contact IT for help");
                return 0;
            }
        }
    }

    public static double get_door(){
        Scanner reader = new Scanner(System.in);
        String input;

        double h;
        double w;

        System.out.println("What is the height of the door?");
        input = reader.next();
        h = Double.parseDouble(input.trim());

        System.out.println("What is the width of the door?");
        input = reader.next();
        w = Double.parseDouble(input.trim());

        return -1*w*h;

    }

    //takes care of semicircular shaped rooms
    public static double get_semi(boolean ceiling){
        double d; //diameter
        double h;
        double r; //radius

        Scanner reader = new Scanner(System.in);
        String input;

        //get diameter
        System.out.println("What is the diameter of the room?");
        input = reader.next();
        d = Double.parseDouble(input.trim());
        r = d/2;

        //get height
        System.out.println("What is the height of the room?");
        input = reader.next();
        h = Double.parseDouble(input.trim());

        if(ceiling){
            return get_area_SEMI_CEIL(d, h);
        }else{
            //Can't paint a triangular prism without the ceiling, it's only ceiling
            return get_area_SEMI(d, h);
        }

    }

    //takes care of cylinder shaped rooms
    public static double get_circ(boolean ceiling){
        double d; //diameter
        double h;
        double r; //radius

        Scanner reader = new Scanner(System.in);
        String input;

        //get diameter
        System.out.println("What is the diameter of the room?");
        input = reader.next();
        d = Double.parseDouble(input.trim());
        r = d/2;

        //get height
        System.out.println("What is the height of the room?");
        input = reader.next();
        h = Double.parseDouble(input.trim());

        if(ceiling){
            return get_area_CIRC_CEIL(r, h);
        }else{
            //Can't paint a triangular prism without the ceiling, it's only ceiling
            return get_area_CIRC(r, h);
        }

    }

    //takes care of triangular prism shaped rooms
    public static double get_tri(boolean ceiling){
        double w;
        double h;
        double l;

        Scanner reader = new Scanner(System.in);
        String input;

        //get length
        System.out.println("What length is the room?");
        input = reader.next();
        l = Double.parseDouble(input.trim());

        //get width
        System.out.println("What width is the room?");
        input = reader.next();
        w = Double.parseDouble(input.trim());

        //get height
        System.out.println("What height is the room?");
        input = reader.next();
        h = Double.parseDouble(input.trim());

        if(ceiling){
            return get_area_TRI_CEIL(w, h, l);
        }else{
            //Can't paint a triangular prism without the ceiling, it's only ceiling
            return get_area_TRI(w, h, l);
        }

    }

    //takes care of rectangular shaped rooms
    public static double get_rect(boolean ceiling){
        double w;
        double h;
        double l;

        Scanner reader = new Scanner(System.in);
        String input;

        //get length
        System.out.println("What length is the room?");
        input = reader.next();
        l = Double.parseDouble(input.trim());

        //get width
        System.out.println("What width is the room?");
        input = reader.next();
        w = Double.parseDouble(input.trim());

        //get height
        System.out.println("What height is the room?");
        input = reader.next();
        h = Double.parseDouble(input.trim());

        if(ceiling){
            return get_area_RECT_CEIL(w, h, l);
        }else{
            return get_area_RECT(w, h, l);
        }

    }

    //get area of a triangular room with ceiling
    public static double get_area_TRI_CEIL(double w, double h, double l){
        double area_tri = 0.5*w*h;
        //need to get the length of the slant, use pythagoras
        double slant = Math.sqrt(Math.pow((0.5*w),2)+Math.pow(h,2));
        double area_rect = slant*l;

        return area_rect*2+area_tri*2;
    }

    //get area of a triangular room without ceiling
    public static double get_area_TRI(double w, double h, double l){
        double area_tri = 0.5*w*h;
        return area_tri*2;
    }

    //get area of a rectangular room with ceiling
    public static double get_area_RECT_CEIL(double w, double h, double l){
        return w*h*2+l*h*2+w*l;
    }

    //get area of a rectangular room without ceiling
    public static double get_area_RECT(double w, double h, double l){
        return w*h*2+l*h*2;
    }

    //get area of a circular room with ceiling
    public static double get_area_CIRC_CEIL(double r, double h){
        double circumference = 2*Math.PI*r;
        double area = r*r*Math.PI;
        return circumference*h+area;
    }

    //get area of a circular room without ceiling
    public static double get_area_CIRC(double r, double h){
        double circumference = 2*Math.PI*r;
        return circumference*h;
    }

    //get area of a semicircular room with ceiling
    public static double get_area_SEMI_CEIL(double d, double h){
        double r=d/2;
        double circumference = 2*Math.PI*r;
        double area = r*r*Math.PI/2;
        double rect = d*h;
        return circumference*h/2+area+rect;
    }

    //get area of a semicircular room without ceiling
    public static double get_area_SEMI(double d, double h){
        double r=d/2;
        double circumference = 2*Math.PI*r;
        double rect = d*h;
        return circumference*h/2+rect;
    }

}