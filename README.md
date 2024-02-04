# Library Register
#### Video Demo: https://youtu.be/_WtBAtyy_7M
#### Author: Piotr Szumański
#### Description:
Library register is a desktop application created in Java, using: 

    -Spring framework, to connect to database
    -MySQL database, to store data. Container runs locally on Docker
    -JavaFx, to design UI with Scenebuilder

What it can do:

    -Add book/author/reader
    -Remove book/author/reader
    -Search database by selected criteria
    -Filter database by selected criterias
    -Sort table by selected value
    -Lend book to a reader
    -Return book
    -Lengthen return date of a book
    -Pay penalty for overdue books
    
    -Load database
    -Save changes to database

    qol:
    -Simulate passing of time (to test features)
    -Change theme (light/dark mode)
    -Change langauge (English/Polish, my native language)
    -Show notifications when major actions taken
    -Confirm exiting the application, prompts to save changes


Project structure:

    -main
        -java.com.pszumanski.libraryregister
            -application
                -Contains a file responsible for running the application, set-ups necessities.
            -data
                -factories
                    -Contains classes responsbile for creating new objects, makes sure IDs are correct
                -managers
                    -Contains classes responsible for managing objects (books, authors and readers). This is responsible for changes to objects, searcing and obtaining important information.
                    -Contains FileManager which is responsible for loading from/saving to database.
                    -Contains PenaltyManager which is responsible for applying penalties to readers with overdue books.
                    -Contains TimeManager which simulates passing of time
                -objects
                    -Contains data classes (books, authors, readers). They only store information.
                -Repositories
                    -Contains classes of JpaRepositories which enables working with database, and proper automatic db set-up
            -strategy
                -Containg packages with Strategy Design Pattern. It enables dynamic search and filtering.
            -ui
                -controllers
                    -Contains classes which handle loading proper information to the view, validating proper input, and dynamic refreshing
                -utils
                    -DialogUtils is responsible for pop-up alerts (exit, error, info)
                    -NotificationUtils is responsible for notifications
                    -FxmlUtils makes loading views and getting proper text from language bundles easier
        -resources
            -bundles
                -Contains files with all application text in proper languages
            -images
                -Contains few graphics (all opensource or self-created)
            -stylesheets
                -Contains css stylesheets
            -views
                -Contains JavaFx files with scenes
            -application.yml, data.sql, docker-compose.yml
                -Responsible for connecting to db

During creation of the project I've learned quite a lot. Here are some highlights that I can recall:
    
    -I learned Java Stream API, reworking whole searching/filtering classes from classic loop operations, it improved readability quite a lot
    -I learned Java Swing when designing my prior UI, which I abandoned in favor of more modern JavaFx. There is no way I could do such UI in Java Swing in any reasonable amount of time.
    -I learned how to host locally a database, how to connect to it and work with it
    -I learned how to use Maven - project manager, how to add libraries to my project
    -I learned how to use Spring annotations, and although it is not a web application, it made creating application easier
    -I learned how to use lombok annonations, which reduced a lot of bolierplate code
    -I strated to use Design Patterns and think more about proper project structure

Note: In order to run the application, Docker MySQL cointainer is required, it can be created with Docker installed and running docker-compose.yml