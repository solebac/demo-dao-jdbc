package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

public class Program01 {

	public static void main(String[] args) {
		//SellerDao objSellerModoErrado = new Selle
		//Eu implemento a fabrica de Dao's, oculto do programa principal
		SellerDao objSellerDao = DaoFactory.createSellerDao();//Implementou as 5 intefaces...
	
		//Teste
		System.out.println("=== Test 1: seller findById ===");
		Seller seller = objSellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\n=== Test 2: seller findByDepartament ===");
		Department obj1 = new Department(2, null);
		List<Seller> list = objSellerDao.findByDepartment(obj1);
		for (Seller seller2 : list) {
			System.out.println(seller2);
		}
		
		System.out.println("\n=== Test 3: seller findAll ===");
		list = objSellerDao.findAll();
		for (Seller seller2 : list) {
			System.out.println(seller2);
		}
		
		System.out.println("\n=== Test 4: seller insert ===");
		Seller newSeller = new Seller(null, "Green", "green@green", new Date(), 1000.0, obj1);
		objSellerDao.insert(newSeller);
		System.out.println("Insert new id.: " + newSeller.getId());
		
		System.out.println("\n=== Test 5: seller update ===");
		Seller upSeller = objSellerDao.findById(1);
		upSeller.setName("TANIA MARIA");
		objSellerDao.update(upSeller);
		System.out.println("Update realizado com success...!");
		
		Scanner sc = new Scanner(System.in);
		System.out.println("\n=== Test 6: seller delete ===");
		System.out.print("Enter id for delete teste.: ");
		Integer id = sc.nextInt();
		objSellerDao.deleteById(id);
		System.out.println("Delete Completer");
		
		
		
	}

}
