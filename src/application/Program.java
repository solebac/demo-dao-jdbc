package application;

import java.util.Date;

import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

public class Program {

	public static void main(String[] args) {
		Department obj = new Department(1, "Logico");
		Seller seller = new Seller(21, "Bob", "solebac@solebac", new Date(), 1000.0, obj);
		System.out.println(obj);System.out.println(seller);
		
		//SellerDao objSellerModoErrado = new Selle
		//Eu implemento a fabrica de Dao's, oculto do programa principal
		SellerDao objSellerDao = DaoFactory.createSellerDao();//Implementou as 5 intefaces...

	}

}
