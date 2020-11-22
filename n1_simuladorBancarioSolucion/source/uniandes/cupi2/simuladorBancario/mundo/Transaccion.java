package uniandes.cupi2.simuladorBancario.mundo;

public class Transaccion {
	public enum Tipo{
		ENTRADA,
		SALIDA
	}
	public enum Cuenta{
		AHORROS,
		CORRIENTE, 
		INVERSIONES
	}
	private int consecutivo;
	private double valor;
	private Tipo tipo;
	private Cuenta cuenta;
	
	public Transaccion(int pConsecutivo, double pValor, Tipo pTipo, Cuenta pCuenta){
		consecutivo=pConsecutivo;
		valor =pValor;
		tipo=pTipo;
		cuenta=pCuenta;
		
		
	}
	 public int darconsecutivo() {
		return consecutivo;
		 
	 }
	 public double darValor() {
		return valor;
		 
	 }
	 public int darTipoTransaccion() {
		return tipo.ordinal();
		 
	 }
	 public int darTipoCuenta() {
		return cuenta.ordinal();
		 
	 }

}
