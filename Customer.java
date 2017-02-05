/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package execution;

/**
 *
 * @author willie
 */
public abstract class Customer {
  private String ID, name;
  public Customer(String ID, String name){
    this.ID = ID;
    this.name = name;
  }
  public String getCustomerID(){
    return ID;
  }
  public String getCustomerName(){
    return name;
  }
  public abstract void completeSale(double price);
  
  public void print(){
    System.out.println("Customer ID: "+ID);
    System.out.println("Customer Name: "+name);
  }
}
