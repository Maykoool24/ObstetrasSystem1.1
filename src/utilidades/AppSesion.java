package utilidades;

import modelo.Usuario;

public class AppSesion {

    private static AppSesion instance;
    private Usuario usuarioLogueado;

    private AppSesion() {
        this.usuarioLogueado = null;
    }

    public static AppSesion getInstance() {
        if (instance == null) {
            instance = new AppSesion();
        }
        return instance;
    }
    
    public Usuario getUsuarioLogueado(){
        return usuarioLogueado;
    }
    
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }
    
    public void cerrarSesion(){
        this.usuarioLogueado = null;
    }
}
