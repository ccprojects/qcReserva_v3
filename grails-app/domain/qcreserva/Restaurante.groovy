package qcreserva

class Restaurante {
	
	String restaurante
	String especialidad
	String telefono
	String email
	String calle_nro
	String barrio
	Integer capacidad

    static constraints = {
		restaurante ()
		especialidad inList:["italiana","vegetariana","china", "mexicana", "parrillada", "Seleccionar"]
		email email:true, unique: true
		telefono ()
		calle_nro ()
		barrio  ()
		capacidad minSize:0
		
		
    }
}
