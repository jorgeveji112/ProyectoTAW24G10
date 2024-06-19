/*
        Creador: Jorge Velázquez Jiménez
*/
package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.dao.ClienteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import es.uma.proyectotaw.dao.UsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/clienteMain/perfil")
    public String cambiosPerfil(@RequestParam("correo") String email, @RequestParam("telefono") String telefono,
                                @RequestParam("altura") Float altura, @RequestParam("peso") Float peso,
                                @RequestParam("objetivos") String objetivos, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        usuario.setCorreo(email);
        usuario.setTelefono(telefono);

        cliente.setAltura(altura);
        cliente.setPeso(peso);
        cliente.setObjetivos(objetivos);

        this.usuarioRepository.save(usuario);
        this.clienteRepository.save(cliente);

        model.addAttribute("cliente", cliente);
        return "mainCliente";
    }

}
