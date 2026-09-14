# 📱 PrestamoLab CTMA

Aplicación móvil Android para la gestión y trazabilidad de préstamos de equipos y recursos de formación del Centro de Tecnología de la Manufactura Avanzada (CTMA).

El proyecto hace parte del proceso formativo del programa **Análisis y Desarrollo de Software (ADSO) del SENA** y se desarrolla bajo un enfoque de trabajo basado en **Scrum**, historias de usuario, arquitectura por capas, persistencia local y pruebas de software.

---

## 📌 Descripción del proyecto

**PrestamoLab CTMA** es una aplicación móvil diseñada para facilitar la gestión de préstamos de equipos utilizados en los ambientes de formación.

La aplicación permite registrar usuarios, iniciar sesión, consultar equipos, realizar solicitudes de préstamo, consultar el estado de las solicitudes, cancelar solicitudes, gestionar entregas y devoluciones, registrar incidentes, administrar inventario y laboratorios, aplicar sanciones por devoluciones tardías y consultar métricas relacionadas con los préstamos.

El proyecto también incorpora persistencia local mediante **Room/SQLite**, permitiendo conservar información en el dispositivo y registrar acciones pendientes cuando no existe conexión.

> **Importante:** el proyecto actual no depende de un backend propio para su funcionamiento local. La funcionalidad de sincronización implementada actualmente corresponde a un mecanismo local de gestión de acciones pendientes y no representa una sincronización real con un servidor remoto.

---

# 🎯 Objetivo

Mejorar la trazabilidad y consulta de préstamos de recursos de formación mediante una experiencia móvil.

La aplicación busca centralizar y organizar las operaciones relacionadas con:

- Usuarios.
- Equipos.
- Solicitudes.
- Préstamos.
- Devoluciones.
- Incidentes.
- Mantenimiento.
- Inventario.
- Laboratorios.
- Sanciones.
- Métricas y reportes.

---

# 🏫 Contexto académico

**Entidad:** Servicio Nacional de Aprendizaje - SENA  
**Centro:** Centro de Tecnología de la Manufactura Avanzada - CTMA  
**Programa:** Análisis y Desarrollo de Software - ADSO  
**Proyecto:** PrestamoLab CTMA  
**Plataforma:** Android  
**Lenguaje:** Kotlin

El desarrollo se realiza como parte del proceso formativo y utiliza prácticas de desarrollo de software, control de versiones, historias de usuario, pruebas y documentación.

---

# 📋 Alcance

## Incluido

El proyecto contempla:

- Registro de datos personales.
- Inicio de sesión.
- Gestión de solicitudes de préstamo.
- Consulta de historial.
- Gestión de estados de solicitudes.
- Cancelación de solicitudes.
- Rechazo de solicitudes.
- Entrega física de equipos.
- Devolución de equipos.
- Registro de incidentes.
- Gestión de mantenimiento.
- Gestión de inventario.
- Gestión de laboratorios.
- Persistencia local.
- Gestión de acciones pendientes sin conexión.
- Aplicación de sanciones por devolución tardía.
- Métricas y reportes.

## No incluido actualmente

- Backend propio desplegado.
- Base de datos remota.
- Sincronización real con un servidor.
- Autenticación mediante un servicio externo.
- Integración real con sistemas institucionales.
- Gestión de datos personales reales.

Los datos utilizados para pruebas y demostraciones deben ser **sintéticos o ficticios**.

---

# 👤 Historias de Usuario

El proyecto se organiza mediante las siguientes historias de usuario:

| ID | Historia de Usuario | Funcionalidad |
|---|---|---|
| HU-01 | Registrar datos personales | Permite registrar la información básica del usuario. |
| HU-02 | Login | Permite iniciar sesión dentro de la aplicación. |
| HU-03 | Solicitar préstamo | Permite generar una solicitud de préstamo de un equipo. |
| HU-04 | Historial | Permite consultar las solicitudes y préstamos registrados. |
| HU-05 | Endpoints remotos / ApiService | Contempla la integración de servicios para operaciones remotas. |
| HU-06 | Cancelar solicitud | Permite cancelar una solicitud realizada. |
| HU-07 | Alertas y recordatorios | Permite informar eventos relacionados con las solicitudes y préstamos. |
| HU-08 | Solicitudes pendientes | Permite al administrador gestionar solicitudes pendientes. |
| HU-09 | Entrega física | Permite registrar el inicio del préstamo y la entrega del equipo. |
| HU-10 | Devolución | Permite registrar la devolución de un equipo. |
| HU-11 | Incidentes y faltantes | Permite registrar novedades relacionadas con los equipos. |
| HU-12 | Sanciones | Permite aplicar restricciones por devoluciones tardías. |
| HU-13 | Inventario y laboratorios | Permite administrar equipos, estados, disponibilidad y laboratorios. |
| HU-14 | Funcionamiento offline | Permite conservar información local y registrar acciones pendientes sin conexión. |
| HU-15 | Métricas y reportes | Permite consultar información estadística sobre los préstamos. |

---

# 🧩 Funcionalidades principales

## 🔐 Autenticación

La aplicación cuenta con un mecanismo local de sesión para controlar el acceso del usuario.

Se manejan datos como:

- Correo.
- Rol.
- Estado de sesión.
- Sanciones activas.

La sesión se administra mediante `SessionManager`.

---

## 👤 Gestión de usuarios

La aplicación contempla el registro de usuarios con información como:

- Documento.
- Nombre.
- Correo.
- Contraseña.
- Programa.
- Ficha.
- Rol.

Los datos utilizados durante las pruebas deben ser ficticios.

---

## 📦 Gestión de equipos

Cada equipo puede contener:

- ID.
- Nombre.
- Tipo.
- Código.
- Disponibilidad.
- Estado.

Los estados permiten representar diferentes situaciones del equipo, por ejemplo:

- Disponible.
- En préstamo.
- En mantenimiento.
- No disponible.

---

## 📝 Solicitudes de préstamo

El usuario puede realizar solicitudes indicando información como:

- Equipo.
- Fecha de préstamo.
- Fecha de devolución.
- Motivo.
- Estado.

Los estados de una solicitud pueden representar diferentes etapas del proceso:

```text
Solicitada
    ↓
Aprobada
    ↓
En Préstamo
    ↓
Devuelto