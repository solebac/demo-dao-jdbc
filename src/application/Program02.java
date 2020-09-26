package application;

import java.util.List;
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
		
		//Step-00 FindId
		System.out.println("==== Case - 00  FindId ====");
		Department findDep = objDao.findById(3);
		System.out.println("Find.: " + findDep);
		
		//Step-00 FindAll
		System.out.println("==== Case - 00  FindAll ====");
		List<Department> list = objDao.findAll();
		for (Department department : list) {
			System.out.println(department);
		}
		
		//Step-02 INSERT
		System.out.println("==== Case - 01 Insert ====");
		Department obj = new Department(null, "RADIOS");
		objDao.insert(obj);//Processa o Insert
		System.out.println("Insert new id.: " + obj.getId());
		
		//Step-03 UPDATE
		System.out.println("==== Case - 02 Update ====");
		obj.setName("VIOLÃO");
		objDao.update(obj);
		System.out.println("Update success...");
		
		//Step-03 DELETE
		System.out.print("Enter id for delete test.: ");
		int id = sc.nextInt();
		objDao.deleteById(id);
		System.out.println("Delete success...");
		sc.close();
	}

}
