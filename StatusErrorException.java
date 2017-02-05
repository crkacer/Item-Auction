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
public class StatusErrorException extends Exception {
  public StatusErrorException(String message){
    super(message);
  }
}
