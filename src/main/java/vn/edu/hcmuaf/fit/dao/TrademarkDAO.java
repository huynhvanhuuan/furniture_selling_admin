package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.entity.Address;
import vn.edu.hcmuaf.fit.entity.Trademark;

public interface TrademarkDAO extends BaseDAO<Trademark> {
	void createAddress(int id, Address address);
	Trademark findByName(String name);
	Trademark findByWebsite(String website);
}
