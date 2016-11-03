import java.math.BigInteger;

public class RSA {

	private BigInteger p =new BigInteger("36515209"); 

	private BigInteger q =new BigInteger("58806571");

	private BigInteger e = new BigInteger("2311");

	private BigInteger d = new BigInteger("804669563472151");

	private BigInteger n;

	RSA() {

		//Se realiza la comprobaci�n de lo parametros escogidos
		n = p.multiply(q);
		BigInteger p1= p.subtract(BigInteger.ONE);
		BigInteger q1 = q.subtract(BigInteger.ONE);
		BigInteger fn = p1.multiply(q1);

		System.out.println("Se comprueba que cumpla la condici�n e x d mod fn " + e +" x " + d  + " mod " + fn +" = "  + e.multiply(d).mod(fn));



		//Se procede a encriptar y desenciptar
		String mensajePlano = "Hola";
		BigInteger mensajeEncriptado = encriptarMensaje(mensajePlano);
		String mensajeDesencriptado = new String(desencriptarMensaje(mensajeEncriptado).toByteArray()); 

		System.out.println("Mensaje a encriptar: " + mensajePlano);
		System.out.println("Mensaje encriptado: " + mensajeEncriptado);
		System.out.println("Mensaje desencriptado: " + mensajeDesencriptado);



	}


	public BigInteger encriptarMensaje(String mensaje) {

		int i = 0;
		int nbits = n.bitLength(); //Se calcula el numero de bits de p*n

		//Se obtienen los bytes del mensaje a encriptar
		BigInteger  mensajeEnBites= new BigInteger( mensaje.getBytes() );

		BigInteger numeroByte = BigInteger.ZERO;  //Inicia en cero
		BigInteger numeroDos = new BigInteger( "2" ); 
		BigInteger bitmask = numeroDos.pow(nbits-1).subtract(BigInteger.ONE);


		while( mensajeEnBites.compareTo(bitmask) == 1 ) {
			numeroByte = cifrar(mensajeEnBites.and(bitmask),e,n).shiftLeft(i*(nbits)).or(numeroByte);
			mensajeEnBites = mensajeEnBites.shiftRight(nbits-1);
			i++;
		}
		return cifrar(mensajeEnBites.and(bitmask),e,n).shiftLeft(i*(nbits)).or(numeroByte);
	}	


	public BigInteger desencriptarMensaje(BigInteger mensajeEnBites) {

		int i = 0;
		int nbits = n.bitLength(); //Se calcula el numero de bits de p*n


		BigInteger numeroByte = BigInteger.ZERO;  //Inicia en cero
		BigInteger numeroDos = new BigInteger( "2" ); 
		BigInteger bitmask = numeroDos.pow(nbits).subtract(BigInteger.ONE);


		while( mensajeEnBites.compareTo(bitmask) == 1 ) {
			numeroByte = cifrar(mensajeEnBites.and(bitmask),d,n).shiftLeft(i*(nbits-1)).or(numeroByte);
			mensajeEnBites = mensajeEnBites.shiftRight(nbits);
			i++;
		}

		return cifrar(mensajeEnBites.and(bitmask),d,n).shiftLeft(i*(nbits-1)).or(numeroByte);
	}	



	//Aplica la formula de RSA para cifrar el mensaje
	BigInteger cifrar( BigInteger msg, BigInteger cryptkey, BigInteger modkey ) {         
		return msg.modPow( cryptkey,modkey );
	}

	public static void main(String[] args) {
		new RSA();
	}


}