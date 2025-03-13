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
    void maintainTemp(Garden garden){
        int currentTemp = tempSensor.getTemp(garden);
        if(currentTemp < tempLowBound || currentTemp > tempUpperBound){
            heating(garden, tempLowBound + (tempUpperBound - tempLowBound)/2);
        }

    }

    // Heat the garden to a assigned value.
    void heating(Garden garden, int targetTemp){
        garden.setTemperature(targetTemp);
    }

    // Adjust the temp setting according to the plants' information.
    // Assume such range exist.
    void adjustSetting(PriorityQueue<Plant> plants){
        for(Plant plant: plants){
            if(plant.getTempLowbound() > tempLowBound){
                this.tempLowBound = plant.getTempLowbound();
            }
            if(plant.getTempUpperbound() < tempUpperBound){
                this.tempUpperBound = plant.getTempUpperbound();
            }
        }

        System.out.println(tempLowBound + "," + tempUpperBound);
    }

}
