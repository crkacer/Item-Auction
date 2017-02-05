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
public class ReserveItem extends AuctionItem {
  private double reservePrice;
  public ReserveItem(int newID, String newdescript, String newsellerID, double newSPrice, double newRPrice){
    super(newID, newdescript, newsellerID, newSPrice);
    reservePrice = newRPrice;
  }
  public ReserveItem(int newID, String newdescript, String newsellerID, double newSPrice, String status, double highestBid, String highestBidID, double newRPrice){
    super(newID, newdescript, newsellerID, newSPrice,status, highestBid, highestBidID);
    reservePrice = newRPrice;
  }
  public double getReservePrice(){
    return reservePrice;
  }
  @Override
  public String getAuctionStatus(){
    if (super.getAuctionStatus()=="Closed"&&super.hasBid()&&super.getHighestBid()<reservePrice){
      return "Passed in";
    } else {
      return super.getAuctionStatus(); 
    }
  }
  @Override
  public double open() throws Exception{
    double Fee;
    if (super.getAuctionStatus() == "Pending"){
      super.setAuctionStatus("Open");
      if (reservePrice <= 1){
        Fee = 0;
      }
      else if (reservePrice <= 100){
        Fee = 0.04 * reservePrice;
      }
      else if (reservePrice <= 1000){
        Fee = 4 + 0.02 * (reservePrice - 100);
      } else{
        Fee = 22 + 0.03 * (reservePrice - 1000);
      }
      return Fee;
    }
    else{
      throw new Exception("Error! Auction Status is not 'Pending'");
    }
  }
  @Override
  public double close() throws Exception{
    if (getAuctionStatus() == "Passed in"){
      return 0.02 * reservePrice;
    } else {
      return super.close();
    }
  }
  public boolean lowerReserve(double newReservePrice) throws Exception{
    if (getAuctionStatus().equals("Open") && newReservePrice < reservePrice){
      return true;
    } else {
      if (!getAuctionStatus().equals("Open"))
        throw new Exception("Auction status is not 'Open'");
      if (newReservePrice >= reservePrice)
        throw new Exception("New reserve price is not smaller than current reserve price");
      return false;
    }
  }
  @Override
  public void print(){
    super.print();
    System.out.println("Reserve Price: "+reservePrice);
  }
}
