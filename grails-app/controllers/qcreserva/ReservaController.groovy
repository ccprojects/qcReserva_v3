package qcreserva

//import java.lang.String;
import java.lang.Float;
import java.util.Date;
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import java.text.DateFormat
import java.text.SimpleDateFormat
import gorm.recipes.*

@Transactional(readOnly = true)
class ReservaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Reserva.list(params), model:[reservaInstanceCount: Reserva.count()]
    }
	
	
    def show(Reserva reservaInstance) {
        respond reservaInstance
    }

    def create() {
        respond new Reserva(params)
    }
	def Selecciona(){
	
		def restauranteInstance = Restaurante.list(params.id)
		//def datos= params.id
		[restaurants: restauranteInstance]
	
		}
	
def Reservar(params){
		
	//def restauran= qc15.Restaurante.get(params.id)
	//[restaurants:params, restauran:restauran]
	
	//Prueba nuevaReserva= new Prueba(params)
	//nuevaReserva.save()
	
	//def datosres= new qc15.Prueba(params)
	
	def datosres=params
	
	def datos= qcreserva.Restaurante.get(params.id)
	def restaurante= params.restaurante
	def cap= params.capacidad
	
	//def datosres= qc15.Prueba.get(params.id)
	//def datosres= nuevaReserva.get(params.id)
	//def datosres= qc15.Prueba.get(params.id)
	
//	def datosidReserva= qcReserva.Reserva.get(params.id) //trae los datos de la primer reserva con ese id, no vale
	
	/*
	def f= params.fecha
	f= Date.parse( "yyyy-MM-dd", "2011-01-15" )
		*/
	//def listadoReservas = qc15.Prueba.findAllByIdrestauranteAndFechaAndHora(datos.id, datos.fecha, datos.hora)
	//def listadoReservas = qcReserva.Reserva.findAllWhereRestauranteAndFechaAndHora(restaurante, datosres.fecha, datosres.hora)
	
	//println listadoReservas.comensales.sum()
	
	//def restaur= qcReserva.Reserva.findByIdrestaurante(datos)
	//def c= qcReserva.Restaurante.findAllByIdrestaurante(datos.id)
/*
	def cap=params.capacidad
	//def cap1=datos.capacidad
	comens=datosres.comensales
	if(cap >= datosres.comensales ){
		//guardar reserva:
		Reserva prueba= new Reserva(params)
		prueba.save()
		println "Reserva Confirmada"
		render "Reserva Confirmada"
	}else{
		render "Sin disponobilidad"
	
	}
	*/
	
	
	//[reservas:listadoReservas,
	//datos es el id resyau y restaurante es el nombre
	[datos:datos, restaurante:restaurante, cap:cap]

}

@Transactional
def Confirma(params){
	def datosres=params
	def rest= qcreserva.Restaurante.get(params.id)
	def cap=rest.capacidad
	def f
	
	
	//def cap1=datos.capacidad

	//def listreserva= qcreserva.Reserva.findAllByRestauranteAndFechaAndHora(params.restaurante, params.fecha, params.hora)

	//def listadoReservas = qcreserva.Reserva.findAllByIdrestauranteAndFechaAndHora(datos.id, datos.fecha, datos.hora)
	
	//def listaReservas= 
	//def listadoReservas = qcreserva.Reserva.findAllByRestauranteAndFechaAndHora(params.restaurante, new Date( params.fecha), new Float(params.hora))
	///def listadoReservas = qcreserva.Reserva.findAllByRestauranteAndHora(params.restaurante, params.hora)
	
	
		//def reservas= qcreserva.Reserva.findAllWhere([reserva.restaurante:params.restaurante], [reserva.fecha: params.fecha], [Reserva.hora:params.hora])
	
	///def listadoreservas= qcreserva.Reserva.findAllWhere(restaurante:params.restaurante, fecha: params.fecha, hora: params.hora)
	
	//def reservas= Reserva.findAllWhere([restaurante:params.restaurante], [fecha: params.fecha], [hora:params.hora])
	//def suma=listadoreservas.comensales.count()
	//def suma=listadoReservas.comensales.count()
	///def suma=listreservas.comensales.count()
	//def comens=params.comensales
	///if(cap > suma ){
	 	//render " -- "+params

	
		if(cap >= (Integer.parseInt(params.suma)+Integer.parseInt(params.comensales))){
			//	guardar reserva:
			//def Reserva prueba= new Reserva(params)
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Reserva nuevaRes= new Reserva()
			//
			nuevaRes.create()
			nuevaRes.putAt("fecha" , sdf.parse(params.fecha))
			nuevaRes.putAt("usuario" , params.usuario)
			nuevaRes.putAt("comensales" , Integer.parseInt(params.comensales))
			nuevaRes.putAt("restaurante" , params.restaurante)
			nuevaRes.putAt("hora" , params.hora)

//			nuevaRes.save() //( flush:true )
			 
			//render "Total de reservas:"${suma}"Reserva Confirmada"
			nuevaRes.save(flush:true)
		//	nuevaRes.validate()
			//nuevaRes.all
			render "Reserva Confirmada: "+nuevaRes.comensales+" param: "+params.comensales
	}else{
		
		render "Sin disponibilidad"
	
	
	}

}


