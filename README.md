# Memento Estoico v3

App Android nativa en Kotlin con widget de frases estoicas.

## Compilar el APK desde GitHub

Este repositorio incluye un workflow de GitHub Actions que compila la app en la nube con:

- JDK 17
- Gradle 8.13
- Android Gradle Plugin 8.13.0
- compileSdk 36

### Desde el teléfono

1. Crea un repositorio nuevo en GitHub.
2. Sube **el contenido de esta carpeta** a la raíz del repositorio. Debes ver `app`, `.github`, `build.gradle.kts` y `settings.gradle.kts` en la página principal del repo.
3. Abre la pestaña **Actions**.
4. Entra en **Build Android APK**.
5. Pulsa **Run workflow** y confirma **Run workflow**.
6. Espera a que el build termine con una marca verde.
7. Abre la ejecución terminada y baja hasta **Artifacts**.
8. Descarga **MementoEstoico-debug-apk**.
9. GitHub descargará un ZIP; descomprímelo y encontrarás `app-debug.apk`.

También se compila automáticamente cuando haces cambios en las ramas `main` o `master`.

## APK generado

`app/build/outputs/apk/debug/app-debug.apk`

## Nota sobre Gradle Wrapper

El workflow de GitHub instala Gradle 8.13 directamente en el servidor, por lo que no depende del JDK ni del Gradle de tu teléfono.
