print("Started adding the users.");
db = db.getSiblingDB("admin");
db.createUser({  user: "julia",  pwd: "password",  roles: [{ role: "userAdminAnyDatabase", db: "admin" }],});
print("End adding the user roles.");