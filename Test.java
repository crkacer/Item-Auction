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
public class Test {
  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    AuctionItem[] itemArray = new AuctionItem[4];
    itemArray[0] = new AuctionItem(1, "Tonka Truck", "M001", 1.00);
    itemArray[1] = new AuctionItem(2, "Xbox", "M002", 20.00);
    itemArray[2] = new AuctionItem(3, "Teddy Bear", "M003", 5.00);
    itemArray[3] = new AuctionItem(4, "Antique Doll", "M004", 100.00);
    /*===========================================================*/
    System.out.println("Available Item List:");
    for (int i=0; i<4; i++){
      System.out.println("Item ID: "+itemArray[i].getID()+" Desc: "+itemArray[i].getDescription()+" Start Price: "+itemArray[i].getStartPrice());
    }
    /*===========================================================*/
    for (int i=0; i<4; i++){
      System.out.println("Auction "+(i+1)+" has started - listing fee: "+itemArray[i].open());
    }
    
    /*===========================================================*/
    System.out.println("Enter the id of the auction to bid on:");
    int id = sc.nextInt();
    int checked = -1;
    for (int i=0; i<4; i++){
      if (itemArray[i].getID() == id){
        checked = i;
      }
    }
    if (checked == -1){
      System.out.println("Error! Could not find the ID");
    } else {
      System.out.println("You are bidding in the auction for an "+itemArray[checked].getDescription());
      System.out.print("Enter bid amount:");
      double price = sc.nextDouble();
      System.out.println("Enter bidder id:");
      String bidder = sc.nextLine();
      itemArray[checked].placeBid(price, bidder);
        System.out.println("Your bid was successful!");
        itemArray[checked].print();
      
        System.out.println("Invalid bid for auction "+id);
      }
    }
    /*===========================================================*/
    for (int i=0; i<4; i++){
      System.out.println("Auction "+itemArray[i].getID()+" has ended - sale fee: "+itemArray[i].close());
    }
  }
}
