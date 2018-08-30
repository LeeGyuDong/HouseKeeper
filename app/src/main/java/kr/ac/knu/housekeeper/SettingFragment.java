package kr.ac.knu.housekeeper;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class SettingFragment extends Fragment {

    String c = "#FF4081";
    String cc = "#aaaaaa";
    String ccc = "#70ac47";

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ서비스
    private Switch bluetooth_switch;
    private Switch alarm_all_switch;
    private CheckBox door_box;
    private CheckBox dust_box;
    private CheckBox flame_box;
    private CheckBox gas_box;
    private CheckBox temp_box;
    private CheckBox humi_box;
    private CheckBox vib_box;
    private CheckBox water_box;

    private CheckBox door_auto;
    private CheckBox dust_auto;
    private CheckBox flame_auto;
    private CheckBox gas_auto;
    private CheckBox temp_auto;
    private CheckBox humi_auto;
    private CheckBox vib_auto;
    private CheckBox water_auto;

    private Button dust_button;
    private Button flame_button;
    private Button gas_button;
    private Button temp_button;
    private Button humi_button;
    private Button vib_button;
    private Button water_button;



    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ함수ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    public SettingFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ초기화

        View view =  inflater.inflate(R.layout.fragment_setting, container, false);

        bluetooth_switch = view.findViewById(R.id.bluetooth_switch);
        alarm_all_switch = view.findViewById(R.id.alarm_all_switch);
        door_box = view.findViewById(R.id.door_checkbox);
        dust_box = view.findViewById(R.id.dust_checkbox);
        flame_box = view.findViewById(R.id.firec_checkbox);
        gas_box = view.findViewById(R.id.gasd_checkbox);
        temp_box = view.findViewById(R.id.temperature_checkbox);
        humi_box = view.findViewById(R.id.humidity_checkbox);
        vib_box = view.findViewById(R.id.quaked_checkbox);
        water_box = view.findViewById(R.id.waterd_checkbox);

        door_auto = view.findViewById(R.id.door_auto);
        dust_auto = view.findViewById(R.id.dust_auto);
        flame_auto = view.findViewById(R.id.fire_auto);
        gas_auto = view.findViewById(R.id.gas_auto);
        temp_auto = view.findViewById(R.id.temperature_auto);
        humi_auto = view.findViewById(R.id.humidity_auto);
        vib_auto = view.findViewById(R.id.quake_auto);
        water_auto = view.findViewById(R.id.water_auto);

        dust_button = view.findViewById(R.id.dust_button);
        gas_button = view.findViewById(R.id.gas_button);
        flame_button = view.findViewById(R.id.fire_button);
        temp_button = view.findViewById(R.id.temp_button);
        humi_button = view.findViewById(R.id.humi_button);
        vib_button = view.findViewById(R.id.quake_button);
        water_button = view.findViewById(R.id.water_button);


        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ이벤트

        bluetooth_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    ControlActivity.controlActivity.Start_Service();
                }
                else
                {
                    ControlActivity.controlActivity.End_Service();
                }
            }
        });

        alarm_all_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    Turn_Alert_Switch(true);
                }
                else
                {
                    Turn_Alert_Switch(false);
                }
            }
        });

        door_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.door_alert_check = b;
                Auto_Setting(0, b);
            }
        });

        dust_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.dust_alert_check = b;
                Auto_Setting(1, b);
            }
        });

        flame_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.flame_alert_check = b;
                Auto_Setting(2, b);
            }
        });

        gas_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.gas_alert_check = b;
                Auto_Setting(3, b);
            }
        });

        temp_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.temp_alert_check = b;
                Auto_Setting(4, b);
            }
        });

        humi_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.humid_alert_check = b;
                Auto_Setting(5, b);
            }
        });

        vib_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.vib_alert_check = b;
                Auto_Setting(6, b);
            }
        });

        water_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.water_alert_check = b;
                Auto_Setting(7, b);
            }
        });

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ오토

        door_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.door_auto_control = b;
            }
        });

        dust_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.dust_auto_control = b;
            }
        });

        flame_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.flame_auto_control = b;
            }
        });

        gas_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.gas_auto_control = b;
            }
        });

        temp_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.temp_auto_control = b;
            }
        });

        humi_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.humid_auto_control = b;
            }
        });

        vib_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.vib_auto_control = b;
            }
        });

        water_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ControlActivity.water_auto_control = b;
            }
        });


        //ㅡㅡㅡㅡㅡ상한값 세팅

        dust_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlActivity.controlActivity.Popup_Setting("dust", view);
            }
        });

        flame_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlActivity.controlActivity.Popup_Setting("fire", view);
            }
        });

        gas_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlActivity.controlActivity.Popup_Setting("gas", view);
            }
        });

        temp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlActivity.controlActivity.Popup_Setting("temp", view);
            }
        });

        humi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlActivity.controlActivity.Popup_Setting("humi", view);
            }
        });

        vib_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlActivity.controlActivity.Popup_Setting("vib", view);
            }
        });

        water_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlActivity.controlActivity.Popup_Setting("water", view);
            }
        });


        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ초기화

        if(MyService.myService == null)
            Turn_Bluetooth_Switch(false);
        else
            Turn_Bluetooth_Switch(true);


        Turn_Alert_Switch(ControlActivity.all_alert_check);
        Sync_Alarm_Setting();

        view.findViewById(R.id.go_dust).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlActivity.controlActivity.top_dust_button.callOnClick();
            }
        });



        return view;
    }

    public void Sync_Alarm_Setting()
    {
        if(door_box == null || gas_box == null || flame_box == null || dust_box == null || temp_box == null || humi_box == null || vib_box == null || water_box == null
                || door_auto == null || gas_auto == null || flame_auto == null || dust_auto == null || temp_auto == null || humi_auto == null || vib_auto == null || water_auto == null)
            return;

        door_box.setChecked(ControlActivity.door_alert_check);
        Auto_Setting(0, ControlActivity.door_alert_check);
        gas_box.setChecked(ControlActivity.gas_alert_check);
        Auto_Setting(1, ControlActivity.gas_alert_check);
        flame_box.setChecked(ControlActivity.flame_alert_check);
        Auto_Setting(2, ControlActivity.flame_alert_check);
        dust_box.setChecked(ControlActivity.dust_alert_check);
        Auto_Setting(3, ControlActivity.dust_alert_check);
        temp_box.setChecked(ControlActivity.temp_alert_check);
        Auto_Setting(4, ControlActivity.temp_alert_check);
        humi_box.setChecked(ControlActivity.humid_alert_check);
        Auto_Setting(5, ControlActivity.humid_alert_check);
        vib_box.setChecked(ControlActivity.vib_alert_check);
        Auto_Setting(6, ControlActivity.vib_alert_check);
        water_box.setChecked(ControlActivity.water_alert_check);
        Auto_Setting(7, ControlActivity.water_alert_check);

        door_auto.setChecked(ControlActivity.door_auto_control);
        gas_auto.setChecked(ControlActivity.gas_auto_control);
        flame_auto.setChecked(ControlActivity.flame_auto_control);
        dust_auto.setChecked(ControlActivity.dust_auto_control);
        temp_auto.setChecked(ControlActivity.temp_auto_control);
        humi_auto.setChecked(ControlActivity.humid_auto_control);
        vib_auto.setChecked(ControlActivity.vib_auto_control);
        water_auto.setChecked(ControlActivity.water_auto_control);
    }

    void Sensor_Checkbox_Setting(int i, boolean show)
    {
        switch (i)
        {
            case 0:
                if(show)
                {
                    door_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(c)));
                    door_box.setTextColor(Color.BLACK);
                }
                else
                {
                    door_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    door_box.setTextColor(ContextCompat.getColor(getContext(), R.color.setting_grey));
                }
                break;
            case 1:
                if(show)
                {
                    dust_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(c)));
                    dust_box.setTextColor(Color.BLACK);
                }
                else
                {
                    dust_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    dust_box.setTextColor(ContextCompat.getColor(getContext(), R.color.setting_grey));
                }
                break;
            case 2:
                if(show)
                {
                    flame_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(c)));
                    flame_box.setTextColor(Color.BLACK);
                }
                else
                {
                    flame_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    flame_box.setTextColor(ContextCompat.getColor(getContext(), R.color.setting_grey));
                }
                break;
            case 3:
                if(show)
                {
                    gas_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(c)));
                    gas_box.setTextColor(Color.BLACK);
                }
                else
                {
                    gas_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    gas_box.setTextColor(ContextCompat.getColor(getContext(), R.color.setting_grey));
                }
                break;
            case 4:
                if(show)
                {
                    temp_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(c)));
                    temp_box.setTextColor(Color.BLACK);
                }
                else
                {
                    temp_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    temp_box.setTextColor(ContextCompat.getColor(getContext(), R.color.setting_grey));
                }
                break;
            case 5:
                if(show)
                {
                    humi_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(c)));
                    humi_box.setTextColor(Color.BLACK);
                }
                else
                {
                    humi_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    humi_box.setTextColor(ContextCompat.getColor(getContext(), R.color.setting_grey));
                }
                break;
            case 6:
                if(show)
                {
                    vib_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(c)));
                    vib_box.setTextColor(Color.BLACK);
                }
                else
                {
                    vib_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    vib_box.setTextColor(ContextCompat.getColor(getContext(), R.color.setting_grey));
                }
                break;
            case 7:
                if(show)
                {
                    water_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(c)));
                    water_box.setTextColor(Color.BLACK);
                }
                else
                {
                    water_box.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    water_box.setTextColor(ContextCompat.getColor(getContext(), R.color.setting_grey));
                }
                break;
        }
    }

    void Auto_Setting(int i, boolean show)
    {
        switch (i)
        {
            case 0:
                if(show)
                {
                    door_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ccc)));
                    door_auto.setTextColor(Color.BLACK);
                }
                else
                {
                    door_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    door_auto.setTextColor(Color.GRAY);
                }
                break;
            case 1:
                if(show)
                {
                    dust_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ccc)));
                    dust_auto.setTextColor(Color.BLACK);
                }
                else
                {
                    dust_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    dust_auto.setTextColor(Color.GRAY);
                }
                break;
            case 2:
                if(show)
                {
                    flame_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ccc)));
                    flame_auto.setTextColor(Color.BLACK);
                }
                else
                {
                    flame_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    flame_auto.setTextColor(Color.GRAY);
                }
                break;
            case 3:
                if(show)
                {
                    gas_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ccc)));
                    gas_auto.setTextColor(Color.BLACK);
                }
                else
                {
                    gas_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    gas_auto.setTextColor(Color.GRAY);
                }
                break;
            case 4:
                if(show)
                {
                    temp_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ccc)));
                    temp_auto.setTextColor(Color.BLACK);
                }
                else
                {
                    temp_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    temp_auto.setTextColor(Color.GRAY);
                }
                break;
            case 5:
                if(show)
                {
                    humi_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ccc)));
                    humi_auto.setTextColor(Color.BLACK);
                }
                else
                {
                    humi_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    humi_auto.setTextColor(Color.GRAY);
                }
                break;
            case 6:
                if(show)
                {
                    vib_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ccc)));
                    vib_auto.setTextColor(Color.BLACK);
                }
                else
                {
                    vib_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    vib_auto.setTextColor(Color.GRAY);
                }
                break;
            case 7:
                if(show)
                {
                    water_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ccc)));
                    water_auto.setTextColor(Color.BLACK);
                }
                else
                {
                    water_auto.setButtonTintList(ColorStateList.valueOf(Color.parseColor(cc)));
                    water_auto.setTextColor(Color.GRAY);
                }
                break;
        }
    }

    void Turn_Alert_Switch(boolean b)
    {
        door_box.setClickable(b);
        dust_box.setClickable(b);
        flame_box.setClickable(b);
        gas_box.setClickable(b);
        temp_box.setClickable(b);
        humi_box.setClickable(b);
        vib_box.setClickable(b);
        water_box.setClickable(b);

        door_auto.setClickable(b);
        dust_auto.setClickable(b);
        flame_auto.setClickable(b);
        gas_auto.setClickable(b);
        temp_auto.setClickable(b);
        humi_auto.setClickable(b);
        vib_auto.setClickable(b);
        water_auto.setClickable(b);


        ControlActivity.all_alert_check = b;


        for(int i = 0 ; i <= 7 ; i++)
            Sensor_Checkbox_Setting(i, b);

        for(int i = 0 ; i <= 7 ; i++)
            Auto_Setting(i, b);

        if(b)
            Sync_Alarm_Setting();
    }

    public void Turn_Bluetooth_Switch(boolean b)
    {
        if(b)
        {
            ControlActivity.controlActivity.Start_Service();
        }
        else
        {
            ControlActivity.controlActivity.End_Service();
        }
        bluetooth_switch.setChecked(b);
    }



}
