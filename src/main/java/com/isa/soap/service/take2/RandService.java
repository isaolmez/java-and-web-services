package com.isa.ws.chapter4.take2;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface RandService {

	@WebMethod
	int next1();

	@WebMethod
	int[] nextN(int n);

}
