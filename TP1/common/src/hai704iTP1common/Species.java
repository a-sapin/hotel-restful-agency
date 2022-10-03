package hai704iTP1common;

public class Species {
	private String name;
	private int lifespan;
	
	public Species(String name, int lifespan) {
		this.name = name;
		this.lifespan = lifespan;
	}
	
	@Override
	public String toString() {
		return "L'espèce de l'animal est "+name+" et vit en moyenne "+String.valueOf(lifespan)+" ans";
	}
}
