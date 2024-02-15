# Image Downloader Desktop Application

A Java Swing application for downloading images from specified URLs concurrently.

## Description

This application allows users to enter up to five URLs of images they want to download simultaneously. It provides features such as pausing, resuming, and canceling downloads. Each download is displayed with a progress bar indicating the download progress. Once downloaded, images are saved to the user's Downloads folder with unique filenames.

## Features

- Download images from specified URLs concurrently
- Pause, resume, and cancel download tasks
- Display download progress with progress bars
- Save downloaded images to the user's Downloads folder with unique filenames
- Display status messages for each download

## Multithreading

The application utilizes multithreading to download images concurrently, improving download performance and responsiveness of the user interface. Key aspects of multithreading implementation include:

- **ExecutorService**: The application uses an ExecutorService with a fixed thread pool size to manage download tasks. This allows multiple downloads to be processed simultaneously without overwhelming system resources.

- **Callable Tasks**: Each image download is like a mini-job. These jobs are neatly packed into Callable objects and handed over to the ExecutorService. It's like giving tasks to a group of workers who can work on them independently.

- **Thread Safety**: When updating the progress bars or showing messages about the downloads, the app is careful not to mess things up. It uses a special thread, called the Event Dispatch Thread, to handle these updates. This keeps everything running smoothly and prevents any issues that could arise from multiple things happening at once. 

  GUI updates, such as progress bar updates and status message display, are performed within the Event Dispatch Thread (EDT) using `SwingUtilities.invokeLater()`. This ensures thread safety and prevents potential concurrency issues when updating Swing components from worker threads.

Overall, these techniques make sure your downloads happen quickly and without causing any hiccups in the app's performance.

## Usage

1. Launch the application.
2. Enter up to five URLs of images you want to download.
3. Click the "Download" button to start downloading.
4. Use the "Pause", "Resume", and "Cancel" buttons to manage download tasks.
5. View download progress and status messages in the application.

---
