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
	
	//função que retorna a coleção de espécies
	public ArrayList<Specie> getSpecies(){
		
		return this.species;
		
	}
	
	//função que liberta a coleção de espécies da memória
	public void clearSpecies() {
		
		if(this.species != null) {
			
			this.species.clear();
			
			this.species = null;
			
		}
		
	}
	
	//função que carrega as espécies de um ficheiro de objetos
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
			
			System.out.println("\nFicheiro não encontrado ao ler.\n");
			
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
	
	//função que grava as espécies num ficheiro de objetos
	public boolean saveSpecies() {
		
		File file = new File(this.filename);
		
		try {
			
			ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(file));
			
			objectStream.writeObject(this.species);
			
			objectStream.close();
			
			return true;
		
		} catch (FileNotFoundException e) {
			
			System.out.println("\nFicheiro não encontrado ao ler.\n");
			
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
	
	//função que adiciona uma espécie à coleção
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
	
	//função que pesquisa espécie pelo seu nome
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
	
	//função que edita uma espécie
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
	
	//função que elimina uma espécie
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
	
	//função que muda a quantidade da espécie
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
	
	//função que devolve os dados de todas as espécies
	public String toString() {
			
		this.loadSpecies();
		
		if(this.species == null || this.species.size() == 0) {
			
			return "Nenhuma espécie encontrada\n";
			
		}
		
		String buffer = "Espécies\n\n";
		
		for(Specie specie : this.species) {
			
			buffer += "Espécie: " + specie.getName() 
					+ "\nRação diária para um animal da espécie: " + specie.getDiaryRationKiloPerAnimal()
					+ "\nObservações: " + specie.getObs()
					+ "\nQuantidade de animais da espécie: " + specie.getQuantity() 
					+ "\nCapacidade máxima da espécie: " + specie.getCapacity()
					+ "\n\n";
			
		}
		
		return buffer;
		
		
	}
	
}
