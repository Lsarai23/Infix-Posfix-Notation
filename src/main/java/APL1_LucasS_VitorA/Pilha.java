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

public class Pilha {
    //Declaração de variáveis.
    private final String dados[];
    private int nextIndex;
    
    //Equivalente ao create(), cria uma pilha vazia.
    public Pilha() {
        nextIndex = 0;
        dados = new String [26];
    }
    
    //Retorna o tamanho máximo disponível.
    public int size(){
        return dados.length;
    }
    
    //Retorna a quantidade de elementos no vetor.
    public int count(){
        return nextIndex;
    }
    
    //Retorna true se o tamanho máximo do vetor for alcançado. 
    public boolean isFull(){
        return size() == count();
    }
    
    //Retorna true se não houver elementos.
    public boolean isEmpty(){
        return count() == 0;
    }
    
    //Limpa a pilha, excluindo todos os elementos.
    public void clear(){
        while(!isEmpty()) pop();
    }
    
    //Caso não esteja cheia, adiciona um elemento em cima da pilha.
    public void push(String valor){
        if(!isFull())dados[nextIndex++] = valor;
        else System.out.println("Operação inválida! Pilha cheia.");
    }
    
    //Retira e retorna o elemento de cima da pilha, caso não esteja vazia.
    public String pop(){
        if(isEmpty()) System.out.println("POP: Operação inválida! Pilha vazia.");
        
         nextIndex--;
         String topo = dados[nextIndex];
         dados[nextIndex] = null;
         return topo;
    }
    
    //Retorna o elemento mais acima da pilha.
    public String top(){
        if(isEmpty()) System.out.println("TOP: Operação inválida! Pilha vazia.");
        return dados[nextIndex-1];
    }
    
}
