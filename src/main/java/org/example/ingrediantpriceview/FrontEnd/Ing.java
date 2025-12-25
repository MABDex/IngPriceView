package org.example.ingrediantpriceview.FrontEnd;




import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.example.ingrediantpriceview.entities.IngrediantPrice;
import org.example.ingrediantpriceview.repository.IngredientPriceRepository;



@Route("ingredient-price") // URL: http://localhost:8899/ingredient-price

public class Ing extends VerticalLayout {

    private final IngredientPriceRepository ingredientPriceRepository;

    public Ing(IngredientPriceRepository ingredientPriceRepository) {
        this.ingredientPriceRepository = ingredientPriceRepository;

        Grid<IngrediantPrice> grid = new Grid<>(IngrediantPrice.class);
        grid.setItems(ingredientPriceRepository.findAll());
        grid.setColumns("name", "price");

        TextField nameField = new TextField("Ingredient Name");
        TextField priceField = new TextField("New Price");

        Button updateButton = new Button("Update Price", event -> {
            String name = nameField.getValue();
            double newPrice;
            try {
                newPrice = Double.parseDouble(priceField.getValue());
            } catch (NumberFormatException e) {
                Notification.show("Bitte gültigen Preis eingeben");
                return;
            }

            IngrediantPrice ingredient = ingredientPriceRepository.findByName(name);
            if (ingredient == null) {
                Notification.show("Ingredient nicht gefunden");
            } else {
                ingredient.setPrice(newPrice);
                ingredientPriceRepository.save(ingredient);
                Notification.show("Preis aktualisiert");
                grid.setItems(ingredientPriceRepository.findAll());
            }
        });

        add(grid, nameField, priceField, updateButton);
        setPadding(true);
        setSpacing(true);
    }
}
