package gm.zona_fit;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger = LoggerFactory.getLogger(ZonaFitApplication.class);

	public static void main(String[] args) {
		logger.info("Iniciando la aplicación");
		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("Aplicación finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();
	}

	private void zonaFitApp() {
		var salir = false;
		var sc = new Scanner(System.in);

		while(!salir){
			var opcion = mostrarMenu(sc);
			salir = ejecutarOpciones(sc, opcion);
			logger.info("");
		}
	}

	private int mostrarMenu(Scanner sc) {
		logger.info("""
				\n**** Zona Fit ****
				1. Listar clientes.
				2. Buscar cliente.
				3. Agregar cliente.
				4. Modificar cliente.
				5. Eliminar cliente.
				6. Salir.
				Elige una opción:\s""");
		return Integer.parseInt(sc.nextLine());
	}

	private boolean ejecutarOpciones(Scanner sc, int opcion) {
		var salir = false;
		switch (opcion) {
			case 1 -> {
				logger.info("--- Listado de clientes ---");
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString()));
			}
			case 2 -> {
				logger.info("--- Buscar cliente por Id ---");
				logger.info("Id cliente: ");
				var id = Integer.parseInt(sc.nextLine());

				Cliente cliente = clienteServicio.buscarClientePorId(id);

				if (cliente != null) {
					logger.info("Cliente encontrado: " + cliente);
				} else {
					logger.info("Cliente no encontrado: " + cliente);
				}
			}
			case 3 -> {
				logger.info("--- Agregar cliente ---");
				logger.info("Nombre: ");
				var nombre = sc.nextLine();
				logger.info("Apellido: ");
				var apellido = sc.nextLine();
				logger.info("Membresía: ");
				var membresia = Integer.parseInt(sc.nextLine());

				var cliente = new Cliente();
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setMembresia(membresia);
				clienteServicio.guardarCliente(cliente);

				logger.info("Cliente agregado: " + cliente);
			}
			case 4 -> {
				logger.info("--- Modificar cliente ---");
				logger.info("Id cliente: ");
				var id = Integer.parseInt(sc.nextLine());

				var cliente = clienteServicio.buscarClientePorId(id);

				if (cliente != null) {
					logger.info("Nombre: ");
					var nombre = sc.nextLine();
					logger.info("Apellido: ");
					var apellido = sc.nextLine();
					logger.info("Membresía: ");
					var membresia = Integer.parseInt(sc.nextLine());

					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMembresia(membresia);

					clienteServicio.guardarCliente(cliente);

					logger.info("Cliente modificado: " + cliente);
				} else {
					logger.info("Cliente no encontrado: " + cliente);
				}
			}
			case 5 -> {
				logger.info("--- Eliminar cliente ---");
				logger.info("Id cliente: ");
				var id = Integer.parseInt(sc.nextLine());

				var cliente = clienteServicio.buscarClientePorId(id);

				if (cliente != null) {
					clienteServicio.eliminarCliente(cliente);
					logger.info("Cliente eliminado: " + cliente);
				} else {
					logger.info("Cliente no encontrado: " + cliente);
				}
			}
			case 6 -> {
				logger.info("Hasta pronto.");
				salir = true;
			}
			default -> logger.info("Opción desconocida: " + opcion);
		}

		return salir;
	}
}
