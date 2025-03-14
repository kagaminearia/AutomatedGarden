import java.util.ArrayList;
import java.util.List;

class Area {
    private String plantName;
    private List<Plant> plants;
    private String[] insects;  // Insects affecting this area

    public Area(String plantName, int tempLowerbound, int tempUpperbound,
                String[] parasites, int waterLowbound, int waterUpperbound) {
        this.plantName = plantName;
        this.plants = new ArrayList<>();
        this.insects = new String[0];
//        addPlant(tempLowerbound, tempUpperbound, parasites, waterLowbound, waterUpperbound);
    }

    public void addPlant(int tempLowerbound, int tempUpperbound, String[] parasites, int waterLowbound, int waterUpperbound) {
        plants.add(new Plant(plantName, tempLowerbound, tempUpperbound, parasites, waterLowbound, waterUpperbound));
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

        for(String item : insects)
            if(!insect.equals(item))
                result.add(item);

        this.insects = result.toArray(new String[0]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Area: ").append(plantName)
                .append(" | Plant Count: ").append(plants.size())
                .append(" | Insects: ").append(String.join(", ", insects))
                .append(" | Plant Health: ");

        for (Plant plant : plants) {
            sb.append(plant.getHealth()).append(" ");
        }

        return sb.toString();
    }
}
