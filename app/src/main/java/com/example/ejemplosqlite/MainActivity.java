package com.example.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ejemplosqlite.bdd.BDHelper;

public class MainActivity extends AppCompatActivity {
    EditText et_funcionarios, et_cargo, et_area, et_nHijos, et_estadoC, et_atraso, et_horasExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UNIR ELEMENT0S DE BACK CON FRONT
        et_funcionarios=findViewById(R.id.txtFuncionario);
        et_cargo=findViewById(R.id.txtCargo);
        et_area=findViewById(R.id.txtArea);
        et_nHijos=findViewById(R.id.txtnHijos);
        et_estadoC=findViewById(R.id.txtEstadoCivil);
        et_atraso=findViewById(R.id.txtAtraso);
        et_horasExtras=findViewById(R.id.txtHorasExtras);
    }

    public void registrar(View view){
        BDHelper admin=new BDHelper(this,"registro.db",null,1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        String funcionario=et_funcionarios.getText().toString();
        String cargo=et_cargo.getText().toString();
        String area=et_area.getText().toString();
        String nHijos=et_nHijos.getText().toString();
        String estadoCivil=et_estadoC.getText().toString();
        String atraso=et_atraso.getText().toString();
        String horasExtras=et_horasExtras.getText().toString();

        if(!funcionario.isEmpty() && !cargo.isEmpty() && !area.isEmpty() && !nHijos.isEmpty() && !estadoCivil.isEmpty() && !atraso.isEmpty() && !horasExtras.isEmpty()){
            ContentValues registro=new ContentValues();
            registro.put("usu_funcionario",funcionario);
            registro.put("usu_cargo",cargo);
            registro.put("usu_area",area);
            registro.put("usu_nHijos",nHijos);
            registro.put("usu_estadoCivil",estadoCivil);
            registro.put("usu_atraso",atraso);
            registro.put("usu_horasExtras",horasExtras);

            bd.insert("t_RolPagos",null,registro);
            Toast.makeText(this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();

            //mostrar en los campos posteriores al botón
            this.Mostrar();

            //Vaciar los campos de ingreso
            et_funcionarios.setText("");
            et_cargo.setText("");
            et_area.setText("");
            et_nHijos.setText("");
            et_estadoC.setText("");
            et_atraso.setText("");
            et_horasExtras.setText("");
            //cerrar conexión
            bd.close();
        }else{
            Toast.makeText(this,"FAVOR INGRESAR TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
        }

    }

    private void Mostrar() {
        EditText cargo = (EditText) findViewById(R.id.txtCargo);
        float sueldofijo = 0;
        EditText numeroHijos  = (EditText) findViewById(R.id.txtnHijos);
        EditText atrasos  = (EditText) findViewById(R.id.txtAtraso);
        float descuentosueldof = 0;

        EditText horasExtras = (EditText) findViewById(R.id.txtHorasExtras);

        //condición de cargo
        TextView setcargo = findViewById(R.id.set_txtSubsidio);
        if(cargo.getText().toString().equals("administrativo")) {
            sueldofijo = 880;
            setcargo.setText(""+sueldofijo);
        }else if(cargo.getText().toString().equals("docente")) {
            sueldofijo = 1000;
            setcargo.setText(""+sueldofijo);
        } else{
            setcargo.setText("Error, no es docente ni administrativo");
        }


        //Valicación de atraso
        TextView setatraso = findViewById(R.id.set_txtDescuentoA);
        if (atrasos.getText().toString().equals("Si")) {
            descuentosueldof = (sueldofijo * 8)/100;
            setatraso.setText(""+descuentosueldof);
        }else if (atrasos.getText().toString().equals("No")) {
            setatraso.setText("No hay descuento");
        }else{
            setatraso.setText("No ha determinado correctamente");
        }

        //Bonificacion x numero de hijos
        float bonificacion = 50 * Float.parseFloat(numeroHijos.getText().toString());
        TextView setbonificacion = findViewById(R.id.set_txtBonificacion);
        setbonificacion.setText(""+bonificacion);

        //Horas extras
        float totalhoras = 12 * Float.parseFloat(horasExtras.getText().toString());
        TextView horasEx = findViewById(R.id.set_txtHorasE);
        horasEx.setText(""+totalhoras);


        
    }
}