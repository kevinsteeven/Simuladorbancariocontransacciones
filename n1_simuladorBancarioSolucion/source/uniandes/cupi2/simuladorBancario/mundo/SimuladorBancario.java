/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n1_simuladorBancario
 * Autor: Equipo Cupi2 2017
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package uniandes.cupi2.simuladorBancario.mundo;
import java.util.ArrayList;

/**
 * Clase que representa el simulador bancario para las tres cuentas de un cliente.
 */
public class SimuladorBancario
{
	
	public static final double INVERSION_MAXIMO = 100000000;
	
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

	//TODO: 1 Crear atributo
    private double interesGenerado;
	
    /**
     * Cédula del cliente.
     */
    private String cedula;

    /**
     * Nombre del cliente.
     */
    private String nombre;

    /**
     * Mes actual.
     */
    private int mesActual;

    /**
     * Cuenta corriente del cliente.
     */
    private CuentaCorriente corriente;

    /**
     * Cuenta de ahorros del cliente.
     */
    private CuentaAhorros ahorros;

    /**
     * CDT del cliente.
     */
    private CDT inversion;
    
    private ArrayList<Transaccion> transaccion;
    private int consecutivo;

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Inicializa el simulador con la información del cliente. <br>
     * <b>post: </b> El mes fue inicializado en 1, y las tres cuentas (CDT, corriente y de ahorros) fueron inicializadas como vacías. <br>
     * @param pCedula Cédula del nuevo cliente. pCedula != null && pCedula != "".
     * @param pNombre Nombre del nuevo cliente. pNombre != null && pNombre != "".
     */
    public SimuladorBancario( String pCedula, String pNombre )
    {
        // Inicializa los atributos personales del cliente
        nombre = pNombre;
        cedula = pCedula;
        // Inicializa el mes en el 1
        mesActual = 1;
        // Inicializa las tres cuentas en vacío
        corriente = new CuentaCorriente( );
        ahorros = new CuentaAhorros( );
        inversion = new CDT( );
        transaccion = new ArrayList<Transaccion>();
        consecutivo=0;
        
        verificarinvariante();
    }

    /**
     * Retorna el nombre del cliente.
     * @return Nombre del cliente.
     */
    public String darNombre( )
    {	verificarinvariante();
        return nombre;
    }
    
    public double darInteresGenerado() {
    	verificarinvariante();
    	return interesGenerado + ahorros.darInteresGenerado();
    }

    /**
     * Retorna la cédula del cliente.
     * @return Cédula del cliente.
     */
    public String darCedula( )
    {	verificarinvariante();
        return cedula;
    }

    /**
     * Retorna la cuenta corriente del cliente.
     * @return Cuenta corriente del cliente.
     */
    public CuentaCorriente darCuentaCorriente( )
    {	verificarinvariante();
        return corriente;
    }

    /**
     * Retorna el CDT del cliente.
     * @return CDT del cliente.
     */
    public CDT darCDT( )
    {	verificarinvariante();
        return inversion;
    }

    /**
	 * Retorna la cuenta de ahorros del cliente.
	 * @return Cuenta de ahorros del cliente.
	 */
	public CuentaAhorros darCuentaAhorros( )
	{	verificarinvariante();
	    return ahorros;
	}

	/**
     * Retorna el mes en el que se encuentra la simulación.
     * @return Mes actual.
     */
    public int darMesActual( )
    {	verificarinvariante();
        return mesActual;
    }

    /**
     * Calcula el saldo total de las cuentas del cliente.
     * @return Saldo total de las cuentas del cliente.
     */
    public double calcularSaldoTotal( )
    {	verificarinvariante();
        return corriente.darSaldo( ) + ahorros.darSaldo( ) + inversion.calcularValorPresente( mesActual );
    }

    /**
     * Invierte un monto de dinero en un CDT. <br>
     * <b>post: </b> Invirtió un monto de dinero en un CDT.
     * @param pMonto Monto de dinero a invertir en un CDT. pMonto > 0.
     * @param pInteresMensual Interés del CDT elegido por el cliente.
     */
    public void invertirCDT( double pMonto, String pInteresMensual ) throws Exception
    {	try {
		double pInteres = Double.parseDouble(pInteresMensual) / 100.0;
		inversion.invertir( pMonto, pInteres, mesActual );
		consecutivo++;
		Transaccion nueva = new Transaccion(consecutivo,pMonto,Transaccion.Tipo.ENTRADA,Transaccion.Cuenta.INVERSIONES);
		transaccion.add(nueva);
		verificarinvariante();}
    catch (Exception e) {
		System.out.println(e.getMessage());
	}
    }

