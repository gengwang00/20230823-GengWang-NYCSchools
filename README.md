# NYC-School Android App Readme

The NYC-School application is designed to display a list of NYC high schools and provide SAT scores for the selected school when the user clicks on it. The app follows the MVVM programming pattern and utilizes various technologies to ensure efficient data retrieval and UI rendering.

## Features

1. Display NYC High Schools: Upon launching the app, a list of NYC high schools is retrieved via an API call and displayed on the main screen.

2. View SAT Scores: When the user clicks on a school from the list, the average SAT scores for that school are displayed.

3. Caching SAT Scores: The app employs a caching mechanism to store SAT scores after retrieving them for the first time. Subsequent clicks on a school will not require additional API calls, as the scores will be returned from the cache.

## Technologies Used

The NYC-School app has been implemented using the following technologies:

- Kotlin: The main programming language used in this project, providing a modern and concise syntax for Android development.

- Jetpack Compose: Utilized for building UIs, Jetpack Compose offers a declarative approach to creating interactive and dynamic user interfaces.

- Jetpack Navigation: Used to handle navigation between different screens within the app, ensuring a smooth and cohesive user experience.

- Retrofit and OkHttp: These libraries are employed for handling REST API calls, making it easy to fetch data from external servers.

- Coroutines: Coroutines are used for handling multi-threaded programming in a concise and efficient manner, ensuring smooth execution of asynchronous tasks.

- Hilt: Hilt is utilized for dependency injection, allowing for a cleaner and more maintainable codebase.

## Data Retrieval and Caching

Data retrieval from the API is triggered within the `onCreate()` function, ensuring that the API call is made only once during the app's lifecycle. Once the list of NYC high schools is fetched, it is displayed on the main screen. When a user clicks on a specific school, the app checks if the SAT scores for that school are already cached. If not, another API call is made to retrieve the scores, which are then stored in the cache for future use. Subsequent clicks on the same school will directly fetch the scores from the cache, eliminating the need for additional API calls.

## Setup and Build

To build and run the NYC-School app, follow these steps:

1. Clone the repository from GitHub.

2. Open the project using Android Studio or any compatible IDE.

3. Ensure that you have the necessary dependencies and plugins installed, as specified in the `build.gradle` files.

4. Build and run the app on an Android emulator or physical device.

## Contributing

If you would like to contribute to the NYC-School app, please fork the repository and submit your changes through a pull request. We welcome any bug fixes, enhancements, or new features that can improve the app's functionality and user experience.

## Feedback and Support

If you encounter any issues or have suggestions for improvements, please open an issue on the GitHub repository. We value your feedback and will do our best to address any concerns promptly.

## License

The NYC-School app is open-source and released under the [MIT License](link-to-license). Feel free to use, modify, and distribute the code according to the terms of the license.

Thank you for using the NYC-School app! We hope it provides a valuable and informative experience for users looking to explore NYC high schools and their SAT scores. Happy coding!