def prueba1(params){
	
	//def listaReservas = Reserva.list(params.restaurante)
	def listaReservas = Reserva.findAllByRestaurante(params.restaurante)
	
	//def listaReservas= qcreserva.Reserva.findAllByRestaurante(params.restaurante)
	
	//import java.text.SimpleDateFormat
	/*
	def format = new SimpleDateFormat("dd-MM-yyyy")
	def date = format.parse("14-01-2014")
	println date  // prints "Tue Jan 14 00:00:00 CST 2014"
	*/
	
	
	
	//para closure:
	def original = new SimpleDateFormat("yyyy/MM/dd")
	def formatoObjetivo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	//def fe= original.parse(params.fecha)
	//def f= formatoObjetivo.format(fe)
	//def formato= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")	//formato en la que està
	///def f= params.fecha //dato que quiero formatear
	//def fecha= formato.parse(f)
	
	//def reservadas= listaReservas.findAllWhere(fecha: fe)
	
	/*
	def format1 = '2014-01-21 00:00:00'
	def format2 = Date.parse("yyyy-MM-dd hh:mm:ss", format1).format("dd/MM/yyyy")
	assert format2 == '01/21/2014'
	*/
	
	///Pasaje de date a String
	def formatA= "yyyy/MM/dd"
	//def formatB= Date.parse('yyyy/MM/dd hh:mm:ss', formatA).format("yyyy/MM/dd")
	//assert formatB == params.fecha
	
	def otra= listaReservas.findAllWhere(usuario: params.usuario, restaurante: params.restaurante)
/*
	def reservadas = qcreserva.Reserva.executeQuery(
		"from Reserva where restaurante = :searchRestaurante and fecha = :searchFecha", // and hora = :searchHora",
		[searchRestaurante: params.restaurante, searchFecha: params.fecha]) //, searchHora: params.hora])
	*/
	/*def result = Person.createCriteria().list{
		if (firstNameToSearch != null) {
			eq('firstName', firstNameToSearch)
		}
		if (lastNameToSearch != null) {
			eq('lastName', lastNameToSearch)
		}
	} */
	
	/*def result = Reserva.createCriteria().list{
			
	}*/
	//def result = Person.executeQuery(
		//"from Person where firstName = ? and lastName = ?", ['John', 'Doe'])
	
	//def result = Reserva.executeQuery(		//ver query
		//"from Reserva where restaurante = ? and fecha = ? and hora = ?", [params.restaurante, params.fecha, params.hora])
	
	//def reservadas = Reserva.createCriteria().list{	}
	
	
	//def reservadas= listaReservas.findAllByFecha(fecha: fe)
	
	def ver= qcreserva.Reserva.get(params.id)
	//def listadoReservas= Reserva.findByRestaurante(params.restaurante)
	//def reservadas= qcreserva.Reserva.queryList("SELECT * FROM qcreserva.Reserva WHERE fecha = ${params.fecha}")
	 /*
	def criteria= Reserva.createCriteria(){
//	def reservadas = criteria.list(){
		eq('restaurante', params.restaurante)
	and {	eq('fecha', params.fecha)}
	and {	eq('hora', params.hora)}
	}  */
	//def reservadas= queryList(SELECT * FROM qcreserva.Reserva WHERE restaurante LIKE '%params.restaurante%' AND fecha LIKE '%params.fecha%' AND hora LIKE '%params.hora%')
	
	def reservadas= listaReservas.fin   .findAllByFechaAndHora(params.fecha, params.hora)
	
	[fec: formatB, otra:otra, ver:ver, listaReservas: listaReservas, reservadas: reservadas, d:d]  //f:f,
	
	}



