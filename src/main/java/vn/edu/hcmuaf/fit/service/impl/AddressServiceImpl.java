package vn.edu.hcmuaf.fit.service.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.AppError;
import vn.edu.hcmuaf.fit.dao.AddressDAO;
import vn.edu.hcmuaf.fit.dao.DistrictDAO;
import vn.edu.hcmuaf.fit.dao.WardDAO;
import vn.edu.hcmuaf.fit.dao.impl.AddressDAOImpl;
import vn.edu.hcmuaf.fit.dao.impl.DistrictDAOImpl;
import vn.edu.hcmuaf.fit.dao.impl.WardDAOImpl;
import vn.edu.hcmuaf.fit.domain.AppBaseResult;
import vn.edu.hcmuaf.fit.domain.AppServiceResult;
import vn.edu.hcmuaf.fit.dto.address.AddressCreate;
import vn.edu.hcmuaf.fit.dto.address.AddressDto;
import vn.edu.hcmuaf.fit.dto.address.AddressUpdate;
import vn.edu.hcmuaf.fit.entity.Address;
import vn.edu.hcmuaf.fit.entity.District;
import vn.edu.hcmuaf.fit.entity.Ward;
import vn.edu.hcmuaf.fit.service.AddressService;
import vn.edu.hcmuaf.fit.util.AddressUtil;

import java.util.ArrayList;
import java.util.List;

public class AddressServiceImpl implements AddressService {
	private final AddressDAO addressDAO;
	private final DistrictDAO districtDAO;
	private final WardDAO wardDAO;
	
	public AddressServiceImpl(IConnectionPool connectionPool) {
		this.addressDAO = new AddressDAOImpl(connectionPool);
		this.districtDAO = new DistrictDAOImpl(connectionPool);
		this.wardDAO = new WardDAOImpl(connectionPool);
	}

	@Override
	public AppServiceResult<List<AddressDto>> findAddressByTrademarkId(Long trademarkId) {
		try {
			List<Address> entities = addressDAO.findByTrademarkId(trademarkId);

			List<AddressDto> result = new ArrayList<>();

			entities.forEach(entity -> result.add(AddressDto.createFromEntity(entity)));

			return new AppServiceResult<>(true, 0, "Succeed!", result);
		} catch (Exception e) {
			e.printStackTrace();
			return new AppServiceResult<>(false, AppError.Unknown.errorCode(),
					AppError.Unknown.errorMessage(), null);
		}
	}

	@Override
	public AppServiceResult<List<AddressDto>> findAddressByUserId(Long userId) {
		try {
			List<Address> entities = addressDAO.findByUserId(userId);

			List<AddressDto> result = new ArrayList<>();

			entities.forEach(entity -> result.add(AddressDto.createFromEntity(entity)));

			return new AppServiceResult<>(true, 0, "Succeed!", result);
		} catch (Exception e) {
			e.printStackTrace();
			return new AppServiceResult<>(false, AppError.Unknown.errorCode(),
					AppError.Unknown.errorMessage(), null);
		}
	}

	@Override
	public AppServiceResult<AddressDto> findAddressById(Long id) {
		try {
			Address address = addressDAO.findById(id);

			if (address == null)
				return new AppServiceResult<>(false, AppError.Validattion.errorCode(),
						"Address id is not exist: " + id, null);

			return new AppServiceResult<>(true, 0, "Succeed!", AddressDto.createFromEntity(address));
		} catch (Exception e) {
			e.printStackTrace();
			return new AppServiceResult<>(false, AppError.Unknown.errorCode(),
					AppError.Unknown.errorMessage(), null);
		}
	}

	@Override
	public AppServiceResult<AddressDto> findAddressByPath(String path) {
		try {
			Address address = addressDAO.findByPath(path);

			if (address == null)
				return new AppServiceResult<>(false, AppError.Validattion.errorCode(),
						"Address path is not exist: " + path, null);

			return new AppServiceResult<>(true, 0, "Succeed!", AddressDto.createFromEntity(address));
		} catch (Exception e) {
			e.printStackTrace();
			return new AppServiceResult<>(false, AppError.Unknown.errorCode(),
					AppError.Unknown.errorMessage(), null);
		}
	}

