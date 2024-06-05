import java.awt.*;

/**
	Esta classe representa a bola usada no jogo. A classe princial do jogo (Pong)
	instancia um objeto deste tipo quando a execução é iniciada.
*/

public class Ball {
	private double cx, cy, width, height, speed, Xspeed, Yspeed;
	double directionX, directionY;
	Color color;
 public double wallHeight = 300, wallWidth = 300;
	/**
		Construtor da classe Ball. Observe que quem invoca o construtor desta classe define a velocidade da bola 
		(em pixels por millisegundo), mas não define a direção deste movimento. A direção do movimento é determinada 
		aleatóriamente pelo construtor.

		@param cx coordenada x da posição inicial da bola (centro do retangulo que a representa).
		@param cy coordenada y da posição inicial da bola (centro do retangulo que a representa).
		@param width largura do retangulo que representa a bola.
		@param height altura do retangulo que representa a bola.
		@param color cor da bola.
		@param speed velocidade da bola (em pixels por millisegundo).
	*/
	public Ball(double cx, double cy, double width, double height, Color color, double speed){
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.color = color;

		double direction = Math.random()*360; // ângulo aleatório
		double directionInRad = direction * Math.PI/180; //transformado em radiano

		directionX = Math.cos(directionInRad); 
		directionY = Math.sin(directionInRad);

		Xspeed = this.speed;
		Yspeed = this.speed;

	}


	/**
		Método chamado sempre que a bola precisa ser (re)desenhada.
	*/

	public void draw(){

		GameLib.setColor(color);
		GameLib.fillRect(cx, cy, width, height);
	}

	/**
		Método chamado quando o estado (posição) da bola precisa ser atualizado.

	 *Implementação: a posição atual da bola calculada com a fórmula S = S0 + velocidade*tempo.

	 *@param delta quantidade de millisegundos que se passou entre o ciclo anterior de
		atualização do jogo e o atual.
	 *@param cx posição atual da bola no eixo x
	 *@param cy posição atual da bola no eixo y
	 * **/
	public void update(long delta){
		cx = cx + Xspeed*delta;
		cy = cy + Yspeed*delta;

	}

	/**
		Método chamado quando detecta-se uma colisão da bola com um jogador.
	
		@param playerId uma string cujo conteúdo identifica um dos jogadores.
	*/
	public void onPlayerCollision(String playerId){
		this.speed = - this.speed;
		//como acessar a localização atual do player?
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com uma parede.

		@param wallId uma string cujo conteúdo identifica uma das paredes da quadra.
	*/

	public void onWallCollision(String wallId){
		
		if((cx >= 30 && cx <= 770) && (cy <= 120)){  // cima 
			this.Yspeed = this.Yspeed*(-1);
		}
		else if((cx >= 30 && cx <= 770) && (cy >= 570)){  // baixo
			this.Yspeed = this.Yspeed*(-1);
		}
		else if((cy >= 30 && cy <= 570) && (cx >= 30 || cx <= 770)){  // direita e esquerda
			this.Xspeed = this.Xspeed*(-1);
		}
		else if( ( cx <= 30 && cy <= 120 ) || (cx <= 30 && cy >= 570) || (cx >= 770 && cy >= 570) || (cx >= 770 && cy >= 570) ){  // áreas de bug
			this.Xspeed = this.Xspeed*(-1);
			this.Yspeed = this.Yspeed*(-1);
		}
	}

	/**
		Método que verifica se houve colisão da bola com uma parede.

		@param wall referência para uma instância de Wall contra a qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/
	public boolean checkCollision(Wall wall){
		switch (wall.getId()){
			case "Left":
					if (cx - width/2 < wall.getCx() + wall.getWidth()/2)
							return true;
					break;

			case "Right":
					if (cx + width/2 > wall.getCx() - wall.getWidth()/2)
							return true;
					break;
			case "Top":
					if (cy - height/2 < wall.getCy() + wall.getHeight()/2)
							return true;
					break;
			case "Bottom":
					if (cy + height/2 > wall.getCy() - wall.getHeight()/2)
							return true;
					break;
	}
	return false;
	}

	/**
		Método que verifica se houve colisão da bola com um jogador.

		@param player referência para uma instância de Player contra o qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/	
	Player playerColisao;
	public boolean checkCollision(Player player){
		if(player.getId().equals("Player 1")) {
			playerColisao = player;
			if(player.getCx() + player.getWidth()/2.0 + 5 >= cx - width/2.0  &&	// frente
		      (player.getHeight()/2.0 + player.getCy() >= (cy + height/2.0) && 
		       playerColisao.getCy() - playerColisao.getHeight()/2.0  <= (cy - height/2.0))){
				return true;

			}
			else if( cy + height/2.0 == player.getCy() - player.getHeight() && 		// em cima
					(cx + width/2.0 >= player.getCx() - player.getWidth()/2.0 && cx + width/2.0 
					<= player.getCx() + player.getWidth()/2.0 )){
				return true;

			}
			else if( cy - height/2.0 == player.getCy() + player.getHeight() && 		// em baixo
					(cx + width/2.0 >= player.getCx() - player.getWidth()/2.0 && cx + width/2.0 <= player.getCx() + player.getWidth()/2.0 )){
				return true;

			}
			else if( cx + width/2.0 == player.getCx() - player.getWidth() &&		// atrás
			( cy - height/2.0 >= player.getCy() - player.getHeight()/2.0 && cy - height/2.0 <= player.getCy() + player.getHeight()/2.0 ) ){
				return true;

			}
  
		}
		else if(player.getId().equals("Player 2")) {
			playerColisao = player;
			if(player.getCx() - player.getWidth()/2.0 <= cx + width/2.0 && // em frente
		       (player.getHeight()/2.0 + player.getCy() - (cy + height/2.0) >= 0 && 
		        playerColisao.getCy() - playerColisao.getHeight()/2.0  <= (cy - height/2.0))){
				return true;

			}
			else if( cy + height/2.0 == player.getCy() - player.getHeight() && 		// em cima
					(cx + width/2.0 >= player.getCx() - player.getWidth()/2.0 && cx + width/2.0 <= player.getCx() + player.getWidth()/2.0 )){
				return true;
				
			}
			else if( cy - height/2.0 == player.getCy() + player.getHeight() && 		// em baixo
					(cx + width/2.0 >= player.getCx() - player.getWidth()/2.0 && cx + width/2.0 <= player.getCx() + player.getWidth()/2.0 )){
				return true;

			}
			else if( cx - width/2.0 == player.getCx() + player.getWidth() && ( cy - height/2.0 >=
			 player.getCy() - player.getHeight()/2.0 && cy - height/2.0 <=
			 player.getCy() + player.getHeight()/2.0 ) ){
				return true;

			}
		}
		return false;
	}

	/**
		Método que devolve a coordenada x do centro do retângulo que representa a bola.
		@return o valor double da coordenada x.
	*/
	
	public double getCx(){

		return this.cx;
	}

	/**
		Método que devolve a coordenada y do centro do retângulo que representa a bola.
		@return o valor double da coordenada y.
	*/

	public double getCy(){

		return this.cy;
	}

	/**
		Método que devolve a velocidade da bola.
		@return o valor double da velocidade.

	*/

	public double getSpeed(){

		return speed;
	}

}