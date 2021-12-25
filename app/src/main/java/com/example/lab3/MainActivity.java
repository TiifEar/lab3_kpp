package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void inputsClear(View v){
        EditText inputC = (EditText) findViewById(R.id.inputC);
        EditText inputT = (EditText) findViewById(R.id.inputT);
        EditText inputX1 = (EditText) findViewById(R.id.inputX1);
        EditText inputX2 = (EditText) findViewById(R.id.inputX2);
        EditText inputStep = (EditText) findViewById(R.id.inputStep);
        TextView showResult = (TextView) findViewById(R.id.showResult);
        inputC.setText("");
        inputT.setText("");
        inputX1.setText("");
        inputX2.setText("");
        inputStep.setText("");
        showResult.setText("");
        String mess="Поля очищено";
        showResultMessage(mess);
    }

    public void onCalculate(View v){
        EditText inputC = (EditText) findViewById(R.id.inputC);
        EditText inputT = (EditText) findViewById(R.id.inputT);
        EditText inputX1 = (EditText) findViewById(R.id.inputX1);
        EditText inputX2 = (EditText) findViewById(R.id.inputX2);
        EditText inputStep = (EditText) findViewById(R.id.inputStep);
        TextView showResult1 = (TextView) findViewById(R.id.showResult);
        String mess="";
        mess+=emptyTest();
        if (mess.isEmpty())
            try {
                double t=Double.parseDouble(inputT.getText().toString());
                double c=Double.parseDouble(inputC.getText().toString());
                int x1=Integer.parseInt(inputX1.getText().toString());
                int x2=Integer.parseInt(inputX2.getText().toString());
                double step=Double.parseDouble(inputStep.getText().toString());

                if((c+t)<0)
                    mess="Вираз під коренем не може бути менше нуля";
                else if((c+t)==0)
                    mess="На нуль ділити не можна";
                else if(c==0)
                    mess="ctg від 0 не існує";
                else if(x1>x2)
                    mess="Поміняйте місцями х1 та х2";
                else if(step<=0)
                    mess="крок не коректний";
                else
                {


                    String saveResults="";
                    for(double i=x1;i<=x2;i+=step)
                    {
                        double result=Math.pow((1/Math.tan(c)),2)+((2*Math.pow(i,2)+5)/(Math.sqrt(c+t)));
                        String formattedDouble = new DecimalFormat("#0.00").format(result);
                        String formattedDouble1 = new DecimalFormat("#0.00").format(i);
                        String line=formattedDouble1+" "+formattedDouble;
                        if(i<x2)
                        saveResults+=line+"\n";
                        else saveResults+=line;
                    }
                    mess="Операція успішна";
                    showResult1.setText(saveResults.toString());
                    try {
                        FileOutputStream fileoutput = openFileOutput("save.txt", MODE_PRIVATE);
                        fileoutput.write(saveResults.getBytes());
                        fileoutput.close();
                    }
                    catch (FileNotFoundException e){
                        showResultMessage("файл для запису не знайдено");
                    }
                    catch (IOException e){
                        showResultMessage("Не вдалось записати");
                    }


                }
            }
            catch (Exception e){
                mess = "Не вдалося конвертувати данні";

            }
        showResultMessage(mess);
    }

    public void readTxt(View v){
        TextView showResult1 = (TextView) findViewById(R.id.showResult);
        try  {
            FileInputStream fileInput = openFileInput("save.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader buffer = new BufferedReader(reader);
            StringBuffer strBuffer = new StringBuffer();
            String lines;
            while ((lines=buffer.readLine()) != null)
            {
                strBuffer.append(lines+"\n");
            }
            showResult1.setText(strBuffer.toString());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String emptyTest(){
        EditText inputC = (EditText) findViewById(R.id.inputC);
        EditText inputT = (EditText) findViewById(R.id.inputT);
        EditText inputX1 = (EditText) findViewById(R.id.inputX1);
        EditText inputX2 = (EditText) findViewById(R.id.inputX2);
        EditText inputStep = (EditText) findViewById(R.id.inputStep);
        String mess="";
        if(inputT.getText().toString().isEmpty())
            mess="Т не вказано";
        if(inputC.getText().toString().isEmpty())
            if (mess.isEmpty())
                mess=mess+"C не вказано";
            else
                mess=mess+"\n C не вказано ";
        if(inputX1.getText().toString().isEmpty())
            if (mess.isEmpty())
                mess=mess+"X1 не вказано";
            else
                mess=mess+"\n X1 не вказано ";
        if(inputX2.getText().toString().isEmpty())
            if (mess.isEmpty())
                mess=mess+"X2 не вказано";
            else
                mess=mess+"\n X2 не вказано ";
        if(inputStep.getText().toString().isEmpty())
            if (mess.isEmpty())
                mess=mess+"Крок не вказано";
            else
                mess=mess+"\n Крок не вказано ";
        return mess;
    }

    private void showResultMessage(String mess){
        LayoutInflater inflater=getLayoutInflater();
        View layout =inflater.inflate(R.layout.messege_toast, (ViewGroup) findViewById(R.id.showMessage));

        TextView message = layout.findViewById(R.id.message);
        message.setText(mess.toString());

        Toast toast = new Toast(getApplicationContext());

        toast.setGravity(Gravity.CENTER, 0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void autorInfoToast(View v){
        LayoutInflater inflater=getLayoutInflater();
        View layout =inflater.inflate(R.layout.autor_info_toast, (ViewGroup) findViewById(R.id.authorInfoToast));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}