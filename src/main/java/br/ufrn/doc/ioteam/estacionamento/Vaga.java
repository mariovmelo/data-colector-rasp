package br.ufrn.doc.ioteam.estacionamento;

public class Vaga {
	
	private String donoDaVaga;
	private int vagaID;
	private boolean disponivel;
	
	
	public Vaga (int vagaID) {
		this.vagaID = vagaID;
		this.disponivel = false;
	}

	public int getVagaID() {
		return vagaID;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	
	public String getDonoDaVaga() {
		return donoDaVaga;
	}

	public void setDonoDaVaga(String donoDaVaga) {
		this.donoDaVaga = donoDaVaga;
	}
}
