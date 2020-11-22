/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n1_simuladorBancario
 * Autor: Equipo Cupi2 2017
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package uniandes.cupi2.simuladorBancario.mundo;

/**
 * Clase que representa un CDT.
 */
public class CDT
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Valor inicial del CDT.
     */
    private double valorInvertido;

    /**
     * Inter�s mensual que del CDT
     */
    private double interesMensual;

    /**
     * Mes de apertura del CDT.
     */
    private int mesApertura;
    
    private boolean noexisteCDT = true;

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Inicializa el CDT. <br>
     * <b>post: </b> El valor invertido, el inter�s mensual y la fecha se inicializaron en 0.
     */
    public CDT( )
    {
        valorInvertido = 0;
        interesMensual = 0;
        mesApertura = 0;
        verificarinvariante();
    }

    /**
     * Retorna el inter�s que paga el banco mensualmente por este CDT.
     * @return Inter�s mensual del CDT.
     */
    public double darInteresMensual( )
    {	verificarinvariante();
        return interesMensual;
        
    }

    /**
     * Inicia una inversi�n en un CDT .<br>
     * <b>post: </b> Se cambian los valores del CDT, con los valores recibidos. <br>
     * @param pMontoInvertido Monto de dinero que se va a invertir en el CDT. pMontoInvertido > 0.
     * @param pInteresMensual Inter�s mensual que va a ganar el CDT. pInteresMensual > 0.
     * @param pMes Mes de apertura del CDT. pMes > 0.
     */
    public void invertir( double pMontoInvertido, double pInteresMensual, int pMes ) throws Exception 
    {
    	
    	if (noexisteCDT == true) {
		noexisteCDT = false;
    	valorInvertido = pMontoInvertido;
        interesMensual = pInteresMensual;
        mesApertura = pMes;
        verificarinvariante();}
    	else {
    		Exception e=new Exception("ERROR: SOLO SE PUEDE TENER UN CDTACTIVO ALA VEZ");
    		throw e;
    	}
    }

    /**
     * Calcula el valor presente de la inversi�n teniendo en cuenta el inter�s de la cuenta. <br>
     * @param pMesActual Mes actual del simulador. pMesActual > 0.
     * @return Valor presente del dinero invertido en CDT.
     */
    public double calcularValorPresente( int pMesActual )
    {
        int mesesTranscurridos = pMesActual - mesApertura;
        verificarinvariante();
        return ( double ) ( valorInvertido + ( mesesTranscurridos * interesMensual * valorInvertido ) );
    }

    /**
     * Cierra el CDT y retorna el valor invertido m�s los intereses. <br>
     * <b>post: </b> Se retorn� el rendimiento del CDT, y se reinici� sus atributos a 0.
     * @param pMesActual Mes de cierre para calcular el rendimiento del CDT.
     * @return Valor de cierre del CDT.
     */
    public double cerrar( int pMesActual )
    {
        double valorCierre = calcularValorPresente( pMesActual );
        valorInvertido = 0;
        interesMensual = 0;
        mesApertura = 0;
        noexisteCDT=true;
        verificarinvariante();
        return valorCierre;
    }
    
    //TODO: 7 Calcular los intereses del cdt
    public double darInteresGenerado(int pMesActual) {
    	int mesesTranscurridos = pMesActual - mesApertura;
    	verificarinvariante();
    	return (double) ( mesesTranscurridos * interesMensual * valorInvertido );
    }
    
    public double darValorInvertido() {
    	return valorInvertido;
    }
    private void verificarinvariante() {
    	assert valorInvertido>= 0: "El monto invertido debe ser positivo";
    	assert interesMensual>= 0: "La tasa de inter�s debe ser positiva";
    	assert mesApertura >= 0:"El mes de apertura debe ser positivo";
    	
    	
    }
}