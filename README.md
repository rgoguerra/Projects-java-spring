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
Los elementos guardados en el archivo con el formato JSON pueden ser tratados como registros si se utilizan librerias para el manejo de ese tipo de archivos.

Una vez que se carga la información y se tratan como un Arreglo de objetos Json es posible acceder al contenido y convertir ese contenido en una cadena, en donde se pueden utilizar librerias como expresiones regulares para localizar contenidos dentro de cadenas con patrones definidos.

Para el ejemplo donde las coincidencias pueden ser de dos caracteres y según el texto dentro de la expresión de consulta se optó por asignar un incrementos de 2 caracteres para la separación de la cadena y considerar la misma cadena como un datos de búsqueda, de sesa forma si el usuario intenta buscar algo que coincida con almenos dos caracteres le presentará la coinciedncia que se encuentre.

Puede utilizarse con aproximaciones de tres caracteres y se podrá modificar la constante TAMINCREMENTO para hacer el ajuste el la búsqueda.

Existen otras librerias y frameworks como Lucene para realizar una búsqueda tipo fuzzy-search, donde inicialmente es necesario crear los indices binarios correspondientes y despues ejecutar los comandos (querys) apropiados para localizar el contenido según la expresión de busqueda, para el caso del presente ejemplo se decidio por el algoritmo presentado para hacer uso de las expresiones regulares, y por tratarse de un sólo archivo con información. 
