# 1. Install PostgreSQL
brew install postgresql@14

# 2. Start the PostgreSQL service
brew services start postgresql@14

# Wait 3 seconds to let it start up
sleep 3

# 3. Create the 'postgres' user with password '1919'
createuser -s postgres
psql -d postgres -c "ALTER USER postgres PASSWORD '1919';"

# 4. Create the specific database your app expects
createdb -U postgres transportation_system_db05

# 5. Import your database tables and default data
psql -U postgres -d transportation_system_db05 -f db-migration/sequence.sql
psql -U postgres -d transportation_system_db05 -f db-migration/pre-ddl.sql
psql -U postgres -d transportation_system_db05 -f db-migration/dml.sql