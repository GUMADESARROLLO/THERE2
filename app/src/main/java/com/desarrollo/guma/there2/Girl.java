package com.desarrollo.guma.there2;
import java.util.Random;

/**
 * POJO de chicas
 */
public class Girl {

    private String name;
    private int idDrawable;

    public Girl(String name, int idDrawable) {
        this.name = name;
        this.idDrawable = idDrawable;
    }

    public Girl(String name) {
        this.name = name;
        this.idDrawable = getRandomGirlDrawable();
    }

    public String getName() {
        return name;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    /**
     * Asigna un drawable en forma aleatoria
     *
     * @return id del drawable
     */
    private int getRandomGirlDrawable() {
        Random rnd = new Random();
        switch (rnd.nextInt(8)) {
            default:
            case 0:
                return R.drawable.girl1;
            case 1:
                return R.drawable.girl2;
            case 2:
                return R.drawable.girl3;
            case 3:
                return R.drawable.girl4;
            case 4:
                return R.drawable.girl5;
            case 5:
                return R.drawable.girl6;
            case 6:
                return R.drawable.girl7;
            case 7:
                return R.drawable.girl8;
        }
    }
}