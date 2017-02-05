/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package execution;

import java.util.Scanner;

/**
 *
 * @author willie
 */
public class CasualCustomer extends Customer{
  private int creditCard, PIN;
  private Scanner sc = new Scanner(System.in);
  public CasualCustomer(String ID, String name, int creditNumb, int PINNumb){
    super(ID, name);
    creditCard = creditNumb;
    PIN = PINNumb;
  }
  public int getCreditNumber(){
    return creditCard;
  }
  public int getPIN(){
    return PIN;
  }
  @Override
  public void completeSale(double price){
    System.out.println("Please enter your PIN: ");
    int custPIN = sc.nextInt();
    if (PIN == custPIN){
      System.out.println("The sale price has been charge to credit card: "+creditCard);
    } else {
      System.out.println("The item has been withheld pending confirmation of PIN");
    }
  }
  @Override
  public void print(){
    super.print();
    System.out.println("Credit Card Number: "+creditCard);
    System.out.println("PIN: "+PIN);
  }
}
