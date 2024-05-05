package es.uma.proyectotaw.controller;


import es.uma.proyectotaw.entity.UsuarioEntity;

import es.uma.proyectotaw.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClienteEntrenadorController extends  BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/entrenadorMain/clientes")
    public String doClientes(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<UsuarioEntity> clientes = usuarioRepository.findClientesByEntrenadorId(usuario.getId());
        model.addAttribute("clientes", clientes);
        return "clientesEntrenador";
    }
}
