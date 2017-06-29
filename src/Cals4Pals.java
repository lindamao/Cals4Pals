
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


public class Cals4Pals extends javax.swing.JFrame {   

    //FIELDS 
    double quantity, ingredientCalorie, multiple;
    String measurement, ingredient, ingredientInfo, matchedIngredients;
    String[] nutritionAmount = new String [32];
    double[] nutritionAmountNumber = new double[32];
    double[] totalNutrition = new double [32];
    double[] dailyPercentage = new double [32];
    String[] percentage = new String [32];
    static String[] nutrition;
    static int file;
    static String[] cn;
    static String[] ingredientList;
    static int numLines, totalLines;
    double totalCalorieCount = 0;
    String quantityString;
    
    static Category[] category = new Category [ 17 ]; 
    static Ingredient[] newIngredient = new Ingredient[24134];
    
    int[] fileLines = {92, 48, 133, 90, 12, 41, 42, 61, 74, 51, 142, 27, 40, 23, 33, 75, 114};
    
    static DefaultComboBoxModel all, bakedGoods, beverages, breads, dairy, eggs, fastFood, fatsOils, fish, fruit, legumes, meat, misc, mixed, snacks, soups, sugars, vegetables;
    
    //sets the drop down menu models for each category
    public void setCategory() {
        
        String[] allIngredients = new String[1098];
        
        //set up combo box for all ingredients listed ("all" category selected)
        for (int i = 0; i < 1098; i++) {
            allIngredients[i] = newIngredient[i].ingredientInfo.get(0).toString();
        }
        all = new DefaultComboBoxModel(allIngredients);
        
        //sets up boundaries for combobox
        int low = 0;
        int high = 92;
        
        //sets up ingredients in combo box based on selected category
        for (int i = 0; i < fileLines.length; i++) {
            int n = 0;
            String[] categoryIngredients = new String[high]; //makes array of the category ingredients based on the boundaries
            for (int j = low; j < categoryIngredients.length; j++) {
                categoryIngredients[n] = newIngredient[j].ingredientInfo.get(0).toString(); //gets ingredient name
                n++;
            }
            
            //resets boundaries for each category
            low = high;
            if (i < 16) {
                high = high + fileLines[i+1];
            }
            
            //enters respective ingredients into combobox
            if (i == 0)
                bakedGoods = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 1)
                beverages = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 2)
                breads = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 3)
                dairy = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 4)
                eggs = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 5)
                fastFood = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 6)
                fatsOils = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 7)
                fish = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 8)
                fruit = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 9)
                legumes = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 10)
                meat = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 11)
                misc = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 12)
                mixed = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 13)
                snacks = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 14)
                soups = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 15)
                sugars = new DefaultComboBoxModel(categoryIngredients);
            else if (i == 16)
                vegetables = new DefaultComboBoxModel(categoryIngredients);
        }
    }
    
    //reads in database
    public static void readDB() throws IOException {
        FileReader r = new FileReader("Nutrition.txt");
        Scanner s = new Scanner(r);
        nutrition = s.nextLine().split("\t"); //splits each question on the first line into a an array
        
        //initial declarations
        file = 0;
        totalLines = 0;
        
        //reads each file from folder
        //String targetDirNew = "H://Profile//Desktop//Cals 4 Pals//Nutrition Database/"; //school
        String targetDir = "C://Users///Linda//Desktop//Cals 4 Pals//Nutrition Database/"; //home
        File dir = new File(targetDir);
        File[] files = dir.listFiles();
        
        //loops that runs through each fine
        for (File f : files) {
            if (f.isFile()) {
                numLines = 0;
                BufferedReader inputStream = null;
                
                try {
                    inputStream = new BufferedReader (new FileReader(f));
                    category[file] = new Category (f.getName()); //gets name of file

                    //gets first line of file (category name)
                    String categoryName = inputStream.readLine();
                    cn = categoryName.split("\t"); //split line by tab
                    
                    for (int i = 0; i < cn.length; i++) { //puts category individually in an array
                        category[file].nutrition.add(cn[i]);
                    }
                                        
                    String line;
                    
                    //reads each line in file
                    while ((line = inputStream.readLine()) != null) {
                        String[] info = line.split("\t"); //split line by tab
                        ingredientList = new String[info.length]; //create array of each item
                                                
                        for (int i = 0; i < info.length; i++) { //puts each category in each line in an array
                            ingredientList[i] = info[i]; //adds each item to array
                            category[file].ingredient.add(new ArrayList<String>()); //adds empty arraylists of each ingredient
                            category[file].ingredient.get(numLines).add(ingredientList[i]); //add information for each ingredient into inner arraylist
                            newIngredient[totalLines] = new Ingredient(category[file].nutrition, category[file].ingredient.get(numLines), nutrition); //stores values in Ingredient class
                        }
                        numLines++; //increments
                        totalLines++;
                    }
                    file++; //increment
                }
                
                finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }                
            }
        }
    }
    
    public Cals4Pals() {
        initComponents();
        Autocomplete.enable(ingredientInput); //initiates the autocomplete search
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        tabs = new javax.swing.JTabbedPane();
        ingredientsTab = new javax.swing.JPanel();
        ingredientTitle = new javax.swing.JLabel();
        quantityLabel = new javax.swing.JLabel();
        quantityInput = new javax.swing.JTextField();
        measurementLabel = new javax.swing.JLabel();
        measurementInput = new javax.swing.JComboBox();
        ingredientName = new javax.swing.JLabel();
        inputIngredients = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ingredientTable = new javax.swing.JTable();
        ingredientInput = new javax.swing.JComboBox();
        foodCategory = new javax.swing.JComboBox();
        calorieCounterTab = new javax.swing.JPanel();
        calorieCounterLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        caloriesTable = new javax.swing.JTable();
        totalCalorieLabel = new javax.swing.JLabel();
        totalCalories = new javax.swing.JLabel();
        nutritionFactsTab = new javax.swing.JPanel();
        nutritionFactsLabel = new javax.swing.JLabel();
        caloriesLabel = new javax.swing.JLabel();
        caloriesNutritionFacts = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        nutritionFactTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        appTitle = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tabs.setBackground(javax.swing.UIManager.getDefaults().getColor("tab_sel_fill_bright"));
        tabs.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tabs.setFont(new java.awt.Font("HelvLight", 0, 14)); // NOI18N

        ingredientsTab.setBackground(new java.awt.Color(255, 255, 255));
        ingredientsTab.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ingredientsTab.setToolTipText("");
        ingredientsTab.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ingredientsTab.setDoubleBuffered(false);
        ingredientsTab.setPreferredSize(new java.awt.Dimension(600, 391));

        ingredientTitle.setFont(new java.awt.Font("HelvLight", 0, 14)); // NOI18N
        ingredientTitle.setText("Enter ingredient information:");

        quantityLabel.setFont(new java.awt.Font("HelvLight", 0, 12)); // NOI18N
        quantityLabel.setText("Quantity");
        quantityLabel.setToolTipText("When entering mixed fractions, put a space between the whole number and the fraction ");

        quantityInput.setFont(new java.awt.Font("HelvLight", 0, 11)); // NOI18N
        quantityInput.setText("1");
        quantityInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityInputActionPerformed(evt);
            }
        });

        measurementLabel.setFont(new java.awt.Font("HelvLight", 0, 12)); // NOI18N
        measurementLabel.setText("Measurement");

        measurementInput.setFont(new java.awt.Font("HelvLight", 0, 11)); // NOI18N
        measurementInput.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "g", "ml", "l", "cup", "tbsp", "tsp", "small", "large" }));

        ingredientName.setFont(new java.awt.Font("HelvLight", 0, 12)); // NOI18N
        ingredientName.setText("Ingredient");
        ingredientName.setToolTipText("The ingredient box is searchable. Search for your ingredient in all categories, or look for them in specific categories.");

        inputIngredients.setFont(new java.awt.Font("HelvLight", 0, 12)); // NOI18N
        inputIngredients.setText("Enter");
        inputIngredients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputIngredientsActionPerformed(evt);
            }
        });

        ingredientTable.setFont(new java.awt.Font("HelvLight", 0, 11)); // NOI18N
        ingredientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Quantity", "Measurement", "Ingredient Name"
            }
        ));
        ingredientTable.setToolTipText("Drag the headers to change the length of each column display.");
        jScrollPane1.setViewportView(ingredientTable);

        ingredientInput.setEditable(true);
        ingredientInput.setFont(new java.awt.Font("HelvLight", 0, 11)); // NOI18N
        ingredientInput.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Biscuit, plain or buttermilk, fast food", "Biscuit, plain or buttermilk, from mix, baked", "Biscuit, plain or buttermilk, homemade", "Biscuit, plain, refrigerated dough, baked", "Croissant, butter", "Muffin, blueberry, from mix, prepared", "Muffin, bran, from mix, prepared", "Muffin, bran, homemade", "Muffin, carrot, commercial", "Muffin, chocolate chip, commercial", "Muffin, fruit, commercial", "Muffin, fruit, homemade", "Breakfast bar, Oatmeal to Go�", "Cereal bar, fruit filled (Nutri-Grain�)", "Granola bar, hard, chocolate chip", "Granola bar, hard, plain", "Granola bar, soft, chocolate chip, graham and -marshmallow", "Granola bar, soft, nuts and raisins", "Granola bar, soft, peanut butter, chocolate coated", "Granola bar, soft, plain", "Muffin bar (Hop&Go�, Sweet Mornings�)", "Chocolate chip, commercial", "Chocolate chip, homemade", "Chocolate chip, refrigerated dough, baked", "Chocolate coated marshmallow", "Chocolate sandwich", "Coconut macaroons, homemade", "Fig", "Ginger snaps", "Graham crackers, plain or honey", "Molasses", "Oatmeal, with raisins, commercial", "Oatmeal, without raisins, homemade", "Peanut butter sandwich", "Peanut butter, homemade", "Shortbread, commercial, plain", "Shortbread, homemade", "Animal crackers (arrowroot, social tea)", "Sugar cookies, commercial", "Sugar cookies, homemade", "Vanilla wafers", "Angelfood, commercial (25 cm diam)", "Angelfood, from mix (25 cm diam)", "Banana bread, homemade (11 cm X 6 cm X 1 cm)", "Boston cream pie, commercial", "Brownies, commercial (5 cm X 5 cm)", "Brownies, homemade (5 cm X 5 cm)", "Carrot, homemade with cream cheese icing (2 layer, 23 cm diam)", "Cheesecake, commercial (15 cm diam)", "Cheesecake, from mix, no-bake type (20 cm diam)", "Cheesecake, plain, homemade with cherry topping (20 cm diam)", "Chocolate, from mix, with icing (23 cm diam)", "Chocolate, frozen, commercial, with chocolate icing (1 layer, 5 cm X 20 cm diam)", "Chocolate, homemade, with icing (2 layer, 23 cm diam)", "Coffee cake, cinnamon with crumb topping, commercial", "Coffee cake, cinnamon with crumb topping, from mix (20 cm X 15 cm)", "Fruitcake, commercial", "Gingerbread, from mix (23 cm X 23 cm)", "Pound cake, homemade (23 cm X 13 cm X 7.5 cm)", "Shortcake, biscuit-type, homemade (4 cm X 7.5 cm diam)", "Sponge, commercial, individual shell", "Sponge, homemade (25 cm diam)", "White, from mix, with icing (2 layer, 23 cm diam)", "White, homemade, with icing (2 layer, 23 cm diam)", "White, frozen, commercial, with icing (1 layer, 5 cm X 20 cm diam)", "Yellow, from mix, with icing (2 layer, 23 cm diam)", "Apple, commercial, 2 crust (23 cm diam)", "Apple, homemade, 2 crust (23 cm diam)", "Banana cream, from mix, no-bake type (23 cm diam)", "Butter tart", "Cherry, commercial, 2 crust (23 cm diam)", "Chocolate cream, commercial (20 cm diam)", "Coconut cream, commercial (20 cm diam)", "Fried pie, fruit (13 cm X 10 cm)", "Lemon meringue, commercial (20 cm diam)", "Mincemeat pie, homemade, 2 crust (23 cm diam)", "Pecan, commercial (20 cm diam)", "Pumpkin, commercial (20 cm diam)", "Sugar pie, homemade, 1 crust", "Apple crisp, homemade", "Bread pudding with raisins, homemade", "Danish pastry, cinnamon (11 cm diam)", "Danish pastry, fruit (11 cm diam)", "Date squares, homemade", "Doughnut, cake-type, plain (8 cm diam)", "Doughnut, cake-type, plain, chocolate coated (9 cm diam)", "Doughnut, yeast-leavened, honey bun, glazed (9 cm x 6 cm)", "Doughnut, yeast-leavened, jelly filled (9 cm X 6 cm)", "Eclairs, custard filled, chocolate glaze", "Rice Krispies Squares�, commercial", "Toaster pastries (Pop-Tarts�), brown sugar & cinnamon", "Toaster pastries (Pop-Tarts�), fruit, frosted", "Chai, latte", "Coffee, brewed", "Coffee, brewed, decaffeinated", "Coffee, instant, regular, powder + water", "Coffee, latte", "Coffee, substitute, powder + water", "Iced cappuccino - original - with cream (Tim Hortons�)", "Iced cappuccino - with 2% milk (Tim Hortons�)", "Iced coffee, Frappuccino (Starbucks�)", "Iced tea, lemon flavor, ready-to-drink", "Iced tea, lemon flavour, powder + water", "Tea, brewed", "Tea, brewed, herbal", "Club soda", "Cola", "Cola, aspartame sweetened", "Cola, decaffeinated", "Ginger ale", "Lemon-lime soda", "Non cola soda, aspartame sweetened", "Orange soda", "Tonic water", "Citrus juice drink, frozen, diluted (Five Alive�)", "Fruit flavour drink, low Calorie, powder + water (Crystal Light�)", "Fruit punch flavour drink, powder (Kool-Aid�) + water", "Fruit punch flavour drink, vitamin C added, powder + water", "Fruit punch juice drink, ready-to-drink (Sunny D�)", "Lemonade, pink or white, frozen, diluted", "Mixed vegetable and fruit juice drink, ready-to-drink (V8 Splash�)", "Orange drink, vitamin C added (Hi-C�), ready-to-drink", "Orange drink, vitamin C added (Tang�, Quench�, Rise'n Shine�), powder + water", "Sports drink, fruit flavour, low Calorie, ready-to-drink (Gatorade�, Powerade�)", "Sports drink, fruit flavour, ready-to-drink (Gatorade�, Powerade�)", "Water, mineral (Perrier�)", "Water, municipal", "Beer, de-alcoholized, (Labbat .5�)", "Beer, high alcohol (7%alcohol by volume)", "Beer, light (4% alcohol by volume)", "Beer, regular (5% alcohol by volume)", "Cocktail, daiquiri", "Cocktail, margarita", "Liqueur, coffee and cream", "Sangria", "Spirits (gin, rum, vodka, whisky)", "Vodka cooler", "Wine, dessert, sweet", "Wine, table, red", "Wine, table, white", "Chickpea flour", "Cornmeal, dry", "Oat bran, dry", "Oat flour", "Potato flour", "Rice flour", "Rye flour, light", "Soy flour", "Wheat bran", "Wheat flour, all purpose", "Wheat flour, bread", "Wheat flour, cake", "Wheat flour, whole grain", "Wheat germ, toasted", "Bagel, plain (10 cm diam)", "Bannock", "Bread, French or Vienna", "Bread, Italian", "Bread, mixed-grain", "Bread, naan", "Bread, oatmeal", "Bread, pita, white (17 cm diam)", "Bread, pita, whole wheat (17 cm diam)", "Bread, pumpernickel", "Bread, raisin", "Bread, rye", "Bread, white, Calorie-reduced", "Bread, white, commercial", "Bread, white, homemade with 2% milk", "Bread, whole wheat, commercial", "Bread, whole wheat, homemade with 2% milk", "English muffin, white, toasted", "English muffin, whole wheat, toasted", "Fry bread", "Roll, crusty (kaiser)", "Roll, dinner, white", "Roll, dinner, whole wheat", "Roll, hamburger or hotdog, white", "Roll, hamburger or hotdog, whole wheat", "Bread stick, plain (19 cm X 2 cm)", "Bread stuffing, dry mix, prepared", "Croutons, plain", "Dumpling", "Matzo, plain", "Taco shell, baked (13 cm diam)", "Tortilla, corn (15 cm diam)", "Tortilla, wheat (20 cm diam)", "French toast, frozen, ready to heat, heated", "French toast, homemade", "Pancake, buckwheat, prepared from mix (13 cm diam)", "Pancake, homemade with butter and syrup (13 cm diam)", "Pancake, plain, from complete mix (13 cm diam)", "Pancake, plain, frozen, ready-to-heat (13 cm diam), heated", "Pancake, plain, homemade (13 cm diam)", "Potato pancake, homemade (8 cm diam)", "Waffle, homemade", "Waffle, plain, frozen, ready-to-heat, heated", "Barley, pearled, cooked", "Bulgur, cooked", "Couscous, cooked", "Quinoa, cooked", "Macaroni, cooked", "Noodles, Chinese, chow mein", "Noodles, egg, cooked", "Pasta, fresh-refrigerated, cooked", "Pasta, fresh-refrigerated, spinach, cooked", "Ramen noodles, chicken flavour, dry", "Rice noodles, cooked", "Rice, brown, long-grain, cooked", "Rice, white, long-grain, cooked", "Rice, white, long-grain, instant, prepared", "Rice, white, long-grain, parboiled, cooked", "Rice, wild, cooked", "Soba noodles, cooked", "Spaghetti, cooked", "Spaghetti, whole wheat, cooked", "Cream of wheat, regular", "Oat bran, cooked", "Oatmeal, instant, apple-cinnamon", "Oatmeal, instant, regular", "Oatmeal, large flakes/quick", "Red River, Robin Hood�", "All Bran Buds with psyllium, Kellogg's�", "All Bran, Kellogg's�", "Almond Raisin Muslix, Kellogg's�", "Alpha-Bits, Post�", "Bran Flakes, Post�", "Cap'n Crunch, Quaker�", "Cheerios, Honey Nut, General Mills�", "Cheerios, regular General Mills�", "Cinnamon Toast Crunch, General Mills�", "Corn Bran, Quaker�", "Corn Flakes, Kellogg's�", "Corn Pops, Kellogg's�", "Fibre 1, General Mills�", "Froot Loops, Kellogg's�", "Frosted Flakes, Kellogg's�", "Fruit & Fibre, Dates/Raisins/Walnuts, Post�", "Granola with Raisins, low fat, Kellogg's�", "Granola with Raisins, Rogers�", "Grape-Nuts, Post�", "Harvest Crunch, regular, Quaker�", "Honeycomb, Post�", "Just Right, Kellogg's�", "Life, Quaker�", "Lucky Charms, General Mills�", "Mini-Wheats with White Frosting, Kellogg's�", "Muesli, President's Choice�", "Nesquik, General Mills�", "Oatmeal Crisp Almond, General Mills�", "Oatmeal Crisp Maple Walnut, General Mills�", "Puffed Wheat, Quaker�", "Raisin Bran, Kellogg's�", "Reese's Puffs, General Mills�", "Rice Krispies, Kellogg's�", "Shredded Wheat, Post�", "Shreddies, Post�", "Special K, Kellogg's�", "Sugar Crisp, Post�", "Trix, General Mills�", "Weetabix�", "Cheese crackers, small", "Melba toast, plain", "Milk crackers", "Rusk toast", "Rye wafers, plain", "Saltine (oyster, soda, soup)", "Saltine (oyster, soda, soup), unsalted top", "Standard-type (snack-type) (Ritz�)", "Standard-type, reduced sodium (Ritz�)", "Wheat crackers", "Wheat crackers, low fat", "Whole wheat crackers", "Buttermilk", "Milk, chocolate, 1% M.F.", "Milk, chocolate, 2% M.F.", "Milk, skim", "Milk, partly skimmed, 1% M.F.", "Milk, partly skimmed, 2% M.F.", "Milk, partly skimmed, 2% M.F., with added milk solids", "Milk, whole, 3.3% M.F.", "Rice beverage, flavoured and unflavoured, enriched", "Soy beverage, chocolate, enriched", "Soy beverage, original and vanilla, enriched", "Soy beverage, unsweetened, enriched", "Milk, condensed, sweetened, canned (Eagle Brand�)", "Milk, evaporated, partly skimmed, canned, diluted, 2% M.F.", "Milk, evaporated, partly skimmed, canned, undiluted, 2% M.F.", "Milk, evaporated, skim, canned, diluted, 0.2% M.F.", "Milk, evaporated, skim, canned, undiluted, 0.2% M.F.", "Milk, evaporated, whole, canned, diluted, 7.8% M.F.", "Milk, evaporated, whole, canned, undiluted, 7.8% M.F.", "Milk, reconstituted, from skim milk powder", "Skim milk powder", "Chocolate milk, chocolate flavour powder + 2% milk", "Chocolate milk, syrup + 2% milk", "Eggnog", "Hot chocolate, aspartame sweetened, powder + water", "Hot chocolate, homemade with cocoa + 2% milk", "Hot chocolate, powder + 2% milk", "Hot chocolate, powder + water", "Instant breakfast powder + 2% milk", "Milk shake, chocolate", "Milk shake, vanilla", "Drinkable yogourt", "Fresh cheese (Danimal�, Minigo�)", "Kefir, plain", "Yogourt parfait with berries and granola", "Yogourt, plain, 1-2% M.F.", "Yogourt, plain, 2-4% M.F.", "Yogourt, plain, fat-free", "Yogourt, vanilla or fruit, 1-2% M.F.", "Yogourt, vanilla or fruit, fat-free", "Yogourt, vanilla or fruit, fat-free with sugar substitute", "Blue", "Brick", "Brie", "Camembert", "Cheddar", "Cheddar, low fat (18% M.F.)", "Cottage cheese (1% M.F.)", "Cream cheese, light", "Cream cheese, regular", "Edam", "Feta", "Goat cheese, soft", "Gouda", "Gruyere", "Imitation cheese", "Mozzarella (22.5% M.F.)", "Mozzarella, partially skimmed (16.5% M.F.)", "Parmesan, grated", "Processed cheese food, thin slices", "Processed cheese food, thin slices, light", "Processed cheese spread (Cheez Whiz�)", "Processed cheese spread, light (Light Cheez Whiz�)", "Ricotta cheese, partly skimmed milk", "Romano, grated", "Swiss (Emmental)", "Swiss, processed, thin slices", "Half and half, 10% M.F.", "Sour cream, 14% M.F.", "Sour cream, light, 5% M.F.", "Table cream (coffee cream), 18% M.F.", "Whipped, pressurized", "Whipping cream, 35% M.F., not whipped", "Whipping cream, 35% M.F., sweetened, whipped", "Coffee whitener, frozen liquid", "Coffee whitener, powdered", "Coffee whitener, powdered, light", "Dessert topping, frozen", "Dessert topping, frozen, low fat", "Dessert topping, powdered, prepared with 2% milk", "Dessert topping, pressurized", "All flavours, instant, from mix, prepared with 2% milk", "Chocolate, ready-to-eat", "Chocolate, ready-to-eat, fat-free", "Rice, homemade", "Rice, ready-to-eat", "Tapioca, ready-to-eat", "Tapioca, ready-to-eat, fat-free", "Vanilla, ready-to-eat", "Vanilla, ready-to-eat, fat-free", "Egg substitute, frozen (yolk replaced), cooked", "Egg white, cooked", "Egg yolk, cooked", "Egg", "0.89", "Egg, fried", "Egg, hard-boiled", "Egg, poached", "Eggs benedict", "Eggs, scrambled, made with 2 eggs", "Omelet, cheese, made with 2 eggs", "Omelet, spanish, made with 2 eggs (mushrooms, onions, green peppers, tomatoes)", "Omelet, western, made with 2 eggs (green peppers, ham, onions)", "Milk shake, chocolate", "Milk shake, vanilla", "French fries", "Garlic bread", "Onion rings, breaded and fried", "Zucchini, breaded and fried, sticks", "Breakfast bagel, with ham, egg and cheese", "Breakfast biscuit with egg, cheese and bacon", "Breakfast English muffin with egg, cheese and bacon", "Cheeseburger, double patty + condiments + vegetables", "Cheeseburger, single patty, plain", "Chicken sandwich, breaded chicken + condiments + vegetables", "Chicken sandwich, grilled chicken + condiments + vegetables", "Donair / Gyro", "Fish sandwich with breaded fish", "Hamburger, double patty + condiments", "Hamburger, single patty, plain", "Submarine sandwich (6 inches), vegetarian", "Submarine sandwich (6 inches), with cold cuts", "Submarine sandwich (6 inches), with grilled/roasted chicken", "Submarine sandwich (6 inches), with tuna", "Veggie burger, single patty + condiments + vegetables", "Wrap sandwich, chicken ranch", "Beef and broccoli stir fry", "Chicken almond guy ding", "Chicken chow mein", "Chicken fried rice", "Egg roll", "General Tao/Tso chicken", "Hot and sour soup", "Sweet and sour chicken balls", "Won ton soup", "Pizza with cheese (medium - 12 inches)", "Pizza with cheese and pepperoni (medium - 12 inches)", "Pizza with cheese and vegetables (medium - 12 inches)", "Pizza with cheese, meat and vegetables (medium - 12 inches)", "Chicken, breaded and fried (pieces)", "Chicken, breaded and fried, boneless (nuggets)", "Chili con carne", "Corndog (Pogo�)", "Hot-dog, plain", "Becel�, tub, calorie-reduced, canola and safflower oils (non-hydrogenated)", "Becel�, tub, canola and safflower oils (non-hydrogenated)", "Butter", "Chefmaster�, tub, unspecified vegetable oils (hydrogenated)", "Imperial�, stick, soy and canola oils (hydrogenated)", "Imperial�, tub, soya oil (non-hydrogenated)", "Lactantia�, tub, soya oil (hydrogenated)", "Margarine, tub, composite", "Spread (20% butter / 80% margarine)", "Spread (50% butter / 50% margarine)", "Canola", "Corn", "Flaxseed", "Grapeseed", "Olive", "Peanut", "Sesame", "Soybean", "Sunflower", "Bacon grease", "Lard", "Shortening", "Blue cheese", "Blue cheese, low Calorie", "Creamy Caesar", "Creamy Caesar, low Calorie", "Creamy dressing, fat-free", "French", "French, low fat", "Italian", "Italian, low Calorie", "Mayonnaise", "Mayonnaise, light", "Non creamy dressing, fat-free", "Oil and vinegar", "Ranch", "Ranch, low fat", "Salad dressing, mayonnaise type", "Salad dressing, mayonnaise type, fat-free", "Salad dressing, mayonnaise type, light", "Thousand Island", "Thousand Island, low Calorie", "Anchovies, canned in oil, drained solids", "Arctic char, cooked", "Bass, mixed species, baked or broiled", "Burbot (loche), raw", "Catfish, channel, farmed, baked or broiled", "Cisco (lake herring, tullibee), baked or broiled", "Cisco (lake herring, tullibee), raw", "Cod, Atlantic, baked or broiled", "Cod, Atlantic, dried and salted, soaked in water", "Gefiltefish", "Grayling, baked or broiled", "Haddock, baked or broiled", "Halibut, Atlantic and Pacific, baked or broiled", "Herring, Atlantic, kippered", "Mackerel, Atlantic, baked or broiled", "Ocean Perch, Atlantic, baked or broiled", "Pickerel (Walleye), baked or broiled", "Pike, northern, baked or broiled", "Pollock, Atlantic, baked or broiled", "Salmon, Atlantic, farmed, baked or broiled", "Salmon, chum (keta), baked or broiled", "Salmon, chum (keta), canned, drained solids with bone, salted", "Salmon, chum (keta), canned, drained solids with bone, unsalted", "Salmon, coho, farmed, baked or broiled", "Salmon, eggs, raw", "Salmon, king or chinook, smoked, canned", "Salmon, pink, canned, drained with bones", "Salmon, smoked", "Salmon, smoked, lox", "Salmon, sockeye, baked or broiled", "Sardines, Atlantic, canned in oil, drained with bones", "Sardines, Pacific, canned in tomato sauce, drained with bones", "Smelt, breaded and fried", "Snapper, mixed species, baked or broiled", "Sole (flatfish), baked or broiled", "Trout, rainbow, farmed, baked or broiled", "Tuna, light, canned in water, drained, salted", "Tuna, light, canned with oil, drained, salted", "Turbot, baked or broiled", "Whitefish, lake, native, baked", "Clams, mixed species, boiled or steamed", "Clams, mixed species, canned, drained solids", "Crab, canned, drained", "Crab, snow, boiled or steamed", "Crayfish, mixed species, farmed, boiled or steamed", "Lobster, boiled or steamed", "Mussels, boiled or steamed", "Oysters, boiled or steamed", "Oysters, canned, solids and liquid", "Oysters, raw", "Scallops, cooked, steamed", "Shrimp, boiled or steamed", "Calamari, breaded and fried", "Caviar, black or red", "Crab cake", "Crab, imitation, made from surimi", "Fish cake", "Fish fillet, battered and fried", "Fish sticks, frozen, heated (10 cm x 2.5 cm x 1.3 cm)", "Shrimp, breaded and fried", "Tuna salad", "Apple with skin (7 cm.diam)", "Applesauce, unsweetened", "Apricots, dried", "Apricots, raw", "Avocado", "Banana", "Blackberries", "Blueberries, frozen, unsweetened", "Blueberries, raw", "Cherries, sweet", "Clementine", "Cranberries, dried, sweetened", "Dates, dried", "Figs, dried", "Figs, raw", "Fruit cocktail, canned, juice pack", "Fruit cocktail, canned, light syrup pack", "Fruit salad, tropical, canned, heavy syrup pack", "Fruit salad, tropical, canned, juice pack", "Grapefruit, pink or red", "Grapefruit, white", "Grapes", "Groundcherries", "Kiwifruit", "Lychees (litchis)", "Mango", "Melon, cantaloupe, cubes", "Melon, honeydew, cubes", "Melon, watermelon, cubes", "Nectarine", "Orange", "Papaya, cubes", "Peach", "Peach, canned halves or slices, juice pack", "Peach, canned halves or slices, light syrup pack", "Peach, canned halves or slices, water pack", "Pear with skin", "Pear, canned halves, juice pack", "Pear, canned halves, light syrup pack", "Pear, canned halves, water pack", "Pineapple, canned, juice pack", "Pineapple, cubes", "Plantain, baked or boiled, sliced", "Plum", "Pomegranate (9.5 cm diam)", "Prunes, dried", "Prunes, dried, cooked, without added sugar", "Raisins", "Raspberries", "Rhubarb, frozen, cooked, with added sugar", "Strawberries", "Strawberries, frozen, unsweetened", "Tangerine (mandarin)", "Tangerine (mandarin), canned, juice pack, drained", "Apple juice, ready-to-drink, vitamin C added", "Cranberry juice cocktail, ready-to-drink, vitamin C added", "Cranberry juice, unsweetened, ready-to-drink", "Cranberry-apple juice-drink, ready-to-drink, low Calorie, vitamin C added", "Grape juice, frozen, sweetened, diluted, vitamin C added", "Grape juice, ready-to-drink, vitamin C added", "Grapefruit juice, ready-to-drink unsweetened or freshly squeezed", "Grapefruit juice, ready-to-drink, sweetened", "Lemon juice, canned or bottled", "Lime juice, canned or bottled", "Nectar, apricot", "Nectar, mango", "Orange and grapefruit juice, ready-to-drink", "Orange juice, frozen, diluted", "Orange juice, ready-to-drink", "Orange juice, ready-to-drink, refrigerated, vitamin D and calcium added", "Orange, strawberry and banana juice, ready-to-drink", "Pineapple juice, ready-to-drink, vitamin C added", "Pomegranate juice, ready-to-drink", "Prune juice, ready-to-drink", "Meatless breaded chicken nuggets", "Meatless ground beef", "Soy patty", "Tofu, regular, firm and extra firm", "Tofu, silken, soft", "Vegetable patty", "Vegetarian luncheon meat", "Wiener, meatless", "Beans, baked, homemade", "Beans, baked, plain or vegetarian, canned", "Beans, baked, with pork, canned", "Beans, black, canned, not drained", "Beans, kidney, dark red, canned, not drained", "Beans, navy, canned, not drained", "Beans, pinto, canned, not drained", "Beans, refried, canned", "Beans, white, canned, not drained", "Black-eyed peas, canned, not drained", "Chickpeas (garbanzo beans), canned, not drained", "Falafel, homemade", "Hummus, commercial", "Lentils, boiled, salted", "Lentils, pink, boiled", "Peas, split, boiled", "Soybeans, boiled", "Peanut butter, chunk type, fat, sugar and salt added", "Peanut butter, natural", "Peanut butter, smooth type, fat, sugar and salt added", "Peanut butter, smooth type, light", "Peanuts, all types, shelled, oil-roasted, salted", "Peanuts, all types, shelled, roasted", "Almonds, dried", "Almonds, oil roasted", "Almonds, roasted, salted", "Brazil nuts, dried", "Cashews, roasted, salted", "Hazelnuts or filberts, dried", "Macadamia nuts, roasted, salted", "Mixed nuts, oil roasted, salted", "Mixed nuts, roasted", "Mixed nuts, roasted, salted", "Pecans, dried", "Pine nuts, pignolia, dried", "Pistachios, shelled, roasted, salted", "Walnuts, dried", "Almond butter", "Cashew butter", "Sesame butter, tahini", "Flaxseeds, whole and ground", "Pumpkin and squash seeds, kernels, dried", "Sunflower seed kernels, roasted, salted", "Blade roast, lean + fat, braised", "Blade steak, lean + fat, braised", "Composite, roast, lean + fat, cooked", "Composite, steak, lean + fat, cooked", "Cross rib roast, lean + fat, braised", "Eye of round roast, lean + fat, roasted", "Eye of round steak, lean + fat, braised", "Flank steak, lean + fat, braised", "Ground, extra lean, crumbled, pan-fried", "Ground, lean, crumbled, pan-fried", "Ground, medium, crumbled, pan-fried", "Ground, regular, crumbled, pan-fried", "Inside (top) round roast, lean + fat, roasted", "Inside (top) round steak, lean + fat, braised", "Outside (bottom) round roast, lean + fat, roasted", "Outside (bottom) round steak, lean + fat, braised", "Rib eye steak, lean + fat, broiled", "Rib steak, lean + fat, broiled", "Rump roast, lean + fat, broiled", "Short ribs, lean + fat, simmered", "Sirloin tip roast, lean + fat, roasted", "Standing rib roast, lean + fat, roasted", "Stewing beef, lean, simmered", "Strip loin ( New York ) steak, lean + fat, broiled", "T-Bone (Porterhouse) steak, lean + fat, broiled", "Tenderloin, steak, lean + fat, broiled", "Top sirloin steak, lean + fat, broiled", "Composite cuts, lean + fat, cooked", "Cutlets, grain-fed, pan-fried", "Cutlets, milk-fed, pan-fried", "Ground, broiled", "Leg, lean + fat, breaded, pan-fried", "Leg, lean + fat, roasted", "Loin, lean + fat, roasted", "Shoulder, whole, lean + fat, roasted", "Stewing meat, lean, braised", "Back ribs, lean + fat, roasted", "Centre cut, loin, chop, lean + fat, broiled", "Centre cut, loin, chop, lean + fat, pan-fried", "Ground, lean, pan-fried", "Ground, medium, pan-fried", "Leg, butt end, lean + fat, roasted", "Loin, rib end, lean + fat, broiled", "Loin, rib end, lean + fat, pan-fried", "Shoulder, butt, lean + fat, roasted", "Shoulder, whole, lean + fat, roasted", "Spareribs, lean + fat, braised", "Tenderloin, lean, roasted", "American, fresh, foreshank, lean + fat, cooked", "American, fresh, ground, cooked", "American, fresh, leg, whole, lean + fat, cooked", "American, fresh, loin, lean + fat, cooked", "American, fresh, rib, lean + fat, cooked", "American, fresh, shoulder, whole, lean+ fat, cooked", "New Zealand, frozen, composite, lean + fat, cooked", "New Zealand, frozen, foreshank, lean + fat, braised", "New Zealand, frozen, leg, whole, lean + fat, roasted", "New Zealand, frozen, loin, lean + fat, broiled", "Chicken, broiler, breast, meat and skin, roasted", "Chicken, broiler, breast, meat, roasted", "Chicken, broiler, drumstick, meat and skin, roasted", "Chicken, broiler, drumstick, meat, roasted", "Chicken, broiler, flesh and skin, roasted", "Chicken, broiler, flesh, roasted", "Chicken, broiler, thigh, meat and skin, roasted", "Chicken, broiler, thigh, meat, roasted", "Chicken, broiler, wing, meat and skin, roasted", "Chicken, cornish game hens, flesh and skin, roasted", "Chicken, ground, lean, cooked", "Duck, domesticated, roasted", "Duck, wild, cooked", "Goose, domesticated, flesh, roasted", "Goose, wild ( Canada goose), flesh, roasted", "Ptarmigan, flesh, cooked", "Spruce grouse, flesh, cooked", "Turkey, dark meat and skin, roasted", "Turkey, dark meat, roasted", "Turkey, ground, cooked", "Turkey, light meat and skin, roasted", "Turkey, light meat, roasted", "Bear, simmered", "Beaver, roasted", "Bison, roasted", "Caribou (reindeer), roasted", "Deer (venison), roasted", "Emu, inside drum, broiled", "Goat, roasted", "Horsemeat, roasted", "Moose, roasted", "Narwhal skin (muktuk), raw", "Ostrich, inside strip, cooked", "Rabbit, composite cuts, roasted", "Seal meat, boiled", "Heart, beef, simmered", "Kidney, beef, simmered", "Liver, beef, pan-fried", "Liver, chicken, pan-fried", "Liver, veal, pan-fried", "Thymus, veal, braised", "Tongue, beef, canned or pickled", "Back bacon, pork, grilled", "Bacon, pork, broiled, pan-fried or roasted", "Bacon, pork, broiled, pan-fried or roasted, reduced sodium", "Bologna (baloney), beef and pork", "Bologna (baloney), beef and pork, light", "Bologna (baloney), chicken", "Chicken, canned, flaked", "Corned beef, brisket, cooked", "Cottage roll, pork, lean and fat, roasted", "Creton", "Deli meat, beef, thin sliced", "Deli meat, chicken breast roll", "Deli meat, chicken breast, low fat", "Deli meat, ham, extra lean (5% fat)", "Deli meat, ham, regular (11% fat)", "Deli meat, mock chicken, loaved", "Deli meat, turkey breast", "Ham, extra lean, canned", "Ham, lean, canned", "Ham, flaked, canned", "Ham, lean and regular, roasted", "Ham, lean, roasted", "Kielbasa (Kolbassa), pork and beef", "Liver sausage (liverwurst), pork", "Pastrami, beef", "Pate, liver, canned", "Pepperoni, pork, beef", "Salami, beef and pork", "Salami, pork and beef, dry or hard", "Salami, pork and beef, reduced salt", "Sausage, Bratwurst, pork, cooked", "Sausage, Italian, pork, cooked", "Sausage, breakfast, pork and beef, cooked", "Sausage, breakfast, pork, cooked", "Sausage, turkey, cooked", "Summer sausage, beef", "Turkey, canned, flaked", "Vienna sausage (cocktail), beef and pork, canned", "Wiener (frankfurter), beef", "Wiener (frankfurter), beef and pork", "Wiener (frankfurter), beef and pork, light", "Wiener (frankfurter), chicken", "Bacon bits, simulated meat", "Ketchup", "Mustard", "Olives, pickled, canned or bottled", "Olives, ripe, canned, jumbo", "Pickle relish, sweet", "Pickles, cucumber, dill", "Pickles, cucumber, sweet, slices", "Salsa", "Cream cheese dip", "Onion dip", "Spinach dip", "Gravy, beef, canned", "Gravy, beef, dehydrated, prepared with water", "Gravy, chicken, canned", "Gravy, chicken, dehydrated, prepared with water", "Gravy, turkey, canned", "Gravy, unspecified, dehydrated, prepared with water", "Sauce, barbecue", "Sauce, cheese, dehydrated, prepared with 2% milk", "Sauce, cranberry, canned, sweetened", "Sauce, nacho cheese, ready-to-serve", "Sauce, soy", "Sauce, steak (HP�, A1�)", "Sauce, sweet and sour", "Sauce, teriyaki", "Sauce, white, medium, homemade with 2% milk", "Burrito with beans and cheese", "Burrito with beef, cheese and chilli", "Nachos with cheese", "Quesadilla with meat", "Taco salad", "Taco with beef, cheese, salsa + vegetables", "Club sandwich", "Egg salad", "Hot chicken sandwich", "Ham", "Roast beef", "Salmon salad", "Tuna salad", "Caesar", "Caesar salad with chicken", "Garden", "Greek", "Pasta salad with vegetables", "Lasagna with meat (7.5 cm x 9 cm)", "Lasagna, vegetarian (7.5 cm x 9 cm)", "Macaroni and cheese (Kraft Dinner�)", "Macaroni casserole with beef and tomato soup", "Spaghetti with cream sauce", "Spaghetti with meat sauce", "Beef pot pie, commercial, individual", "Beef stew", "Butter chicken", "Chicken fajita", "Chicken pot pie, commercial, individual", "Pad Tha�", "Poutine", "Samosa, vegetarian", "Shepherd's pie", "Stir fry with beef", "Stir fry with chicken", "Stir fry with tofu", "Sushi with fish", "Sushi with vegetables, no fish", "Sweet and sour meatballs", "Tourtiere, homemade (20 cm diam)", "Air-popped", "Caramel-coated", "Microwave, low fat and reduced salt", "Oil-popped, regular and microwaved", "Corn-based puffs or twists, cheese (Cheesies�)", "Potato chips made from dried potatoes, plain (Pringles�)", "Potato chips, baked, plain", "Potato chips, flavoured", "Potato chips, plain", "Tortilla chips, nacho flavoured (Doritos�)", "Tortilla chips, plain", "Banana chips", "Beef jerky (22 cm long)", "Beer nuts", "Bits and bites snack bites (Bits & Bites�)", "Fruit leather bar (Fruit to Go�)", "Pretzels, hard, plain, salted", "Pretzels, hard, plain, unsalted", "Rice cakes, plain", "Sesame sticks, salted", "Soybeans, roasted, salted", "Trail mix, regular", "Trail mix, tropical", "Beef or chicken, broth/bouillon", "Beef, chunky", "Chicken noodle, chunky", "Chicken noodle, low fat, reduced salt", "Chicken vegetable, chunky", "Clam chowder, Manhattan", "Minestrone, chunky", "Split pea with ham, chunky", "Vegetable, chunky", "Beef noodle", "Chicken broth", "Chicken noodle", "Cream of mushroom", "Tomato", "Tomato, reduced salt", "Vegetables with beef", "Vegetarian vegetable", "Clam chowder, New England", "Cream of chicken", "Cream of mushroom", "Cream of mushroom, reduced salt", "Cream of tomato", "Chicken noodle", "Minestrone", "Onion", "Ramen noodles, chicken flavour, cooked", "Tomato vegetable", "Chicken noodle", "Cream of vegetable", "French onion", "Lentil", "Split pea with ham", "Vegetable", "Brown sugar", "Honey", "Icing sugar (powdered)", "Sugar substitute, aspartame (Equal�)", "Sugar substitute, sucralose (Splenda�)", "White sugar (granulated)", "Chocolate syrup, thin type", "Corn syrup", "Maple syrup", "Molasses", "Pancake syrup", "Double fruit jam type spread", "Double fruit jam type spread, reduced sugar", "Jams and preserves", "Jelly", "Marmalade", "Chocolate topping, fudge-type", "Pie filling, cherry, canned", "Spread, chocolate hazelnut (Nutella�)", "Topping or spread, butterscotch", "Topping, strawberry", "Butterscotch", "Candy, chocolate covered, sweetened with sorbitol", "Caramel", "Chewing gum", "Chewing gum, sugarless", "Fruit leather", "Fudge, chocolate, homemade", "Fudge, vanilla, homemade", "Gumdrops", "Hard candy", "Hard candy, reduced sugar", "Jellybeans", "Licorice, strawberry (Twizzlers�)", "Marshmallows", "Sesame crunch (sesame snap)", "Skittles�", "Toffee", "Almonds, chocolate covered", "Caramel coated cookies, chocolate covered (Twix�)", "Caramel with nuts, chocolate covered (Turtles�)", "Caramel, chocolate covered (Rolo�, Caramilk�)", "Chocolate covered wafer (Kit Kat�, Coffee Crisp�)", "Chocolate malt-nougat and caramel, chocolate covered (Mars�)", "Chocolate, candy coated (M&M'S�, Smarties�)", "Chocolate, semisweet, bars or chips", "Coconut candy, chocolate covered (Bounty�, -Almond Joy�)", "Fondant, chocolate covered (After Eight�)", "Fudge, caramel and nuts, chocolate covered (Oh Henry!�)", "Milk chocolate and crisped rice (Nestle Crunch�)", "Milk chocolate, bars or chips", "Peanut butter cups (Reese's�)", "Peanuts, chocolate covered", "Raisins, chocolate covered (Glosette�)", "Toffee, chocolate covered (Skor�)", "Chocolate ice milk bar (Fudgesicle�)", "Frozen yogourt, chocolate", "Frozen yogourt, vanilla", "Fruit and juice bar", "Ice cream cone, vanilla, chocolate covered, with nuts", "Ice cream cone, vanilla, soft serve", "Ice cream sandwich", "Ice cream, chocolate", "Ice cream, dairy free", "Ice cream, strawberry", "Ice cream, vanilla, low fat", "Ice cream, vanilla, low fat, aspartame sweetened", "Ice cream, vanilla, premium", "Ice cream, vanilla, regular", "Popsicles", "Sherbet, orange", "Soft serve ice cream with Oreo� cookies (Blizzard�, McFlurry�)", "Chocolate mousse, homemade", "Gelatin dessert, calorie-reduced, prepared (Jello�)", "Gelatin dessert, prepared (Jello�)", "Alfalfa sprouts, raw", "Artichoke hearts, canned in water", "Artichoke hearts, marinated in oil", "Artichoke, boiled, drained", "Asparagus, canned, drained", "Asparagus, fresh or frozen, boiled, drained", "Bean sprouts, stir-fried", "Beans, lima, frozen, boiled, drained", "Beans, snap (green, yellow, Italian), canned, drained", "Beans, snap (green, yellow, Italian), fresh or frozen, boiled, drained", "Beets, pickled, sliced, not drained", "Beets, sliced, boiled, drained", "Beets, sliced, canned, drained", "Belgium endive, raw", "Bok Choy, Pak-Choi, shredded, boiled, drained", "Broccoli, chopped, boiled, drained", "Broccoli, chopped, raw", "Broccoli, frozen spears, boiled, drained", "Brussels sprouts, fresh or frozen, boiled, drained", "Cabbage, green, shredded, boiled, drained", "Cabbage, green, shredded, raw", "Cabbage, napa, shredded, boiled, drained", "Cabbage, red, shredded, raw", "Carrots, baby, raw", "Carrots, fresh or frozen, boiled, drained", "Carrots, raw", "Cauliflower, pieces, boiled, drained", "Cauliflower, pieces, raw", "Celery, raw", "Corn, sweet, canned, cream style", "Corn, sweet, canned, niblets", "Corn, sweet, on or off cob, fresh or frozen, boiled, drained", "Cucumber, peeled, raw", "Edamame", "Eggplant, pieces, boiled, drained", "Fiddleheads, frozen, boiled", "Fireweed leaves, raw", "Hearts of palm, canned", "Kale, chopped, boiled, drained", "Leeks, chopped, boiled, drained", "Lettuce, Boston, shredded", "Lettuce, iceberg, shredded", "Lettuce, looseleaf, shredded", "Lettuce, romaine, shredded", "Lettuce, spring mix (mesclun)", "Mushrooms, pieces, canned, drained", "Mushrooms, portobello, grilled", "Mushrooms, raw", "Mushrooms, shiitake, sliced, stir-fried", "Mushrooms, white, sliced, stir-fried", "Onions, green (scallion), raw", "Onions, yellow, chopped, raw", "Onions, yellow, chopped, sauteed", "Parsnip, sliced, boiled, drained", "Peas, green, canned, drained", "Peas, green, frozen, boiled, drained", "Peas, snowpeas, boiled, drained", "Peas, snowpeas, raw", "Pepper, jalapeno, raw", "Pepper, sweet, green, raw", "Pepper, sweet, green, sauteed", "Pepper, sweet, red, raw", "Pepper, sweet, red, sauteed", "Pepper, sweet, yellow, raw", "Pepper, sweet, yellow, sauteed", "Potato, baked, flesh", "Potato, baked, flesh and skin", "Potato, boiled without skin", "Potato, boiled, flesh and skin", "Potato, canned, drained", "Potato, microwaved, flesh and skin", "Potato, microwaved, peeled after cooking", "Potatoes, French fried, frozen, home-prepared in oven", "Potatoes, hashed brown, plain, frozen, heated", "Potatoes, mashed, dried, with 2% milk and margarine", "Potatoes, mashed, homemade with 2% milk and margarine", "Potatoes, scalloped, from mix with water, with 2% milk and margarine", "Potatoes, scalloped, homemade", "Pumpkin, canned", "Radicchio, chopped", "Radishes", "Rutabaga (yellow turnip), diced, boiled, drained", "Sauerkraut, canned, not drained", "Seaweed, dulse, dried", "Spinach, boiled, drained", "Spinach, chopped, raw", "Squash, acorn, cubed, baked", "Squash, butternut, cubed, baked", "Squash, spaghetti, baked", "Sweet potato, baked, peeled after cooking", "Sweet potato, boiled without skin", "Swiss chard, chopped, boiled, drained", "Tomatoes, canned, stewed", "Tomatoes, canned, whole", "Tomatoes, raw", "Tomatoes, sun-dried", "Tomatoes, sun-dried, packed in oil, drained", "Turnip (white turnip), cubed, boiled, drained", "Vegetables, Asian mix (broccoli, carrots, green beans, \"mini corn\", snow peas, sweet red pepper), frozen, boiled, drained", "Vegetables, broccoli and cauliflower, frozen, boiled, drained", "Vegetables, mixed (corn, lima beans, snap beans, peas, carrots), frozen, boiled, drained", "Vegetables, peas and carrots, canned, not drained", "Zucchini, raw, slices", "Zucchini, sliced, boiled, drained", "Carrot juice", "Coleslaw with dressing, homemade", "Potato salad, homemade", "Tomato clam cocktail", "Tomato juice", "Tomato juice, without added salt", "Tomato sauce for spaghetti, canned", "Tomato sauce, canned", "Vegetable juice cocktail", "Vegetable juice cocktail, low sodium" }));
        ingredientInput.setToolTipText("");
        ingredientInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingredientInputActionPerformed(evt);
            }
        });

        foodCategory.setFont(new java.awt.Font("HelvLight", 0, 11)); // NOI18N
        foodCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All Categories", "Baked Goods", "Beverages", "Breads, Cereals and Other Grain Products", "Dairy Foods and Other Related Products", "Eggs and Egg Dishes", "Fast Foods", "Fats and Oils", "Fish and Shellfish", "Fruit and Fruit Juices", "Legumes, Nuts and Seeds", "Meat and Poultry", "Miscellaneous", "Mixed Dishes", "Snacks", "Soups", "Sweets and Sugars", "Vegetable and Vegetable Products" }));
        foodCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ingredientsTabLayout = new javax.swing.GroupLayout(ingredientsTab);
        ingredientsTab.setLayout(ingredientsTabLayout);
        ingredientsTabLayout.setHorizontalGroup(
            ingredientsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ingredientsTabLayout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(ingredientsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(ingredientTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ingredientsTabLayout.createSequentialGroup()
                        .addGroup(ingredientsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(quantityInput, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(quantityLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(ingredientsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(measurementLabel)
                            .addComponent(measurementInput, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ingredientsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ingredientInput, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ingredientsTabLayout.createSequentialGroup()
                                .addComponent(ingredientName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(foodCategory, 0, 1, Short.MAX_VALUE)))
                        .addGap(28, 28, 28)
                        .addComponent(inputIngredients)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        ingredientsTabLayout.setVerticalGroup(
            ingredientsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ingredientsTabLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(ingredientTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ingredientsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityLabel)
                    .addComponent(measurementLabel)
                    .addComponent(ingredientName)
                    .addComponent(foodCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ingredientsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(measurementInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputIngredients)
                    .addComponent(ingredientInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("Ingredients", ingredientsTab);

        calorieCounterTab.setBackground(java.awt.SystemColor.window);
        calorieCounterTab.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        calorieCounterLabel.setFont(new java.awt.Font("HelvLight", 0, 24)); // NOI18N
        calorieCounterLabel.setText("Calorie Counter");

        caloriesTable.setFont(new java.awt.Font("HelvLight", 0, 11)); // NOI18N
        caloriesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ingredient", "Calories"
            }
        ));
        jScrollPane2.setViewportView(caloriesTable);

        totalCalorieLabel.setFont(new java.awt.Font("HelvLight", 0, 18)); // NOI18N
        totalCalorieLabel.setText("Total Calories:");

        totalCalories.setFont(new java.awt.Font("HelvLight", 0, 18)); // NOI18N

        javax.swing.GroupLayout calorieCounterTabLayout = new javax.swing.GroupLayout(calorieCounterTab);
        calorieCounterTab.setLayout(calorieCounterTabLayout);
        calorieCounterTabLayout.setHorizontalGroup(
            calorieCounterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calorieCounterTabLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(calorieCounterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(calorieCounterTabLayout.createSequentialGroup()
                        .addComponent(totalCalorieLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalCalories, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calorieCounterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        calorieCounterTabLayout.setVerticalGroup(
            calorieCounterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calorieCounterTabLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(calorieCounterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(calorieCounterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totalCalorieLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalCalories, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        tabs.addTab("Calorie Counter", calorieCounterTab);

        nutritionFactsTab.setBackground(new java.awt.Color(255, 255, 255));
        nutritionFactsTab.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nutritionFactsLabel.setFont(new java.awt.Font("HelvLight", 0, 24)); // NOI18N
        nutritionFactsLabel.setText("Nutrition Facts");

        caloriesLabel.setFont(new java.awt.Font("HelvLight", 0, 18)); // NOI18N
        caloriesLabel.setText("Calories:");

        caloriesNutritionFacts.setFont(new java.awt.Font("HelvLight", 0, 18)); // NOI18N

        nutritionFactTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nutrition", "Amount", "Daily Percentage"
            }
        ));
        nutritionFactTable.setToolTipText("");
        jScrollPane3.setViewportView(nutritionFactTable);

        jLabel1.setFont(new java.awt.Font("HelvLight", 0, 11)); // NOI18N
        jLabel1.setText("< 5% Daily Percentage is very little");

        jLabel2.setFont(new java.awt.Font("HelvLight", 0, 11)); // NOI18N
        jLabel2.setText("> 15% Daily Percentage is a lot");

        javax.swing.GroupLayout nutritionFactsTabLayout = new javax.swing.GroupLayout(nutritionFactsTab);
        nutritionFactsTab.setLayout(nutritionFactsTabLayout);
        nutritionFactsTabLayout.setHorizontalGroup(
            nutritionFactsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nutritionFactsTabLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(nutritionFactsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(nutritionFactsTabLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane3)
                    .addComponent(nutritionFactsLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, nutritionFactsTabLayout.createSequentialGroup()
                        .addComponent(caloriesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(caloriesNutritionFacts, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        nutritionFactsTabLayout.setVerticalGroup(
            nutritionFactsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nutritionFactsTabLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(nutritionFactsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(nutritionFactsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caloriesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(caloriesNutritionFacts, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(nutritionFactsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        tabs.addTab("Nutrition Facts", nutritionFactsTab);

        appTitle.setBackground(new java.awt.Color(255, 255, 255));
        appTitle.setFont(new java.awt.Font("HelvLight", 1, 48)); // NOI18N
        appTitle.setText("Cals 4 Pals");

        jLabel3.setFont(new java.awt.Font("HelvLight", 0, 10)); // NOI18N
        jLabel3.setText("*Hover over title for specific instructions");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(appTitle)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(appTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(2, 2, 2)))
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void foodCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodCategoryActionPerformed

        //sets model for combobox based on category selected
        if ("All Categories".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(all);
        }

        if ("Baked Goods".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(bakedGoods);
        }
        if ("Beverages".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(beverages);
        }
        if ("Breads, Cereals and Other Grain Products".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(breads);
        }
        if ("Dairy Foods and Other Related Products".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(dairy);
        }
        if ("Eggs and Egg Dishes".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(eggs);
        }
        if ("Fast Foods".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(fastFood);
        }
        if ("Fats and Oils".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(fatsOils);
        }
        if ("Fish and Shellfish".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(fish);
        }
        if ("Fruit and Fruit Juices".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(fruit);
        }
        if ("Legumes, Nuts and Seeds".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(legumes);
        }
        if ("Meat and Poultry".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(meat);
        }
        if ("Miscellaneous".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(misc);
        }
        if ("Mixed Dishes".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(mixed);
        }
        if ("Snacks".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(snacks);
        }
        if ("Soups".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(soups);
        }
        if ("Sweets and Sugars".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(sugars);
        }
        if ("Vegetable and Vegetable Products".equals(foodCategory.getSelectedItem())) {
            ingredientInput.setModel(vegetables);
        }
    }//GEN-LAST:event_foodCategoryActionPerformed

    private void ingredientInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingredientInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ingredientInputActionPerformed

    private void inputIngredientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputIngredientsActionPerformed

        //gets user inputs for quantity information
        quantityString = quantityInput.getText();

        try { //checks if input is a double or string
            quantity = Integer.parseInt(quantityString);
        }

        catch(NumberFormatException e) { //input is a string (fraction input)

            if (quantityString.contains(" ")) { //check if input is a mixed fraction
                //converts the fraction into a double value for calculations
                int spaceIndex = quantityString.indexOf(" ");
                int slashIndex = quantityString.indexOf("/");
                String whole = quantityString.substring(0, spaceIndex);
                String num = quantityString.substring(spaceIndex + 1, slashIndex);
                String denom = quantityString.substring(slashIndex + 1, quantityString.length());
                quantity = Double.parseDouble(whole) + (Double.parseDouble(num)/Double.parseDouble(denom));
            }

            else {
                //converts the fraction into a double value for calculations
                int slashIndex = quantityString.indexOf("/");
                String num = quantityString.substring(0, slashIndex);
                String denom = quantityString.substring(slashIndex + 1, quantityString.length());
                quantity = Double.parseDouble(num)/Double.parseDouble(denom);
            }            
        }

        //gets user input for measurement and ingredient information
        measurement = (String) measurementInput.getSelectedItem();
        ingredient = ingredientInput.getSelectedItem().toString();

        //places user input into ingredient table
        Object[] row = {quantityString, measurement, ingredient};
        DefaultTableModel it = (DefaultTableModel) ingredientTable.getModel();
        it.addRow(row);

        //converts quantity and measurements to simplest form
        Measurement m = new Measurement (quantity, measurement);
        m.convert();

        //checks if inputted ingredient matches for each ingredient in database
        for (int j = 0; j < totalLines; j++) {
            matchedIngredients = newIngredient[j].getIngredient(ingredient); //assigns current ingredient of database the loop is on
            if (matchedIngredients.equals("Not Found")) { //if the ingredient does not match, continue on the array
                continue;
            }
            else { //break the loop if matched and gets matched calorie as well
                ingredientCalorie = newIngredient[j].getCalorie(ingredient, m.quantity, m.measurement);
                nutritionAmount = newIngredient[j].getNutritionFacts(ingredient);
                multiple = newIngredient[j].getMultiple();
                break;
            }
        }

        //rounds calorie value to 2 decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        ingredientCalorie = Double.valueOf(df.format(ingredientCalorie));

        Object[] row1 = {matchedIngredients, ingredientCalorie}; //adds ingredient info and calories as objects to add to table
        DefaultTableModel ct = (DefaultTableModel) caloriesTable.getModel();
        ct.addRow(row1);

        //totals calorie information
        totalCalorieCount = totalCalorieCount + ingredientCalorie;
        totalCalorieCount = Double.valueOf(df.format(totalCalorieCount));

        totalCalories.setText(Double.toString(totalCalorieCount));
        caloriesNutritionFacts.setText(Double.toString(totalCalorieCount));

        //sets up table for nutrition facts
        DefaultTableModel nt = (DefaultTableModel) nutritionFactTable.getModel(); //set nutrition table
        nt.setRowCount(0);

        //NUTRITION FACTS
        for (int i = 0; i < nutrition.length; i++) {
            if (nutritionAmount[i] != null ) { //checks if value is null (if the category doesn't apply to ingredient)
                if (nutritionAmount[i].equals("tr") || nutritionAmount[i].equals("N/A")) { //converts "tr" and "N/A" to 0
                    nutritionAmountNumber[i] = 0;
                }
                else {
                    nutritionAmountNumber[i] = (Double.parseDouble(nutritionAmount[i])/multiple)*m.quantity; //gets nutrition fact values and scales it based on quantity multiple
                }
            }
            else { //sets null values to 0
                nutritionAmountNumber[i] = 0;
            }

            //adds up total nutrition values
            totalNutrition[i] = totalNutrition[i] + nutritionAmountNumber[i];

            //rounds nutrition values
            totalNutrition[i] = Double.valueOf(df.format(totalNutrition[i]));

            //DAILY PERCENTAGE
            if (nutrition[i].equals("Total Fat (g)")) {
                dailyPercentage[i] = (totalNutrition[i]/65)*100;
            }

            else if (nutrition[i].equals("Saturated Fat (g)")) {
                dailyPercentage[i] = (totalNutrition[i]/20)*100;
            }

            else if (nutrition[i].equals("Cholesterol (mg)")) {
                dailyPercentage[i] = (totalNutrition[i]/300)*100;
            }

            else if (nutrition[i].equals("Sodium (mg)")) {
                dailyPercentage[i] = (totalNutrition[i]/2400)*100;
            }

            else if (nutrition[i].equals("Carbohydrate (g)")) {
                dailyPercentage[i] = (totalNutrition[i]/300)*100;
            }

            else if (nutrition[i].equals("Total Dietary Fibre (g)")) {
                dailyPercentage[i] = (totalNutrition[i]/25)*100;
            }

            else if (nutrition[i].equals("Vitamin A (mcg RAE)")) {
                dailyPercentage[i] = (totalNutrition[i]/1000)*100;
            }

            else if (nutrition[i].equals("Vitamin C (mg)")) {
                dailyPercentage[i] = (totalNutrition[i]/60)*100;
            }

            else if (nutrition[i].equals("Calcium (mg)")) {
                dailyPercentage[i] = (totalNutrition[i]/1100)*100;
            }

            else if (nutrition[i].equals("Iron (mg)")) {
                dailyPercentage[i] = (totalNutrition[i]/14)*100;
            }

            //rounds daily percentage
            dailyPercentage[i] = Double.valueOf(df.format(dailyPercentage[i]));

            //displays daily percentage values into string percentages
            if (dailyPercentage[i] != 0.00) {
                percentage[i] = Double.toString(dailyPercentage[i]) + "%";
            }

            else { //for nutrition categories that don't have daily percentage values
                percentage[i] = "";
            }

            //adds nutrition facts to the nutrition facts table
            Object[] n1 = {nutrition[i], totalNutrition[i], percentage[i]};
            nt.addRow(n1);
        }       
        nt.fireTableDataChanged(); //updates table after each click
    }//GEN-LAST:event_inputIngredientsActionPerformed

    private void quantityInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityInputActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cals4Pals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cals4Pals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cals4Pals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cals4Pals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //SPLASH SCREEN
        JWindow window = new JWindow();
        window.getContentPane().add(new JLabel("", new ImageIcon("SplashScreen.gif"), SwingConstants.CENTER));
        window.setBounds(0, 0, 750, 480);
        window.setVisible(true);
        try {
            Thread.sleep(3000);
        }
        catch(InterruptedException e) {}
        window.dispose();
        
        Cals4Pals c4p = new Cals4Pals ();
        c4p.readDB();
        c4p.setCategory();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Cals4Pals c4p = new Cals4Pals ();
                c4p.setVisible(true);
                c4p.setTitle("Cals 4 Pals");
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appTitle;
    private javax.swing.JLabel calorieCounterLabel;
    private javax.swing.JPanel calorieCounterTab;
    private javax.swing.JLabel caloriesLabel;
    private javax.swing.JLabel caloriesNutritionFacts;
    private javax.swing.JTable caloriesTable;
    private javax.swing.JComboBox foodCategory;
    private javax.swing.JComboBox ingredientInput;
    private javax.swing.JLabel ingredientName;
    private javax.swing.JTable ingredientTable;
    private javax.swing.JLabel ingredientTitle;
    private javax.swing.JPanel ingredientsTab;
    private javax.swing.JButton inputIngredients;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox measurementInput;
    private javax.swing.JLabel measurementLabel;
    private javax.swing.JTable nutritionFactTable;
    private javax.swing.JLabel nutritionFactsLabel;
    private javax.swing.JPanel nutritionFactsTab;
    private javax.swing.JTextField quantityInput;
    private javax.swing.JLabel quantityLabel;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JLabel totalCalorieLabel;
    private javax.swing.JLabel totalCalories;
    // End of variables declaration//GEN-END:variables
}
