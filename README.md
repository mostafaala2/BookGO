# BookGO
Book Listing App
Android app that leverages the GoogleBooks API to search books and display cover images. 

The app is composed of two screens. The first screen displays a list of books, in which, each book is described by its title, author and cover photo and book rating and book price in user country currency. After a user selects a book from the list, a second screen appears displaying additional details about the book.

Book List

![screenshot_1550166949](https://user-images.githubusercontent.com/24213038/52807804-35a86700-3095-11e9-84fb-d2b82d1f21ce.png)

Book Details (Collapsing Toolbar)

![screenshot_1550167305](https://user-images.githubusercontent.com/24213038/52807939-7c965c80-3095-11e9-8bdd-c782df889401.png)
# Overview
The app does the following:

Search a list of books using the [GoogleBooks Search API](https://developers.google.com/books/)
Display the list of books with their cover images and details
Use SearchView to search for books with a title
Show ProgressBar before each network request
Add a detail view to display more information about the selected book from the list

# Libraries
[Picasso](http://square.github.io/picasso)
[Glide](https://github.com/bumptech/glide) For remote image loading .
