### Part 1: Reading and writing file images
##### Task 1 (worth 20% of the grade)
Write a program which reads a JPEG image file and rewrites it with a different name.

This simple task has the goal of getting you acquainted with the usage of libraries for reading and writing files.

Test your program with the sample images which are available for this assignment. After doing it, check the size differences between the original images and the files you've created.
Do you notice any visual difference between the images? Any changes in the file sizes?

In case there are differences in the file sizes, do a little research on the web regarding JPEG files and try to explain the differences noticed.

### Part 2: Reading, showing and operating on images
Extend the program you wrote on Part I to show the images read and use a toolkit to manage the windows.

Your program should show two different windows (or two images side-by-side on the same window).
Show the original picture on the left and the result of the operation applied onto it on the right.

Operations to be implemented:

##### Task 1 (worth 25% of the grade)
Horizontal and vertical mirroring of the image.
When mirroring the image (either vertically or horizontally)an even number of times,
the result should be equal to the original image.

Try to implement these operations in an efficient way (Tip: consider the usage of command memcpy)
 whenever possible, instead of exchanging a pair of pixels at a time.

Make sure the operations work for images with both even or odd amounts of lines and columns.

##### Task 2 (worth 20% of the grade)
Convert a colored image to shades of gray (luminance).
An image in shaded of gray might be obtained from a colored image by applying the following
formula to each pixel of the original image:

>L = 0.299R + 0.587G + 0.114B,

Where R, G and B are the color components of the original pixel.

When creating an image to be displayed in shades of gray, for each pixel Pi, do:

> Ri = Gi = Bi = Li

Your program should allow the application of the luminance calculation an
arbitrary number of times during its execution.

Question: what happens to an image in shades of gray in case the luminance calculation is
repeatedly applied (e.g. recursively) to the image?

##### Task 3 (worth 25% of the grade)
Implement a (shade) quantization process over the images in shades of gray.
Notice that in this case, as the color space dimension is 1, this quantization process becomes simple.
Thus, your program should receive as input the amount of shades to be used in the quantization process.

##### Task 4 (worth 10% of the grade)
Save the image (result of the operations applied) to a JPEG file.
Provide an option to save the new image as a JPEG file.