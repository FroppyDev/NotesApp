# NotesApp - Aplicación de Notas para Android

NotesApp es una aplicación de Android sencilla pero funcional, diseñada para ayudar a los usuarios a organizar sus pensamientos, tareas e ideas. 
La aplicación permite crear, editar, eliminar y categorizar notas, proporcionando una experiencia de usuario limpia e intuitiva. 

Este proyecto fue desarrollado como una práctica para solidificar conceptos clave del desarrollo de aplicaciones Android nativas, utilizando Java.

## Caracteristicas principales

•Crear y Editar Notas: Interfaz simple para añadir nuevas notas o modificar las existentes.
•Eliminar Notas: Funcionalidad para borrar notas con un solo toque.
•Gestión de Categorías: Permite a los usuarios crear y asignar categorías a sus notas para una mejor organización.
•Interfaz Limpia y Moderna: Uso de RecyclerView y CardView para una presentación visual agradable y eficiente de las notas.
•Persistencia de Datos: Almacenamiento local de notas y categorías utilizando una base de datos SQLite.
•Arquitectura Sólida: Sigue un patrón de diseño que separa la lógica de negocio (Controller) de la interfaz de usuario (View), mejorando la mantenibilidad del código.

## Instalacion y ejecucion
  1. Copia el repositorio:

   https://github.com/FroppyDev/To-Do-App.git

  2. Abrir en Android Studio

    Selecciona "Clone Repository"

    Pega el url del primer paso

    dale al boton de "clone"

  3. Sincronizar Gradle

    Android Studio detectará todas las dependencias y generará los archivos necesarios.

  4. Ejecutar la app

    Conecta un dispositivo físico o usa un emulador

    Presiona Run ▶ en Android Studio

    Android studio se encargara de instalar y ejecutar la aplicacion en el dispositivo fisico o emulado (Recuerda activar la depuracion USB)

## Estructura del proyecto

com.fic.notesapp/
├── controller/       # Lógica de negocio y acceso a datos
│   ├── CategoryController.java
│   └── NoteController.java
├── db/               # Clases relacionadas con la base de datos│   └── DBHelper.java
├── model/            # Clases de datos (POJOs)
│   ├── category/
│   │   └── Category.java
│   └── note/
│       └── Note.java
└── view/             # Activities, Adapters y ViewHolders
    ├── category/
    │   ├── CategoryAdapter.java
    │   └── CategoryViewHolder.java
    ├── note/
    │   ├── NoteAdapter.java
    │   └── NoteViewHolder.java
    ├── AddNotesActivity.java
    └── MainActivity.java

## Estructura de la base de datos

┌──────────────┐           ┌───────────────────────────┐
│   category    │           │           note            │
├──────────────┤           ├───────────────────────────┤
│ category_id PK│◄─────────│ category_id FK            │
│ category_name │           │ note_id PK                │
└──────────────┘           │ note_title                │
                           │ note_content              │
                           │ created_at                │
                           └───────────────────────────┘

-- Tabla de categorías
CREATE TABLE category (
    category_id INTEGER PRIMARY KEY AUTOINCREMENT,
    category_name TEXT NOT NULL
);

-- Tabla de notas
CREATE TABLE note (
    note_id INTEGER PRIMARY KEY AUTOINCREMENT,
    category_id INTEGER NOT NULL,
    note_title TEXT NOT NULL,
    note_content TEXT,
    created_at TEXT,
    FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE
);

## Interfaces de usuario


![add_category_interface](https://github.com/user-attachments/assets/089a5a80-b991-4458-8278-86279004dfc7)
![note_main_interface](https://github.com/user-attachments/assets/0488abd2-10d4-4798-8ff6-890a954e09b1)
![main_withNote_interface](https://github.com/user-attachments/assets/221cddae-97b5-41cf-a49b-4af44f2e36e7)
![delete_note_interface](https://github.com/user-attachments/assets/b9b2e222-d9d5-423d-b661-d54b83e91fe8)
![delete_category_interface](https://github.com/user-attachments/assets/cdc5bc8b-b512-4001-8c59-98f76bd7a4ed)
![add_note_interface](https://github.com/user-attachments/assets/ad98bc4e-e186-4cc3-a00b-ce50ea6b0b38)
