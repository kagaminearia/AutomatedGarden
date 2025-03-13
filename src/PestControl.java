import java.util.ArrayList;
import java.util.PriorityQueue;

public class PestControl {
    private Sensor pestSensor;
    private Sprinkler sprinkler;
    private ArrayList<String> parasites;         // parasite records all kinds of insects that might be harmful

    PestControl(){
        this.pestSensor = new Sensor();
        this.sprinkler = new Sprinkler();
        this.parasites = new ArrayList<>();
    }

    // Kill all the harmful insects
    public void killInsects(Garden garden){
        // Get the information from the sensor.
        String[] insects = this.pestSensor.getPest(garden);
        for(String insect: insects){
            if (this.parasites.contains(insect)){
                this.sprinkler.sprayPesticide(garden, insect);
            }
        }
    }

    // Update the parasite array.
    public void updateSetting( PriorityQueue<Plant> plants){
        for(Plant plant: plants){
            for(String insect: plant.getParasites()){
                if(!this.parasites.contains(insect)){
                    this.parasites.add(insect);
                    System.out.print(insect);
                }
            }
        }

    }
}
