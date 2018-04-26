package read;
import item.Messege.MessegeBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.profesorfalken.jpowershell.PowerShell;
import com.pubnub.api.*;


public class reader implements Runnable {
	 private static int count = 0 ;
	 public static final String CHANNEL = "scott";
	 private File file = new File("C:\\Users\\special_lab\\Documents\\openvr\\openvr\\samples\\bin\\win32\\output.txt");

	 private Pubnub pubnub;   
	 private boolean running;
	 public reader(String pubKey, String subKey){
	        pubnub = new Pubnub(pubKey, subKey);
	        pubnub.setUUID("LeapController");
	        try {
	            pubnub.subscribe(CHANNEL, new Callback() {
	                        @Override
	                        public void connectCallback(String channel, Object message) {
	                       
	                        }

	                        @Override
	                        public void disconnectCallback(String channel, Object message) {
	                            System.out.println("SUBSCRIBE : DISCONNECT on channel:" + channel
	                                    + " : " + message.getClass() + " : "
	                                    + message.toString());
	                        }

	                        @Override
							public void reconnectCallback(String channel, Object message) {
	                            System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
	                                    + " : " + message.getClass() + " : " + message.toString());
	                        }

	                        @Override
	                        public void successCallback(String channel, Object message) {
	                        			//System.out.println("Debug:We are on Track");
	                        }

	                        @Override
	                        public void errorCallback(String channel, PubnubError error) {
	                            System.out.println("SUBSCRIBE : ERROR on channel " + channel
	                                    + " : " + error.toString());
	                        }
	                    }
	            );
	        } catch (PubnubException e) {
	            System.out.println(e.toString());
	        }
	    }

		private static JSONObject toJSONObject (String[] tokens) throws JSONException {
			double timestamp = Double.parseDouble(tokens[0]);
			double x = Double.parseDouble(tokens[1].split(":")[1]);
			double y = Double.parseDouble(tokens[2].split(":")[1]);
			double z = Double.parseDouble(tokens[3].split(":")[1]);
			double qw = Double.parseDouble(tokens[4].split(":")[1]);
			double qx = Double.parseDouble(tokens[5].split(":")[1]);
			double qy = Double.parseDouble(tokens[6].split(":")[1]);
			double qz = Double.parseDouble(tokens[7].split(":")[1]);
			String deviceName = tokens[8].split(":")[1];
			double[] euler = q_to_euler(qw,qx,qy,qz);
			MessegeBuilder builder = new MessegeBuilder();
			builder.setTimestamp(timestamp);
			builder.setDeviceId(++count);  // for log purpose  
			builder.setX(x);
			builder.setY(y);
			builder.setZ(z);
			builder.setQx(qx); 
			builder.setQw(qw);
			builder.setQz(qz);
			builder.setQy(qy);
			builder.setDeviceName(deviceName);
			if (euler != null) {
				builder.setPitch(euler[0]);
				builder.setYaw(euler[1]);
				builder.setRoll(euler[2]);
			} 
			return builder.build().toJSONObject();
		}
		
		private static double[] q_to_euler(double w, double x, double y, double z) {
			double yaw = Math.atan2(2.0*(y*z + w*x),w*w - x*x - y*y + z*z);
			double pitch = Math.asin(-2.0*(x*z - w*y));
			double roll = Math.atan2(2.0*(x*y + w*z), w*w + x*x - y*y - z*z);
		    double[] euler= {yaw,pitch,roll};
		    return euler;
		}
		
		 @Override
		public void run(){
			  	PowerShell.executeSingleCommand("cd \\Users\\special_lab\\Documents\\openvr\\openvr\\samples\\bin\\win32; .\\hellovr_opengl.exe");
		        for(;;) {
		            if (!running) break;
            		try {
            	    		BufferedReader f = new BufferedReader(new FileReader(file));
            	    		String st;
            	    		while ((st = f.readLine()) != null) {
               	    			String[] tokens = st.split(",");
            	    			if (tokens.length > 1) { 
            	    				 JSONObject data= toJSONObject(tokens);
            	    				 System.out.println(data.toString(4)); // Print it with specified indentation
            	    		         pubnub.publish(CHANNEL, data, new Callback() {});
            	    			}
            	    		}
            	    		f.close();
        	    	} catch (IOException e) {
        	    		e.printStackTrace();
        	    	} catch (JSONException e) {
						e.printStackTrace();
					}
		            try {
		                Thread.sleep(50);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }


		public void startTracking(){
		        this.running = true;
		        Thread t = new Thread(this);
		        t.start();
		        System.out.println("Press Enter to quit...");
		        try {
		            System.in.read();//async
		            this.running=false;
		            t.join();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (InterruptedException e){
	                System.out.println("you hit enter");
		            e.printStackTrace();
		        } finally {
		            PowerShell.executeSingleCommand("Stop-Process -Name \"hellovr_opengl\"");
		            System.out.println("you are now fully quit");
		        }
		 }
		 
	    public static void main(String[] args) throws IOException, JSONException {
	        String pubKey = "pub-c-012ea47d-f770-4c04-8176-1cd817b319b4";
	        String subKey = "sub-c-e1ee380e-d3b8-11e7-b83f-86d028961179";
	        reader s = new reader(pubKey, subKey);
	        s.startTracking();
	    	
	    }
}	
