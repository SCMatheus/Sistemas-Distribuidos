/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserviceadmin;

import javax.xml.ws.Endpoint;
import webserviceecommerce.ClientServerImpl;

/**
 *
 * @author mathe
 */
public class AdminServerPublisher {
    public static void main(String[] args)
  {
    Endpoint.publish("http://127.0.0.1:7778/webservice", new AdminServerImpl());
  }
}
