import java.util.ArrayList;

public class Category {
    
    //FIELDS
    String group;
    ArrayList nutrition;
    ArrayList<ArrayList<String>> ingredient;
    
    //CONSTRUCTOR
    public Category (String g) {
        this.group = g;
        this.nutrition = new ArrayList();
        this.ingredient = new ArrayList<ArrayList<String>>();
    }
    
    public String print() {
        String printLine = group + nutrition.toString() + ingredient.toString();
        return printLine;
    }
    
}
