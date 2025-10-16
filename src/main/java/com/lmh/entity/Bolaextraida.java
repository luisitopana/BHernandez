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

@Entity
@Table(name = "BOLAEXTRAIDA")
public class Bolaextraida {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bolaextraida_seq")
	@SequenceGenerator(name="bolaextraida_seq", sequenceName = "seq_bolaextraida", allocationSize=1)
	private Integer idbolaextraida;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "idpartida")
	private Partida idpartida;
	
	private Integer numerobola;

	private Integer orden;
	
	@Version
	@CurrentTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public Integer getIdbolaextraida() {
		return idbolaextraida;
	}

	public void setIdbolaextraida(Integer idbolaextraida) {
		this.idbolaextraida = idbolaextraida;
	}

	public Partida getIdpartida() {
		return idpartida;
	}

	public void setIdpartida(Partida idpartida) {
		this.idpartida = idpartida;
	}

	public Integer getNumerobola() {
		return numerobola;
	}

	public void setNumerobola(Integer numerobola) {
		this.numerobola = numerobola;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
