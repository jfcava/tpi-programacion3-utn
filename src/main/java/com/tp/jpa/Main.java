package com.tp.jpa;

import com.tp.jpa.DTOs.ItemTemporal;
import com.tp.jpa.model.*;
import com.tp.jpa.model.enums.Estado;
import com.tp.jpa.model.enums.FormaPago;
import com.tp.jpa.model.enums.Rol;
import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.repository.PedidoRepository;
import com.tp.jpa.repository.ProductoRepository;
import com.tp.jpa.repository.UsuarioRepository;
import com.tp.jpa.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoriaRepository categoriaRepo = new CategoriaRepository();
    private static final ProductoRepository productoRepo = new ProductoRepository();
    private static final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private static final PedidoRepository pedidoRepo = new PedidoRepository();

    public static void main(String[] args) {

        //Filtrar el nivel de logs que la librería de Hibernate imprime en la consola
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        boolean ejecutando = true;

        System.out.println("#############################################");
        System.out.println("====    SISTEMA DE GESTIÓN DE PEDIDOS    ====");
        System.out.println("#############################################");

        while(ejecutando){
            mostrarMenuPrincipal();
            int opc = leerEntero("Seleccione una opción: ");

            switch (opc){
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuUsuarios();
                case 4 -> menuPedidos();
                case 5 -> menuReportes();
                case 0 -> ejecutando = false;
                default -> System.out.println("  [!] Opción inválida. Intente de nuevo.");
            }
        }
        System.out.println("\n  Cerrando conexión con la base de datos...");
        JPAUtil.close();
        System.out.println("  ¡Hasta luego!");
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n========================");
        System.out.println("     MENÚ PRINCIPAL     ");
        System.out.println("========================");
        System.out.println(" 1. Gestionar Categorías");
        System.out.println(" 2. Gestionar Productos");
        System.out.println(" 3. Gestionar Usuarios");
        System.out.println(" 4. Gestionar Pedidos");
        System.out.println(" 5. Reportes");
        System.out.println(" 0. Salir");
        System.out.println("------------------------");
    }

    // Metodos Menu Principal
    public static void menuCategorias(){
        boolean ejecutando = true;
        while(ejecutando){
            System.out.println("\n  -- Submenu: Categorías ------");
            System.out.println("  1. Alta de categoría");
            System.out.println("  2. Modificar categoría");
            System.out.println("  3. Baja lógica de categoría");
            System.out.println("  4. Listar categorías activas");
            System.out.println("  0. Volver al menú principal");
            System.out.println("  -------------------------------");
            int opc = leerEntero("Seleccione una opción: ");

            switch (opc){
                case 1 -> altaCategoria();
                case 2 -> modificarCategoria();
                case 3 -> bajaCategoria();
                case 4 -> listarCategorias();
                case 0 -> ejecutando = false;
                default -> System.out.println("  [!] Opción inválida. Intente de nuevo.");
            }
        }
    }

    public static void menuProductos(){
        boolean ejecutando = true;
        while(ejecutando){
            System.out.println("\n  -- Submenu: Productos ------");
            System.out.println("  1. Alta de producto");
            System.out.println("  2. Modificar producto");
            System.out.println("  3. Baja lógica de producto");
            System.out.println("  4. Listar productos activos");
            System.out.println("  0. Volver al menú principal");
            System.out.println("  -------------------------------");
            int opc = leerEntero("Seleccione una opción: ");

            switch (opc){
                case 1 -> altaProducto();
                case 2 -> modificarProducto();
                case 3 -> bajaProducto();
                case 4 -> listarProductos();
                case 0 -> ejecutando = false;
                default -> System.out.println("  [!] Opción inválida. Intente de nuevo.");
            }
        }
    }

    public static void menuUsuarios(){
        boolean ejecutando = true;
        while(ejecutando){
            System.out.println("\n  -- Submenu: Usuarios ------");
            System.out.println("  1. Alta de usuario");
            System.out.println("  2. Modificar usuario");
            System.out.println("  3. Baja lógica de usuario");
            System.out.println("  4. Listar usuarios activos");
            System.out.println("  5. Buscar usuario por mail");
            System.out.println("  0. Volver al menú principal");
            System.out.println("  -------------------------------");
            int opc = leerEntero("Seleccione una opción: ");

            switch (opc){
                case 1 -> altaUsuario();
                case 2 -> modificarUsuario();
                case 3 -> bajaUsuario();
                case 4 -> listarUsuariosActivos();
                case 5 -> buscarUsuarioPorMail();
                case 0 -> ejecutando = false;
                default -> System.out.println("  [!] Opción inválida. Intente de nuevo.");
            }
        }
    }

    public static void menuPedidos(){
        boolean ejecutando = true;
        while(ejecutando){
            System.out.println("\n  -- Submenu: Pedidos ------");
            System.out.println("  1. Alta de pedido");
            System.out.println("  2. Cambiar estado de pedido");
            System.out.println("  3. Baja lógica de pedido");
            System.out.println("  4. Listar pedidos activos");
            System.out.println("  5. Listar pedidos por usuario");
            System.out.println("  6. Listar pedidos por estado");
            System.out.println("  0. Volver al menú principal");
            System.out.println("  -------------------------------");
            int opc = leerEntero("Seleccione una opción: ");

            switch (opc){
                case 1 -> altaPedido();
                case 2 -> cambiarEstadoPedido();
                case 3 -> bajaPedido();
                case 4 -> listarPedidosActivos();
                case 5 -> listarPedidosPorUsuario();
                case 6 -> listarPedidosPorEstado();
                case 0 -> ejecutando = false;
                default -> System.out.println("  [!] Opción inválida. Intente de nuevo.");
            }
        }
    }

    public static void menuReportes(){
        boolean ejecutando = true;
        while(ejecutando){
            System.out.println("\n  -- Submenu: Reportes ------");
            System.out.println("  1. Productos por categoría");
            System.out.println("  2. Pedidos por usuario");
            System.out.println("  3. Pedidos por estado");
            System.out.println("  4. Total facturado");
            System.out.println("  0. Volver al menú principal");
            System.out.println("  -------------------------------");
            int opc = leerEntero("Seleccione una opción: ");

            switch (opc){
                case 1 -> mostrarProductosPorCategoria();
                case 2 -> mostrarPedidosPorUsuario();
                case 3 -> listarPedidosPorEstado();
                case 4 -> mostrarTotalFacturado();
                case 0 -> ejecutando = false;
                default -> System.out.println("  [!] Opción inválida. Intente de nuevo.");
            }
        }
    }

    // Metodos Categorias
    public static void altaCategoria(){
        System.out.println("\n[ALTA DE CATEGORÍA]");
        System.out.print("Ingrese el nombre de la categoría (Obligatorio): ");
        String nombre = scanner.nextLine().trim();

        if(nombre.isEmpty()){
            System.out.println("  [!] ERROR: El nombre de la categoría no puede estar vacío.");
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.print("Ingrese descripción (Opcional): ");
        String descripcion = scanner.nextLine().trim();

        Categoria nuevaCategoria = Categoria.builder()
                .nombre(nombre)
                .descripcion(descripcion.isEmpty() ? null : descripcion)
                .build();

        try {
            Categoria catPersistida = categoriaRepo.guardar(nuevaCategoria);
            System.out.println("\n  -- Categoría creada con éxito [Id: " + catPersistida.getId() + "]");
        } catch (Exception e) {
            System.out.println("\n  [!] ERROR: No se pudo guardar la categoría.");
            System.out.println("  Mas Info: " + e.getMessage());
        }

        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void modificarCategoria(){
        if(!listarCategoriasActivas()){
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }
        Long id = (long)leerEntero("Ingrese el ID de la categoría a modificar: ");
        Optional<Categoria> optCategoria = categoriaRepo.buscarPorId(id);

        if(optCategoria.isEmpty() || optCategoria.get().isEliminado()){
            System.out.println("\n  [!] No se encontró una categoría activa con el ID " + id);
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Categoria cat = optCategoria.get();
        System.out.println("\nValores actuales -> [Nombre: " + cat.getNombre() + " | Descripción: " + cat.getDescripcion() + "]");
        System.out.println("(Deje el campo en blanco y presione ENTER si no desea modificar el valor)");

        System.out.print("Nuevo Nombre: ");
        String nuevoNombre = scanner.nextLine().trim();
        if(!nuevoNombre.isEmpty()){
            cat.setNombre(nuevoNombre);
        }

        System.out.print("Nueva Descripción: ");
        String nuevaDescripcion = scanner.nextLine().trim();
        if(!nuevaDescripcion.isEmpty()){
            cat.setDescripcion(nuevaDescripcion);
        }

        try {
            categoriaRepo.guardar(cat);
            System.out.println("\n  [*] Categoría modificada con éxito.");
        } catch (Exception e) {
            System.out.println("\n  [!] ERROR al modificar la categoría: " + e.getMessage());
        }
        System.out.println("  Presione Enter para continuar...");
        scanner.nextLine();
    }

    public static void bajaCategoria(){
        if(!listarCategoriasActivas()){
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Long id = (long)leerEntero("Ingrese el ID de la categoría a dar de baja: ");

        Optional<Categoria> optCategoria = categoriaRepo.buscarPorId(id);

        if (optCategoria.isEmpty() || optCategoria.get().isEliminado()) {
            System.out.println("\n  [!] Error: Categoría no encontrada o ya dada de baja.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Categoria cat = optCategoria.get();

        List<Producto> productosActivos = categoriaRepo.buscarProductosPorCategoria(id);
        if(!productosActivos.isEmpty()){
            System.out.println("\n  [!] La categoría no se puede dar de baja porque tiene "
                    + productosActivos.size() + " producto(s) activo(s) asociado(s).");
            System.out.println("  Debe dar de baja o reasignar los productos primero.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        try {
            boolean exito = categoriaRepo.eliminarLogico(id);

            if (exito) {
                System.out.println("\n  [✓] Baja lógica confirmada para la categoría: " + cat.getNombre());
            } else {
                System.out.println("\n  [!] Error: Categoría no encontrada o ya dada de baja");
            }
        }catch (Exception e){
            System.out.println("\n  [!] ERROR al intentar la baja lógica.");
            System.out.println("  Mas info: " + e.getMessage());
        }

        System.out.println("  Presione Enter para continuar...");
        scanner.nextLine();

    }

    public static void listarCategorias(){
        System.out.println("\n>> [Listado de Categorías Activas]");
        listarCategoriasActivas();
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static boolean listarCategoriasActivas(){
        List<Categoria> catActivas = categoriaRepo.listarActivos();

        if(catActivas.isEmpty()){
            System.out.println("  [!] No hay categorías activas registradas.");
            return false;
        }

        System.out.println("\n>> Listado de Categorías Activas");
        System.out.printf("%-5s %-20s %-30s%n", "ID", "Nombre", "Descripción");
        System.out.println("------------------------------------------------------------------");

        for (Categoria cat : catActivas){
            String descripcion = Optional.ofNullable(cat.getDescripcion())
                    .filter(d -> !d.isBlank())
                    .orElse("Sin descripción");

            System.out.printf("%-5d %-20s %-30s%n", cat.getId(), cat.getNombre(), descripcion);
        }
        System.out.println("------------------------------------------------------------------");
        return true;
    }

    // Metodos Productos
    public static void altaProducto(){
        if(!listarCategoriasActivas()){
            System.out.println("  (Agregue una Categoría antes de agregar un Producto)");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("=== INGRESAR NUEVO PRODUCTO:");
        System.out.println("(Presione Enter para dejar vacío");
        Long idCategoria = (long)leerEntero("\nID Categoría (Obligatorio):  ");
        Optional<Categoria> optCategoria = categoriaRepo.buscarPorId(idCategoria);

        if(optCategoria.isEmpty() || optCategoria.get().isEliminado()){
            System.out.println("\n  [!] Error: La categoría seleccionada no existe o está dada de baja.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Categoria categoria = optCategoria.get();


        String nombre = leerCadena("  Nombre (Obligatorio): ");
        if(nombre.isEmpty()){
            System.out.println("  [!] ERROR: El nombre del producto no puede estar vacío.");
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.print("  Descripción: ");
        String descripcion = scanner.nextLine().trim();

        Double precio;
        while (true) {
            precio = leerDouble("  Precio (mayor a 0): ");
            if (precio > 0) break;
            System.out.println("  [!] El precio debe ser mayor a 0.");
        }

        int stock = leerEntero("  Stock: ");

        System.out.print("  URL Imagen: ");
        String imagen = scanner.nextLine().trim();

        System.out.print("  ¿Está disponible? (S/N) [Por Defecto S]: ");
        String respuesta = scanner.nextLine().trim().toUpperCase();

        boolean disponible = !respuesta.equals("N");

        Producto nuevoProducto = Producto.builder()
                .nombre(nombre)
                .descripcion(descripcion.isBlank() ? null : descripcion)
                .precio(precio)
                .stock(stock)
                .imagen(imagen.isBlank() ? null : imagen)
                .disponible(disponible)
                .build();

        try {
            Producto productoPersistido = categoriaRepo.agregarProducto(idCategoria, nuevoProducto);
            System.out.println("\n  [✓] Producto agregado con éxito. ");
            System.out.println("  ID generado: " + productoPersistido.getId());
            System.out.println("  Categoría asignada: " + categoria.getNombre());
        } catch (Exception e) {
            System.out.println("\n  [!] Error al dar de alta el producto: " + e.getMessage());
        }

        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void modificarProducto(){
        if(!listarProductosActivos()){
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }
        Long id = (long)leerEntero("Ingrese el ID del producto a modificar: ");
        Optional<Producto> optProducto = productoRepo.buscarPorId(id);

        if(optProducto.isEmpty() || optProducto.get().isEliminado()){
            System.out.println("\n  [!] No se encontró un producto activo con el ID " + id);
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Producto prod = optProducto.get();
        System.out.println("\nValores actuales -> [Nombre: " + prod.getNombre() + " | Precio: " + prod.getPrecio() + " | Stock: " + prod.getStock() + "]");
        System.out.println("(Deje el campo en blanco y presione ENTER si no desea modificar el valor)");

        System.out.print("Nuevo Nombre: ");
        String nuevoNombre = scanner.nextLine().trim();
        if(!nuevoNombre.isBlank()){
            prod.setNombre(nuevoNombre);
        }

        System.out.print("Nuevo Precio: ");
        String entradaPrecio = scanner.nextLine().trim();
        if(!entradaPrecio.isBlank()){
            try {
                double nuevoPrecio = Double.parseDouble(entradaPrecio);
                if(nuevoPrecio > 0){
                    prod.setPrecio(nuevoPrecio);
                }else {
                    System.out.println("  [!] Error: El precio debe ser mayor a 0. Se conserva el valor anterior.");
                }
            }catch (NumberFormatException e){
                System.out.println("  [!] Error: Formato de precio inválido. Se conserva el valor anterior.");
            }
        }

        System.out.print("Nuevo Stock: ");
        String entradaStock = scanner.nextLine().trim();
        if(!entradaStock.isBlank()){
            try {
                int nuevoStock = Integer.parseInt(entradaStock);
                if(nuevoStock >= 0){
                    prod.setStock(nuevoStock);
                }else {
                    System.out.println("  [!] Error: El stock no puede ser negativo. Se conserva el valor anterior.");
                }
            }catch (NumberFormatException e){
                System.out.println("  [!] Error: Formato inválido. Se conserva el valor anterior.");
            }
        }

        try{
            productoRepo.guardar(prod);
            System.out.println("\n  [*] Producto modificado con éxito.");
        } catch (Exception e) {
            System.out.println("\n  [!] ERROR al modificar el producto: " + e.getMessage());
        }
        System.out.println("  Presione Enter para continuar...");
        scanner.nextLine();
    }

    public static void bajaProducto(){
        if(!listarProductosActivos()){
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Long id = (long)leerEntero("Ingrese el ID del producto a dar de baja: ");

        Optional<Producto> optProducto = productoRepo.buscarPorId(id);

        if (optProducto.isEmpty() || optProducto.get().isEliminado()) {
            System.out.println("\n  [!] Error: Producto no encontrado o dado de baja.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Producto prod = optProducto.get();

        try {
            boolean exito = productoRepo.eliminarLogico(id);

            if (exito) {
                System.out.println("\n  [✓] Baja lógica confirmada para el producto: " + prod.getNombre());
            } else {
                System.out.println("\n  [!] Error: Producto no encontrado o dado de baja");
            }
        }catch (Exception e){
            System.out.println("\n  [!] ERROR al intentar la baja lógica.");
            System.out.println("  Mas info: " + e.getMessage());
        }

        System.out.println("  Presione Enter para continuar...");
        scanner.nextLine();

    }

    public static void listarProductos(){
        System.out.println("\n>> Listado de Productos Activos");
        listarProductosActivos();
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static boolean listarProductosActivos() {
        List<Categoria> categorias = categoriaRepo.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("  [!] No hay categorías activas registradas.");
            return false;
        }

        boolean hayProductos = false;
        System.out.println("\n>> Listado de Productos Activos por Categoría");
        System.out.printf("%-5s %-20s %-12s %-8s %-12s %-15s%n", "ID", "Nombre", "Precio", "Stock", "Disponible", "Categoría");
        System.out.println("--------------------------------------------------------------------------------");

        for (Categoria cat : categorias) {
            List<Producto> productos = categoriaRepo.buscarProductosPorCategoria(cat.getId());
            for (Producto p : productos) {
                hayProductos = true;
                String precioFormateado = String.format("$%.2f", p.getPrecio()).replace('.', ',');
                System.out.printf("%-5d %-20s %-12s %-8d %-12s %-15s%n",
                        p.getId(), p.getNombre(), precioFormateado, p.getStock(),
                        p.isDisponible() ? "Sí" : "No", cat.getNombre());
            }
        }

        if (!hayProductos) {
            System.out.println("  [!] No hay productos activos registrados en ninguna categoría.");
            System.out.println("--------------------------------------------------------------------------------");
            return false;
        }

        System.out.println("--------------------------------------------------------------------------------");
        return true;
    }

    // Metodos Usuarios
    public static void altaUsuario(){
        System.out.println("\n[ALTA DE USUARIO]");
        String nombre = leerCadena("Nombre (Obligatorio): ");
        String apellido = leerCadena("Apellido (Obligatorio): ");

        String mail;
        while(true){
            mail = leerCadena("Mail (Obligatorio): ");
            if(usuarioRepo.buscarPorMail(mail).isPresent()){
                System.out.println("  [!] ERROR: El mail ya se encuentra registrado por otro usuario. Intente con otro.\n");
            }else {
                break;
            }
        }

        System.out.print("Celular (Opcional - Presione Enter para omitir): ");
        String celular = scanner.nextLine().trim();

        String contrasenia = leerCadena("Contraseña (Obligatorio): ");

        Rol rol = null;
        while(rol == null){
            System.out.println("  Seleccione el Rol:");
            System.out.println("  1. ADMIN");
            System.out.println("  2. USUARIO");
            int opcRol = leerEntero("Opción: ");

            switch(opcRol){
                case 1 -> rol = Rol.ADMIN;
                case 2 -> rol = Rol.USUARIO;
                default -> System.out.println("  [!] Opción inválida. Debe elegir 1 o 2.\n");
            }
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombre(nombre)
                .apellido(apellido)
                .mail(mail)
                .celular(celular.isBlank() ? null : celular)
                .contraseña(contrasenia)
                .rol(rol)
                .build();
        try {
            Usuario usuarioAgregado = usuarioRepo.guardar(nuevoUsuario);
            System.out.println("\n  [✓] Usuario creado con éxito [ID: " + usuarioAgregado.getId() + "]");
        } catch (Exception e) {
            System.out.println("\n  [!] ERROR al guardar el usuario: " + e.getMessage());
        }
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void modificarUsuario(){
        if(!listarUsuariosActivos()){
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }
        Long id = (long)leerEntero("Ingrese el ID del usuario a modificar: ");
        Optional<Usuario> optUsuario = usuarioRepo.buscarPorId(id);

        if(optUsuario.isEmpty() || optUsuario.get().isEliminado()){
            System.out.println("\n  [!] No se encontró un usuario activo con el ID " + id);
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Usuario u = optUsuario.get();
        System.out.println("\nValores actuales -> [Nombre: " + u.getNombre()
                + " | Apellido: " + u.getApellido()
                + " | Mail: " + u.getMail()
                + " | Celular: " + u.getCelular()
                + " | Rol: " + u.getRol() + "]");
        System.out.println("(Deje el campo en blanco y presione ENTER si no desea modificar el valor)");

        System.out.print("Nuevo Nombre: ");
        String nuevoNombre = scanner.nextLine().trim();
        if(!nuevoNombre.isBlank()){
            u.setNombre(nuevoNombre);
        }

        System.out.print("Nuevo Apellido: ");
        String nuevoApellido = scanner.nextLine().trim();
        if(!nuevoApellido.isBlank()){
            u.setApellido(nuevoApellido);
        }

        System.out.print("Nuevo Mail: ");
        String nuevoMail = scanner.nextLine().trim();
        if(!nuevoMail.isBlank()){
            Optional<Usuario> optUsuarioConMail = usuarioRepo.buscarPorMail(nuevoMail);
            if(optUsuarioConMail.isPresent() && !optUsuarioConMail.get().getId().equals(u.getId())){
                System.out.println("  [!] Error: El mail ingresado ya está en uso por otro usuario. Se conserva el mail anterior.");
            }else{
                u.setMail(nuevoMail);
            }
        }

        System.out.print("Nuevo Celular: ");
        String nuevoCelular = scanner.nextLine().trim();
        if(!nuevoCelular.isBlank()){
            u.setCelular(nuevoCelular);
        }

        System.out.print("Nueva Contraseña: ");
        String nuevaContrasenia = scanner.nextLine().trim();
        if(!nuevaContrasenia.isBlank()){
            u.setContraseña(nuevaContrasenia);
        }

        try {
            usuarioRepo.guardar(u);
            System.out.println("\n  [*] Usuario modificado con éxito.");
        } catch (Exception e) {
            System.out.println("\n  [!] ERROR al modificar el usuario: " + e.getMessage());
        }
        System.out.println("  Presione Enter para continuar...");
        scanner.nextLine();
    }

    public static void bajaUsuario(){
        if(!listarUsuariosActivos()){
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Long id = (long)leerEntero("Ingrese el ID del usuario a dar de baja: ");

        Optional<Usuario> optUsuario = usuarioRepo.buscarPorId(id);

        if (optUsuario.isEmpty() || optUsuario.get().isEliminado()) {
            System.out.println("\n  [!] Error: Usuario no encontrado o dado de baja.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Usuario u = optUsuario.get();

        try {
            boolean exito = usuarioRepo.eliminarLogico(id);

            if (exito) {
                System.out.println("\n  [✓] Baja lógica confirmada para el usuario: " + u.getNombreCompleto());
            } else {
                System.out.println("\n  [!] Error: Usuario no encontrado o dado de baja");
            }
        }catch (Exception e){
            System.out.println("\n  [!] ERROR al intentar la baja lógica.");
            System.out.println("  Mas info: " + e.getMessage());
        }

        System.out.println("  Presione Enter para continuar...");
        scanner.nextLine();
    }

    public static boolean listarUsuariosActivos() {
        List<Usuario> usuarios = usuarioRepo.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("  [!] No hay usuarios activos registrados.");
            return false;
        }

        System.out.println("\n>> Listado de Usuarios Activos");
        System.out.printf("%-5s %-20s %-30s %-8s%n", "ID", "Nombre Completo", "Mail", "Rol");
        System.out.println("--------------------------------------------------------------------------------");

        for (Usuario u : usuarios) {
            System.out.printf("%-5s %-20s %-30s %-8s%n",
                        u.getId(), u.getNombreCompleto(), u.getMail(),
                        u.getRol());
        }

        System.out.println("--------------------------------------------------------------------------------");
        return true;
    }

    public static void buscarUsuarioPorMail(){
        System.out.println("\n[BÚSQUEDA DE USUARIO POR MAIL]");

        Optional<Usuario> usuarioBuscado = usuarioRepo.buscarPorMail(leerCadena("Ingrese mail de usuario a buscar: "));

        if(usuarioBuscado.isPresent()){
            Usuario usuarioEncontrado = usuarioBuscado.get();

//            String celular = (usuarioEncontrado.getCelular() != null && !usuarioEncontrado.getCelular().isBlank())
//                    ? usuarioEncontrado.getCelular()
//                    : "Sin registrar";

            System.out.println("\n>> Usuario Encontrado");
            System.out.printf("%-5s %-25s %-30s %-15s %-8s%n", "ID", "Nombre Completo", "Mail", "Celular", "Rol");
            System.out.println("-------------------------------------------------------------------------------------");

            System.out.printf("%-5s %-25s %-30s %-15s %-8s%n", usuarioEncontrado.getId(), usuarioEncontrado.getNombreCompleto(), usuarioEncontrado.getMail(), (usuarioEncontrado.getCelular() != null && !usuarioEncontrado.getCelular().isBlank()) ? usuarioEncontrado.getCelular() : "Sin registrar", usuarioEncontrado.getRol());
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
        }else{
            System.out.println("\n  [!] No existe un usuario activo con el mail buscado.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
        }
    }

    // Metodos Pedidos
    public static void altaPedido(){
        System.out.println("\n === [ALTA DE PEDIDO]");

        if(!listarUsuariosActivos()){
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Long idUsuario = (long)leerEntero("Ingrese el ID del usuario: ");
        Optional<Usuario> optUsuario = usuarioRepo.buscarPorId(idUsuario);

        if (optUsuario.isEmpty() || optUsuario.get().isEliminado()) {
            System.out.println("\n  [!] Error: Usuario no encontrado o dado de baja.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Usuario usuarioSeleccionado = optUsuario.get();

        FormaPago formaPago = null;
        while (formaPago == null) {
            System.out.println("Seleccione la forma de pago:");
            System.out.println("  1. TARJETA");
            System.out.println("  2. TRANSFERENCIA");
            System.out.println("  3. EFECTIVO");
            int opcFormaPago = leerEntero("Opción: ");

            switch (opcFormaPago) {
                case 1 -> formaPago = FormaPago.TARJETA;
                case 2 -> formaPago = FormaPago.TRANSFERENCIA;
                case 3 -> formaPago = FormaPago.EFECTIVO;
                default -> System.out.println("  [!] Opción inválida. Intente nuevamente.\n");
            }
        }

        List<ItemTemporal> itemsTemporales = new ArrayList<>();
        boolean agregarMasProductos = true;

        while(agregarMasProductos){
            if(!listarProductosActivos()) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
                break;
            }

            Long idProducto = (long)leerEntero("Ingrese el ID del producto a agregar: ");
            Optional<Producto> optProducto = productoRepo.buscarPorId(idProducto);

            if(optProducto.isEmpty() || optProducto.get().isEliminado()){
                System.out.println("\n  [!] No se encontró un producto activo con el ID " + idProducto);
                System.out.println("  Presione Enter para continuar...");
                scanner.nextLine();
                continue;
            }

            Producto productoSeleccionado = optProducto.get();
            if(!productoSeleccionado.isDisponible()){
                System.out.println("\n  [!] El producto seleccionado no esta disponible.");
                System.out.println("  Presione Enter para continuar...");
                scanner.nextLine();
                continue;
            }

            int cantidad = leerEntero("Cantidad: ");
            if(cantidad <= 0){
                System.out.println("\n  [!] La cantidad debe ser mayor a 0.");
                continue;
            }

            int cantidadAcumulada = itemsTemporales.stream()
                    .filter(item -> item.idProducto().equals(idProducto))
                    .mapToInt(ItemTemporal::cantidad)
                    .sum();

            int stockDisponibleReal = productoSeleccionado.getStock() - cantidadAcumulada;

            if (stockDisponibleReal < cantidad) {
                System.out.println("\n  [!] La cantidad ingresada supera el stock disponible real.");
                System.out.println("  Stock Total: " + productoSeleccionado.getStock() + " | Ya agregados al carrito: " + cantidadAcumulada);
                System.out.println("  Presione Enter para continuar...");
                scanner.nextLine();
                continue;
            }

            itemsTemporales.add(new ItemTemporal(idProducto, cantidad));
            System.out.println("  [✓] Producto '" + productoSeleccionado.getNombre() + "' agregado al pedido temporal.");

            System.out.print("\n¿Desea agregar otro producto? (S/N) [Por defecto N]: ");
            String resp = scanner.nextLine().trim().toUpperCase();
            agregarMasProductos = resp.equals("S");
        }

        if (itemsTemporales.isEmpty()) {
            System.out.println("\n  [!] Operación cancelada: El pedido debe contener al menos un producto.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Usuario usuario = em.find(Usuario.class, idUsuario);
            if(usuario == null || usuario.isEliminado()){
                throw new RuntimeException("El usuario seleccionado no existe o está dado de baja.");
            }

            Pedido pedido = Pedido.builder()
                    .fecha(LocalDate.now())
                    .estado(Estado.PENDIENTE)
                    .formaPago(formaPago)
                    .build();

            for (ItemTemporal item : itemsTemporales) {
                Producto producto = em.find(Producto.class, item.idProducto());

                if (producto == null || producto.isEliminado() || !producto.isDisponible()) {
                    throw new RuntimeException("El producto ID " + item.idProducto() + " ya no está disponible.");
                }

                if (producto.getStock() < item.cantidad()) {
                    throw new RuntimeException("Stock insuficiente durante la transacción para: " + producto.getNombre());
                }

                pedido.addDetallePedido(item.cantidad(), producto);
                producto.setStock(producto.getStock() - item.cantidad());
            }

            pedido.calcularTotal();

            em.persist(pedido);
            usuario.getPedidos().add(pedido);

            tx.commit();

            System.out.println("\n  ==================================================");
            System.out.println("  [✓] ¡PEDIDO REGISTRADO EXITOSAMENTE!");
            System.out.println("  ID Pedido: " + pedido.getId());
            System.out.println("  Fecha: " + pedido.getFecha());
            System.out.println("  Cliente: " + usuario.getNombreCompleto());
            System.out.println("  Forma de Pago: " + pedido.getFormaPago());
            System.out.println("  --------------------------------------------------");
            System.out.println("  Resumen de Ítems:");
            for (DetallePedido dp : pedido.getDetallesPedidos()) {
                System.out.printf("   - %s x%d -> Subtotal: $%.2f%n",
                        dp.getProducto().getNombre(), dp.getCantidad(), dp.getSubtotal());
            }
            System.out.println("  --------------------------------------------------");
            System.out.printf("  TOTAL DEL PEDIDO: $%.2f%n", pedido.getTotal());
            System.out.println("  ==================================================");

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("\n  [!] ERROR AL PROCESAR LA TRANSACCIÓN. Operación cancelada (Rollback completo).");
            System.out.println("  Detalle: " + e.getMessage());
        } finally {
            em.close();
        }

        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void cambiarEstadoPedido(){
        System.out.println("\n === [CAMBIAR ESTADO DE PEDIDO]");

        Long idPedido = (long) leerEntero("Ingrese el ID del pedido: ");
        Optional<Pedido> optPedido = pedidoRepo.buscarPorId(idPedido);

        if(optPedido.isEmpty() || optPedido.get().isEliminado()){
            System.out.println("\n  [!] Error: El pedido ID " + idPedido + " no existe o está dado de baja.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Pedido pedido = optPedido.get();

        System.out.println("\nEstado actual del Pedido ID " + pedido.getId() + ": [" + pedido.getEstado() + "]");

        System.out.println("\nSeleccione el nuevo estado:");
        System.out.println("  1. PENDIENTE");
        System.out.println("  2. CONFIRMADO");
        System.out.println("  3. TERMINADO");
        System.out.println("  4. CANCELADO");
        int opcEstado = leerEntero("Opción (1-4): ");

        Estado nuevoEstado = switch (opcEstado) {
            case 1 -> Estado.PENDIENTE;
            case 2 -> Estado.CONFIRMADO;
            case 3 -> Estado.TERMINADO;
            case 4 -> Estado.CANCELADO;
            default -> null;
        };

        if (nuevoEstado == null) {
            System.out.println("\n  [!] Opción de estado inválida. Operación cancelada.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        pedido.setEstado(nuevoEstado);

        try {
            pedidoRepo.guardar(pedido);

            System.out.println("\n  [✓] Estado actualizado con éxito.");
            System.out.println("  ID Pedido: " + pedido.getId());
            System.out.println("  Nuevo Estado: " + pedido.getEstado());
        } catch (Exception e) {
            System.out.println("\n  [!] Error al actualizar el estado del pedido: " + e.getMessage());
        }

        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void bajaPedido(){
        System.out.println("\n === [BAJA LOGICA DE PEDIDO]");

        Long idPedido = (long) leerEntero("Ingrese el ID del pedido: ");
        Optional<Pedido> optPedido = pedidoRepo.buscarPorId(idPedido);

        if(optPedido.isEmpty() || optPedido.get().isEliminado()){
            System.out.println("\n  [!] Error: El pedido ID " + idPedido + " no existe o está dado de baja.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Pedido pedido = optPedido.get();

        try {
            boolean exito = pedidoRepo.eliminarLogico(idPedido);

            if(exito){
                String totalFormateado = String.format("$%.2f", pedido.getTotal()).replace('.', ',');
                System.out.println("\n  [✓] Baja lógica realizada con éxito para el pedido.");
                System.out.println("  ID Pedido: " + pedido.getId());
                System.out.println("  Total del Pedido: " + totalFormateado);
            }else{
                System.out.println("\n  [!] Error: Pedido no encontrado o ya dado de baja.");
            }
        }catch (Exception e){
            System.out.println("\n  [!] ERROR al intentar la baja lógica del pedido.");
            System.out.println("  Detalle: " + e.getMessage());
        }
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void listarPedidosActivos(){
        List<Pedido> pedidosActivos = pedidoRepo.listarActivos();

        if(pedidosActivos.isEmpty()){
            System.out.println("  [!] No hay pedidos activos registrados.");
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("\n>> Listado de Pedidos Activos");
        System.out.printf("%-5s %-15s %-15s %-20s %-15s%n", "ID", "Fecha", "Estado", "Forma de Pago", "Total");
        System.out.println("-----------------------------------------------------------------------------");

        for (Pedido pedido : pedidosActivos){
            String totalFormateado = String.format("$%.2f", pedido.getTotal()).replace('.', ',');
            System.out.printf("%-5d %-15s %-15s %-20s %-15s%n", pedido.getId(), pedido.getFecha(), pedido.getEstado(), pedido.getFormaPago(), totalFormateado);
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void listarPedidosPorUsuario(){
        if(!listarUsuariosActivos()){
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Long id = (long)leerEntero("Ingrese el ID del usuario: ");
        Optional<Usuario> optUsuario = usuarioRepo.buscarPorId(id);

        if(optUsuario.isEmpty() || optUsuario.get().isEliminado()){
            System.out.println("\n  [!] No se encontró un usuario activo con el ID " + id);
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Usuario u = optUsuario.get();
        List<Pedido> pedidos = usuarioRepo.buscarPedidosPorUsuario(u.getId());
        if(pedidos.isEmpty()){
            System.out.println("\n  [!] El usuario ID: " + u.getId() + " no posee pedidos registrados.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("\n>> Listado de Pedidos - USUARIO: " + u.getNombreCompleto());
        System.out.printf("%-5s %-15s %-15s %-15s%n", "ID", "Fecha", "Estado", "Total");
        System.out.println("-----------------------------------------------------------------------------");

        for (Pedido pedido : pedidos){
            String totalFormateado = String.format("$%.2f", pedido.getTotal()).replace('.', ',');
            System.out.printf("%-5d %-15s %-15s %-15s%n", pedido.getId(), pedido.getFecha(), pedido.getEstado(), totalFormateado);
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void listarPedidosPorEstado(){
        System.out.println("\n === [LISTA DE PEDIDOS POR ESTADO]");
        System.out.println("\nSeleccion el estado a filtrar:");
        System.out.println("  1. PENDIENTE");
        System.out.println("  2. CONFIRMADO");
        System.out.println("  3. TERMINADO");
        System.out.println("  4. CANCELADO");
        int opcEstado = leerEntero("Opción (1-4): ");

        Estado estadoSeleccionado = switch (opcEstado) {
            case 1 -> Estado.PENDIENTE;
            case 2 -> Estado.CONFIRMADO;
            case 3 -> Estado.TERMINADO;
            case 4 -> Estado.CANCELADO;
            default -> null;
        };

        if (estadoSeleccionado == null) {
            System.out.println("\n  [!] Opción de estado inválida. Operación cancelada.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        List<Pedido> pedidos = pedidoRepo.buscarPorEstado(estadoSeleccionado);
        if(pedidos.isEmpty()){
            System.out.println("\n  [!] No existen pedidos registrados con estado " + estadoSeleccionado);
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("\n>> Listado de Pedidos - ESTADO: " + estadoSeleccionado);
        System.out.printf("%-5s %-15s %-20s %-15s%n", "ID", "Fecha", "Forma de Pago", "Total");
        System.out.println("-----------------------------------------------------------------------------");

        for (Pedido pedido : pedidos){
            String totalFormateado = String.format("$%.2f", pedido.getTotal()).replace('.', ',');
            System.out.printf("%-5d %-15s %-20s %-15s%n", pedido.getId(), pedido.getFecha(), pedido.getFormaPago(), totalFormateado);
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();

    }

    // Metodos Reportes
    public static void mostrarProductosPorCategoria(){
        System.out.println("\n[REPORTE: PRODUCTOS POR CATEGORIA]");

        if(!listarCategoriasActivas()){
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Long id = (long)leerEntero("Seleccione el ID de la categoria: ");

        List<Producto> productos = categoriaRepo.buscarProductosPorCategoria(id);

        if(productos.isEmpty()){
            System.out.println("  [!] La categoría seleccionada no contiene productos activos.");
        }else{
            System.out.println("\n>> Productos encontrados para la categoria ID " +  id);
            System.out.printf("%-5s %-20s %-12s %-8s%n", "ID", "Nombre", "Precio", "Stock");
            System.out.println("------------------------------------------------------------------");

            for(Producto prod : productos){
                String precioFormateado = String.format("$%.2f", prod.getPrecio()).replace('.',',');
                System.out.printf("%-5d %-20s %-12s %-8d%n", prod.getId(), prod.getNombre(), precioFormateado, prod.getStock());
            }
            System.out.println("------------------------------------------------------------------");
        }
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void mostrarPedidosPorUsuario(){
        System.out.println("\n[REPORTE: PEDIDOS POR USUARIO]");

        if (!listarUsuariosActivos()) {
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Long id = (long) leerEntero("Ingrese el ID del usuario: ");
        Optional<Usuario> optUsuario = usuarioRepo.buscarPorId(id);

        if (optUsuario.isEmpty() || optUsuario.get().isEliminado()) {
            System.out.println("\n  [!] No se encontró un usuario activo con el ID " + id);
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Usuario u = optUsuario.get();
        List<Pedido> pedidos = usuarioRepo.buscarPedidosPorUsuario(u.getId());

        if (pedidos.isEmpty()) {
            System.out.println("\n  [!] El usuario ID " + u.getId() + " (" + u.getNombreCompleto() + ") no posee pedidos activos.");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("\n>> REPORTE DE PEDIDOS - CLIENTE: " + u.getNombreCompleto());
        System.out.printf("%-5s %-15s %-15s %-20s %-15s%n", "ID", "Fecha", "Estado", "Forma de Pago", "Total");
        System.out.println("-----------------------------------------------------------------------------");

        for (Pedido pedido : pedidos) {
            String totalFormateado = String.format("$%.2f", pedido.getTotal()).replace('.', ',');
            System.out.printf("%-5d %-15s %-15s %-20s %-15s%n",
                    pedido.getId(),
                    pedido.getFecha(),
                    pedido.getEstado(),
                    pedido.getFormaPago(),
                    totalFormateado);
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void mostrarTotalFacturado(){
        System.out.println("\n[REPORTE: TOTAL FACTURADO]");

        List<Pedido> pedidos = pedidoRepo.buscarPorEstado(Estado.TERMINADO);
        if(pedidos.isEmpty()){
            System.out.println("\n  TOTAL FACTURADO: $0.00");
            System.out.println("  Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Double total = pedidos.stream().mapToDouble(Pedido::getTotal).sum();
        String totalFormateado = String.format(Locale.US, "$%.2f", total).replace('.', ',');

        System.out.println("\n  TOTAL FACTURADO: " + totalFormateado);
        System.out.println("  Presione Enter para continuar...");
        scanner.nextLine();
    }

    // Metodos auxiliares
    private static int leerEntero(String mensaje){
        while(true){
            try{
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim();
                int entradaInt = Integer.parseInt(entrada);
                if(entradaInt >= 0){
                    return entradaInt;
                }else{
                    System.out.println("  [!] Entrada inválida. Debe ingresar un número mayor o igual a 0.");
                }
            }catch(NumberFormatException e){
                System.out.println("  [!] Entrada inválida. Debe ingresar un número entero.");
            }
            System.out.println();
        }
    }

    private static Double leerDouble(String mensaje){
        while(true){
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim();
                Double entradaDouble = Double.parseDouble(entrada);
                if(entradaDouble >= 0) {
                    return entradaDouble;
                }else{
                    System.out.println("  [!] Entrada inválida. Debe ingresar un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  [!] Entrada inválida. Debe ingresar un número entero o decimal.");
            }
        }
    }

    private static String leerCadena(String mensaje){
        while(true){
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            if(!entrada.isBlank()){
                return entrada;
            }

            System.out.println("  [!] ERROR: Este campo no puede estar vacío. Intente de nuevo.\n");
        }
    }
}
