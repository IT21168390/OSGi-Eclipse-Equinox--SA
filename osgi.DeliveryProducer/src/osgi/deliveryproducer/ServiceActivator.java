package osgi.deliveryproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class ServiceActivator implements BundleActivator {

	ServiceRegistration deliveryServiceRegistration;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Delivery Service Publisher Started.");
		DeliveryServicePublish deliveryPublisherService = new DeliveryServicePublishImpl();
		
		deliveryServiceRegistration = bundleContext.registerService(DeliveryServicePublish.class.getName(), deliveryPublisherService, null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Delivery Service Publisher Stopped.");
		deliveryServiceRegistration.unregister();
	}

}
