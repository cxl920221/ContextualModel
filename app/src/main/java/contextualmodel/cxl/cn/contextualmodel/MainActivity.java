package contextualmodel.cxl.cn.contextualmodel;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import contextualmodel.cxl.cn.contextualmodel.base.BaseActivity;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_jobssid)
    TextView tvJobssid;
    @BindView(R.id.et_jobssid)
    EditText etJobssid;
    @BindView(R.id.tv_unjobssid)
    TextView tvUnjobssid;
    @BindView(R.id.et_unjobssid)
    EditText etUnjobssid;
    @BindView(R.id.tv_startTime)
    TextView tvStartTime;
    @BindView(R.id.tve_startTime)
    TextView tveStartTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R.id.tve_endTime)
    TextView tveEndTime;
    @BindView(R.id.rd_wifi)
    RadioButton rdWifi;
    @BindView(R.id.rd_time)
    RadioButton rdTime;
    @BindView(R.id.rd_gps)
    RadioButton rdGps;
    @BindView(R.id.rg_type)
    RadioGroup rgType;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_status)
    ToggleButton btnStatus;

    private String ip = null;
    private WifiTool wifi = null;
    private ListPopupWindow listPopupWindow = null;
    private boolean jobflag = false;

//    final int DRAWABLE_LEFT = 0;
//    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
//    final int DRAWABLE_BOTTOM = 3;

    SharedPreferences pre;
    SharedPreferences.Editor edi;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pre = getSharedPreferences("mode", MODE_PRIVATE);
        edi = pre.edit();
        wifi = new WifiTool(MainActivity.this);
        //String SSID = wifi.getWifiSSID();
        showCzNotify(wifi.getWifiSSID(), ip);
        initData();
        initListener();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initData() {
        etJobssid.setText(pre.getString("jobssid", null));
        etUnjobssid.setText(pre.getString("unjobssid", null));
        jobflag = pre.getBoolean("jobflag", false);
        btnStatus.setChecked(jobflag);
    }

    public void showCzNotify(String SSID, String IP) {
        Intent i = new Intent(this, WifiService.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("wifiname", SSID);
        i.putExtra("ip", IP);
        this.startService(i);
    }

    private void initListener() {
        etJobssid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (etJobssid.getWidth() - etJobssid
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        etJobssid.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(MainActivity.this,R.drawable.ic_expand_less_black_18dp), null);
                        showListPopulWindow(etJobssid);
                        return true;
                    }
                }
                return false;
            }
        });

        etUnjobssid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (etUnjobssid.getWidth() - etUnjobssid
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        etUnjobssid.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(MainActivity.this,R.drawable.ic_expand_less_black_18dp), null);
                        showListPopulWindow(etUnjobssid);
                        return true;
                    }
                }
                return false;
            }
        });
//        btn_show_cz.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                SSID = wifi.getWifiSSID();
//                ip = "wifiManager:" + wifi.getWifiIp() + "\n java:" + WifiTool.getLocalIpAddress();
//                for (int i = 0; i < 10; i++) {
//                    ip += "\n java";
//                }
//                showCzNotify(SSID, ip);
//            }
//        });
    }

    public void showTimePickerDialog(int hour, int minute) {
        Toast.makeText(MainActivity.this, "选择的时间" + hour + ":" + minute, Toast.LENGTH_SHORT).show();
        new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String times = hourOfDay + ":" + minute;
                Toast.makeText(MainActivity.this, "选择的时间" + times, Toast.LENGTH_SHORT).show();
                tveStartTime.setText(times);
            }
        }, hour, minute, true).show();
    }


    private void showListPopulWindow(final EditText editText) {

        final String[] list = wifi.getWifiSSIDList().toArray(new String[wifi.getWifiSSIDList().size()]);
        listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(new ArrayAdapter<>(this,-
                android.R.layout.simple_list_item_1, list));
        listPopupWindow.setAnchorView(editText);
        listPopupWindow.setModal(true);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(list[i]);
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                editText.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(MainActivity.this,R.drawable.ic_expand_more_black_18dp ), null);
            }
        });
        listPopupWindow.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                Uri.parse("android-app://contextualmodel.cxl.cn.contextualmodel/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                Uri.parse("android-app://contextualmodel.cxl.cn.contextualmodel/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @OnClick({ R.id.et_unjobssid, R.id.tve_startTime, R.id.tve_endTime, R.id.rd_wifi, R.id.rd_time, R.id.rd_gps, R.id.btn_save, R.id.btn_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_unjobssid:
                break;
            case R.id.tve_startTime:
                Calendar calendar = Calendar.getInstance();
                showTimePickerDialog(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
                break;
            case R.id.tve_endTime:
                break;
            case R.id.rd_wifi:
                break;
            case R.id.rd_time:
                break;
            case R.id.rd_gps:
                break;
            case R.id.btn_save:
                edi.putString("jobssid", etJobssid.getText().toString());
                edi.putString("unjobssid", etUnjobssid.getText().toString());
                edi.commit();
                Toast.makeText(MainActivity.this, "设置成功!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_status:
                jobflag = btnStatus.isChecked();
                edi.putBoolean("jobflag", jobflag);
                edi.commit();
                Toast.makeText(MainActivity.this, jobflag ? "监听启动" : "监听停止", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}