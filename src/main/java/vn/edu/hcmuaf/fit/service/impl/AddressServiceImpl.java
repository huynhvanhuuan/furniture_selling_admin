package vn.edu.hcmuaf.fit.service.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.dao.AddressDAO;
import vn.edu.hcmuaf.fit.dao.impl.AddressDAOImpl;
import vn.edu.hcmuaf.fit.dto.address.District;
import vn.edu.hcmuaf.fit.dto.address.Province;
import vn.edu.hcmuaf.fit.dto.address.Ward;
import vn.edu.hcmuaf.fit.helper.DbManager;
import vn.edu.hcmuaf.fit.model.Address;
import vn.edu.hcmuaf.fit.service.AddressService;

import java.sql.SQLException;
import java.util.List;

public class AddressServiceImpl implements AddressService {
	private final AddressDAO addressDAO;
	
	public AddressServiceImpl(IConnectionPool connectionPool) {
		this.addressDAO = new AddressDAOImpl(connectionPool);
	}
	
	@Override
	public List<Address> getListByTrademarkId(int trademarkId) throws SQLException {
		return addressDAO.getListByTrademarkId(trademarkId);
	}
	
	@Override
	public List<Address> getListByUserId(int userId) throws SQLException {
		return addressDAO.getListByUserId(userId);
	}
	
	@Override
	public List<Province> getProvinceList() throws SQLException {
		return addressDAO.getProvinceList();
	}
	
	@Override
	public List<District> getDistrictListByProvinceId(int provinceId) throws SQLException {
		return addressDAO.getDistrictListByProvinceId(provinceId);
	}
	
	@Override
	public List<Ward> getWardListByDistrictId(int districtId) throws SQLException {
		return addressDAO.getWardListByDistrictId(districtId);
	}
	
	@Override
	public Address getAddress(int id) throws SQLException {
		return addressDAO.getAddress(id);
	}
	
	@Override
	public Province getProvince(int id) throws SQLException {
		return addressDAO.getProvince(id);
	}
	
	@Override
	public District getDistrict(int id) throws SQLException {
		return addressDAO.getDistrict(id);
	}
	
	@Override
	public Ward getWard(int id) throws SQLException {
		return addressDAO.getWard(id);
	}
	
	@Override
	public void createTrademarkAddress(int trademarkId, Address address) throws SQLException {
		addressDAO.createTrademarkAddress(trademarkId, address);
	}
	
	@Override
	public void createUserAddress(String userId, Address address) throws SQLException {
		addressDAO.createUserAddress(userId, address);
	}
	
	@Override
	public void update(Address address) throws SQLException {
		addressDAO.update(address);
	}
	
	@Override
	public void delete(int id) throws SQLException {
		addressDAO.delete(id);
	}
	
	@Override
	public boolean checkExist(String path) throws SQLException {
		return addressDAO.checkExist(path);
	}
}
