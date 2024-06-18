package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.dao.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SesionEntrenadorController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;

    @Autowired
    private SesionentrenamientoHasSesionejercicioRepository sesionentrenamientoHasSesionejercicioRepository;

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Autowired
    private SesionejercicioRepository sesionejercicioRepository;

    @Autowired
    private RutinaSesionentrenamientoRepository rutinaSesionentrenamientoRepository;

    @GetMapping("/entrenadorMain/sesiones")
    public String doRutinas(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<SesionentrenamientoEntity> sesiones = sesionentrenamientoRepository.findByUsuario(usuario);
        model.addAttribute("sesiones", sesiones);
        return "sesionesEntrenador";
    }

    @GetMapping("/entrenadorMain/sesiones/crear")
    public String doCrearSesion(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        List<EjercicioEntity> listaEjercicios = ejercicioRepository.findAll();
        model.addAttribute("listaEjercicios", listaEjercicios);
        SesionentrenamientoEntity sesion = new SesionentrenamientoEntity();
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        sesion.setUsuario(usuario);
        sesionentrenamientoRepository.save(sesion);
        model.addAttribute("sesion", sesion);
        return "crearSesionEntrenador";
    }

    //Borrar Sesion
    @GetMapping("/entrenadorMain/sesiones/borrar")
    public String doBorrarSesion(Model model, HttpSession session, @RequestParam("id") Integer id) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(id).get();
        List<RutinaSesionentrenamientoEntity> rutinasSesiones = rutinaSesionentrenamientoRepository.findBySesionentrenamientoOrderByPosicion(sesion);
        for (RutinaSesionentrenamientoEntity rutinaSesion : rutinasSesiones) {
            rutinaSesionentrenamientoRepository.delete(rutinaSesion);
        }
        List<SesionentrenamientoHasSesionejercicioEntity> sesionesHasSesiones = sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesion);
        for (SesionentrenamientoHasSesionejercicioEntity sesionHasSesion : sesionesHasSesiones) {
            sesionejercicioRepository.delete(sesionHasSesion.getSesionejercicio());
            sesionentrenamientoHasSesionejercicioRepository.delete(sesionHasSesion);
        }
        sesionentrenamientoRepository.deleteById(id);
        return "redirect:/entrenadorMain/sesiones";
    }
    @GetMapping("/entrenadorMain/sesiones/ver")
    public String doVerSesion(Model model, HttpSession session, @RequestParam("id") Integer id) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(id).get();
        List<SesionentrenamientoHasSesionejercicioEntity> listaSesionHasSesion = sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesion);

        model.addAttribute("listaSesionHasSesion", listaSesionHasSesion);
        model.addAttribute("sesion", sesion);
        List<EjercicioEntity> listaEjercicios = ejercicioRepository.findByTipoEntrenamiento(sesion.getUsuario().getTipoEntrenamiento());
        model.addAttribute("listaEjercicios", listaEjercicios);

        return "verSesionEntrenador";
    }
    private List<Integer> convertirAEnteros(List<String> valores) {
        List<Integer> enteros = new ArrayList<>();
        for (String valor : valores) {
            try {
                enteros.add(Integer.parseInt(valor));
            } catch (NumberFormatException e) {
                enteros.add(null); // Si no es un entero, agregar null
            }
        }
        return enteros;
    }

    @PostMapping("/entrenadorMain/sesiones/guardar")
    public String doGuardarSesion(@RequestParam("id") Integer id,
                                  @RequestParam("nombre") String nombre,
                                  @RequestParam("descripcion") String descripcion,
                                  @RequestParam(value = "ejercicios", required = false) List<Integer> ejercicios,
                                  @RequestParam(value = "series", required = false) List<String> series,
                                  @RequestParam(value = "repeticiones", required = false) List<String> repeticiones,
                                  @RequestParam(value = "duracion", required = false) List<String> duracion,
                                  HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";
        SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(id).get();
        sesion.setNombre(nombre);
        sesion.setDescripcion(descripcion);
        sesionentrenamientoRepository.save(sesion);

        List<SesionentrenamientoHasSesionejercicioEntity> sesionesHasSesiones = sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesion);
        for (SesionentrenamientoHasSesionejercicioEntity sesionHasSesion : sesionesHasSesiones) {
            sesionejercicioRepository.delete(sesionHasSesion.getSesionejercicio());
            sesionentrenamientoHasSesionejercicioRepository.delete(sesionHasSesion);
        }
        if(ejercicios == null) return "redirect:/entrenadorMain/sesiones";
        List<Integer> seriesInt = convertirAEnteros(series);
        List<Integer> repeticionesInt = convertirAEnteros(repeticiones);
        List<Integer> duracionInt = convertirAEnteros(duracion);
        for(int i = 0; i < ejercicios.size(); i++) {
            SesionentrenamientoHasSesionejercicioEntity sesionHasSesion = new SesionentrenamientoHasSesionejercicioEntity();
            sesionHasSesion.setSesionentrenamiento(sesion);
            SesionejercicioEntity sesionEjercicio = new SesionejercicioEntity();
            sesionEjercicio.setEjercicio(ejercicioRepository.findById(ejercicios.get(i)).get());
            sesionEjercicio.setSeries(seriesInt.get(i));
            sesionEjercicio.setRepeticiones(repeticionesInt.get(i));
            sesionEjercicio.setDuracion(duracionInt.get(i));
            sesionejercicioRepository.save(sesionEjercicio);
            sesionHasSesion.setSesionejercicio(sesionEjercicio);
            sesionHasSesion.setPosicion(i);
            sesionentrenamientoHasSesionejercicioRepository.save(sesionHasSesion);
        }

        return "redirect:/entrenadorMain/sesiones";
    }
    @PostMapping("/entrenadorMain/sesiones/añadirEjercicio")
    public String añadirEjercicio(@RequestParam("sesionId") Integer sesionId, @RequestParam("ejercicioId") Integer ejercicioId) {
        // Obtener la sesión de entrenamiento
        SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(sesionId).get();


        // Obtener el ejercicio
        EjercicioEntity ejercicio = ejercicioRepository.findById(ejercicioId).get();

        // Crear un nuevo objeto SesionentrenamientoHasSesionejercicioEntity
        SesionentrenamientoHasSesionejercicioEntity nuevaRelacion = new SesionentrenamientoHasSesionejercicioEntity();
        nuevaRelacion.setSesionentrenamiento(sesion);
        SesionejercicioEntity nuevaSesionEjercicio = new SesionejercicioEntity();
        nuevaSesionEjercicio.setEjercicio(ejercicio);
        sesionejercicioRepository.save(nuevaSesionEjercicio);

        nuevaRelacion.setSesionejercicio(nuevaSesionEjercicio);
        // Calcular la posición de la nueva relación
        List<SesionentrenamientoHasSesionejercicioEntity> relacionesExistentes = sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesion);
        int nuevaPosicion = relacionesExistentes.size() + 1;
        nuevaRelacion.setPosicion(nuevaPosicion);

        // Guardar la nueva relación en la base de datos
        sesionentrenamientoHasSesionejercicioRepository.save(nuevaRelacion);

        // Redirigir a una página de éxito o a la misma página
        return "redirect:/entrenadorMain/sesiones/ver?id=" + sesionId;
    }
}
