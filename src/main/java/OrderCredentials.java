public class OrderCredentials {
    private String[] ingredients;
    private Integer orderId;

    public OrderCredentials() {
    }

    public OrderCredentials(OrderClient orderClient) {

        this.ingredients = IngredientsGenerator.randomListOfIngredients(
                orderClient.getCorrectHashCodesOfIngredientsFromApi());
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }
}
