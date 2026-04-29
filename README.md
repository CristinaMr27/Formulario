# Formulario App

Aplicación Android de gestión de solicitudes (requests) construida con Jetpack Compose y Supabase.

## 🎥 Demo

[Ver video demo](https://everisgroup-my.sharepoint.com/:v:/g/personal/cmoninor_emeal_nttdata_com/IQA8I96Evml_RLG8JK72MumPAYhjA3QMFexpX9aTb7JomNI?e=2az6o6)

## 🚀 Configuración del Proyecto

### Prerrequisitos

- Android Studio (última versión recomendada)
- JDK 11 o superior
- Una cuenta de Supabase

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

   ⚠️ **IMPORTANTE**: El archivo `local.properties` está en `.gitignore` y NO se subirá a GitHub. Esto protege tus credenciales.

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

## 📁 Estructura del Proyecto

```
app/
├── data/
│   ├── model/          # Modelos de datos
│   └── repository/     # Repositorios para acceso a datos
├── di/                 # Módulos de inyección de dependencias
├── domain/
│   └── validation/     # Lógica de validación
├── ui/
│   ├── navigation/     # Configuración de navegación
│   ├── screens/        # Pantallas de la app
│   └── theme/          # Tema y estilos
└── util/               # Utilidades
```

## 🔒 Seguridad

Las credenciales de Supabase se almacenan de forma segura en `local.properties`, que:
- ✅ NO se sube a GitHub (está en `.gitignore`)
- ✅ Se carga en tiempo de compilación
- ✅ No aparece en el código fuente

**Para colaboradores**: Cada desarrollador debe crear su propio archivo `local.properties` usando `local.properties.example` como plantilla.

## 🤝 Contribuir

1. Fork el proyecto
2. Crea tu rama de feature (`git checkout -b feature/AmazingFeature`)
3. Configura tu `local.properties` con tus propias credenciales
4. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
5. Push a la rama (`git push origin feature/AmazingFeature`)
6. Abre un Pull Request

## 📝 Notas

- Nunca compartas tu archivo `local.properties`
- Si accidentalmente expones tus credenciales, regenera inmediatamente tu API key desde Supabase Dashboard
- Considera implementar Row Level Security (RLS) en Supabase para mayor seguridad

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.
