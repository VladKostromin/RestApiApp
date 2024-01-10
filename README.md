REST Api application including CRUD operations on entities: User, File, Even
Stores the download history as an Event entity.
Interaction using postman, response as a JSON object.
Endpoints for CRUD operations:
/users
parameters for requests:
get - {"user_id" : "Integer"}
post - {"user_id" : "Integer", "name" : "String"}
put - {"name" : "String"}
delete - {"user_id" : "Integer"}

/files
params for request:
get - {"file_id" : "Integer", "user_id" : "Integer"}
post - {"file_id" : "Integer", "name" : "String". "file_path" : "String"}
put - {"name" : "String", "file_path" : "String"}
delete - {"file_id" : "Integer"}

/events
params for request:
get - {"event_id" : "Integer"}
post - {"event_id" : "Integer", "file_id" : "Integer", "user_id" : "Integer"}
put - calling doPost()
delete {"event_id" : "Integer"}
