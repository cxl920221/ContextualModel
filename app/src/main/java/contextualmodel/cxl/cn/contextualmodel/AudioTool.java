package contextualmodel.cxl.cn.contextualmodel;

import android.content.Context;
import android.media.AudioManager;

class AudioTool {

	private AudioManager AudioManager = null;
	AudioTool(Context context) {
		AudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE)  ;
	}

	void noRingAndVibrate(boolean bool) {
		//优先模式
		//audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		//震动模式
		//audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		//普通模式
		//audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		//String mString = "设置成功！当前为静音模式";
		if (bool) {
			//优先模式
			AudioManager.setRingerMode(android.media.AudioManager.RINGER_MODE_VIBRATE);
		//	mString = "设置成功！当前为普通模式";
		}else{
			//优先模式
			AudioManager.setRingerMode(android.media.AudioManager.RINGER_MODE_SILENT);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			AudioManager.setRingerMode(android.media.AudioManager.RINGER_MODE_SILENT);
		}
	}
}
