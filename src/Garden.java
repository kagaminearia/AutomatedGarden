import java.util.*;


public class Garden {
    private int temperature;
    private int rainAmount;
//    private String[] insects;
//    private PriorityQueue<Plant> plants;
    private List<Plant> deadPlants;
    private Map<String, Area> areaMap;
    private List<Area> deadAreas;


    public Garden(int initialTemp, int rainAmount) {
        this.temperature = initialTemp;
        this.rainAmount = rainAmount;
//        this.insects = insects;
//        this.plants = new PriorityQueue<>();
        this.deadPlants = new ArrayList<>();
        this.areaMap = new HashMap<>();
        this.deadAreas = new ArrayList<>();
    }

    // Synchronize all areas with the same temp.
    public void syncTemp(int temp){
        for (Area area : areaMap.values()){
            area.setCurrentTemp(temp);
        }
    }

    // Synchronize all areas with the same water amount.
    public void syncWater(int water){
        for (Area area : areaMap.values()){
            area.setCurrentTemp(water);
        }
    }


    // experience everyday, change plant's situation base on garden's environment
    public void dayPass() {
        List<Area> toRemoveAreas = new ArrayList<>();

        for (Area area : areaMap.values()) { // Iterate over areas in the Map
            List<Plant> deadInThisArea = new ArrayList<>();

            for (Plant plant : new ArrayList<>(area.getPlants())) {
                plant.impact(area.getCurrentTemp(), area.getCurrentWater(), area.getInsects());
                if (!plant.ifAlive()) {
                    deadPlants.add(plant);
                    deadInThisArea.add(plant);
                }
            }

            // Remove dead plants from the area
            area.getPlants().removeAll(deadInThisArea);

            // If the area has no plants left, move it to dead areas
            if (area.getPlants().isEmpty()) {
                deadAreas.add(area);
                toRemoveAreas.add(area);
            }
        }

        // Remove empty areas from the Map
        for (Area area : toRemoveAreas) {
            areaMap.remove(area.getPlantName());
        }
    }

    public void addPlantToArea(String name, int tempLowerbound, int tempUpperbound, String[] parasites, int waterLowerbound, int waterUpperbound) {
        Area area = areaMap.get(name); // Look up the area by plant name
        if (area == null) {
            // If the area doesn't exist, create a new one

            area = new Area(name, tempLowerbound, tempUpperbound, parasites, waterLowerbound, waterUpperbound);
            areaMap.put(name, area); // Add the new area to the Map
        }
        // Add the plant to the area
        area.addPlant(tempLowerbound, tempUpperbound, parasites, waterLowerbound, waterUpperbound);
    }

    public Area findAreaWithLowestHealthPlant() {
        Area areaWithLowestHealth = null;
        int lowestHealth = Integer.MAX_VALUE;

        for (Area area : areaMap.values()) {
            for (Plant plant : area.getPlants()) {
                if (plant.getHealth() < lowestHealth) {
                    lowestHealth = plant.getHealth();
                    areaWithLowestHealth = area;
                }
            }
        }

        return areaWithLowestHealth;
    }

//    public Plant getLowestHealthPlant() {
//        return plants.stream()
//                .min(Comparator.comparingInt(Plant::getHealth))
//                .orElse(null);
//    }

    public List<Area> getAreas() {
        return new ArrayList<>(areaMap.values());
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        syncTemp(this.temperature);
    }

    public int getRainAmount() {
        return rainAmount;
    }

    public void setRainAmount(int rainAmount) {
        this.rainAmount = rainAmount;
        syncWater(this.rainAmount);
    }

//    public String[] getInsects() {
//        return insects;
//    }
//
//    public void setInsect(String[] insects) {
//        this.insects = insects;
//    }

//    public void killInsect(String insect){
//        List<String> result = new ArrayList<>();
//
//        for(String item : insects)
//            if(!insect.equals(item))
//                result.add(item);
//
//        this.insects = result.toArray(new String[0]);
//    }

//    public void addPlant(Plant plant){
//        this.plants.add(plant);
//    }
//
//    public PriorityQueue<Plant> getPlants() {
//        return plants;
//    }

    public List<Plant> getDeadPlants() {
        return deadPlants;
    }

    public List<Plant> getAllAlivePlants() {
        List<Plant> allPlants = new ArrayList<>();
        for (Area area : areaMap.values()) { // Iterate over the values (areas) in the Map
            allPlants.addAll(area.getPlants());
        }
        return allPlants;
    }
}
