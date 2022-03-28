package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.domain.AppBaseResult;
import vn.edu.hcmuaf.fit.domain.AppServiceResult;
import vn.edu.hcmuaf.fit.dto.address.AddressCreate;
import vn.edu.hcmuaf.fit.dto.address.AddressDto;
import vn.edu.hcmuaf.fit.dto.address.AddressUpdate;

import java.util.List;

public interface AddressService {
	AppServiceResult<List<AddressDto>> findAddressByTrademarkId(int trademarkId);
	AppServiceResult<List<AddressDto>> findAddressByUserId(int userId);
	AppServiceResult<AddressDto> findAddressById(int addressId);
	AppServiceResult<AddressDto> findAddressByPath(String path);
	void createAddress(AddressCreate address);
	AppBaseResult updateAddress(AddressUpdate address);
	AppBaseResult delete(int addressId);
}
