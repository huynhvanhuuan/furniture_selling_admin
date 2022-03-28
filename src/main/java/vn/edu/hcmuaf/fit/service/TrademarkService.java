package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.entity.Trademark;

import java.sql.SQLException;
import java.util.List;

public interface TrademarkService {
	AppServiceResult<List<TrademarkDto>> getList() throws SQLException;
	List<String> getListNameHasProduct() throws SQLException;
	Trademark get(int id) throws SQLException;
	void create(Trademark trademark) throws SQLException;
	void update(Trademark trademark) throws SQLException;
	void delete(int id) throws SQLException;
	void changeActive(int id) throws SQLException;
	boolean isExist(String name, String website) throws SQLException;
}
