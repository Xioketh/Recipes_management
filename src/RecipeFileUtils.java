import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeFileUtils {

    public static RecipeBook readRecipesFromFile(String filename) throws IOException {
        List<Recipe> recipes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("%%");
            if (parts.length == 5) {
                String name = parts[0];
                String author = parts[1];
                double rating = Double.parseDouble(parts[2]);
                String meal = parts[3];
                String[] ingredients = parts[4].split("~~");
                Recipe recipe = new Recipe(name, author, rating, meal, ingredients);
                recipes.add(recipe);
            }
        }
        reader.close();

        RecipeBook recipeBook = new RecipeBook(recipes.size());
        for (Recipe recipe : recipes) {
            recipeBook.addRecipe(recipe);
        }
        return recipeBook;
    }
}
