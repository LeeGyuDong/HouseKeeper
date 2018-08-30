package kr.ac.knu.housekeeper;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;


public class SensorDoorFragment extends Fragment {

    Button today_button;
    Button all_button;
    ImageView door_state_image = null;
    TextView door_state_text = null;
    Switch my_switch = null;

    public void Refresh_Switch()
    {
        if(my_switch != null)
            my_switch.setChecked(ControlActivity.door_led);
    }

    public SensorDoorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensor_door, container, false);

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ초기화

        ListView listview = (ListView) view.findViewById(R.id.door_list_view);
        listview.setAdapter(ControlActivity.controlActivity.listViewAdapterForDoor);

        today_button = (Button)view.findViewById(R.id.today_button);
        all_button = (Button)view.findViewById(R.id.all_button);
        door_state_image = (ImageView)view.findViewById(R.id.door_state_image);
        door_state_text = view.findViewById(R.id.door_state_text);
        my_switch = view.findViewById(R.id.sw);


        Toggle_Button(ControlActivity.listViewAdapterForDoor.all_show);

        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ이벤트 등록
        today_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Toggle_Button(false);
                return false;
            }
        });

        all_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Toggle_Button(true);
                return false;
            }
        });

        my_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(MyService.myService != null) {
                    ControlActivity.door_led = b;
                    ControlActivity.water_led = b;
                    MyService.myService.Turn_Item("E", b);
                }
            }
        });
        Refresh_Switch();

        if(MyService.myService != null)
            MyService.myService.Check_State();

        return view;
    }

    public void Refresh(int check_state)
    {
        //ControlActivity.controlActivity.listViewAdapterForDoor.notifyDataSetChanged();

        if(check_state == 1) {

            if(door_state_image != null)
                door_state_image.setImageResource(R.drawable.lock_2);

            if(door_state_text != null) {
                door_state_text.setText("Unlock");
            }

        }
        else {
            if (door_state_image != null)
                door_state_image.setImageResource(R.drawable.lock_1);

            if(door_state_text != null) {
                door_state_text.setText("Lock");

            }
        }
    }

    private void Toggle_Button(boolean all)
    {
        if(all)
        {
            all_button.setBackgroundResource(R.drawable.outline);
            today_button.setBackgroundResource(R.drawable.click_outline);

            ControlActivity.controlActivity.listViewAdapterForDoor.all_show = true;
            ControlActivity.controlActivity.listViewAdapterForDoor.notifyDataSetChanged();
        }
        else
        {
            today_button.setBackgroundResource(R.drawable.outline);
            all_button.setBackgroundResource(R.drawable.click_outline);

            ControlActivity.controlActivity.listViewAdapterForDoor.all_show = false;
            ControlActivity.controlActivity.listViewAdapterForDoor.notifyDataSetChanged();
        }
    }

}
