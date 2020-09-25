package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import entities.Department;
import entities.Seller;
import model.dao.SellerDao;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					" INSERT INTO seller "
					+ " ( "
					+ " Name, "
					+ " Email, "
					+ " BirthDate, "
					+ " BaseSalary, "
					+ " DepartmentId )"
					+ " VALUES "
					+ " (?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS
			);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			//Execução
			int rows = st.executeUpdate();
			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else{
				throw new DbException("Nenhum insert foi finalizado com exito.");	
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					" select seller.*, " 
					+ " department.Name as DepName " 
					+ " From seller inner join department " 
					+ " ON seller.DepartmentId = department.Id " 
					+ " where seller.id = ? "
			);
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				
				Seller seller = instantiateSeller(rs, department);
				
				return seller;
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);//Não fecha a conexao, pois podemos realizar o reuso dentro da class
		}
		
		return null;
	}

	private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(department);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department department = new Department();
		department.setId(rs.getInt("Id"));
		department.setName(rs.getString("Name"));
		return department;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					" select seller.*, department.Name as DepName "
					+ " From seller inner join department "
					+ " ON seller.DepartmentId = department.Id "
			);
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			rs = st.executeQuery();
			while (rs.next()) {
				//Reaproveitavamente de Object com MAP
				Department department = map.get(rs.getInt("Id"));
				if (department == null) {
					department = new Department(rs.getInt("Id"), rs.getString("Name"));
					map.put(rs.getInt("Id"), department);
				}
				Seller obj = instantiateSeller(rs, department);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					" select seller.*, " 
					+ " department.Name as DepName " 
					+ " From seller inner join department " 
					+ " ON seller.DepartmentId = department.Id " 
					+ " where seller.DepartmentId = ? "
			);
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			List<Seller> list = new ArrayList<>();
			/*
			 * Erro Deve de otimizar
			 * if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				
				Seller seller = instantiateSeller(rs, dep);
				list.add(seller);
				return list;
			}*/
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					//Salva no Map
					map.put(rs.getInt("DepartmentId"), dep);					
				}
				Seller seller = instantiateSeller(rs, dep);
				list.add(seller);
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);//Não fecha a conexao, pois podemos realizar o reuso dentro da class
		}
	}

}
