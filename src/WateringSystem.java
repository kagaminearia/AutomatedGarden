import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class WateringSystem {
    private int waterLowBound = Integer.MIN_VALUE;
    private int waterUpperBound = Integer.MAX_VALUE;
    private Sensor waterSensor;
    private Sprinkler sprinkler;

    WateringSystem(int waterLowBound, int waterUpperBound){
        this.waterLowBound = waterLowBound;
        this.waterUpperBound = waterUpperBound;
        this.waterSensor = new Sensor();
        this.sprinkler = new Sprinkler();
    }

    // Adjust the setting lower and upper bound
    public boolean adjustSetting(List<Plant> plants){
        for(Plant plant: plants){
            if(plant.getWaterLowerbound() > waterLowBound){
                this.waterLowBound = plant.getWaterLowerbound();
            }
            if(plant.getWaterUpperbound() < waterUpperBound){
                this.waterUpperBound = plant.getWaterUpperbound();
            }
        }

        // System.out.println(plants.getFirst().getName() + " water range " + waterLowBound + ", " + waterUpperBound);

        return this.waterLowBound > this.waterUpperBound;
    }


    // Given the garden and the specific area, maintain its temp
    public void maintainWater(Area area, boolean checkIfDying){
        // If the best range of water for each plant doesn't overlap.
        if(checkIfDying){
            Plant lowestHealthPlant = area.findAreaWithLowestHealthPlant();
            if(Objects.nonNull(lowestHealthPlant)){
                if(lowestHealthPlant.getHealth() < Config.PLANT_HEALTH){
                    watering(area, (lowestHealthPlant.getWaterLowerbound() + lowestHealthPlant.getWaterUpperbound()) / 2);
                }
            }
        }
        // If there is a common range.
        else{
            int currentWater = waterSensor.getWater(area);
            if(currentWater < waterLowBound || currentWater > waterUpperBound){
                watering(area, (waterLowBound + waterUpperBound)/2);
            }
        }
    }

    public void watering(Area area, int targetRain){
        sprinkler.waterToAmount(area, targetRain);
    }

    /*
    // Maintain the water level within bound.
    void maintainWater(Garden garden, boolean checkIfDying){
        // if it doesn't need to check the lowest dying
        if(!checkIfDying){
            int currentTemp = waterSensor.getWater(garden);
            if(currentTemp < waterLowBound || currentTemp > waterUpperBound){
                watering(garden, waterLowBound + (waterUpperBound - waterLowBound)/2);
            }
        }
        else{
            Plant lowestHealthPlant = garden.findAreaWithLowestHealthPlant().getPlants().get(0);
            if(Objects.nonNull(lowestHealthPlant)) {
                // System.out.println(lowestHealthPlant.getHealth() + ", waterlb " + lowestHealthPlant.getWaterLowbound() + ", waterub " + lowestHealthPlant.getWaterUpperbound());

                if (lowestHealthPlant.getHealth() < Config.PLANT_HEALTH) {
                    watering(garden, (lowestHealthPlant.getWaterLowerbound() + lowestHealthPlant.getWaterUpperbound()) / 2);
                }
            }
        }
    }

    // Water the garden to an assigned value.
    void watering(Garden garden, int targetWater){
        this.sprinkler.waterToAmount(garden, targetWater);
    }

    // Adjust the temp setting according to the plants' information.
    // Assume such range exist.


     */

}
