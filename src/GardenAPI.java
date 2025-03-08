import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

class GardenAPI {
    private Garden garden;
    private String configFilename = "input.txt";
    private Random random = new Random();


    void initialize() throws IOException {
        int initialTemp = Config.GARDEN_TEMP_LOW_BOUND + random.nextInt(Config.GARDEN_TEMP_RANGE);
        int initialRain = Config.GARDEN_RAIN_LOW_BOUND + random.nextInt(Config.GARDEN_RAIN_RANGE);
        String[] initialInsects = {};
        garden = new Garden(initialTemp, initialRain, initialInsects);

        try (BufferedReader reader = new BufferedReader(new FileReader(configFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String name = parts[0];
                int tempLowbound = Integer.parseInt(parts[1]);
                int tempUpperbound = Integer.parseInt(parts[2]);
                String[] parasites = parts[3].split(",");
                int waterLowbound = Integer.parseInt(parts[4]);
                int waterUpperbound = Integer.parseInt(parts[5]);

                Plant plant = new Plant(name, tempLowbound, tempUpperbound, parasites, waterLowbound, waterUpperbound);
                garden.addPlant(plant);
            }
        }
    }

    void newDay() {
        int ifTemp = random.nextInt(2);
        int ifRain = random.nextInt(2);
        int ifInsect = random.nextInt(2);
        if(ifTemp == 1){
            int newTemp = Config.GARDEN_TEMP_LOW_BOUND + random.nextInt(Config.GARDEN_TEMP_RANGE);
            temperature(newTemp);
        }
        if(ifRain == 1){
            int newRain = Config.GARDEN_RAIN_LOW_BOUND + random.nextInt(Config.GARDEN_RAIN_RANGE);
            rain(newRain);
        }
        if(ifInsect == 1){
            List<String> newInsects = new ArrayList<>();
            for(String i : Config.GARDEN_POSSIBLE_INSECTS){
                int binary = random.nextInt(2);
                if(binary==1)newInsects.add(i);
            }
            String[] added = newInsects.toArray(new String[0]);
            parasite(added);
        }

        dayPass();
        logState();
    }

    void dayPass() {
        garden.dayPass();
    }

    // set garden's weather
    void rain(int rainAmount){
        garden.setRainAmount(rainAmount);
    }

    // set garden's temperature
    void temperature(int temp){
        garden.setTemperature(temp);
    }

    // generate parasite in garden
    void parasite(String[] insects){
        garden.setInsect(insects);
    }

    // log garden's current state
    void logState(){
        System.out.println("-------- Current Garden State --------");
        System.out.println("Temperature: " + garden.getTemperature());
        System.out.println("Rain Amount: " + garden.getRainAmount());
        System.out.println("Insects: " + String.join(", ", garden.getInsects()));
        System.out.println("Plants Alive: " + garden.getPlants().size());
        System.out.println("Plants Dead: " + garden.getDeadPlants().size());
        System.out.println();
    }
}
