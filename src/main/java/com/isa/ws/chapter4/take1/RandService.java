package com.isa.ws.chapter4.take1;

import java.util.Random;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class RandService {
	private static final int Max_Rands = 16;

	@WebMethod
	public int next1() {
		Random random = new Random();
		return random.nextInt();
	}

	@WebMethod
	public int[] nextN(int n) {
		int k = (n > Max_Rands) ? Max_Rands : Math.abs(n);
		int[] result = new int[k];
		Random random = new Random();
		for (int i = 0; i < k; i++) {
			result[i] = random.nextInt();
		}

		return result;
	}

}
