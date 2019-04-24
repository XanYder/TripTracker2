package com.example.triptracker;

import android.support.v7.app.AppCompatActivity;


public class Main2Activity extends AppCompatActivity {
    /*
    int checked = 0;
    int memories = 0;



    public String getTextFromFile(){
        String text = "";
        memories = 0;
        try {
            FileInputStream fis = openFileInput("test.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);


            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while ((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines+"\n");
                memories += 1;
            }

            return String.valueOf(stringBuffer);

        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public void setTextinFile(String text){
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput("test.txt", Context.MODE_APPEND);
            outputStream.write(text.getBytes());
            outputStream.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Switch simpleSwitch = (Switch) findViewById(R.id.switch1);
        checked = getIntent().getIntExtra("checked", 0);
        memories = getIntent().getIntExtra("memoriesAmount", 0);

        final TextView textfromFile = (TextView) findViewById(R.id.textFile);


        textfromFile.setText(getTextFromFile());

        if (checked == 1){
            simpleSwitch.setChecked(true);
        }
        else{
            simpleSwitch.setChecked(false);
        }

        Button backButton = (Button) findViewById(R.id.button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class   );
                intent.putExtra("checked", checked );
                intent.putExtra("memoriesAmount", memories );
                startActivity(intent);
            }
        });


        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch simpleSwitch = (Switch) findViewById(R.id.switch1);
                if (simpleSwitch.isChecked() == Boolean.TRUE) {
                    simpleSwitch.setText("Hi");
                    checked = 1;
                }
                else {
                    simpleSwitch.setText("Not Checked");
                    checked = 0;
                }
            }





        });




        final Button memoryButton = (Button)findViewById(R.id.create);
        final EditText memoryInput = (EditText)findViewById(R.id.editMemory);
        memoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String text = memoryInput.getText().toString();
               setTextinFile(text+"\n");
               memories += 1;
               textfromFile.setText(getTextFromFile());
            }
        });

        Button resetButton = (Button)findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile("test.txt");
                textfromFile.setText(getTextFromFile());

            }
        });




    }
    */
}
