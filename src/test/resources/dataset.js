
db = connect('mongodb://localhost:27017/proyecto');
db.artistas.insertMany([
{
  "nombre": "CURA METAL",
  "genero": "Metal",
  "email": "cura@gmail.com",
  "telefono": "7327003",
  "estado": "DISPONIBLE",
  "_class": "co.edu.uniquindio.proyecto.model.docs.Artista"
},
{
  "nombre": "SONIDO ESCORPIÓN",
  "genero": "Rock",
  "email": "sonidoescorpion@gmail.com",
  "telefono": "7128004",
  "estado": "NO DISPONIBLE",
  "_class": "co.edu.uniquindio.proyecto.model.docs.Artista"
},
{
  "nombre": "RITMO CLÁSICO",
  "genero": "Clásica",
  "email": "ritmoclasico@gmail.com",
  "telefono": "7229015",
  "estado": "DISPONIBLE",
  "_class": "co.edu.uniquindio.proyecto.model.docs.Artista"
},
{
  "nombre": "MÚSICA DE FUEGO",
  "genero": "Hip Hop",
  "email": "fuegohiphop@gmail.com",
  "telefono": "7326002",
  "estado": "NO DISPONIBLE",
  "_class": "co.edu.uniquindio.proyecto.model.docs.Artista"
},
{
  "nombre": "ARMONÍA DIVINA",
  "genero": "Jazz",
  "email": "armoniajazz@gmail.com",
  "telefono": "7452011",
  "estado": "DISPONIBLE",
  "_class": "co.edu.uniquindio.proyecto.model.docs.Artista"
}])

db.carritos.insertMany([
  {
    "fecha": new Date("2024-10-12T10:00:00.000Z"),
    "items": [
      {
        "nombreLocalidad": "VIP",
        "cantidad": 2,
        "idEvento": ObjectId("67085f8c518e810db631197d"),
        "precioUnitario": 150000
      }
    ],
    "idUsuario": ObjectId("670854d36e334608369450c8"),
    "total": 300000,
    "subTotal": 210000,
    "cupon": "Black Friday 2024",
    "_class": "co.edu.uniquindio.proyecto.model.docs.Carrito"
  },
  {
    "fecha": new Date("2024-11-15T12:30:00.000Z"),
    "items": [
      {
        "nombreLocalidad": "General",
        "cantidad": 3,
        "idEvento": ObjectId("67085f8c518e810db631197d"),
        "precioUnitario": 50000
      }
    ],
    "idUsuario": ObjectId("67086142f50d464f6fc481d3"),
    "total": 150000,
    "subTotal": 112500,
    "cupon": "Cyber Monday 2024",
    "_class": "co.edu.uniquindio.proyecto.model.docs.Carrito"
  },
  {
    "fecha": new Date("2024-12-01T15:45:00.000Z"),
    "items": [
      {
        "nombreLocalidad": "Preferencial",
        "cantidad": 4,
        "idEvento": ObjectId("67085f8c518e810db631197d"),
        "precioUnitario": 85000
      }
    ],
    "idUsuario": ObjectId("67086142f50d464f6fc481d4"),
    "total": 340000,
    "subTotal": 255000,
    "cupon": "New Year 2025",
    "_class": "co.edu.uniquindio.proyecto.model.docs.Carrito"
  },
  {
    "fecha": new Date("2025-01-10T14:20:00.000Z"),
    "items": [
      {
        "nombreLocalidad": "Platea",
        "cantidad": 6,
        "idEvento": ObjectId("67085f8c518e810db631197d"),
        "precioUnitario": 99000
      }
    ],
    "idUsuario": ObjectId("67086142f50d464f6fc481d5"),
    "total": 594000,
    "subTotal": 415800,
    "cupon": "San Valentin 2025",
    "_class": "co.edu.uniquindio.proyecto.model.docs.Carrito"
  }
])

