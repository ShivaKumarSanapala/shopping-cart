# Shopping Cart Assignment

## Project Description
This project implements a simple shopping cart system for an e-commerce website. The assignment aims to create a shopping cart that allows users to add products, calculate the cart state (subtotal, tax, and total), and generate an invoice.

## Instructions
1. Clone this repository on your local machine.
2. Open the project in your preferred IDE.
3. Build the project using Gradle by running the following command in the project root directory:
   * `./gradlew build`
   This will resolve any dependencies and compile the source code.
4. Run the test cases by executing the following command:
   * `./gradlew test`
   This will run the test cases written in the ShoppingCartTest.java and PricingServiceTest.java files located in the src/test/java directory. You can review the test results in the console output.
5. Run the application by executing the following command:
   * `./gradlew run` This will run the Main.java file located in the src/main/java directory, demonstrating the usage of the shopping cart and generating an invoice.
6. Or Better Run this single command to build, test and run the project.
   *  `./gradlew` (or `./gradlew buildTestAndRun`)
7. Review the console output to see the generated invoice.


## File Structure

````
├── src
│   └── main
│       └── java
│           ├── Main.java: The main entry point of the application. It demonstrates the usage of the shopping cart and generates an invoice.
│           ├── cart
│           │   ├── CartItem.java: Represents an item in the shopping cart.
│           │   ├── Invoice.java: Represents an invoice generated from the shopping cart.
│           │   ├── ShoppingCart.java: Manages the shopping cart and provides methods to add/remove items and generate an invoice.
│           │   └── User.java: Represents a user/customer of the shopping cart.
│           ├── exceptions
│           │   ├── CustomHttpRequestException.java: Custom exception class for HTTP request errors.
│           │   └── ProductDetailsException.java: Custom exception class for product details errors.
│           └── services
│               ├── CustomHttpRequest.java: Handles HTTP requests to the product API.
│               └── PricingService.java: Provides pricing calculations for the shopping cart.
└── build.gradle: Gradle build file.
````
## Code Documentation
The code is self-explanatory and follows the principles of simplicity and readability. Important functions and classes are documented with comments.

## Input/Output Format
The assignment expects the user to interact with the code by running the `Main.java` file. The expected output is the generated invoice, which includes details such as the invoice number, date, customer information, items in the cart, subtotal, tax, and total.

## Usage Examples
Example inputs- Edit the main class if needed.
```
shoppingCart.addItem("cornflakes");
shoppingCart.addItem("cornflakes", 1);
shoppingCart.removeItemFromCart("cornflakes");

shoppingCart.addItem("cornflakes", 1);
shoppingCart.addItem("cornflakes", 1);
shoppingCart.addItem("cornflakes", 1);
shoppingCart.removeItemFromCart("cornflakes", 1);

shoppingCart.addItem("weetabix", 1);
// invalid product
shoppingCart.addItem("dairy_milk_chocolate", 3);
```

Expected output:
````
-----YOUR INVOICE-----

Invoice Number: INV-563711
Date: 2023-07-02
Customer: John Doe
Email: john.doe@example.com
Items:
Cart contains 2 x cornflakes (Price: 2.52)
Cart contains 1 x weetabix (Price: 9.98)
Subtotal = 15.02
Tax = 1.88
Total = 16.90

-----END OF INVOICE-----

````


## Assumptions
- The product details are obtained from a product API specified in the assignment.
- The product API's base URL is `https://equalexperts.github.io/`.
- The product API provides the product details in JSON format.
- The product API offers a `GET /backend-take-home-test-data/{product}.json` endpoint to retrieve the details of a specific product.

## Known Issues or Limitations
None known at the moment.

