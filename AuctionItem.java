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
public class AuctionItem {
  private int ID;
  private String description, sellerID, auctionStatus, highestBidID;
  private double startPrice, highestBid;
  public AuctionItem(int newID, String newdescript, String newsellerID, double newSPrice){
    ID = newID;
    description = newdescript;
    sellerID = newsellerID;
    startPrice = newSPrice;
    auctionStatus = "Pending";
    highestBid = 0;
  }
  public AuctionItem(int newID, String newdescript, String newsellerID, double newSPrice, String status, double highestBid, String highestBidID){
    ID = newID;
    description = newdescript;
    sellerID = newsellerID;
    startPrice = newSPrice;
    auctionStatus = status;
    this.highestBid = highestBid;
    this.highestBidID = highestBidID;
  }
  public int getID(){
    return ID;
  }
  public String getDescription(){
    return description;
  }
  public String getSellerID(){
    return sellerID;
  }
  public double getStartPrice(){
    return startPrice;
  }
  public String getAuctionStatus(){
    return auctionStatus;
  }
  public double getHighestBid(){
    return highestBid;
  }
  public String getHighestBidID(){
    return highestBidID;
  }
  public void setAuctionStatus(String s){
    auctionStatus = s;
  }
  public boolean hasBid(){
    return (highestBid >= startPrice);
  }
  public double open() throws Exception{
    double Fee;
    if (auctionStatus == "Pending"){
      auctionStatus = "Open";
      if (startPrice <= 5){
        Fee = 0.2;
      }
      else if (startPrice <= 20){
        Fee = 0.5;
      }
      else if (startPrice <= 100){
        Fee = 1;
      }
      else if (startPrice <= 250){
        Fee = 2.5;
      }
      else{
        Fee = 5;
      }
      return Fee;
    }
    else{
      //return -1;
      throw new Exception("Auction "+ID+" cannot be opened because it is not pending");
    }
    
  }
  public double close() throws Exception {
    double Fee;
    if (auctionStatus == "Open"){
      auctionStatus = "Closed";
      if (highestBid < 1){
        Fee = 0;
      }
      else if (highestBid <= 100){
        Fee = 0.05 * highestBid;
      }
      else if (highestBid <= 1000){
        Fee = 5 + 0.03 * (highestBid - 100);
      }
      else {
        Fee = 32 + 0.01 * (highestBid - 1000);
      }
      return Fee;
    }
    else{
      throw new Exception("Auction "+ID+" cannot be close because it is not currently open!");
    }
  }
  public void placeBid(double bidAmount, String bidder) throws Exception{
    if (auctionStatus == "Open"){
      if ((bidAmount >= startPrice) && (bidAmount > highestBid)){
        highestBid = bidAmount;
        highestBidID = bidder;
        System.out.println("Your bid was successful");
      }
      else{
        //return -2;
        if (bidAmount < startPrice)
          throw new Exception("Error! Bid amount cannot be smaller than Starting price");
        else if (bidAmount < highestBid)
          throw new Exception("Error! Bid amount must be greater than Highest bid");
      }
    }
    else{
      throw new Exception("Error! Auction Status is not 'Open'");
    }
  }
  public void print(){
    System.out.println("Item ID: "+ID);
    System.out.println("Description: "+description); 
    System.out.println("Seller ID: "+sellerID); 
    System.out.println("Starting price: "+startPrice); 
    System.out.println("Auction Status: "+auctionStatus);
    System.out.println("Highest Bid: "+highestBid);
    System.out.println("Highest Bidder: "+highestBidID);
  }
}
