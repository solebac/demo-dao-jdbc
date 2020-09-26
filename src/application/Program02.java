package application;

import java.util.Locale;
import java.util.Scanner;

import entities.Department;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;

public class Program02 {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		//Step-01 Instancia do object INTERFACE <=> FACTORY
		DepartmentDao objDao = DaoFactory.createDepartmentDao();
		
		//Step-02 INSERT
		System.out.println("==== Case - 01 Insert ====");
		Department obj = new Department(null, "RADIOS");
		objDao.insert(obj);//Processa o Insert
		System.out.println("Insert new id.: " + obj.getId());
		
		//Step-03 UPDATE
		System.out.println("==== Case - 02 Update ====");
		obj.setName("VIOLÃO");
		objDao.update(obj);
		sc.close();

	}

}
