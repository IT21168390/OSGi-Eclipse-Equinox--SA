package osgi.deliveryproducer;

public interface DeliveryServicePublish {
	public String publishDeliveryService();
	
	public void setDeliveryDistance(double distance);
	
	public String checkDiscountStatus();
	
	public double calculateTotalDeliveryCost(boolean expressDelivery);

	public String getReport();
}
