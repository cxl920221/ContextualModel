package contextualmodel.cxl.cn.contextualmodel.base;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
	/** Notification���? */
	public NotificationManager mNotificationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initService();
	}

	/**
	 * ��ʼ��Ҫ�õ���ϵͳ�?�?
	 */
	private void initService() {
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	
	/** 
	 * ��?��ǰ������֪ͨ�� 
	 */
	public void clearNotify(int notifyId){
		mNotificationManager.cancel(notifyId);//ɾ�?һ���ض���֪ͨID��Ӧ��֪ͨ
//		mNotification.cancel(getResources().getString(R.string.app_name));
	}
	
	/**
	 * ��?����֪ͨ��
	 * */
	public void clearAllNotify() {
		mNotificationManager.cancelAll();// ɾ�?�㷢������֪ͨ
	}
	
	/**
	 * @��ȡĬ�ϵ�pendingIntent,Ϊ�˷�ֹ2.3�����°汾���?
	 * @flags����:  
	 * �ڶ�����פ:Notification.FLAG_ONGOING_EVENT  
	 * ���ȥ�?�� Notification.FLAG_AUTO_CANCEL 
	 */
	public PendingIntent getDefalutIntent(int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
		return pendingIntent;
	}
}
