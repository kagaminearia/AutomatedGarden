public class Sprinkler {

    // Start watering, until reach a certain value.
    public void waterToAmount(Garden garden, int targetWater){

        garden.setRainAmount(targetWater);

    }

    // Sprinkle a certain type of pesticide to kill that kind of insect
    public void sprayPesticide(Garden garden, String insect){

        garden.killInsect(insect);

    }
}
