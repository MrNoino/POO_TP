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
			
			//fecha a função
			return;
			
		}
		
		//inicializa a coleção de animais
		this.animals = new ArrayList<Animal>();
		
		try {
			
			//inicia um objeto de leitura de ficheiros de objetos
			ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(file));
			
			//le e adiciona a coleção de dentro do ficheiro para a coleção desta classe
			this.animals.addAll((ArrayList<Animal>) objectStream.readObject());
			
			//fecha o objeto de leitura
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
	
	//função para gravar os animais num ficheiro de objetos
	public boolean saveAnimals() {
		
		File file = new File(this.filename);
		
		//se nao existir animais
		if(this.animals == null || this.animals.size() == 0) {
			
			//fecha a função
			return false;
			
		}
		
		try {
			
			//cria um objeto de escrita de ficheiro de objetos
			ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(file));
			
			//escreve a coleção de animais
			objectStream.writeObject(this.animals);
			
			//fecha o objeto de escrita
			objectStream.close();
			
		
		} catch (FileNotFoundException e) {
			
			System.out.println("\nFicheiro não encontrado ao ler.\n");
			
			this.clearAnimals();
			return false;
			
		} catch (IOException e) {
			
			System.out.println("\nErro de entrada ao tentar escrever o ficheiro.\n");
			
			this.clearAnimals();
			return false;
			
		}finally {
			
			//liberta a coleção
			this.clearAnimals();
			
		}
		
		return true;
		
	}
	
	//função para adicionar animal
	public boolean addAnimal(Animal animal) {
			
		//carrega os animais
		this.loadAnimals();
		
		//se nao existir nenhum animal
		if(this.animals == null) {
			
			//iniciliza a coleção
			this.animals = new ArrayList<Animal>();
			
		}
		
		//adiciona o animal à coleção
		this.animals.add(animal);
			
		//se foi gravado com sucesso
		if(this.saveAnimals()) {
			
			ManageSpecies manageSpecies = new ManageSpecies();
			
			//aumenta a quantidade da espécie
			manageSpecies.changeQuantity("add", animal.getSpecie(), 1);
	
			//liberta a coleção da memória
			this.clearAnimals();
			
			return true;
			
			
		}else {
			
			//liberta a coleção da memória
			this.clearAnimals();
			
			return false;
			
		}
		
	}
	
	//função para editar um animal
	public boolean editAnimal(String name, Animal animal) {
		
		//carrega os animais	
		this.loadAnimals();
		
		//se nao existir nenhum animal
		if(this.animals == null || this.animals.size() == 0) {
			
			//fecha a função
			return false;
			
		}
		
		//percorre a coleção
		for(int i = 0; i < this.animals.size(); i++) {
			
			//se o animal é igual ao nome a procurar
			if(this.animals.get(i).getName().equals(name)) {
				
				//substitui o animal
				this.animals.set(i, animal);	
				
				//grava os animais
				return this.saveAnimals();
				
			}
			
		}
		
		//liberta a coleção da memória
		this.clearAnimals();
		return false;
		
	}
	
	//função para eliminar um animal
	public boolean deleteAnimal(String name) {
			
		//carrega os animais
		this.loadAnimals();
		
		//se nao existe animais
		if(this.animals == null || this.animals.size() == 0) {
			
			//fecha a função
			return false;
			
		}
		
		//percorre os animais
		for(int i = 0; i < this.animals.size(); i++) {
			
			//se o animal é igual ao nome a procurar
			if(this.animals.get(i).getName().equals(name)) {
				
				//obtem a espécie
				String specie = this.animals.get(i).getSpecie();
				
				//remove o animal
				this.animals.remove(i);
					
				//se foi gravado com sucesso
				if(this.saveAnimals()) {
					
					ManageSpecies manageSpecies = new ManageSpecies();
					
					//decrementa na quantidade da espécie
					manageSpecies.changeQuantity("remove", specie, 1);
					
					return true;
					
				}else {
					
					//liberta a coleção da memória
					this.clearAnimals();
					return false;
					
				}
				
			}
			
		}
		
		this.clearAnimals();
		return false;
		
	}
	
	//função que devolve os nomes dos animais de todas as espécies
	public String returnNamesByAllSpecies() {
			
		this.loadAnimals();
			
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return "Nenhum animal encontrado";
			
		}
		
		String buffer = "";
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		manageSpecies.loadSpecies();
	
		for(Specie specie : manageSpecies.getSpecies()) {
			
			buffer += "Animais da espécie \"" + specie.getName() + "\":\n\n";
			
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
	
	//função que devolve os nomes e idades de uma espécie especifica
	public String returnNamesAgesBySpecie(String specie) {
			
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return "Nenhum animal encontrado";
			
		}
		
		String buffer = "Animais da espécie \"" + specie + "\"\n\n";
		
		for(Animal animal : this.animals) {
			
			if(animal.getSpecie().equals(specie)) {
				
				buffer += "Nome: " + animal.getName() + "\n"
						+ "Idade: " + animal.getAge() + "\n\n";
				
			}
			
		}
		
		this.clearAnimals();
		
		return buffer;
		
	}
	
	//função que devolve nomes, idades e observações de animais de uma raça especifica
	public String returnNamesAgesObsByBreed(String breed) {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			return "Nenhum animal encontrado";
			
		}
		
		String buffer = "Animais da raça \"" + breed + "\"\n\n";
		
		for(Animal animal : this.animals) {
			
			if(animal.getBreed().equals(breed)) {
				
				buffer += "Nome: " + animal.getName() + "\n"
						+ "Idade: " + animal.getAge() + "\n"
						+ "Observações: " + animal.getObs() + "\n\n";
				
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
			
			buffer += "Dos animais da espécie \"" + specie.getName() + "\", " + count + " foram adotados.\n\n";
			
		}
		
		manageSpecies.clearSpecies();
		
		this.clearAnimals();
		
		return buffer;
		
	}
	
	//gera relatórios de animais por espécie
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
						
						System.out.println("Erro ao escrever o ficheiro para a espécie \"" + specie.getName() + "\"");
						
					}
				
			}
			
		}
		
		manageSpecies.clearSpecies();
		
		this.clearAnimals();
		
		return file_count;
		
	}
	
	//gera relatório de adoção
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
				
				buffer += "Animal: " + animal.getName() + "\nEspécie: " + animal.getSpecie() + "\nDono: " + animal.getObs() + "\n\n";
				
			}
			
		}
			
		if(!buffer.isBlank()) {
			
			try {
				
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("RelatorioAdocao.txt"));
				
				bufferedWriter.write(buffer);
				
				bufferedWriter.close();
				
				return true;
				
				
			} catch (IOException e) {
				
				System.out.println("Erro ao escrever o relatório de adoção.\n");
				
				return false;
				
			}
			
		}
		
		return false;
		
	}
	
	//gera realatórios por espécie de animais com raça não indefinida
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
						
						buffer += "Animal: " + animal.getName() + "\nIdade: " + animal.getAge() + "\nRaça: " + animal.getBreed() + "\n\n";
						
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
						
						System.out.println("Erro ao escrever o ficheiro para a espécie \"" + specie.getName() + "\"");
						
					}
				
			}
			
		}
		
		manageSpecies.clearSpecies();
		
		this.clearAnimals();
		
		return file_count;
		
	}
	
	//função que reporta um ato veterinário
	public boolean reportVetAct(String name, String report) {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			System.out.println("Nenhum animal encontrado");
			
			return false;
			
		}
		
		for(int i = 0; i < this.animals.size(); i++) {
			
			if(this.animals.get(i).getName().equals(name)) {
				
				this.animals.get(i).appendObs("\n" + report + "\nData: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "\n");
				
				String buffer = "Animal: " + this.animals.get(i).getName() + "\nActos Veterinários: " + report + "\n\n";
				
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
	
	//função que reporta uma adoção
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
	
	//função que reporta um óbito
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
	
	//função que retorna a ração diária por espécie
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
			
			buffer += "Quantidade diária de ração da espécie \"" + specie.getName() + "\": " + (specie.getDiaryRationKiloPerAnimal() * animal_count) + "\n\n";
			
		}
		
		this.clearAnimals();
		
		manageSpecies.clearSpecies();
		
		return buffer;
		
	}
	
	//função que retorna a estimativa de custo diário de ração para todos os animais (ração mais barata)
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
		
		
		return "Estimativa de custo diário de alimentação de todos os animais (marca mais barata): " + sum + " €\n";
		
		
	}
	
	//função que retorna a estimativa de custo diário de ração para todos os animais de uma espécie (ração mais barata)
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
			
			return "Espécie não encontrada.\n";
			
		}
			
		for(Animal animal : this.animals) {
			
			if(animal.getSpecie().equals(specie)) {
				
				animal_count++;
				
			}
			
		}
		
		if(lowest != -1) {
			
			sum += lowest * animal_count;
			
		}
		
		return "Estimativa de custo diário de alimentação da espécie \"" + specie + "\" (marca mais barata): " + sum + " €\n";
		
	}
	
	//função que declara todos os animais fêmeas como esterilizados
	public int setAllFemalesSterilized() {
		
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			this.clearAnimals();
			return 0;
			
		}
		
		int count = 0;
		
		
		for(int i = 0; i < this.animals.size(); i++) {
			
			if(!this.animals.get(i).isSterilized() && (this.animals.get(i).getGender().equals("Fêmea") || this.animals.get(i).getGender().equals("fêmea"))) {
				
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
	
	//função que devolve os dados de todos os animais
	public String toString() {
			
		this.loadAnimals();
		
		if(this.animals == null || this.animals.size() == 0) {
			
			this.clearAnimals();
			return "Nenhum animal encontrado.\n";
			
		}
		
		String buffer = "Animais\n\n";
		
		for(Animal animal : this.animals) {
			
			buffer += "Nome: " + animal.getName() + "\n"
					+ "Espécie: " + animal.getSpecie() + "\n"
					+ "Gênero: " + animal.getGender() + "\n"
					+ "Peso: " + animal.getWeight() + "\n"
					+ "Raça: " + animal.getBreed() + "\n"
					+ "Idade: " + animal.getAge() + "\n"
					+ "Data de chegada: " + animal.getArrivedDate() + "\n"
					+ "Observações\n" + animal.getObs() + "\n"
					+ "Estado: " + animal.getState() + "\n"
					+ "Esterelizado: " + (animal.isSterilized() ? "Sim" : "Não") + "\n\n";
			
		}
		
		this.clearAnimals();
		return buffer;
		
	}
	
	//função que devolve os dados de um animal
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
						+ "Espécie: " + animal.getSpecie() + "\n"
						+ "Gênero: " + animal.getGender() + "\n"
						+ "Peso: " + animal.getWeight() + "\n"
						+ "Raça: " + animal.getBreed() + "\n"
						+ "Idade: " + animal.getAge() + "\n"
						+ "Data de chegada: " + animal.getArrivedDate() + "\n"
						+ "Observações\n" + animal.getObs() + "\n"
						+ "Estado: " + animal.getState() + "\n"
						+ "Esterelizado: " + (animal.isSterilized() ? "Sim" : "Não") + "\n\n";
				
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
