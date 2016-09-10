package com.desarrollo.guma.there2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Envoltura para generar una lista de ejemplo
 */
public class Girls {

    public static final String[] girlsNamesDummy = {
            "Catherine", "Evelyn", "Phyllis", "Beverly", "Michelle",
            "Denise", "Virginia", "Ruth", "Barbara", "Amanda", "Anna",
            "Catherine", "Melissa", "Sandra", "Julia", "Paula", "Kimberly",
            "Diane", "Betty", "Sharon", "Ruby", "Barbara", "Ann", "Phyllis",
            "Linda", "Marie", "Deborah", "Sara", "Gloria", "Karen", "Patricia",
            "Donna", "Catherine", "Louise", "Catherine", "Joyce", "Katherine",
            "Janice", "Cheryl", "Lisa", "Andrea", "Elizabeth", "Nicole",
            "Cynthia", "Angela", "Donna", "Deborah", "Sandra", "Cheryl", "Jane"
    };

    /**
     * Genera una lista de objetos {@link Girl} con un tamaño determinado
     *
     * @param count Tamaño
     * @return Lista aleatoria
     */
    public static List<Girl> randomList(int count) {
        Random random = new Random();
        List<Girl> items = new ArrayList<>();

        // Restricción de tamaño
        count = Math.min(count, girlsNamesDummy.length);

        while (items.size() < count) {
            items.add(new Girl(girlsNamesDummy[random.nextInt(girlsNamesDummy.length)]));
        }

        return new ArrayList<>(items);
    }
}