package com.example.ambienteproyecto;

public class Producto {

    private String nombreProduct;
    private String tipo;
    private int idTarro;

    public Producto() {
    }

    public Producto(String nombreProduct, String tipo, int idTarro) {
        this.nombreProduct = nombreProduct;
        this.tipo = tipo;
        this.idTarro = idTarro;
    }

    public String getNombreProduct() {
        return nombreProduct;
    }

    public void setNombreProduct(String nombreProduct) {
        this.nombreProduct = nombreProduct;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdTarro() {
        return idTarro;
    }

    public void setIdTarro(int idTarro) {
        this.idTarro = idTarro;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombreProduct='" + nombreProduct + '\'' +
                ", tipo='" + tipo + '\'' +
                ", idTarro=" + idTarro +
                '}';
    }
}
