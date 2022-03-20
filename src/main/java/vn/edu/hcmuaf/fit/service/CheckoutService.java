package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.dto.checkout.Checkout;
import vn.edu.hcmuaf.fit.dto.checkout.CheckoutResponse;

public interface CheckoutService {
	CheckoutResponse createOrder(Checkout checkout);
}
