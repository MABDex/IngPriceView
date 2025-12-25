package org.example.ingrediantpriceview.Web;


import org.example.ingrediantpriceview.entities.IngrediantPrice;
import org.example.ingrediantpriceview.repository.IngredientPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngrediantPriceController {

    @Autowired
    private IngredientPriceRepository ingredientPricerepository;

    @GetMapping("/ingrediants")
    public List<IngrediantPrice> getAllIngredients(){
        return ingredientPricerepository.findAll();
    }

    @PutMapping("/Priceupdate")
    public IngrediantPrice updateIngredientPrice(@RequestParam String name, @RequestParam double newprice) {
        // 1. Ingredient aus DB holen
        IngrediantPrice existing = ingredientPricerepository.findByName(name);
        if (existing == null) {
            throw new RuntimeException("Ingredient nicht gefunden");
        }

        // 2. Preis aktualisieren
        existing.setPrice(newprice);

        // 3. Speichern
        return ingredientPricerepository.save(existing);
    }
}
