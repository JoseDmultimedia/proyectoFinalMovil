package com.example.ambienteproyecto;

public class Tarro {

    private int idTarro;
    private String tipoTarro;
    private String latitud;
    private String longitud;

    public Tarro() {
    }

    public Tarro(int idTarro, String tipoTarro, String latitud, String longitud) {
        this.idTarro = idTarro;
        this.tipoTarro = tipoTarro;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getIdTarro() {
        return idTarro;
    }

    public void setIdTarro(int idTarro) {
        this.idTarro = idTarro;
    }

    public String getTipoTarro() {
        return tipoTarro;
    }

    public void setTipoTarro(String tipoTarro) {
        this.tipoTarro = tipoTarro;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "\n" +
                "Id Tarro = " + idTarro + "\n" +
                "Tipo Tarro = '" + tipoTarro + '\'' + "\n" +
                "Latitud = '" + latitud + '\'' + "\n" +
                "Longitud = '" + longitud + '\'' + "\n"
                ;
    }
}
