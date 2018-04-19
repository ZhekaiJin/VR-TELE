package read;
import item.Messege;
import item.Messege.MessegeBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

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
			//JSONObject data = new JSONObject();
			double timestamp = Double.parseDouble(tokens[0].split(":")[1]);
			double x = Double.parseDouble(tokens[1].split(":")[1]);
			double y = Double.parseDouble(tokens[2].split(":")[1]);
			double z = Double.parseDouble(tokens[3].split(":")[1]);
			double qw = Double.parseDouble(tokens[4].split(":")[1]);
			double qx = Double.parseDouble(tokens[5].split(":")[1]);
			double qy = Double.parseDouble(tokens[6].split(":")[1]);
			double qz = Double.parseDouble(tokens[7].split(":")[1]);
			
			//---------------------------------------------------
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
			if (euler != null) {
				builder.setPitch(euler[0]);
				builder.setRoll(euler[2]);
				builder.setYaw(euler[1]);
			} 
			//--------------------------------------------------
//			double[] euler = q_to_euler(qw_d,qx_d,qy_d,qz_d);
//			for (double angle :euler) {
//				System.out.println(angle);
//			}
//			System.out.println("-------");
//			try {
////				data.put("qw",qw);
////				data.put("qx",qx);
////				data.put("qy",qy);
////				data.put("qz",qz);
//				data.put("pitch",euler[0]);
//				data.put("yaw",euler[1]);
//				data.put("roll",euler[2]);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
			return builder.build().toJSONObject();
		}
		public void captureFrame(JSONObject data) {  // handler processing [ 
	        //if(!data.toString().equals("{}")) {
	            pubnub.publish(CHANNEL, data, new Callback() {});
	        //}

	    }
		
		private static double[] q_to_euler(double w, double x, double y, double z) {
			double yaw = Math.atan2(2.0*(y*z + w*x),w*w - x*x - y*y + z*z);
			double pitch = Math.asin(-2.0*(x*z - w*y));
			double roll = Math.atan2(2.0*(x*y + w*z), w*w + x*x - y*y - z*z);
//		    double sqw = w*w;
//		    double sqx = x*x;
//		    double sqy = y*y;
//		    double sqz = z*z;
//		    double heading = Math.atan2(2.0 * (x*y + z*w),(sqx - sqy - sqz + sqw));
//		    double bank = Math.atan2(2.0 * (y*z + x*w),(-sqx - sqy + sqz + sqw));
//		    double attitude = Math.asin(-2.0 * (x*z - y*w));
		    double[] euler= {yaw,pitch,roll};
		    return euler;
		}
		
		 public void run(){
		        for(;;) {
		            if (!running) break;
		           // System.out.println("successfully subscribed");
            		try {
            			   // System.out.println("procesing");
            	    		BufferedReader f = new BufferedReader(new FileReader(file));
            	    		String st;
            	    		while ((st = f.readLine()) != null) {
            	    			
                			    //System.out.println(st);
            	    			String[] tokens = st.split(",");
//            	    			for (String t : tokens) {
//            	    				System.out.println(t);
//            	    			}
            	    			if (tokens.length > 1) { // && tokens[8].equals("device: HMD")) {//added this to distinguish HMD from other controllers
            	    				 JSONObject data= toJSONObject(tokens);
            	    				 captureFrame(data);
            	    			}
            	    		}
            	    		f.close();
            	    	}catch (IOException e) {
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
		        // Create a controller
		        this.running = true;
		        Thread t = new Thread(this);
		        t.start();
		        // Keep this process running until Enter is pressed
		        System.out.println("Press Enter to quit...");
		        try {
		            System.in.read();
		            this.running=false;
		            t.join();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (InterruptedException e){
		            e.printStackTrace();
		        }
		 }
		 
	    public static void main(String[] args) throws IOException, JSONException {
	        String pubKey = "pub-c-012ea47d-f770-4c04-8176-1cd817b319b4";
	        String subKey = "sub-c-e1ee380e-d3b8-11e7-b83f-86d028961179";
	        reader s = new reader(pubKey, subKey);
	        s.startTracking();
	    	
	    }
}	
