package osgi.deliveryproducer;

public final class DeliveryValues {
	private DeliveryValues() {
	}
	// Basic & Additional fees
	public static final double BASE_DELIVERY_FEE = 100;
	public static final double EXPRESS_DELIVERY_SPECIAL_CHARGE = 300;
	
	// Delivery Charges according to distances
	public static final double DELIVERY_CHARGE_PER_KM_BELOW_5 = 0;
	public static final double DELIVERY_CHARGE_PER_KM_5_TO_10 = 5;
	public static final double DELIVERY_CHARGE_PER_KM_10_TO_25 = 6;
	public static final double DELIVERY_CHARGE_PER_KM_ABOVE_25 = 7.5;
	
	// Discount factors
	public static final double DISCOUNT_ELIGIBLE_LIMIT = 1000;
	public static final double DISCCOUNT_RATE = 0.1; // 10%
	
}
