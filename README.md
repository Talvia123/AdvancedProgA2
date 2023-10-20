# AdvancedProgA2

README FILE

Social Media Dashboard Application

Overview

The Social Media Dashboard Application is a JavaFX-based application that provides a user interface for managing social media posts. It includes features for user authentication, profile management, and various operations on social media posts.

Features

•	User Authentication.

•	Allows users to log in with their username and password.

•	Supports validation of user credentials against a database.

User Profile Management

•	Enables users to view and edit their profile information.

•	Supports updating the user's first name, last name, username, and password.

Dashboard Options

•	Add a Social Media Post

•	Allows users to create and post content on their social media accounts.

•	Delete an Existing Social Media Post

•	Enables users to remove previously posted content.

•	Retrieve a Social Media Post

•	Allows users to view details of a specific social media post.

•	Retrieve the Top N Posts with Most Likes

•	Provides a list of the most popular posts based on likes.

•	Retrieve the Top N Posts with Most Shares

•	Provides a list of the most shared posts.

•	Export a Post to a File Based on Post ID

•	Allows users to save a specific post to a file.

•	Show Shares Distribution Pie Chart (VIP Feature)

•	Displays a graphical representation of post shares.

•	Bulk Import Posts (VIP Feature)

•	Allows VIP users to import multiple posts at once.


IDE USED

Eclipse 

JRE System Library – zulu 21

JavaFX Version 17.0.8 

Database – SQLite 


How to run this code


To run the provided Java code in Eclipse, follow these steps:

Open Eclipse:

Launch the Eclipse IDE.

Download the project 

Go to File > Import 

Browse name of the project (e.g., "DAHP").

Click "Finish".

Packages should be the in the project. 

Add Classes:

Copy and paste the respective Java classes into their corresponding packages. Ensure that the package declarations at the top of each file match the folder structure.

Configure JavaFX:

If you haven't set up JavaFX in your Eclipse environment, you'll need to do so. Follow this link to set up JavaFX - https://gluonhq.com/products/javafx/

Set Up Database Connection

There is already a file called ‘dahp.db’

Open HeidiSql

Select network type as SQLite in HeidiSQL

Choose database file from HeidiSQL

Open it

Create table using queries or using GUI


Connecting Databse:

In eclipse import a library called sqlite-jdbc-3.7.2.jar

In the class databaseUtil > package dbConnection 

Resolve Dependencies:

If there are any missing imports, Eclipse will show errors. You may need to add the necessary JavaFX libraries to your project's build path. 

Right-click on your project, go to Build Path > Add Libraries, and select "User Library". Then, create a new user library and add the JavaFX libraries.

Run the Code:

Find the class with the main method. 

Right-click on the class and select Run As > Java Application.

Interact with the Application:

Small Description of OO Design 

The Java code snippets showcase an Object-Oriented (OO) design, which is a paradigm focused on organizing code around objects representing real-world entities. Here's a short description of the OO design based on the code:

Classes and Objects:

The code defines several classes, each representing a distinct entity or component of the application, such as EditProfilePage, LoginPage, RegistrationPage, and DashboardPage.
Objects of these classes are created and utilized to perform specific tasks, such as managing user profiles, handling login/authentication, and displaying the dashboard interface.

Encapsulation:

Each class encapsulates related functionalities, ensuring that the internal workings are hidden from the outside world. For example, the EditProfilePage class encapsulates methods for updating user profiles.

Inheritance:

The code doesn't explicitly showcase inheritance, but it's a common OO principle. Inheritance allows classes to inherit properties and behaviors from a parent class, promoting code reusability.
Polymorphism:

The code doesn't explicitly showcase polymorphism, but it's another key concept in OO design. Polymorphism allows objects of different types to be treated uniformly, enabling flexibility in method invocation.

Abstraction:

The classes provide high-level interfaces for various functionalities. For instance, EditProfilePage abstracts the process of updating user profiles, and LoginPage abstracts user authentication.
Composition:

The code utilizes composition to create complex structures from simpler components. For example, the DashboardPage class composes various UI elements (labels, buttons, etc.) to construct the dashboard interface.


Dependency Injection:

The DashboardPage class accepts parameters like fullName, username, and isVIP in its start method, demonstrating a form of dependency injection. This allows the class to receive external information it needs to function.

Single Responsibility Principle (SRP):

Each class appears to have a clear and specific responsibility. For example, EditProfilePage focuses on managing user profile updates, LoginPage handles authentication, and so on.

Error/Exception Handling:

The code includes exception handling to manage potential errors that may occur during database operations or other critical tasks.

UI and Business Logic Separation:

The code demonstrates a clear separation between user interface components (JavaFX elements) and the underlying business logic (model classes like EditProfilePage, LoginPage, etc.).

Comments and Documentation:

The code includes comments that provide explanatory information about the purpose and functionality of various methods and classes, aiding in code understanding and maintenance.
Overall, the OO design in this code emphasizes modularity, encapsulation, and separation of concerns, which are key principles in creating maintainable and extensible software systems.



