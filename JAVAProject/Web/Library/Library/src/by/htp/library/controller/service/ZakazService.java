package by.htp.library.service;

import java.util.ArrayList;

import by.htp.library.entity.Zakaz;
import by.htp.library.service.exception.ServiceException;

public interface ZakazService {	
	
	ArrayList<Zakaz> allUsersZakazSearch() throws ServiceException;
	
	ArrayList<Zakaz> einUsersZakazSearch(String idUser) throws ServiceException;
	
	ArrayList<Zakaz> einUserZakazSearchPage(int nStartRows, int nFinishRows, String idUser) throws ServiceException;
	
	Integer countRowsZakazTable() throws ServiceException;
	
	Integer countRowsZakazTableForUser(String idUser) throws ServiceException;
	
	ArrayList<Zakaz> allZakazSearchPage(int nStartRows, int nFinishRows) throws ServiceException;

}
