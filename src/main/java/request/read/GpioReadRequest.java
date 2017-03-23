/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package request.read;
import java.io.IOException;
import net.ProtocolManager;
import request.manager.GpioManager;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public class GpioReadRequest implements ReadRequest {
    private static final GpioReadRequest INSTANCE = new GpioReadRequest();
    private static String pinName;
    
    private GpioReadRequest() {
    }
    
    public static GpioReadRequest getInstance(String pinName) {
        return INSTANCE;
    }
    
    public static void setPinName(String pinName) {
        GpioReadRequest.pinName = pinName;
    }

    /**
     * Attempts to read input denoted in getInstance() method and if successful, 
     * returns the pin numeric value as a result. String containing number is
     * returned due to interface which this class implements. 
     * @return String numeric representation of the read signal
     */
    @Override
    public String read() {
        return GpioManager.readVoltage(pinName) ? "1" : "0";
    }

    @Override
    public void giveFeedbackToClient() throws IOException {
        ProtocolManager.getInstance().setMessageToSend(String.format(
                "Pin '%s' is currently %s", pinName, 
                Integer.parseInt(read()) == 0 ? "off" : "on"));
    }
}
