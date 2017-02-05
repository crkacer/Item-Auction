/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package execution;

import java.io.*; 
import java.util.*; 


public class Execution {
  private static int indexItem = -1; 
  private static int indexCustomer = -1;
  private static AuctionItem[] itemArray = new AuctionItem[10];
  private static Customer[] customerArray = new Customer[50];
  private static Scanner sc = new Scanner("System.in");
  
  public static int checkCustomerID(String id){
    for (int i=0; i<50; i++){
      if (customerArray[i].getCustomerID().equals(id)){
        return i;
      }
    }
    return -1;
  }
  public static boolean checkCustomer(String id){
    for (int i=0; i<50; i++){
      if (customerArray[i].getCustomerID().equals(id));
        return true;
    }
    return false;
  }
  public static int checkExisted(int id){
    for (int i=0; i<10; i++){
      if (itemArray[i] != null)
        if (itemArray[i].getID() == id)
          return i;
    }
    return -1;
  }
  public static boolean checkBalance(int id){
    RegisteredCustomer chkCustomer = (RegisteredCustomer)customerArray[id];
    if (chkCustomer.getBalance() < 0){
      return false;
    } else
      return true;
  }
  public static int checkSeller(String sellerID){
    for (int i=0; i<50; i++){
      if (customerArray[i] != null)
        if (customerArray[i].getCustomerID() == sellerID)
          if (customerArray[i] instanceof RegisteredCustomer)
            return i;
    }
    return -1;
  }
  public static boolean checkItem(int id){
    for (int i=0; i<10; i++){
      if (itemArray[i] != null)
        if (itemArray[i].getID() == id)
          return false;
    }
    return true;
  }
  public static void addFunds(){
    System.out.print("Enter customer id you'd like to add fund: ");
    String cusId = sc.next();
    int check = checkCustomerID(cusId);
    if (check ==-1){
      System.out.println("Error! The customer id is not found");
    } else {
      if (customerArray[check] instanceof RegisteredCustomer){
        System.out.print("Enter funds you want to add: ");
        double amount = sc.nextDouble();
        RegisteredCustomer cust = (RegisteredCustomer) customerArray[check];
        cust.addFunds(amount);
      } else {
        System.out.println("Customer type is not Registered Customer");
        return;
      }
    }
  }  
  public static void displayCustomer(){
    for (int i=0; i<=indexCustomer; i++){
      if (customerArray[i] instanceof CasualCustomer){
        CasualCustomer cust = (CasualCustomer) customerArray[i];
        cust.print();
      } else if (customerArray[i] instanceof RegisteredCustomer){
        RegisteredCustomer cust = (RegisteredCustomer) customerArray[i];
        cust.print();
      }
    }
  }
  public static void addCustomer(){
    System.out.print("Enter customer id: ");
    String cusId = sc.next();
    System.out.print("Enter customer name: ");
    String cusName = sc.next();
    if (checkCustomerID(cusId)!=-1){
      System.out.println("Error! The customer id existed");
      return;
    } else{
      System.out.println("Which type of customer ([C]asual or [R]egistered): ");
      String type = sc.next();
      if (type.equals("C")){
        System.out.println("Enter credit card number: ");
        int cc = sc.nextInt();
        System.out.println("Enter PIN: ");
        int pin = sc.nextInt();
        indexCustomer ++;
        customerArray[indexCustomer] = new CasualCustomer(cusId, cusName, cc, pin);
      } else if (type.equals("R")){
        System.out.println("Enter starting account balance: ");
        double balance = sc.nextDouble();
        indexCustomer ++;
        customerArray[indexCustomer] = new RegisteredCustomer(cusId, cusName, balance);
      } else{
        System.out.println("Enter 'C' or 'R' only ");
        return;
      }
    }
  }
  public static void closeAuction() throws Exception{
    System.out.print("Enter the id of the auction to close: ");
    int itemId = sc.nextInt();
    int check = checkExisted(itemId);
    if (check == -1){
      System.out.println("The auction item with id "+itemId+" is not found! ");
      return;
    } else {
      if (itemArray[check] instanceof ReserveItem){
        ReserveItem rItem = (ReserveItem) itemArray[check];
        if (rItem.getHighestBid()<rItem.getReservePrice()){
          System.out.print("Would you like to lower the reserve price ('Y' or 'N'): ");
          String answer = sc.next();
          if (answer.equals("Y")){
            System.out.print("Enter a new reserve price: ");
            double newRPrice = sc.nextDouble();
            try{
              boolean b = rItem.lowerReserve(newRPrice);
            } catch (Exception ex){
              System.out.println(ex);
            }
          }
        }
      } else if(itemArray[check] instanceof AuctionItem){
        try{
          itemArray[check].close();
        } catch (Exception ex){
          System.out.println(ex);
          return;
        } finally{
          System.out.println("Auction "+itemId+" has ended - sale fee: "+itemArray[check].close());
        }
      }
    }
  }
  
