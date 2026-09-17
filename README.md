# 📱 PréstamoLab CTMA

Aplicación móvil Android para la gestión, seguimiento y trazabilidad de préstamos de equipos y recursos de formación del **Centro de Tecnología de la Manufactura Avanzada (CTMA) del SENA**.

El proyecto es desarrollado como parte del proceso formativo del programa **Análisis y Desarrollo de Software (ADSO)** y busca digitalizar el proceso de consulta de equipos, solicitudes, préstamos, devoluciones y seguimiento de recursos.

---

## 📋 Tabla de contenido

- [Descripción](#-descripción)
- [Objetivo](#-objetivo)
- [Contexto académico](#-contexto-académico)
- [Características](#-características)
- [Flujo principal](#-flujo-principal)
- [Arquitectura](#-arquitectura)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Persistencia de datos](#-persistencia-de-datos)
- [Funcionamiento sin conexión](#-funcionamiento-sin-conexión)
- [Interfaz de usuario](#-interfaz-de-usuario)
- [Tecnologías utilizadas](#-tecnologías-utilizadas)
- [Requisitos](#-requisitos)
- [Instalación](#-instalación)
- [Ejecución](#-ejecución)
- [Flujo básico de uso](#-flujo-básico-de-uso)
- [Historias de usuario](#-historias-de-usuario)
- [Flujo de un préstamo](#-flujo-de-un-préstamo)
- [Backend y sincronización](#-backend-y-sincronización)
- [Seguridad y datos](#-seguridad-y-datos)
- [Control de versiones](#-control-de-versiones)
- [Pruebas manuales](#-pruebas-manuales)
- [Estado actual del proyecto](#-estado-actual-del-proyecto)
- [Trabajo futuro](#-trabajo-futuro)
- [Propósito del proyecto](#-propósito-del-proyecto)
- [Autor](#-autor)
- [Licencia](#-licencia)
- [Repositorio](#-repositorio)

---

## 📌 Descripción

**PréstamoLab CTMA** es una aplicación móvil desarrollada para apoyar la gestión de los equipos utilizados en los ambientes de formación del **Centro de Tecnología de la Manufactura Avanzada**.

La aplicación permite a los usuarios:

- Consultar equipos disponibles.
- Consultar información detallada de los equipos.
- Generar solicitudes de préstamo.
- Consultar solicitudes.
- Consultar préstamos.
- Registrar devoluciones.
- Consultar laboratorios.
- Consultar métricas.
- Recibir notificaciones y recordatorios.
- Gestionar la sesión de usuario.

El proyecto utiliza una arquitectura organizada por responsabilidades, separando la interfaz de usuario, la lógica de presentación, los repositorios y el acceso a datos.

---

## 🎯 Objetivo

Desarrollar una solución móvil que permita mejorar la organización, consulta y trazabilidad del proceso de préstamo de equipos dentro de los ambientes de formación.

La aplicación busca centralizar información relacionada con:

- Usuarios.
- Equipos.
- Solicitudes.
- Préstamos.
- Devoluciones.
- Laboratorios.
- Incidentes.
- Inventario.
- Métricas.
- Notificaciones.

---

## 🏫 Contexto académico

| Información | Detalle |
|---|---|
| Institución | Servicio Nacional de Aprendizaje - SENA |
| Centro | Centro de Tecnología de la Manufactura Avanzada - CTMA |
| Programa | Análisis y Desarrollo de Software - ADSO |
| Tipo de proyecto | Aplicación móvil Android |
| Lenguaje principal | Kotlin |
| Interfaz | Jetpack Compose |
| Base de datos local | Room / SQLite |
| Control de versiones | Git / GitHub |
| Rama de desarrollo | `FABIAN-00906` |

---

# 🚀 Características

## 🔐 Autenticación

La aplicación cuenta con un sistema de acceso local que permite:

- Registro de usuarios.
- Inicio de sesión.
- Validación del correo institucional.
- Gestión de contraseña.
- Manejo de sesión activa.
- Cierre de sesión.
- Redirección según el estado de la sesión.

El estado de la sesión es administrado mediante `SessionManager`.

---

## 👤 Registro de usuarios

El registro contempla información como:

- Documento.
- Nombre completo.
- Correo institucional.
- Contraseña.
- Programa de formación.
- Ficha.
- Rol.

Actualmente los usuarios registrados utilizan el rol:

```text
Aprendiz
```

---

## 📦 Catálogo de equipos

El usuario puede consultar los equipos registrados en el sistema.

Cada equipo puede mostrar información como:

- Nombre.
- Tipo.
- Estado.
- Disponibilidad.

Los equipos disponibles pueden seleccionarse para consultar su información y realizar una solicitud de préstamo.

---

## 🔎 Detalle de equipos

Cada equipo cuenta con una pantalla de detalle donde se puede consultar:

- Nombre.
- Tipo.
- Estado.
- Disponibilidad.

Cuando el equipo está disponible se habilita la opción:

```text
Solicitar préstamo
```

---

## 📝 Solicitudes de préstamo

Los usuarios pueden generar solicitudes indicando:

- Equipo.
- Fecha de préstamo.
- Fecha de devolución.
- Motivo de la solicitud.

El sistema valida:

- Disponibilidad del equipo.
- Selección de fecha de préstamo.
- Selección de fecha de devolución.
- Orden correcto de las fechas.
- Motivo obligatorio.

Las solicitudes inicialmente se registran como:

```text
Pendiente
```

---

## 🔄 Estados de las solicitudes

El flujo principal contempla:

```text
Pendiente
    ↓
Aprobada
    ↓
En Préstamo
    ↓
Devuelto
```

También pueden existir los estados:

```text
Rechazada
Cancelada
```

Estos estados permiten realizar seguimiento al ciclo de vida de una solicitud.

---

## 📋 Gestión de solicitudes

La pantalla de solicitudes permite consultar y gestionar las solicitudes registradas.

Entre las acciones contempladas se encuentran:

- Aprobar solicitud.
- Rechazar solicitud.
- Cancelar solicitud.
- Registrar entrega del equipo.
- Consultar estado.
- Consultar fechas.
- Consultar motivo.

---

## 🎒 Mis préstamos

El módulo **Mis préstamos** permite consultar el historial de préstamos del usuario.

La información puede incluir:

- Equipo.
- Tipo de equipo.
- Fecha de préstamo.
- Fecha de devolución.
- Motivo.
- Estado.

Cuando existe un préstamo activo también se muestra un contador del tiempo restante para la devolución.

Además, el usuario puede iniciar el proceso de devolución del equipo.

---

## ↩️ Devolución

El sistema permite registrar la devolución de equipos que se encuentran en préstamo.

Antes de realizar la devolución se muestra una confirmación para evitar acciones accidentales.

Posteriormente, el estado del préstamo cambia a:

```text
Devuelto
```

---

## 🧪 Laboratorios

El proyecto contempla un módulo dedicado a los laboratorios y ambientes de formación.

Este módulo permite organizar información relacionada con los espacios donde se encuentran los equipos disponibles para préstamo.

---

## 📊 Métricas y reportes

La aplicación incluye un módulo de métricas para visualizar información resumida sobre el funcionamiento del sistema.

Entre las métricas contempladas se encuentran:

- Total de solicitudes.
- Solicitudes pendientes.
- Solicitudes aprobadas.
- Solicitudes rechazadas.
- Solicitudes canceladas.
- Préstamos activos.
- Equipos devueltos.

La información se presenta mediante tarjetas estadísticas dentro de la interfaz.

---

## 🔔 Notificaciones

El proyecto incluye componentes relacionados con notificaciones y recordatorios.

La aplicación contempla la creación de un canal de notificaciones mediante:

```text
NotificationHelper
```

También existe un componente relacionado con recordatorios:

```text
ReminderReceiver
```

Estos componentes permiten preparar la aplicación para notificaciones relacionadas con eventos de préstamos y devoluciones.

---

# 🏗️ Arquitectura

El proyecto utiliza una estructura organizada por responsabilidades.

La separación general puede representarse de la siguiente manera:

```text
UI
│
├── Pantallas
├── Navegación
└── Theme
        │
        ▼
   ViewModel
        │
        ▼
   Repository
        │
        ▼
      Data
        │
        ├── Room
        ├── DAO
        └── Entity
```

---

## 🧩 Patrón MVVM

La aplicación utiliza conceptos asociados con **MVVM (Model-View-ViewModel)**.

### Model

Representa los datos y entidades utilizadas por la aplicación.

Ejemplos:

```text
Usuario
Equipo
Solicitud
Laboratorio
Incidente
```

### View

Las pantallas desarrolladas con Jetpack Compose representan la interfaz de usuario.

Entre las principales pantallas se encuentran:

```text
LoginScreen
RegistroScreen
HomeScreen
CatalogoScreen
EquiposScreen
DetalleEquipoScreen
SolicitudScreen
SolicitudesScreen
MisPrestamosScreen
LaboratoriosScreen
MetricasScreen
```

### ViewModel

Gestiona el estado y la lógica relacionada con la presentación.

Ejemplo:

```text
LoginViewModel
```

---

# 🗂️ Estructura del proyecto

La estructura principal del proyecto se organiza de manera similar a:

```text
PrestamoLabCTMA/
│
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── ctma/
│   │       │           └── prestamolabctma/
│   │       │
│   │       └── res/
│   │
│   └── build.gradle.kts
│
├── gradle/
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

Dentro del código fuente:

```text
com.ctma.prestamolabctma
│
├── data/
│   ├── local/
│   │   ├── entity/
│   │   ├── dao/
│   │   └── AppDatabase
│   │
│   ├── repository/
│   └── session/
│
├── model/
│
├── navigation/
│
├── notification/
│
├── ui/
│   ├── catalogo/
│   ├── equipo/
│   ├── home/
│   ├── laboratorio/
│   ├── login/
│   ├── metricas/
│   ├── misprestamos/
│   ├── registro/
│   ├── solicitud/
│   └── theme/
│
└── viewmodel/
```

---

# 💾 Persistencia de datos

La aplicación utiliza **Room** como capa de persistencia sobre **SQLite**.

La estructura general es:

```text
Entity
   ↓
 DAO
   ↓
Room Database
   ↓
Repository
   ↓
ViewModel / UI
```

Entre los componentes utilizados se encuentran:

```text
AppDatabase
UsuarioEntity
UsuarioDao
```

Esto permite almacenar información localmente en el dispositivo.

---

# 📡 Funcionamiento sin conexión

El proyecto contempla un mecanismo local para manejar acciones pendientes cuando no existe conectividad.

La aplicación puede conservar información localmente y registrar determinadas operaciones pendientes para su posterior procesamiento.

> **Importante:** el funcionamiento offline implementado actualmente no debe interpretarse como sincronización completa con un servidor remoto. La sincronización real con un backend requiere un servicio remoto y una estrategia de sincronización.

---

# 🎨 Interfaz de usuario

La interfaz está desarrollada utilizando:

```text
Jetpack Compose + Material 3
```

El diseño utiliza:

- `MaterialTheme`.
- Colores personalizados.
- Tipografía personalizada.
- Tarjetas.
- Botones.
- Iconos Material.
- Campos de entrada.
- Diálogos.
- `DatePicker`.
- Indicadores de estado.
- Diseño adaptable a diferentes tamaños de pantalla.

---

## 🏠 Pantalla principal

El Home presenta:

- Identidad de PréstamoLab CTMA.
- Mensaje de bienvenida.
- Accesos rápidos.
- Catálogo.
- Equipos.
- Solicitudes.
- Préstamos.
- Laboratorios.
- Métricas.
- Cierre de sesión.

Los accesos se presentan mediante tarjetas con iconos para facilitar la navegación.

---

## 🎨 Sistema de colores

El proyecto cuenta con un sistema de colores personalizado para representar la identidad visual de la aplicación.

Se manejan dos esquemas:

```text
Light Theme
Dark Theme
```

Los colores y estilos se centralizan principalmente en:

```text
ui/theme/Color.kt
ui/theme/Theme.kt
ui/theme/Type.kt
```

Esto permite mantener una apariencia consistente entre las diferentes pantallas.

---

# 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Kotlin | Lenguaje principal |
| Android | Plataforma |
| Jetpack Compose | Interfaz de usuario |
| Material 3 | Componentes visuales |
| Navigation Compose | Navegación |
| Room | Persistencia local |
| SQLite | Base de datos local |
| ViewModel | Gestión de estado |
| Coroutines | Operaciones asíncronas |
| Git | Control de versiones |
| GitHub | Repositorio y colaboración |
| Gradle Kotlin DSL | Configuración y construcción |

---

# 📋 Requisitos

Para ejecutar el proyecto se recomienda contar con:

- Android Studio.
- JDK compatible con la versión de Android Gradle Plugin utilizada.
- Android SDK.
- Gradle mediante el Gradle Wrapper incluido en el proyecto.
- Un emulador Android o dispositivo físico.

Se recomienda utilizar una versión reciente y compatible de Android Studio con la configuración actual del proyecto.

---

# ⚙️ Instalación

## 1. Clonar el repositorio

```bash
git clone https://github.com/Ficha-3223875/PrestamoLab_CTMA.git
```

Entrar al proyecto:

```bash
cd PrestamoLab_CTMA
```

## 2. Cambiar a la rama de desarrollo

```bash
git checkout FABIAN-00906
```

## 3. Abrir el proyecto

Abrir la carpeta del proyecto:

```text
PrestamoLabCTMA
```

desde Android Studio.

Android Studio descargará automáticamente las dependencias configuradas mediante Gradle.

## 4. Sincronizar Gradle

En Android Studio:

```text
File → Sync Project with Gradle Files
```

Esperar a que finalice la sincronización.

---

# ▶️ Ejecución

La aplicación puede ejecutarse mediante Android Studio.

Seleccionar:

```text
app
```

y posteriormente seleccionar:

- Un emulador Android.

o

- Un dispositivo físico conectado.

Finalmente presionar:

```text
Run ▶
```

---

# 🔑 Flujo básico de uso

El flujo principal de la aplicación es:

```text
Inicio
   ↓
 Login
   │
   ├── Registrarse
   │       ↓
   │   Crear cuenta
   │
   ↓
 Home
   │
   ├── Catálogo
   │      ↓
   │   Detalle del equipo
   │      ↓
   │   Nueva solicitud
   │
   ├── Solicitudes
   │
   ├── Mis préstamos
   │      ↓
   │   Devolución
   │
   ├── Equipos
   │
   ├── Laboratorios
   │
   ├── Métricas
   │
   └── Cerrar sesión
           ↓
         Login
```

---

# 👥 Historias de usuario

El desarrollo del proyecto se organiza mediante historias de usuario relacionadas con el proceso de préstamo.

| ID | Historia | Descripción |
|---|---|---|
| HU-01 | Registro | Registrar información básica del usuario. |
| HU-02 | Inicio de sesión | Permitir acceso mediante credenciales. |
| HU-03 | Catálogo | Consultar equipos disponibles. |
| HU-04 | Solicitud | Solicitar el préstamo de un equipo. |
| HU-05 | Historial | Consultar solicitudes y préstamos. |
| HU-06 | Cancelación | Cancelar solicitudes cuando corresponda. |
| HU-07 | Gestión de solicitudes | Administrar solicitudes pendientes. |
| HU-08 | Aprobación | Aprobar o rechazar solicitudes. |
| HU-09 | Entrega | Registrar la entrega del equipo. |
| HU-10 | Devolución | Registrar la devolución de equipos. |
| HU-11 | Laboratorios | Consultar información de ambientes y laboratorios. |
| HU-12 | Notificaciones | Gestionar avisos y recordatorios. |
| HU-13 | Métricas | Consultar estadísticas del sistema. |
| HU-14 | Offline | Conservar información y acciones pendientes localmente. |
| HU-15 | Sesión | Mantener y cerrar la sesión del usuario. |

---

# 🔄 Flujo de un préstamo

El ciclo principal de un préstamo puede representarse como:

```text
┌──────────────┐
│    Usuario   │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│   Consulta   │
│   equipos    │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│   Solicitud  │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│   Pendiente  │
└──────┬───────┘
       │
   ┌───┴────┐
   ▼        ▼
Aprobada  Rechazada
   │
   ▼
┌──────────────┐
│ En Préstamo  │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│   Devuelto   │
└──────────────┘
```

---

# 🌐 Backend y sincronización

Actualmente el proyecto está orientado principalmente al funcionamiento local mediante **Room/SQLite**.

Aunque el proyecto contempla componentes relacionados con servicios y repositorios, actualmente no se debe considerar que exista un backend institucional desplegado como requisito para ejecutar la aplicación local.

La arquitectura actual puede representarse como:

```text
Aplicación Android
       │
       ▼
Persistencia local
       │
       ▼
Room / SQLite
```

Una implementación futura podría incorporar:

```text
Aplicación Android
       │
       ▼
     API REST
       │
       ▼
     Backend
       │
       ▼
Base de datos remota
```

Esto permitiría implementar sincronización multiusuario y centralización de la información.

---

# 🔒 Seguridad y datos

El proyecto se encuentra en contexto académico y de desarrollo.

Por lo tanto, se recomienda:

- No utilizar credenciales reales.
- No publicar contraseñas.
- No incluir información personal real en el repositorio.
- No subir archivos de configuración sensibles.
- Utilizar datos ficticios para pruebas.
- No almacenar claves API directamente en el código fuente.

---

# 🌿 Control de versiones

El proyecto utiliza **Git y GitHub** para gestionar el código fuente.

La rama de desarrollo utilizada es:

```text
FABIAN-00906
```

Los cambios se gestionan mediante commits descriptivos.

Ejemplo:

```bash
git add .
git commit -m "feat(ui): rediseñar interfaz de PrestamoLab CTMA"
git push origin FABIAN-00906
```

Para cambios relacionados con autenticación:

```bash
git commit -m "feat(auth): agregar cierre de sesión"
```

---

# 📌 Convención de commits

Se recomienda utilizar **Conventional Commits**:

```text
feat:
fix:
docs:
refactor:
style:
test:
chore:
```

Ejemplos:

```text
feat(auth): agregar cierre de sesión
feat(ui): rediseñar pantalla principal
fix(login): corregir validación de acceso
fix(solicitud): corregir selección de fechas
docs: actualizar README
refactor(repository): organizar acceso a datos
```

---

# 🧪 Pruebas manuales

Antes de considerar una versión funcional se recomienda comprobar:

## Registro

- [ ] Registro con datos completos.
- [ ] Validación de campos vacíos.
- [ ] Validación del correo institucional.
- [ ] Creación correcta del usuario.

## Login

- [ ] Credenciales correctas.
- [ ] Credenciales incorrectas.
- [ ] Estado de carga.
- [ ] Mensajes de error.
- [ ] Persistencia de sesión.

## Equipos

- [ ] Visualización del catálogo.
- [ ] Consulta de detalles.
- [ ] Identificación de disponibilidad.
- [ ] Bloqueo de solicitud para equipos no disponibles.

## Solicitudes

- [ ] Selección de fecha.
- [ ] Validación de fechas.
- [ ] Motivo obligatorio.
- [ ] Creación de solicitud.
- [ ] Cancelación.
- [ ] Aprobación.
- [ ] Rechazo.

## Préstamos

- [ ] Visualización de préstamos.
- [ ] Estado del préstamo.
- [ ] Contador de devolución.
- [ ] Confirmación de devolución.
- [ ] Cambio de estado.

## Sesión

- [ ] Cerrar sesión.
- [ ] Regresar al Login.
- [ ] Evitar acceso al Home después de cerrar sesión.

---

# 🚧 Estado actual del proyecto

## ✅ Implementado

- Aplicación Android.
- Interfaz Jetpack Compose.
- Material 3.
- Login.
- Registro.
- Gestión de sesión.
- Cierre de sesión.
- Home rediseñado.
- Catálogo.
- Equipos.
- Detalle de equipos.
- Solicitudes.
- Aprobación y rechazo.
- Cancelación de solicitudes.
- Gestión de préstamos.
- Devoluciones.
- Laboratorios.
- Métricas.
- Room / SQLite.
- DAO y entidades.
- Repositorios.
- Notificaciones.
- Recordatorios.
- Manejo local de acciones pendientes.
- Tema claro y oscuro.

## 🔄 En evolución

- Backend remoto.
- Sincronización real.
- Autenticación remota.
- Sincronización multiusuario.
- Integración con servicios institucionales.
- Pruebas automatizadas adicionales.
- Mejoras de seguridad.

---

# 🔮 Trabajo futuro

Entre las posibles mejoras del proyecto se encuentran:

- Implementar un backend REST.
- Incorporar autenticación segura mediante servidor.
- Implementar sincronización entre dispositivos.
- Agregar roles y permisos más detallados.
- Incorporar códigos QR o códigos de barras para identificar equipos.
- Mejorar el sistema de notificaciones.
- Incorporar reportes exportables.
- Implementar pruebas unitarias y de interfaz.
- Incorporar registro completo de auditoría.
- Implementar control centralizado del inventario.
- Integrar una base de datos remota.
- Mejorar la gestión de mantenimiento e incidentes.

---

# 📚 Propósito del proyecto

**PréstamoLab CTMA** se desarrolla como una solución académica orientada a aplicar conocimientos de:

- Desarrollo móvil.
- Kotlin.
- Android.
- Jetpack Compose.
- Arquitectura de software.
- Bases de datos.
- Persistencia local.
- Gestión de estados.
- Navegación.
- Control de versiones.
- Metodologías ágiles.
- Pruebas de software.
- Documentación técnica.

El proyecto busca representar un caso de uso realista de digitalización del proceso de préstamo de recursos de formación.

---

# 👨‍💻 Autor

**Fabián Andrés Marín Montoya**

**Programa:**  
Análisis y Desarrollo de Software (ADSO)

**Institución:**  
SENA - CTMA

---

# 📄 Licencia

Proyecto desarrollado con fines académicos y formativos dentro del programa de **Análisis y Desarrollo de Software del SENA**.

No se deben utilizar datos personales reales ni información institucional sensible para pruebas o demostraciones.

---

# 🔗 Repositorio

Repositorio del proyecto:

```text
https://github.com/Ficha-3223875/PrestamoLab_CTMA
```

Rama de desarrollo:

```text
FABIAN-00906
```

---