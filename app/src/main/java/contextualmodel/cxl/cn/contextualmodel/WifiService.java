package contextualmodel.cxl.cn.contextualmodel;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.util.Log;

import java.util.ArrayList;

public class WifiService extends Service {

    int notifyId = 100;
    AudioTool at = null;
    SharedPreferences pre;
    WifiTool wifi;
    String ip;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //String wifiname = intent.getStringExtra("wifiname");
        //String ip = intent.getStringExtra("ip");
        //ArrayList<String> wifiList = intent.getStringArrayListExtra("wifilist");

        init();
        showCzNotify(wifi.getWifiSSID(), ip);
        //之后的修改方向:插件化,并且可以指定开启关闭功能  功能判断通过checkJobflag方法实现
        //但本次未实现,仅实现了情景状态功能的控制
        checkJobflag();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 初始化
     */
    private void init() {
        pre = getSharedPreferences("mode",MODE_PRIVATE);
        at = new AudioTool(WifiService.this);
        wifi = new WifiTool(WifiService.this);
        ip = "wifiManager:"+wifi.getWifiIp()+"\n java:"+ WifiTool.getLocalIpAddress();
    }

    /**
     * 判断工作状态
     */
    private void checkJobflag() {
        if (pre.getBoolean("jobflag",false)){
            String jobwifi = pre.getString("jobssid","");
            String unjobwifi = pre.getString("unjobssid","");
            if (jobwifi.length() >0){
                setCM(checkWifi(wifi.getWifiSSIDList(),jobwifi,unjobwifi));
            }
        }
    }

    /**
     * 通过ssid判断现在的位置,改变情景模式
     * @param wifiList 获取到的SSID列表
     * @param jobwifi 工作用的SSID
     * @param unjobwifi 非工作用的SSID
     * @return 情景模式flag
     */
    private boolean checkWifi(ArrayList<String> wifiList, String jobwifi, String unjobwifi) {
        boolean flag = true;
        if(wifiList != null){
            for (String wifiname: wifiList) {
                if (wifiname.equalsIgnoreCase(jobwifi)){
                    flag = false;
                    break;
//                }else if (wifiname.equalsIgnoreCase(jobwifi)){
//                    flag = true;
//                    break;
                }else if (wifiname.equalsIgnoreCase(unjobwifi)){
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }


    //tongzhilanchangzhu 10085455 20160620 start

    /**
     * 在通知栏显示wifi信息
     * @param wifiname SSID
     * @param ip 当前ip
     */
    public void showCzNotify(String wifiname, String ip) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        PendingIntent pendingIntent = PendingIntent.getActivity(WifiService.this, 0, intent, 0);
        BigTextStyle BigTextStyle = new BigTextStyle();
        BigTextStyle.bigText(ip);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("0")
                .setContentTitle(wifiname)
                .setContentText(ip)
                .setStyle(BigTextStyle)
                .setContentIntent(pendingIntent);
        Notification mNotification = mBuilder.build();
        //mNotification.icon = R.drawable.common_ic_googleplayservices;
        //mNotification.
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
        mNotification.tickerText = getText(R.string.ticker);
        mNotification.when = System.currentTimeMillis();
        mNotificationManager.notify(notifyId, mNotification);

    }
    //tongzhilanchangzhu 10085455 20160620 end

    //情景模式变更 10085455 20160928 start
    /*
     *设置情景模式
     */
    public void setCM(boolean flag){
        if (at != null){
            at.noRingAndVibrate(flag);
        }
    }
    //情景模式变更 10085455 20160928 end


}
