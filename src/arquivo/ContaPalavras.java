package arquivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ContaPalavras {
  public static void main(String[] args) {
	  
	Scanner sc = new Scanner(System.in);
	  
	System.out.println("Digite um caminho de pasta: ");
	
    String fileName = sc.nextLine(); 
    
    HashMap<String, Integer> contadorDePalavras = new HashMap<>();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
    	  String linha = reader.readLine();
    	  while (linha != null) {
    	    String[] palavras = linha.split(" ");
    	    for (String palavra : palavras) {
    	      if (contadorDePalavras.containsKey(palavra)) {
    	        int contador = contadorDePalavras.get(palavra);
    	        contadorDePalavras.put(palavra, contador + 1);
    	      } else {
    	        contadorDePalavras.put(palavra, 1);
    	      }
    	    }
    	    linha = reader.readLine();
    	  }
	} catch (IOException e) {
	  e.printStackTrace();
	}
    
    List<Map.Entry<String, Integer>> entradas = new ArrayList<>(contadorDePalavras.entrySet());
    entradas.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    for (Map.Entry<String, Integer> entrada : entradas) {
    String palavra = entrada.getKey();
    int contador = entrada.getValue();
    System.out.println(palavra + "' aparece " + contador + " vezes no texto.");
    }
  }
}
