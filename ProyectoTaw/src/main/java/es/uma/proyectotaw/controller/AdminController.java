package es.uma.proyectotaw.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController extends BaseController{

    @GetMapping("adminMain/entrenadores")
    public String doEntrenadores(HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "mainAdmin";
    }

    @GetMapping("adminMain/clientes")
    public String doClientes(HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "mainAdmin";
    }

    @GetMapping("adminMain/solicitudes")
    public String doSolicitudes(HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "mainAdmin";
    }

    @GetMapping("adminMain/ejercicios")
    public String doEjercicios(HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "mainAdmin";
    }
}
