import java.io.Serializable;

public class Animal implements Serializable {
	
	private String name;
	
	private String specie;
	
	private String gender;
	
	private float weight;
	
	private String breed;//raça
	
	private int age;
	
	private String arrivedDate;
	
	private String obs;
	
	private String state;
	
	private boolean sterilized;

	public Animal(String name, String specie, String gender, float weight, String breed, int age, String arrivedDate, String obs, String state, boolean sterilized) {
		
		this.name = name;
		this.specie = specie;
		this.gender = gender;
		this.weight = weight;
		this.breed = breed;
		this.age = age;
		this.arrivedDate = arrivedDate;
		this.obs = obs;
		this.state = state;
		this.specie = specie;
		this.sterilized = sterilized;
		
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecie() {
		return this.specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public float getWeight() {
		return this.weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public int getAge() {
		
		return this.age;
		
	}

	public void setAge(int age) {
		
		this.age = age;
		
	}

	public String getBreed() {
		
		return this.breed;
		
	}

	public void setBreed(String breed) {
		
		this.breed = breed;
		
	}

	public String getArrivedDate() {
		
		return this.arrivedDate;
		
	}

	public void setArrivedDate(String arrivedDate) {
		
		this.arrivedDate = arrivedDate;
		
	}

	public String getObs() {
		
		return this.obs;
		
	}

	public void setObs(String obs) {
		
		this.obs = obs;
		
	}

	public String getState() {
		
		return this.state;
		
	}

	public void setState(String state) {
		
		this.state = state;
		
	}
	
	//acrescenta uma string as observações
	public void appendObs(String obs) {
		
		if(this.obs.isBlank()) {
			
			this.obs = "";
			
		}
		
		this.obs += obs;
		
	}

	public boolean isSterilized() {
		
		return sterilized;
		
	}

	public void setSterilized(boolean sterilized) {
		
		this.sterilized = sterilized;
		
	}

}
