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


public class SensorFlameFragment extends Fragment {

    ImageView flame_state_image = null;
    TextView flame_state_text = null;
    TextView flame_state_number = null;
    TextView[] base_text = new TextView[3];
    Switch my_switch = null;

    public void Refresh_Switch()
    {
        if(my_switch != null)
            my_switch.setChecked(ControlActivity.flame_led);
    }

    public void Refresh_Text()
    {
        for(int i = 0 ; i < 3 ; i++)
            base_text[i].setText(String.valueOf(ControlActivity.flame_guide[i]));
    }

    public SensorFlameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_flame, container, false);

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ초기화

        flame_state_image = (ImageView)view.findViewById(R.id.flame_state_image);
        flame_state_text = view.findViewById(R.id.flame_state_text);
        flame_state_number = view.findViewById(R.id.flame_state_number);
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
                    ControlActivity.flame_led = b;
                    ControlActivity.gas_led = b;
                    MyService.myService.Turn_Item("B", b);
                }
            }
        });
        Refresh_Switch();
        Refresh_Text();

        return view;
    }

    public void Refresh(int check_state)
    {
        float flame_state = ControlActivity.controlActivity.flame_state;


        if(flame_state_number != null)
            flame_state_number.setText(String.valueOf(flame_state));

        //ControlActivity.controlActivity.listViewAdapterForDoor.notifyDataSetChanged();
        if(check_state == -1) {


        }
        else if(check_state == 0) {
            if(flame_state_image != null)
                flame_state_image.setImageResource(R.drawable.housefire_1);

            if(flame_state_text != null)
                flame_state_text.setText(R.string.good);
        }
        else  if(check_state == 1){
            if(flame_state_image != null)
                flame_state_image.setImageResource(R.drawable.housefire_1);

            if(flame_state_text != null)
                flame_state_text.setText(R.string.normal);
        }
        else  if(check_state == 2){
            if(flame_state_image != null)
                flame_state_image.setImageResource(R.drawable.housefire_2);

            if(flame_state_text != null)
                flame_state_text.setText(R.string.bad);
        }
        else {
            if(flame_state_image != null)
                flame_state_image.setImageResource(R.drawable.housefire_2);

            if(flame_state_text != null)
                flame_state_text.setText(R.string.very_bad);
        }
    }

}