  public static void placeBid() throws Exception{
    System.out.print("Enter the id of the auction to bid on: ");
    int auctionId = sc.nextInt();
    if (checkExisted(auctionId)== -1){
      System.out.println("The auction item with id "+auctionId+" is not found! ");
      return;
    } else {
      System.out.println("You are bidding in the auction for item "+itemArray[auctionId].getDescription());
      System.out.print("Enter bid amount: ");
      double amount = sc.nextDouble();
      System.out.print("Enter bidder id: ");
      String bidderId = sc.nextLine();
      if (!checkCustomer(bidderId)){
        System.out.println("Error! Bidder ID is not found");
        return;
      } else {
          try{
            itemArray[auctionId].placeBid(amount, bidderId);
          } catch (Exception ex){
              System.out.println(ex);
              return;
          } finally{
              itemArray[auctionId].print();
          }
      }
    }
  }
  
  public static void openAuction() throws Exception{
    System.out.print("Enter the id of the auction to open: ");
    int itemId = sc.nextInt();
    if (checkExisted(itemId)==-1){
      System.out.println("The auction item with id "+itemId+" is not found! ");
      return;
    } else {
        int custId;
        String seller = itemArray[itemId].getSellerID();
        for (custId =0; custId<50; custId++){
          if (customerArray[custId] != null)
            if (customerArray[custId].getCustomerID() == seller)
              break;
        }
        RegisteredCustomer regCust = (RegisteredCustomer) customerArray[custId];
        double openFee=0;
        try{
          openFee = itemArray[itemId].open();
        } catch (Exception ex){
          System.out.println(ex);
          return;
        } finally{
          regCust.deductFees(openFee);
          System.out.println("Auction "+itemId+" has started - listing fee: "+openFee);
        }
        
      }
  }
  
  
  public static void addAuction(){
    System.out.println("Adding a new item auction..."); 
    System.out.print("Enter item ID: ");
    int newID = sc.nextInt();
    if (!checkItem(newID)){
      System.out.println("Error - duplicate item ID entered");
      return;
    }
    System.out.print("Enter item description: ");
    String newDes = sc.nextLine();
    sc.nextLine();
    System.out.print("Enter seller ID: ");
    String newSellerID = sc.nextLine();
    int checkNumber = checkSeller(newSellerID);
    if (checkNumber == -1){
      System.out.println("Error - Casual Customer ID entered");
      return;
    } else {
      if (!checkBalance(checkNumber)){
        System.out.println("Error - Currently negative balance");
        return;
      }
    }
    System.out.print("Enter starting price: ");
    double newPrice = sc.nextDouble();
    System.out.print("Create a new AuctionItem or a ReserveItem? (enter A or R): ");
    String newAR = sc.nextLine();
    newAR = newAR.toUpperCase();
    int i = indexItem;
    if (newAR == "A"){
      indexItem ++;
      itemArray[i] = new AuctionItem(newID, newDes, newSellerID, newPrice);
    } else if (newAR == "R"){
      System.out.println("Enter reserve price: ");
      double newRPrice = sc.nextDouble();
      indexItem ++;
      itemArray[i] = new ReserveItem(newID, newDes, newSellerID, newPrice, newRPrice);
    }
  }
  public static void displayAuction(){
    for (int i=0; i<10; i++){
      if (itemArray[i] != null){
        if (itemArray[i] instanceof AuctionItem){
          itemArray[i].print();
        } else if (itemArray[i] instanceof ReserveItem){
          ReserveItem rItem = (ReserveItem)itemArray[i];
          rItem.print();
        }
      }
    }
  }
  public static void writeFile() throws IOException{
    PrintWriter output = new PrintWriter(new FileWriter("source.txt", false));
    output.println("ITEM:");
    for (int i=0; i<=indexItem; i++){
      if (itemArray[i] instanceof AuctionItem){
        AuctionItem a = (AuctionItem) itemArray[i];
        output.println(a.getID()+","+a.getDescription()+","+a.getSellerID()+","+a.getStartPrice()+","+a.getAuctionStatus()+","+a.getHighestBid()+","+a.getHighestBidID());
      } else if (itemArray[i] instanceof ReserveItem){
        ReserveItem a = (ReserveItem) itemArray[i];
        output.println(a.getID()+","+a.getDescription()+","+a.getSellerID()+","+a.getStartPrice()+","+a.getAuctionStatus()+","+a.getHighestBid()+","+a.getHighestBidID()+","+a.getReservePrice());
      }
    }
    output.println("CUSTOMER:");
    for (int i=0; i<=indexCustomer; i++){
      if (customerArray[i] instanceof CasualCustomer){
        CasualCustomer a = (CasualCustomer) customerArray[i];
        output.println(a.getCustomerID()+","+a.getCustomerName()+","+a.getCreditNumber()+","+a.getPIN());
      } else if (customerArray[i] instanceof RegisteredCustomer){
        RegisteredCustomer a = (RegisteredCustomer) customerArray[i];
        output.println(a.getCustomerID()+","+a.getCustomerName()+","+a.getBalance());
      }
    }
    output.close();
  }
  
