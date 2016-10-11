package contextualmodel.cxl.cn.contextualmodel;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

class WifiTool {

	private WifiManager wifiManager = null;
	private WifiInfo wifiInfo = null;
	WifiTool(Context context) {
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiInfo = wifiManager.getConnectionInfo();
	}

	/**
	 * 获取ip
	 * @return ip地址
     */
	 String getWifiIp() {
		String ip = "wifi is null!!";
		if (isNull()) {
			int ipAddress = wifiInfo.getIpAddress();
			ip = intToIp(ipAddress);
		}
		return ip;
	}

	/**
	 * 获取SSID
	 * @return SSID
	 */
	String getWifiSSID() {
		String ssid = "wifi is null!!";
		if (isNull()) {
			ssid = wifiInfo.getSSID();
		}
		return ssid;
	}

	ArrayList<String> getWifiSSIDList() {
		List<ScanResult> list;
		ArrayList<String> returnList = new ArrayList<>();
		if (isNull()) {
			list = wifiManager.getScanResults();
			for (ScanResult scanResult : list) {
				if (scanResult.SSID != null && !"".equals(scanResult.SSID)) {
					returnList.add(scanResult.SSID);
				}
			}
		}
		return returnList;
	}

	private boolean isNull() {
		boolean flag = false;
		if (wifiManager != null) {
			flag = true;
		}
		return flag;
	}
	private String intToIp(int i) {
		return (i & 0xFF ) + "." +
				((i >> 8 ) & 0xFF) + "." +
				((i >> 16 ) & 0xFF) + "." +
				( i >> 24 & 0xFF) ;
	}

	static String getLocalIpAddress() {
		try {
			Log.v("8011--", "001");
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				Log.v("8011--", "Name:");
				Log.v("8011--", "Name:"+intf.getName());
				Log.v("8011--", "getNetworkInterfaces:"+ NetworkInterface.getNetworkInterfaces());
				Log.v("8011--", "Display Name:"+intf.getDisplayName());
				Log.v("8011--", "Name--:");
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					Log.v("8011--", "inetAddress:"+inetAddress.getHostAddress());
					if (!inetAddress.isLoopbackAddress()
							&& !inetAddress.isLinkLocalAddress()) {
						if (inetAddress instanceof Inet4Address){
							Log.v("8011--", "returninetAddress:"+inetAddress.getHostAddress());
						}else{
							Log.v("8011--", "isipv6");
						}
//						Log.v("8011--", "isipv6:"+InetAddressUtils.isIPv4Address(inetAddress.getHostAddress().toString()));
//						if (InetAddressUtils
//								.isIPv6Address(inetAddress
//										.getHostAddress().toString().split("%")[0])) {
//
//						}else{
//
//
//						}
						return inetAddress.getHostAddress() +" \n Name:"+intf.getName();
					}
				}
			}
		} catch (SocketException ex) {
			Log.v("8011--", "002");
			ex.printStackTrace();
		}
		Log.v("8011--", "003");
		return null;
	}
}
