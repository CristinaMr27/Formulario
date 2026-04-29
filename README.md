# Formulario App

Aplicación Android de gestión de solicitudes (requests) construida con Jetpack Compose y Supabase.

## 🎥 Demo

[Ver video demo](https://everisgroup-my.sharepoint.com/:v:/g/personal/cmoninor_emeal_nttdata_com/IQA8I96Evml_RLG8JK72MumPAYhjA3QMFexpX9aTb7JomNI?e=2az6o6)

### Configuración de Supabase

1. **Crea un proyecto en Supabase**
   - Ve a [Supabase Dashboard](https://supabase.com/dashboard)
   - Crea un nuevo proyecto

2. **Obtén tus credenciales**
   - Ve a `Settings` > `API` en tu proyecto de Supabase
   - Copia la `URL` del proyecto
   - Copia la `anon/public key`

3. **Configura local.properties**
   ```bash
   # Copia el archivo de ejemplo
   cp local.properties.example local.properties
   ```
   
   Luego edita `local.properties` y reemplaza los valores:
   ```properties
   SUPABASE_URL=tu_url_de_supabase
   SUPABASE_KEY=tu_clave_anon_de_supabase
   ```

   ⚠️ **IMPORTANTE**: El archivo `local.properties` está en `.gitignore` y NO se subirá a GitHub.

### Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/CristinaMr27/Formulario.git
   cd Formulario
   ```

2. Configura `local.properties` según las instrucciones anteriores

3. Abre el proyecto en Android Studio

4. Sincroniza Gradle

5. Ejecuta la aplicación

## 🏗️ Arquitectura

- **UI**: Jetpack Compose con Material 3
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Inyección de dependencias**: Dagger Hilt
- **Base de datos**: Supabase (PostgreSQL)
- **Navegación**: Jetpack Navigation Compose
