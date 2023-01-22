import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ManageRations implements Serializable{

	private ArrayList<Ration> rations = null;
	
	private String filename;
	
	public ManageRations() {
		
		this.filename = "Rations.dat";
		
	}
	
	//função que liberta a coleção de rações da memória
	public void clearRations() {
		
		if(this.rations != null) {
			
			this.rations.clear();
			
			this.rations = null;
			
		}
		
	}
	
	//função que carrega as rações de um ficheiro de objetos
	public void loadRations() {
		
		File file = new File(this.filename);
		
		if(!file.exists()) {
			
			return;
			
		}
		
		this.rations = new ArrayList<Ration>();
		
		try {
			
			ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(file));
			
			this.rations.addAll((ArrayList<Ration>) objectStream.readObject());
			
			objectStream.close();
		
		} catch (FileNotFoundException e) {
			
			System.out.println("\nFicheiro não encontrado ao ler.\n");
			
			return;
			
		} catch (ClassNotFoundException e) {
			
			System.out.println("\nErro ao tentar ler o ficheiro.\n");
			
			return;
			
		} catch (IOException e) {
			
			System.out.println("\nErro de entrada ao tentar ler o ficheiro.\n");
			
			return;
			
		}
		
	}
	
	//função que grava as rações num ficheiro de objetos
	public boolean saveRations() {
		
		File file = new File(this.filename);
		
		try {
			
			ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(file));
			
			objectStream.writeObject(this.rations);
			
			objectStream.close();
			
			return true;
		
		} catch (FileNotFoundException e) {
			
			System.out.println("\nFicheiro não encontrado ao ler.\n");
			
			return false;
			
		} catch (IOException e) {
			
			System.out.println("\nErro de entrada ao tentar escrever o ficheiro.\n");
			
			return false;
			
		}finally {
			
			this.clearRations();
			
		}
		
	}
	
	//função que devolve a coleção de rações
	public ArrayList<Ration> getRations(){
		
		return this.rations;
		
	}
	
	//função que adiciona uma ração à coleção de rações
	public boolean addRation(Ration ration) {
		
			
		this.loadRations();
		
		if(this.rations == null) {
			
			this.rations = new ArrayList<Ration>();
			
		}
		
		this.rations.add(ration);
			
		if(this.saveRations()) {
			
			this.clearRations();
			return true;
			
		}else {
			
			this.clearRations();
			return false;
			
		}
		
	}
	
	//função que pesquisa por uma ração através da sua marca
	public Ration searchRation(String brand) {
			
		this.loadRations();
		
		if(this.rations == null || this.rations.size() == 0) {
			
			return null;
			
		}
		
		for(Ration ration : this.rations) {
			
			if(ration.getBrand().equals(brand)) {
				
				this.clearRations();
				return ration;
				
			}
			
		}
		
		this.clearRations();
		return null;
		
	}
	
	//função que edita uma ração
	public boolean editRation(String brand, Ration ration) {
			
		this.loadRations();	
		
		if(this.rations == null || this.rations.size() == 0) {
			
			return false;
			
		}
		
		for(int i = 0; i < this.rations.size(); i++) {
			
			if(this.rations.get(i).getBrand().equals(brand)) {
				
				this.rations.set(i, ration);
					
				if(this.saveRations()) {
					
					this.clearRations();
					return true;
					
				}else {
					
					this.clearRations();
					return false;
					
				}
				
			}
			
		}
		
		this.clearRations();
		return false;
		
	}
	
	//função que elimina uma ração
	public boolean deleteRation(String brand) {
			
		this.loadRations();
				
		if(this.rations == null || this.rations.size() == 0) {
			
			return false;
			
		}
		
		for(int i = 0; i < this.rations.size(); i++) {
			
			if(this.rations.get(i).getBrand().equals(brand)) {
				
				this.rations.remove(i);
					
				if(this.saveRations()) {
					
					this.clearRations();
					return true;
					
				}else {
					
					this.clearRations();
					return false;
					
				}
				
			}
			
		}
		
		this.clearRations();
		return false;
		
	}
	
	//função que devolve os dados de todas as rações
	public String toString() {
			
		this.loadRations();
		
		if(this.rations == null || this.rations.size() == 0) {
			
			return "Nenhuma ração encontrada\n";
			
		}
		
		String buffer = "Rações\n\n";
		
		for(Ration ration : this.rations) {
			
			buffer += "Ração: " + ration.getBrand() + "\n"
					+ "Idades recomendadas: entre " + ration.getAgeLimits()[0] + " e " + ration.getAgeLimits()[1] + "\n"
					+ "Preço por kilograma: " + ration.getPricePerKilo() + " €\n"
					+ "Espécie da ração: " + ration.getSpecie() + "\n\n";
			
		}
		
		return buffer;
		
	}
	
}
