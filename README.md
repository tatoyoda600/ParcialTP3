# ParcialTP3
 
## Integrantes

- Guido Naselli (@guidonaselli)

- Malena Emilse Gonzalez (@malenagonzalez)

- Santiago Alcacer-Mackinlay (@tatoyoda600)

- Fernando Maluf Caruso (@maluf1979)

- Juan Pablo Furgiuele (@JuanFurgi)

## Preguntas

1 - En el caso que se pida extender la app para otros tipos de mascotas, por ejemplo gatos, ¿la app es flexible? ¿Qué cambios realizarían?

Sí, se puede abstraer las entidades a una padre llamada Mascota la cual tenga propiedades comunes a todas las mascotas, para luego crear subclases específicas como "Perro" y "Gato" que hereden de esa clase base. También es posible agregar un tipo de mascota a la entidad padre para poder realizar filtros, y así el usuario busque según sus preferencias. 

 
2 - ¿Qué tipo de arquitectura usaron y por qué? ¿Podría mejorarla?

Usamos la arquitectura MVVM ya que nos permite separar de manera efectiva la lógica de la interfaz de usuario de la lógica de negocio, dejando como resultado un código más modular y mantenible. Otra razón por la que utilizamos MVVM es porque es compatible con las bibliotecas de Android Jetpack, como LiveData y ViewModel, lo que nos facilitó la implementación de patrones de observación y gestión del ciclo de vida.

3 - ¿Tuvieron fugas o retención de memoria? ¿Qué consideraciones tuvieron en cuenta?

No, no tuvimos ninguna de las 2. En ningun momento alocamos memoria manualmente, los singletons que usamos los implementamos bien para que no se puedan crear mas de 1, y casi no trabajamos con listas, fuera de conseguir datos de la base de datos, como para tener una lista de tanta longitud. Teniendo en cuenta la forma que decidimos hacer la aplicacion, no teniamos gran riesgo de que ocurran.

4 -¿Qué mejoras detectan que podrían realizarle a la app?

Ampliación de las características de registro: El proceso de registro es bastante básico y se limita a la recopilación de información mínima. Para mejorar la experiencia del usuario, podríamos permitir al user proporcionar información adicional como contraseña y mail tanto para tener mejor seguridad sobre sus datos como el facil acceso a la recuperación de los mismos en caso de que se necesite. 

Mejoras en la interfaz de usuario: Mejorar la usabilidad y la estética de la interfaz de usuario
