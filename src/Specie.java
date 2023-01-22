import java.io.Serializable;

public class Specie implements Serializable{
	
	private String name;
	
	private float diaryRationKiloPerAnimal;
	
	private String obs;
	
	private int capacity;
	
	private int quantity;

	public Specie(String name, float diaryRationKiloPerAnimal, String obs, int capacity) {
	
		this.name = name;
		this.diaryRationKiloPerAnimal = diaryRationKiloPerAnimal;
		this.obs = obs;
		this.capacity = capacity;
		this.quantity = 0;
		
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getDiaryRationKiloPerAnimal() {
		return this.diaryRationKiloPerAnimal;
	}

	public void setDiaryRationKiloPerAnimal(float diaryRationKiloPerAnimal) {
		this.diaryRationKiloPerAnimal = diaryRationKiloPerAnimal;
	}

	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	//incrementa a quantidade da espécie e gera alerta caso atinja o limite ou exceda
	public void incrementQuantity(int quantity) {
		
		this.quantity += quantity;
		
		//se a capacidade for apenas de mais um animal da especie
		if(this.quantity == (this.capacity -1)) {
			
			System.out.println("\n!!! Alerta !!!\nCapacidade da espécie \"" + this.name + "\" no limite.\n");
			
		//se a capacidade da especie foi atingida
		}else if(this.quantity == this.capacity) {
			
			System.out.println("\n!!! Alerta !!!\nCapacidade da espécie \"" + this.name + "\" atingida.\n");
			
		//se a capacidade foi excedida
		}else if (this.quantity > this.capacity) {
			
			System.out.println("\n!!! Alerta !!!\nCapacidade da espécie \"" + this.name + "\" excedida em " + (this.quantity - this.capacity) +"animais.\n");
			
		}
		
	}
	
	//decrementa a quantidade da espécie
	public void decrementQuantity(int quantity) {
		
		if((this.quantity - quantity) >= 0) {
			
			this.quantity -= quantity;
			
		}
		
	}

}