    /**
     * Consigna un monto de dinero en la cuenta corriente. <br>
     * <b>post: </b> Consignó un monto de dinero en la cuenta corriente.
     * @param pMonto Monto de dinero a consignar en la cuenta. pMonto > 0.
     */
    public void consignarCuentaCorriente( double pMonto )
    {	
        corriente.consignarMonto( pMonto );
        consecutivo++;
        Transaccion nueva = new Transaccion(consecutivo,pMonto,Transaccion.Tipo.ENTRADA,Transaccion.Cuenta.CORRIENTE);
		transaccion.add(nueva);
        verificarinvariante();
    }

    /**
     * Consigna un monto de dinero en la cuenta de ahorros. <br>
     * * <b>post: </b> Consignó un monto de dinero en la cuenta de ahorros.
     * @param pMonto Monto de dinero a consignar en la cuenta. pMonto > 0.
     */
    public void consignarCuentaAhorros( double pMonto )
    {	
        ahorros.consignarMonto( pMonto );
        consecutivo++;
        Transaccion nueva = new Transaccion(consecutivo,pMonto,Transaccion.Tipo.ENTRADA,Transaccion.Cuenta.AHORROS);
		transaccion.add(nueva);
        verificarinvariante();
    }

    /**
     * Retira un monto de dinero de la cuenta corriente. <br>
     * <b>pre: </b> La cuenta corriente ha sido inicializada
     * <b>post: </b> Si hay saldo suficiente, entonces este se redujo en el monto especificado.
     * @param pMonto Monto de dinero a retirar de la cuenta. pMonto > 0.
     */
    public void retirarCuentaCorriente( double pMonto )
    {
        corriente.retirarMonto( pMonto );
        consecutivo++;
        Transaccion nueva = new Transaccion(consecutivo,pMonto,Transaccion.Tipo.SALIDA,Transaccion.Cuenta.CORRIENTE);
		transaccion.add(nueva);
        verificarinvariante();
    }

    /**
     * Retira un monto de dinero de la cuenta de ahorros. <br>
     * <b>post: </b> Se redujo el saldo de la cuenta en el monto especificado.
     * @param pMonto Monto de dinero a retirar de la cuenta. pMonto > 0.
     */
    public void retirarCuentaAhorros( double pMonto )
    {
        ahorros.retirarMonto( pMonto );
        consecutivo++;
        Transaccion nueva = new Transaccion(consecutivo,pMonto,Transaccion.Tipo.SALIDA,Transaccion.Cuenta.AHORROS);
		transaccion.add(nueva);
        verificarinvariante();
    }
    

    /**
     * Avanza en un mes la simulación. <br>
     * <b>post: </b> Se avanzó el mes de la simulación en 1. Se actualizó el saldo de la cuenta de ahorros.
     */
    public void avanzarMesSimulacion( )
    {
        mesActual += 1;
        ahorros.actualizarSaldoPorPasoMes( );
        if (ahorros.darSaldo()>0) {
        	consecutivo++;
            Transaccion nueva = new Transaccion(consecutivo,interesGenerado,Transaccion.Tipo.ENTRADA,Transaccion.Cuenta.AHORROS);
    		transaccion.add(nueva);
        	
        }
        if(inversion.darValorInvertido()>0) {
        	consecutivo++;
            Transaccion nueva = new Transaccion(consecutivo,inversion.darValorInvertido()*inversion.darInteresMensual(),Transaccion.Tipo.ENTRADA,Transaccion.Cuenta.INVERSIONES);
    		transaccion.add(nueva);
        	
        }
        verificarinvariante();
    }

    /**
     * Cierra el CDT, pasando el saldo a la cuenta corriente. <br>
     * <b>pre: </b> La cuenta corriente y el CDT han sido inicializados. <br>
     * <b>post: </b> El CDT quedó cerrado y con valores en 0, y la cuenta corriente aumentó su saldo en el valor del cierre del CDT.
     */
    public void cerrarCDT( )
    {
    	//TODO: 8 agregar el interes generado por el cdt al total de la simulacion
        interesGenerado += inversion.darInteresGenerado(mesActual);
        double valorCierreCDT = inversion.cerrar( mesActual );
        consecutivo++;
        Transaccion nueva = new Transaccion(consecutivo,valorCierreCDT,Transaccion.Tipo.SALIDA,Transaccion.Cuenta.INVERSIONES);
		transaccion.add(nueva);
        consignarCuentaCorriente( valorCierreCDT );
        verificarinvariante();
    }
    