  public static void readFile(){
    Scanner input = new Scanner("source.txt");
    
    String line;
    int pointer = 0;
    while (input.hasNextLine()){
      line = input.nextLine();
      StringTokenizer tokenString = new StringTokenizer(line,",");
      if (tokenString.countTokens() ==0){
        if (tokenString.equals("ITEM:")){
          pointer = 1;
          continue;
        } else if (tokenString.equals("CUSTOMER:")){
          pointer = 2;
          continue;
        }
      }
      if (pointer == 1){
        //read item array
        if (tokenString.countTokens()==6){
          indexItem ++;
          int id = Integer.parseInt(tokenString.nextToken());
          String desc = tokenString.nextToken();
          String seller = tokenString.nextToken();
          double sprice = Double.parseDouble(tokenString.nextToken());
          String status = tokenString.nextToken();
          double highestBid = Double.parseDouble(tokenString.nextToken());
          String highestBidID = tokenString.nextToken();
          itemArray[indexItem] = new AuctionItem(id,desc,seller,sprice,status,highestBid,highestBidID);
        } else if (tokenString.countTokens()==7){
          indexItem ++;
          int id = Integer.parseInt(tokenString.nextToken());
          String desc = tokenString.nextToken();
          String seller = tokenString.nextToken();
          double sprice = Double.parseDouble(tokenString.nextToken());
          String status = tokenString.nextToken();
          double highestBid = Double.parseDouble(tokenString.nextToken());
          String highestBidID = tokenString.nextToken();
          double rprice = Double.parseDouble(tokenString.nextToken());
          itemArray[indexItem] = new ReserveItem(id,desc,seller,sprice,status,highestBid,highestBidID,rprice);
        }
      } else if (pointer == 2){
        //read customer array
        if (tokenString.countTokens() == 3){
          indexCustomer ++;
          String id = tokenString.nextToken();
          String name = tokenString.nextToken();
          int credit = Integer.parseInt(tokenString.nextToken());
          int pin = Integer.parseInt(tokenString.nextToken());
          customerArray[indexCustomer] = new CasualCustomer(id,name,credit,pin);
        } else if (tokenString.countTokens() == 2){
          indexCustomer ++;
          String id = tokenString.nextToken();
          String name = tokenString.nextToken();
          double balance = Double.parseDouble(tokenString.nextToken());
          customerArray[indexCustomer] = new RegisteredCustomer(id,name,balance);
        }
      }
    }
    input.close();
  }
  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    String option;
    
    do{
      System.out.println("*** iBuy Auction Recording System *********");
      System.out.println("A - Add Auction");
      System.out.println("B - Display Auction Details");
      System.out.println("C - Open Auction");
      System.out.println("D - Place Bid");
      System.out.println("E - Close Auction");
      System.out.println("F – Add Customer");
      System.out.println("G – Display Customer Details");
      System.out.println("H – Add Funds to Account");
      System.out.println("X - Exit the Program");
      System.out.println("Enter you selection:");
      option = sc.next();
      option = option.toUpperCase();
      readFile();
      switch (option){
        case "A":{
          addAuction();
          break;
        }
        case "B":{
          displayAuction();
          break;
        }
        case "C":{
          openAuction();
          break;
        }
        case "D":{
          placeBid();
          break;
        }
        case "E":{
          closeAuction();
          break;
        }
        case "F":{
          addCustomer();
          break;
        }
        case "G":{
          displayCustomer();
          break;
        }
        case "H":{
          addFunds();
          break;
        }
        case "X":{
          writeFile();
          System.exit(0);
          break;
        }
        default:{
          break;
        }
      }
    } while (true); 
  }
  
}
