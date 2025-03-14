import java.util.Objects;
import java.util.PriorityQueue;

public class HeatingSystem {
    private int tempLowBound = Integer.MIN_VALUE;
    private int tempUpperBound = Integer.MAX_VALUE;
    private Sensor tempSensor;

    HeatingSystem(int tempLowBound, int tempUpperBound){
        this.tempLowBound = tempLowBound;
        this.tempUpperBound = tempUpperBound;
        this.tempSensor = new Sensor();
    }

    // Maintain the temp within bound.
    public void maintainTemp(Garden garden){
        int currentTemp = tempSensor.getTemp(garden);
        if(currentTemp < tempLowBound || currentTemp > tempUpperBound){
            heating(garden, tempLowBound + (tempUpperBound - tempLowBound)/2);
        }

    }

    // Retrieve the lowest health plant, set the temp to its temp range's middle.
    public void maintainTemp(Garden garden, boolean checkIfDying){
        if(!checkIfDying){
            int currentTemp = tempSensor.getTemp(garden);
            if(currentTemp < tempLowBound || currentTemp > tempUpperBound){
                heating(garden, tempLowBound + (tempUpperBound - tempLowBound)/2);
            }
        }
        else{
            Plant lowestHealthPlant = garden.findAreaWithLowestHealthPlant().getPlants().get(0);
            if(Objects.nonNull(lowestHealthPlant)){
                // System.out.println(lowestHealthPlant.getHealth() + ", templowbound " + lowestHealthPlant.getTempLowbound() + ", tempupperbound " + lowestHealthPlant.getTempUpperbound());
                if(lowestHealthPlant.getHealth() < Config.PLANT_HEALTH){
                    heating(garden, (lowestHealthPlant.getTempLowerbound()+lowestHealthPlant.getTempUpperbound())/2);
                }
            }


        }

    }

    // Heat the garden to a assigned value.
    public void heating(Garden garden, int targetTemp){
        garden.setTemperature(targetTemp);
    }

    // Adjust the temp setting according to the plants' information.
    // Assume such range exist.
    public boolean adjustSetting(PriorityQueue<Plant> plants){
        for(Plant plant: plants){
            if(plant.getTempLowerbound() > tempLowBound){
                this.tempLowBound = plant.getTempLowerbound();
            }
            if(plant.getTempUpperbound() < tempUpperBound){
                this.tempUpperBound = plant.getTempUpperbound();
            }
        }

        System.out.println(tempLowBound + "," + tempUpperBound);

        return this.tempLowBound > this.tempUpperBound;

    }

}
