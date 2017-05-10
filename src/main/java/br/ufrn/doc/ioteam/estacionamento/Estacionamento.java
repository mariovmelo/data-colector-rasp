package br.ufrn.doc.ioteam.estacionamento;

import java.util.ArrayList;

public class Estacionamento {
	
	private int numeroDeVagas;
	private ArrayList<Vaga> vagasEstacionamento;
	
	public Estacionamento(int numeroDeVagas) {
		this.numeroDeVagas = numeroDeVagas;
		
		vagasEstacionamento = new ArrayList<Vaga>();
		for (int i = 1; i <= numeroDeVagas; i++) {
			vagasEstacionamento.add(new Vaga(i));
		}
	}

	public int getNumeroDeVagas() {
		return numeroDeVagas;
	}
	
	public void alocarVaga(String idMotorista) {
		for (Vaga v : vagasEstacionamento) {
			if (v.isDisponivel()) {
				v.setDisponivel(false);
				v.setDonoDaVaga(idMotorista);
			}
		}
	}

	public void desalocarVaga(int idVaga) {
		for (Vaga v : vagasEstacionamento) {
			if (v.getVagaID() == idVaga){
				v.setDisponivel(true);
				v.setDonoDaVaga("");
			}
		}
	}
	
}