def prueba(params){
	
	//def listaReservas = Reserva.list(params.restaurante)
	//def listaReservas = Reserva.findByRestaurante(params.restaurante)

	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date fechaReserva = sdf.parse(params.fecha);

	
		
	def listaReservas = Reserva.findAllByRestauranteAndFechaAndHora(params.restaurante, fechaReserva, params.hora)
	def suma=listaReservas.comensales.sum()
	
	
	//def listaReservas= qcreserva.Reserva.findAllByRestaurante(params.restaurante)
	
	//import java.text.SimpleDateFormat
	/*
	def format = new SimpleDateFormat("dd-MM-yyyy")
	def date = format.parse("14-01-2014")
	println date  // prints "Tue Jan 14 00:00:00 CST 2014"
	*/
	
	//def fec= params.fecha
	///def format = new SimpleDateFormat("yyyy-MM-dd")
	///def d= format.parse(params.fecha)
	
	/*
	def fecha= params.fecha
	def d= Date.parse('yyyy-MM-dd', fecha)
	*/
/*
	def originalFormat = new SimpleDateFormat("yyyy/MM/dd");
	def targetFormat = new SimpleDateFormat("yyyy-MM-dd");
	//def targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	def date = originalFormat.parse(params.fecha);
	def formattedDate = targetFormat.format(date);
	*/
	///
	/* ESTA ES...pasa de un formato al otro
	DateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
	DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = originalFormat.parse("19611015");
	String formattedDate = targetFormat.format(date);
	*/
	
	//para closure:
	def original = new SimpleDateFormat("yyyy/MM/dd")
	def formatoObjetivo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	//def fe= original.parse(params.fecha)
	//def f= formatoObjetivo.format(fe)
	//def formato= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")	//formato en la que està
	///def f= params.fecha //dato que quiero formatear
	//def fecha= formato.parse(f)
	
	//def reservadas= listaReservas.findAllWhere(fecha: fe)
	
	/*
	def format1 = '2014-01-21 00:00:00'
	def format2 = Date.parse("yyyy-MM-dd hh:mm:ss", format1).format("dd/MM/yyyy")
	assert format2 == '01/21/2014'
	*/
	
	///Pasaje de date a String
	//def formatA= "yyyy/MM/dd"
	
	//def formatB= Date.parse('yyyy/MM/dd hh:mm:ss', formatA).format("yyyy/MM/dd")
	//assert formatB == params.fecha
	
	//def otra= listaReservas.findAllWhere(usuario: params.usuario, restaurante: params.restaurante)
/*
	def reservadas = qcreserva.Reserva.executeQuery(
		"from Reserva where restaurante = :searchRestaurante and fecha = :searchFecha", // and hora = :searchHora",
		[searchRestaurante: params.restaurante, searchFecha: params.fecha]) //, searchHora: params.hora])
	*/
	/*def result = Person.createCriteria().list{
		if (firstNameToSearch != null) {
			eq('firstName', firstNameToSearch)
		}
		if (lastNameToSearch != null) {
			eq('lastName', lastNameToSearch)
		}
	} */
	
	/*def result = Reserva.createCriteria().list{
			
	}*/
	//def result = Person.executeQuery(
		//"from Person where firstName = ? and lastName = ?", ['John', 'Doe'])
	
	//def result = Reserva.executeQuery(		//ver query
		//"from Reserva where restaurante = ? and fecha = ? and hora = ?", [params.restaurante, params.fecha, params.hora])
	
	//def reservadas = Reserva.createCriteria().list{	}
	
	
	//def reservadas= listaReservas.findAllByFecha(fecha: fe)
	
	def ver= qcreserva.Reserva.get(params.id)
	//def listadoReservas= Reserva.findByRestaurante(params.restaurante)
	//def reservadas= qcreserva.Reserva.queryList("SELECT * FROM qcreserva.Reserva WHERE fecha = ${params.fecha}")
	 /*
	def criteria= Reserva.createCriteria(){
//	def reservadas = criteria.list(){
		eq('restaurante', params.restaurante)
	and {	eq('fecha', params.fecha)}
	and {	eq('hora', params.hora)}
	}  */
	//def reservadas= queryList(SELECT * FROM qcreserva.Reserva WHERE restaurante LIKE '%params.restaurante%' AND fecha LIKE '%params.fecha%' AND hora LIKE '%params.hora%')
	

//	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	Date fechaReserva = sdf.parse(params.fecha);

//	def reservadas= listaReservas.findAll(  ) //, params.hora )	
	
	[fec: fechaReserva,  ver:ver, listaReservas: listaReservas, suma:suma]//, reservadas: reservadas, d:d]  //f:f,otra:otra,
	
	}



////&&&////


    @Transactional
    def save(Reserva reservaInstance) {
        if (reservaInstance == null) {
            notFound()
            return
        }

        if (reservaInstance.hasErrors()) {
            respond reservaInstance.errors, view:'create'
            return
        }

        reservaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'reserva.label', default: 'Reserva'), reservaInstance.id])
                redirect reservaInstance
            }
            '*' { respond reservaInstance, [status: CREATED] }
        }
    }

    def edit(Reserva reservaInstance) {
        respond reservaInstance
    }

    @Transactional
    def update(Reserva reservaInstance) {
        if (reservaInstance == null) {
            notFound()
            return
        }

        if (reservaInstance.hasErrors()) {
            respond reservaInstance.errors, view:'edit'
            return
        }

        reservaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Reserva.label', default: 'Reserva'), reservaInstance.id])
                redirect reservaInstance
            }
            '*'{ respond reservaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Reserva reservaInstance) {

        if (reservaInstance == null) {
            notFound()
            return
        }

        reservaInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Reserva.label', default: 'Reserva'), reservaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    

}

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'reserva.label', default: 'Reserva'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

}



