package kr.ac.knu.housekeeper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PopSettingActivity extends Activity {

    Button confirm_button = null;
    Button back_button = null;
    TextView title = null;
    EditText[] editText = new EditText[3];
    TextView param_text = null;
    //ImageView param_image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_setting);

        //데이터 가져오기
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        confirm_button = findViewById(R.id.confirm_button);
        back_button = findViewById(R.id.back_button);
        title = findViewById(R.id.pop_title);
        editText[0] = findViewById(R.id.first_edit);
        editText[1] = findViewById(R.id.second_edit);
        editText[2] = findViewById(R.id.last_edit);
        param_text = findViewById(R.id.param);
        //param_image = findViewById(R.id.param2);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Initiaialize(name);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    public void Initiaialize(String name)
    {
        if(name.equals("dust"))
        {
            title.setText("Dust Reference");
            param_text.setVisibility(View.VISIBLE);
            param_text.setText("(g/m^3)");
            //param_image.setVisibility(View.VISIBLE);

            for(int i = 0 ; i < 3 ; i++)
                editText[i].setText(String.valueOf(ControlActivity.dust_guide[i]));

            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0 ; i < 3 ; i++)
                    {
                        if (editText[i].getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "please, Fill the blank.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            ControlActivity.dust_guide[i] = Integer.valueOf(editText[i].getText().toString());
                        }
                    }

                    mOnClose();
                }
            });
        }
        else if(name.equals("fire"))
        {
            title.setText("Fire Reference");
            param_text.setVisibility(View.VISIBLE);
            //param_image.setVisibility(View.INVISIBLE);
            param_text.setText("(nm)");

            for(int i = 0 ; i < 3 ; i++)
                editText[i].setText(String.valueOf(ControlActivity.flame_guide[i]));

            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0 ; i < 3 ; i++)
                    {
                        if (editText[i].getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "please, Fill the blank.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            ControlActivity.flame_guide[i] = Integer.valueOf(editText[i].getText().toString());
                        }
                    }

                    mOnClose();
                }
            });
        }
        else if(name.equals("gas"))
        {
            title.setText("Gas Reference");
            param_text.setVisibility(View.VISIBLE);
            //param_image.setVisibility(View.INVISIBLE);
            param_text.setText("(level)");

            for(int i = 0 ; i < 3 ; i++)
                editText[i].setText(String.valueOf(ControlActivity.gas_guide[i]));

            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0 ; i < 3 ; i++)
                    {
                        if (editText[i].getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "please, Fill the blank.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            ControlActivity.gas_guide[i] = Integer.valueOf(editText[i].getText().toString());
                        }
                    }

                    mOnClose();
                }
            });
        }
        else if(name.equals("temp"))
        {
            title.setText("Temperature Reference");
            param_text.setVisibility(View.VISIBLE);
            //param_image.setVisibility(View.INVISIBLE);
            param_text.setText("(℃)");

            for(int i = 0 ; i < 3 ; i++)
                editText[i].setText(String.valueOf(ControlActivity.temp_guide[i]));

            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0 ; i < 3 ; i++)
                    {
                        if (editText[i].getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "please, Fill the blank.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            ControlActivity.temp_guide[i] = Integer.valueOf(editText[i].getText().toString());
                        }
                    }

                    mOnClose();
                }
            });
        }
        else if(name.equals("humi"))
        {
            title.setText("Humidity Reference");
            param_text.setVisibility(View.VISIBLE);
            //param_image.setVisibility(View.INVISIBLE);
            param_text.setText("(%)");

            for(int i = 0 ; i < 3 ; i++)
                editText[i].setText(String.valueOf(ControlActivity.humid_guide[i]));

            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0 ; i < 3 ; i++)
                    {
                        if (editText[i].getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "please, Fill the blank.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            ControlActivity.humid_guide[i] = Integer.valueOf(editText[i].getText().toString());
                        }
                    }

                    mOnClose();
                }
            });
        }
        else if(name.equals("vib"))
        {
            title.setText("Quake Reference");
            param_text.setVisibility(View.VISIBLE);
            //param_image.setVisibility(View.INVISIBLE);
            param_text.setText("(level)");

            for(int i = 0 ; i < 3 ; i++)
                editText[i].setText(String.valueOf(ControlActivity.vib_guide[i]));

            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0 ; i < 3 ; i++)
                    {
                        if (editText[i].getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "please, Fill the blank.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            ControlActivity.vib_guide[i] = Integer.valueOf(editText[i].getText().toString());
                        }
                    }

                    mOnClose();
                }
            });
        }
        else if(name.equals("water"))
        {
            title.setText("Water Reference");
            param_text.setVisibility(View.VISIBLE);
            //param_image.setVisibility(View.INVISIBLE);
            param_text.setText("(level)");

            for(int i = 0 ; i < 3 ; i++)
                editText[i].setText(String.valueOf(ControlActivity.water_guide[i]));

            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0 ; i < 3 ; i++)
                    {
                        if (editText[i].getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "please, Fill the blank.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            ControlActivity.water_guide[i] = Integer.valueOf(editText[i].getText().toString());
                        }
                    }

                    mOnClose();
                }
            });
        }


    }

    //확인 버튼 클릭
    public void mOnClose(){
        //데이터 전달하기
        //Intent intent = new Intent();
        //intent.putExtra("fragment", "setting");
        //setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

}


