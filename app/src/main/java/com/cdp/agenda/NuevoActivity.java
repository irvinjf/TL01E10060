package com.cdp.agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cdp.agenda.db.DbContactos;

import java.io.File;
import java.io.IOException;

public class NuevoActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtNota;
    String rutaImagen;
    ImageView imgView2;
    Spinner SprPais;
    Button btnGuarda, btnTomarFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);


        SprPais = findViewById(R.id.SprPais);
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtNota = findViewById(R.id.txtNota);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);

        btnGuarda = findViewById(R.id.btnGuarda);

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SprPais.getSelectedItem().toString().equals("Seleccione un Pais")){
                    Toast.makeText(NuevoActivity.this, "DEBE SELECIONAR UN PAIS", Toast.LENGTH_SHORT).show();

                }else if(txtNombre.getText().toString().equals("")) {

                    Toast.makeText(NuevoActivity.this, "DEBE ESCRIBIR UN NOMBRE", Toast.LENGTH_LONG).show();

                } else if (txtTelefono.getText().toString().equals("")) {
                    Toast.makeText(NuevoActivity.this, "DEBE PROPORCIONAR UN NUMERO DE TELEFONO", Toast.LENGTH_LONG).show();

                } else if (txtNota.getText().toString().equals("")) {
                    Toast.makeText(NuevoActivity.this, "DEBE AGREGAR UNA NOTA AL CONTACTO", Toast.LENGTH_LONG).show();

                } else {
                    DbContactos dbContactos = new DbContactos(NuevoActivity.this);
                    long id = dbContactos.insertarContacto(SprPais.getSelectedItem().toString(), txtNombre.getText().toString(), txtTelefono.getText().toString(), txtNota.getText().toString());

                    if (id > 0) {
                        Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirCamara();
            }
        });

    }

    private void AbrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagenArchivo = null;

        try{
            imagenArchivo = crearImagen();

        }catch (IOException ex){
            Log.e("Error", ex.toString());
        }


        if(imagenArchivo != null)
        {
            Uri fotoUri = FileProvider.getUriForFile(this, "com.example.myapplication.fileprovider", imagenArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
        }

        startActivityForResult(intent, 1);
    }

    private File crearImagen() throws IOException {

        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);

        rutaImagen = imagen.getAbsolutePath();
        return imagen;

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView2.setImageBitmap(imgBitmap);
        }

    }

    private void limpiar() {
        SprPais.setSelection(0);
        txtNombre.setText("");
        txtTelefono.setText("");
        txtNota.setText("");
    }
}