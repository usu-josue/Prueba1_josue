package com.example.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejemplosqlite.bdd.BDHelper;

public class MainActivity extends AppCompatActivity {
    EditText et_cedula, et_nombre, et_apellido, et_direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UNIR ELEMENT0S DE BACK CON FRONT
        et_cedula=findViewById(R.id.txtCedula);
        et_nombre=findViewById(R.id.txtNombre);
        et_apellido=findViewById(R.id.txtApellidos);
        et_direccion=findViewById(R.id.txtDireccion);
    }

    public void registrar(View view){
        BDHelper admin=new BDHelper(this,"registro.db",null,1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        String cedula=et_cedula.getText().toString();
        String nombre=et_nombre.getText().toString();
        String apellido=et_apellido.getText().toString();
        String direccion=et_direccion.getText().toString();

        if(!cedula.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !direccion.isEmpty()){
            ContentValues registro=new ContentValues();
            registro.put("usu_cedula",cedula);
            registro.put("usu_nombre",nombre);
            registro.put("usu_apellido",apellido);
            registro.put("usu_direccion",direccion);
            bd.insert("tblUsuarios",null,registro);
            Toast.makeText(this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();
            et_cedula.setText("");
            et_nombre.setText("");
            et_apellido.setText("");
            et_direccion.setText("");
            bd.close();
        }else{
            Toast.makeText(this,"FAVOR INGRESAR TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
        }

    }
}