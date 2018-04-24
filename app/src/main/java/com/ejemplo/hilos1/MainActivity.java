package com.ejemplo.hilos1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText text;
    Button button4;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button4 = (Button) findViewById(R.id.button4);

        textView = (TextView) findViewById(R.id.textView);
        text = (EditText) findViewById(R.id.editText);

        button4.setOnClickListener(this);
    }

    private void UnSegundo() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int i;
        switch (v.getId()) {
            case R.id.button4:
                AsyncTarea asyncTarea = new AsyncTarea();
                asyncTarea.execute();
                break;
            default:
                break;
        }

    }

    public void hilos() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    UnSegundo();
                }
               Toast.makeText(getBaseContext(), "Segundos:5", Toast.LENGTH_LONG).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Segundos:5", Toast.LENGTH_LONG).show();
                    }

                });
            }
        }).start();
    }

    private class  AsyncTarea extends AsyncTask<Void, String,Boolean>{
        String cadena;
        int li=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cadena="";
            li= Integer.parseInt (text.getText().toString());
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            for (int i=0; i<li; i++){
                cadena+=""+ fibonacci(i)+" \n";
                System.out.println(""+cadena);
                UnSegundo();
                if (isCancelled()){
                    break;
                }
                this.publishProgress(cadena);
            }
            return true;
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textView.setText(""+values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            if (aVoid){
                Toast.makeText(getApplicationContext(),"Tarea finaliza AsyncTask",Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(getApplicationContext(),"Tarea NO finaliza AsyncTask",Toast.LENGTH_SHORT).show();

        }
        int fibonacci(int n)
        {
            if (n>1){
                return fibonacci(n-1) + fibonacci(n-2);
            }
            else if (n==1) {
                return 1;
            }
            else if (n==0){
                return 0;
            }
            else{
                System.out.println("Debes ingresar un tamaño mayor o igual a 1");
                return -1;
            }
        }
    }
}
