package vn.edu.hcmuaf.fit.service.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.dao.TrademarkDAO;
import vn.edu.hcmuaf.fit.dao.impl.TrademarkDAOImpl;
import vn.edu.hcmuaf.fit.entity.Trademark;
import vn.edu.hcmuaf.fit.helper.DbManager;
import vn.edu.hcmuaf.fit.model.Trademark;
import vn.edu.hcmuaf.fit.service.TrademarkService;

import java.sql.SQLException;
import java.util.List;

public class TrademarkServiceImpl implements TrademarkService {
	private final TrademarkDAO trademarkDAO;
	
	public TrademarkServiceImpl(IConnectionPool connectionPool) {
		this.trademarkDAO = new TrademarkDAOImpl(connectionPool);
	}
	
	@Override
	public List<Trademark> getTrademarks() throws SQLException {
		return trademarkDAO.findAll();
	}
	
	@Override
	public List<String> getListNameHasProduct() throws SQLException {
		return trademarkDAO.getListNameHasProduct();
	}
	
	@Override
	public Trademark get(int id) throws SQLException {
		return trademarkDAO.get(id);
	}
	
	@Override
	public void create(Trademark trademark) throws SQLException {
		trademarkDAO.create(trademark);
	}
	
	@Override
	public void update(Trademark trademark) throws SQLException {
		trademarkDAO.update(trademark);
	}
	
	@Override
	public void delete(int id) throws SQLException {
		trademarkDAO.delete(id);
	}
	
	@Override
	public void changeActive(int id) throws SQLException {
		trademarkDAO.changeActive(id);
	}
	
	@Override
	public boolean isExist(String name, String website) throws SQLException {
		return trademarkDAO.isExist(name, website);
	}
}
