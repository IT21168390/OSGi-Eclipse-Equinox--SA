package osgi.deliveryproducer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DeliveryServicePublishImpl implements DeliveryServicePublish {

	private double deliveryDistance;
	private double deliveryCost;
	private double finalDeliveryCost;
	private double discount;
	private double totalDiscounts = 0;
	private int deliveriesCount = 0;
	private Map<String, Double> deliveries = new HashMap<String, Double>();
	
	//Initiate the Delivery Service by displaying the service information
	@Override
	public String publishDeliveryService() {
		return ("Retrieving data from Delivery-Service-Publisher...\n"
				+"\n_____________________________________________________________\n"
				+ "\n Base Delivery Charge: \t\t\t\tRs."+DeliveryValues.BASE_DELIVERY_FEE
				+ "\n Additional: Express Delivery Charge: \t\tRs."+DeliveryValues.EXPRESS_DELIVERY_SPECIAL_CHARGE
				+ "\n\n* <5km \t\t| Delivery Charge per km: \tRs."+DeliveryValues.DELIVERY_CHARGE_PER_KM_BELOW_5+" (FREE)"
				+ "\n* 5-10km \t| Delivery Charge per Km: \tRs."+DeliveryValues.DELIVERY_CHARGE_PER_KM_5_TO_10
				+ "\n* 10-25km \t| Delivery Charge per Km: \tRs."+DeliveryValues.DELIVERY_CHARGE_PER_KM_10_TO_25
				+ "\n* >25km \t| Delivery Charge per Km: \tRs."+DeliveryValues.DELIVERY_CHARGE_PER_KM_ABOVE_25
				+ "\n\n Discount Threshold: \t\t\t\tRs."+DeliveryValues.DISCOUNT_ELIGIBLE_LIMIT)
				+ "\n Discount Factor: \t\t\t\t"+DeliveryValues.DISCCOUNT_RATE*100+"%"
				+ "\n_____________________________________________________________";
	}

	//Set the delivery distance value
	@Override
	public void setDeliveryDistance(double distance) {
		this.deliveryDistance = distance;
	}

	//Calculation of the delivery charges, and storing data of each delivery
	@Override
	public double calculateTotalDeliveryCost(boolean expressDelivery) {
		if(deliveryDistance<5)
			deliveryCost = 0;
		else if(deliveryDistance>=5 && deliveryDistance<=10)
			deliveryCost = DeliveryValues.BASE_DELIVERY_FEE + (deliveryDistance*DeliveryValues.DELIVERY_CHARGE_PER_KM_5_TO_10);
		else if(deliveryDistance>10 && deliveryDistance<=25)
			deliveryCost = DeliveryValues.BASE_DELIVERY_FEE + (deliveryDistance*DeliveryValues.DELIVERY_CHARGE_PER_KM_10_TO_25);
		else
			deliveryCost = DeliveryValues.BASE_DELIVERY_FEE + (deliveryDistance*DeliveryValues.DELIVERY_CHARGE_PER_KM_ABOVE_25);

		if(expressDelivery)
			deliveryCost += DeliveryValues.EXPRESS_DELIVERY_SPECIAL_CHARGE;
		
		finalDeliveryCost = applyDiscount(deliveryCost);
		discount = deliveryCost-finalDeliveryCost;
		totalDiscounts += discount;
		
		deliveriesCount++;
		deliveries.put(Integer.toString(deliveriesCount)+"---"+new Date()+" | "+(expressDelivery?"Express Delivery ":"Standard Delivery")+" | "+deliveryDistance+"km", finalDeliveryCost);
		
		return finalDeliveryCost;
	}
	
	//Apply discounts for eligible deliveries
	private double applyDiscount(double total) {
		if(total>=DeliveryValues.DISCOUNT_ELIGIBLE_LIMIT) {
			return total - (total*DeliveryValues.DISCCOUNT_RATE);
		}
		else
			return total;
	}

	//Check whether a discount is applied or not, and returning display information
	@Override
	public String checkDiscountStatus() {
		if(discount>0)
			return "(Congratulations! "+DeliveryValues.DISCCOUNT_RATE*100+"% Discount is applied for this delivery.) \n\t[Rs."+discount+" OFF from the cost of Rs."+deliveryCost+"]";
		return "(This is not eligible for any discount at the moment...)";
	}
	
	//Generate a detailed report which includes a number, data & time, preferred delivery option, and charge for each delivery
	public String getReport() {
		if(deliveriesCount>0 && !deliveries.isEmpty()) {
			double total = 0;
			String report = "\n\t\t\t****-----Delivery Report-----****\n"
							+"==============================================================================================\n"
							+"    No.\t|\tTimestamp\t|\tDelivery Mode\t | Distance\t\t| Fee\n"
							+ "----------------------------------------------------------------------------------------------\n";
			for(Map.Entry<String, Double> entry : deliveries.entrySet()) {
				report += "### "+entry.getKey()+"\t\t : Rs."+entry.getValue()+"\n";
				total += entry.getValue();
			}

			report += "----------------------------------------------------------------------------------------------\n\t\t\t\t\t\t\t\t\t"
					+ " Total = Rs."+total+"\n----------------------------------------------------------------------------------------------"+"\n";
			if(totalDiscounts>0) {
				report += "(Rs."+totalDiscounts+" worth total discounts have been applied.)"+"\n";
			}
			return report;
		}
		return "";
	}

}
