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
	
	//fun��o que liberta a cole��o de ra��es da mem�ria
	public void clearRations() {
		
		if(this.rations != null) {
			
			this.rations.clear();
			
			this.rations = null;
			
		}
		
	}
	
	//fun��o que carrega as ra��es de um ficheiro de objetos
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
			
			System.out.println("\nFicheiro n�o encontrado ao ler.\n");
			
			return;
			
		} catch (ClassNotFoundException e) {
			
			System.out.println("\nErro ao tentar ler o ficheiro.\n");
			
			return;
			
		} catch (IOException e) {
			
			System.out.println("\nErro de entrada ao tentar ler o ficheiro.\n");
			
			return;
			
		}
		
	}
	
	//fun��o que grava as ra��es num ficheiro de objetos
	public boolean saveRations() {
		
		File file = new File(this.filename);
		
		try {
			
			ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(file));
			
			objectStream.writeObject(this.rations);
			
			objectStream.close();
			
			return true;
		
		} catch (FileNotFoundException e) {
			
			System.out.println("\nFicheiro n�o encontrado ao ler.\n");
			
			return false;
			
		} catch (IOException e) {
			
			System.out.println("\nErro de entrada ao tentar escrever o ficheiro.\n");
			
			return false;
			
		}finally {
			
			this.clearRations();
			
		}
		
	}
	
	//fun��o que devolve a cole��o de ra��es
	public ArrayList<Ration> getRations(){
		
		return this.rations;
		
	}
	
	//fun��o que adiciona uma ra��o � cole��o de ra��es
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
	
	//fun��o que pesquisa por uma ra��o atrav�s da sua marca
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
	
	//fun��o que edita uma ra��o
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
	
	//fun��o que elimina uma ra��o
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
	
	//fun��o que devolve os dados de todas as ra��es
	public String toString() {
			
		this.loadRations();
		
		if(this.rations == null || this.rations.size() == 0) {
			
			return "Nenhuma ra��o encontrada\n";
			
		}
		
		String buffer = "Ra��es\n\n";
		
		for(Ration ration : this.rations) {
			
			buffer += "Ra��o: " + ration.getBrand() + "\n"
					+ "Idades recomendadas: entre " + ration.getAgeLimits()[0] + " e " + ration.getAgeLimits()[1] + "\n"
					+ "Pre�o por kilograma: " + ration.getPricePerKilo() + " �\n"
					+ "Esp�cie da ra��o: " + ration.getSpecie() + "\n\n";
			
		}
		
		return buffer;
		
	}
	
}
