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
    public boolean adjustSetting(PriorityQueue<Plant> plants){
        for(Plant plant: plants){
            if(plant.getWaterLowerbound() > waterLowBound){
                this.waterLowBound = plant.getWaterLowerbound();
            }
            if(plant.getWaterUpperbound() < waterUpperBound){
                this.waterUpperBound = plant.getWaterUpperbound();
            }
        }

        System.out.println(waterLowBound + "," + waterUpperBound);

        return this.waterLowBound > this.waterUpperBound;


    }

}
