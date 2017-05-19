package com.isa.security.https;

import com.sun.net.httpserver.HttpExchange;

public interface IService {
  void doGet(HttpExchange e);

  void doPost(HttpExchange e);

  void doPut(HttpExchange e);

  void doDelete(HttpExchange e);
}
