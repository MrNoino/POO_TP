import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ManageSpecies implements Serializable{

	private ArrayList<Specie> species = new ArrayList<Specie>();
	
	private String filename;
	
	public ManageSpecies() {
		
		this.filename = "Species.dat";
		
	}
	
	//fun��o que retorna a cole��o de esp�cies
	public ArrayList<Specie> getSpecies(){
		
		return this.species;
		
	}
	
	//fun��o que liberta a cole��o de esp�cies da mem�ria
	public void clearSpecies() {
		
		if(this.species != null) {
			
			this.species.clear();
			
			this.species = null;
			
		}
		
	}
	
	//fun��o que carrega as esp�cies de um ficheiro de objetos
	public void loadSpecies() {
		
		File file = new File(this.filename);
		
		if(!file.exists()) {
			
			return;
			
		}
		
		this.species = new ArrayList<Specie>();
		
		try {
			
			ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(file));
			
			this.species.addAll((ArrayList<Specie>) objectStream.readObject());
			
			objectStream.close();
		
		} catch (FileNotFoundException e) {
			
			System.out.println("\nFicheiro n�o encontrado ao ler.\n");
			
			return;
			
		} catch (ClassNotFoundException e) {
			
			System.out.println("\nErro ao tentar ler o ficheiro.\n");
			
			return;
			
		} catch (IOException e) {
			
			System.out.println("\nErro de entrada ao tentar ler o ficheiro.\n");
			
			e.printStackTrace();
			
			return;
			
		}
		
	}
	
	//fun��o que grava as esp�cies num ficheiro de objetos
	public boolean saveSpecies() {
		
		File file = new File(this.filename);
		
		try {
			
			ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(file));
			
			objectStream.writeObject(this.species);
			
			objectStream.close();
			
			return true;
		
		} catch (FileNotFoundException e) {
			
			System.out.println("\nFicheiro n�o encontrado ao ler.\n");
			
			this.clearSpecies();
			return false;
			
		} catch (IOException e) {
			
			System.out.println("\nErro de entrada ao tentar escrever o ficheiro.\n");
			
			this.clearSpecies();
			return false;
			
		}finally {
			
			this.clearSpecies();
			
		}
		
	}
	
	//fun��o que adiciona uma esp�cie � cole��o
	public boolean addSpecie(Specie specie) {
			
		this.loadSpecies();
			
		
		if(this.species == null) {
			
			this.species = new ArrayList<Specie>();
			
		}
		
		this.species.add(specie);
			
		if(this.saveSpecies()) {
			
			this.clearSpecies();
			return true;
			
		}else {
			
			this.clearSpecies();
			return false;
			
		}
		
	}
	
	//fun��o que pesquisa esp�cie pelo seu nome
	public Specie searchSpecie(String name) {
			
		this.loadSpecies();
		
		if(this.species == null || this.species.size() == 0) {
			
			return null;
			
		}
		
		for(Specie specie : this.species) {
			
			if(specie.getName().equals(name)) {
				
				this.clearSpecies();
				return specie;
				
			}
			
		}
		
		this.clearSpecies();
		return null;
		
	}
	
	//fun��o que edita uma esp�cie
	public boolean editSpecie(String name, Specie specie) {
			
		this.loadSpecies();
		
		if(this.species == null || this.species.size() == 0) {
			
			return false;
			
		}
		
		for(int i = 0; i < this.species.size(); i++) {
			
			if(this.species.get(i).getName().equals(name)) {
				
				this.species.set(i, specie);
					
				if(this.saveSpecies()) {
					
					this.clearSpecies();
					return true;
					
				}else {
					
					this.clearSpecies();
					return false;
					
				}	
				
			}
			
		}
		
		this.clearSpecies();
		return false;
		
	}
	
	//fun��o que elimina uma esp�cie
	public boolean deleteSpecie(String name) {
			
		this.loadSpecies();
		
		if(this.species == null || this.species.size() == 0) {
			
			return false;
			
		}
		
		for(int i = 0; i < this.species.size(); i++) {
			
			if(this.species.get(i).getName().equals(name)) {
				
				this.species.remove(i);
					
				if(this.saveSpecies()) {
					
					this.clearSpecies();
					return true;
					
				}else {
					
					this.clearSpecies();
					return false;
					
				}
				
			}
			
		}
		
		this.clearSpecies();
		return false;
		
	}
	
	//fun��o que muda a quantidade da esp�cie
	public boolean changeQuantity(String operation, String name, int quantity) {
		
		if(operation.equals("add") || operation.equals("remove")) {
				
			this.loadSpecies();	
			
			if(this.species == null || this.species.size() == 0) {
				
				return false;
				
			}
			
		}else {
			
			return false;
			
		}
			
		for(int i = 0; i < this.species.size(); i++) {
			
			if(this.species.get(i).getName().equals(name)) {
				
				if(operation.equals("add")) {
					
					this.species.get(i).incrementQuantity(quantity);
					
					if(this.saveSpecies()) {
						
						this.clearSpecies();
						return true;
						
					}else {
						
						this.clearSpecies();
						return false;
						
					}
					
				}else if(operation.equals("remove")) {
					
					this.species.get(i).decrementQuantity(quantity);
					
					if(this.saveSpecies()) {
						
						this.clearSpecies();
						return true;
						
					}else {
						
						this.clearSpecies();
						return false;
						
					}
					
				}
				
			}
			
		}
		
		return false;
		
	}
	
	//fun��o que devolve os dados de todas as esp�cies
	public String toString() {
			
		this.loadSpecies();
		
		if(this.species == null || this.species.size() == 0) {
			
			return "Nenhuma esp�cie encontrada\n";
			
		}
		
		String buffer = "Esp�cies\n\n";
		
		for(Specie specie : this.species) {
			
			buffer += "Esp�cie: " + specie.getName() 
					+ "\nRa��o di�ria para um animal da esp�cie: " + specie.getDiaryRationKiloPerAnimal()
					+ "\nObserva��es: " + specie.getObs()
					+ "\nQuantidade de animais da esp�cie: " + specie.getQuantity() 
					+ "\nCapacidade m�xima da esp�cie: " + specie.getCapacity()
					+ "\n\n";
			
		}
		
		return buffer;
		
		
	}
	
}
