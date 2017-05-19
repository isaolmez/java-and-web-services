package com.isa.ws.chapter4.client;

import java.util.List;

import com.isa.ws.chapter4.client.generated.RandService;
import com.isa.ws.chapter4.client.generated.RandServiceImplService;

public class RandClient {
	public static void main(String[] args) {
		RandServiceImplService randService = new RandServiceImplService();
		RandService randServiceImplPort = randService.getRandServiceImplPort();
		randServiceImplPort.next1();
		System.out.println(randServiceImplPort.next1());
		List<Integer> result = randServiceImplPort.nextN(10);
		for (int i : result) {
			System.out.println(i);
		}
	}
}
