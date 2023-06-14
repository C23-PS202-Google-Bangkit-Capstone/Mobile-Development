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
- com.dhaval2404.imagepicker (for taking image purposes)

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


## Contributors
- Amar Al Fatah 
- Zahran Difa Vidian
