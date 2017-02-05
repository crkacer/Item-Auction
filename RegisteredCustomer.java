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
public class RegisteredCustomer extends Customer{
  private double balance;
  public RegisteredCustomer(String ID, String name, double startBalance){
    super(ID, name);
    balance = startBalance;
  }
  public double getBalance(){
    return balance;
  }
  public void addFunds(double amount){
    balance += amount;
  }
  public void deductFees(double fees){
    balance -= fees;
  }
  @Override
  public void completeSale(double price){
    balance -= price;
  }
  @Override
  public void print(){
    super.print();
    System.out.println("Balance: "+balance);
  }
}
