import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IngredientsGenerator {

    public static String[] randomListOfIngredients(List<String> list) {

        int quantityOfIngredients = new Random().nextInt(list.size()) + 1;
        List<String> uniqueSetOfIngredientsForOrder = new ArrayList<>();

        for (int i=1; i <= quantityOfIngredients; i++) {
            uniqueSetOfIngredientsForOrder.add(list.get(new Random().nextInt(list.size())));
        }

        return uniqueSetOfIngredientsForOrder.toArray(new String[0]);
    }
}