db.cuentas.insertMany([
  {
    "email": "cliente1@gmail.com",
    "tipoUsuario": "CLIENTE",
    "estadoCuenta": "ACTIVO",
    "codigoValidacionPassword": {
      "codigo": "123",
      "fechaCreacion": new Date("2024-10-11T08:30:00.000Z"),
      "fechaExpiracion": new Date("2024-10-11T08:45:00.000Z")
    },
    "usuario": {
      "cedula": "1094851",
      "nombreCompleto": "Carlos Martínez",
      "direccion": "Av. 9 #12-34",
      "telefono": "3124567890"
    },
    "password": "cliente1",
    "codigoValidacionRegistro": {
      "codigo": "123",
      "fechaCreacion": new Date("2024-10-11T08:30:00.000Z"),
      "fechaExpiracion": new Date("2024-10-11T08:45:00.000Z")
    },
    "_class": "co.edu.uniquindio.proyecto.model.docs.Cuenta"
  },
  {
    "email": "cliente2@gmail.com",
    "tipoUsuario": "CLIENTE",
    "estadoCuenta": "ACTIVO",
    "codigoValidacionPassword": {
      "codigo": "456",
      "fechaCreacion": new Date("2024-10-12T10:00:00.000Z"),
      "fechaExpiracion": new Date("2024-10-12T10:15:00.000Z")
    },
    "usuario": {
      "cedula": "1094852",
      "nombreCompleto": "María Gómez",
      "direccion": "Calle 5 #4-67",
      "telefono": "3139876543"
    },
    "password": "cliente2",
    "codigoValidacionRegistro": {
      "codigo": "456",
      "fechaCreacion": new Date("2024-10-12T10:00:00.000Z"),
      "fechaExpiracion": new Date("2024-10-12T10:15:00.000Z")
    },
    "_class": "co.edu.uniquindio.proyecto.model.docs.Cuenta"
  },
  {
    "email": "cliente3@gmail.com",
    "tipoUsuario": "CLIENTE",
    "estadoCuenta": "ACTIVO",
    "codigoValidacionPassword": {
      "codigo": "789",
      "fechaCreacion": new Date("2024-10-13T12:00:00.000Z"),
      "fechaExpiracion": new Date("2024-10-13T12:15:00.000Z")
    },
    "usuario": {
      "cedula": "1094853",
      "nombreCompleto": "Luis Pérez",
      "direccion": "Cra 7 #23-45",
      "telefono": "3109871234"
    },
    "password": "cliente3",
    "codigoValidacionRegistro": {
      "codigo": "789",
      "fechaCreacion": new Date("2024-10-13T12:00:00.000Z"),
      "fechaExpiracion": new Date("2024-10-13T12:15:00.000Z")
    },
    "_class": "co.edu.uniquindio.proyecto.model.docs.Cuenta"
  },
  {
    "email": "cliente4@gmail.com",
    "tipoUsuario": "CLIENTE",
    "estadoCuenta": "ACTIVO",
    "codigoValidacionPassword": {
      "codigo": "321",
      "fechaCreacion": new Date("2024-10-14T14:30:00.000Z"),
      "fechaExpiracion": new Date("2024-10-14T14:45:00.000Z")
    },
    "usuario": {
      "cedula": "1094854",
      "nombreCompleto": "Ana Rojas",
      "direccion": "Cl 10 #8-89",
      "telefono": "3151234567"
    },
    "password": "cliente4",
    "codigoValidacionRegistro": {
      "codigo": "321",
      "fechaCreacion": new Date("2024-10-14T14:30:00.000Z"),
      "fechaExpiracion": new Date("2024-10-14T14:45:00.000Z")
    },
    "_class": "co.edu.uniquindio.proyecto.model.docs.Cuenta"
  }
])

