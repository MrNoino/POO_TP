import java.util.InputMismatchException;
import java.util.Scanner;

//classe principal
public class Main {
	
	//scanner par obter inputs
	private static Scanner keyboard;

	//função principal
	public static void main(String[] args) {

		//declara o scanner
		keyboard = new Scanner(System.in);
		
		//variavel para as opções do menu
		int option;
		
		do {
			
			//recebe a opção escolhida
			option = scanInteger("Menu - Abrigo de Animais\n\n"
					+ "1. Visualizar\n"
					+ "2. Adicionar\n"
					+ "3. Atualizar\n"
					+ "4. Eliminar\n"
					+ "5. Operações\n"
					+ "6. Relatórios\n"
					+ "0. Sair\n"
					+ "Opção: ");
			
			switch(option) {
			
				case 1:
					
					//chama a função com o sub-menu visualizar
					view();
					
					break;
					
				case 2:
					
					//chama a função com o sub-menu adicionar
					add();
					
					break;
					
				case 3:
					
					//chama a função com o sub-menu atualizar
					update();
					break;
					
				case 4:
					
					//chama a função com o sub-menu eliminar
					delete();
					break;
					
				case 5: 
					
					//chama a função com o sub-menu operações
					operations();
					break;
					
				case 6:
					
					//chama a função com o sub-menu relatórios
					reports();
					break;
					
				case 0:
					
					System.out.println("Programa encerrado.");
					break;
					
				default:
					
					//imprime mensagem caso a opção nao seja valida
					System.out.println("\nOpção inválida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
		//fecha o scanner
		keyboard.close();
		
	}
	
	//função sub-menu visualizar
	private static void view() {
		
		//variavel para as opções
		int option;
		
		//instanceia objetos para manipular animals, espécies e rações
		ManageAnimals manageAnimals = new ManageAnimals();
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		ManageRations manageRations = new ManageRations();
		
		do {
			
			//obtem a opção do menu
			option = scanInteger("   Menu - Visualizar\n\n"
					+ "1. Nome dos animais por espécie\n"
					+ "2. Quantidade de animais de uma espécie\n"
					+ "3. Nome e a idade dos animais de uma espécie\n"
					+ "4. Nome, idade e as observações dos animais de uma raça\n"
					+ "5. Quantidade de animais adotados por espécie\n"
					+ "6. Quantidade diária de ração por espécie\n"
					+ "7. Estimativa do custo diário da alimentação de todos os animais (considerando as rações mais baratas)\n"
					+ "8. Estimativa do custo diário da alimentação de uma espécie (considerando as rações mais baratas)\n"
					+ "9. Dados completos de um animal\n"
					+ "10. Ver Animais\n"
					+ "11. Ver Espécies\n"
					+ "12: Ver Rações\n"
					+ "0. Sair\n"
					+ "Opção: ");
			
			switch(option) {
			
				case 1:
					
					//imprime os nomes dos animais por espécie
					System.out.println(manageAnimals.returnNamesByAllSpecies());
					
					break;
					
				case 2:
					
					//pede a espécie pretendida
					Specie specie = manageSpecies.searchSpecie(scanString("Nome da espécie: "));
					
					//se nao encontrou a espécie pretendida
					if(specie == null) {
						
						//imprime uma mensagem de erro
						System.out.println("Não foi possível processar a informação");
						
					//senao
					}else {
						
						//imrpime a quantidade da espécie no abrigo
						System.out.println("Quantidade da espécie \"" + specie.getName() + "\" no abrigo: " + specie.getQuantity());
						
					}
					
					break;
					
				case 3:
					
					//pede a espécie e imprime os nomes e idades dos animais da espécie fornecida
					System.out.println(manageAnimals.returnNamesAgesBySpecie(scanString("Espécie a pesquisar: ")));
					
					break;
					
				case 4:
					
					//pede a raça e imprime os nomes, as idades e obeservações da raça fornecida
					System.out.println(manageAnimals.returnNamesAgesObsByBreed(scanString("Raça a pesquisar: ")));
					
					break;
					
				case 5:
	
					//imprime a quantidade de animais adotados o abrigo
					System.out.println(manageAnimals.returnQuantityAdoptedByAllSpecie());
					
					break;
					
				case 6:
					
					//imrpime a quantidade de ração diária para cada espécie
					System.out.println(manageAnimals.returnDailyRationBySpecie());
					
					break;
					
				case 7:
					
					//imprime a estimativa de custo da ração mais barata para todos os animais
					System.out.println(manageAnimals.returnEstimateDailyCostAllAnimals());
					
					break;
					
				case 8:
					
					//pede a espécie e imprime a estimativa de custo da ração mais barata para a espécie pretendida
					System.out.println(manageAnimals.returnEstimateDailyCostBySpecie(scanString("Espécie pretendida: ")));
					
					break;
					
				case 9:
					
					//pede o nome do animal e imprime os dados completos desse animal
					System.out.println(manageAnimals.toString(scanString("Animal a pesquisar: ")));
					
					break;
					
				case 10:
					
					//imprime todos os animais
					System.out.println(manageAnimals.toString());
					
					break;
					
				case 11:
					
					//imprime todas as espécies
					System.out.println(manageSpecies.toString());
					
					break;
					
				case 12:
					
					//imprime todas as rações
					System.out.println(manageRations.toString());
					
					break;
	
				case 0:
					
					System.out.println("\nA voltar ao menu principal...");
					break;
					
				default:
					
					System.out.println("\nOpção inválida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
	}
	
	//função sub-menu adicionar
	private static void add() {
		
		int option;
		
		String input;
		
		boolean found = false;
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		ManageRations manageRations = new ManageRations();
		
		do {
			
			//obtem a opção do menu
			option = scanInteger("   Menu - Adicionar\n\n"
					+ "1. Adicionar Animal\n"
					+ "2. Adicionar Espécie\n"
					+ "3. Adicionar Ração\n"
					+ "0. Sair\n"
					+ "Opção: ");
			
			switch(option) {
			
				case 1:
					
					ManageAnimals manageAnimals = new ManageAnimals();
					
					found = false;
					
					do {
						
						//pede a espécie do animal
						input = scanString("Espécie do animal: ");
							
							//se a espécie fornecida não for encontrada
							if(manageSpecies.searchSpecie(input) == null) {
								
								//imprime mensagem de erro
								System.out.println("Espécie não econtrada, tente novamente.\n");
								
							//senao
							}else {
								
								//foi econtrado
								found = true;
								
							}
						
					//enquanto nao foi encontrado
					}while(!found);
					
					//se o animal foi inserido com sucesso
					if(manageAnimals.addAnimal(new Animal(scanString("Nome do animal: "), 
															input,
															scanString("Gênero do animal: "), 
															scanFloat("Peso do animal: "), 
															scanString("Raça do animal: "), 
															scanInteger("Idade do animal: "), 
															scanString("Data de chegada do animal: "), 
															scanString("Observações: "), 
															scanString("Estado: "),
															scanBoolean("Está esterelizado: ")))) {
						
						//imprime mensagem de sucesso
						System.out.println("Inserido com sucesso.");
						
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("\nErro ao inserir.");
						
					}
					
					break;
					
				case 2:
					
					//se a espécie foi inserida com sucesso
					if(manageSpecies.addSpecie(new Specie(scanString("Nome da espécie: "), 
							scanFloat("Ração diária de um animal da espécie: "),
							scanString("Observações: "), 
							scanInteger("Capacidade máxima do abrigo para a espécie: ")))) {

						//imprime mensagem de sucesso
						System.out.println("Inserido com sucesso.");
					
					//senao
					}else {
					
						//imprime mensagem de erro
						System.out.println("\nErro ao inserir.");
					
					}
					
					break;
					
				case 3:
					
					found = false;
					
					do {
						
						//pede a espécie do animal
						input = scanString("Espécie do animal associado à ração: ");
							
						//senao encontrou a espécie fornecida
						if(manageSpecies.searchSpecie(input) == null) {
							
							//imprime mensagem de erro
							System.out.println("Espécie não econtrada, tente novamente.\n");
						
						//senao	
						}else {
							
							//foi encontrado
							found = true;
							
						}
						
					//enquanto nao foi encontrado
					}while(!found);
					
					//se a ração foi inserida com sucesso
					if(manageRations.addRation(new Ration(scanString("Marca da ração: "), new int[] {scanInteger("Idade mínima: "), scanInteger("Idade máxima: ")}, scanFloat("Preço por kilograma: "), input))) {

						//imprime mensagem de sucesso
						System.out.println("Inserido com sucesso.");
					
					//senao
					}else {
					
						//imprime mensagem de erro
						System.out.println("\nErro ao inserir.");
					
					}
					
					break;
	
				case 0:
					
					System.out.println("\nA voltar ao menu principal...");
					break;
					
				default:
					
					System.out.println("\nOpção inválida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
	}
	
	//função sub-menu atualizar
	private static void update() {
		
		int option;
		
		String input;
		
		boolean found = false;
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		ManageRations manageRations = new ManageRations();
		
		do {
			
			//obtem a opção do menu
			option = scanInteger("   Menu - Atualizar\n\n"
					+ "1. Atualizar Animal\n"
					+ "2. Atualizar Espécie\n"
					+ "3. Atualizar Ração\n"
					+ "0. Sair\n"
					+ "Opção: ");
			
			switch(option) {
			
				case 1:
					
					ManageAnimals manageAnimals = new ManageAnimals();
					
					found = false;
					
					do {
						
						//pede a nova espécie
						input = scanString("Nova espécie do animal: ");
							
						//se nao econtrou a espécie
						if(manageSpecies.searchSpecie(input) == null) {
							
							//imprime mensagem de erro
							System.out.println("Espécie não econtrada, tente novamente.\n");
						
						//senao	
						}else {
							
							//foi encontrado
							found = true;
							
						}
						
					//enquanto nao foi encontrado
					}while(!found);
					
					//se o animal foi editado com sucesso
					if(manageAnimals.editAnimal(scanString("Nome do animal a atualizar: "), new Animal(scanString("Novo nome do animal: "), 
															input,
															scanString("Novo gênero do animal: "), 
															scanFloat("Novo peso do animal: "), 
															scanString("Nova raça do animal: "), 
															scanInteger("Nova idade do animal: "), 
															scanString("Nova data de chegada do animal: "), 
															scanString("Observações: "), 
															scanString("Novo estado: "),
															scanBoolean("Está esterelizado: ")))) {
						
						//imprime mensagem de sucesso
						System.out.println("Atualizado com sucesso.");
					
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("\nErro ao atualizar.");
						
					}
					
					break;
					
				case 2:
					
					//se a espécie foi editada com sucesso
					if(manageSpecies.editSpecie(scanString("Nome da espécie a atualizar: "), new Specie(scanString("Novo Nome: "), 
							scanFloat("Ração diária de um animal da espécie atualizado: "),
							scanString("Observações: "), 
							scanInteger("Nova capacidade máxima do abrigo para a espécie: ")))) {

						//imprime mensagem de sucesso
						System.out.println("Atualizado com sucesso.");
					
					//senao
					}else {
					
						//imprime mensagem de erro
						System.out.println("\nErro ao atualizar.");
					
					}
					
					break;
					
				case 3:
					
					found = false;
					
					do {
						
						//pede a nova espécie
						input = scanString("Nova espécie do animal associado à ração: ");
							
						//se nao encontrou a espécie 
						if(manageSpecies.searchSpecie(input) == null) {
							
							//imprime mensagem de erro
							System.out.println("Espécie não econtrada, tente novamente.\n");
							
						//senao
						}else {
							
							//foi encontrado
							found = true;
							
						}
						
					//enquanto nao foi encontrado
					}while(!found);
					
					//se a ração foi editada com sucesso
					if(manageRations.editRation(scanString("Marca da ração a ser atualizada: "), new Ration(scanString("Nova marca da ração: "), new int[] {scanInteger("Nova idade mínima: "), scanInteger("Nova idade máxima: ")}, scanFloat("Novo preço por kilograma: "), input))) {
						
						//imprime mensagem de sucesso
						System.out.println("Atualizado com sucesso.");
					
					//senao
					}else {
					
						//imprime mensagem de erro
						System.out.println("\nErro ao atualizar.");
					
					}
					
					break;
	
				case 0:
					
					System.out.println("\nA voltar ao menu principal...");
					break;
					
				default:
					
					System.out.println("\nOpção inválida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
	}
	
	//funcao sub-menu eliminar
	private static void delete() {
		
		int option;
		
		do {
			
			//obtem a opção do menu
			option = scanInteger("   Menu - Eliminar\n\n"
					+ "1. Eliminar Animal\n"
					+ "2. Eliminar Espécie\n"
					+ "3. Eliminar Ração\n"
					+ "0. Sair\n"
					+ "Opção: ");
			
			switch(option) {
			
				case 1:
					
					ManageAnimals manageAnimals = new ManageAnimals();
					
					//se o animal foi eliminado com sucesso
					if(manageAnimals.deleteAnimal(scanString("Nome do animal a eliminar: "))) {
						
						//imprime a mensagem de sucesso
						System.out.println("Eliminado com sucesso.");
						
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("Erro ao eliminar.");
						
					}
					
					break;
					
				case 2:
					
					ManageSpecies manageSpecies = new ManageSpecies();
					
					//se a espécie foi eliminada com sucesso
					if(manageSpecies.deleteSpecie(scanString("Nome da espécie a eliminar: "))) {
						
						//imprime a mensagem de sucesso
						System.out.println("Eliminado com sucesso.");
						
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("Erro ao eliminar.");
						
					}
					
					break;
					
				case 3:
					
					ManageRations manageRations = new ManageRations();
					
					//se a ração foi eliminada com sucesso
					if(manageRations.deleteRation(scanString("Marca da ração a eliminar: "))) {
						
						//imprime a mensagem de sucesso
						System.out.println("Eliminado com sucesso.\n");
						
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("Erro ao eliminar.\n");
						
					}
					
					break;
	
				case 0:
					
					System.out.println("\nA voltar ao menu principal...");
					break;
					
				default:
					
					System.out.println("\nOpção inválida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
	}
	
	//funcao sub-menu operações
	private static void operations() {
		
		int option;
		
		ManageAnimals manageAnimals = new ManageAnimals();
		
		do {
			
			//obtem a opção do menu
			option = scanInteger("	Menu - Operações\n\n"
								+ "1. Reportar ato veterinário\n"
								+ "2. Reportar adoção\n"
								+ "3. Reportar óbito\n"
								+ "4. Declarar esterilização de todos os animais fêmeas\n"
								+ "0. Sair\n"
								+ "Opção: ");
			
			switch(option) {
			
				case 1:
					
					//se o ato veterinario foi reportado com sucesso
					if(manageAnimals.reportVetAct(scanString("Nome do animal: "), scanString("Relatório do acto: "))) {
						
						//imprime mensagem de sucesso
						System.out.println("Acto veterinário reportado com sucesso.\n");
						
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("Acto veterinário não reportado.\n");
						
					}
					
					break;
			
				case 2:
					
					//se a adoção foi reportada com sucesso
					if(manageAnimals.reportAdotion(scanString("Nome do animal: "), scanString("Novo dono: "))) {
						
						//imprime mensagem de sucesso
						System.out.println("Adoção reportada com sucesso.\n");
						
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("Adoção não reportada.\n");
						
					}
					
					break;
					
				case 3:
					
					//se o óbito foi reportado com sucesso
					if(manageAnimals.reportDeath(scanString("Nome do animal: "), scanString("Data de falecimento: "))) {
					
						//imprime mensagem de sucesso
						System.out.println("Óbito reportado com sucesso.\n");
					
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("Óbito não reportado.\n");
						
					}
					
					break;
					
				case 4:
					
					//delcara todos os animais fêmeas como esterilizadas e imprime a quantidade de animais que esta ação afetou
					System.out.println("Quantidade de animais fêmea declaradas como esterilizadas: " + manageAnimals.setAllFemalesSterilized() + "\n");
					
					break;
					
				case 0:
					
					System.out.println("\nA voltar ao menu principal...");
					break;
					
				default:
					
					System.out.println("\nOpção inválida, tente novamente.");
					break;
			
			}
			
		}while(option != 0);
		
	}
	
	//função sub-menu relatórios
	private static void reports() {
		
		int option;
		
		ManageAnimals manageAnimals = new ManageAnimals();
		
		do {
			
			//obtem a opcao do menu
			option = scanInteger("   Menu - Relatórios\n\n"
					+ "1. Animais (nome, peso e idade) de cada espécie existentes no abrigo\n"
					+ "2. Animais (nome, idade e raça) que não têm raça indefinida, para cada espécie\n"
					+ "3. Animais (nome e espécie) adoptados e o nome do seu novo dono\n"
					+ "0. Sair\n"
					+ "Opção: ");
			
			switch(option) {
			
				case 1:
					
					//gera relatórios de animais por espécie e imprime a quantidade de ficheiros gerados
					System.out.println("Número de relatórios gerados: " + manageAnimals.generateReportsPerSpecie());
					
					break;
					
				case 2:
					
					//gera relatórios de animais sem raça indefinida por espécie e imprime a quantidade de ficheiros gerados
					System.out.println("Número de relatórios gerados: " + manageAnimals.generateBreedAnimalsPerSpecieReport());
					
					break;
					
				case 3:
					
					//se foi gerado o relatório de adoções
					if(manageAnimals.generateAdoptionReport()) {
						
						//imprime mensagem de sucesso
						System.out.println("Relatório gerado com sucesso.");
						
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("Relatório não foi gerado.");
						
					}
					
					break;
	
				case 0:
					
					System.out.println("\nA voltar ao menu principal...");
					break;
					
				default:
					
					System.out.println("\nOpção inválida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
	}
	
	//função para obter do input um inteiro
	private static int scanInteger(String msg) {
		
		boolean valid;
		
		int integer = 0;
		
		do {
			
			System.out.print(msg);
			
			try {
				
				integer = keyboard.nextInt();
				valid = true;
				
				
			}catch(InputMismatchException exception) {
				
				System.out.println("\nEntrada inválida, tente novamente.");
				keyboard.next();
				valid = false;
				
			}
			
			System.out.println();
			
		}while(!valid);
		
		keyboard.nextLine();
		
		return integer;
		
	}
	
	//função para obter do input um float
	private static float scanFloat(String msg) {
		
		boolean valid;
		
		float f = 0;
		
		do {
			
			System.out.print(msg);
			
			try {
				
				f = keyboard.nextFloat();
				valid = true;
				
				
			}catch(InputMismatchException exception) {
				
				System.out.println("\nEntrada inválida, tente novamente.");
				keyboard.next();
				valid = false;
				
			}
			
			System.out.println();
			
		}while(!valid);
		
		keyboard.nextLine();
		
		return f;
		
	}
	
	//função para obter do input uma string
	private static String scanString(String msg) {
		
		boolean valid;
		
		String str = "";
		
		do {
			
			System.out.print(msg);
			
			try {
				
				str = keyboard.nextLine();
				valid = true;
				
				
			}catch(InputMismatchException exception) {
				
				System.out.println("\nEntrada inválida, tente novamente.");
				keyboard.next();
				valid = false;
				
			}
			
			System.out.println();
			
		}while(!valid);
		
		return str;
		
	}
	
	//função para obter do input um booleano
	private static boolean scanBoolean(String msg) {
		
		boolean valid;
		
		String str = "";
		
		do {
			
			System.out.print(msg);
			
			try {
				
				str = keyboard.nextLine();
				
				if(str.equals("Sim") || str.equals("sim") || str.equals("Não") || str.equals("não")) {
					
					valid = true;
					
				}else {
					
					valid = false;
					
				}
				
				
				
			}catch(InputMismatchException exception) {
				
				System.out.println("\nEntrada inválida, tente novamente.");
				keyboard.next();
				valid = false;
				
			}
			
			System.out.println();
			
		}while(!valid);
		
		if(str.equals("Sim") || str.equals("sim")) {
			
			return true;
			
		}else {
			
			return false;
			
		}
		
	}

}
