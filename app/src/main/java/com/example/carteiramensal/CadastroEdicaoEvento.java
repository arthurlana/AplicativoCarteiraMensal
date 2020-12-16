package com.example.carteiramensal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ferramentas.EventosDB;
import modelo.Evento;

public class CadastroEdicaoEvento extends AppCompatActivity {

    private DatePickerDialog calendarioUsuario;

    private TextView tituloTxt;
    private EditText nomeTxt;
    private EditText valorTxt;
    private TextView dataTxt;
    private CheckBox repeteBtn;
    private ImageView foto;
    private Button fotoBtn;
    private Button salvarBtn;
    private Button cancelarBtn;

    private Calendar calendarioTemp;

    // 0 - cadastro de entrada, 1 - cadastro de saída, 2 - edição de entrada, 3 - edição de saída

    private int acao = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_edicao_evento);

        tituloTxt = (TextView) findViewById(R.id.tituloCadastroTxt);
        nomeTxt = (EditText) findViewById(R.id.nomeCadastroTxt);
        valorTxt = (EditText) findViewById(R.id.valorCadastroTxt);
        dataTxt = (TextView) findViewById(R.id.dataCadastroTxt);
        repeteBtn = (CheckBox) findViewById(R.id.repeteBtn);
        foto = (ImageView) findViewById(R.id.fotoCadastro);
        fotoBtn = (Button) findViewById(R.id.fotoBtn);
        salvarBtn = (Button) findViewById(R.id.salvarCadastroBtn);
        cancelarBtn = (Button) findViewById(R.id.cancelarCadastroBtn);

        Intent intencao = getIntent();
        acao = intencao.getIntExtra("acao", -1);

        ajustaPorAcao();
        cadastrarEventos();

    }

    private void cadastrarEventos(){
        //configurando o datepicker
        calendarioTemp = Calendar.getInstance();
        calendarioUsuario = new DatePickerDialog(CadastroEdicaoEvento.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int ano, int mes, int dia) {
                calendarioTemp.set(ano, mes, dia);
                dataTxt.setText(dia+"/"+(mes+1)+"/"+ano);
            }
        }, calendarioTemp.get(Calendar.YEAR), calendarioTemp.get(Calendar.MONTH), calendarioTemp.get(Calendar.DAY_OF_MONTH));

        dataTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarioUsuario.show();
            }
        });

        salvarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarNovoEvento();
            }
        });
    }

    private void ajustaPorAcao(){
        //recuperando data atual
        Calendar hoje = Calendar.getInstance();

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        dataTxt.setText(formatador.format(hoje.getTime()));

        switch (acao){
            case 0:
                tituloTxt.setText("Cadast. Entrada");
                break;
            case 1:
                tituloTxt.setText("Cadast. Saída");
                break;
            case 2:
                tituloTxt.setText("Edição de Entrada");
                break;
            case 3:
                tituloTxt.setText("Edição de Saída");
                break;
            default:
        }
    }

    private void cadastrarNovoEvento(){
        String nome = nomeTxt.getText().toString();
        double valor = Double.parseDouble(valorTxt.getText().toString());

        if(acao == 1 || acao == 3){
            valor *= -1;
        }
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        String dataStr = dataTxt.getText().toString();

        try{
            Date diaEvento = formatador.parse(dataStr);

            //um novo calendário para calcular a data limite
            Calendar dataLimite = Calendar.getInstance();
            dataLimite.setTime(calendarioTemp.getTime());
            //verificando se o evento repetirá
            if(repeteBtn.isChecked()){
                //por enquanto consideramos apenas 1 mês
            }

            //setando para o último dia do mês limite
            dataLimite.set(Calendar.DAY_OF_MONTH, dataLimite.getActualMaximum(Calendar.DAY_OF_MONTH));

            Evento novoEvento = new Evento(nome, valor, new Date(), null, diaEvento, null);

            //inserir esse evento no banco de dados
            EventosDB bd = new EventosDB(CadastroEdicaoEvento.this);
            bd.insereEvento(novoEvento);
            Toast.makeText(CadastroEdicaoEvento.this, "Cadastro feito com sucesso", Toast.LENGTH_LONG).show();
            finish();
        }catch (ParseException ex){
            System.out.println("erro no formato da data");
        }


    }
}