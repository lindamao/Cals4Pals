import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Ingredient {   
    
    //FIELDS
    ArrayList group;
    ArrayList ingredientInfo;
    String[] nutritionFacts;
    double quantity;
    String measurement;
    double multiple;
    
    //CONSTRUCTOR
    public Ingredient (ArrayList g, ArrayList ii, String[] n) {
        this.group = g;
        this.ingredientInfo = ii;
        this.nutritionFacts = n;
    }
    
    //METHODS
    
    //gets ingredient and matches it to database
    public String getIngredient (String i) {
        String ingredient;
        
        if (i.equals(ingredientInfo.get(0))) { //gets matched ingredient
            ingredient = (ingredientInfo.get(0)).toString();
        }
        else {
            ingredient = "Not Found"; //if ingredient is not in the database
        }
            
        return ingredient;
    }
    
    //gets calorie of ingredient
    public double getCalorie (String i, double q, String m) {
        double calorie = 0;
        multiple = 0;
        
        String measurementString = ingredientInfo.get(1).toString(); //the measurement input
        
        if (m.equals("ml")) { //for conversions with ml
            if (measurementString.contains(" mL")) { //if the database has information with ml
                Scanner s = new Scanner(measurementString);
                while (!s.hasNextDouble()) {
                    s.next();
                }
                double newMeasure = s.nextDouble(); //gets the mL value from database
                multiple = newMeasure;
            }
            else { //if database doesn't include ml in it --> use g instead (brute way to do this, not accurate, doesn't take into account density)
                measurementString = ingredientInfo.get(2).toString();
                multiple = Double.parseDouble(measurementString);
            }
        }
        else {
            measurementString = ingredientInfo.get(2).toString();
            multiple = Double.parseDouble(measurementString);
        }
        
        calorie = (Double.parseDouble(ingredientInfo.get(3).toString())/multiple)*q; //converts the calorie information based on quantity scaling
        return calorie;
    }
    
    //method that gets the multiple to be used in nutrition fact conversions
    public double getMultiple () {
        return multiple;
    }
    
    //gets nutrition fact of ingredient
    public String[] getNutritionFacts (String i) {
        String[] amount = new String [nutritionFacts.length];
        
        for (int j = 0; j < nutritionFacts.length; j++) {
            for (int k = 0; k < group.size(); k++) {
                //if the ingredient has a nutritional category that matches with on in the database
                if (group.get(k).equals(nutritionFacts[j])) {
                    amount[j] = ingredientInfo.get(k).toString(); //sets the respective nutrition value to its category
                }
                else {
                    continue;
                }
            }
        }
        return amount;
    }
    
    //displays the ingredient information
    public String print() {
        String printLine = group.toString() + ingredientInfo.toString();
        return printLine;
    }
    
}
