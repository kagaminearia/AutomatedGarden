import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class Garden {
    private int temperature;
    private int rainAmount;
    private String[] insects;
    private PriorityQueue<Plant> plants;
    private List<Plant> deadPlants;

    public Garden(int initialTemp, int rainAmount, String[] insects) {
        this.temperature = initialTemp;
        this.rainAmount = rainAmount;
        this.insects = insects;
        this.plants = new PriorityQueue<>();
        this.deadPlants = new ArrayList<>();
    }

    // experience everyday, change plant's situation base on garden's environment
    void dayPass() {
        List<Plant> toRemove = new ArrayList<>();
        for (Plant plant : plants) {
            plant.impact(temperature, rainAmount, insects);
            if (!plant.ifAlive()) {
                deadPlants.add(plant);
                toRemove.add(plant);
            }
        }
        plants.removeAll(toRemove);
    }


    public Plant getLowestHealthPlant() {
        return plants.stream()
                .min(Comparator.comparingInt(Plant::getHealth))
                .orElse(null);
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getRainAmount() {
        return rainAmount;
    }

    public void setRainAmount(int rainAmount) {
        this.rainAmount = rainAmount;
    }

    public String[] getInsects() {
        return insects;
    }

    public void setInsect(String[] insects) {
        this.insects = insects;
    }

    public void addPlant(Plant plant){
        this.plants.add(plant);
    }

    public PriorityQueue<Plant> getPlants() {
        return plants;
    }

    public List<Plant> getDeadPlants() {
        return deadPlants;
    }
}
