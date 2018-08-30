package kr.ac.knu.housekeeper;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;


public class SensorDustFragment extends Fragment {

    ImageView dust_state_image = null;
    TextView dust_state_text = null;
    TextView dust_state_number = null;
    ImageButton info_button = null;
    ImageButton info_view = null;
    Switch my_switch = null;
    TextView[] base_text = new TextView[3];

    public void Refresh_Switch()
    {
        if(my_switch != null)
            my_switch.setChecked(ControlActivity.dust_led);
    }

    public void Refresh_Text()
    {
        for(int i = 0 ; i < 3 ; i++)
            base_text[i].setText(String.valueOf(ControlActivity.dust_guide[i]));
    }

    public SensorDustFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_dust, container, false);

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ초기화

        dust_state_image = (ImageView)view.findViewById(R.id.dust_state_image);
        dust_state_text = view.findViewById(R.id.dust_state_text);
        dust_state_number = view.findViewById(R.id.dust_state_number);
        info_button = view.findViewById(R.id.info_button);
        info_view = view.findViewById(R.id.dust_info);
        base_text[0] = view.findViewById(R.id.base_text_1);
        base_text[1] = view.findViewById(R.id.base_text_2);
        base_text[2] = view.findViewById(R.id.base_text_3);
        my_switch = view.findViewById(R.id.sw);

        if(MyService.myService != null)
            MyService.myService.Check_State();
        else
            Refresh(-1);


        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ이벤트 등록
        info_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toggle_Info(true);
                return false;
            }
        });

        info_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toggle_Info(false);
                return false;
            }
        });

        my_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(MyService.myService != null) {
                    ControlActivity.dust_led = b;
                    MyService.myService.Turn_Item("A", b);
                }
            }
        });
        Refresh_Switch();
        Refresh_Text();


        return view;
    }

    void Toggle_Info(boolean show)
    {
        if(show) {
            info_view.setVisibility(View.VISIBLE);
            info_view.setEnabled(true);
        }
        else {
            info_view.setVisibility(View.INVISIBLE);
            info_view.setEnabled(false);
        }
    }



    public void Refresh(int check_state)
    {
        if(dust_state_number != null)
            dust_state_number.setText(String.valueOf(ControlActivity.controlActivity.dust_state));



        //ControlActivity.controlActivity.listViewAdapterForDoor.notifyDataSetChanged();
        if(check_state == -1) {


        }
        else if(check_state == 0) {

            if(dust_state_image != null)
                dust_state_image.setImageResource(R.drawable.air_1);

            if(dust_state_text != null)
                dust_state_text.setText(R.string.good);
        }
        else  if(check_state == 1){
            if(dust_state_image != null)
                dust_state_image.setImageResource(R.drawable.air_1);

            if(dust_state_text != null)
                dust_state_text.setText(R.string.normal);
        }
        else  if(check_state == 2){
            if(dust_state_image != null)
                dust_state_image.setImageResource(R.drawable.air_2);

            if(dust_state_text != null)
                dust_state_text.setText(R.string.bad);
        }
        else {
            if(dust_state_image != null)
                dust_state_image.setImageResource(R.drawable.air_2);

            if(dust_state_text != null)
                dust_state_text.setText(R.string.very_bad);
        }
    }

}
