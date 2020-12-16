package com.example.carteiramensal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class VisualizarEventos extends AppCompatActivity {



    private TextView tituloTxt;
    private ListView ListaEventos;
    private TextView totalTxt;
    private Button novoButton;
    private Button cancelarButton;

    //operação = 0 > entrada || operação = 1 > saída
    private int operacao = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_eventos);

        tituloTxt = (TextView) findViewById(R.id.tituloTxt);
        ListaEventos = (ListView) findViewById(R.id.listaEventos);
        totalTxt = (TextView) findViewById(R.id.valorTotalTxt);
        novoButton = (Button) findViewById(R.id.novoButton);
        cancelarButton = (Button) findViewById(R.id.cancelarButton);

        Intent intencao = getIntent();
        operacao = intencao.getIntExtra("acao", -1);

        ajusteOperacao();
    }

    public void cadastrarEventos() {
        novoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operacao != -1) {
                    Intent trocaAct = new Intent(VisualizarEventos.this, CadastroEdicaoEvento.class);

                    if (operacao == 0){
                        trocaAct.putExtra("acao", 0);
                    }else{
                        trocaAct.putExtra("acao",1);
                    }

                    startActivity(trocaAct);
                }
            }
        });
    }

    private void ajusteOperacao(){

        //precisaremos realizar uma busca no banco a respeito dos eventos a serem apresentados na lista

        if(operacao == 0){
            tituloTxt.setText("Entradas");
        }else if(operacao == 1){
            tituloTxt.setText("Saídas");
        }else{
            //erro na configuração da intent
            Toast.makeText(VisualizarEventos.this, "erro no parâmetro ação", Toast.LENGTH_LONG).show();
        }


    }
}