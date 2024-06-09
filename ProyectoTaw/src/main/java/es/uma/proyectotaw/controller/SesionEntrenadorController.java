package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Pablo Pardo Fernández - 80% (Listar/ Crear/Borrar/Ver/Guardar Sesiones)
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

    @Autowired
    private ValoracionRepository valoracionRepository;

    @GetMapping("/entrenadorMain/sesiones")
    public String doSesiones(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<SesionentrenamientoEntity> sesiones = sesionentrenamientoRepository.findByUsuario(usuario);
        model.addAttribute("sesiones", sesiones);
        return "sesionesEntrenador";
    }

    @PostMapping("/entrenadorMain/sesiones/filtrar")
    public String doFiltrar(@RequestParam("filtro") String filtro, Model model, HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<SesionentrenamientoEntity> listaSesiones = sesionentrenamientoRepository.findByUsuario(usuario);
        List<SesionentrenamientoEntity> listaFiltrada = new ArrayList<>();

        for (SesionentrenamientoEntity sesion : listaSesiones) {
            if (sesion.getNombre() != null && sesion.getNombre().toLowerCase().contains(filtro.toLowerCase())){
                listaFiltrada.add(sesion);
            }
        }

        if(filtro.isEmpty()){
            listaFiltrada = listaSesiones;
        }

        model.addAttribute("filtro", filtro);
        model.addAttribute("sesiones", listaFiltrada);
        model.addAttribute("entrenador", usuario);
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
            SesionejercicioEntity sesionEjercicio = sesionHasSesion.getSesionejercicio();
            List<ValoracionEntity> valoraciones = valoracionRepository.findBySesionejercicio(sesionEjercicio);
            for (ValoracionEntity valoracion : valoraciones) {
                valoracionRepository.delete(valoracion);
            }
            // Eliminar la relación entre la sesión de entrenamiento y la sesión de ejercicio
            sesionentrenamientoHasSesionejercicioRepository.delete(sesionHasSesion);

            // Eliminar la sesión de ejercicio
            sesionejercicioRepository.delete(sesionEjercicio);
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



    @PostMapping("/entrenadorMain/sesiones/ver/ejercicio")
    public String doVerEjercicio(Model model, HttpSession session, @RequestParam("id") Integer id ){
        if(!estaAutenticado(session)) return "redirect:/acceso";
        EjercicioEntity ejercicio = ejercicioRepository.getReferenceById(id);
        model.addAttribute("ejercicio", ejercicio);

        return "verEjercicioEntrenador";
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
                                  HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";

        SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(id).get();
        sesion.setNombre(nombre);
        sesion.setDescripcion(descripcion);
        sesionentrenamientoRepository.save(sesion);

        List<SesionentrenamientoHasSesionejercicioEntity> sesionesHasSesiones = sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesion);

        // Crear una lista de ejercicios IDs existentes
        List<Integer> ejerciciosExistentes = new ArrayList<>();
        for (SesionentrenamientoHasSesionejercicioEntity sesionHasSesion : sesionesHasSesiones) {
            ejerciciosExistentes.add(sesionHasSesion.getSesionejercicio().getEjercicio().getId());
        }

        // Si no hay ejercicios nuevos, eliminar todos los ejercicios existentes
        if (ejercicios == null) {
            for (SesionentrenamientoHasSesionejercicioEntity sesionHasSesion : sesionesHasSesiones) {
                SesionejercicioEntity sesionEjercicio = sesionHasSesion.getSesionejercicio();
                sesionentrenamientoHasSesionejercicioRepository.delete(sesionHasSesion);
                sesionejercicioRepository.delete(sesionEjercicio);
            }
            return "redirect:/entrenadorMain/sesiones";
        }

        List<Integer> seriesInt = convertirAEnteros(series);
        List<Integer> repeticionesInt = convertirAEnteros(repeticiones);
        List<Integer> duracionInt = convertirAEnteros(duracion);

        // Mapear ejercicios existentes por ID para una fácil búsqueda
        Map<Integer, SesionentrenamientoHasSesionejercicioEntity> mapSesionesExistentes = sesionesHasSesiones.stream()
                .collect(Collectors.toMap(s -> s.getSesionejercicio().getEjercicio().getId(), s -> s));

        // Actualizar o eliminar ejercicios existentes
        for (SesionentrenamientoHasSesionejercicioEntity sesionHasSesion : sesionesHasSesiones) {
            Integer ejercicioId = sesionHasSesion.getSesionejercicio().getEjercicio().getId();
            if (!ejercicios.contains(ejercicioId)) {
                // Si el ejercicio no está en la nueva lista, eliminarlo
                SesionejercicioEntity sesionEjercicio = sesionHasSesion.getSesionejercicio();
                sesionentrenamientoHasSesionejercicioRepository.delete(sesionHasSesion);
                sesionejercicioRepository.delete(sesionEjercicio);

            }
        }

        // Agregar nuevos ejercicios o actualizar existentes
        for (int i = 0; i < ejercicios.size(); i++) {
            Integer ejercicioId = ejercicios.get(i);
            if (mapSesionesExistentes.containsKey(ejercicioId)) {
                // Si el ejercicio ya existe, actualizarlo
                SesionentrenamientoHasSesionejercicioEntity sesionHasSesion = mapSesionesExistentes.get(ejercicioId);
                sesionHasSesion.getSesionejercicio().setSeries(seriesInt.get(i));
                sesionHasSesion.getSesionejercicio().setRepeticiones(repeticionesInt.get(i));
                sesionHasSesion.getSesionejercicio().setDuracion(duracionInt.get(i));
                sesionHasSesion.setPosicion(i); // Actualizar la posición
                sesionentrenamientoHasSesionejercicioRepository.save(sesionHasSesion);
            } else {
                // Si el ejercicio es nuevo, agregarlo
                SesionentrenamientoHasSesionejercicioEntity sesionHasSesion = new SesionentrenamientoHasSesionejercicioEntity();
                sesionHasSesion.setSesionentrenamiento(sesion);
                SesionejercicioEntity sesionEjercicio = new SesionejercicioEntity();
                sesionEjercicio.setEjercicio(ejercicioRepository.findById(ejercicioId).get());
                sesionEjercicio.setSeries(seriesInt.get(i));
                sesionEjercicio.setRepeticiones(repeticionesInt.get(i));
                sesionEjercicio.setDuracion(duracionInt.get(i));
                sesionejercicioRepository.save(sesionEjercicio);
                sesionHasSesion.setSesionejercicio(sesionEjercicio);
                sesionHasSesion.setPosicion(i); // Establecer la posición
                sesionentrenamientoHasSesionejercicioRepository.save(sesionHasSesion);
            }
        }

        return "redirect:/entrenadorMain/sesiones";
    }
}
