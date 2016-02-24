package qcreserva

import java.util.Date;

class Reserva {

	
	String restaurante
	String usuario
	Integer comensales
	Date fecha
	String hora
	//int id
	

	
	
    static constraints = {
		
		restaurante()
		comensales()
		fecha()
		hora()	
		usuario()
    }
}
