package kr.ac.knu.housekeeper;

import android.animation.ObjectAnimator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class ControlActivity extends AppCompatActivity {

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ전역 변수ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    static boolean all_alert_check = true;


    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡLED 작동

    static boolean door_led = false;
    static boolean dust_led = false;
    static boolean flame_led = false;
    static boolean gas_led = false;
    static boolean temp_led = false;
    static boolean humid_led = false;
    static boolean vib_led = false;
    static boolean water_led = false;

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ터치 x
    static boolean door_alert_flag = true;
    static boolean dust_alert_flag = true;
    static boolean flame_alert_flag = true;
    static boolean gas_alert_flag = true;
    static boolean temp_alert_flag = true;
    static boolean humid_alert_flag = true;
    static boolean vib_alert_flag = true;
    static boolean water_alert_flag = true;

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ설정창 조정ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    static boolean door_auto_control = true;
    static boolean dust_auto_control = true;
    static boolean flame_auto_control = true;
    static boolean gas_auto_control = true;
    static boolean temp_auto_control = true;
    static boolean humid_auto_control = true;
    static boolean vib_auto_control = true;
    static boolean water_auto_control = true;

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ설정창 조정ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    static boolean door_alert_check = true;
    static boolean dust_alert_check = true;
    static boolean flame_alert_check = true;
    static boolean gas_alert_check = true;
    static boolean temp_alert_check = true;
    static boolean humid_alert_check = true;
    static boolean vib_alert_check = true;
    static boolean water_alert_check = true;



    public static int[] dust_guide = {15, 35, 75, Integer.MAX_VALUE};
    public static int[] flame_guide = {1030, 700, 500, Integer.MIN_VALUE};
    public static int[] gas_guide = {200, 500, 800, Integer.MAX_VALUE};
    public static int[] temp_guide = {15, 35, 75, Integer.MAX_VALUE};
    public static int[] humid_guide = {15, 100, 200, Integer.MAX_VALUE};
    public static int[] vib_guide = {100, 500, 1000, Integer.MAX_VALUE};
    public static int[] water_guide = {100, 300, 500, Integer.MAX_VALUE};

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ센서 변수
    public static float gas_state = 0;
    public static int door_state_motion = 0, door_state_button = 0;
    public static float vibration_state = 0;
    public static float dust_state = 0;
    public static float flame_state = Integer.MAX_VALUE;
    public static float temp_state = 0;
    public static float moi_state = 0;
    public static int water_state = 0;
    int duration_time = 500;

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ내부 데이터 변수

    //컨트롤 액티비티
    public static ControlActivity controlActivity = null;

    public static Fragment main_fragment = new MainFragment();
    public static Fragment door_fragment = new SensorDoorFragment();
    public static Fragment dust_fragment = new SensorDustFragment();
    public static Fragment flame_fragment = new SensorFlameFragment();
    public static Fragment gas_fragment = new SensorGasFragment();
    public static Fragment temp_fragment = new SensorTempFragment();
    public static Fragment vib_fragment = new SensorVibFragment();
    public static Fragment water_fragment = new SensorWaterFragment();
    public static Fragment setting_fragment = new SettingFragment();

    public static ListViewAdapterForDoor listViewAdapterForDoor;

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ상단 버튼

    LinearLayout top_title;
    HorizontalScrollView top_horizontalScrollView;

    FloatingActionButton floatingActionButton;
    public LinearLayout top_main_button;
    public LinearLayout top_door_button;
    public LinearLayout top_degree_button;
    public LinearLayout top_flame_button;
    public LinearLayout top_dust_button;
    public LinearLayout top_quake_button;
    public LinearLayout top_water_button;
    public LinearLayout top_gas_button;
    public LinearLayout top_setting_button;

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ블루투스
    // 사용자 정의 함수로 블루투스 활성 상태의 변경 결과를 App으로 알려줄때 식별자로 사용됨(0보다 커야함)
    static final int REQUEST_ENABLE_BT = 10;
    int mPariedDeviceCount = 0;
    Set<BluetoothDevice> mDevices;

    // 폰의 블루투스 모듈을 사용하기 위한 오브젝트.
    BluetoothAdapter mBluetoothAdapter;


    //BluetoothDevice mRemoteDevie;

    // 스마트폰과 페어링 된 디바이스간 통신 채널에 대응 하는 BluetoothSocket
    //BluetoothSocket mSocket = null;
    //OutputStream mOutputStream = null;
    //InputStream mInputStream = null;
    String mStrDelimiter = "\n";
    //char mCharDelimiter =  '\n';


    //Thread mWorkerThread = null;
    //byte[] readBuffer;
    //int readBufferPosition;

    boolean blue_window_show = false;

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ변수 끝ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    public void Initialize_Top_Button_Color()
    {
        top_main_button.setBackgroundResource(R.color.colorLogo);
        top_door_button.setBackgroundResource(R.color.colorLogo);
        top_degree_button.setBackgroundResource(R.color.colorLogo);
        top_flame_button.setBackgroundResource(R.color.colorLogo);
        top_degree_button.setBackgroundResource(R.color.colorLogo);
        top_dust_button.setBackgroundResource(R.color.colorLogo);
        top_quake_button.setBackgroundResource(R.color.colorLogo);
        top_water_button.setBackgroundResource(R.color.colorLogo);
        top_gas_button.setBackgroundResource(R.color.colorLogo);
        top_setting_button.setBackgroundResource(R.color.colorLogo);
    }


    public void Call_119()
    {
        String tel = "tel:119";
        startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
    }

    public void Call_112()
    {
        String tel = "tel:112";
        startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        Initialize();


        top_main_button.callOnClick();
    }


    @Override
    protected void onResume() {
        super.onResume();

        Initialize();
    }

    public void Initialize()
    {
        controlActivity = this;

        main_fragment = new MainFragment();
        door_fragment = new SensorDoorFragment();
        dust_fragment = new SensorDustFragment();
        flame_fragment = new SensorFlameFragment();
        gas_fragment = new SensorGasFragment();
        temp_fragment = new SensorTempFragment();
        vib_fragment = new SensorVibFragment();
        water_fragment = new SensorWaterFragment();
        setting_fragment = new SettingFragment();


        //버튼 초기화
        top_title = findViewById(R.id.top_title);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        top_horizontalScrollView = findViewById(R.id.scroll);
        top_main_button = findViewById(R.id.top_main_button);
        top_door_button = findViewById(R.id.top_door_button);
        top_degree_button = findViewById(R.id.top_degree_button);
        top_flame_button = findViewById(R.id.top_fire_button);
        top_degree_button = findViewById(R.id.top_degree_button);
        top_dust_button = findViewById(R.id.top_dust_button);
        top_quake_button = findViewById(R.id.top_quake_button);
        top_water_button = findViewById(R.id.top_water_button);
        top_gas_button = findViewById(R.id.top_gas_button);
        top_setting_button = findViewById(R.id.top_setting_button);


        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ상단버튼 이벤트
        top_main_button.setOnClickListener(  new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Change_Fragment(R.id.fragment_container, main_fragment);
                Initialize_Top_Button_Color();
                top_main_button.setBackgroundResource(R.color.colorPrimaryDark);
                //top_horizontalScrollView.smoothScrollTo(top_main_button.getLeft() + (top_main_button.getWidth() - top_title.getWidth())/ 2, 0);
                ObjectAnimator.ofInt(top_horizontalScrollView, "scrollX",  top_main_button.getLeft() + (top_main_button.getWidth() - top_title.getWidth())/ 2).setDuration(duration_time).start();
            }
        });


        top_door_button.setOnClickListener(  new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Change_Fragment(R.id.fragment_container, door_fragment);
                ((SensorDoorFragment)door_fragment).Refresh_Switch();
                Initialize_Top_Button_Color();
                top_door_button.setBackgroundResource(R.color.colorPrimaryDark);
                //top_horizontalScrollView.smoothScrollTo(top_door_button.getLeft() + (top_main_button.getWidth() - top_title.getWidth())/ 2, 0);
                ObjectAnimator.ofInt(top_horizontalScrollView, "scrollX",  top_door_button.getLeft() + (top_door_button.getWidth() - top_title.getWidth())/ 2).setDuration(duration_time).start();
            }
        });

        top_degree_button.setOnClickListener(  new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Change_Fragment(R.id.fragment_container, temp_fragment);
                ((SensorTempFragment)temp_fragment).Refresh_Switch();
                Initialize_Top_Button_Color();
                top_degree_button.setBackgroundResource(R.color.colorPrimaryDark);
                //top_horizontalScrollView.smoothScrollTo(top_degree_button.getLeft() + (top_main_button.getWidth() - top_title.getWidth())/ 2, 0);
                ObjectAnimator.ofInt(top_horizontalScrollView, "scrollX",  top_degree_button.getLeft() + (top_degree_button.getWidth() - top_title.getWidth())/ 2).setDuration(duration_time).start();

            }
        });


        top_flame_button.setOnClickListener(  new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Change_Fragment(R.id.fragment_container, flame_fragment);
                ((SensorFlameFragment)flame_fragment).Refresh_Switch();
                Initialize_Top_Button_Color();
                top_flame_button.setBackgroundResource(R.color.colorPrimaryDark);
                //top_horizontalScrollView.smoothScrollTo(top_flame_button.getLeft() + (top_main_button.getWidth() - top_title.getWidth())/ 2, 0);
                ObjectAnimator.ofInt(top_horizontalScrollView, "scrollX",  top_flame_button.getLeft() + (top_flame_button.getWidth() - top_title.getWidth())/ 2).setDuration(duration_time).start();

            }
        });


        top_dust_button.setOnClickListener(  new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Change_Fragment(R.id.fragment_container, dust_fragment);
                ((SensorDustFragment)dust_fragment).Refresh_Switch();
                Initialize_Top_Button_Color();
                top_dust_button.setBackgroundResource(R.color.colorPrimaryDark);
                //top_horizontalScrollView.smoothScrollTo(top_dust_button.getLeft() + (top_main_button.getWidth() - top_title.getWidth())/ 2, 0);
                ObjectAnimator.ofInt(top_horizontalScrollView, "scrollX",  top_dust_button.getLeft() + (top_dust_button.getWidth() - top_title.getWidth())/ 2).setDuration(duration_time).start();
            }
        });


        top_quake_button.setOnClickListener(  new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Change_Fragment(R.id.fragment_container, vib_fragment);
                ((SensorVibFragment)vib_fragment).Refresh_Switch();
                Initialize_Top_Button_Color();
                top_quake_button.setBackgroundResource(R.color.colorPrimaryDark);
                //top_horizontalScrollView.smoothScrollTo(top_quake_button.getLeft() + (top_main_button.getWidth() - top_title.getWidth())/ 2, 0);
                ObjectAnimator.ofInt(top_horizontalScrollView, "scrollX",  top_quake_button.getLeft() + (top_quake_button.getWidth() - top_title.getWidth())/ 2).setDuration(duration_time).start();
            }
        });

        top_water_button.setOnClickListener(  new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Change_Fragment(R.id.fragment_container, water_fragment);
                ((SensorWaterFragment)water_fragment).Refresh_Switch();
                Initialize_Top_Button_Color();
                top_water_button.setBackgroundResource(R.color.colorPrimaryDark);
                //op_horizontalScrollView.smoothScrollTo(top_water_button.getLeft() + (top_main_button.getWidth() - top_title.getWidth())/ 2, 0);
                ObjectAnimator.ofInt(top_horizontalScrollView, "scrollX",  top_water_button.getLeft() + (top_water_button.getWidth() - top_title.getWidth())/ 2).setDuration(duration_time).start();
            }
        });

        top_gas_button.setOnClickListener(  new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Change_Fragment(R.id.fragment_container, gas_fragment);
                ((SensorGasFragment)gas_fragment).Refresh_Switch();
                Initialize_Top_Button_Color();
                top_gas_button.setBackgroundResource(R.color.colorPrimaryDark);
                //top_horizontalScrollView.smoothScrollTo(top_gas_button.getLeft() + (top_main_button.getWidth() - top_title.getWidth())/ 2, 0);
                ObjectAnimator.ofInt(top_horizontalScrollView, "scrollX",  top_gas_button.getLeft() + (top_gas_button.getWidth() - top_title.getWidth())/ 2).setDuration(duration_time).start();
            }
        });

        top_setting_button.setOnClickListener(  new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Change_Fragment(R.id.fragment_container, setting_fragment);
                Initialize_Top_Button_Color();
                ((SettingFragment)setting_fragment).Sync_Alarm_Setting();
                top_setting_button.setBackgroundResource(R.color.colorPrimaryDark);
                //top_horizontalScrollView.smoothScrollTo(top_setting_button.getLeft() + (top_main_button.getWidth() - top_title.getWidth())/ 2, 0);
                ObjectAnimator.ofInt(top_horizontalScrollView, "scrollX",  top_setting_button.getLeft() + (top_setting_button.getWidth() - top_title.getWidth())/ 2).setDuration(duration_time).start();
            }
        });

        floatingActionButton.setOnClickListener(  new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                top_setting_button.callOnClick();
            }
        });





        /*
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ블루투스 아이콘 이벤트
        bluetooth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBluetooth();
            }
        });
        */


        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ내부 Door 출입 List 데이터
        listViewAdapterForDoor = new ListViewAdapterForDoor();
        Calendar tempaa = Calendar.getInstance();

        listViewAdapterForDoor.addList(tempaa.getTime());
        tempaa.add(Calendar.HOUR,-10);
        listViewAdapterForDoor.addList(tempaa.getTime());
        listViewAdapterForDoor.addList(Calendar.getInstance().getTime());
        listViewAdapterForDoor.addList(tempaa.getTime());
        tempaa.add(Calendar.HOUR,-10);
        listViewAdapterForDoor.addList(tempaa.getTime());
        listViewAdapterForDoor.addList(Calendar.getInstance().getTime());
        listViewAdapterForDoor.addList(tempaa.getTime());
        listViewAdapterForDoor.addList(tempaa.getTime());
        listViewAdapterForDoor.addList(Calendar.getInstance().getTime());


        tempaa.add(Calendar.DATE,-10);
        listViewAdapterForDoor.addList(tempaa.getTime());
        tempaa.add(Calendar.HOUR,-10);
        listViewAdapterForDoor.addList(tempaa.getTime());
        tempaa.add(Calendar.DATE,+20);
        listViewAdapterForDoor.addList(tempaa.getTime());


        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ인텐트 내용 확인후 올바른 프레그먼트 선택
        String str = getIntent().getStringExtra("fragment");
        if(str !=null)
        {
            if(str.equals("door"))
                top_door_button.callOnClick();
            else if(str.equals("gas"))
                top_gas_button.callOnClick();
            else if(str.equals("flame"))
                top_flame_button.callOnClick();
            else if(str.equals("water"))
                top_water_button.callOnClick();
            else if(str.equals("humidity"))
                top_degree_button.callOnClick();
            else if(str.equals("temperature"))
                top_degree_button.callOnClick();
            else if(str.equals("earthquake"))
                top_quake_button.callOnClick();
            else if(str.equals("dust"))
                top_dust_button.callOnClick();
            else if(str.equals("setting"))
                top_setting_button.callOnClick();
        }


    }


    public void Change_Fragment(@IdRes int id, Fragment fragment)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void Popup_Setting(String name, View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopSettingActivity.class);
        intent.putExtra("name", name);
        startActivityForResult(intent, 1432);
    }


    public void Start_Service() {
        if(MyService.myService != null)
            return;
        //Toast.makeText( getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ControlActivity.this, MyService.class);
        startService(intent);
        checkBluetooth();
    }

    public void End_Service() {
        if(MyService.myService == null)
            return;
        //Toast.makeText(getApplicationContext(),"Service 끝",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ControlActivity.this, MyService.class);
        stopService(intent);
        MyService.myService.Clean();
    }


    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ블루투스 함수ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    // 블루투스 장치의 이름이 주어졌을때 해당 블루투스 장치 객체를 페어링 된 장치 목록에서 찾아내는 코드.
    BluetoothDevice getDeviceFromBondedList(String name) {
        // BluetoothDevice : 페어링 된 기기 목록을 얻어옴.
        BluetoothDevice selectedDevice = null;
        // getBondedDevices 함수가 반환하는 페어링 된 기기 목록은 Set 형식이며,
        // Set 형식에서는 n 번째 원소를 얻어오는 방법이 없으므로 주어진 이름과 비교해서 찾는다.
        for(BluetoothDevice deivce : mDevices) {
            // getName() : 단말기의 Bluetooth Adapter 이름을 반환
            if(name.equals(deivce.getName())) {
                selectedDevice = deivce;
                break;
            }
        }
        return selectedDevice;
    }

    /*
    // 문자열 전송하는 함수(쓰레드 사용 x)
    void sendData(String msg) {
        msg += mStrDelimiter;  // 문자열 종료표시 (\n)
        try{
            // getBytes() : String을 byte로 변환
            // OutputStream.write : 데이터를 쓸때는 write(byte[]) 메소드를 사용함.
            // byte[] 안에 있는 데이터를 한번에 기록해 준다.
            mOutputStream.write(msg.getBytes());  // 문자열 전송.
        }catch(Exception e) {  // 문자열 전송 도중 오류가 발생한 경우
            Toast.makeText(getApplicationContext(), "데이터 전송중 오류가 발생",
                    Toast.LENGTH_LONG).show();
            finish();  // App 종료
        }
    }
    */

    //  connectToSelectedDevice() : 원격 장치와 연결하는 과정을 나타냄.
    //   실제 데이터 송수신을 위해서는 소켓으로부터 입출력 스트림을 얻고 입출력 스트림을 이용하여 이루어 진다.
    void connectToSelectedDevice(String selectedDeviceName) {
        // BluetoothDevice 원격 블루투스 기기를 나타냄.
        BluetoothDevice mRemoteDevie = getDeviceFromBondedList(selectedDeviceName);
        // java.util.UUID.fromString : 자바에서 중복되지 않는 Unique 키 생성.
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            // 소켓 생성, RFCOMM 채널을 통한 연결.
            // createRfcommSocketToServiceRecord(uuid) : 이 함수를 사용하여 원격 블루투스 장치와
            //                                           통신할 수 있는 소켓을 생성함.
            // 이 메소드가 성공하면 스마트폰과 페어링 된 디바이스간 통신 채널에 대응하는
            //  BluetoothSocket 오브젝트를 리턴함.
            BluetoothSocket mSocket = mRemoteDevie.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect(); // 소켓이 생성 되면 connect() 함수를 호출함으로써 두기기의 연결은 완료된다.

            // 데이터 송수신을 위한 스트림 얻기.
            // BluetoothSocket 오브젝트는 두개의 Stream을 제공한다.
            // 1. 데이터를 보내기 위한 OutputStrem
            // 2. 데이터를 받기 위한 InputStream
            OutputStream mOutputStream = null;
            InputStream mInputStream = null;

            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();

            //Intent intent = new Intent(ControlActivity.this, MyService.class);
            //startService(intent);


            // 데이터 수신 준비.
            //beginListenForData();
            MyService.myService.Set_Connection(mRemoteDevie, mSocket , mInputStream, mOutputStream);

        }catch(Exception e) { // 블루투스 연결 중 오류 발생
            Toast.makeText(getApplicationContext(),
                    "Error with bluetooth connection", Toast.LENGTH_LONG).show();

            //finish();  // App 종료
            blue_window_show = false;
            if(setting_fragment != null)
                ((SettingFragment)setting_fragment).Turn_Bluetooth_Switch(false);
        }
    }

    // 블루투스 지원하며 활성 상태인 경우.
    void selectDevice() {
        // 블루투스 디바이스는 연결해서 사용하기 전에 먼저 페어링 되어야만 한다
        // getBondedDevices() : 페어링된 장치 목록 얻어오는 함수.
        mDevices = mBluetoothAdapter.getBondedDevices();
        mPariedDeviceCount = mDevices.size();

        if(mPariedDeviceCount == 0 ) { // 페어링된 장치가 없는 경우.
            //Toast.makeText(getApplicationContext(), "Can't find paring device", Toast.LENGTH_LONG).show();
            //finish(); // App 종료.
            blue_window_show = false;
            if(setting_fragment != null)
                ((SettingFragment)setting_fragment).Turn_Bluetooth_Switch(false);
        }
        // 페어링된 장치가 있는 경우.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select bluetooth device");

        // 각 디바이스는 이름과(서로 다른) 주소를 가진다. 페어링 된 디바이스들을 표시한다.
        List<String> listItems = new ArrayList<String>();
        for(BluetoothDevice device : mDevices) {
            // device.getName() : 단말기의 Bluetooth Adapter 이름을 반환.
            listItems.add(device.getName());
        }
        listItems.add("cancel");  // 취소 항목 추가.


        // CharSequence : 변경 가능한 문자열.
        // toArray : List형태로 넘어온것 배열로 바꿔서 처리하기 위한 toArray() 함수.
        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
        // toArray 함수를 이용해서 size만큼 배열이 생성 되었다.
        listItems.toArray(new CharSequence[listItems.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                if(item == mPariedDeviceCount) { // 연결할 장치를 선택하지 않고 '취소' 를 누른 경우.
                    //Toast.makeText(getApplicationContext(), "Some function is off", Toast.LENGTH_LONG).show();
                    //finish();
                    blue_window_show = false;
                    if(setting_fragment != null)
                        ((SettingFragment)setting_fragment).Turn_Bluetooth_Switch(false);
                }
                else { // 연결할 장치를 선택한 경우, 선택한 장치와 연결을 시도함.
                    connectToSelectedDevice(items[item].toString());
                    blue_window_show = false;
                }
            }

        });

        builder.setCancelable(false);  // 뒤로 가기 버튼 사용 금지.
        AlertDialog alert = builder.create();
        alert.show();
    }

    static void Add_New_Door_List()
    {
        listViewAdapterForDoor.addList(Calendar.getInstance().getTime());
    }

    public void checkBluetooth() {

        if(blue_window_show == true)
            return;
        else
            blue_window_show = true;

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null ) {  // 블루투스 미지원
            Toast.makeText(getApplicationContext(), "This device don't support bluetooth function", Toast.LENGTH_LONG).show();
            //finish();  // 앱종료
            blue_window_show = false;
            if(setting_fragment != null)
                ((SettingFragment)setting_fragment).Turn_Bluetooth_Switch(false);
        }
        else { // 블루투스 지원

            if(!mBluetoothAdapter.isEnabled()) { // 블루투스 지원하며 비활성 상태인 경우.
                Toast.makeText(getApplicationContext(), "현재 블루투스가 비활성 상태입니다.", Toast.LENGTH_LONG).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // REQUEST_ENABLE_BT : 블루투스 활성 상태의 변경 결과를 App 으로 알려줄 때 식별자로 사용(0이상)

                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                //blue_window_show = false;
                //if(setting_fragment != null)
                //    ((SettingFragment)setting_fragment).Turn_Bluetooth_Switch(false);
            }
            else // 블루투스 지원하며 활성 상태인 경우.
            {
                //Toast.makeText(getApplicationContext(), "Select bluetooth device", Toast.LENGTH_LONG).show();
                selectDevice();
            }
        }
    }

    // onDestroy() : 어플이 종료될때 호출 되는 함수.
    //               블루투스 연결이 필요하지 않는 경우 입출력 스트림 소켓을 닫아줌.
    @Override
    protected void onDestroy() {
        try{
            //mWorkerThread.interrupt(); // 데이터 수신 쓰레드 종료
            //mInputStream.close();
            //mSocket.close();
        }catch(Exception e){}
        super.onDestroy();
    }


    // onActivityResult : 사용자의 선택결과 확인 (아니오, 예)
    // RESULT_OK: 블루투스가 활성화 상태로 변경된 경우. "예"
    // RESULT_CANCELED : 오류나 사용자의 "아니오" 선택으로 비활성 상태로 남아 있는 경우  RESULT_CANCELED

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // startActivityForResult 를 여러번 사용할 땐 이런 식으로
        // switch 문을 사용하여 어떤 요청인지 구분하여 사용함.
        switch(requestCode) {
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK) { // 블루투스 활성화 상태
                    selectDevice();
                }
                else if(resultCode == RESULT_CANCELED) { // 블루투스 비활성화 상태 (종료)
                    Toast.makeText(getApplicationContext(), "일정 기능이 비활성화 됩니다.", Toast.LENGTH_LONG).show();
                    //finish();
                    blue_window_show = false;
                    if(setting_fragment != null)
                        ((SettingFragment)setting_fragment).Turn_Bluetooth_Switch(false);
                }
                break;
            case 1432:
                top_setting_button.callOnClick();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




}
