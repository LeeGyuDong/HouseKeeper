package kr.ac.knu.housekeeper;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


public class SensorTempFragment extends Fragment {


    ImageView state_image = null;
    TextView state_text = null;
    TextView state_number = null;

    ImageView state_image2 = null;
    TextView state_text2 = null;
    TextView state_number2 = null;

    TextView[] base_text = new TextView[3];
    TextView[] base_text2 = new TextView[3];
    Switch my_switch = null;
    Switch my_switch2 = null;

    public void Refresh_Switch()
    {
        if(my_switch != null)
            my_switch.setChecked(ControlActivity.temp_led);
        if(my_switch2 != null)
            my_switch2.setChecked(ControlActivity.humid_led);
    }

    public void Refresh_Text()
    {
        for(int i = 0 ; i < 3 ; i++) {
            base_text[i].setText(String.valueOf(ControlActivity.temp_guide[i]));
            base_text2[i].setText(String.valueOf(ControlActivity.humid_guide[i]));
        }
    }

    public SensorTempFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_temp, container, false);

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ초기화

        state_image = view.findViewById(R.id.degree_state_image);
        state_text = view.findViewById(R.id.degree_state_text);
        state_number = view.findViewById(R.id.degree_state_number);

        state_image2 = view.findViewById(R.id.humid_state_image);
        state_text2 = view.findViewById(R.id.humid_state_text);
        state_number2 = view.findViewById(R.id.humid_state_number);
        base_text[0] = view.findViewById(R.id.base_text_1);
        base_text[1] = view.findViewById(R.id.base_text_2);
        base_text[2] = view.findViewById(R.id.base_text_3);
        base_text2[0] = view.findViewById(R.id.base_text_4);
        base_text2[1] = view.findViewById(R.id.base_text_5);
        base_text2[2] = view.findViewById(R.id.base_text_6);
        my_switch = view.findViewById(R.id.sw);
        my_switch2 = view.findViewById(R.id.sw2);

        if(MyService.myService != null)
            MyService.myService.Check_State();
        else {
            Refresh(-1);
            Refresh2(-1);
        }

        my_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(MyService.myService != null) {
                    ControlActivity.temp_led = b;
                    ControlActivity.humid_led = b;
                    my_switch2.setChecked(b);
                    //MyService.myService.Turn_Item("D", b);
                }
            }
        });
        my_switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(MyService.myService != null) {
                    ControlActivity.humid_led = b;
                    ControlActivity.temp_led = b;
                    my_switch.setChecked(b);
                    MyService.myService.Turn_Item("D", b);
                }
            }
        });
        Refresh_Switch();
        Refresh_Text();

        return view;
    }

    public void Refresh(int check_state)
    {
        float temp_state = ControlActivity.controlActivity.temp_state;


        if(state_number != null)
            state_number.setText(String.valueOf(temp_state));

        //ControlActivity.controlActivity.listViewAdapterForDoor.notifyDataSetChanged();

        if(check_state == -1) {


        }
        else if(check_state == 0) {
            if(state_image != null)
                state_image.setImageResource(R.drawable.temp_1);

            if(state_text != null)
                state_text.setText(R.string.good);
        }
        else  if(check_state == 1){
            if(state_image != null)
                state_image.setImageResource(R.drawable.temp_1);

            if(state_text != null)
                state_text.setText(R.string.normal);
        }
        else  if(check_state == 2){
            if(state_image != null)
                state_image.setImageResource(R.drawable.temp_2);

            if(state_text != null)
                state_text.setText(R.string.bad);
        }
        else {
            if(state_image != null)
                state_image.setImageResource(R.drawable.temp_2);

            if(state_text != null)
                state_text.setText(R.string.very_bad);
        }
    }




    public void Refresh2(int check_state)
    {
        float humid_state = ControlActivity.controlActivity.moi_state;

        if(state_number2 != null)
            state_number2.setText(String.valueOf(humid_state));

        //ControlActivity.controlActivity.listViewAdapterForDoor.notifyDataSetChanged();

        if(check_state == -1) {


        }
        else if(check_state == 0) {
            if(state_image2 != null)
                state_image2.setImageResource(R.drawable.humid_1);

            if(state_text2 != null)
                state_text2.setText(R.string.good);
        }
        else  if(check_state == 1){
            if(state_image2 != null)
                state_image2.setImageResource(R.drawable.humid_1);

            if(state_text2 != null)
                state_text2.setText(R.string.normal);
        }
        else  if(check_state == 2){
            if(state_image2 != null)
                state_image2.setImageResource(R.drawable.humid_2);

            if(state_text2 != null)
                state_text2.setText(R.string.bad);
        }
        else {
            if(state_image2 != null)
                state_image2.setImageResource(R.drawable.humid_2);

            if(state_text2 != null)
                state_text2.setText(R.string.very_bad);
        }
    }
}
