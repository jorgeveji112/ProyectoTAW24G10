package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.ClienteRepository;
import es.uma.proyectotaw.dto.ClienteDTO;
import es.uma.proyectotaw.entity.ClienteEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends DTOService<ClienteDTO, ClienteEntity> {

    @Autowired
    protected ClienteRepository clienteRepository;

    public ClienteDTO buscarCliente(int id){
        ClienteEntity usuario = clienteRepository.findById(id).get();
        return usuario.toDTO();
    }
}
