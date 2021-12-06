package com.example.cat_water;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.charts.LineChart;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {

    EditText BMI, GOAL;
    TextView Today1, Today2, Dbtest;
    LineChart lineChart;
    ImageView Cup1, Cup2, Pet1, Pet2;;
    ImageView profile;
    Button Up1, Up2, upload;
    final int REQUEST_CODE = 1;


    int cupID[] = new int[3];
    private int count1 = 0;
    private int count2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("고양이랑 같이 물 마시라옹");

        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "drink-db")
                .allowMainThreadQueries()
                .build();

        TabHost tabhost = (TabHost) findViewById(R.id.Tabhost1);
        tabhost.setup();

        TabHost.TabSpec ts1 = tabhost.newTabSpec("cat");
        ts1.setContent(R.id.cat);
        ts1.setIndicator("cat");
        tabhost.addTab(ts1);

        TabHost.TabSpec ts2 = tabhost.newTabSpec("human");
        ts2.setContent(R.id.human);
        ts2.setIndicator("human");
        tabhost.addTab(ts2);

        TabHost.TabSpec ts3 = tabhost.newTabSpec("data");
        ts3.setContent(R.id.data);
        ts3.setIndicator("data");
        tabhost.addTab(ts3);



        Cup1 = (ImageView)findViewById(R.id.cup1);
        Cup2 = (ImageView)findViewById(R.id.cup2);
        profile = (ImageView) findViewById(R.id.Profile);
        Pet1 = (ImageView)findViewById(R.id.pet1);
        Pet2 = (ImageView)findViewById(R.id.pet2);
        Button BmiBtn = findViewById(R.id.bmibtn);
        Button Up1 = findViewById(R.id.up1);
        Button Up2 = findViewById(R.id.up2);
        EditText BMI = findViewById(R.id.bmi);
        EditText Goal = findViewById(R.id.goal);
        TextView Water = findViewById(R.id.water);
        Today1 =(TextView)findViewById(R.id.today1);
        Today2 = (TextView)findViewById(R.id.today2);
        Button upload = findViewById(R.id.Upload);
        TextView Dbtest = findViewById(R.id.dbtest);

        //오늘 날짜 가져오기
        String format_yyyyMMdd = "yyyy-MM-dd";
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new
                SimpleDateFormat(format_yyyyMMdd, Locale.getDefault());
        String current = format.format(currentTime);


        Today1.setText(current);
        Today2.setText(current);

        //적정 음수량 계산
        BmiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BMI.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "몸무게를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String weight = BMI.getText().toString();
                    int result = Integer.parseInt(weight) * 40;
                    Water.setText(result + "ml");
                }
            }
        });

        lineChart = (LineChart)findViewById(R.id.chart);

        List<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(1, 18));
        entries1.add(new Entry(2, 18));
        entries1.add(new Entry(3, 24));
        entries1.add(new Entry(4, 12));
        entries1.add(new Entry(5, 18));
        entries1.add(new Entry(6, 12));
        entries1.add(new Entry(7, 0));

        LineDataSet lineDataSet1 = new LineDataSet(entries1, "음수량");


        lineDataSet1.setLineWidth(2);
        lineDataSet1.setCircleRadius(6);
        lineDataSet1.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet1.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet1.setDrawCircleHole(true);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setDrawHorizontalHighlightIndicator(false);
        lineDataSet1.setDrawHighlightIndicators(false);
        lineDataSet1.setDrawValues(false);
        LineData lineData = new LineData(lineDataSet1);
        lineChart.setData(lineData);



        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.invalidate();



        //컵에 물 채우기
        Up1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ml = 0;

                if (count1 % 6 == 0) {
                    ml += 60 ;
                    Cup1.setImageResource(R.drawable.cup60);
                    db.drinkDao().insert(new Drink(Today1.getText().toString(), Integer.toString(ml)));
                    Dbtest.setText(db.drinkDao().getAll().toString());

                } else if (count1 % 6 == 1) {
                    ml += 120 ;
                    Cup1.setImageResource(R.drawable.cup120);
                    db.drinkDao().insert(new Drink(Today1.getText().toString(), Integer.toString(ml)));
                    Dbtest.setText(db.drinkDao().getAll().toString());
                } else if (count1 % 6 == 2) {
                    ml += 180 ;
                    Cup1.setImageResource(R.drawable.cup180);
                    db.drinkDao().insert(new Drink(Today1.getText().toString(), Integer.toString(ml)));
                    Dbtest.setText(db.drinkDao().getAll().toString());
                } else if (count1 % 6 == 3) {
                    ml += 240 ;
                    Cup2.setImageResource(R.drawable.cup60);
                    db.drinkDao().insert(new Drink(Today1.getText().toString(), Integer.toString(ml)));
                    Dbtest.setText(db.drinkDao().getAll().toString());
                } else if (count1 % 6 == 4) {
                    ml += 300 ;
                    Cup2.setImageResource(R.drawable.cup120);
                    db.drinkDao().insert(new Drink(Today1.getText().toString(), Integer.toString(ml)));
                    Dbtest.setText(db.drinkDao().getAll().toString());
                } else if (count1 % 6 == 5) {
                    ml += 360 ;
                    Cup2.setImageResource(R.drawable.cup180);
                    db.drinkDao().insert(new Drink(Today1.getText().toString(), Integer.toString(ml)));
                    Dbtest.setText(db.drinkDao().getAll().toString());

                    Up1.setEnabled(false);

                }
                    count1++;

            }
        });


        //병에 물 채우기

        Up2.setOnClickListener(new View.OnClickListener() {
            int man=0 ;
            @Override
            public void onClick(View v) {
                if (count2 % 12 == 0) {
                    man += 200;
                    Pet1.setImageResource(R.drawable.pet200);

                }else if (count2 % 12 == 1) {
                    man += 400;
                    Pet1.setImageResource(R.drawable.pet400);

                }else if (count2 % 12 == 2) {
                    man += 600;
                    Pet1.setImageResource(R.drawable.pet600);

                }else if (count2 % 12 == 3) {
                    man += 800;
                    Pet1.setImageResource(R.drawable.pet800);

                }else if (count2 % 12 == 4) {
                    man += 1000;
                    Pet1.setImageResource(R.drawable.pet1000);

                }else if (count2 % 12 == 5) {
                    man += 1200;
                    Pet1.setImageResource(R.drawable.pet1200);

                }else if (count2 % 12 == 6) {
                    man += 1400;
                    Pet2.setImageResource(R.drawable.pet200);

                }else if (count2 % 12 == 7) {
                    man += 1600;
                    Pet2.setImageResource(R.drawable.pet400);

                }else if (count2 % 12 == 8) {
                    man += 1800;
                    Pet2.setImageResource(R.drawable.pet600);

                }else if (count2 % 12 == 9) {
                    man += 2000;
                    Pet2.setImageResource(R.drawable.pet800);

                }else if (count2 % 12 == 10) {
                    man += 2200;
                    Pet2.setImageResource(R.drawable.pet1000);

                }else if (count2 % 12 == 11) {
                    man += 2400;
                    Pet2.setImageResource(R.drawable.pet1200);

                    Up2.setEnabled(false);
                }
                count2++;
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }





    //프로필 사진 등록
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //이미지 뷰를 클릭하면 시작되는 함수

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            Uri photoUri = data.getData();
            Bitmap bitmap = null;
            //bitmap 이용
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);

            } catch (IOException e) {
                e.printStackTrace();
            }

            //이미지뷰에 이미지 불러오기
            profile.setImageBitmap(bitmap);

            //아래 커서 이용해서 사진의 경로 불러오기
            Cursor cursor = getContentResolver().query(Uri.parse(selectedImage.toString()), null, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            String mediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            Log.d("경로 확인 >> ", "$selectedImg  /  $absolutePath");

        } else {
            Toast.makeText(this, "사진 업로드 실패", Toast.LENGTH_LONG).show();
        }




    }

}