db.cupones.insertMany([
  {
    "codigo": "Black Friday 2024",
    "descuento": 0.5,
    "fechaVencimiento": new Date("2024-12-01T23:59:00.000Z"),
    "tipoCupon": "MULTIPLE",
    "estado": "ACTIVO",
    "esEspecial": false,
    "limiteUso": 100,
    "cantidadUsados": 50,
    "_class": "co.edu.uniquindio.proyecto.model.docs.Cupon"
  },
  {
    "codigo": "New Year 2025",
    "descuento": 0.4,
    "fechaVencimiento": new Date("2025-01-05T23:59:00.000Z"),
    "tipoCupon": "UNICO",
    "estado": "ACTIVO",
    "esEspecial": true,
    "limiteUso": 1,
    "cantidadUsados": 0,
    "_class": "co.edu.uniquindio.proyecto.model.docs.Cupon"
  },
  {
    "codigo": "Cyber Monday 2024",
    "descuento": 0.25,
    "fechaVencimiento": new Date("2024-12-02T23:59:00.000Z"),
    "tipoCupon": "MULTIPLE",
    "estado": "ACTIVO",
    "esEspecial": false,
    "limiteUso": 200,
    "cantidadUsados": 100,
    "_class": "co.edu.uniquindio.proyecto.model.docs.Cupon"
  },
  {
    "codigo": "San Valentin 2025",
    "descuento": 0.15,
    "fechaVencimiento": new Date("2025-02-14T23:59:00.000Z"),
    "tipoCupon": "UNICO",
    "estado": "ACTIVO",
    "esEspecial": true,
    "limiteUso": 1,
    "cantidadUsados": 0,
    "_class": "co.edu.uniquindio.proyecto.model.docs.Cupon"
  }
])

db.deseos.insertMany([
  {
    "cuenta": ObjectId("670854d36e334608369450c9"),
    "evento": ObjectId("67085b8a76fafb320689e150"),
    "recibeInfo": true,
    "_class": "co.edu.uniquindio.proyecto.model.docs.Deseo"
  },
  {
    "cuenta": ObjectId("670854d36e334608369450ca"),
    "evento": ObjectId("67085b8a76fafb320689e151"),
    "recibeInfo": false,
    "_class": "co.edu.uniquindio.proyecto.model.docs.Deseo"
  },
  {
    "cuenta": ObjectId("670854d36e334608369450cb"),
    "evento": ObjectId("67085b8a76fafb320689e152"),
    "recibeInfo": true,
    "_class": "co.edu.uniquindio.proyecto.model.docs.Deseo"
  },
  {
    "cuenta": ObjectId("670854d36e334608369450cc"),
    "evento": ObjectId("67085b8a76fafb320689e153"),
    "recibeInfo": false,
    "_class": "co.edu.uniquindio.proyecto.model.docs.Deseo"
  }
])

