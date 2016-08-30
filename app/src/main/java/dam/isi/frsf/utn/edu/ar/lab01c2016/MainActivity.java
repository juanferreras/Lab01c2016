package dam.isi.frsf.utn.edu.ar.lab01c2016;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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
            String valorPlazoFijo = calcularPlazoFijo();
            retornoTextView.setText(valorPlazoFijo);
            resultadoTextView.setText(getString(R.string.label_success, valorPlazoFijo));
            resultadoTextView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.VERDE));
        } catch(AndroidAppException e){
            resultadoTextView.setText(e.getMessage());
            resultadoTextView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.ROJO));
        } catch(Exception e){
            resultadoTextView.setText(R.string.label_error);
            resultadoTextView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.ROJO));
        }
    }

    /**
     * Realiza las operaciones necesarias para obtener el monto final del plazo fijo.
     *
     * @return Retorna el resultado de la aplicación del interes al importe ingresado.
     * @throws Exception
     */
    private String calcularPlazoFijo() throws Exception{

        validate();

        int importe = Integer.parseInt(importeEditText.getText().toString());
        int dias = sliderSeekBar.getProgress();

        Double interes = calcularInteres(importe,dias);

        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(interes);
    }

    /**
     * Calcula el monto de interes sobre el capital ingresado.
     *
     * @param importe
     *      Importe ingresado.
     * @param dias
     *      Dias ingresados
     * @return Interes sobre el importe ingresado
     * @throws Exception
     */
    private Double calcularInteres(int importe, int dias) throws Exception{

        // Obtengo la tasa a aplicar.
        Double tasa = Double.parseDouble(getTasa(importe, dias));

        //Calculo el interesa sobre el importe
        Double interes = importe * (Math.pow((1 + tasa),(double) dias/360 ) - 1);

        return interes;
    }

    /**
     * Obtiene la tasa a aplicar segun el importe y los días ingresados.
     *
     * @param importe
     *      Importe ingresado.
     * @param dias
     *      Días ingresados
     * @return Tasa
     */
    private String getTasa(int importe, int dias){
        if (importe < 5000){
            return dias < 30 ?  getString(R.string.menor30_0_5000) : getString(R.string.mayorigual30_0_5000);
        } else if (importe < 99999){
            return dias < 30 ? getString(R.string.menor30_5000_99999) : getString(R.string.mayorigual30_5000_99999);
        } else {
            return dias < 30 ? getString(R.string.menor30_99999) : getString(R.string.mayorigual30_99999);
        }
    }

    /**
     * Realiza las validaciones sobre el formulario.
     *
     * @throws Exception
     */
    private void validate() throws Exception {
        if(importeEditText.getText().toString().isEmpty()){
            throw new AndroidAppException(getString(R.string.label_required_importe));
        }

        if(sliderSeekBar.getProgress() == 0){
            throw new AndroidAppException(getString(R.string.label_required_dias));
        }
    }
}
