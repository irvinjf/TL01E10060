package com.cdp.agenda.entidades;

import android.widget.ImageView;
import android.widget.Spinner;

public class Contactos {

    private int id;
    private String pais;
    private String nombre;
    private String telefono;
    private String nota;




    private ImageView imagen;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {return pais;}

    public void setPais(String pais) {this.pais = pais;}

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

}
