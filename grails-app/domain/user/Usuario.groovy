package user

class Usuario {
	
	String nombre
	String apellido
	String email
	String telefono
	String user

    static constraints = {
		
		nombre()
		apellido ()
		email email: true, unique: true
		telefono ()
		user inList:['admin','normal']
		
		
    }
}
