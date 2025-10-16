package com.lmh.entity;

import java.util.Date;

import com.lmh.manager.PartidaState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "PARTIDA")
public class Partida {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partida_seq")
	@SequenceGenerator(name="partida_seq", sequenceName = "seq_partida", allocationSize=1)
	@Column(name = "idpartida")
	private Integer idpartida;

	@ManyToOne
	@JoinColumn(name = "idsala")
	private Sala idsala;

	@Column(name = "fechainicio")
	private Date fechainicio;

	@Column(name = "fechafin")
	private Date fechafin;

	@Column(name = "premiolinea")
	private Integer premiolinea;

	@Column(name = "premiobingo")
	private Integer premiobingo;

	@Column(name = "estado")
	private PartidaState estado;
	
	@Column(name = "saliobote")
	private boolean saliobote;

	@Version
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;

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

	public PartidaState getEstado() {
		return estado;
	}

	public void setEstado(PartidaState estado) {
		this.estado = estado;
	}

	public boolean isSaliobote() {
		return saliobote;
	}

	public void setSaliobote(boolean saliobote) {
		this.saliobote = saliobote;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
