package com.example.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ejemplosqlite.bdd.BDHelper;

public class MainActivity extends AppCompatActivity {
    //variables globales
    double sueldoFijo = 0;
    double subsidio = 0;
    double Bonificacion = 0;
    double atrasoss = 0;
    double resultTotalSueldo = 0;

    //variables para la insercción
    EditText et_cedula ,et_funcionarios, et_cargo, et_area, et_nHijos, et_estadoC, et_atraso, et_horasExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UNIR ELEMENT0S DEL FRONT CON EL BACKEND
        et_cedula=findViewById(R.id.txtCedula);
        et_funcionarios=findViewById(R.id.txtFuncionario);
        et_cargo=findViewById(R.id.txtCargo);
        et_area=findViewById(R.id.txtArea);
        et_nHijos=findViewById(R.id.txtnHijos);
        et_estadoC=findViewById(R.id.txtEstadoCivil);
        et_atraso=findViewById(R.id.txtAtraso);
        et_horasExtras=findViewById(R.id.txtHorasExtras);

    }
    public void Limpiar(View view){
        et_cedula.setText("");
        et_funcionarios.setText("");
        et_cargo.setText("");
        et_area.setText("");
        et_nHijos.setText("");
        et_estadoC.setText("");
        et_atraso.setText("");
        et_horasExtras.setText("");
    }
    public void registrar(View view){
        BDHelper admin=new BDHelper(this,"registro.db",null,1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        //Conectar backend con SQLite
        String cedula = et_cedula.getText().toString();
        String funcionario=et_funcionarios.getText().toString();
        String cargo=et_cargo.getText().toString();
        String area=et_area.getText().toString();
        String nHijos=et_nHijos.getText().toString();
        String estadoCivil=et_estadoC.getText().toString();
        String atraso=et_atraso.getText().toString();
        String horasExtras=et_horasExtras.getText().toString();

        if(!cedula.isEmpty() && !funcionario.isEmpty() && !cargo.isEmpty() && !area.isEmpty() && !nHijos.isEmpty() && !estadoCivil.isEmpty() && !atraso.isEmpty() && !horasExtras.isEmpty()){
            ContentValues registro=new ContentValues();
            registro.put("usu_cedula",cedula);
            registro.put("usu_funcionario",funcionario);
            registro.put("usu_cargo",cargo);
            registro.put("usu_area",area);
            registro.put("usu_nHijos",nHijos);
            registro.put("usu_estadoCivil",estadoCivil);
            registro.put("usu_atraso",atraso);
            registro.put("usu_horasExtras",horasExtras);

            bd.insert("t_RolPagos",null,registro);
            Toast.makeText(this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();
            //mostrar los calculos en los campos posteriores al botón
            //this.Mostrar();
            //textView
            TextView setsubsidio = findViewById(R.id.set_txtSubsidio);
            TextView setDescuento = findViewById(R.id.set_txtDescuentoA);
            TextView setBonificacion = findViewById(R.id.set_txtBonificacion);
            TextView setHorExtra = findViewById(R.id.set_txtHorasE);
            TextView setSueldoTotal = findViewById(R.id.set_txtSueldoTotal);
            //Enviar datos en los textView de calculos
            sueldoFijo = this.Calculosubsidio(cargo);
            setsubsidio.setText(sueldoFijo+"");
            subsidio = this.DescuxAtraso(atraso);
            setDescuento.setText(subsidio+"");
            Bonificacion = this.Calculobonificacion(Integer.parseInt(nHijos));
            setBonificacion.setText(Bonificacion+"");
            atrasoss = this.CalculoHorasExtras(Double.parseDouble(horasExtras));
            setHorExtra.setText(atrasoss+"");
            resultTotalSueldo = this.TotaldeSueldo(sueldoFijo,subsidio,Bonificacion,atrasoss);
            setSueldoTotal.setText(resultTotalSueldo+"");
            //Vaciar los campos de ingreso
            et_cedula.setText("");
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
    public void MostrarDatos (View view) {
        BDHelper admin = new BDHelper(this, "registro.db", null, 2);
        SQLiteDatabase bd = admin.getReadableDatabase();
        String cedula = et_cedula.getText().toString();

        if (!cedula.isEmpty()) {

            // Define las columnas que deseas recuperar
            String[] columnas = {"usu_cedula", "usu_funcionario", "usu_cargo", "usu_area", "usu_nHijos", "usu_estadoCivil", "usu_atraso", "usu_horasExtras"};

            // Define la cláusula con el parametro cedula, WHERE para seleccionar el registro con el ID correspondiente
            String whereClause = "usu_cedula = ?";
            String[] whereArgs = new String[] {cedula};

            // Realiza la consulta
            Cursor cursor = bd.query("t_RolPagos", columnas, whereClause, whereArgs, null, null, null);

            if (cursor.moveToFirst()) {
                // Obtén los valores de las columnas del cursor
                @SuppressLint("Range") String funcionario = cursor.getString(cursor.getColumnIndex("usu_funcionario"));
                @SuppressLint("Range") String cargo = cursor.getString(cursor.getColumnIndex("usu_cargo"));
                @SuppressLint("Range") String area = cursor.getString(cursor.getColumnIndex("usu_area"));
                @SuppressLint("Range") String nHijos = cursor.getString(cursor.getColumnIndex("usu_nHijos"));
                @SuppressLint("Range") String estadoCivil = cursor.getString(cursor.getColumnIndex("usu_estadoCivil"));
                @SuppressLint("Range") String atraso = cursor.getString(cursor.getColumnIndex("usu_atraso"));
                @SuppressLint("Range") String horasExtras = cursor.getString(cursor.getColumnIndex("usu_horasExtras"));

                // Muestra los valores en los campos correspondientes
                et_funcionarios.setText(funcionario);
                et_cargo.setText(cargo);
                et_area.setText(area);
                et_nHijos.setText(nHijos);
                et_estadoC.setText(estadoCivil);
                et_atraso.setText(atraso);
                et_horasExtras.setText(horasExtras);
            }
            cursor.close();
            bd.close();

        }else{
            Toast.makeText(this,"INGRESE LA CEDULA DEL FUNCIONARIO",Toast.LENGTH_SHORT).show();
        }
    }
    

    public double Calculosubsidio (String cargo) {
        double sueldo = 0;
        if (cargo.equals("administrativo")==true){
            sueldo = 880;
        }else if (cargo.equals("docente")==true) {
            sueldo = 1000;
        }
        return sueldo;
    }
    public double Calculobonificacion (int nro_hijos) {
        double bonificacion=0;
        if (nro_hijos>0) {
            bonificacion = nro_hijos * 50;
        }else{
            bonificacion = 0;
        }
        return bonificacion;
    }
    public double DescuxAtraso (String Atraso) {
        double descuento = 0;
        if (Atraso.equals("Si")) {
            descuento = (sueldoFijo*8)/100;
        }else if (Atraso.equals("No")) {
            descuento = 0;
        }
        return descuento;
    }
    public double CalculoHorasExtras (double horas) {
        double hextra = 0;
        if (horas>0) {
            hextra = horas * 12;
        }else{
            return hextra = 0;
        }
        return hextra;
    }
    public double TotaldeSueldo (double suel_fijo, double sub_fidio, double boni_ficacion, double hor_extra) {
        double resultadoTotal = (suel_fijo - sub_fidio) + boni_ficacion + hor_extra;
        return resultadoTotal;
    }
    /*
    private void Mostrar() {
        EditText cargo = (EditText) findViewById(R.id.txtCargo);
        float sueldofijo = 0;
        EditText numeroHijos  = (EditText) findViewById(R.id.txtnHijos);
        EditText atrasos  = (EditText) findViewById(R.id.txtAtraso);
        float descuentosueldof = 0;
        EditText horasExtras = (EditText) findViewById(R.id.txtHorasExtras);

        //validación de cargo para determinar Subsidio
        TextView setsubsidio = findViewById(R.id.set_txtSubsidio);
        if(cargo.getText().toString().equals("administrativo")) {
            sueldofijo = 880;
            setsubsidio.setText("$"+sueldofijo);
        }else if(cargo.getText().toString().equals("docente")) {
            sueldofijo = 1000;
            setsubsidio.setText("$"+sueldofijo);
        } else{
            setsubsidio.setText("Error, no es docente ni administrativo");
        }

        //Verificar si es docente o administrativo para evitar los calculos erróneos.
        if (!cargo.getText().toString().equals("administrativo") || !cargo.getText().toString().equals("docente")){
            setsubsidio.setText("Funcionario no determinado correctamente");
        }else{

            //Valicación de atraso para descontar el valor del subsidio
            TextView setDescuento = findViewById(R.id.set_txtDescuentoA);
            if (atrasos.getText().toString().equals("Si")) {
                descuentosueldof = (sueldofijo * 8)/100;
                setDescuento.setText("-"+"$"+descuentosueldof);
            }else if (atrasos.getText().toString().equals("No")) {
                setDescuento.setText("No hay descuento");
            }else{
                setDescuento.setText("No ha determinado correctamente");
            }

            //Bonificacion x numero de hijos
            float bonificacion = 50 * Float.parseFloat(numeroHijos.getText().toString());
            TextView setbonificacion = findViewById(R.id.set_txtBonificacion);
            setbonificacion.setText("+"+"$"+bonificacion);

            //Horas extras
            float totalhoras = 12 * Float.parseFloat(horasExtras.getText().toString());
            TextView horasEx = findViewById(R.id.set_txtHorasE);
            horasEx.setText("+"+"$"+totalhoras);

            float total = (sueldofijo-descuentosueldof)+bonificacion+totalhoras;
            TextView mostrarfinal = findViewById(R.id.set_txtSueldoTotal);
            mostrarfinal.setText(total+" "+"USD");
        }
    }
     */
}