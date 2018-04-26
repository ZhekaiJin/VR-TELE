public class Messege {
	private double timestamp;
	private double DeviceId;
	private String name;
	private double x;
	private double y;
	private double z;
	private double qw;
	private double qx;
	private double qy;
	private double qz;
	private double pitch;
	private double yaw;
	private double roll;
	private String DeviceName;

	public String getDeviceName() {
		return DeviceName;
	}
	public double getPitch() {
		return pitch;
	}
	public double getYaw() {
		return yaw;
	}
	public double getRoll() {
		return roll;
	}
	public double getTimestamp() {
		return timestamp;
	}
	public double getDeviceId() {
		return DeviceId;
	}
	public String getName() {
		return name;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
	public double getQw() {
		return qw;
	}
	public double getQx() {
		return qx;
	}
	public double getQy() {
		return qy;
	}
	public double getQz() {
		return qz;
	}
	
	public static class MessegeBuilder {
		private double timestamp;
		private double DeviceId;
		private String name;
		private double x;
		private double y;
		private double z;
		private double qw;
		private double qx;
		private double qy;
		private double qz;
		private double pitch;
		private double yaw;
		private double roll;
		private String DeviceName;

		public void setPitch(double pitch) {
			this.pitch = pitch;
		}
		public void setYaw(double yaw) {
			this.yaw = yaw;
		}
		public void setRoll(double roll) {
			this.roll = roll;
		}
		public void setTimestamp(double timestamp) {
			this.timestamp = timestamp;
		}
		public void setDeviceId(double deviceId) {
			DeviceId = deviceId;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setX(double x) {
			this.x = x;
		}
		public void setY(double y) {
			this.y = y;
		}
		public void setZ(double z) {
			this.z = z;
		}
		public void setQw(double qw) {
			this.qw = qw;
		}
		public void setQx(double qx) {
			this.qx = qx;
		}
		public void setQy(double qy) {
			this.qy = qy;
		}
		public void setQz(double qz) {
			this.qz = qz;
		} 
		
		public void setDeviceName(String deviceName) {
			DeviceName = deviceName;
		}
		public Messege build() {
			return new Messege(this);
		}
	}
	
	private Messege(MessegeBuilder builder) {
		this.DeviceId = builder.DeviceId;
		this.timestamp = builder.timestamp;
		this.name = builder.name;
		this.x = builder.x;
		this.y = builder.y;
		this.z = builder.z;
		this.qx = builder.qx;
		this.qy = builder.qy;
		this.qz = builder.qz;
		this.qw = builder.qw;
		this.pitch = builder.pitch;
		this.yaw = builder.yaw;
		this.roll = builder.roll;
		this.DeviceName = builder.DeviceName;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("timestamp", timestamp);
			obj.put("DeviceId", DeviceId);
			obj.put("qx", qx);
			obj.put("qy", qy);
			obj.put("qz", qz);
			obj.put("qw", qw);
			obj.put("name", name);
			obj.put("x", x);
			obj.put("y", y);
			obj.put("z", z);
			obj.put("pitch",pitch);
			obj.put("yaw",yaw);
			obj.put("roll",roll);
			obj.put("DeviceName", DeviceName);
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		return obj;
	} 
}
