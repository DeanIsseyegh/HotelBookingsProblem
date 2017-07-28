# HotelBookingsProblem

Implemented using plain Java/Gradle using BDD/TDD.

For the sake of the excercise, I created a `DomainStore` object which acts a database facade and stores bookings/rooms for the hotel (usually would let something like ORM handle this).

It is assumed hotel has a fixed number of rooms when they start up the application.

Decided to use a `Map` to store rooms and bookings as rooms are unique (can be used as a key) and a booking must always belong to a room. 
For a real application, I would have used a Room table with Room Number as a primary key, with a Bookings table that links to the Room table with the room Room Number as foreign key.

Thread safety is acquired by adding the synchronized keyword to the addBooking method (It is assumed the app will run with only one BookingManager instance)
This will lock any of the other read/insert BookingManager instance calls preventing any race conditions (e.g. two bookings being made at the same time, or a manager thinking a room is available just as another one books it out).
Because the other methods are read only, it does not make sense to lock them (e.g. does not matter if managers are concurrently reading the domain store.)

SynchronizedMap was used to allow thread-safe iteration over all the bookings, specifically for the `domainStore.getBookings()` method


