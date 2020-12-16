package com.example.carteiramensal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ferramentas.EventosDB;

public class MainActivity extends AppCompatActivity {

    private TextView titulo;
    private TextView entrada;
    private TextView saida;
    private TextView saldo;
    private ImageButton entradaBtn;
    private ImageButton saidaBtn;
    private Button anteriorBtn;
    private Button proximoBtn;
    private Button novoBtn;
    private Calendar hoje;
    private Calendar dataAPP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //criação da conexão entre os componentes java e xml
        titulo = (TextView) findViewById(R.id.tituloMain);
        entrada = (TextView) findViewById(R.id.entradaTxt);
        saida = (TextView) findViewById(R.id.saidaTxt);
        saldo = (TextView) findViewById(R.id.saldoTxt);

        entradaBtn = (ImageButton) findViewById(R.id.entradaBtn);
        saidaBtn = (ImageButton) findViewById(R.id.saidaBtn);

        anteriorBtn = (Button) findViewById(R.id.anteriorBtn);
        proximoBtn = (Button) findViewById(R.id.proximoBtn);
        novoBtn = (Button) findViewById(R.id.novoBtn);

        //cadastra eventos de botões
        cadastroEventos();

        //recuperação de data e hora atual;
        hoje = Calendar.getInstance();
        dataAPP = Calendar.getInstance();
        mostraDataApp();
    }

    private void mostraDataApp(){
        String nomeMes[] = new String[]{"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        int mes = dataAPP.get(Calendar.MONTH);
        int ano = dataAPP.get(Calendar.YEAR);

        titulo.setText(nomeMes[mes]+" / "+ano);
    }

    public void cadastroEventos(){
        anteriorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaMes(-1);
            }
        });

        proximoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaMes(1);
            }
        });

        novoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EventosDB db = new EventosDB(MainActivity.this);
                //db.insereEvento();

                //Toast.makeText(MainActivity.this, db.getDatabaseName(), Toast.LENGTH_LONG).show();
            }
        });

        entradaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trocaAct = new Intent(MainActivity.this, VisualizarEventos.class);
                trocaAct.putExtra("acao",0);
                startActivity(trocaAct);
            }
        });

        saidaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trocaAct = new Intent(MainActivity.this, VisualizarEventos.class);
                trocaAct.putExtra("acao",1);
                startActivity(trocaAct);
            }
        });
    }

    private void atualizaMes(int ajuste){
        dataAPP.add(Calendar.MONTH, ajuste);
        if(ajuste > 0){
            if(dataAPP.after(hoje)){
                dataAPP.add(Calendar.MONTH, -1);
            }
        }else{
            //realização de busca no banco para avaliar se existem meses anteriores cadastrados
        }

        mostraDataApp();

    }
}