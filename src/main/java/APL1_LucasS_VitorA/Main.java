package APL1_LucasS_VitorA;

/**
 *
 * @author Lucas Pires de Camargo Sarai - 10418013
 * @author Vitor Alves Pereira - 10410862
 * 
 * Ciência da Computação
 * 3D
 * EDI
 * 
 * Referências:
 * https://profkishimoto.github.io/edi03d-2024-1/atividades/n1/EDI-2024.1%20-%20Apl1.pdf
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
   	// exibirMenu() --> exibe um menu com opções de 1 a 5 para o cálculo de operações aritméticas.
    public static void exibirMenu() {
    	
    	System.out.println("""
    			
    			------------MENU-------------
    			| [1] Entrada da expressao aritmetica (notacao infixa).
    			| [2] Entrada dos valores numericos.
    			| [3] Conversao infixa --> posfixa.
    			| [4] Solucao da expressao.
    			| [5] Encerrar. """);
    	
    }
    
    /*Métodos auxiliares */
    
    // estaNoVetor(char elemento, char vetor[]) --> verifica se um elemento pertence a um vetor de caraceres.
    // NÃO USAR
    public static boolean estaNoVetor(char e, char v[]) {
        
    	for (char c: v) {
            if (c == e) return true;
        }
    	
        return false;
        
    }
    
    // indiceNoVetor(char elemento, char vetor[]) --> busca o índice de um caractere no vetor.
    //NÃO USAR
    public static int indiceNoVetor(char e, char v[]){
        for(int i=0; i<v.length;i++){
            if(v[i]==e) return i;
        }
        return -1;
    }
  
    // tamanhoUtilizado(char vetor[]) --> verifica quanto de espaço foi usado em um vetor de caracteres.
    //NÃO USAR
    public static int tamanhoUtilizado(char v[]){
        int count = 0;
        for(int i=0; v[i] != ' '; i++) 
            count++;
        return count;
    }
    
   // tamanhoUtilizado(Integer vetor[]) --> verifica quanto de espaço foi usado em um vetor de inteiros.
    //NÃO USAR
    public static int tamanhoUtilizado(Float v[]){
        int count = 0;
        for(int i=0; v[i] != null; i++) 
            count++;
        return count;
    }
    
    // limparVetor(Integer vetor[]) --> atribui o valor null para todos os elementos de um vetor de inteiros, limpando o vetor por completo.
    //NÃO USAR
    public static void limparVetor(Float v[]){
        for(int i = 0; i < tamanhoUtilizado(v); i++) v[i]=null;
    }
    
    // limparVetor(char operandos[]) --> atribui ' ' para todos os elementos de um vetor de caracteres, limpando o vetor por completo.
    //NÃO USAR
    public static void limparVetor(char ops[]){
        for(int i = 0; i < tamanhoUtilizado(ops); i++) ops[i] = ' ';
    }
    
    // inicializarVetor(Integer vetor[]) --> inicializa o vetor de inteiros com um valor que nunca será utilizado.
    //NÃO USAR
    public static void inicializarVetor(char v[]){
        for(int i=0; i< v.length; i++) 
            v[i] = ' ';
    }
    
    // receberOperandos(String expressao) --> lê uma expressão infixa e filtra os operandos A, B, C, etc.
    //NÃO USAR
    public static char[] receberOperandos(String exp){
        
        // Validação: se a expressão não foi passada, retorna um '!' como código de erro.
        if(exp.isEmpty()) return "!".toCharArray();
        
         // Transforma a expressão em um vetor de caracteres
        char exp_array[] = exp.toCharArray();

         // Vetor de operandos com o tamanho máximo de 26 caracteres (alfabeto)
        char ops[] = new char[26];
        
        int i=0; //Índice para adicionar os operandos no vetor.
        
        for(char c: exp_array){
             // Condição: se for uma letra e ainda não estiver no vetor, adiciona.
            if(c!='*' && c!='^' && c!='/' && c!='+' && c!='-' && c!='(' 
               && c!=')' && c!=' ' && !estaNoVetor(c,ops))
                ops[i++] = c;
        }
        
        // Preenche o resto do vetor com espaços.
        for(int j = i; j<ops.length; j++){
            ops[j] = ' ';
        }
        
        return ops; //Retorna o vetor de operandos.
    }
    
    // infixaParaPosfixa(String expressão, char operandos[], Pilha pilha) 
    //--> Converte uma expressão na forma infixa para posfixa
    //USAR
    public static String infixaParaPosfixa(String exp, char ops[], Pilha p){
        
        p.clear(); //Limpa a pilha, para não exibir o valor da outra operação.
        
        StringBuilder posfixa = new StringBuilder();
        int prioridadeTopo = 0, prioridadeEl;
        
        //Percorrendo a expressão infixa.
        for(char c: exp.toCharArray()){
            
             // Condição: se for um operando, copia para a saída.
            if(estaNoVetor(c,ops)) posfixa.append(c);
            
            //Caso contrário, é uma operação ou ().
            else{
                
                // Condição: se c for igual a '(' ou a pilha for vazia, empilha.
                if(c == '(' || p.isEmpty()) {
                    
                   // Parêntese aberto reduz a prioridade do topo da pilha.
                    if(c == '(') prioridadeTopo -= 3;
                    p.push(Character.toString(c));
                    
                }
                
                //')' faz com que desempilhe e copie na saída até achar '('.
                else if(c == ')'){
                    while(!p.top().equals("(")) posfixa.append(p.pop());
                    p.pop();
               
                }
                
                // Definição da prioridade das operações.
                else{
                   prioridadeEl = switch(c){
                        case '^' -> 3;
                        case '*', '/' -> 2;
                        case '+', '-' -> 1;
                        default -> 0;
                    };
                
                   prioridadeTopo = switch (p.top()) {
                        case "^" -> 3;
                        case "*", "/" -> 2;
                        case "+", "-" -> 1;
                        default -> 0;
                   };
                   
                   // Condição: se a prioridade do topo for maior, desempilha e 
                   //empilha a nova. Caso contrário, apenas empilha a nova operação.
                    if(prioridadeTopo >= prioridadeEl) posfixa.append(p.pop());
                    p.push(Character.toString(c)); 
                }
            }
        }
      
        //Desempilha e copia para a saída as últimas operações.
        while(!p.isEmpty())posfixa.append(p.pop());
        return posfixa.toString();
    }

    // verificar(String sentence, Pilha pilha) --> Verificação da sentença.
    public static boolean verificar(String sentence, Pilha p){
        char charSentence[] = sentence.toCharArray();
        
        //Analisa a simetria dos sinais de abertura e fechamento.
        for(char c: charSentence){
            
            // Insere os caracteres que abrem.
            if(c == '(')
                p.push(Character.toString(c));
            
            //Analisa os caracteres que fecham.
            else if(c == ')'){
                
                // Condição: Se todos não forem fechados, retorna falso.
                if(p.isEmpty() || (c == ')'  && !p.top().equals("(")))
                    return false;
                
                // Remove os que fecham corretamente.
                p.pop(); 
            }

        }
        //Se a pilha estiver vazia, é válido, pois todos foram fechados.
        return p.isEmpty(); 
    }
    
    // calcularPosfixa(String posfixa, Pilha pilha, char operandos[], Float values[]) 
    //--> calcula uma operação na forma posfixa.
    //USAR
    public static Float calcularPosfixa(String posf, Pilha p, char ops[],Float values[] ){
        
        // Todas as operações são realizadas entre dois operandos (a;b).
        float a,b; 
        
        //Percorrendo a posfixa
        for(char c: posf.toCharArray()){
            
            //Se for operando, empilha seu valor.
            if(estaNoVetor(c,ops))
                p.push(values[indiceNoVetor(c,ops)].toString());
            
            //Caso contrário, desempilha os operandos, empilha o resultado.
            else{
                
                //Operandos: desempilham e recebem os valores da pilha.
                b = Float.parseFloat(p.pop());
                a = Float.parseFloat(p.pop());
                
                //Empilha o resultado de acordo com a operação.
                switch(c){
                    case '+' -> p.push(Float.toString(a+b));
                    case '-' -> p.push(Float.toString(a-b));
                    case '*' -> p.push(Float.toString(a*b));
                    case '/' -> {
                        if(b == 0) return null;
                        p.push(Float.toString(a/b));
                    }
                    case '^' -> p.push(Float.toString((int)Math.pow(a, b)));
                }
            }  
        }
        
        //Resultado
        return Float.valueOf(p.top()); 
    }

    
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        Pilha principal = new Pilha();
        Pilha verificacao = new Pilha(); 
        String infixa = "", posfixa;
        char operandos[] = {'!'};
        Float valores[] = new Float[26];
        Integer opc = 0; 
        Float resultado;
        boolean valorEhNumero,opcEhInteiro, infixaEhValida;
        
        while(true){
            
            //Validação da opção.
            do{
                opcEhInteiro = false; 
                try{
                    exibirMenu();
                    System.out.print("Escolha uma opção: ");
                    opc = input.nextInt();
                    
                     // Validação: Opção fora do intervalo (1 a 5).
                    while(opc < 1 || opc > 6){ 
                        
                        System.out.println("Opção inválida!\n");
                        exibirMenu();
                        System.out.print("Escolha uma opção: ");
                        opc = input.nextInt();
                        
                    }
                    
                    opcEhInteiro = opc.getClass().getSimpleName().equals("Integer");
                    
                // Validação: Entrada de tipo diferente de inteiro.        
                }catch(InputMismatchException e){ 
                    
                    System.out.println("Opção inválida!\n");
                    input.nextLine(); //Limpeza do buffer.
                }
            }while(!opcEhInteiro);
            
            //Opção 5: Encerramento.
             if(opc == 5) {
                input.close();
                System.out.println("\nPrograma finalizado.");
                break;
            }
            
            //Fim do menu e análise da opção.
            System.out.println("-----------------------------");
            
            switch (opc){
                
                // Opção 1 --> Entrada da expressão na forma infixa.
                case 1 -> {
                    input.nextLine(); // Limpa o buffer.
                    limparVetor(valores); //Exclui os valores anteriores.
                    infixaEhValida = false;
                    
                    do{
                        
                        System.out.print("Insira a expressão: ");
                        infixa = input.nextLine().trim().replace(" ", "")
                                                    .toUpperCase();
                        
                        //Validação: expressão com erro nos '()'.
                        if(!verificar(infixa,verificacao)){
                            
                            //Limpando a pilha para a próxima verificação.
                            verificacao.clear(); 
                            System.out.println("\nERRO: Não houve correspondência"
                                    + " entre parênteses de abertura e"
                                    + " fechamento.\n");
                            continue;
                            
                        }
                        
                        
                       
                        int i = 0;
                        while(i < infixa.length()){
                            
                            char inf = infixa.charAt(i);
                            
                            //Validação 1: Evitando variáveis com mais de uma letra.
                            if(i<infixa.length()-1 && (inf>=65 && inf<=90)
                                &&((infixa.charAt(i+1)>=65 && infixa.charAt(i+1)<=90)
                                || infixa.charAt(i+1)=='(')){
                                
                                System.out.println("\nERRO: Variáveis devem ter"
                                        + " apenas um caracter!");
                                System.out.println("--Ou não omita sinais de"
                                        + " operação, como 'AB' ao invés de "
                                        + "A*B. \n");
                                break;
                            
                           //Validação 2: Evitando caracteres inválidos.
                            } else if((inf<65 || inf>90)
                                     && inf != '*' && inf != '/'
                                     && inf != '^' && inf != '+'
                                     && inf != '-' && inf != '('
                                     && inf != ')'){
                                System.out.println("\nERRO: Caractere inválido!\n");
                                break;  
                            }
                            
                            ++i;
                            
                        }
                        
                       //Caso de todos os dados da expressão serem válidos.
                        if(i == infixa.length()) infixaEhValida = true;
                        
                    }while(!infixaEhValida);
                    
                    operandos = receberOperandos(infixa);
                    System.out.print("Expressão: " + infixa + "\n");
                    
                }
                
                // Opção 2 --> Entrada dos valores dos operandos. 
                case 2 -> {
                    limparVetor(valores); //Exclui os valores anteriores.
                    
                    // Validação: Caso não existam operandos (pulando a opção 1), retorna o erro.
                    if(operandos[0] == '!'){ 
                        System.out.println("ERRO: Insira primeiro a expressão.");
                        break;
                    }
                    
                    System.out.println("Insira os valores");
                    
                    //Validação: Caso os valores não sejam numéricos, retorna erro.
                    for(int j = 0; j < tamanhoUtilizado(operandos); j++){
                        
                        do{
                            
                            valorEhNumero = false;
                            try{
                                System.out.print(operandos[j] + " = ");
                                valores[j] = input.nextFloat();
                                valorEhNumero = valores[j].getClass()
                                        .getSimpleName().equals("Float");
                                
                            } catch(InputMismatchException e){
                                
                                System.out.println("\nInsira valores numéricos!");
                                System.out.println("--Ou verifique se passou"
                                        + " o valor real seguindo o padrão da sua"
                                        + " máquina:\n-Vírgula(BR -> 2,5)"
                                        + "\n-Ponto(US -> 2.5)\n");
                                input.nextLine(); //Limpeza do buffer.
                                
                            }
                            
                        } while(!valorEhNumero);
                    }
                }
                
                // Opção 3 --> Conversão da forma infixa para posfixa.
                case 3 -> { 
                    
                    //Validação: Se pular a opção 1, retorna um erro. 
                    if(infixa.equals("") || operandos[0]==' ') 
                        System.out.println("ERRO: Insira primeiro a expressão.");
                    
                    else{
                        System.out.println("\nInfixa: " + infixa);
                        System.out.print("Posfixa: ");
                        System.out.println(infixaParaPosfixa(infixa,
                                operandos,
                                principal) + "\n");
                    }
                }
                
                // Opção 4 --> Resultado da expressão posfixa.
                case 4 -> {
                    
                    // Validação: Se os operandos não possuem valor 
                    // (opção 2 não foi utilizada), retorna um erro.
                    if(tamanhoUtilizado(valores) == 0 
                       || tamanhoUtilizado(valores) != tamanhoUtilizado(operandos))
                        System.out.println("ERRO: Não foram passados os valores.");
                    
                    //Validação: Se pular a opção 1, retorna um erro.
                    else if(infixa.equals("")) 
                        System.out.println("ERRO: Insira primeiro a expressão.");
                    
                    else{
                        
                        //Limpa a pilha antes de usá-la novamente.
                        if(!principal.isEmpty()) principal.clear();
                        
                       //Executa a conversão.
                        posfixa = infixaParaPosfixa(infixa,operandos,principal);
                        resultado = calcularPosfixa(posfixa,
                                        principal,
                                        operandos,
                                        valores);
                        System.out.println("Expressão posfixa: " + posfixa);
                        System.out.println("Valores:");
                        
                        //Exibe os operandos e seus valores.
                        for(int i = 0; i < tamanhoUtilizado(operandos); i++){
                            System.out.println(operandos[i] + " = " + valores[i]);
                        }
                        
                        //Validação: Retorno 'null' foi configurado para
                        //divisão por zero.
                        if(resultado == null) 
                            System.out.println("ERRO! Divisão por zero.");
                        else System.out.println("\nResultado = " + resultado);
                    }
                }
            }
        }   
    }
}
