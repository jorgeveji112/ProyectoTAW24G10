/*
        Creador: Jorge Velázquez Jiménez
*/
package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import es.uma.proyectotaw.repository.UsuarioRepository;

@Controller
public class clientePerfilController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    @GetMapping("/clienteMain")
    public String doClienteMain(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";

        return "redirect:/clienteMain/inicio";
    }

    @GetMapping("/clienteMain/inicio")
    public String doMainInicio(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        model.addAttribute("cliente", cliente);
        return "mainCliente";
    }

    @GetMapping("/clienteMain/perfil")
    public String doPerfil(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        model.addAttribute("cliente", cliente);
        return "perfilCliente";
    }

}
