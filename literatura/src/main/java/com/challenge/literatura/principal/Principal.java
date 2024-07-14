package com.challenge.literatura.principal;

import com.challenge.literatura.model.*;
import com.challenge.literatura.repository.IrepositoryLibrary;
import com.challenge.literatura.services.apiConmsumo;
import com.challenge.literatura.services.convierteDatos;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private apiConmsumo apiConsumo = new apiConmsumo ();
    private convierteDatos converteDAtos = new convierteDatos();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private static final String URL_BUSQUEDA = "?search=";
    private final IrepositoryLibrary repositorio;
    public Principal(IrepositoryLibrary repositorio) {
        this.repositorio = repositorio;
    }


    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Buscar libro por titulo 
                    2 - mostrar libros registrados
                    3 - Mostrar Autores registrados
                    4 - Buscar Autores en un determinado año
                    5 - Mostrar libros por idoma
                    6 - Mostrar top 10 de los mejores libros
                    7 - Mostrar estaditicas generales
                    8 - Buscar autor por nombre 
                                        
                                  
                    0 - Salir
                    """;
            while (option != 0) {
                out.println(menu);
                option = scanner.nextInt();
                scanner.nextLine();
                try{
                    switch (option){
                        case 1:
                            busquedaLibroPorTitulo();
                            break;
                        case 2:
                            mostrarLibrosGuardados();
                            break;
                        case 3: 
                            mostrarAutoresRegistrados();
                            break;
                        case 4:
                            buscarAutorPorAno();
                            break;
                        case 5:
                            mostarLibrosPorIdioma();
                            break;
                        case 6:
                            top10();
                            break;
                        case  7:
                            estadisticas();
                            break;
                        case 8:
                            buscarAutorPorNombre();
                            break;
                        case 0:
                            break;
                        default:
                            out.println("opcion invalida");


                    }
                }catch (NumberFormatException e){
                    out.println("OPCION INVÁLIDA");

                }



        }
    }
}

    private void buscarAutorPorNombre() {
    }

    private void estadisticas() {
       var json = apiConsumo.gettingData(URL_BASE);
       var informacion = converteDAtos.gettingData(json, datos.class);
        IntSummaryStatistics est= informacion.libross().stream()
                .filter(e->e.downloadCount()>0)
                .collect(Collectors.summarizingInt(datosLibros::downloadCount));
        Integer media = (int)est.getAverage();
        out.println(
                "\n---------ESTADISTICA---------"+
                        "\n LA MEDIA DE DESCARGAS ES: " + est.getAverage() +
                        "\n LA CANTIDAD MAXIMA DE DESCARGAS ES: " + est.getMax() +
                        "\n LA CANTIDAD MINIMA DE DESCARGAS ES: " + est.getMin() +
                        "\nEL RECOED EVALUADO ES: " + est.getCount()

        );

    }

    private void top10() {
        var json = apiConsumo.gettingData(URL_BASE);
        var informacion = converteDAtos.gettingData(json, datos.class);
        informacion.libross().stream()
                .sorted(Comparator.comparing(datosLibros::downloadCount).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

    }

    private void mostarLibrosPorIdioma() {
        var mapeoLenguaje = """
                en-INGLES
                es-ESPAÑOL
                it-ITALIANO
                pt-PORTUGUES""";
        out.println(mapeoLenguaje);
        out.println("SELECCIONA EL IDIOMA A BUSCAR: ");
        var seleccion = scanner.nextLine().toLowerCase();
        if(seleccion.equalsIgnoreCase("es")
                || seleccion.equalsIgnoreCase("en")
                || seleccion.equalsIgnoreCase("it")
                || seleccion.equalsIgnoreCase("fr")
                || seleccion.equalsIgnoreCase("pt")){
            lenguajes lenguaje = lenguajes.fromString(seleccion);
            List<libros> Libros = repositorio.findBookByLanguage(lenguaje);
            if(Libros.isEmpty()){
                out.println("NO SE PUDO ENCONTRAR EL LIBRO");
            }else {
                Libros.forEach(t-> out.println(
                        "------ LIBRO ------" +
                                "\nTITULO: " + t.getTitulo() +
                                "\nAUTOR: " + t.getAutor().getNombre() +
                                "\nIDIOMA: " + t.getLenguaje().getIdioma() +
                                "\nNUMERO DE DESCARGAS: " + t.getDownloadCount()));
            }
        }else {
            out.println("ERROR, INDICA UNA OPCION VALIDA");
        }


    }

    private void buscarAutorPorAno() {
        out.println("INGRESE EL AÑO A BUSCAR: ");
        try {
            var anoAutor = Integer.valueOf(scanner.nextLine());
            List<autores> autoress = repositorio.getfechaDeFallecimiento(anoAutor);
            if (!autoress.isEmpty()) {
                autoress.forEach(a -> out.println(
                        "\nAUTOR: " + a.getNombre() +
                                "\nFECHA DE NACIMIENTO: " + a.getFechaDeNacimiento() +
                                "\nFECHA DE FALLECIMIENTO: " + a.getFechaDeFallecimiento() +
                                "\n LIBROS: " + a.getLibros().stream()
                                .map(t -> t.getTitulo()).collect(Collectors.toList())));
            }else {
                out.println("AUTOR NO ENCONTRADO POR LA FECHA "+anoAutor);}
        }catch (NumberFormatException e){
            out.println("POR FAVOR INGRESE UNA FECHA VALIDA "+e.getMessage());
        }
    }

    private void mostrarAutoresRegistrados() {
        List<autores> Autores = repositorio.findAll();
        Autores.forEach(a-> out.println("DATOS DEL AUTOR\n" +
                "NOMBRE AUTOR: "+a.getNombre()+
                "\n FECHA DE NACIMIENTO: "+a.getFechaDeNacimiento()+
                "\n FECHA DE FALLECIMIENTO: "+a.getFechaDeFallecimiento()+
                "\n LIBROS: "+a.getLibros().stream()
                .map(t->t.getTitulo()).collect(Collectors.toList())));
        out.println("                                                  ");}


    private void busquedaLibroPorTitulo() {
        out.println("Ingresa el nombre del libro a buscar: ");
        var tituloLibroUsuario = scanner.nextLine().replace(" ", "+");
        var json = apiConsumo.gettingData(URL_BASE+URL_BUSQUEDA+tituloLibroUsuario);
        var DatosLibro = converteDAtos.gettingData(json, datos.class);
        Optional<datosLibros> busquedaLibro = DatosLibro.libross().stream()
                .findFirst();
        if (busquedaLibro.isPresent()) {
            out.println("***LIBRO ENCONTRADO***\n+EL LIBRO ES:\n"
            +"TITULO: "+busquedaLibro.get().titulo()
            +"\n AUTOR: "+busquedaLibro.get().autores().stream().map(e->e.nombre()).collect(Collectors.joining())
            +"\n IDIOMA: "+busquedaLibro.get().lenguajes().stream().collect(Collectors.joining())
            +"\n CANTIDAD DE DESACARGAS: "+busquedaLibro.get().downloadCount());
            try{
                List<libros> Libros;
                Libros = busquedaLibro.stream()
                        .map(e-> new libros(e)).collect(Collectors.toList());
                autores autoresAPI = busquedaLibro.stream()
                        .flatMap(l-> l.autores().stream()
                                .map(a->new autores(a)))
                        .collect(Collectors.toList()).stream().findFirst().get();
                Optional<autores> opcionalAutores = repositorio.findAuthorByNameContaining(busquedaLibro.get().autores().stream()
                        .map(n->n.nombre())
                        .collect(Collectors.joining()));
                Optional<libros> opcionalLibros = repositorio.getBookContainsEqualsIgnoreCaseTitle(tituloLibroUsuario);
                if(opcionalLibros.isPresent()){
                    out.println("El libro ya ha suido guardado");
                }else{
                    autores autor;
                    if(opcionalAutores.isPresent()){
                        autor = opcionalAutores.get();
                        out.println("El autor ya está registrado en la base de datos");
                    }else{
                        autor = autoresAPI;
                        repositorio.save(autor);
                    }
                    autor.setLibros(Libros);
                    repositorio.save(autor);
                }
             }catch (Exception e){
                out.println(e.getMessage());
            }
        }else {
            out.println("***LIBRO NO ENCONTRADO***");}}
    private void mostrarLibrosGuardados() {
        List<libros> llibros = repositorio.findBooksByAuthor();
        out.println("LOS LIBROS BUSCADOS SON: ");
        llibros.forEach(l-> out.println("TITULO: "+l.getTitulo()
        +"\n AUTOR: "+l.getAutor().getNombre()
        +"\n IDIOMA: "+l.getLenguaje().getIdioma()
        +"\n NUMERO DE DESCARGAS: "+l.getDownloadCount()));}
    

    }