db.eventos.insertMany([
  {
    "nombre": "Festival del Jazz",
    "direccion": "Av. Principal, Teatro de la Ciudad",
    "ciudad": "Bogotá",
    "descripcion": "Un evento dedicado al jazz contemporáneo",
    "tipoEvento": "CONCIERTO",
    "imagen": "imagen2.com",
    "fecha": new Date("2024-11-20T18:30:00.000Z"),
    "localidades": [
      {
        "nombre": "General",
        "precio": 15000,
        "capacidad": 800,
        "entradasVendidas": 600,
        "disponibilidad": true,
        "descripcion": "Área general con asientos numerados",
        "imagen": "imagenLocal3.com"
      },
      {
        "nombre": "VIP",
        "precio": 30000,
        "capacidad": 200,
        "entradasVendidas": 150,
        "disponibilidad": true,
        "descripcion": "Zona exclusiva frente al escenario",
        "imagen": "imagenLocal4.com"
      }
    ],
    "estadoEvento": "ACTIVO",
    "artista": ObjectId("67085d83c7db7bf747b1b119"),
	
    "_class" :"co.edu.uniquindio.proyecto.model.docs.Evento"
  },
  {
    "nombre": "Fiesta Electrónica",
    "direccion": "Playa Blanca, Cartagena",
    "ciudad": "Cartagena",
    "descripcion": "Un evento de música electrónica a la orilla del mar",
    "tipoEvento": "FESTIVAL",
    "imagen": "imagen3.com",
    "fecha": new Date("2025-01-15T23:59:00.000Z"),
    "localidades": [
      {
        "nombre": "Zona General",
        "precio": 20000,
        "capacidad": 1000,
        "entradasVendidas": 800,
        "disponibilidad": true,
        "descripcion": "Espacio al aire libre para disfrutar la música",
        "imagen": "imagenLocal5.com"
      },
      {
        "nombre": "Zona VIP",
        "precio": 50000,
        "capacidad": 300,
        "entradasVendidas": 250,
        "disponibilidad": true,
        "descripcion": "Zona exclusiva con servicios adicionales",
        "imagen": "imagenLocal6.com"
      }
    ],
    "estadoEvento": "ACTIVO",
    "artista": ObjectId("67085d83c7db7bf747b1b11a"),
    "_class" :"co.edu.uniquindio.proyecto.model.docs.Evento"
  },
  {
    "nombre": "Cumbia Festival",
    "direccion": "Parque Simón Bolívar",
    "ciudad": "Bogotá",
    "descripcion": "Festival al aire libre con los mejores exponentes de la cumbia",
    "tipoEvento": "FESTIVAL",
    "imagen": "imagen4.com",
    "fecha": new Date("2025-03-05T14:00:00.000Z"),
    "localidades": [
      {
        "nombre": "General",
        "precio": 18000,
        "capacidad": 700,
        "entradasVendidas": 500,
        "disponibilidad": true,
        "descripcion": "Espacio amplio con buena visibilidad",
        "imagen": "imagenLocal7.com"
      },
      {
        "nombre": "Palco VIP",
        "precio": 40000,
        "capacidad": 200,
        "entradasVendidas": 180,
        "disponibilidad": true,
        "descripcion": "Zona elevada con asientos más cómodos",
        "imagen": "imagenLocal8.com"
      }
    ],
    "estadoEvento": "ACTIVO",
    "artista": ObjectId("67085d83c7db7bf747b1b11b"),
    "_class" :"co.edu.uniquindio.proyecto.model.docs.Evento"
  },
  {
    "nombre": "Sonidos del Pacífico",
    "direccion": "Auditorio Nacional",
    "ciudad": "Cali",
    "descripcion": "Evento dedicado a la música tradicional del Pacífico colombiano",
    "tipoEvento": "CONCIERTO",
    "imagen": "imagen5.com",
    "fecha": new Date("2025-06-10T20:00:00.000Z"),
    "localidades": [
      {
        "nombre": "General",
        "precio": 12000,
        "capacidad": 500,
        "entradasVendidas": 400,
        "disponibilidad": true,
        "descripcion": "Asientos en la parte baja del auditorio",
        "imagen": "imagenLocal9.com"
      },
      {
        "nombre": "Preferencial",
        "precio": 25000,
        "capacidad": 300,
        "entradasVendidas": 270,
        "disponibilidad": true,
        "descripcion": "Asientos más cercanos al escenario",
        "imagen": "imagenLocal10.com"
      }
    ],
    "estadoEvento": "ACTIVO",
    "artista": ObjectId("67085d83c7db7bf747b1b11c"),
    "_class" :"co.edu.uniquindio.proyecto.model.docs.Evento"
  }
])

