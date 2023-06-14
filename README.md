# Mobile Development
Welcome, this is the Mobile Development Repository. Written here are information about what this android studio project needed and how it works conceptually.

# Permission And Requirements
## Permission
- Camera
- Internet

## Requirements
- Android Studio
- Kotlin
- Gradle ^8.0
- JVM
- MinSDK 30
- Target SDK 33

# Dependencies
Listed bellow are required libraries for the android studio project
- Android core ktx
- Livedata-ktx and LiveModel-ktx
- Material design
- Retrofit (Manage API)
- Picasso (for image purposes)
- Tensorflow Lite
- Paging
- Datastore
- navigation-fragment and ui
- com.dhaval2404.imagepicker (for taking image purposes)
- com.airbnb.android:lottie

# How the scan feature works
The scan system consisted of 3 parts
- The image classifier
- Image upload
- Description retrieval

### The image clasifier
The image that has been took is now getting analyzed by our tflite within the app, and then get the id of the object that has been classified

### Image Upload
The image that has been scanned is going to uploaded to [our database](https://freshcheck-j5ohfpkera-et.a.run.app/storage/upload) for learning purposes and to enhance our next machine learning that yet to be built 

### Description Retrieval
For the description retrieval, we get the id of the object that has been scanned earlier and then we made a request to https://freshcheck-j5ohfpkera-et.a.run.app/api/getAdditionalData with a query "fruit_id" and then the integer of the id

# How to Use
1. Open the app on your Android device.
2. Select the Detection menu from the main screen.
3. Tap on the "Pick Image" button to choose an image from your device's gallery.
4. Once you have selected the image, press the "Detection" button to initiate the scanning process.
5. Wait for the app to analyze the image using the built-in image classifier.
6. The app will upload the scanned image to our database for further learning and analysis.
7. After the analysis is complete, the app will retrieve additional data and descriptions for the detected object.
8. The results of the object detection and its description will be displayed on the screen.

Feel free to customize the instructions based on the specific UI elements or functionality of your app.

# Contributors
- Amar Al Fatah 
- Zahran Difa Vidian