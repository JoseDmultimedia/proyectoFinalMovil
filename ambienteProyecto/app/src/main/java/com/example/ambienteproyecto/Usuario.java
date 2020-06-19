package com.example.ambienteproyecto;

public class Usuario {

    private String nombre;
    private String cargo;
    private String password;
    private String username;

    public Usuario() {
    }

    public Usuario(String nombre, String cargo, String password, String username) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.password = password;
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return
                "\n" +
                "Nombre = '" + nombre + '\''+ "\n" +
                "Cargo = '" + cargo + '\'' + "\n" +
                "Contraseña = '" + password + '\'' + "\n" +
                "Username = '" + username + '\'' + "\n"
                ;
    }
}
