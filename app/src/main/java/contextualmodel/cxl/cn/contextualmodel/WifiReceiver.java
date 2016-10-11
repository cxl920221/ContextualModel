package contextualmodel.cxl.cn.contextualmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
			// signal strength changed
		} else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {// wifi连接上与否
			//System.out.println("网络状态改变");
			Log.v("wifitest", "网络状态改变");
			NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
				//System.out.println("wifi网络连接断开");
				Log.v("wifitest", "wifi网络连接断开");
			} else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {

//		        WifiTool wifi = new WifiTool(context);
//		        String ip = "wifiManager:"+wifi.getWifiIp()+"\n java:"+ WifiTool.getLocalIpAddress();

				// 获取当前wifi名称
//				Log.v("wifitest", "连接到网络" + wifi.getWifiSSID());
				//tongzhilanchangzhu 10085455 20160620 start
				Intent i = new Intent(context, WifiService.class);
		        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		        i.putExtra("wifiname", wifi.getWifiSSID());
//		        i.putExtra("ip", ip);
//				i.putStringArrayListExtra("wifilist",wifi.getWifiSSIDList());
		        context.startService(i);
				//tongzhilanchangzhu 10085455 20160620 end

			}

		} else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {// wifi打开与否
			int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);

			if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
				//System.out.println("系统关闭wifi");
				Log.v("wifitest", "系统关闭wifi");
			} else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
				//System.out.println("系统开启wifi");
				Log.v("wifitest", "系统开启wifi");
			}
		}
	}
}
