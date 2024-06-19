package es.uma.proyectotaw.ui;

// Pablo Pardo Fernández 100%
public class Usuario {
    protected String user;
    protected String password;
    public Usuario () {}
    public Usuario (String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
