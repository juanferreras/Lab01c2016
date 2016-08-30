package dam.isi.frsf.utn.edu.ar.lab01c2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, Button.OnClickListener {

    private SeekBar sliderSeekBar;
    private Button submitButton;
    private TextView valorTextView;
    private TextView resultadoTextView;
    private TextView retornoTextView;
    private EditText importeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderSeekBar = (SeekBar) findViewById(R.id.input_slider);
        importeEditText = (EditText) findViewById(R.id.input_importe);
        submitButton = (Button) findViewById(R.id.button);
        valorTextView = (TextView) findViewById(R.id.input_slider_text);
        resultadoTextView = (TextView) findViewById(R.id.input_success);
        retornoTextView = (TextView) findViewById(R.id.input_monto);

        sliderSeekBar.setOnSeekBarChangeListener(this);
        submitButton.setOnClickListener(this);
    }

    public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
        valorTextView.setText(String.valueOf(Integer.valueOf(progress)));
    }

    public void onStartTrackingTouch(SeekBar seekBar){}
    public void onStopTrackingTouch(SeekBar seekBar){}

    public void onClick(View v) {
        try{
            String valor = calcularInteres().toString();
            retornoTextView.setText(valor);
            resultadoTextView.setText(getString(R.string.label_success, valor));
            resultadoTextView.setTextColor(getResources().getColor(R.color.VERDE));
        } catch(Exception e){
            resultadoTextView.setText(R.string.label_error);
            resultadoTextView.setTextColor(getResources().getColor(R.color.ROJO));
        }
    }

    private Double calcularInteres() throws Exception{
        int importe = Integer.parseInt(importeEditText.getText().toString());
        int dias = sliderSeekBar.getProgress();
        Double tasa = Double.parseDouble(getTasa(importe, dias));


        return importe * (Math.pow((1 + tasa),(double) dias/360 ) - 1);
    }

    private String getTasa(int importe, int dias){
        if (importe < 5000){
            return dias < 30 ?  getString(R.string.menor30_0_5000) : getString(R.string.mayorigual30_0_5000);
        } else if (importe < 99999){
            return dias < 30 ? getString(R.string.menor30_5000_99999) : getString(R.string.mayorigual30_5000_99999);
        } else {
            return dias < 30 ? getString(R.string.menor30_99999) : getString(R.string.mayorigual30_99999);
        }
    }

}
