FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD 1234
ENV MYSQL_USER soundgood
ENV MYSQL_PASSWORD soundgood
ENV MYSQL_DATABASE school

# ADD src/table_setup.sql /docker-entrypoint-initdb.d
#ADD 20201208_exported_db.sql /docker-entrypoint-initdb.d

# docker run --name sgs_mysql --mount src="$(pwd)",target=/host_files,type=bind -d 
ADD ./dump/20201208_exported_db.sql /docker-entrypoint-initdb.d

EXPOSE 8081
