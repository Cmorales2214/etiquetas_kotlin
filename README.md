# etiquetas_kotlin
Proyecto de programación en dispositivos móviles.

Nuestro proyecto consistirá en una aplicación para la administración de etiquetas de trabajo y fallas en maquinas a nivel industrial, específicamente dirigidos al personal de producción y mantenimiento, para apoyar la mejora continua de los procesos.

La aplicación contará con las siguientes interfaces:

-Una interfaz para el inicio de sesión, usando como nombre de usuario su numero de carnet, una contraseña y cual es su rol (Operario/Mecánico).

-La aplicación contará con una interfaz en donde los operarios crearan la etiqueta:
Seleccionarán un color de la etiqueta, fecha, número de carnet, nombre de la línea de producción y descripción de la falla.

-Los colores y sus respectivos significados son los siguientes:
*Rojo: Falla o defecto funcional
*Verde: Control de calidad
*Azul: Una mejora a la máquina o al proceso

-Los mecánicos, al ingresar a la cuenta verán las etiquetas que están creadas, podrán seleccionarlas, podrán agregar comentarios sobre el trabajo realizados y cerrar la etiqueta.

-Se podrán realizar llamadas y enviar correos principalmente para reportar fallas en la aplicación.

Nuestro modelo de negocio será "Gratuito" ya que será una aplicación hecha a medida para atacar un problema específico.

Temas
[Y] Autenticación
[Y] Fragmentos, íconos y Firebase Realtime
[Y] Firebase Storage
[Y] Recycled View
[Y] Encriptación
[Y] Internacionalización
[Y] Monetización

[N] Almacenado de datos (SQLite)
[N] Interacción entre apps
[N] Servicios Web