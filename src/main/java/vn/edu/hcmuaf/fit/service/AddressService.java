package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.domain.AppBaseResult;
import vn.edu.hcmuaf.fit.domain.AppServiceResult;
import vn.edu.hcmuaf.fit.dto.address.AddressCreate;
import vn.edu.hcmuaf.fit.dto.address.AddressDto;
import vn.edu.hcmuaf.fit.dto.address.AddressUpdate;

import java.util.List;

public interface AddressService {
	AppServiceResult<List<AddressDto>> findAddressByTrademarkId(Long trademarkId);
	AppServiceResult<List<AddressDto>> findAddressByUserId(Long userId);
	AppServiceResult<AddressDto> findAddressById(Long id);
	AppServiceResult<AddressDto> findAddressByPath(String path);
	AppServiceResult<AddressDto> createAddress(AddressCreate address);
	AppBaseResult updateAddress(AddressUpdate address);
	AppBaseResult deleteAddress(Long id);
}
