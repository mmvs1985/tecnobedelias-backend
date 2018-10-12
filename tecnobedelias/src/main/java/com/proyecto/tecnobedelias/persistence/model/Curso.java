package com.proyecto.tecnobedelias.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="cursos", indexes = {@Index(name="asignatura_curso_index", columnList="id_asignatura")})
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="anio", nullable=false)
	private int anio;
	
	@Column(name="semestre", nullable=false)
	private int semestre;
	
	@Column(name="fechaInicio", nullable=false)
	private Date fechaInicio;
	
	@Column(name="fechaFin", nullable=false)
	private Date fechaFin;
	
	@ManyToOne
	@JoinColumn(name="id_asignatura", foreignKey = @ForeignKey(name="curso_asignatura_fkey"))
	private Asignatura asignatura;
	
	@Column(name="activa", nullable=true)
	private boolean activa;
	
	@OneToMany(mappedBy="curso", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Curso_Estudiante> cursoEstudiante;	
	
	public Curso() {
	}

	public Curso(int anio, int semestre, Date fechaInicio, Date fechaFin) {
		this.anio = anio;
		this.semestre = semestre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.activa = true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}	
	
	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}
	
	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}	

	public List<Curso_Estudiante> getCursoEstudiante() {
		return cursoEstudiante;
	}

	public void setCursoEstudiante(List<Curso_Estudiante> cursoEstudiante) {
		this.cursoEstudiante = cursoEstudiante;
	}	
	
}

