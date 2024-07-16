# Adventura
### Unforgettable travel experiences start with Adventura
This progect was made in cooperate with frontend developer
[Adventura](https://neotourakunov.netlify.app/)

## Description

Adventura is an optimized app designed for convenient tour booking. Offering a range of categories, including a comprehensive list of tours and popular options, Adventura provides a convenient platform for travelers. Whether exploring destinations through breathtaking photos, delving into detailed tour descriptions, or managing bookings through a query backend, Adventura is the way to seamless and unforgettable travel experiences.

## Build with
- [Spring Boot](https://spring.io/projects/spring-boot) - Server framework
- [Spring Security](https://spring.io/projects/spring-security) - Security
- [Maven](https://maven.apache.org/) - Build and dependency management
- [PostgreSQL](https://www.postgresql.org/) - Database
- [Cloudinary](https://cloudinary.com/) - Image Storege
- [Swagger](https://swagger.io/) - Documentation

## Entity relationship diagram

![ERD](https://github.com/nastenka-ooops/Adventura/blob/main/diagrams/ERD.png)

## Installation

To install the application, follow these steps:

1. Clone the repository:
   ```
   git clone https://github.com/nastenka-ooops/NeoTour.git
   cd Adventura
   ```

2. If you have Maven installed locally, run:
   ```
   mvn clean install
   ```

   If you do not have Maven installed, run:
   ```
   ./mvnw clean install
   ```

## Testing

To run tests, open a terminal in the root directory and type:
```
mvn clean test
```
All test cases can be found in the `test` directory of the project.

## How To Run

After a successful installation, set the following variables and run the application:

1. Run the application with the required variables:
   ```
   java -jar *.jar --DATABASE_URL=your-database-url (omit 'jdbc:' part of URL) --api_key= --api_secret= --cloud_name= --CLOUDINARY_URL= --DATABASE_URL=
   ```

## How To Use

Visit [Adventura Website](https://neotourakunov.netlify.app/) to explore the site developed in collaboration with the frontend developer.

## Documentation

[Documentation](https://neotour-production-392c.up.railway.app/swagger-ui/index.html)

## Author

- *Developer* - [Brytskaya Anastasia](https://github.com/nastenka-ooops)
- *Designer* - [Adilet](https://github.com/akkunov)
