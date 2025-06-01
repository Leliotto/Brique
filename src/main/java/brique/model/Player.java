package brique.model;

public class Player {
    private String name;
  // Constructors
    public Player() {
        this.name = "None";
    }
    public Player(String name){
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }

    public String name() {return name;}

    public void setName(String name) {this.name = name;}
}
