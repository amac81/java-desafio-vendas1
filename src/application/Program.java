/**
* Programa desafio Java vendas 1
* Devsuperior.club

* @author  Arnaldo Canelas
* @version 1.0
* @since   2024-06-20
*/

package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		
		Scanner sc = new Scanner(System.in);

		System.out.print("Digite o caminho do ficheiro: ");
		String filePath = sc.nextLine();

		
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String fileLine = br.readLine();
			
			List <Sale> salesList = new ArrayList<Sale>();
			
			while (fileLine != null) {
				String [] fields = fileLine.split(",");
				
				salesList.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), 
						Double.parseDouble(fields[4])));
				
				fileLine = br.readLine();
			}
			
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de preço médio: ");
			
			//Comparator
			Comparator <Sale> saleComparator = (s1, s2) -> s1.averagePrice().compareTo(s2.averagePrice());
			
			//pipeline para 5 maiores pm de 2016
			List<Sale> majorSalesPipeline =  salesList.stream()
					.filter(s -> s.getYear() == 2016)
					.sorted(saleComparator.reversed())
					.limit(5)
					.collect(Collectors.toList());
			
			majorSalesPipeline.forEach(System.out::println);
			
			
			
			//pipeline para total vendido pelo vendedor Logan nos meses 1 e 7
						
			Double loganSales =	salesList.stream()
					.filter(s -> s.getSeller().equals("Logan"))
					.filter(s -> s.getMonth() == 1 || s.getMonth() == 7)
					.map(s -> s.getTotal())
					.reduce(0.0, (s1, s2)-> s1 + s2);	
			
						
			System.out.println();
			System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f\n", loganSales);
			
	
		} catch (IOException err) {
			System.out.println("Erro: " + "(" + err.getMessage() + ")");
		}
	
		sc.close();	
	}
}