db.ordenes_compra.insertMany([
  {
    "codigo": "fgh67sdf76g8sa8f76g",
    "cliente": ObjectId("67086142f50d464f6fc481d3"),
    "items": [
      {
        "nombreLocalidad": "VIP",
        "cantidad": 2,
        "idEvento": ObjectId("67085f8c518e810db631197c"),
        "precioUnitario": 150000
      }
    ],
    "subTotal": 0,
    "total": 300000,
    "fecha": new Date("2024-11-05T14:45:00.000Z"),
    "pago": {
      "codigoAutorizacion": "12345",
      "metodo": "tarjeta",
      "estado": "pagado",
      "valorTransaccion": 300000,
      "fecha": new Date("2024-11-05T14:45:00.000Z")
    },
    "_class": "co.edu.uniquindio.proyecto.model.docs.OrdenCompra"
  },
  {
    "codigo": "kjh87wer76jk89asd9",
    "cliente": ObjectId("67086142f50d464f6fc481d4"),
    "items": [
      {
        "nombreLocalidad": "General",
        "cantidad": 3,
        "idEvento": ObjectId("67085f8c518e810db631197d"),
        "precioUnitario": 50000
      }
    ],
    "subTotal": 0,
    "total": 150000,
    "fecha": new Date("2024-12-01T12:00:00.000Z"),
    "pago": {
      "codigoAutorizacion": "23456",
      "metodo": "tarjeta",
      "estado": "pagado",
      "valorTransaccion": 150000,
      "fecha": new Date("2024-12-01T12:00:00.000Z")
    },
    "_class": "co.edu.uniquindio.proyecto.model.docs.OrdenCompra"
  },
  {
    "codigo": "lkh87wrie75jk7hg6ds",
    "cliente": ObjectId("67086142f50d464f6fc481d5"),
    "items": [
      {
        "nombreLocalidad": "Preferencial",
        "cantidad": 4,
        "idEvento": ObjectId("67085f8c518e810db631197f"),
        "precioUnitario": 85000
      }
    ],
    "subTotal": 0,
    "total": 340000,
    "fecha": new Date("2025-01-15T18:30:00.000Z"),
    "pago": {
      "codigoAutorizacion": "34567",
      "metodo": "tarjeta",
      "estado": "pagado",
      "valorTransaccion": 340000,
      "fecha": new Date("2025-01-15T18:30:00.000Z")
    },
    "_class": "co.edu.uniquindio.proyecto.model.docs.OrdenCompra"
  },
  {
    "codigo": "zxc98uhg76uj90fdgds",
    "cliente": ObjectId("67086142f50d464f6fc481d6"),
    "items": [
      {
        "nombreLocalidad": "Platea",
        "cantidad": 6,
        "idEvento": ObjectId("67085f8c518e810db631197f"),
        "precioUnitario": 99000
      }
    ],
    "subTotal": 0,
    "total": 594000,
    "fecha": new Date("2025-02-10T09:45:00.000Z"),
    "pago": {
      "codigoAutorizacion": "45678",
      "metodo": "efectivo",
      "estado": "pagado",
      "valorTransaccion": 594000,
      "fecha": new Date("2025-02-10T09:45:00.000Z")
    },
    "_class": "co.edu.uniquindio.proyecto.model.docs.OrdenCompra"
  }
])

db.valoraciones.insertMany([
  {
    "evento": ObjectId("67085b8a76fafb320689e149"),
    "usuario": ObjectId("670854d36e334608369450c8"),
    "puntuacion": 4,
    "comentario": "El ambiente estuvo genial, aunque la fila fue larga.",
    "fecha": new Date("2024-11-12T18:45:00.000Z"),
    "_class": "co.edu.uniquindio.proyecto.model.docs.Valoracion"
  },
  {
    "evento": ObjectId("67085f8c518e810db631197c"),
    "usuario": ObjectId("67086142f50d464f6fc481d3"),
    "puntuacion": 3,
    "comentario": "La música fue excelente, pero el lugar estaba muy lleno.",
    "fecha": new Date("2024-11-20T20:15:00.000Z"),
    "_class": "co.edu.uniquindio.proyecto.model.docs.Valoracion"
  },
  {
    "evento": ObjectId("67085f8c518e810db631197d"),
    "usuario": ObjectId("67086142f50d464f6fc481d5"),
    "puntuacion": 5,
    "comentario": "Todo estuvo perfecto, desde el sonido hasta la organización.",
    "fecha": new Date("2024-12-05T21:30:00.000Z"),
    "_class": "co.edu.uniquindio.proyecto.model.docs.Valoracion"
  },
  {
    "evento": ObjectId("67085f8c518e810db631197f"),
    "usuario": ObjectId("67086142f50d464f6fc481d6"),
    "puntuacion": 2,
    "comentario": "El evento estuvo bien, pero la comida estaba muy cara.",
    "fecha": new Date("2024-12-10T19:00:00.000Z"),
    "_class": "co.edu.uniquindio.proyecto.model.docs.Valoracion"
  }
])
