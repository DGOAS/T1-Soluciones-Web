# Caso 2 - Billetera Digital

Aplicación web desarrollada con Angular para gestionar una billetera digital, permitiendo registrar recargas y gastos, actualizar el saldo y visualizar el historial de movimientos.

## Tecnologías

- Angular 22
- TypeScript
- Bootstrap 5
- Node.js
- npm

## Estructura

El proyecto está compuesto por dos componentes principales:

- `Billetera`: componente padre encargado de administrar el saldo y los movimientos.
- `NuevoMovimiento`: componente hijo encargado de registrar nuevos movimientos.

La comunicación entre componentes se realiza mediante:

- `input()`: permite enviar el saldo actual desde `Billetera` hacia `NuevoMovimiento`.
- `output()`: permite enviar el nuevo movimiento desde `NuevoMovimiento` hacia `Billetera`.

## Funcionalidades

- Saldo inicial de S/ 250.00.
- Registro de recargas.
- Registro de gastos.
- Actualización automática del saldo.
- Historial de movimientos.
- Movimientos ordenados del más reciente al más antiguo.
- Validación para impedir gastos superiores al saldo disponible.
- Mensaje de validación cuando el gasto supera el saldo.
- Botón de registro deshabilitado cuando el gasto supera el saldo disponible.
- Interfaz desarrollada utilizando Bootstrap.

## Requisitos

- Node.js
- npm
- Angular CLI

## Instalación

Desde la carpeta `Caso2-Angular`, instalar las dependencias del proyecto:

```bash
npm install
```

## Ejecución

Para iniciar la aplicación en modo desarrollo:

```bash
ng serve
```

Luego abrir el siguiente enlace en el navegador:

`http://localhost:4200/`

## Compilación

Para generar la versión compilada del proyecto:

```bash
ng build
```

Los archivos generados se encuentran en la carpeta `dist/`.

## Validaciones

La aplicación valida que:

- El monto registrado sea mayor que cero.
- Un gasto no pueda superar el saldo disponible.
- Cuando un gasto supera el saldo disponible, se muestra un mensaje de error y se deshabilita el botón de registro.

## Historial de movimientos

Los movimientos registrados se muestran en la sección de historial y se ordenan desde el más reciente hasta el más antiguo.

Las recargas se muestran con el signo `+` y los gastos con el signo `-`.

## Componentes

### Billetera

Es el componente padre de la aplicación. Se encarga de:

- Mantener el saldo actual.
- Mantener el historial de movimientos.
- Actualizar el saldo después de cada movimiento.
- Recibir los movimientos registrados por el componente `NuevoMovimiento`.
- Mostrar el historial de movimientos.

### NuevoMovimiento

Es el componente hijo encargado de registrar nuevos movimientos. Se encarga de:

- Seleccionar el tipo de movimiento.
- Ingresar el monto.
- Recibir el saldo actual mediante `input()`.
- Validar los gastos que superen el saldo disponible.
- Enviar el nuevo movimiento al componente `Billetera` mediante `output()`.

## Comandos principales

Instalar dependencias:

```bash
npm install
```

Ejecutar el proyecto:

```bash
ng serve
```

Compilar el proyecto:

```bash
ng build
```