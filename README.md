# Projects-java-spring
Demo para utilizar un archivo JSON y búsqueda en el contenido

Es una aplicación que almacena registros en un archivo tipo JSON
con un solo encabezado "name" en un Arreglo de objetos JSON

# Contenido de la aplicación

Se tiene dentro de la aplicación tres opciones para su operación a través de la linea de comandos.

list - muestra los elementos guradados en el archivo
ejemplos: ./applition list

add - agrega un elemento al archivo
ejemplos: ./applition add {“name”:“Juan Antonio Perez”}

fuzzy-search - permite realizar búsqueda de información contenida en el archivo
ejemplos: ./application fuzzy-search {“search”:“Alver”} 

# Algoritmo de búsqueda
Los elementos guardados en el archivo con el formato JSON pueden ser tratados como registros si se utilizan librerías para el manejo de ese tipo de archivos.

Una vez que se carga la información y se tratan como un Arreglo de objetos Json es posible acceder al contenido y convertir ese contenido en una cadena, en donde se pueden utilizar librerías como expresiones regulares para localizar contenidos dentro de cadenas con patrones definidos.

Para el ejemplo donde las coincidencias pueden ser de dos caracteres y según el texto dentro de la expresión de consulta se optó por asignar incrementos de 2 caracteres para la separación de la cadena y considerar la misma cadena como un datos de búsqueda, de esa forma si el usuario intenta buscar algo que coincida con al menos dos caracteres le presentará la coincidencia que se encuentre, la cadena de expresión se descompone dependiendo del incremento hasta agotar los caracteres, formando una expresión regular con los elementos de búsqueda por duplas para este caso, o podrá modificarse para hacer grupos de tres caracteres, se considera una búsqueda donde se desconoce el contenido, se utiliza poca información para aproximar la búsqueda. 

Puede utilizarse con aproximaciones de tres caracteres y se podrá modificar la constante TAMINCREMENTO para hacer el ajuste en la búsqueda.

Existen otras librerías y frameworks como Lucene para realizar una búsqueda tipo fuzzy-search, donde inicialmente es necesario crear los índices binarios correspondientes y después ejecutar los comandos (querys) apropiados para localizar el contenido según la expresión de búsqueda, para el caso del presente ejemplo se decidió por el algoritmo presentado para hacer uso de las expresiones regulares, y por tratarse de un sólo archivo con información.
