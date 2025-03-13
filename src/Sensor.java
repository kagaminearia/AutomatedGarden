public class Sensor {
    public int getTemp(Garden garden){
        return garden.getTemperature();
    }

    public int getWater(Garden garden){
        return garden.getRainAmount();
    }

    public String[] getPest(Garden garden){
        return garden.getInsects();
    }


}
