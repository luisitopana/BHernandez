package com.lmh.entity;

import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CARTON")
public class Carton {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carton_seq")
	@SequenceGenerator(name="carton_seq", sequenceName = "seq_carton", allocationSize=1)
	private Integer idcarton;
	
	@Size(min = 15, max = 4000)
	private String numeros;
	
	private boolean premiadolinea = false;
	
	private boolean premiadobingo = false;
	
	private boolean estaenjuego = false;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "idpartida")
	private Partida idpartida;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "idusuario")
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

	public boolean isPremiadolinea() {
		return premiadolinea;
	}

	public void setPremiadolinea(boolean premiadolinea) {
		this.premiadolinea = premiadolinea;
	}

	public boolean isPremiadobingo() {
		return premiadobingo;
	}

	public void setPremiadobingo(boolean premiadobingo) {
		this.premiadobingo = premiadobingo;
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

	public boolean isEstaenjuego() {
		return estaenjuego;
	}

	public void setEstaenjuego(boolean estaenjuego) {
		this.estaenjuego = estaenjuego;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
