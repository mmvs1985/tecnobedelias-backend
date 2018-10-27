package com.proyecto.tecnobedelias.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.tecnobedelias.persistence.model.Asignatura;
import com.proyecto.tecnobedelias.persistence.model.Carrera;
import com.proyecto.tecnobedelias.persistence.model.Usuario;
import com.proyecto.tecnobedelias.persistence.repository.AsignaturaRepository;
import com.proyecto.tecnobedelias.service.AsignaturaService;
import com.proyecto.tecnobedelias.service.CarreraService;

@RestController

@RequestMapping("/asignatura")
public class AsignaturaController{
	private AsignaturaRepository asignaturaRepository;	
	public AsignaturaController(AsignaturaRepository asignaturaRepository) {
		super();
		this.asignaturaRepository = asignaturaRepository;
	}
	
	@Autowired
	AsignaturaService asignaturaService;	
	
	@GetMapping("/listar")
	@PreAuthorize("hasRole('DIRECTOR') or hasRole('ESTUDIANTE')")
	public List<Asignatura> listarAsignaturas(){
		return asignaturaService.listarAsignaturas();
	}

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public boolean crearAsignatura(@RequestBody(required = true) Asignatura asignatura){  
    	if (!asignaturaService.existeAsignaturaCodigo(asignatura.getCodigo())) {
    		if (!asignaturaService.existeAsignaturaNombre(asignatura.getNombre())) {
    			asignaturaRepository.save(asignatura);
    			return true;
    		}
    		else return false;
    	}
    	else return false;
    }
    
    @GetMapping("/borrar")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public boolean borrarAsignatura(HttpServletRequest request,
			@RequestParam(name = "nombre", required = true) String nombre) {
    	if (asignaturaService.existeAsignaturaNombre(nombre)) { 
    		Asignatura asignaturaExistente = asignaturaService.obtenerAsignaturaNombre(nombre);
    		if (asignaturaExistente.getAsignaturaCarrera().isEmpty()) {
    			if (asignaturaExistente.getCursos().isEmpty()) {
    				if (asignaturaExistente.getExamenes().isEmpty()) {
    					asignaturaService.bajaAsignatura(asignaturaExistente);
    					return true;  
    				}
    				else return false;
    			}
    			else return false;
    		}
    		else return false;
    	}
    	else return false;
    }
    
    
    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public boolean modificarAsignatura(HttpServletRequest request,
    		@RequestBody(required = true) Asignatura asignatura, @RequestParam(name = "nombre", required = true) String nombre) {
    	if (asignaturaService.existeAsignaturaNombre(nombre)) {
    		Asignatura asignaturaExistente;
    		if (asignaturaService.existeAsignaturaCodigo(asignatura.getCodigo())) {
    			asignaturaExistente = asignaturaService.obtenerAsignaturaCodigo(asignatura.getCodigo());
    			if (asignaturaExistente.getNombre().equals(nombre)) {
    				asignaturaExistente = asignaturaService.obtenerAsignaturaNombre(nombre);
    				asignatura.setId(asignaturaExistente.getId());
    				asignatura.setNombre(nombre);
    				asignaturaService.modificacionAsignatura(asignatura);
    				return true;  
    			}
    			else return false;
    		}
    		else {
    			asignaturaExistente = asignaturaService.obtenerAsignaturaNombre(nombre);
				asignatura.setId(asignaturaExistente.getId());
				asignatura.setNombre(nombre);
				asignaturaService.modificacionAsignatura(asignatura);
				return true; 
    		}
    	}
    	else return false;
    }
    

}

