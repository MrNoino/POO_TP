import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManageAnimals implements Serializable{
	
	private ArrayList<Animal> animals = null;
	
	private String filename;

	public ManageAnimals() {
		
		this.filename = "Animals.dat";
		
	}
	
	//limpa a lista de animais da memoria
	public void clearAnimals() {
		
		if(this.animals != null) {
			
			this.animals.clear();
			
			this.animals = null;
			
		}
		
	}
	
	//carrega os animais do ficheiro de objetos
	public void loadAnimals(){
		
		File file = new File(this.filename);
		
		//se oficheiro nao existe
		if(!file.exists()) {
			
			//fecha a fun��o
			return;
			
		}
		
		//inicializa a cole��o de animais
		this.animals = new ArrayList<Animal>();
		
		try {
			
			//inicia um objeto de leitura de ficheiros de objetos
			ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(file));
			
			//le e adiciona a cole��o de dentro do ficheiro para a cole��o desta classe
			this.animals.addAll((ArrayList<Animal>) objectStream.readObject());
			
			//fecha o objeto de leitura
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
	
	//fun��o para gravar os animais num ficheiro de objetos
	public boolean saveAnimals() {
		
		File file = new File(this.filename);
		
		//se nao existir animais
		if(this.animals == null || this.animals.size() == 0) {
			
			//fecha a fun��o
			return false;
			
		}
		
		try {
			
			//cria um objeto de escrita de ficheiro de objetos
			ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(file));
			
			//escreve a cole��o de animais
			objectStream.writeObject(this.animals);
			
			//fecha o objeto de escrita
			objectStream.close();
			
		
		} catch (FileNotFoundException e) {
			
			System.out.println("\nFicheiro n�o encontrado ao ler.\n");
			
			this.clearAnimals();
			return false;
			
		} catch (IOException e) {
			
			System.out.println("\nErro de entrada ao tentar escrever o ficheiro.\n");
			
			this.clearAnimals();
			return false;
			
		}finally {
			
			//liberta a cole��o
			this.clearAnimals();
			
		}
		
		return true;
		
	}
	
	//fun��o para adicionar animal
	public boolean addAnimal(Animal animal) {
			
		//carrega os animais
		this.loadAnimals();
		
		//se nao existir nenhum animal
		if(this.animals == null) {
			
			//iniciliza a cole��o
			this.animals = new ArrayList<Animal>();
			
		}
		
		//adiciona o animal � cole��o
		this.animals.add(animal);
			
		//se foi gravado com sucesso
		if(this.saveAnimals()) {
			
			ManageSpecies manageSpecies = new ManageSpecies();
			
			//aumenta a quantidade da esp�cie
			manageSpecies.changeQuantity("add", animal.getSpecie(), 1);
	
			//liberta a cole��o da mem�ria
			this.clearAnimals();
			
			return true;
			
			
		}else {
			
			//liberta a cole��o da mem�ria
			this.clearAnimals();
			
			return false;
			
		}
		
	}
	
	//fun��o para editar um animal
	public boolean editAnimal(String name, Animal animal) {
		
		//carrega os animais	
		this.loadAnimals();
		
		//se nao existir nenhum animal
		if(this.animals == null || this.animals.size() == 0) {
			
			//fecha a fun��o
			return false;
			
		}
		
		//percorre a cole��o
		for(int i = 0; i < this.animals.size(); i++) {
			
			//se o animal � igual ao nome a procurar
			if(this.animals.get(i).getName().equals(name)) {
				
				//substitui o animal
				this.animals.set(i, animal);	
				
				//grava os animais
				return this.saveAnimals();
				
			}
			
		}
		
		//liberta a cole��o da mem�ria
		this.clearAnimals();
		return false;
		
	}
	
	//fun��o para eliminar um animal
	public boolean deleteAnimal(String name) {
			
		//carrega os animais
		this.loadAnimals();
		
		//se nao existe animais
		if(this.animals == null || this.animals.size() == 0) {
			
			//fecha a fun��o
			return false;
			
		}
		
		//percorre os animais
		for(int i = 0; i < this.animals.size(); i++) {
			
			//se o animal � igual ao nome a procurar
			if(this.animals.get(i).getName().equals(name)) {
				
				//obtem a esp�cie
				String specie = this.animals.get(i).getSpecie();
				
				//remove o animal
				this.animals.remove(i);
					
				//se foi gravado com sucesso
				if(this.saveAnimals()) {
					
					ManageSpecies manageSpecies = new ManageSpecies();
					
					//decrementa na quantidade da esp�cie
					manageSpecies.changeQuantity("remove", specie, 1);
					
					return true;
					
				}else {
					
					//liberta a cole��o da mem�ria
					this.clearAnimals();
					return false;
					
				}
				
			}
			
		}
		
		this.clearAnimals();
		return false;
		
	}
	
	//fun��o que devolve os nomes dos animais de todas as esp�cies
	public String returnNamesByAllSpecies() {
			
		this.loadAnimals();
			
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return "Nenhum animal encontrado";
			
		}
		
		String buffer = "";
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		manageSpecies.loadSpecies();
	
		for(Specie specie : manageSpecies.getSpecies()) {
			
			buffer += "Animais da esp�cie \"" + specie.getName() + "\":\n\n";
			
			for(Animal animal : this.animals) {
				
				if(specie.getName().equals(animal.getSpecie())) {
					
					buffer += animal.getName() + "\n";
					
				}
				
			}
			
			buffer += "\n";
			
		}
		
		this.clearAnimals();
		
		return buffer;
		
	}
	
	//fun��o que devolve os nomes e idades de uma esp�cie especifica
	public String returnNamesAgesBySpecie(String specie) {
			
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return "Nenhum animal encontrado";
			
		}
		
		String buffer = "Animais da esp�cie \"" + specie + "\"\n\n";
		
		for(Animal animal : this.animals) {
			
			if(animal.getSpecie().equals(specie)) {
				
				buffer += "Nome: " + animal.getName() + "\n"
						+ "Idade: " + animal.getAge() + "\n\n";
				
			}
			
		}
		
		this.clearAnimals();
		
		return buffer;
		
	}
	
	//fun��o que devolve nomes, idades e observa��es de animais de uma ra�a especifica
	public String returnNamesAgesObsByBreed(String breed) {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return "Nenhum animal encontrado";
			
		}
		
		String buffer = "Animais da ra�a \"" + breed + "\"\n\n";
		
		for(Animal animal : this.animals) {
			
			if(animal.getBreed().equals(breed)) {
				
				buffer += "Nome: " + animal.getName() + "\n"
						+ "Idade: " + animal.getAge() + "\n"
						+ "Observa��es: " + animal.getObs() + "\n\n";
				
			}
			
		}
		
		this.clearAnimals();
		
		return buffer;
		
	}
	
	//devolve quantidade de animais adotados no abrigo
	public String returnQuantityAdoptedByAllSpecie() {
			
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return "Nenhum animal encontrado";
			
		}
		
		int count = 0;
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		manageSpecies.loadSpecies();
		
		String buffer = "";
		
		for(Specie specie : manageSpecies.getSpecies()) {
			
			for(Animal animal : this.animals) {
				
				if(animal.getSpecie().equals(specie.getName())) {
					
					if(animal.getState().equals("Adotado") || animal.getState().equals("adotado")) {
						
						count ++;
						
					}
					
				}
				
			}
			
			buffer += "Dos animais da esp�cie \"" + specie.getName() + "\", " + count + " foram adotados.\n\n";
			
		}
		
		manageSpecies.clearSpecies();
		
		this.clearAnimals();
		
		return buffer;
		
	}
	
	//gera relat�rios de animais por esp�cie
	public int generateReportsPerSpecie() {
			
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			System.out.println("Nenhum animal encontrado");
			
			return 0;
			
		}
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		manageSpecies.loadSpecies();
		
		String buffer;
		
		int file_count = 0;
		
		for(Specie specie : manageSpecies.getSpecies()) {
			
			buffer = "";
			
			for(Animal animal : this.animals) {
				
				if(specie.getName().equals(animal.getSpecie())) {
					
					buffer += "Animal: " + animal.getName() + "\nPeso: " + animal.getWeight() + "\nIdade: " + animal.getAge() + "\n\n";
					
				}
				
			}
			
			if(!buffer.isBlank()) {
				
					try {
						
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(specie.getName() + ".txt")));
						
						bufferedWriter.write(buffer);
						
						bufferedWriter.close();
						
						file_count++;
						
						
					} catch (IOException e) {
						
						System.out.println("Erro ao escrever o ficheiro para a esp�cie \"" + specie.getName() + "\"");
						
					}
				
			}
			
		}
		
		manageSpecies.clearSpecies();
		
		this.clearAnimals();
		
		return file_count;
		
	}
	
	//gera relat�rio de ado��o
	public boolean  generateAdoptionReport() {
			
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			System.out.println("Nenhum animal encontrado");
			
			return false;
			
		}
		
		String buffer = "";
		
		int file_count = 0;
			
		for(Animal animal : this.animals) {
			
			if(animal.getState().equals("Adotado") || animal.getState().equals("adotado")) {
				
				buffer += "Animal: " + animal.getName() + "\nEsp�cie: " + animal.getSpecie() + "\nDono: " + animal.getObs() + "\n\n";
				
			}
			
		}
			
		if(!buffer.isBlank()) {
			
			try {
				
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("RelatorioAdocao.txt"));
				
				bufferedWriter.write(buffer);
				
				bufferedWriter.close();
				
				return true;
				
				
			} catch (IOException e) {
				
				System.out.println("Erro ao escrever o relat�rio de ado��o.\n");
				
				return false;
				
			}
			
		}
		
		return false;
		
	}
	
	//gera realat�rios por esp�cie de animais com ra�a n�o indefinida
	public int generateBreedAnimalsPerSpecieReport() {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			System.out.println("Nenhum animal encontrado");
			
			return 0;
			
		}
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		manageSpecies.loadSpecies();
		
		String buffer;
		
		int file_count = 0;
		
		for(Specie specie : manageSpecies.getSpecies()) {
			
			buffer = "";
			
			for(Animal animal : this.animals) {
				
				if(specie.getName().equals(animal.getSpecie())) {
					
					if(!animal.getBreed().isBlank() || animal.getBreed().equals("Indefinida") || animal.getBreed().equals("indefinida")) {
						
						buffer += "Animal: " + animal.getName() + "\nIdade: " + animal.getAge() + "\nRa�a: " + animal.getBreed() + "\n\n";
						
					}
					
				}
				
			}
			
			if(!buffer.isBlank()) {
				
					try {
						
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(specie.getName() + "_raca.txt")));
						
						bufferedWriter.write(buffer);
						
						bufferedWriter.close();
						
						file_count++;
						
						
					} catch (IOException e) {
						
						System.out.println("Erro ao escrever o ficheiro para a esp�cie \"" + specie.getName() + "\"");
						
					}
				
			}
			
		}
		
		manageSpecies.clearSpecies();
		
		this.clearAnimals();
		
		return file_count;
		
	}
	
	//fun��o que reporta um ato veterin�rio
	public boolean reportVetAct(String name, String report) {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			System.out.println("Nenhum animal encontrado");
			
			return false;
			
		}
		
		for(int i = 0; i < this.animals.size(); i++) {
			
			if(this.animals.get(i).getName().equals(name)) {
				
				this.animals.get(i).appendObs("\n" + report + "\nData: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "\n");
				
				String buffer = "Animal: " + this.animals.get(i).getName() + "\nActos Veterin�rios: " + report + "\n\n";
				
				if(this.saveAnimals()) {
					
					try {
						
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("ActosVeterinarios_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".txt"), true));
						
						bufferedWriter.write(buffer);
						
						bufferedWriter.close();
						
						this.clearAnimals();
						return true;
						
					} catch (IOException e) {
						
						this.clearAnimals();
						return false;
						
					}
					
				}else {
					
					this.clearAnimals();
					return false;
					
				}
				
			}
			
		}
		
		this.clearAnimals();
		return false;
		
	}
	
	//fun��o que reporta uma ado��o
	public boolean reportAdotion(String name, String owner) {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return false;
			
		}
		
		for(int i = 0; i < this.animals.size(); i++) {
			
			if(this.animals.get(i).getName().equals(name) && !this.animals.get(i).getState().equals("Adotado") && !this.animals.get(i).getState().equals("Falecido")) {
				
				this.animals.get(i).setState("Adotado");
				
				this.animals.get(i).setObs(owner);	
				
				ManageSpecies manageSpecies = new ManageSpecies();
				
				manageSpecies.changeQuantity("remove", this.animals.get(i).getSpecie(), 1);
				
				return this.saveAnimals();
				
			}
			
		}
		
		this.clearAnimals();
		return false;
		
	}
	
	//fun��o que reporta um �bito
	public boolean reportDeath(String name, String date) {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return false;
			
		}
		
		for(int i = 0; i < this.animals.size(); i++) {
			
			if(this.animals.get(i).getName().equals(name) && !this.animals.get(i).getState().equals("Adotado") && !this.animals.get(i).getState().equals("Falecido")) {
				
				this.animals.get(i).setState("Falecido");
				
				this.animals.get(i).setObs(date);
				
				ManageSpecies manageSpecies = new ManageSpecies();
				
				manageSpecies.changeQuantity("remove", this.animals.get(i).getSpecie(), 1);
				
				return this.saveAnimals();
				
			}
			
		}
		
		this.clearAnimals();
		return false;
		
	}
	
	//fun��o que retorna a ra��o di�ria por esp�cie
	public String returnDailyRationBySpecie() {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return "Nenhum animal encontrado";
			
		}
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		manageSpecies.loadSpecies();
		
		String buffer = "";
		
		int animal_count = 0;
		
		for(Specie specie : manageSpecies.getSpecies()) {
			
			animal_count = 0;
			
			for(Animal animal : this.animals) {
				
				if(specie.getName().equals(animal.getSpecie()) && !animal.getState().equals("Adotado") && !animal.getState().equals("Falecido")) {
					
					animal_count++;
					
				}
				
			}
			
			buffer += "Quantidade di�ria de ra��o da esp�cie \"" + specie.getName() + "\": " + (specie.getDiaryRationKiloPerAnimal() * animal_count) + "\n\n";
			
		}
		
		this.clearAnimals();
		
		manageSpecies.clearSpecies();
		
		return buffer;
		
	}
	
	//fun��o que retorna a estimativa de custo di�rio de ra��o para todos os animais (ra��o mais barata)
	public String returnEstimateDailyCostAllAnimals() {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return "Nenhum animal encontrado";
			
		}
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		manageSpecies.loadSpecies();
		
		ManageRations manageRations = new ManageRations();
		
		manageRations.loadRations();
		
		ArrayList<Ration> rations = manageRations.getRations();
		
		float sum = 0;
		
		float lowest;
		
		int animal_count;
		
		for(Specie specie : manageSpecies.getSpecies()) {
			
			lowest = -1;
			
			animal_count = 0;
			
			for(int i = 0; i < rations.size(); i++) {
				
				if(specie.getName().equals(rations.get(i).getSpecie())) {
					
					if(lowest == -1) {
						
						lowest = rations.get(i).getPricePerKilo();
						
					}else {
						
						if(lowest > rations.get(i).getPricePerKilo()) {
							
							lowest = rations.get(i).getPricePerKilo();
							
						}
						
					}
					
				}
				
			}
			
			for(Animal animal : this.animals) {
				
				if(specie.getName().equals(animal.getSpecie())) {
					
					animal_count++;
					
				}
				
			}
			
			if(lowest != -1) {
				
				sum += lowest * animal_count;
				
			}
			
		}
		
		
		return "Estimativa de custo di�rio de alimenta��o de todos os animais (marca mais barata): " + sum + " �\n";
		
		
	}
	
	//fun��o que retorna a estimativa de custo di�rio de ra��o para todos os animais de uma esp�cie (ra��o mais barata)
	public String returnEstimateDailyCostBySpecie(String specie) {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return "Nenhum animal encontrado";
			
		}
		
		ManageRations manageRations = new ManageRations();
		
		manageRations.loadRations();
		
		ArrayList<Ration> rations = manageRations.getRations();
		
		float lowest = -1, sum = 0;
		
		int animal_count = 0;
		
		boolean found = false;
			
		for(int i = 0; i < rations.size(); i++) {
			
			if(rations.get(i).getSpecie().equals(specie)) {
				
				found = true;
				
				if(lowest == -1) {
					
					lowest = rations.get(i).getPricePerKilo();
					
				}else {
					
					if(lowest > rations.get(i).getPricePerKilo()) {
						
						lowest = rations.get(i).getPricePerKilo();
						
					}
					
				}
				
			}
			
		}
		
		if(!found) {
			
			return "Esp�cie n�o encontrada.\n";
			
		}
			
		for(Animal animal : this.animals) {
			
			if(animal.getSpecie().equals(specie)) {
				
				animal_count++;
				
			}
			
		}
		
		if(lowest != -1) {
			
			sum += lowest * animal_count;
			
		}
		
		return "Estimativa de custo di�rio de alimenta��o da esp�cie \"" + specie + "\" (marca mais barata): " + sum + " �\n";
		
	}
	
	//fun��o que declara todos os animais f�meas como esterilizados
	public int setAllFemalesSterilized() {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			this.clearAnimals();
			return 0;
			
		}
		
		int count = 0;
		
		
		for(int i = 0; i < this.animals.size(); i++) {
			
			if(!this.animals.get(i).isSterilized() && (this.animals.get(i).getGender().equals("F�mea") || this.animals.get(i).getGender().equals("f�mea"))) {
				
				this.animals.get(i).setSterilized(true);
				
				count++;
				
			}
			
		}
		
		if(count > 0) {
			
			if(this.saveAnimals()) {
				
				return count;
				
			}else {
				
				return 0;
				
			}
			
		}
		
		return count;
		
	}
	
	//fun��o que devolve os dados de todos os animais
	public String toString() {
			
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			this.clearAnimals();
			return "Nenhum animal encontrado.\n";
			
		}
		
		String buffer = "Animais\n\n";
		
		for(Animal animal : this.animals) {
			
			buffer += "Nome: " + animal.getName() + "\n"
					+ "Esp�cie: " + animal.getSpecie() + "\n"
					+ "G�nero: " + animal.getGender() + "\n"
					+ "Peso: " + animal.getWeight() + "\n"
					+ "Ra�a: " + animal.getBreed() + "\n"
					+ "Idade: " + animal.getAge() + "\n"
					+ "Data de chegada: " + animal.getArrivedDate() + "\n"
					+ "Observa��es\n" + animal.getObs() + "\n"
					+ "Estado: " + animal.getState() + "\n"
					+ "Esterelizado: " + (animal.isSterilized() ? "Sim" : "N�o") + "\n\n";
			
		}
		
		this.clearAnimals();
		return buffer;
		
	}
	
	//fun��o que devolve os dados de um animal
	public String toString(String name) {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			this.clearAnimals();
			return "Nenhum animal encontrado.\n";
			
		}
		
		String buffer = "";
		
		boolean found = false;
		
		for(Animal animal : this.animals) {
			
			if(animal.getName().equals(name)) {
				
				found = true;
				
				buffer += "Animal \"" + animal.getName() + "\":\n\n"
						+ "Esp�cie: " + animal.getSpecie() + "\n"
						+ "G�nero: " + animal.getGender() + "\n"
						+ "Peso: " + animal.getWeight() + "\n"
						+ "Ra�a: " + animal.getBreed() + "\n"
						+ "Idade: " + animal.getAge() + "\n"
						+ "Data de chegada: " + animal.getArrivedDate() + "\n"
						+ "Observa��es\n" + animal.getObs() + "\n"
						+ "Estado: " + animal.getState() + "\n"
						+ "Esterelizado: " + (animal.isSterilized() ? "Sim" : "N�o") + "\n\n";
				
				break;
				
			}
			
		}
		
		this.clearAnimals();
		
		if(found) {
			
			return buffer;
			
		}else {
			
			return "Nenhum animal foi encontrado com esse nome\n";
			
		}
		
	}
	

}
