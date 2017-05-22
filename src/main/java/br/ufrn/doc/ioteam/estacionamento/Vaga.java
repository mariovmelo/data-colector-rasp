package br.ufrn.doc.ioteam.estacionamento;

public class Vaga {
	
	private String donoDaVaga;
	private int numero;
	private int status;
	
	
	public Vaga (int numero) {
		this.numero = numero;
	}


	public String getDonoDaVaga() {
		return donoDaVaga;
	}


	public void setDonoDaVaga(String donoDaVaga) {
		this.donoDaVaga = donoDaVaga;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}

}
