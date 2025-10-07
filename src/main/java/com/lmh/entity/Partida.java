package com.lmh.entity;

import java.util.Date;

import com.lmh.manager.PartidaState;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PARTIDA")
public class Partida {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idpartida;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Sala idsala;
	
	private Date fechainicio;
	
	private Date fechafin;
	
	private Integer premiolinea;

	private Integer premiobingo;
	
	private Integer premiobote;
	
	private PartidaState estado;

	public Integer getIdpartida() {
		return idpartida;
	}

	public void setIdpartida(Integer idpartida) {
		this.idpartida = idpartida;
	}

	public Sala getIdsala() {
		return idsala;
	}

	public void setIdsala(Sala idsala) {
		this.idsala = idsala;
	}

	public Date getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public Date getFechafin() {
		return fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public Integer getPremiolinea() {
		return premiolinea;
	}

	public void setPremiolinea(Integer premiolinea) {
		this.premiolinea = premiolinea;
	}

	public Integer getPremiobingo() {
		return premiobingo;
	}

	public void setPremiobingo(Integer premiobingo) {
		this.premiobingo = premiobingo;
	}

	public Integer getPremiobote() {
		return premiobote;
	}

	public void setPremiobote(Integer premiobote) {
		this.premiobote = premiobote;
	}

	public PartidaState getEstado() {
		return estado;
	}

	public void setEstado(PartidaState estado) {
		this.estado = estado;
	}
	
	
}
