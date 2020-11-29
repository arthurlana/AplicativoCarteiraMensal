package ferramentas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class EventosDB extends SQLiteOpenHelper {

    private Context contexto;

    public EventosDB(Context cont){
        super(cont, "evento", null, 1);
        contexto = cont;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String criaTabela = "CREATE TABLE IF NOT EXISTS evento(id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, valor REAL, imagem TEXT, " +
                "dataocorreu DATE, datacadastro DATE, datavalida DATE)";

        db.execSQL(criaTabela);
    }

    public void insereEvento(){

        try(SQLiteDatabase db = this.getWritableDatabase()){
            //Maneira mais objetiva

            ContentValues valores = new ContentValues();

            valores.put("nome", "padaria");
            valores.put("valor", -70);

            db.insert("evento", null, valores);

            /*Maneira já aprendida
            String sql = "INSERT into evento(nome, valor) VALUES ('evento1', 89)";
            db.execSQL(sql);
            */

        }catch (SQLiteException ex){
            ex.printStackTrace();
        }

    }

    public void atualizaEvento(){

    }

    public void buscaEventos(){

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ficará parado até a atualização da activity de update (funcionalidade)
    }
}
