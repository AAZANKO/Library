package by.htp.library.dao;

import java.util.ArrayList;

import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Zakaz;

public interface ZakazDAO {
	
	ArrayList<Zakaz> allZakaz() throws DAOException;
	
	ArrayList<Zakaz> einUserZakaz(String idUser) throws DAOException;
	
	ArrayList<Zakaz> einUserZakazSearchPageDAO(int nStartRows, int nFinishRows, String idUser) throws DAOException;
	
	Integer countRowsTable() throws DAOException;
	
	Integer countRowsTableForUser(String idUser) throws DAOException;
	
	ArrayList<Zakaz> allZakazSearchPageDAO(int nStartRows, int nFinishRows) throws DAOException;

}
