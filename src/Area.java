import java.util.ArrayList;
import java.util.List;

public class Area {
    private String plantName;
    private List<Plant> plants;
    private String[] insects;  // Insects affecting this area

    private WateringSystem wateringSystem;
    private HeatingSystem heatingSystem;
    private PestControl pestControl;

    private boolean waterCheckIfDying;
    private boolean heatCheckIfDying;

    private int currentTemp;
    private int currentWater;

    public Area(String plantName, int tempLowerbound, int tempUpperbound,
                String[] parasites, int waterLowbound, int waterUpperbound) {
        this.plantName = plantName;
        this.plants = new ArrayList<>();
        this.insects = new String[0];
        this.insects = parasites;
//        addPlant(tempLowerbound, tempUpperbound, parasites, waterLowbound, waterUpperbound);

        this.wateringSystem = new WateringSystem(waterLowbound, waterUpperbound);
        this.heatingSystem = new HeatingSystem(tempLowerbound,tempUpperbound);
        this.pestControl = new PestControl(parasites);

    }

    public void addPlant(int tempLowerbound, int tempUpperbound, String[] parasites, int waterLowbound, int waterUpperbound) {
        plants.add(new Plant(plantName, tempLowerbound, tempUpperbound, parasites, waterLowbound, waterUpperbound));
    }

    public void adjustSetting(){
        // System.out.println(plantName + " insects num " + insects.length);
        waterCheckIfDying = wateringSystem.adjustSetting(plants);
        heatCheckIfDying = heatingSystem.adjustSetting(plants);
        pestControl.updateSetting(plants);
    }

    public void maintenance(){
        wateringSystem.maintainWater(this, waterCheckIfDying);
        heatingSystem.maintainTemp(this, heatCheckIfDying);
        pestControl.killInsects(this);
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public int getCurrentWater() {
        return currentWater;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public void setCurrentWater(int currentWater) {
        this.currentWater = currentWater;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public String getPlantName() {
        return plantName;
    }

    public int getPlantCount() {
        return plants.size();
    }

    public String[] getInsects() {
        return insects;
    }

    public void setInsects(String[] newInsects) {
        this.insects = newInsects;
    }

    public void killInsect(String insect){
        List<String> result = new ArrayList<>();

        for(String item : insects){
            if(!insect.equals(item)) {
                result.add(item);
            }
        }

        this.insects = result.toArray(new String[0]);
    }

    // Return the lowestHealthPlant.
    public Plant findAreaWithLowestHealthPlant(){

        Plant lowestHealthPlant = new Plant(Integer.MAX_VALUE);
        for(Plant p : plants){
            if(p.getHealth() < lowestHealthPlant.getHealth()){
                lowestHealthPlant = p;
            }
        }
        return lowestHealthPlant;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Area: ").append(plantName)
                .append(" | Plant Count: ").append(plants.size())
                .append(" | Water:").append(currentWater)
                .append(" | Temp:").append(currentTemp)
                .append(" | Insects: ").append(String.join(", ", insects))
                .append(" | Plant Health: ");

        for (Plant plant : plants) {
            sb.append(plant.getHealth()).append(" ");
        }

        return sb.toString();
    }
}
