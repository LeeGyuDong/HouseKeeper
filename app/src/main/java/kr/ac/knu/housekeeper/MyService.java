package kr.ac.knu.housekeeper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.Delayed;

public class MyService extends Service {

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ변수ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    public static MyService myService = null;
    NotificationManager Notifi_M;
    //ServiceThread thread;
    Notification Notifi ;
    myServiceHandler handler;
    Thread mWorkerThread = null;
    Bitmap bitmap;

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ블루투스 변수

    String mStrDelimiter = "\n";
    char mCharDelimiter =  '\n';
    byte[] readBuffer;
    int readBufferPosition;
    BluetoothDevice mRemoteDevie;
    OutputStream mOutputStream = null;
    InputStream mInputStream = null;
    BluetoothSocket mSocket = null;


    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ함수ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    public void Set_Connection(BluetoothDevice bluetoothDevice ,BluetoothSocket bluetoothSocket, InputStream inputStream, OutputStream outputStream)
    {
        mRemoteDevie = bluetoothDevice;
        mSocket = bluetoothSocket;
        mOutputStream = outputStream;
        mInputStream = inputStream;


        beginListenForData();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myService = this;
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        handler = new myServiceHandler();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업
    public void Clean()
    {
        try{
            if(mWorkerThread != null)
                mWorkerThread.interrupt();
            mWorkerThread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
            if(mInputStream != null)
            mInputStream.close();
                mInputStream = null;
            if(mSocket != null)
                mSocket.close();
            mSocket = null;
            if(myService == this)
                myService = null;
        }catch(Exception e){}

    }


    public void onDestroy() {

        try{
            mWorkerThread.interrupt();
            mWorkerThread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
            mInputStream.close();
            mInputStream = null;
            mSocket.close();
            mSocket = null;
            if(myService == this)
                myService = null;
        }catch(Exception e){}
        super.onDestroy();
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            /*
            Intent intent = new Intent(MyService.this, ControlActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            Notifi = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Content Title")
                    .setContentText("Content Text")
                    .setSmallIcon(R.drawable.appicon)
                    .setTicker("알림!!!")
                    .setContentIntent(pendingIntent)
                    .build();



            //소리추가
            //Notifi.defaults = Notification.DEFAULT_SOUND;

            //알림 소리를 한번만 내도록
            //Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;

            //확인하면 자동으로 알림이 제거 되도록
            Notifi.flags = Notification.FLAG_AUTO_CANCEL;


            Notifi_M.notify( 777 , Notifi);

            //토스트 띄우기
            Toast.makeText(MyService.this, (String)msg.obj, Toast.LENGTH_LONG).show();
            */
            Log.d("로그다 로그", (String)msg.obj);
        }
    }

