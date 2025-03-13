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
    void maintainWater(Garden garden){
        int currentTemp = waterSensor.getTemp(garden);
        if(currentTemp < waterLowBound || currentTemp > waterUpperBound){
            watering(garden, waterLowBound + (waterUpperBound - waterLowBound)/2);
        }

    }

    // Water the garden to an assigned value.
    void watering(Garden garden, int targetWater){
        this.sprinkler.waterToAmount(garden, targetWater);
    }

    // Adjust the temp setting according to the plants' information.
    // Assume such range exist.
    void adjustSetting(PriorityQueue<Plant> plants){
        for(Plant plant: plants){
            if(plant.getWaterLowbound() > waterLowBound){
                this.waterLowBound = plant.getWaterLowbound();
            }
            if(plant.getWaterUpperbound() < waterUpperBound){
                this.waterUpperBound = plant.getWaterUpperbound();
            }
        }

        // System.out.println(waterLowBound + "," + waterUpperBound);
    }

}
