package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.entity.Address;

import java.util.List;

public interface AddressDAO {
	List<Address> findByTrademarkId(int trademarkId);
	List<Address> findByUserId(String userId);
	void createTrademarkAddress(int trademarkId, Address address);
	void createUserAddress(String userId, Address address);
}
