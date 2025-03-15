import java.util.ArrayList;
import java.util.List;
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

    // Initialize with parasites setting.
    PestControl(String[] parasites){
        this.pestSensor = new Sensor();
        this.sprinkler = new Sprinkler();
        this.parasites = new ArrayList<>();

        for(String para : parasites){
            if(!this.parasites.contains(para)){
                this.parasites.add(para);
            }
        }
    }

    // Kill all the harmful insects
    public void killInsects(Area area){
        // Get the information from the sensor.
        String[] insects = this.pestSensor.getPest(area);
        for(String insect: insects){
            if (this.parasites.contains(insect)){
                this.sprinkler.sprayPesticide(area, insect);
            }
        }
    }


    // Update the parasite array.
    public void updateSetting(List<Plant> plants){
        for(Plant plant: plants){
            for(String insect: plant.getParasites()){
                if(!this.parasites.contains(insect)){
                    this.parasites.add(insect);

                }
            }
        }
    }




}
