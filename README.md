# Web Crawler Project

This project is a simple web crawler written in Java and built with Maven. It's designed to crawl a specified number of pages starting from a given URL.

## Project Structure

The project consists of several classes:

- `Main.java`: This is the entry point of the application. It creates an instance of the `Scheduler` class and starts the crawling process.

- `Scheduler.java`: This class manages the crawling process. It keeps track of URLs to be crawled and the results of the crawling.

- `Crawler.java`: This class extends `Thread` and does the actual crawling of each page. It also checks if a URL is valid and if it's allowed to be crawled according to the `robot.txt` file.

- `PageResult.java`: This class represents the result of crawling a single page. It contains the URL of the page, the content of the page, and the links found on the page.

## How to Run

1. Clone the repository.
2. Navigate to the project directory.
3. Run `mvn clean install` to build the project.
4. Run `java -cp target/myproject-1.0-SNAPSHOT.jar org.toudonou.Main` to start the application.

## Requirements

- OpenJDK 22
- Maven

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.


## Author

- [Toudonou](https://github.com/Toudonou)