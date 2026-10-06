package edu.itm.SerenitySpa.services;
import edu.itm.SerenitySpa.identities.Empleado;
import edu.itm.SerenitySpa.repositories.IEmpleadoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpleadoService implements IEmpleadoService {
    private final IEmpleadoRepository empleadoRepository;
    public EmpleadoService(IEmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }
    @Override
    public Empleado buscar(int id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }
    @Override
    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }
    @Override
    public Empleado actualizar(int id, Empleado empleado) {
        Empleado existente = buscar(id);
        existente.setNombre(empleado.getNombre());
        existente.setCargo(empleado.getCargo());
        existente.setCorreo(empleado.getCorreo());
        existente.setTelefono(empleado.getTelefono());
        existente.setServiciosHabilitados(empleado.getServiciosHabilitados());

        return empleadoRepository.save(existente);
    }
    @Override
    public void eliminar(int id) {
        Empleado empleado = buscar(id);
        empleadoRepository.delete(empleado);
    }


}
