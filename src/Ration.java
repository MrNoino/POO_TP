import java.io.Serializable;

public class Ration implements Serializable{
	
	private String brand;
	
	private int[] ageLimits;
	
	private float pricePerKilo;
	
	private String specie;

	public Ration(String brand, int[] ageLimits, float pricePerKilo, String specie) {
		
		this.brand = brand;
		this.ageLimits = ageLimits;
		this.pricePerKilo = pricePerKilo;
		this.specie = specie;
		
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int[] getAgeLimits() {
		return this.ageLimits;
	}

	public void setAgeLimits(int[] ageLimits) {
		this.ageLimits = ageLimits;
	}

	public float getPricePerKilo() {
		return this.pricePerKilo;
	}

	public void setPricePerKilo(float pricePerKilo) {
		this.pricePerKilo = pricePerKilo;
	}

	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

}