    public void Push_Alarm(String title, String contents, String channer)
    {
        /*
        Intent intent = new Intent(MyService.this, ControlActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notifi = new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(contents)
                .setSmallIcon(R.drawable.appicon)
                .setTicker("알림!!!")
                .setContentIntent(pendingIntent)
                .build();



        //소리추가
        //Notifi.defaults = Notification.DEFAULT_SOUND;

        //알림 소리를 한번만 내도록
        //Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;

        //확인하면 자동으로 알림이 제거 되도록
        Notifi.flags = Notification.FLAG_AUTO_CANCEL;


        Notifi_M.notify( 777 , Notifi);

        */
        Intent intent1 = new Intent(ControlActivity.controlActivity.getApplicationContext(),MainActivity.class); //인텐트 생성.

        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("fragment", channer);

        //현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를없앤다.

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( ControlActivity.controlActivity,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);


        Bitmap temp_bitmap = null;

        if(channer !=null)
        {
            if(channer.equals("door"))
                temp_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock_2);
            else if(channer.equals("gas"))
                temp_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fire_2);
            else if(channer.equals("flame"))
                temp_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.housefire_2);
            else if(channer.equals("water"))
                temp_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water_2);
            else if(channer.equals("humidity"))
                temp_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.humid_2);
            else if(channer.equals("temperature"))
                temp_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.temp_2);
            else if(channer.equals("earthquake"))
                temp_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.earth_2);
            else if(channer.equals("dust"))
                temp_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.air_2);
            else
                temp_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
        }
        else
            temp_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyService.this, channer)
                .setSmallIcon(R.drawable.appicon).setContentTitle(title).setContentText(contents)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setLargeIcon(temp_bitmap)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setContentIntent(pendingNotificationIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

    }

    void Door_Push_Alarm(int check_state)
    {
        if(!ControlActivity.door_alert_check || !ControlActivity.all_alert_check)
            return;

        if(check_state == 1)
        {
            if(ControlActivity.door_alert_flag){
                ControlActivity.door_alert_flag = false;
                Push_Alarm("Danger", "Door is unlock!", "door");
                if(ControlActivity.door_auto_control) {
                    ControlActivity.controlActivity.Call_112();

                    ControlActivity.door_led = false;
                    ControlActivity.water_led = false;
                    if(ControlActivity.door_fragment != null)
                        ((SensorDoorFragment)ControlActivity.door_fragment).Refresh_Switch();
                }
            }
        }
        else
            ControlActivity.door_alert_flag = true;
    }

    void Flame_Push_Alarm(int check_state)
    {
        if(!ControlActivity.flame_alert_check || !ControlActivity.all_alert_check)
            return;

        if(check_state == 0)
        {
            ControlActivity.flame_alert_flag = true;
        }
        else if(check_state == 1)
        {
            ControlActivity.flame_alert_flag = true;
        }
        else if(check_state == 2)
        {
            ControlActivity.flame_alert_flag = true;
        }
        else
        {
            if(ControlActivity.flame_alert_flag){
                ControlActivity.flame_alert_flag = false;
                Push_Alarm("Danger", "Flame is burning!", "flame");
                if(ControlActivity.flame_auto_control) {
                    ControlActivity.controlActivity.Call_119();
                    ControlActivity.flame_led = false;
                    ControlActivity.gas_led = false;
                    if(ControlActivity.flame_fragment != null)
                        ((SensorFlameFragment)ControlActivity.flame_fragment).Refresh_Switch();
                }

            }
        }
    }

    void Gas_Push_Alarm(int check_state)
    {
        if(!ControlActivity.gas_alert_check || !ControlActivity.all_alert_check)
            return;

        if(check_state == 0)
        {
            ControlActivity.gas_alert_flag = true;
        }
        else if(check_state == 1)
        {
            ControlActivity.gas_alert_flag = true;
        }
        else if(check_state == 2)
        {
            ControlActivity.gas_alert_flag = true;
        }
        else
        {
            if(ControlActivity.gas_alert_flag){
                ControlActivity.gas_alert_flag = false;
                Push_Alarm("Danger", "Gas is leaking!", "gas");
                if(ControlActivity.gas_auto_control) {
                    ControlActivity.gas_led = false;
                    ControlActivity.flame_led = false;
                    if(ControlActivity.gas_fragment != null)
                        ((SensorGasFragment)ControlActivity.gas_fragment).Refresh_Switch();
                }
            }
        }
    }

    void Dust_Push_Alarm(int check_state)
    {
        if(!ControlActivity.dust_alert_check || !ControlActivity.all_alert_check)
            return;

        if(check_state == 0)
        {
            ControlActivity.dust_alert_flag = true;
        }
        else if(check_state == 1)
        {
            ControlActivity.dust_alert_flag = true;
        }
        else if(check_state == 2)
        {
            ControlActivity.dust_alert_flag = true;
        }
        else
        {
            if(ControlActivity.dust_alert_flag){
                ControlActivity.dust_alert_flag = false;
                Push_Alarm("Danger", "Dust is very bad!", "dust");
                if(ControlActivity.dust_auto_control) {
                    ControlActivity.dust_led = false;
                    if(ControlActivity.dust_fragment != null)
                        ((SensorDoorFragment)ControlActivity.dust_fragment).Refresh_Switch();
                }
            }
        }
    }

    void Temp_Push_Alarm(int check_state)
    {
        if(!ControlActivity.temp_alert_check || !ControlActivity.all_alert_check)
            return;

        if(check_state == 0)
        {
            ControlActivity.temp_alert_flag = true;
        }
        else if(check_state == 1)
        {
            ControlActivity.temp_alert_flag = true;
        }
        else if(check_state == 2)
        {
            ControlActivity.temp_alert_flag = true;
        }
        else
        {
            if(ControlActivity.temp_alert_flag){
                ControlActivity.temp_alert_flag = false;
                Push_Alarm("Danger", "Temperature is too high", "temperature");
                if(ControlActivity.temp_auto_control) {
                    ControlActivity.temp_led = false;
                    ControlActivity.humid_led = false;
                    if(ControlActivity.temp_fragment != null)
                        ((SensorTempFragment)ControlActivity.temp_fragment).Refresh_Switch();

                }
            }
        }
    }

    void Vib_Push_Alarm(int check_state)
    {
        if(!ControlActivity.vib_alert_check || !ControlActivity.all_alert_check)
            return;

        if(check_state == 0)
        {
            ControlActivity.vib_alert_flag = true;
        }
        else if(check_state == 1)
        {
            ControlActivity.vib_alert_flag = true;
        }
        else if(check_state == 2)
        {
            ControlActivity.vib_alert_flag = true;
        }
        else
        {
            if(ControlActivity.vib_alert_flag){
                ControlActivity.vib_alert_flag = false;
                Push_Alarm("Danger", "Earthquake occurred ", "earthquake");
                if(ControlActivity.vib_auto_control) {
                    ControlActivity.vib_led = false;
                    if(ControlActivity.vib_fragment != null)
                        ((SensorVibFragment)ControlActivity.vib_fragment).Refresh_Switch();
                }
            }
        }
    }

    void Water_Push_Alarm(int check_state)
    {
        if(!ControlActivity.water_alert_check || !ControlActivity.all_alert_check)
            return;

        if(check_state == 0)
        {
            ControlActivity.water_alert_flag = true;
        }
        else if(check_state == 1)
        {
            ControlActivity.water_alert_flag = true;
        }
        else if(check_state == 2)
        {
            ControlActivity.water_alert_flag = true;
        }
        else
        {
            if(ControlActivity.water_alert_flag){
                ControlActivity.water_alert_flag = false;
                Push_Alarm("Danger","The water is overflowing", "water");
                if(ControlActivity.water_auto_control) {
                    ControlActivity.water_led = false;
                    ControlActivity.door_led = false;
                    if(ControlActivity.water_fragment != null)
                        ((SensorWaterFragment)ControlActivity.water_fragment).Refresh_Switch();
                }
            }
        }
    }

    void Humid_Push_Alarm(int check_state)
    {
        if(!ControlActivity.humid_alert_check || !ControlActivity.all_alert_check)
            return;

        if(check_state == 0)
        {
            ControlActivity.humid_alert_flag = true;
        }
        else if(check_state == 1)
        {
            ControlActivity.humid_alert_flag = true;
        }
        else if(check_state == 2)
        {
            ControlActivity.humid_alert_flag = true;
        }
        else
        {
            if(ControlActivity.humid_alert_flag){
                ControlActivity.humid_alert_flag = false;
                Push_Alarm("Danger", "Humidity is too high", "humidity");
                if(ControlActivity.humid_auto_control) {
                    ControlActivity.humid_led = false;
                    ControlActivity.temp_led = false;
                    if(ControlActivity.temp_fragment != null)
                        ((SensorTempFragment)ControlActivity.temp_fragment).Refresh_Switch();
                }
            }
        }
    }



    public void Check_State()
    {
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ문
        if(ControlActivity.door_state_button == 1) {
            if(ControlActivity.door_fragment != null)
                ((SensorDoorFragment)ControlActivity.door_fragment).Refresh(1);
            if(ControlActivity.main_fragment != null)
                ((MainFragment)ControlActivity.main_fragment).Refresh(0, 1);
            Door_Push_Alarm(1);
        }
        else {
            if(ControlActivity.door_fragment != null)
                ((SensorDoorFragment)ControlActivity.door_fragment).Refresh(0);
            if(ControlActivity.main_fragment != null)
                ((MainFragment)ControlActivity.main_fragment).Refresh(0, 0);

            Door_Push_Alarm(0);
        }

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ먼지
        for(int i = 0; i <= 3; i++)
        {
            if(ControlActivity.dust_state <= ControlActivity.dust_guide[i]) {
                if(ControlActivity.dust_fragment != null)
                    ((SensorDustFragment)ControlActivity.dust_fragment).Refresh(i);
                if(ControlActivity.main_fragment != null)
                    ((MainFragment)ControlActivity.main_fragment).Refresh(1, i);
                Dust_Push_Alarm(i);
                break;
            }
        }

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ불꽃
        for(int i = 0; i <= 3; i++)
        {
            if(ControlActivity.flame_state >= ControlActivity.flame_guide[i]) {
                if(ControlActivity.flame_fragment != null)
                    ((SensorFlameFragment)ControlActivity.flame_fragment).Refresh(i);
                if(ControlActivity.main_fragment != null)
                    ((MainFragment)ControlActivity.main_fragment).Refresh(2, i);
                Flame_Push_Alarm(i);
                break;
            }
        }

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ가스
        for(int i = 0; i <= 3; i++)
        {
            if(ControlActivity.gas_state <= ControlActivity.gas_guide[i]) {
                if(ControlActivity.gas_fragment != null)
                    ((SensorGasFragment)ControlActivity.gas_fragment).Refresh(i);
                if(ControlActivity.main_fragment != null)
                    ((MainFragment)ControlActivity.main_fragment).Refresh(3, i);
                Gas_Push_Alarm(i);
                break;
            }
        }

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ온도
        for(int i = 0; i <= 3; i++)
        {
            if(ControlActivity.temp_state <= ControlActivity.temp_guide[i]) {
                if(ControlActivity.temp_fragment != null)
                    ((SensorTempFragment)ControlActivity.temp_fragment).Refresh(i);
                if(ControlActivity.main_fragment != null)
                    ((MainFragment)ControlActivity.main_fragment).Refresh(4, i);
                Temp_Push_Alarm(i);
                break;
            }
        }

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ습도
        for(int i = 0; i <= 3; i++)
        {
            if(ControlActivity.moi_state <= ControlActivity.humid_guide[i]) {
                if(ControlActivity.gas_fragment != null)
                    ((SensorTempFragment)ControlActivity.temp_fragment).Refresh2(i);
                if(ControlActivity.main_fragment != null)
                    ((MainFragment)ControlActivity.main_fragment).Refresh(4, i);
                Humid_Push_Alarm(i);
                break;
            }
        }

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ진동
        for(int i = 0; i <= 3; i++)
        {
            if(ControlActivity.vibration_state <= ControlActivity.vib_guide[i]) {
                if(ControlActivity.vib_fragment != null)
                    ((SensorVibFragment)ControlActivity.vib_fragment).Refresh(i);
                if(ControlActivity.main_fragment != null)
                    ((MainFragment)ControlActivity.main_fragment).Refresh(5, i);
                Vib_Push_Alarm(i);
                break;
            }
        }

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ물
        for(int i = 0; i <= 3; i++)
        {
            if(ControlActivity.water_state <= ControlActivity.water_guide[i]) {
                if(ControlActivity.water_fragment != null)
                    ((SensorWaterFragment)ControlActivity.water_fragment).Refresh(i);
                if(ControlActivity.main_fragment != null)
                    ((MainFragment)ControlActivity.main_fragment).Refresh(6, i);
                Water_Push_Alarm(i);
                break;
            }
        }

    }



    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ블루투스 함수
    public void Turn_Item(String item, boolean turn)
    {
        String tempS = item + "/";

        if(turn)
            tempS = tempS + "0";
        else
            tempS = tempS + "1";
        sendData(tempS);
    }


    // 문자열 전송하는 함수(쓰레드 사용 x)
    void sendData(String msg) {
        msg += mStrDelimiter;  // 문자열 종료표시 (\n)
        try{
            // getBytes() : String을 byte로 변환
            // OutputStream.write : 데이터를 쓸때는 write(byte[]) 메소드를 사용함.
            // byte[] 안에 있는 데이터를 한번에 기록해 준다.
                mOutputStream.write(msg.getBytes());  // 문자열 전송.
        }catch(Exception e) {  // 문자열 전송 도중 오류가 발생한 경우
            Toast.makeText(MyService.this, "Error in Data transfer ",
                    Toast.LENGTH_LONG).show();
           // finish();  // App 종료
        }
    }


    // 데이터 수신(쓰레드 사용 수신된 메시지를 계속 검사함)
    void beginListenForData() {

        readBufferPosition = 0;                 // 버퍼 내 수신 문자 저장 위치.
        readBuffer = new byte[1024];            // 수신 버퍼.

        // 문자열 수신 쓰레드.
        mWorkerThread = new Thread(new Runnable()
        {
            @Override
            public void run() {
                // interrupt() 메소드를 이용 스레드를 종료시키는 예제이다.
                // interrupt() 메소드는 하던 일을 멈추는 메소드이다.
                // isInterrupted() 메소드를 사용하여 멈추었을 경우 반복문을 나가서 스레드가 종료하게 된다.
                while(!Thread.currentThread().isInterrupted()) {
                    try {
                        // InputStream.available() : 다른 스레드에서 blocking 하기 전까지 읽은 수 있는 문자열 개수를 반환함.
                        int byteAvailable = mInputStream.available();   // 수신 데이터 확인
                        if(byteAvailable > 0) {
                            // 데이터가 수신된 경우.
                            //mEditReceive.setText("데이터 수신됨");
                            byte[] packetBytes = new byte[byteAvailable];
                            // read(buf[]) : 입력스트림에서 buf[] 크기만큼 읽어서 저장 없을 경우에 -1 리턴.
                            mInputStream.read(packetBytes);
                            for(int i=0; i<byteAvailable; i++) {
                                byte b = packetBytes[i];
                                if(b == mCharDelimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    //  System.arraycopy(복사할 배열, 복사시작점, 복사된 배열, 붙이기 시작점, 복사할 개수)
                                    //  readBuffer 배열을 처음 부터 끝까지 encodedBytes 배열로 복사.
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);

                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable(){
                                        // 수신된 문자열 데이터에 대한 처리.
                                        @Override
                                        public void run() {
                                            if(data.length() < 5)
                                                return;

                                            //Log.d("로그", data);
                                            String split_string[];
                                            char split_name = data.charAt(0);
                                            String split_warning = "없음";
                                            String split_info1 = "없음";
                                            String split_info2 = "없음";

                                            if( split_name == 'A')
                                            {
                                                split_string = data.split("/");
                                                split_warning  = split_string[1];
                                                split_info1 = split_string[2];
                                                split_info2 = split_string[3];

                                                ControlActivity.temp_state = Float.parseFloat(split_info1);
                                                ControlActivity.moi_state = Float.parseFloat(split_info2);
                                            }else if( split_name == 'B')
                                            {
                                                split_string = data.split("/");
                                                split_warning  = split_string[1];
                                                split_info1 = split_string[2];

                                                ControlActivity.door_state_motion = (int)Float.parseFloat(split_info1);

                                                if(ControlActivity.door_state_motion == 1) {
                                                    ControlActivity.Add_New_Door_List();
                                                    ControlActivity.door_state_motion = 0;
                                                }
                                            }
                                            else if( split_name == 'C')
                                            {
                                                split_string = data.split("/");
                                                split_warning  = split_string[1];
                                                split_info1 = split_string[2];

                                                ControlActivity.flame_state = Float.parseFloat(split_info1);
                                            }
                                            else if( split_name == 'D')
                                            {
                                                split_string = data.split("/");
                                                split_warning  = split_string[1];
                                                split_info1 = split_string[2];

                                                ControlActivity.gas_state = Float.parseFloat(split_info1);
                                            }
                                            else if( split_name == 'E')
                                            {
                                                split_string = data.split("/");
                                                split_warning  = split_string[1];
                                                split_info1 = split_string[2];

                                                ControlActivity.vibration_state = (int)Float.parseFloat(split_info1);
                                            }
                                            else if( split_name == 'F')
                                            {
                                                split_string = data.split("/");
                                                split_warning  = split_string[1];
                                                split_info1 = split_string[2];

                                                ControlActivity.dust_state = Float.parseFloat(split_info1);
                                            }
                                            else if( split_name == 'H')
                                            {
                                                split_string = data.split("/");
                                                split_warning  = split_string[1];
                                                split_info1 = split_string[2];

                                                ControlActivity.door_state_button = (int)Float.parseFloat(split_info1);
                                            }
                                            else if( split_name == 'G')
                                            {
                                                split_string = data.split("/");
                                                split_warning  = split_string[1];
                                                split_info1 = split_string[2];

                                                ControlActivity.water_state = (int)Float.parseFloat(split_info1);
                                            }

                                            Check_State();
                                        }


                                    });
                                }
                                else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }


                    } catch (Exception e) {    // 데이터 수신 중 오류 발생.
                        Toast.makeText(MyService.this, "데이터 수신 중 오류 " + e.toString(), Toast.LENGTH_LONG).show();
                        //finish();            // App 종료.
                        Thread.currentThread().interrupt();
                        ControlActivity.controlActivity.End_Service();
                        //stopSelf();

                    }
                }
            }

        });
        mWorkerThread.start();


    }
}


