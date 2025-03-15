public class Sprinkler {

    /*
    // Start watering, until reach a certain value.
    public void waterToAmount(Garden garden, int targetWater){

        garden.setRainAmount(targetWater);

    }

     */

    // Start watering, until reach a certain value.
    public void waterToAmount(Area area, int targetWater){

        area.setCurrentWater(targetWater);

    }

    // Sprinkle a certain type of pesticide to kill that kind of insect
    public void sprayPesticide(Area area, String insect){

        area.killInsect(insect);

    }
}
