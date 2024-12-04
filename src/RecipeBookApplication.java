import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RecipeBookApplication {


    public static void main(String[] args) throws IOException {
        RecipeBook recipeBook = RecipeFileUtils.readRecipesFromFile("recipes.txt");
        Scanner scanner = new Scanner(System.in);

        System.out.println("--------------------------------------------------------------------------");
        while (true) {
            System.out.println("Please Select an Option:\n");
            System.out.println("1. Display all recipes");
            System.out.println("2. Sort recipes");
            System.out.println("3. Display all recipes with a specific ingredient");
            System.out.println("4. Edit a recipe");
            System.out.println("5. Display the most popular recipe");
            System.out.println("6. Display the most common meal");
            System.out.println("7. Display all recipes for a specified meal");
            System.out.println("10. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayAllRecipes(recipeBook);
                    break;
                case 2:
                    System.out.println("Descending alphabetical order of recipe meal");
                    sortRecipesByMeal(recipeBook);
                    recipeBook.sortRecipesByMeal();
                    break;
                case 3:
                    System.out.print("Enter ingredient: ");
                    String ingredient = scanner.nextLine();
                    displayRecipesByIngredient(recipeBook, ingredient);
                    break;
                case 4:
                    System.out.println("Do you want to ->");
                    System.out.println("1. Add ingredient to the specified recipe");
                    System.out.println("2. Remove an ingredient from the specified recipe");
                    System.out.println("3. Sort the ingredients of the specified recipe");

                    int editOption = scanner.nextInt();
                    scanner.nextLine();
                    String recipeName = "";
                    String requestedIngredient = "";
                    Recipe result = new Recipe();

                    System.out.print("Enter Recipe Name :");
                    recipeName = scanner.nextLine();
                    result = RecipeBook.getRecipeByName(recipeName);
                    if (result != null) {
                        System.out.println("Recipe found!");

                        if (editOption == 1 || editOption == 2) {
                            System.out.print("Enter Ingredient Name : ");
                            requestedIngredient = scanner.nextLine();
                        }

                        switch (editOption) {
                            case 1:
                                editRecipe(result, "add", requestedIngredient);
                                break;
                            case 2:
                                editRecipe(result, "remove", requestedIngredient);
                                break;
                            case 3:
                                result.sortIngredients();
                                System.out.print("Ingredients Sorted ");
                                System.out.print("Sorted Ingredients ->");
                                System.out.println(Arrays.asList(result.getIngredients()));
                                System.out.println("----------------------------------------------------------------");

                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                                break;
                        }
                    } else {
                        System.out.println("Recipe not found.");
                    }

                    break;
                case 5:
                    displayMostPopularRecipe(recipeBook);
                    break;
                case 6:
                    displayMostCommonMeal(recipeBook);
                    break;
                case 7:
                    System.out.print("Enter meal: ");
                    String meal = scanner.nextLine();
                    displayRecipesByMeal(recipeBook, meal);
                    break;
                case 10:
                    System.out.println("Exiting... Have a nice day!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void displayAllRecipes(RecipeBook recipeBook) {
        Recipe[] recipes = recipeBook.getAllRecipes();

        int recipeCount = 1;
        System.out.println("Recipes List:");
        for (Recipe recipe : recipes) {
            displayRecipes(recipe, recipeCount);
            recipeCount++;
        }
        System.out.println("----------------------------------------------------------------");
    }

    private static void sortRecipesByMeal(RecipeBook recipeBook) {
        recipeBook.sortRecipesByMeal();
        displayAllRecipes(recipeBook);
    }

    private static void displayRecipesByIngredient(RecipeBook recipeBook, String ingredient) {
        List<Recipe> matchRecipes = recipeBook.searchByIngredient(ingredient);

        if (matchRecipes.isEmpty()) {
            System.out.println("There is No Matching ingredient include recipes");
            System.out.println();
            System.out.println("----------------------------------------------------------------");
            return;
        }

        int recipeCount = 1;
        System.out.println("Recipes List:");
        for (Recipe matchRecipe : matchRecipes) {
            displayRecipes(matchRecipe, recipeCount);
            recipeCount++;
        }
        System.out.println("----------------------------------------------------------------");

    }

    public static void displayRecipes(Recipe recipe, int recipeCount) {
        System.out.println("Recipe " + recipeCount);
        System.out.println("Name: " + recipe.getName());
        System.out.println("Author: " + recipe.getAuthor());
        System.out.println("Rating: " + recipe.getRating());
        System.out.println("Meal: " + recipe.getMeal());
        System.out.println("Ingredients: " + Arrays.asList(recipe.getIngredients()));
        System.out.println();
    }

    private static void editRecipe(Recipe recipe, String type, String ingredent) {
        Recipe newRecipe = new Recipe(recipe.getName(), recipe.getAuthor(), recipe.getRating(), recipe.getMeal(), recipe.getIngredients());
        if (type.equals("add")) {
            newRecipe.addIngredient(ingredent);
        } else {
            newRecipe.removeIngredient(ingredent);
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------");
    }

    private static void displayMostPopularRecipe(RecipeBook recipeBook) {
        Recipe recipe = recipeBook.findMostPopular();
        if (recipe != null) {
            System.out.println("Most popular recipe: ");
            displayRecipes(recipe, 1);
        } else {
            System.out.println("No recipes found.");
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------");
    }

    private static void displayMostCommonMeal(RecipeBook recipeBook) {
        String meal = recipeBook.findMostCommonMeal();
        System.out.println("Most common meal: " + meal);

        System.out.println();
        System.out.println("----------------------------------------------------------------");
    }

    private static void displayRecipesByMeal(RecipeBook recipeBook, String meal) {
        Recipe[] recipes = recipeBook.getRecipesByMeal(meal);
        int recipeCount = 1;
        for (Recipe recipe : recipes) {
            displayRecipes(recipe, recipeCount);
            recipeCount++;
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------");
    }
}
