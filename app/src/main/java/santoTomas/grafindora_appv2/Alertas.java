package santoTomas.grafindora_appv2;

public class Alertas {

    public String hora;
    public String nombre;
    public String descripcion;
    public int id;

    public Alertas( ) {

    }

    public Alertas(String nombre, String descripcion, String hora,int id) {
        this.hora = hora;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
    }

    public String getHora(){return hora;};

    public void setHora(String hora) {this.hora = hora;}

    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) { this.nombre = nombre;}

    public int getId() {return id;}
}
