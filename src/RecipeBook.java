import java.util.*;

public class RecipeBook {
    private static Recipe[] recipes;
    private static int recipeCount;

    public RecipeBook(int size) {
        recipes = new Recipe[size];
        recipeCount = 0;
    }

    public static void addRecipe(Recipe recipe) {
        if (recipeCount < recipes.length) {
            recipes[recipeCount++] = recipe;
        }
    }


    /**
     * Searching for ingredient from recipes list
     * @param ingredient the String value to search from list
     * return recipes list that use ingredient
     */
    public List<Recipe> searchByIngredient(String ingredient) {
        List<Recipe> matchingRecipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.containsIngredient(ingredient)) {
                matchingRecipes.add(recipe);
            }
        }
        return matchingRecipes;
    }

    /**
     * fetch all recipes details
     * return array of recipes
     */
    public Recipe[] getAllRecipes() {
        return Arrays.copyOf(recipes, recipeCount);
    }

    public Recipe findMostPopular() {
        Recipe bestRecipe = null;
        double maxRating = 0;
        for (Recipe recipe : recipes) {
            if (recipe.getRating() > maxRating) {
                bestRecipe = recipe;
                maxRating = recipe.getRating();
            }
        }
        return bestRecipe;
    }

    /**
     * Sort recipes by meal
     * done by using  bubble sort algorithm
     */
    public void sortRecipesByMeal() {
        int n = recipes.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (recipes[j].getMeal().compareTo(recipes[j + 1].getMeal()) < 0) {
                    String temp = recipes[j].getMeal();
                    recipes[j].setMeal(recipes[j + 1].getMeal());
                    recipes[j + 1].setMeal(temp);
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }


    /**
     * Find most common meal from recipes
     * return meal name
     */
    public String findMostCommonMeal() {
        Map<String, Integer> mealCountMap = new HashMap<>();

        for (Recipe recipe : recipes) {
            mealCountMap.put(recipe.getMeal().toLowerCase(), mealCountMap.getOrDefault(recipe.getMeal().toLowerCase(), 0) + 1);
        }


        String mostCommonMeal = null;
        int mostCommonMealCount = 0;

        for (Map.Entry<String, Integer> entry : mealCountMap.entrySet()) {
            if (entry.getValue() > mostCommonMealCount) {
                mostCommonMeal = entry.getKey();
                mostCommonMealCount = entry.getValue();
            }
        }

        return mostCommonMeal;
    }

    /**
     * Get recipes by meal name
     * @param getMealName the String value to meal
     *  return recips list that contain meal name
     */
    public Recipe[] getRecipesByMeal(String getMealName) {

        String meal = getMealName.toLowerCase();
        List<Recipe> matchRecipes = new ArrayList<>();
        Arrays.sort(recipes, (r1, r2) -> r1.getMeal().toLowerCase().compareTo(r2.getMeal().toLowerCase()));

        int low = 0;
        int high = recipes.length - 1;
        int matchIndex = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = recipes[mid].getMeal().toLowerCase().compareTo(meal);
            if (comparison == 0) {
                matchIndex = mid;
                break;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (matchIndex != -1) {
            matchRecipes.add(recipes[matchIndex]);

            int left = matchIndex - 1;
            while (left >= 0 && recipes[left].getMeal().equalsIgnoreCase(meal)) {
                matchRecipes.add(recipes[left]);
                left--;
            }

            int right = matchIndex + 1;
            while (right < recipes.length && recipes[right].getMeal().equalsIgnoreCase(meal)) {
                matchRecipes.add(recipes[right]);
                right++;
            }
        }

        return matchRecipes.toArray(new Recipe[0]);
    }

    /**
     * get recipes by name
     * @param recipeName the String value to search from list
     * return recipe
     */
    public static Recipe getRecipeByName(String recipeName) {
        String finalRecipeName = recipeName.toLowerCase();

        return Arrays.stream(recipes)
                .filter(recipe -> recipe.getName().toLowerCase().equals(finalRecipeName))
                .findFirst()
                .orElse(null);

    }
}
