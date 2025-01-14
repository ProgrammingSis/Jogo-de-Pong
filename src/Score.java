

import java.awt.*;

import javax.swing.plaf.basic.BasicColorChooserUI.PropertyHandler;

/**
	Esta classe representa um placar no jogo. A classe princial do jogo (Pong)
	instancia dois objeto deste tipo, cada um responsável por gerenciar a pontuação
	de um player, quando a execução é iniciada.
*/

public class Score {
	private String idPlayer;
	private int score = 0;

	/**
		Construtor da classe Score.

		@param playerId uma string que identifica o player ao qual este placar está associado.
	*/
	public Score(String playerId){
		this.idPlayer = playerId;
	}

	/**
		Método de desenho do placar.
	*/
	public void draw(){
		if(this.idPlayer.equals("Player 1")){
			GameLib.drawText(this.idPlayer + ": "+ this.score, 70, GameLib.ALIGN_LEFT);
		}
		if(this.idPlayer.equals("Player 2")){
			GameLib.drawText(this.idPlayer + ": " + this.score, 70, GameLib.ALIGN_RIGHT);
		}		
	}

	/**
		Método que incrementa em 1 unidade a contagem de pontos.
	*/
	public void inc(){
		this.score++;
	}

	/**
		Método que devolve a contagem de pontos mantida pelo placar.

		@return o valor inteiro referente ao total de pontos.
	*/
	public int getScore(){

		return this.score;
	}
}