	@Override
	public AppServiceResult<AddressDto> createAddress(AddressCreate address) {
		try {
			Address newAddress = new Address();

			if (address.getProvinceId() == null) {
				return new AppServiceResult<>(false, AppError.Validattion.errorCode(),
						"Province is required!", null);
			}

			if (address.getDistrictId() == null) {
				return new AppServiceResult<>(false, AppError.Validattion.errorCode(),
						"District is required!", null);
			}

			District district = districtDAO.findById(address.getDistrictId());

			if (district.getWards().size() > 0) {
				if (address.getWardId() == null) {
					return new AppServiceResult<>(false, AppError.Validattion.errorCode(), "Ward is required!", null);
				}

				if (address.getWardId() != 0) {
					if (address.getNumber() == null && address.getNumber().equals("")) {
						return new AppServiceResult<>(false, AppError.Validattion.errorCode(), "Number is required!", null);
					}

					if (address.getStreet() == null && address.getNumber().equals("")) {
						return new AppServiceResult<>(false, AppError.Validattion.errorCode(), "Street is required!", null);
					}
				}
			}

			Ward ward = wardDAO.findById(address.getWardId());

			String path = ward == null ? AddressUtil.formatAddressWithoutWard(address.getNumber(), address.getStreet(), district)
					: AddressUtil.formatAddress(address.getNumber(), address.getStreet(), ward, district);

			List<Address> addresses = addressDAO.findAll();
			for (Address item : addresses) {
				if (item.getPath().equals(path))
					return new AppServiceResult<>(false, AppError.Validattion.errorCode(), "Address is existed", null);
			}

			newAddress.setId(0L);
			newAddress.setNumber(address.getNumber());
			newAddress.setStreet(address.getStreet());
			newAddress.setWard(ward);
			newAddress.setDistrict(district);
			newAddress.setPath(path);

			addressDAO.save(newAddress);

			return new AppServiceResult<>(true, 0, "Succeed!", AddressDto.createFromEntity(newAddress));
		} catch (Exception e) {
			e.printStackTrace();
			return new AppServiceResult<>(false, AppError.Unknown.errorCode(),
					AppError.Unknown.errorMessage(), null);
		}
	}

	@Override
	public AppBaseResult updateAddress(AddressUpdate address) {
		try {
			Address updateAddress = addressDAO.findById(address.getId());

			if (updateAddress == null) {
				return AppBaseResult.GenarateIsFailed(AppError.Validattion.errorCode(), "Address id is not exist: " + address.getId());
			}

			if (address.getProvinceId() == null)
				return new AppServiceResult<AddressDto>(false, AppError.Validattion.errorCode(),
						"Province is required!", null);

			if (address.getDistrictId() == null)
				return new AppServiceResult<AddressDto>(false, AppError.Validattion.errorCode(),
						"District is required!", null);

			District district = districtDAO.findById(address.getDistrictId());

			if (district.getWards().size() > 0) {
				if (address.getWardId() == null)
					return new AppServiceResult<AddressDto>(false, AppError.Validattion.errorCode(), "Ward is required!", null);

				if (address.getWardId() != 0) {
					if (address.getNumber() == null)
						return new AppServiceResult<AddressDto>(false, AppError.Validattion.errorCode(), "Number is required!", null);

					if (address.getStreet() == null)
						return new AppServiceResult<AddressDto>(false, AppError.Validattion.errorCode(), "Street is required!", null);
				}
			}

			Ward ward = wardDAO.findById(address.getWardId());

			String path = ward == null ? AddressUtil.formatAddressWithoutWard(address.getNumber(), address.getStreet(), district)
					: AddressUtil.formatAddress(address.getNumber(), address.getStreet(), ward, district);

			List<Address> addresses = addressDAO.findAll();
			for (Address item : addresses) {
				if (item.getPath().equals(path))
					return new AppServiceResult<AddressDto>(false, AppError.Validattion.errorCode(), "Address is existed", null);
			}

			updateAddress.setId(address.getId());
			updateAddress.setNumber(address.getNumber());
			updateAddress.setStreet(address.getStreet());
			updateAddress.setWard(ward);
			updateAddress.setDistrict(district);
			updateAddress.setPath(path);

			addressDAO.save(updateAddress);

			return new AppServiceResult<>(true, 0, "Succeed!", AddressDto.createFromEntity(updateAddress));
		} catch (Exception e) {
			e.printStackTrace();
			return new AppServiceResult<AddressDto>(false, AppError.Unknown.errorCode(),
					AppError.Unknown.errorMessage(), null);
		}
	}

	@Override
	public AppBaseResult deleteAddress(Long id) {
		try {
			Address address = addressDAO.findById(id);

			if (address != null) {
				addressDAO.delete(id);
				return AppBaseResult.GenarateIsSucceed();
			} else {
				return AppBaseResult.GenarateIsFailed(AppError.Validattion.errorCode(), "Address id is not exist: " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AppBaseResult.GenarateIsFailed(AppError.Unknown.errorCode(), AppError.Unknown.errorMessage());
		}
	}
}
