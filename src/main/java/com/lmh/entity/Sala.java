package com.lmh.entity;

import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;

@Entity
@Table(name = "SALA")
public class Sala {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idsala;
	
	private String codigo;
	
	private String nombre;
	
	private Integer precio;
	
	private Integer porcentajelinea;
	
	private Integer porcentajebingo;
	
	private Integer porcentajebote;
	
	private Integer bolamaxbote = 44;
	
	@Version
	@CurrentTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public Integer getIdsala() {
		return idsala;
	}

	public void setIdsala(Integer idsala) {
		this.idsala = idsala;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public Integer getPorcentajelinea() {
		return porcentajelinea;
	}

	public void setPorcentajelinea(Integer porcentajelinea) {
		this.porcentajelinea = porcentajelinea;
	}

	public Integer getPorcentajebingo() {
		return porcentajebingo;
	}

	public void setPorcentajebingo(Integer porcentajebingo) {
		this.porcentajebingo = porcentajebingo;
	}

	public Integer getPorcentajebote() {
		return porcentajebote;
	}

	public void setPorcentajebote(Integer porcentajebote) {
		this.porcentajebote = porcentajebote;
	}

	public Integer getBolamaxbote() {
		return bolamaxbote;
	}

	public void setBolamaxbote(Integer bolamaxbote) {
		this.bolamaxbote = bolamaxbote;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	
}