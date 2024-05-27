package osgi.deliveryconsumer;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import osgi.deliveryproducer.DeliveryServicePublish;
import osgi.deliveryproducer.DeliveryValues;

public class ServiceActivator implements BundleActivator {

	ServiceReference serviceReference;

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Start Subscriber Service.");
		
		serviceReference = bundleContext.getServiceReference(DeliveryServicePublish.class.getName());
		
		@SuppressWarnings("unchecked")
		DeliveryServicePublish deliveryServicePublish = (DeliveryServicePublish) bundleContext.getService(serviceReference);
		System.out.println(deliveryServicePublish.publishDeliveryService());
		
		boolean repeatProcess = false;
		
		Scanner scanner = new Scanner(System.in);
		
		//Continuously getting deliveries until consumer stops the loop.
		while(!repeatProcess) {
			boolean validDistance = false;
			//Getting a valid distance as a user input
			while(!validDistance) {
				try {
					System.out.print("\nEnter the distance to your location from Colombo (km): ");
					double distance = scanner.nextDouble();
					scanner.nextLine();
					
					if(distance>0) {
						validDistance = true;
						deliveryServicePublish.setDeliveryDistance(distance);
					}
					else {
						System.out.println("... Distance should be greater than 0 ...");
					}
				} catch(InputMismatchException e) {
					System.out.println("Input Mismatch! Please enter a valid distance.");
					scanner.nextLine();
				}
			}
			
			// Special delivery feature check
			System.out.print("Do you need Express Delivery? (+Rs."+DeliveryValues.EXPRESS_DELIVERY_SPECIAL_CHARGE+") (Y/N): ");
			String needExpressDelivery = scanner.nextLine();
			boolean expressDelivery = false;
			System.out.println("--------------------------------------------------------------");
			if(needExpressDelivery.toLowerCase().equals("y") || needExpressDelivery.equals("1") || needExpressDelivery.toLowerCase().equals("yes")) {
				expressDelivery = true;
				System.out.println("\n\t  *** EXPRESS DELIVERY ***");
			}
			else {
				System.out.println("\n\t  *** STANDARD DELIVERY ***");
			}
			
			double deliveryCharge = deliveryServicePublish.calculateTotalDeliveryCost(expressDelivery);
			if(deliveryCharge==0)
				System.out.println("\n\t# Your Delivery is FREE! #");
			else {
				System.out.println();
				System.out.println(deliveryServicePublish.checkDiscountStatus());
				System.out.println("\n\tTotal Delivery Cost: Rs. "+deliveryCharge+"\n\t================================");
			}
			
			System.out.println("\n--------------------------------------------------------------");
			System.out.print("New Delivery? (Y/N) : ");
			String initiateNewDelivery = scanner.nextLine().toLowerCase();
			
			if(!(initiateNewDelivery.equals("y") || initiateNewDelivery.equals("yes") || initiateNewDelivery.equals("1"))) {
				repeatProcess = true;
				System.out.println(deliveryServicePublish.getReport());;
			}
			else {
				System.out.println("--------------------------------------------------------------");
			}
		}
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Delivery Service Consumer Stopped. \nGood Bye!");
		bundleContext.ungetService(serviceReference);
	}

}
