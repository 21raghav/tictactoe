public class Data {

    private String configuration; // comfiguration of data
    private int score;// score associated with the data

//comtructsnew data object
    public Data(String config, int score) {
        this.configuration = config;
        this.score = score;

    }
//gets configuration
    public String getConfiguration(){
        return configuration;

    }
//gets score
    public int getScore() {
        return score;
    }
}
