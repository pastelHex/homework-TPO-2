/**
 *
 *  @author Zesławska Anna S14211
 *
 */

package zad1;

import controller.Controller;

public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI
    Controller c = new Controller();
  }
}
