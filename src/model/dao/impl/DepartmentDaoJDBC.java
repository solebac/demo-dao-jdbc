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
import model.dao.DepartmentDao;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn){
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					" INSERT INTO department "
					+ " ( "
					+ " Name ) "
					+ " VALUES "
					+ " (?) ", Statement.RETURN_GENERATED_KEYS
			);
			st.setString(1, obj.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
				
			}else{
				throw new DbException("Insert failed!");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					" UPDATE department "
					+ " SET "
					+ " Name = ? "
					+ " WHERE Id = ? "
			);
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			int rows = st.executeUpdate();
			if (rows < 0) {
				throw new DbException("Update failed!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					" DELETE FROM department "
					+ " WHERE Id = ?"  
			);
			st.setInt(1, id);
			int rows = st.executeUpdate();
			if (rows <= 0) {
				throw new DbException("Nenhum arquivo foi removido.");	
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					" SELECT Id, "
						    + " Name "
						    + " FROM department "
						    + " where Id = ? "
			);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				return department;	
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department department = new Department();
		department.setId(rs.getInt("Id"));
		department.setName(rs.getString("Name"));
		return department;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					" SELECT Id, "
						    + " Name "
						    + " FROM department "
			);
			rs = st.executeQuery();
			List<Department> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				Department dep = instantiateDepartment(rs);
				/*
				 * Logica para dependencia 1=1
				 * Department dep = map.get(rs.getInt("Id"));
				if (dep == null) {
					dep = new Department(rs.getInt("Id"), rs.getString("Name"));
					map.put(rs.getInt("Id"), dep);
				}*/
				list.add(dep);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
