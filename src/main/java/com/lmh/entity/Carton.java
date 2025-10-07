package com.lmh.entity;

import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CARTON")
public class Carton {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idcarton;
	
	@Size(min = 15, max = 4000)
	private String numeros;
	
	private boolean premiadoLinea = false;
	
	private boolean premiadoBingo = false;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Partida idpartida;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Usuario idusuario;
	
	@Version
	@CurrentTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public Integer getIdcarton() {
		return idcarton;
	}

	public void setIdcarton(Integer idcarton) {
		this.idcarton = idcarton;
	}

	public String getNumeros() {
		return numeros;
	}

	public void setNumeros(String numeros) {
		this.numeros = numeros;
	}

	public boolean isPremiadoLinea() {
		return premiadoLinea;
	}

	public void setPremiadoLinea(boolean premiadoLinea) {
		this.premiadoLinea = premiadoLinea;
	}

	public boolean isPremiadoBingo() {
		return premiadoBingo;
	}

	public void setPremiadoBingo(boolean premiadoBingo) {
		this.premiadoBingo = premiadoBingo;
	}

	public Partida getIdpartida() {
		return idpartida;
	}

	public void setIdpartida(Partida idpartida) {
		this.idpartida = idpartida;
	}

	public Usuario getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Usuario idusuario) {
		this.idusuario = idusuario;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