    /**
     * Retrira el saldo total la cuenta de ahorros, pasandolo a la cuenta corriente. <br>
     * <b>pre: </b> La cuenta corriente y el la cuenta de ahorros han sido inicializados. <br>
     * <b>post: </b> La cuenta de ahorros queda vacia ( con valores en 0 ), y la cuenta corriente aumentó su saldo en el valor del saldo total que tenia la cuenta de ahorros.
     */
    public void pasarAhorrosToCorriente()
    {
    	double cantidad = ahorros.darSaldo();
    	ahorros.cerrarCuenta();
    	corriente.consignarMonto(cantidad);
    	consecutivo++;
        Transaccion nueva = new Transaccion(consecutivo,cantidad,Transaccion.Tipo.SALIDA,Transaccion.Cuenta.AHORROS);
		transaccion.add(nueva);
		consecutivo++;
        Transaccion nueva1 = new Transaccion(consecutivo,cantidad,Transaccion.Tipo.ENTRADA,Transaccion.Cuenta.CORRIENTE);
		transaccion.add(nueva1);
    	verificarinvariante();
    }

    /**
     * Avanza la simulción un numero de meses dado por parámetro.
     * @param pMeses numero de meses a avanzar
     * <b>post: </b> Se avanzaron los meses de la simulación. Se actualizaron los saldos.
     */
    private void verificarinvariante(){
    	assert interesGenerado >= 0: "El interes generado debe ser positivo";
    	assert cedula != null && !cedula.equals(""): "La cedula es invalida";
    	assert nombre != null && !nombre.equals(""): "El nombre es invalido";
    	assert mesActual >= 0: "El mes actual debe ser mayor o igual al mes de apertura";
    	assert corriente != null: "La cuenta corriente no ha sido inicializada";
    	assert ahorros != null: "La cuenta de ahorros no ha sido inicializada";
    	assert inversion!= null : "El CDT no ha sido inicializado";
    	assert inversion.calcularValorPresente(mesActual) + ahorros.darSaldo() < INVERSION_MAXIMO:"ERROR: SE SUPERÓ EL MONTO MÁXIMO DE INVERSIÓN";
    }
    public void metodo1( int pMeses )
    {
    	mesActual += pMeses;
    	ahorros.actualizarSaldoMeses(pMeses);
    	for(int i=0;i<pMeses;i++) {
    		 if (ahorros.darSaldo()>0) {
    	        	consecutivo++;
    	            Transaccion nueva = new Transaccion(consecutivo,interesGenerado,Transaccion.Tipo.ENTRADA,Transaccion.Cuenta.AHORROS);
    	    		transaccion.add(nueva);}
    		 if(inversion.darValorInvertido()>0) {
    	         	consecutivo++;
    	             Transaccion nueva = new Transaccion(consecutivo,inversion.darValorInvertido()*inversion.darInteresMensual(),Transaccion.Tipo.ENTRADA,Transaccion.Cuenta.INVERSIONES);
    	     		transaccion.add(nueva);
    	         	
    	         }

    	}
    	 
    	verificarinvariante();
    }

    /**
     * Reinicia la simulación.
     * @return interes total generado por la simulación.
     */
    public double metodo2( )
    {	
    	cerrarCDT();
    	corriente.cerrarCuenta();
    	double respuesta = interesGenerado + ahorros.darInteresGenerado();
    	ahorros.cerrarCuenta();
    	interesGenerado = 0;
    	mesActual = 1;
    	consecutivo=0;
    	transaccion.clear();
    	verificarinvariante();
        return respuesta;
    }

	public int metodo3(int pTipo, int pCuenta) {
		double mayortransaccion=0;
		int consecutivomayortransaccion = 0;
		for( int i=0;i<transaccion.size();i++) {
			if (transaccion.get(i).darTipoCuenta()==pCuenta && transaccion.get(i).darTipoTransaccion()==pTipo&&transaccion.get(i).darValor()>=mayortransaccion) {
				mayortransaccion=transaccion.get(i).darValor();
				consecutivomayortransaccion=transaccion.get(i).darconsecutivo();
			}
		}
		verificarinvariante();
		return consecutivomayortransaccion;
	}
}