import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class GardenAPI {
    private Garden garden;
    private String configFilename = "input.txt";
    private Random random = new Random();

    private WateringSystem wateringSystem;
    private HeatingSystem heatingSystem;
    private PestControl pestControl;


    void initialize() throws IOException {
        int initialTemp = Config.GARDEN_TEMP_LOWER_BOUND + random.nextInt(Config.GARDEN_TEMP_RANGE);
        int initialRain = Config.GARDEN_RAIN_LOWER_BOUND + random.nextInt(Config.GARDEN_RAIN_RANGE);
//        String[] initialInsects = {};
        garden = new Garden(initialTemp, initialRain);

        // Initialize 3 systems.
        heatingSystem = new HeatingSystem(Config.GARDEN_TEMP_LOWER_BOUND,Config.GARDEN_TEMP_LOWER_BOUND +Config.GARDEN_TEMP_RANGE);
        wateringSystem = new WateringSystem(Config.GARDEN_RAIN_LOWER_BOUND,Config.GARDEN_RAIN_LOWER_BOUND +Config.GARDEN_RAIN_RANGE);
        pestControl = new PestControl();

        try (BufferedReader reader = new BufferedReader(new FileReader(configFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 6) {
                    throw new IOException("Invalid input format: " + line);
                }
                String name = parts[0];
                int tempLowbound = Integer.parseInt(parts[1]);
                int tempUpperbound = Integer.parseInt(parts[2]);
                String[] parasites = parts[3].split(",");
                int waterLowbound = Integer.parseInt(parts[4]);
                int waterUpperbound = Integer.parseInt(parts[5]);

                garden.addPlantToArea(name, tempLowbound, tempUpperbound, parasites, waterLowbound, waterUpperbound);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Invalid input file: " + e.getMessage());
        }

        // Adjust setting for supply systems.
//        heatingSystem.adjustSetting(garden.getPlants());
//        wateringSystem.adjustSetting(garden.getPlants());
//        pestControl.updateSetting(garden.getPlants());

    }





    void newDay() {
        int ifTemp = random.nextInt(2);
        int ifRain = random.nextInt(2);
        int ifInsect = random.nextInt(2);
        if(ifTemp == 1){
            int newTemp = Config.GARDEN_TEMP_LOWER_BOUND + random.nextInt(Config.GARDEN_TEMP_RANGE);
            temperature(newTemp);
        }
        if(ifRain == 1){
            int newRain = Config.GARDEN_RAIN_LOWER_BOUND + random.nextInt(Config.GARDEN_RAIN_RANGE);
            rain(newRain);
        }
        // insects differ in areas
        for (Area area : garden.getAreas()) {
            ifInsect = random.nextInt(2);
            if(ifInsect == 1){
                List<String> newInsects = new ArrayList<>();
                for (String insect : Config.GARDEN_POSSIBLE_INSECTS) {
                    if (random.nextInt(2) == 1) { // 50% chance for each insect
                        newInsects.add(insect);
                    }
                }
                area.setInsects(newInsects.toArray(new String[0])); // Assign new insects to area
            }

        }
//        wateringSystem.maintainWater(garden);
//        heatingSystem.maintainTemp(garden);
//        pestControl.killInsects(garden);

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
//    void parasite(String[] insects){
//        garden.setInsect(insects);
//    }

    // log garden's current state
    void logState() {
        System.out.println("-------- Current Garden State --------");
        System.out.println("Temperature: " + garden.getTemperature());
        System.out.println("Rain Amount: " + garden.getRainAmount());
        System.out.println("Plants Alive: " + garden.getAllAlivePlants().size());
        System.out.println("Plants Dead: " + garden.getDeadPlants().size());

        // Log the state of each area
        System.out.println("\nAreas:");
        for (Area area : garden.getAreas()) {
            System.out.println(area.toString()); // Use the toString() method of Area
        }

        System.out.println();
    }
}