# BackEndRepo

### Description

The Rehearsal Finder project was my final project at the Iron Yard. Two front-end engineering students, Brannon Crumpton and
John Michael McCasland, helped contribute to the project. I was the sole back-end engineering student for this project. The 
purpose of the web app was to write a service that helps artists and musicians find quality, reliable rehearsal spaces. The 
aim is to allow users to browse rehearsal spaces in their area and find the spot that is the right fit. Upon looking at a 
specific rehearsal space, the user can see any relevant details including amenities at the space, available equipment, rules, 
and other pertinent information. If a user is signed up and logged in, they can book a rehearsal time for that space directly
through that page.

The back-end API was written in Java, using the Spring framework. Features of the back-end include: custom serializers and 
parsers for JSON formatted data TX/RX between front and back, PostgreSQL database for rehearsal space and user info, Amazon
Web Services S3 for image storage, Google Maps Geocoding API for map marker creating, and secure user registration and login
using JWT.
