# 🏪 CRUD Tienda — MVC · JDBC · GUI Swing

Aplicación de gestión de datos desarrollada en **Java** como proyecto universitario. Permite administrar clientes, pedidos y envíos de una tienda a través de una interfaz gráfica conectada a una base de datos local.

---

## ✨ Funcionalidades

- Gestión completa de **Customers, Orders y Shipments**
- **Crear, leer, modificar y eliminar** registros (CRUD completo)
- Interfaz con **JTable** para visualizar todos los registros en tiempo real
- **Selección de fila** en la tabla para cargar datos automáticamente en el formulario
- **Búsqueda por ID** para localizar registros concretos
- **Validación de integridad referencial**: no se puede eliminar un cliente con pedidos asociados, ni un pedido con envíos asociados
- Diálogo de **confirmación** antes de eliminar cualquier registro

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| **Java** | Lenguaje principal de la aplicación |
| **Java Swing** | Interfaz gráfica (GUI) |
| **SQLite** | Base de datos local para persistir los datos |
| **sqlite-jdbc 3.53.1.0** | Driver JDBC para conectar Java con SQLite |
| **Apache NetBeans** | IDE utilizado para el desarrollo |

---

## 🏗️ Arquitectura — Patrón MVC

El proyecto sigue el patrón **Modelo-Vista-Controlador**:

```
src/
├── Main.java                      # Punto de entrada
├── Controlador/
│   └── Controlador.java           # Gestiona eventos y coordina Modelo y Vista
├── Modelo/
│   └── Modelo.java                # Lógica de negocio y acceso a la BD (SQLite)
└── Vista/
    └── VentanaPrincipal.java      # Ventana principal con formularios y tablas (JFrame)
```

---

## 🗄️ Base de datos

La base de datos **Tienda.db** (SQLite) se crea automáticamente al iniciar la aplicación. Contiene las siguientes tablas:

- **Customers** — almacena cada cliente con su ID y nombre
- **Orders** — almacena cada pedido con su ID, cliente asociado (FK) y fecha
- **Shipments** — almacena cada envío con su ID, pedido asociado (FK) y fecha de envío

Las claves foráneas garantizan la integridad referencial entre las tablas: un pedido siempre pertenece a un cliente existente, y un envío siempre pertenece a un pedido existente.

---

## ▶️ Cómo ejecutar

### Opción 1 — JAR precompilado

```bash
java -jar dist/CRUD-Java.jar
```

### Opción 2 — Desde NetBeans

1. Abre NetBeans y selecciona **File → Open Project**
2. Navega a la carpeta del proyecto y ábrelo
3. Pulsa **Run** (F6)

> El archivo `Tienda.db` se generará automáticamente en el directorio raíz del proyecto la primera vez que se ejecute.

---

## 📦 Dependencias

- [sqlite-jdbc 3.53.1.0](https://github.com/xerial/sqlite-jdbc) — incluida en `lib/`

No se necesita ninguna instalación adicional de base de datos.
