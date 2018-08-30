package kr.ac.knu.housekeeper;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class MainFragment extends Fragment {

    ImageButton door = null;
    ImageButton gas = null;
    ImageButton dust = null;
    ImageButton temp = null;
    ImageButton flame = null;
    ImageButton vib = null;
    ImageButton water = null;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main, container, false);


        door = (ImageButton)view.findViewById(R.id.door);
        gas = (ImageButton)view.findViewById(R.id.gas);
        dust = (ImageButton)view.findViewById(R.id.dust);
        temp = (ImageButton)view.findViewById(R.id.temperature);
        flame = (ImageButton)view.findViewById(R.id.flame);
        vib = (ImageButton)view.findViewById(R.id.vibration);
        water = (ImageButton)view.findViewById(R.id.water);
        //ImageButton bluetooth = (ImageButton)view.findViewById(R.id.bluetooth);




        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ버튼클릭시 화면이동

        door.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ControlActivity.controlActivity.top_door_button.callOnClick();
            }

        });

        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlActivity.controlActivity.top_gas_button.callOnClick();
            }
        });

        dust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlActivity.controlActivity.top_dust_button.callOnClick();
            }
        });


        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlActivity.controlActivity.top_degree_button.callOnClick();
            }
        });


        vib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlActivity.controlActivity.top_quake_button.callOnClick();
            }
        });

        flame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlActivity.controlActivity.top_flame_button.callOnClick();
            }
        });


        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlActivity.controlActivity.top_water_button.callOnClick();
            }
        });


        if(MyService.myService != null)
            MyService.myService.Check_State();

        return view;
    }

    public void Refresh(int sensor_num, int check_state)
    {
        switch(sensor_num)
        {
            case 0:
                if(door == null)
                    return;
                if(check_state == 0)
                    door.setImageResource(R.drawable.lock_1);
                else
                    door.setImageResource(R.drawable.lock_2);
                break;
            case 1:
                if(dust == null)
                    return;
                if(check_state == 0)
                    dust.setImageResource(R.drawable.air_1);
                else if(check_state == 1)
                    door.setImageResource(R.drawable.air_1);
                else if(check_state == 2)
                    door.setImageResource(R.drawable.air_2);
                else
                    door.setImageResource(R.drawable.air_2);
                break;
            case 2:
                if(flame == null)
                    return;
                if(check_state == 0)
                    flame.setImageResource(R.drawable.housefire_1);
                else if(check_state == 1)
                    flame.setImageResource(R.drawable.housefire_1);
                else if(check_state == 2)
                    flame.setImageResource(R.drawable.housefire_2);
                else
                    flame.setImageResource(R.drawable.housefire_2);
                break;
            case 3:
                if(gas == null)
                    return;
                if(check_state == 0)
                    gas.setImageResource(R.drawable.fire_1);
                else if(check_state == 1)
                    gas.setImageResource(R.drawable.fire_1);
                else if(check_state == 2)
                    gas.setImageResource(R.drawable.fire_2);
                else
                    gas.setImageResource(R.drawable.fire_2);
                break;
            case 4:
                if(temp == null)
                    return;
                if(check_state == 0)
                    temp.setImageResource(R.drawable.temp_1);
                else if(check_state == 1)
                    temp.setImageResource(R.drawable.temp_1);
                else if(check_state == 2)
                    temp.setImageResource(R.drawable.temp_2);
                else
                    temp.setImageResource(R.drawable.temp_2);
                break;
            case 5:
                if(vib == null)
                    return;
                if(check_state == 0)
                    vib.setImageResource(R.drawable.earth_1);
                else if(check_state == 1)
                    vib.setImageResource(R.drawable.earth_1);
                else if(check_state == 2)
                    vib.setImageResource(R.drawable.earth_2);
                else
                    vib.setImageResource(R.drawable.earth_2);
                break;
            case 6:
                if(water == null)
                    return;
                if(check_state == 0)
                    water.setImageResource(R.drawable.water_1);
                else if(check_state == 1)
                    water.setImageResource(R.drawable.water_1);
                else if(check_state == 2)
                    water.setImageResource(R.drawable.water_2);
                else
                    water.setImageResource(R.drawable.water_2);
                break;
        }

    }



}
