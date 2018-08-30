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


public class SensorVibFragment extends Fragment {


    ImageView state_image = null;
    TextView state_text = null;
    TextView state_number = null;
    TextView[] base_text = new TextView[3];
    Switch my_switch = null;

    public void Refresh_Switch()
    {
        if(my_switch != null)
            my_switch.setChecked(ControlActivity.vib_led);
    }


    public void Refresh_Text()
    {
        for(int i = 0 ; i < 3 ; i++)
            base_text[i].setText(String.valueOf(ControlActivity.vib_guide[i]));
    }

    public SensorVibFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_vib, container, false);

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ초기화

        state_image = (ImageView)view.findViewById(R.id.vib_state_image);
        state_text = view.findViewById(R.id.vib_state_text);
        state_number = view.findViewById(R.id.vib_state_number);
        base_text[0] = view.findViewById(R.id.base_text_1);
        base_text[1] = view.findViewById(R.id.base_text_2);
        base_text[2] = view.findViewById(R.id.base_text_3);
        my_switch = view.findViewById(R.id.sw);

        if(MyService.myService != null)
            MyService.myService.Check_State();
        else
            Refresh(-1);

        my_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(MyService.myService != null) {
                    ControlActivity.vib_led = b;
                    MyService.myService.Turn_Item("C", b);
                }
            }
        });
        Refresh_Switch();
        Refresh_Text();

        return view;
    }

    public void Refresh(int check_state)
    {
        float temp_state = ControlActivity.controlActivity.vibration_state;


        if(state_number != null)
            state_number.setText(String.valueOf(temp_state));

        //ControlActivity.controlActivity.listViewAdapterForDoor.notifyDataSetChanged();

        if(check_state == -1) {


        }
        else if(check_state == 0) {
            if(state_image != null)
                state_image.setImageResource(R.drawable.earth_1);

            if(state_text != null)
                state_text.setText(R.string.good);
        }
        else  if(check_state == 1){
            if(state_image != null)
                state_image.setImageResource(R.drawable.earth_1);

            if(state_text != null)
                state_text.setText(R.string.normal);
        }
        else  if(check_state == 2){
            if(state_image != null)
                state_image.setImageResource(R.drawable.earth_2);

            if(state_text != null)
                state_text.setText(R.string.bad);
        }
        else {
            if(state_image != null)
                state_image.setImageResource(R.drawable.earth_2);

            if(state_text != null)
                state_text.setText(R.string.very_bad);
        }
    }
}
