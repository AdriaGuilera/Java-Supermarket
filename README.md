# Supermarket Management System
This project implements a comprehensive supermarket management system developed in Java. It follows a three-layer architecture pattern comprising:
- Custom database implementation for data persistence
- Domain layer for business logic
- Interactive presentation layer using Swing

The system optimizes supermarket operations through efficient inventory management, automated ordering, and smart shelf organization algorithms.


## Features

- Product Management (Productes)
  - Add/remove products
  - Set product specifications (max storage, shelf capacity)
  - Manage product similarities
  - View product details

- Shelf Management (Prestatgeries)
  - Create and manage shelves
  - Add/remove products from shelves
  - Fix/unfix products to specific positions
  - Optimize product distribution using backtracking and hill climbing algorithms

- Order Management (Comandes)
  - Create and manage orders
  - Add/remove products from orders
  - Generate automatic orders based on product's stock
  - Execute multiple orders simultaneously

- Cash Register (Caixa)
  - Add products to cart
  - Remove products from cart
  - Process payments
  - View current transaction details

## Technical Details

- Built with Java 22
- Uses Swing for the graphical user interface
- Implements MVC architecture pattern
- Uses JSON for data persistence
- Gradle build system

## Building and Running

To build the project:

```sh
./gradlew build