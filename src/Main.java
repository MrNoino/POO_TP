import java.util.InputMismatchException;
import java.util.Scanner;

//classe principal
public class Main {
	
	//scanner par obter inputs
	private static Scanner keyboard;

	//fun��o principal
	public static void main(String[] args) {

		//declara o scanner
		keyboard = new Scanner(System.in);
		
		//variavel para as op��es do menu
		int option;
		
		do {
			
			//recebe a op��o escolhida
			option = scanInteger("Menu - Abrigo de Animais\n\n"
					+ "1. Visualizar\n"
					+ "2. Adicionar\n"
					+ "3. Atualizar\n"
					+ "4. Eliminar\n"
					+ "5. Opera��es\n"
					+ "6. Relat�rios\n"
					+ "0. Sair\n"
					+ "Op��o: ");
			
			switch(option) {
			
				case 1:
					
					//chama a fun��o com o sub-menu visualizar
					view();
					
					break;
					
				case 2:
					
					//chama a fun��o com o sub-menu adicionar
					add();
					
					break;
					
				case 3:
					
					//chama a fun��o com o sub-menu atualizar
					update();
					break;
					
				case 4:
					
					//chama a fun��o com o sub-menu eliminar
					delete();
					break;
					
				case 5: 
					
					//chama a fun��o com o sub-menu opera��es
					operations();
					break;
					
				case 6:
					
					//chama a fun��o com o sub-menu relat�rios
					reports();
					break;
					
				case 0:
					
					System.out.println("Programa encerrado.");
					break;
					
				default:
					
					//imprime mensagem caso a op��o nao seja valida
					System.out.println("\nOp��o inv�lida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
		//fecha o scanner
		keyboard.close();
		
	}
	
	//fun��o sub-menu visualizar
	private static void view() {
		
		//variavel para as op��es
		int option;
		
		//instanceia objetos para manipular animals, esp�cies e ra��es
		ManageAnimals manageAnimals = new ManageAnimals();
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		ManageRations manageRations = new ManageRations();
		
		do {
			
			//obtem a op��o do menu
			option = scanInteger("   Menu - Visualizar\n\n"
					+ "1. Nome dos animais por esp�cie\n"
					+ "2. Quantidade de animais de uma esp�cie\n"
					+ "3. Nome e a idade dos animais de uma esp�cie\n"
					+ "4. Nome, idade e as observa��es dos animais de uma ra�a\n"
					+ "5. Quantidade de animais adotados por esp�cie\n"
					+ "6. Quantidade di�ria de ra��o por esp�cie\n"
					+ "7. Estimativa do custo di�rio da alimenta��o de todos os animais (considerando as ra��es mais baratas)\n"
					+ "8. Estimativa do custo di�rio da alimenta��o de uma esp�cie (considerando as ra��es mais baratas)\n"
					+ "9. Dados completos de um animal\n"
					+ "10. Ver Animais\n"
					+ "11. Ver Esp�cies\n"
					+ "12: Ver Ra��es\n"
					+ "0. Sair\n"
					+ "Op��o: ");
			
			switch(option) {
			
				case 1:
					
					//imprime os nomes dos animais por esp�cie
					System.out.println(manageAnimals.returnNamesByAllSpecies());
					
					break;
					
				case 2:
					
					//pede a esp�cie pretendida
					Specie specie = manageSpecies.searchSpecie(scanString("Nome da esp�cie: "));
					
					//se nao encontrou a esp�cie pretendida
					if(specie == null) {
						
						//imprime uma mensagem de erro
						System.out.println("N�o foi poss�vel processar a informa��o");
						
					//senao
					}else {
						
						//imrpime a quantidade da esp�cie no abrigo
						System.out.println("Quantidade da esp�cie \"" + specie.getName() + "\" no abrigo: " + specie.getQuantity());
						
					}
					
					break;
					
				case 3:
					
					//pede a esp�cie e imprime os nomes e idades dos animais da esp�cie fornecida
					System.out.println(manageAnimals.returnNamesAgesBySpecie(scanString("Esp�cie a pesquisar: ")));
					
					break;
					
				case 4:
					
					//pede a ra�a e imprime os nomes, as idades e obeserva��es da ra�a fornecida
					System.out.println(manageAnimals.returnNamesAgesObsByBreed(scanString("Ra�a a pesquisar: ")));
					
					break;
					
				case 5:
	
					//imprime a quantidade de animais adotados o abrigo
					System.out.println(manageAnimals.returnQuantityAdoptedByAllSpecie());
					
					break;
					
				case 6:
					
					//imrpime a quantidade de ra��o di�ria para cada esp�cie
					System.out.println(manageAnimals.returnDailyRationBySpecie());
					
					break;
					
				case 7:
					
					//imprime a estimativa de custo da ra��o mais barata para todos os animais
					System.out.println(manageAnimals.returnEstimateDailyCostAllAnimals());
					
					break;
					
				case 8:
					
					//pede a esp�cie e imprime a estimativa de custo da ra��o mais barata para a esp�cie pretendida
					System.out.println(manageAnimals.returnEstimateDailyCostBySpecie(scanString("Esp�cie pretendida: ")));
					
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
					
					//imprime todas as esp�cies
					System.out.println(manageSpecies.toString());
					
					break;
					
				case 12:
					
					//imprime todas as ra��es
					System.out.println(manageRations.toString());
					
					break;
	
				case 0:
					
					System.out.println("\nA voltar ao menu principal...");
					break;
					
				default:
					
					System.out.println("\nOp��o inv�lida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
	}
	
	//fun��o sub-menu adicionar
	private static void add() {
		
		int option;
		
		String input;
		
		boolean found = false;
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		ManageRations manageRations = new ManageRations();
		
		do {
			
			//obtem a op��o do menu
			option = scanInteger("   Menu - Adicionar\n\n"
					+ "1. Adicionar Animal\n"
					+ "2. Adicionar Esp�cie\n"
					+ "3. Adicionar Ra��o\n"
					+ "0. Sair\n"
					+ "Op��o: ");
			
			switch(option) {
			
				case 1:
					
					ManageAnimals manageAnimals = new ManageAnimals();
					
					found = false;
					
					do {
						
						//pede a esp�cie do animal
						input = scanString("Esp�cie do animal: ");
							
							//se a esp�cie fornecida n�o for encontrada
							if(manageSpecies.searchSpecie(input) == null) {
								
								//imprime mensagem de erro
								System.out.println("Esp�cie n�o econtrada, tente novamente.\n");
								
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
															scanString("G�nero do animal: "), 
															scanFloat("Peso do animal: "), 
															scanString("Ra�a do animal: "), 
															scanInteger("Idade do animal: "), 
															scanString("Data de chegada do animal: "), 
															scanString("Observa��es: "), 
															scanString("Estado: "),
															scanBoolean("Est� esterelizado: ")))) {
						
						//imprime mensagem de sucesso
						System.out.println("Inserido com sucesso.");
						
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("\nErro ao inserir.");
						
					}
					
					break;
					
				case 2:
					
					//se a esp�cie foi inserida com sucesso
					if(manageSpecies.addSpecie(new Specie(scanString("Nome da esp�cie: "), 
							scanFloat("Ra��o di�ria de um animal da esp�cie: "),
							scanString("Observa��es: "), 
							scanInteger("Capacidade m�xima do abrigo para a esp�cie: ")))) {

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
						
						//pede a esp�cie do animal
						input = scanString("Esp�cie do animal associado � ra��o: ");
							
						//senao encontrou a esp�cie fornecida
						if(manageSpecies.searchSpecie(input) == null) {
							
							//imprime mensagem de erro
							System.out.println("Esp�cie n�o econtrada, tente novamente.\n");
						
						//senao	
						}else {
							
							//foi encontrado
							found = true;
							
						}
						
					//enquanto nao foi encontrado
					}while(!found);
					
					//se a ra��o foi inserida com sucesso
					if(manageRations.addRation(new Ration(scanString("Marca da ra��o: "), new int[] {scanInteger("Idade m�nima: "), scanInteger("Idade m�xima: ")}, scanFloat("Pre�o por kilograma: "), input))) {

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
					
					System.out.println("\nOp��o inv�lida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
	}
	
	//fun��o sub-menu atualizar
	private static void update() {
		
		int option;
		
		String input;
		
		boolean found = false;
		
		ManageSpecies manageSpecies = new ManageSpecies();
		
		ManageRations manageRations = new ManageRations();
		
		do {
			
			//obtem a op��o do menu
			option = scanInteger("   Menu - Atualizar\n\n"
					+ "1. Atualizar Animal\n"
					+ "2. Atualizar Esp�cie\n"
					+ "3. Atualizar Ra��o\n"
					+ "0. Sair\n"
					+ "Op��o: ");
			
			switch(option) {
			
				case 1:
					
					ManageAnimals manageAnimals = new ManageAnimals();
					
					found = false;
					
					do {
						
						//pede a nova esp�cie
						input = scanString("Nova esp�cie do animal: ");
							
						//se nao econtrou a esp�cie
						if(manageSpecies.searchSpecie(input) == null) {
							
							//imprime mensagem de erro
							System.out.println("Esp�cie n�o econtrada, tente novamente.\n");
						
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
															scanString("Novo g�nero do animal: "), 
															scanFloat("Novo peso do animal: "), 
															scanString("Nova ra�a do animal: "), 
															scanInteger("Nova idade do animal: "), 
															scanString("Nova data de chegada do animal: "), 
															scanString("Observa��es: "), 
															scanString("Novo estado: "),
															scanBoolean("Est� esterelizado: ")))) {
						
						//imprime mensagem de sucesso
						System.out.println("Atualizado com sucesso.");
					
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("\nErro ao atualizar.");
						
					}
					
					break;
					
				case 2:
					
					//se a esp�cie foi editada com sucesso
					if(manageSpecies.editSpecie(scanString("Nome da esp�cie a atualizar: "), new Specie(scanString("Novo Nome: "), 
							scanFloat("Ra��o di�ria de um animal da esp�cie atualizado: "),
							scanString("Observa��es: "), 
							scanInteger("Nova capacidade m�xima do abrigo para a esp�cie: ")))) {

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
						
						//pede a nova esp�cie
						input = scanString("Nova esp�cie do animal associado � ra��o: ");
							
						//se nao encontrou a esp�cie 
						if(manageSpecies.searchSpecie(input) == null) {
							
							//imprime mensagem de erro
							System.out.println("Esp�cie n�o econtrada, tente novamente.\n");
							
						//senao
						}else {
							
							//foi encontrado
							found = true;
							
						}
						
					//enquanto nao foi encontrado
					}while(!found);
					
					//se a ra��o foi editada com sucesso
					if(manageRations.editRation(scanString("Marca da ra��o a ser atualizada: "), new Ration(scanString("Nova marca da ra��o: "), new int[] {scanInteger("Nova idade m�nima: "), scanInteger("Nova idade m�xima: ")}, scanFloat("Novo pre�o por kilograma: "), input))) {
						
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
					
					System.out.println("\nOp��o inv�lida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
	}
	
	//funcao sub-menu eliminar
	private static void delete() {
		
		int option;
		
		do {
			
			//obtem a op��o do menu
			option = scanInteger("   Menu - Eliminar\n\n"
					+ "1. Eliminar Animal\n"
					+ "2. Eliminar Esp�cie\n"
					+ "3. Eliminar Ra��o\n"
					+ "0. Sair\n"
					+ "Op��o: ");
			
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
					
					//se a esp�cie foi eliminada com sucesso
					if(manageSpecies.deleteSpecie(scanString("Nome da esp�cie a eliminar: "))) {
						
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
					
					//se a ra��o foi eliminada com sucesso
					if(manageRations.deleteRation(scanString("Marca da ra��o a eliminar: "))) {
						
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
					
					System.out.println("\nOp��o inv�lida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
	}
	
	//funcao sub-menu opera��es
	private static void operations() {
		
		int option;
		
		ManageAnimals manageAnimals = new ManageAnimals();
		
		do {
			
			//obtem a op��o do menu
			option = scanInteger("	Menu - Opera��es\n\n"
								+ "1. Reportar ato veterin�rio\n"
								+ "2. Reportar ado��o\n"
								+ "3. Reportar �bito\n"
								+ "4. Declarar esteriliza��o de todos os animais f�meas\n"
								+ "0. Sair\n"
								+ "Op��o: ");
			
			switch(option) {
			
				case 1:
					
					//se o ato veterinario foi reportado com sucesso
					if(manageAnimals.reportVetAct(scanString("Nome do animal: "), scanString("Relat�rio do acto: "))) {
						
						//imprime mensagem de sucesso
						System.out.println("Acto veterin�rio reportado com sucesso.\n");
						
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("Acto veterin�rio n�o reportado.\n");
						
					}
					
					break;
			
				case 2:
					
					//se a ado��o foi reportada com sucesso
					if(manageAnimals.reportAdotion(scanString("Nome do animal: "), scanString("Novo dono: "))) {
						
						//imprime mensagem de sucesso
						System.out.println("Ado��o reportada com sucesso.\n");
						
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("Ado��o n�o reportada.\n");
						
					}
					
					break;
					
				case 3:
					
					//se o �bito foi reportado com sucesso
					if(manageAnimals.reportDeath(scanString("Nome do animal: "), scanString("Data de falecimento: "))) {
					
						//imprime mensagem de sucesso
						System.out.println("�bito reportado com sucesso.\n");
					
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("�bito n�o reportado.\n");
						
					}
					
					break;
					
				case 4:
					
					//delcara todos os animais f�meas como esterilizadas e imprime a quantidade de animais que esta a��o afetou
					System.out.println("Quantidade de animais f�mea declaradas como esterilizadas: " + manageAnimals.setAllFemalesSterilized() + "\n");
					
					break;
					
				case 0:
					
					System.out.println("\nA voltar ao menu principal...");
					break;
					
				default:
					
					System.out.println("\nOp��o inv�lida, tente novamente.");
					break;
			
			}
			
		}while(option != 0);
		
	}
	
	//fun��o sub-menu relat�rios
	private static void reports() {
		
		int option;
		
		ManageAnimals manageAnimals = new ManageAnimals();
		
		do {
			
			//obtem a opcao do menu
			option = scanInteger("   Menu - Relat�rios\n\n"
					+ "1. Animais (nome, peso e idade) de cada esp�cie existentes no abrigo\n"
					+ "2. Animais (nome, idade e ra�a) que n�o t�m ra�a indefinida, para cada esp�cie\n"
					+ "3. Animais (nome e esp�cie) adoptados e o nome do seu novo dono\n"
					+ "0. Sair\n"
					+ "Op��o: ");
			
			switch(option) {
			
				case 1:
					
					//gera relat�rios de animais por esp�cie e imprime a quantidade de ficheiros gerados
					System.out.println("N�mero de relat�rios gerados: " + manageAnimals.generateReportsPerSpecie());
					
					break;
					
				case 2:
					
					//gera relat�rios de animais sem ra�a indefinida por esp�cie e imprime a quantidade de ficheiros gerados
					System.out.println("N�mero de relat�rios gerados: " + manageAnimals.generateBreedAnimalsPerSpecieReport());
					
					break;
					
				case 3:
					
					//se foi gerado o relat�rio de ado��es
					if(manageAnimals.generateAdoptionReport()) {
						
						//imprime mensagem de sucesso
						System.out.println("Relat�rio gerado com sucesso.");
						
					//senao
					}else {
						
						//imprime mensagem de erro
						System.out.println("Relat�rio n�o foi gerado.");
						
					}
					
					break;
	
				case 0:
					
					System.out.println("\nA voltar ao menu principal...");
					break;
					
				default:
					
					System.out.println("\nOp��o inv�lida, tente novamente.");
					break;
			
			}
			
			System.out.println();
			
		}while(option != 0);
		
	}
	
	//fun��o para obter do input um inteiro
	private static int scanInteger(String msg) {
		
		boolean valid;
		
		int integer = 0;
		
		do {
			
			System.out.print(msg);
			
			try {
				
				integer = keyboard.nextInt();
				valid = true;
				
				
			}catch(InputMismatchException exception) {
				
				System.out.println("\nEntrada inv�lida, tente novamente.");
				keyboard.next();
				valid = false;
				
			}
			
			System.out.println();
			
		}while(!valid);
		
		keyboard.nextLine();
		
		return integer;
		
	}
	
	//fun��o para obter do input um float
	private static float scanFloat(String msg) {
		
		boolean valid;
		
		float f = 0;
		
		do {
			
			System.out.print(msg);
			
			try {
				
				f = keyboard.nextFloat();
				valid = true;
				
				
			}catch(InputMismatchException exception) {
				
				System.out.println("\nEntrada inv�lida, tente novamente.");
				keyboard.next();
				valid = false;
				
			}
			
			System.out.println();
			
		}while(!valid);
		
		keyboard.nextLine();
		
		return f;
		
	}
	
	//fun��o para obter do input uma string
	private static String scanString(String msg) {
		
		boolean valid;
		
		String str = "";
		
		do {
			
			System.out.print(msg);
			
			try {
				
				str = keyboard.nextLine();
				valid = true;
				
				
			}catch(InputMismatchException exception) {
				
				System.out.println("\nEntrada inv�lida, tente novamente.");
				keyboard.next();
				valid = false;
				
			}
			
			System.out.println();
			
		}while(!valid);
		
		return str;
		
	}
	
	//fun��o para obter do input um booleano
	private static boolean scanBoolean(String msg) {
		
		boolean valid;
		
		String str = "";
		
		do {
			
			System.out.print(msg);
			
			try {
				
				str = keyboard.nextLine();
				
				if(str.equals("Sim") || str.equals("sim") || str.equals("N�o") || str.equals("n�o")) {
					
					valid = true;
					
				}else {
					
					valid = false;
					
				}
				
				
				
			}catch(InputMismatchException exception) {
				
				System.out.println("\nEntrada inv�lida, tente novamente.");
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
