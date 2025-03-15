import java.util.List;
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

    // Given the garden and the specific area, maintain its temp
    public void maintainTemp(Area area, boolean checkIfDying){
        // If the best range of temp for each plant doesn't overlap.
        if(checkIfDying){
            Plant lowestHealthPlant = area.findAreaWithLowestHealthPlant();
            if(Objects.nonNull(lowestHealthPlant)){
                // System.out.println(lowestHealthPlant.getHealth() + ", templowbound " + lowestHealthPlant.getTempLowbound() + ", tempupperbound " + lowestHealthPlant.getTempUpperbound());
                if(lowestHealthPlant.getHealth() < Config.PLANT_HEALTH){
                    heating(area, (lowestHealthPlant.getTempLowerbound()+lowestHealthPlant.getTempUpperbound())/2);
                }
            }
        }
        // If there is a common range.
        else{
            int currentTemp = tempSensor.getTemp(area);
            if(currentTemp < tempLowBound || currentTemp > tempUpperBound){
                heating(area, tempLowBound + (tempUpperBound - tempLowBound)/2);
            }
        }
    }

    public void heating(Area area, int targetTemp){
        area.setCurrentTemp(targetTemp);
    }

    /* For the whole garden
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

     */

    // Adjust the temp setting according to the plants' information.
    // Assume such range exist.
    public boolean adjustSetting(List<Plant> plants){
        for(Plant plant: plants){
            if(plant.getTempLowerbound() > tempLowBound){
                this.tempLowBound = plant.getTempLowerbound();
            }
            if(plant.getTempUpperbound() < tempUpperBound){
                this.tempUpperBound = plant.getTempUpperbound();
            }
        }

        // System.out.println(plants.getFirst().getName() + " temp range " + tempLowBound + ", " + tempUpperBound);


        // If there's need to check target temp every time, return ture.
        return this.tempLowBound > this.tempUpperBound;

    }

}
