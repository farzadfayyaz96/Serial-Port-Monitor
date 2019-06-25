import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
        System.out.println("serial port listener v1");
        SerialPort selectedPort;
        while (true){
            //get active port from jSerialComm library
            SerialPort[] portArray = SerialPort.getCommPorts();
            if(portArray.length == 0){
                System.out.println("port not found!!");
                System.out.println("connect port to device or exit form program.");

                while (true){
                    System.out.print("do you want to exit (y/n) ? ");
                    try {
                        char selectedChar = new Scanner(System.in).nextLine().charAt(0);
                        if(selectedChar == 'n'){
                            break;//break from "port not found" while and start app again.
                        }
                        else if(selectedChar == 'y'){
                            //exit from program.
                            System.exit(0);
                        }
                        //input not valid.
                        else throw new Exception();

                    }catch (Exception e){
                        System.out.println("input not valid ! try again.");
                        System.out.println();
                    }
                }//end "port not found" while true.
                //start program again.
                continue;
            }

            //print port list to show user.
            System.out.println("port list : ");
            for(int i =0 ; i < portArray.length ; i++){
                SerialPort port = portArray[i];
                System.out.println((i+1)+"."+port.getSystemPortName());
            }
            //get port range
            String portRangeText;
            if(portArray.length == 1) {
                portRangeText = "( 1 )";
            }
            else {
                portRangeText = " ( 1 to "+ portArray.length +" ) ";
            }
            System.out.print("select port number "+portRangeText+": ");
            //get get port number from user
            try {
                int selectedIndex = new Scanner(System.in).nextInt();
                if(selectedIndex <=0 || selectedIndex > portArray.length){
                    throw new Exception();
                }
                //set selected port
                selectedPort = portArray[selectedIndex - 1];
                break;//exit from select port
            }catch (Exception e){
                System.out.println("input not valid ! try again.");
                System.out.println();
            }
        }//end select port true while
        System.out.println();
        System.out.println("start to connect to " + selectedPort.getSystemPortName() + "...");
        selectedPort.openPort();
        //
        selectedPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }
            @Override
            public void serialEvent(SerialPortEvent event)
            {
                byte[] newData = event.getReceivedData();
                for (int i = 0; i < newData.length; ++i)
                    System.out.print((char)newData[i]);
                System.out.println();
            }
        });
        System.out.println("connected to "+ selectedPort.getSystemPortName()+".");
        System.out.println();
        System.out.println(selectedPort.getSystemPortName()+" serial port data :");
        System.out.println();


    }

}
