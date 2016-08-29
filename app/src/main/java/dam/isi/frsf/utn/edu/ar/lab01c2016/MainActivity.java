package dam.isi.frsf.utn.edu.ar.lab01c2016;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, Button.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar slider = (SeekBar) findViewById(R.id.input_slider);
        slider.setOnSeekBarChangeListener(this);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
        TextView valor = (TextView) findViewById(R.id.input_slider_text);
        valor.setText(String.valueOf(new Integer(progress)));
    }

    public void onStartTrackingTouch(SeekBar seekBar){};
    public void onStopTrackingTouch(SeekBar seekBar){};

    public void onClick(View v) {
        TextView resultado = (TextView) findViewById(R.id.input_success);
        TextView retorno = (TextView) findViewById(R.id.input_monto);
        try{
            String valor = calcularInteres().toString();
            retorno.setText(valor);
            resultado.setText(getString(R.string.label_success, valor));
            resultado.setTextColor(getResources().getColor(R.color.VERDE));
        } catch(Exception e){
            resultado.setText(R.string.label_error);
            resultado.setTextColor(getResources().getColor(R.color.ROJO));
        }
    }

    private Double calcularInteres() throws Exception{
        int importe = Integer.parseInt(((EditText) findViewById(R.id.input_importe)).getText().toString());
        int dias = ((SeekBar) findViewById(R.id.input_slider)).getProgress();
        int tasa = getTasa(importe, dias);

        return importe * (Math.pow((1 + tasa),(dias/360)) - 1);
    }

    private int getTasa(int importe, int dias){
        if (importe < 5000){
            return dias < 30 ? R.string.menor30_0_5000 : R.string.mayorigual30_0_5000;
        } else if (importe < 99999){
            return dias < 30 ? R.string.menor30_5000_99999 : R.string.mayorigual30_5000_99999;
        } else {
            return dias < 30 ? R.string.menor30_99999 : R.string.mayorigual30_99999;
        }
    }

}
