package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.ClienteEntity;
import es.uma.proyectotaw.entity.RolEnum;
import es.uma.proyectotaw.entity.TrolEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import es.uma.proyectotaw.repository.TrolRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import es.uma.proyectotaw.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;

@Controller
public class GymController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TrolRepository trolRepository;
    @GetMapping("/")
    public String redirectToInicio(Model model) {
        return "redirect:/inicio";
    }
    @GetMapping("/inicio")
    public String doInicio(Model model) {
        return "inicio";
    }
    @GetMapping("/trabaja")
    public String doTrabaja(Model model) {
        return "trabaja";
    }
    @GetMapping("/acceso")
    public String doAcceso(Model model) {
        return "acceso";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        Model model, HttpSession session) {
        UsuarioEntity usuario = usuarioRepository.findByNombreUsuarioAndContraseña(username, password);
        if (usuario != null) {
            RolEnum rol = usuario.getRol().getRol();
            session.setAttribute("usuario", usuario);
            if(rol == RolEnum.admin) {
                return "redirect:/adminMain";
            }else if(rol == RolEnum.entrenador) {
                return "redirect:/entrenadorMain";
            }else if(rol == RolEnum.cliente){
                    return "redirect:/clienteMain";
            }
        }
        model.addAttribute("error", "Nombre de usuario o contraseña incorrectos");
        return "acceso"; // Reenvía al mismo formulario de inicio de sesión
    }


    @GetMapping("/registro")
    public String doRegistro(@RequestParam("nombre") String nombre, @RequestParam("apellidos") String apellidos,
                             @RequestParam("Fecha_nacimiento") String FechaNac,@RequestParam("DNI") String DNI,
                             @RequestParam("eMail") String eMail,@RequestParam("telefono") String telefono,@RequestParam("altura") String altura,
                             @RequestParam("peso") String peso,@RequestParam("sexo") String sexo,
                             @RequestParam("tipoEntrenamiento") String tipoEntrenamiento,@RequestParam("objetivos") String objetivos,
                             @RequestParam("usuario") String nombre_usuario,@RequestParam("contraseña") String contraseña,Model model) throws ParseException {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = (Date) formato.parse(FechaNac);
        usuario.setFechaNacimiento(fecha);
        usuario.setDni(DNI);
        usuario.setCorreo(eMail);
        usuario.setTelefono(telefono);
        usuario.setNombreUsuario(nombre_usuario);
        usuario.setContraseña(contraseña);
        usuario.setGenero(sexo);
        usuario.setRol();



        ClienteEntity cliente = new ClienteEntity();


        return "registro";
    }
    @GetMapping("/adminMain")
    public String doAdminMain() {
        // Aquí puedes agregar lógica para obtener datos necesarios para la vista de administrador
        return "mainAdmin"; // Retorna el nombre de la vista de administrador
    }



    @GetMapping("/clienteMain")
    public String doClienteMain() {
        // Aquí puedes agregar lógica para obtener datos necesarios para la vista de cliente
        return "mainCliente"; // Retorna el nombre de la vista de cliente
    }


}
