
public class Measurement {
    
    //FIELDS
    double quantity;
    String measurement;
    
    //CONSTRUCTOR
    public Measurement (double q, String m) {
        this.quantity = q;
        this.measurement = m;
    }
    
    //takes in the quantity information and converts it to scale
    public void getConversion() {
        if (quantity > 1) {
            quantity = quantity/quantity;
            convert();
        }
        else {
            quantity = 1/quantity * quantity;
            convert();
        }
    }
    
    public void convert() {
        double temp = 1;
        
        //g
        if (measurement.equals("g")) {
            measurement = "g";
        }
        
        //ml
        if (measurement.equals("ml")) {
            measurement = "ml";
        }
        
        //l
        if (measurement.equals("l")) {
            temp = 1000;
            measurement = "ml";
        }
        
        //oz
        if (measurement.equals("oz")) {
            temp = 28;
            measurement = "g";
        }
        
        //pound
        if (measurement.equals("pound")) {
            temp = 454;
            measurement = "g";
        }
        
        //cup
        if (measurement.equals("cup")) {
            temp = 240;
            measurement = "ml";
        }
        
        //teaspoon
        if (measurement.equals("tsp")) {
            temp = 5;
            measurement = "ml";
        }
        
        //tablespoon
        if (measurement.equals("tbsp")) {
            temp = 15;
            measurement = "ml";
        }
        
        if (measurement.equals("large")) {
            temp = 50;
            measurement = "g";
        }
        
        if (measurement.equals("small")) {
            temp = 40;
            measurement = "g";
        }
        
        quantity = quantity*temp;
    }
}
