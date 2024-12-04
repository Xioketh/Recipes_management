import java.util.ArrayList;
import java.util.Arrays;

public class Recipe {
    private String name;
    private String author;
    private double rating;
    private String meal;
    private String[] ingredients;
    private int ingredientCount;

    public Recipe() {
    }

    public Recipe(String[] ingredients) {
        this.ingredients = Arrays.copyOf(ingredients, ingredients.length);
        this.ingredientCount = ingredients.length;
    }

    public Recipe(String name, String author, double rating, String meal, String[] ingredients) {
        this.name = name;
        this.author = author;
        this.rating = rating;
        this.meal = meal;
        this.ingredients = Arrays.copyOf(ingredients, ingredients.length);
        this.ingredientCount = ingredients.length;
    }


    /**
     * Add ingredient to ingredient list
     * @param ingredient the String value to add
     */
    public void addIngredient(String ingredient) {
        ingredient = ingredient.toLowerCase();
        if (Arrays.stream(ingredients).map(String::toLowerCase).anyMatch(ingredient::equals)) {
            System.out.println("Ingredient already exists!");
            return;
        }
        ingredients = Arrays.copyOf(ingredients, ingredientCount + 1);
        ingredients[ingredientCount++] = ingredient;
        System.out.println("Ingredient Added!");
    }


    /**
     * Remove ingredient from ingredient list
     * @param ingredient the String value to remove
     */
    public void removeIngredient(String ingredient) {
        ingredient = ingredient.toLowerCase();
        ArrayList<String> ingredientsList = new ArrayList<>(Arrays.asList(ingredients));

        if (ingredientsList.contains(ingredient)) {
            ingredientsList.remove(ingredient);
            ingredients = ingredientsList.toArray(new String[0]);
            System.out.println("Ingredient removed: " + ingredient);
        } else {
            System.out.println("Ingredient Not Available!");
        }
    }


    /**
     * Checking whether the ingredient is available at ingredients list
     * @param ingredient the String value to search from list
     * checking done by using binary search algorithm
     * return boolean if ingredent available
     */
    public boolean containsIngredient(String ingredient) {
        ingredient = ingredient.toLowerCase();

        Recipe recipe = new Recipe(ingredients);
        recipe.sortIngredients();

        int low = 0;
        int high = ingredients.length -1;

        ingredients = Arrays.copyOf(recipe.getIngredients(),recipe.getIngredients().length);

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = ingredients[mid].toLowerCase().compareTo(ingredient.toLowerCase());
            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

    /**
     * sorting the ingredients list
     * sorting done by using selection sort algorithm
     */
    public void sortIngredients() {

        for (int i = 0; i < ingredientCount - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < ingredientCount; j++) {
                int x =ingredients[j].compareTo(ingredients[minIndex]);
                if (x < 0) {
                    minIndex = j;
                }
            }
            String temp = ingredients[minIndex];
            ingredients[minIndex] = ingredients[i];
            ingredients[i] = temp;

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getMeal() {
        return meal;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public int getIngredientCount() {
        return ingredientCount;
    }

    public void setIngredientCount(int ingredientCount) {
        this.ingredientCount = ingredientCount;
    }
}
