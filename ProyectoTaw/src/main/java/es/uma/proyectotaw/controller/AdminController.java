package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.ClienteEntity;
import es.uma.proyectotaw.entity.RolEnum;
import es.uma.proyectotaw.entity.UsuarioEntity;
import es.uma.proyectotaw.repository.ClienteRepository;
import es.uma.proyectotaw.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

// Carlos Gálvez Bravo 100%
@Controller
public class AdminController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/adminMain/inicio")
    public String doAdminMain() {
        return "mainAdmin";
    }

    // RAMA DE LISTA DE ENTRENADORES /////////////////////////
    @GetMapping("/adminMain/entrenadores")
    public String doEntrenadores(HttpSession session, Model model) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        List<UsuarioEntity> listaUsuarios = usuarioRepository.findAll();
        List<UsuarioEntity> entrenadoresBodyBuilding = new ArrayList<>();
        List<UsuarioEntity> entrenadoresCrossTraining = new ArrayList<>();
        for (UsuarioEntity usuario : listaUsuarios) {
            if(usuario.getRol().getRol() == RolEnum.entrenador){
                if(usuario.getTipoEntrenamiento().getId() == 1) {
                    entrenadoresBodyBuilding.add(usuario);
                } else {
                    entrenadoresCrossTraining.add(usuario);
                }
            }
        }
        model.addAttribute("entrenadoresBodyBuilding", entrenadoresBodyBuilding);
        model.addAttribute("entrenadoresCrossTraining", entrenadoresCrossTraining);
        return "listaEntrenadores";
    }

    @GetMapping("/adminMain/clientesEntrenador/{id}")
    public String doClientesEntrenador(@PathVariable("id") int id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        List<UsuarioEntity> listaClientes = usuarioRepository.findClientesByEntrenadorId(id);
        UsuarioEntity entrenador = usuarioRepository.findById(id).get();
        model.addAttribute("entrenador", entrenador);
        model.addAttribute("listaClientes", listaClientes);
        return "clientesAsignadosEntrenador";
    }

    @GetMapping("/adminMain/entrenador/desasignarCliente/{id}")
    public String desasignarCliente(@PathVariable("id") int id, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity cliente = usuarioRepository.findById(id).get();
        int idEntrenador = cliente.getEntrenador().getId();
        cliente.setEntrenador(null);
        usuarioRepository.save(cliente);
        return "redirect:/adminMain/clientesEntrenador/"+idEntrenador;
    }

    @GetMapping("/adminMain/nuevosClientesEntrenador/{id}")
    public String listaClientesSinAsignar(@PathVariable("id") int id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity entrenador = usuarioRepository.findById(id).get();
        List<UsuarioEntity> listaClientes = usuarioRepository.findUsuariosWithoutCoachByTipoEntrenamiento(entrenador.getTipoEntrenamiento().getId());
        model.addAttribute("entrenador", entrenador);
        model.addAttribute("listaClientes", listaClientes);
        return "clientesSinEntrenador";
    }

    @GetMapping("/adminMain/asignarClienteEntrenador")
    public String asignarClienteEntrenador(@RequestParam("idEntrenador") int idEntrenador, @RequestParam("idCliente") int idCliente, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity entrenador = usuarioRepository.findById(idEntrenador).get();
        UsuarioEntity cliente = usuarioRepository.findById(idCliente).get();
        cliente.setEntrenador(entrenador);
        usuarioRepository.save(cliente);
        return "redirect:/adminMain/clientesEntrenador/"+entrenador.getId();
    }

    // RAMA DE LISTA DE CLIENTES /////////////////////////
    @GetMapping("/adminMain/clientes")
    public String doClientes(HttpSession session, Model model) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        List<UsuarioEntity> listaClientes = usuarioRepository.findUsuariosByRol(2);
        model.addAttribute("listaClientes", listaClientes);
        return "listaClientes";
    }

    @GetMapping("/adminMain/cliente/borrar/{id}")
    public String borrarCliente(@PathVariable("id") int id){

        clienteRepository.deleteById(id);
        usuarioRepository.deleteById(id);

        return "redirect:/adminMain/clientes";
    }

    @GetMapping("/adminMain/solicitudes")
    public String doSolicitudes(HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "mainAdmin";
    }

    @GetMapping("/adminMain/ejercicios")
    public String doEjercicios(HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "mainAdmin";
    }
}
