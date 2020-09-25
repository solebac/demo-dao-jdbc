package application;

import java.util.Date;

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
		Seller seller = objSellerDao.findById(3);
		System.out.println(seller);
	}

}
