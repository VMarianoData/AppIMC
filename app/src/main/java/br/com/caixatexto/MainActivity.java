package br.com.caixatexto;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView resultado1;
    private TextView resultado2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultado1 = findViewById(R.id.textResultado1);
        resultado2 = findViewById(R.id.textResultado2);
    }

    public void enviar(View view) {
        TextInputEditText campoNome = findViewById(R.id.textoNome);
        TextInputEditText campoPeso = findViewById(R.id.textoPeso);
        TextInputEditText campoAltura = findViewById(R.id.textoAltura);

        String nome = Objects.requireNonNull(campoNome.getText()).toString().trim();
        String pesoStr = Objects.requireNonNull(campoPeso.getText()).toString().trim();
        String alturaStr = Objects.requireNonNull(campoAltura.getText()).toString().trim();

        if (nome.isEmpty() || pesoStr.isEmpty() || alturaStr.isEmpty()) {
            mostrarErro();
            return;
        }

        try {
            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);

            if (peso <= 0 || altura <= 0) {
                mostrarErro();
                return;
            }

            double imc = calcularIMC(peso, altura);
            String imcFormatado = formatarIMC(imc);
            String classificacao = obterClassificacaoIMC(imc);

            resultado1.setText(nome);
            resultado2.setText(String.format("IMC: %s - %s", imcFormatado, classificacao));

        } catch (NumberFormatException e) {
            mostrarErro();
        }
    }

    private double calcularIMC(double peso, double altura) {
        return peso / (altura * altura);
    }

    private String formatarIMC(double imc) {
        return new DecimalFormat("#.##").format(imc);
    }

    private String obterClassificacaoIMC(double imc) {
        if (imc < 18.5) {
            return "Baixo peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else if (imc < 30) {
            return "Sobrepeso";
        } else if (imc < 35) {
            return "Obesidade grau 1";
        } else if (imc < 40) {
            return "Obesidade grau 2";
        } else {
            return "Obesidade extrema";
        }
    }

    private void mostrarErro() {
        resultado1.setText("ERRO");
        resultado2.setText("ENTRADA INVÁLIDA");
    }

    public void limpar(View view) {
        resultado1.setText("---");
        resultado2.setText("---");
    }
}
