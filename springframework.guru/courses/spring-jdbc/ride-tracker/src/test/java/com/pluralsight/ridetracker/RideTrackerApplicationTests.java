package com.pluralsight.ridetracker;

import com.pluralsight.ridetracker.model.Ride;
import com.pluralsight.ridetracker.service.RideService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RideTrackerApplicationTests {

	@Autowired
	private RideService rideService;

	@Test
	public void contextLoads() {
	}


	@Test
	public void testCreateRide() {
		Ride ride = new Ride();
		ride.setName("Sagebrush Trail");
		ride.setDuration(33);


		ride = rideService.createRide(ride);

		System.out.println("Ride: " + ride);
	}

}

