FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD 1234
ENV MYSQL_USER guest
ENV MYSQL_PASSWORD 12341234

# ADD src/table_setup.sql /docker-entrypoint-initdb.d
ADD 20201208_exported_db.sql /docker-entrypoint-initdb.d

# docker run --name [enter name] --mount src="$(pwd)",target=/host_files,type=bind -d [IMAGE name]

EXPOSE 8081
